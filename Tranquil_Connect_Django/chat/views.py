import requests
import json
import jwt  
from django.shortcuts import render, redirect
from django.http import JsonResponse
from .models import MensajeChat, HistorialChatbot, Usuario
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
import base64
from groq import Groq
import os
from dotenv import load_dotenv

# --- CONFIGURACIÓN API GROQ ---
GROQ_API_KEY = os.environ.get('GROQ_API_KEY')
SECRET_KEY_JWT = os.environ.get('SECRET_KEY_JWT')

# Inicializar cliente
client = Groq(api_key=GROQ_API_KEY)

def procesar_archivo_groq(archivo):
    """
    Procesa archivos y retorna descripción para enviar a Groq.
    """
    try:
        nombre = archivo.name.lower()
        tamaño = archivo.size / (1024*1024)  # MB
        
        # Si es imagen
        if nombre.endswith(('.jpg', '.jpeg', '.png', '.gif', '.webp')):
            descripcion = f"📷 Imagen: {archivo.name} ({tamaño:.1f}MB)"
            return descripcion
        
        # Si es audio
        elif nombre.endswith(('.mp3', '.wav', '.ogg', '.m4a', '.aac')):
            descripcion = f"🎵 Audio: {archivo.name} ({tamaño:.1f}MB)"
            return descripcion
        
        else:
            return f"📎 Archivo: {archivo.name}"
    except Exception as e:
        print(f"Error procesando archivo: {e}")
        return "📎 Archivo adjunto"


@csrf_exempt
def sala_chat(request, grupo_id):
    u_id = request.GET.get('user_id') or request.POST.get('usuario_id', 1)

    mensajes = MensajeChat.objects.filter(
        id_grupo=grupo_id
    ).select_related('id_usuario').order_by('fecha_envio')

    if request.method == 'POST':
        contenido = request.POST.get('contenido')
        imagen = request.FILES.get('imagen')

        if contenido or imagen:
            usuario = Usuario.objects.get(id=u_id)

            MensajeChat.objects.create(
                id_grupo=grupo_id,
                id_usuario=usuario,
                contenido=contenido,
                imagen_url=imagen
            )

        return redirect(f'/chat/{grupo_id}/?user_id={u_id}')

    return render(request, 'chat/sala.html', {
        'mensajes': mensajes,
        'grupo_id': grupo_id,
        'usuario_id_actual': u_id
    })


@csrf_exempt
def chatbot_gemini(request):
    """
    Chatbot con Groq - Lee archivos y tiene muchos tokens disponibles.
    """
    token = request.GET.get('auth')
    u_id = 1

    if token:
        try:
            payload = jwt.decode(token, SECRET_KEY_JWT, algorithms=['HS256'])
            u_id = int(payload.get('sub'))
            print(f"DEBUG: Usuario identificado vía JWT. ID: {u_id}")
        except Exception as e:
            print(f"DEBUG: Error decodificando JWT: {e}")
            u_id = 1

    if request.method == 'POST':
        u_id = int(request.POST.get('usuario_id', u_id))
        pregunta = request.POST.get('mensaje', '')
        archivo = request.FILES.get('archivo')
        
        if pregunta:
            pregunta = pregunta.strip()

        # Procesar archivo si existe
        descripcion_archivo = ""
        if archivo:
            descripcion_archivo = procesar_archivo_groq(archivo)

        # Construir mensaje completo
        if descripcion_archivo:
            pregunta_completa = f"{descripcion_archivo}\n\n{pregunta}".strip()
        else:
            pregunta_completa = pregunta

        if not pregunta_completa:
            return JsonResponse({'error': 'No enviaste mensaje o archivo'}, status=400)

        try:
            # Usar Groq con modelo actual disponible
            chat_completion = client.chat.completions.create(
                messages=[
                    {
                        "role": "system",
                        "content": (
                            "Eres Tranquil Connect AI, un asistente virtual empático, comprensivo y "
                            "altamente capacitado para escuchar y brindar orientación de apoyo emocional. "
                            "Responde siempre en español de manera cálida, cercana y concisa. "
                            "Si el usuario comparte archivos (fotos, audios, etc), comenta sobre ellos con empatía."
                        )
                    },
                    {
                        "role": "user",
                        "content": pregunta_completa,
                    }
                ],
                model="llama-3.3-70b-versatile",
                temperature=0.7,
                max_tokens=2048,
            )

            respuesta_texto = chat_completion.choices[0].message.content

            # Guardar en DB
            HistorialChatbot.objects.create(
                usuario_id=u_id, 
                mensaje_usuario=pregunta_completa, 
                respuesta_bot=respuesta_texto
            )

            return JsonResponse({'respuesta': respuesta_texto})

        except Exception as e:
            print(f"❌ ERROR GROQ: {str(e)}")
            return JsonResponse({'error': f"Error: {str(e)}"}, status=500)

    # GET: Mostrar historial
    historial = HistorialChatbot.objects.filter(usuario_id=u_id).order_by('fecha_envio')
    
    return render(request, 'chat/chatbot.html', {
        'historial': historial, 
        'usuario_id': u_id,
        'token_auth': token  
    })


@csrf_exempt
def eliminar_mensaje(request):
    """Elimina un mensaje si pertenece al usuario que lo solicita.

    Request (POST): mensaje_id, usuario_id
    Response JSON: {ok: True} o {ok: False, error: '...'}
    """
    if request.method != 'POST':
        return JsonResponse({'ok': False, 'error': 'Método no permitido'}, status=405)

    mensaje_id = request.POST.get('mensaje_id') or request.POST.get('id')
    usuario_id = request.POST.get('usuario_id') or request.POST.get('user_id')

    if not mensaje_id or not usuario_id:
        return JsonResponse({'ok': False, 'error': 'Faltan parámetros'}, status=400)

    try:
        mensaje = MensajeChat.objects.get(id=mensaje_id)
    except MensajeChat.DoesNotExist:
        return JsonResponse({'ok': False, 'error': 'Mensaje no encontrado'}, status=404)

    # Solo el autor puede eliminar su mensaje
    try:
        if str(mensaje.id_usuario.id) != str(usuario_id):
            return JsonResponse({'ok': False, 'error': 'No autorizado'}, status=403)
    except Exception:
        return JsonResponse({'ok': False, 'error': 'Error verificando propietario'}, status=400)

    # Borramos el registro (podríamos marcar como eliminado en lugar de borrar)
    try:
        mensaje.delete()
        return JsonResponse({'ok': True})
    except Exception as e:
        return JsonResponse({'ok': False, 'error': str(e)}, status=500)
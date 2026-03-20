import requests
import json
import jwt  # Importante para leer el token de Java
from django.shortcuts import render, redirect
from django.http import JsonResponse
from .models import MensajeChat, HistorialChatbot
from django.views.decorators.csrf import csrf_exempt

# --- CONFIGURACIÓN ---
API_KEY = "AIzaSyD5FcyLv1-R6v_I96wLtMdSCYsQBGqnbN8"

# Esta clave DEBE ser idéntica a la SECRET_KEY que pusiste en Java
SECRET_KEY_JWT = "esta_es_una_clave_muy_segura_y_larga_para_jwt_123456"

@csrf_exempt
def sala_chat(request, grupo_id):
    # 1. Intentamos obtener el usuario de la URL (GET) o del formulario (POST)
    u_id = request.GET.get('user_id') or request.POST.get('usuario_id', 1)
    
    mensajes = MensajeChat.objects.filter(id_grupo=grupo_id).order_by('fecha_envio')
    
    if request.method == 'POST':
        contenido = request.POST.get('contenido')
        if contenido:
            # Guardamos con el ID que ya capturamos arriba
            MensajeChat.objects.create(id_grupo=grupo_id, id_usuario=u_id, contenido=contenido)
        
        # IMPORTANTE: Al redirigir, volvemos a pasar el user_id en la URL para que no se pierda
        return redirect(f'/chat/{grupo_id}/?user_id={u_id}')
    
    # Pasamos 'u_id' al contexto como 'usuario_id_actual' para usarlo en el HTML
    return render(request, 'chat/sala.html', {
        'mensajes': mensajes, 
        'grupo_id': grupo_id, 
        'usuario_id_actual': u_id
    })

@csrf_exempt
def chatbot_gemini(request):
    # 1. IDENTIFICACIÓN DEL USUARIO (Vía Token JWT o POST)
    token = request.GET.get('auth')
    u_id = 1  # ID por defecto por seguridad

    if token:
        try:
            # Decodificamos el token enviado por Spring Boot
            payload = jwt.decode(token, SECRET_KEY_JWT, algorithms=['HS256'])
            # En Java usamos .setSubject(id), que en el token es la clave 'sub'
            u_id = int(payload.get('sub'))
            print(f"DEBUG: Usuario identificado vía JWT. ID: {u_id}")
        except Exception as e:
            print(f"DEBUG: Error decodificando JWT: {e}")
            u_id = 1

    # Si es una petición de mensaje (POST), intentamos mantener el u_id enviado
    if request.method == 'POST':
        u_id = int(request.POST.get('usuario_id', u_id))
        pregunta = request.POST.get('mensaje')
        
        if not pregunta:
            return JsonResponse({'error': 'No enviaste mensaje'}, status=400)

        # URL para Gemini
        url = f"https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key={API_KEY}"
        
        try:
            payload_gemini = {
                "contents": [{
                    "parts": [{"text": f"Responde como asistente de Tranquil Connect: {pregunta}"}]
                }]
            }
            
            response = requests.post(url, json=payload_gemini, timeout=15)
            res_data = response.json()

            if 'candidates' in res_data:
                respuesta_texto = res_data['candidates'][0]['content']['parts'][0]['text']
                
                # 2. GUARDAR EN LA BASE DE DATOS CON ID REAL
                HistorialChatbot.objects.create(
                    usuario_id=u_id, 
                    mensaje_usuario=pregunta, 
                    respuesta_bot=respuesta_texto
                )
                
                return JsonResponse({'respuesta': respuesta_texto})
            
            return JsonResponse({'error': 'La IA no pudo responder'}, status=500)

        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    # 3. FILTRAR EL HISTORIAL POR EL USUARIO REAL
    historial = HistorialChatbot.objects.filter(usuario_id=u_id).order_by('fecha_envio')
    
    return render(request, 'chat/chatbot.html', {
        'historial': historial, 
        'usuario_id': u_id,
        'token_auth': token  # Pasamos el token para que el HTML lo conserve
    })
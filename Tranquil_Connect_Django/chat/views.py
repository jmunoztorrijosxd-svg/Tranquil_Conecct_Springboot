from django.shortcuts import render, redirect
from .models import MensajeChat
from django.views.decorators.csrf import csrf_exempt # Importante para evitar el 403

@csrf_exempt # Esto permite que el formulario funcione dentro del iframe sin error CSRF
def sala_chat(request, grupo_id):
    # Obtenemos los mensajes filtrados por grupo
    mensajes = MensajeChat.objects.filter(id_grupo=grupo_id).order_by('fecha_envio')
    
    if request.method == 'POST':
        contenido = request.POST.get('contenido')
        imagen = request.FILES.get('imagen')
        
        # Guardamos el mensaje (id_usuario 1 por ahora para pruebas)
        if contenido or imagen: # Validamos que no envíe mensajes vacíos
            MensajeChat.objects.create(
                id_grupo=grupo_id,
                id_usuario=1, 
                contenido=contenido,
                imagen_url=imagen
            )
        return redirect('sala_chat', grupo_id=grupo_id)

    return render(request, 'chat/sala.html', {'mensajes': mensajes, 'grupo_id': grupo_id})
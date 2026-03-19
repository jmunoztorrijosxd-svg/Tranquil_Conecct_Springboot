from django.db import models

class MensajeChat(models.Model):
    # Usamos IntegerField porque en tu DB son int(11)
    id_grupo = models.IntegerField(db_column='id_grupo') 
    id_usuario = models.IntegerField(db_column='id_usuario')
    contenido = models.TextField(null=True, blank=True)
    # Aquí se guardarán las fotos
    imagen_url = models.ImageField(upload_to='chat_fotos/', null=True, blank=True)
    fecha_envio = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'mensajes_chat'
        managed = False # Muy importante: Django no tocará tus tablas de Java
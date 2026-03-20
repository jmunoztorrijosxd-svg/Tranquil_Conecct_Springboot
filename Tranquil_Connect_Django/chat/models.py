from django.db import models

# --- MODELO EXISTENTE (Chat de Grupos) ---
class MensajeChat(models.Model):
    id_grupo = models.IntegerField(db_column='id_grupo') 
    id_usuario = models.IntegerField(db_column='id_usuario')
    contenido = models.TextField(null=True, blank=True)
    imagen_url = models.ImageField(upload_to='chat_fotos/', null=True, blank=True)
    fecha_envio = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'mensajes_chat'
        managed = False 

class HistorialChatbot(models.Model):
    # db_column debe ser EXACTAMENTE como aparece en phpMyAdmin
    usuario_id = models.IntegerField(db_column='usuario_id') 
    mensaje_usuario = models.TextField(db_column='mensaje_usuario')
    respuesta_bot = models.TextField(db_column='respuesta_bot')
    fecha_envio = models.DateTimeField(auto_now_add=True, db_column='fecha_envio')

    class Meta:
        db_table = 'historial_chatbot'
        managed = False  # Esto es correcto: Django solo lee y escribe, no crea la tabla
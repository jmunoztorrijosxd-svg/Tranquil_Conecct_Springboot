from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static

# Importamos las vistas del chatbot y chat grupal
from chat.views import sala_chat, chatbot_gemini 
from chat.views import eliminar_mensaje

urlpatterns = [
    # Panel de administración de Django
    path('admin/', admin.site.urls),

    # Rutas de la aplicación de posts (donde está el panel de moderación)
    path('', include('posts.urls')),
    
    # Chat Grupal
    path('chat/<int:grupo_id>/', sala_chat, name='sala_chat'),
    path('chat/delete_message/', eliminar_mensaje, name='eliminar_mensaje'),
    
    # Chatbot con Gemini
    path('chatbot/', chatbot_gemini, name='chatbot_gemini'),
]

# --- CONEXIÓN DE ARCHIVOS MULTIMEDIA ---
# Esto permite que Django sirva las imágenes y videos desde la carpeta 'uploads' 
# de Spring Boot definida en MEDIA_ROOT
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
from django.contrib import admin
from django.urls import path
from django.conf import settings
from django.conf.urls.static import static
# Importamos la nueva vista del chatbot
from chat.views import sala_chat, chatbot_gemini 

urlpatterns = [
    path('admin/', admin.site.urls),
    
    # Chat Grupal (El que ya tenías)
    path('chat/<int:grupo_id>/', sala_chat, name='sala_chat'),
    
    # Chatbot con Gemini (Nueva ruta)
    path('chatbot/', chatbot_gemini, name='chatbot_gemini'),
]

# Esto permite que las fotos se vean en el navegador
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
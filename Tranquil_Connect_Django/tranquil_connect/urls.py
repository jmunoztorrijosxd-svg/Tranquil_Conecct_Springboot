from django.contrib import admin
from django.urls import path
from django.conf import settings
from django.conf.urls.static import static
from chat.views import sala_chat

urlpatterns = [
    path('admin/', admin.site.urls),
    path('chat/<int:grupo_id>/', sala_chat, name='sala_chat'),
]

# Esto permite que las fotos se vean en el navegador
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
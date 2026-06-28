from django.db import models
from django.contrib.auth.models import User

class Post(models.Model):
    contenido = models.TextField()
    imagen = models.FileField(upload_to='uploads/', null=True, blank=True) 
    fecha_publicacion = models.DateTimeField()
    # Cambiamos a IntegerField si el JOIN con User da problemas
    usuario_id = models.IntegerField(db_column='usuario_id')

    def __str__(self):
        return f"Post {self.id} - {self.contenido[:20]}"

    class Meta:
        db_table = 'publicaciones'  # Conecta con tu tabla real
        managed = False             # Evita que Django intente modificar la tabla
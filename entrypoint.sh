#!/bin/sh

# 1. Configurar Nginx dinámicamente para usar el puerto que Render asigna
# Esto reemplaza el puerto 80 dentro de tu nginx.conf con el puerto que Render espera
sed -i "s/listen 80;/listen $PORT;/" /etc/nginx/conf.d/default.conf

echo "Iniciando Spring Boot..."
java -Dspring.datasource.url="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true" \
     -Dspring.datasource.username="${DB_USER}" \
     -Dspring.datasource.password="${DB_PASSWORD}" \
     -jar /app/app.jar > /var/log/springboot.log 2>&1 &

echo "Iniciando Django (Gunicorn)..."
cd /app/django && gunicorn tranquil_connect.wsgi:application \
    --bind 127.0.0.1:8000 \
    --workers 3 \
    --timeout 120 > /var/log/gunicorn.log 2>&1 &

echo "Iniciando Nginx en el puerto $PORT..."
# Nginx debe ser el proceso final (en primer plano) para mantener el contenedor activo
nginx -g "daemon off;"
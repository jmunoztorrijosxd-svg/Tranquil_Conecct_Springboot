#!/bin/sh
# 1. Iniciar Spring Boot en segundo plano (escuchando en 8090)
java -jar -Dspring.profiles.active=prod -Dserver.port=8090 /app/app.jar &

# 2. Iniciar Django en segundo plano (escuchando en 127.0.0.1:8000)
# Es vital usar 127.0.0.1 en lugar de 0.0.0.0 para que solo Nginx acceda
cd /app/django && gunicorn tranquil_connect.wsgi:application --bind 127.0.0.1:8000 &

# 3. Iniciar Nginx en primer plano
# Esto mantendrá el contenedor vivo
nginx -g "daemon off;"
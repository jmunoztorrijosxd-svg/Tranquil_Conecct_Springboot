#!/bin/sh
# 1. Iniciar Spring Boot en segundo plano
java -jar -Dspring.profiles.active=prod -Dserver.port=8090 /app/app.jar &
# 2. Iniciar Django en segundo plano
cd /app/django && gunicorn tranquil_connect.wsgi:application --bind 0.0.0.0:8000 &
# 3. Iniciar Nginx en primer plano
nginx -g "daemon off;"
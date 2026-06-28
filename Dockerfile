# ETAPA 1: Construir la aplicación Spring Boot
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
# Ajustamos las rutas a tu estructura actual
COPY ./Tranquil_Conecct_Springboot/pom.xml .
COPY ./Tranquil_Conecct_Springboot/src ./src
RUN mvn clean package -DskipTests -q

# ETAPA 2: Imagen Final
FROM python:3.12-slim
RUN apt-get update && apt-get install -y \
    openjdk-17-jre \
    nginx \
    default-libmysqlclient-dev \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
# Copiar código de Django
COPY ./Tranquil_Connect_Django /app/django
# Copiar el JAR compilado
COPY --from=builder /app/target/*.jar /app/app.jar
# Copiar configuración de Nginx (asegúrate que esté en Tranquil_connect/nginx/nginx.prod.conf)
COPY ./nginx/nginx.prod.conf /etc/nginx/nginx.conf

# Instalar dependencias de Python
RUN pip install --no-cache-dir -r /app/django/requirements.txt gunicorn

# Script de inicio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
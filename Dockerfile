# ETAPA 1: Construcción (Spring Boot)
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia los archivos de Spring Boot (Asegúrate de que esta carpeta exista y tenga ese nombre)
COPY ./Tranquil_Conecct_Springboot/pom.xml .
COPY ./Tranquil_Conecct_Springboot/src ./src
RUN mvn clean package -DskipTests -q

# ETAPA 2: Imagen Final
FROM python:3.12-slim

# Instalación de paquetes con actualización de índices para evitar el error 100
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk-headless \
    nginx \
    default-libmysqlclient-dev \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar el código de Django
COPY ./Tranquil_Connect_Django /app/django

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/target/*.jar /app/app.jar

# Copiar configuración de Nginx
COPY ./nginx/nginx.prod.conf /etc/nginx/nginx.conf

# Instalar dependencias de Python
RUN pip install --no-cache-dir -r /app/django/requirements.txt gunicorn

# Configurar el script de inicio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
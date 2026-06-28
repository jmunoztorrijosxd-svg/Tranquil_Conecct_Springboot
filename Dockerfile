# ETAPA 1: Construcción (Spring Boot)
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY ./Tranquil_Conecct_Springboot/pom.xml .
COPY ./Tranquil_Conecct_Springboot/src ./src
RUN mvn clean package -DskipTests -q

# ETAPA 2: Imagen Final
# Cambiamos a esta base que ya tiene Java 17 y es estable (Ubuntu 22.04)
FROM eclipse-temurin:17-jre-jammy

# Instalamos Python, Nginx y dependencias de sistema
RUN apt-get update && apt-get install -y --no-install-recommends \
    python3 \
    python3-pip \
    nginx \
    default-libmysqlclient-dev \
    build-essential \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar el código de Django
COPY ./Tranquil_Connect_Django /app/django

# Copiar el JAR construido desde la etapa anterior
COPY --from=builder /app/target/*.jar /app/app.jar

# Configuración Nginx
COPY ./nginx/nginx.prod.conf /etc/nginx/nginx.conf

# Instalación de dependencias de Python
# Nota: En esta imagen, usamos pip3 directamente
RUN pip3 install --no-cache-dir --upgrade pip && \
    pip3 install --no-cache-dir -r /app/django/requirements.txt gunicorn

# Script de inicio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
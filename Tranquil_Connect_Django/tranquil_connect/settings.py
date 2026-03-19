import pymysql
import os
from pathlib import Path

# 1. PUENTE PYMYSQL
pymysql.install_as_MySQLdb()

# --- PARCHE 1: PARA ERRORES DE CONEXIÓN Y MIGRACIONES ---
from django.db.backends.mysql.base import DatabaseWrapper
DatabaseWrapper.can_return_columns_from_insert = False

def patched_init(self, *args, **kwargs):
    original_init(self, *args, **kwargs)
    if self.data_types_suffix is None:
        self.data_types_suffix = {}

original_init = DatabaseWrapper.__init__
DatabaseWrapper.__init__ = patched_init
# -------------------------------------------------------

BASE_DIR = Path(__file__).resolve().parent.parent
SECRET_KEY = 'django-insecure-d!i@5-1+%l@k&y9zelr=0u7_tzmkpfh9cz(e*a6=)lsqvjn&c)'
DEBUG = True
ALLOWED_HOSTS = []

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'chat', 
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'tranquil_connect.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'tranquil_connect.wsgi.application'

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'NAME': 'salud_mental',
        'USER': 'root',
        'PASSWORD': '', 
        'HOST': '127.0.0.1',
        'PORT': '3306',
        'OPTIONS': {
            'init_command': "SET sql_mode='STRICT_TRANS_TABLES'",
        },
    }
}

LANGUAGE_CODE = 'es-co'
TIME_ZONE = 'America/Bogota'
USE_I18N = True
USE_TZ = True
STATIC_URL = 'static/'
DEFAULT_AUTO_FIELD = 'django.db.models.BigAutoField'

# --- PARCHE 2: SOLUCIÓN FINAL PARA EL ERROR 'RETURNING' EN EL CHAT ---
# Este bloque impide que Django use RETURNING al hacer .create() en las vistas
from django.db.backends.mysql.features import DatabaseFeatures
def patched_can_return_columns_from_insert(self):
    return False
DatabaseFeatures.can_return_columns_from_insert = property(patched_can_return_columns_from_insert)
X_FRAME_OPTIONS = 'ALLOWALL'
CSRF_TRUSTED_ORIGINS = ['http://localhost:8090', 'http://127.0.0.1:8090']
# Permite que las cookies se envíen dentro del iframe
SESSION_COOKIE_SAMESITE = 'None'
SESSION_COOKIE_SECURE = False  # Ponlo en True solo cuando uses HTTPS
CSRF_COOKIE_SAMESITE = 'None'
CSRF_COOKIE_SECURE = False     # Ponlo en True solo cuando uses HTTPS
# ---------------------------------------------------------------------
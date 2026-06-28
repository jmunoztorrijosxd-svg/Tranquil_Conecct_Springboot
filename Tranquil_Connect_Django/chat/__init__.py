import pymysql

# Engañamos a Django con la versión del conector
pymysql.version_info = (2, 2, 8, "final", 0)
pymysql.install_as_MySQLdb()

# --- NUEVA LÍNEA PARA ENGAÑAR A DJANGO CON MARIADB ---
from django.db.backends.mysql.base import DatabaseWrapper
DatabaseWrapper.display_name = 'MariaDB'
# Esto le dice a Django que tu DB es 11.0 (aunque sea 10.4) para que no se queje
DatabaseWrapper.data_types_suffix = None
DatabaseWrapper.mysql_version = (11, 0, 0)
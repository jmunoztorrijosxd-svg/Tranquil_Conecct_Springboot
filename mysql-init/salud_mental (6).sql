create database if not exists salud_mental;
use salud_mental;
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-06-2026 a las 18:06:13
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `salud_mental`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agenda`
--

CREATE TABLE `agenda` (
  `codigo_agenda` int(11) NOT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `telefono` bigint(20) NOT NULL,
  `id_usuario_solicita` int(11) NOT NULL,
  `usuario_usuario_elige` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `apoyo`
--

CREATE TABLE `apoyo` (
  `codigo_apoyo` int(11) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `enlace` varchar(255) DEFAULT NULL,
  `permisos` varchar(255) NOT NULL,
  `fecha` date DEFAULT NULL,
  `reacciones` varchar(10) DEFAULT NULL,
  `comentarios` varchar(1000) DEFAULT NULL,
  `historial` varchar(255) DEFAULT NULL,
  `formato` varchar(100) NOT NULL,
  `op_descarga` varchar(100) NOT NULL,
  `duracion` mediumint(9) DEFAULT NULL,
  `codigo_agenda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_group`
--

CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_group_permissions`
--

CREATE TABLE `auth_group_permissions` (
  `id` bigint(20) NOT NULL,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_permission`
--

CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `auth_permission`
--

INSERT INTO `auth_permission` (`id`, `name`, `content_type_id`, `codename`) VALUES
(1, 'Can add log entry', 1, 'add_logentry'),
(2, 'Can change log entry', 1, 'change_logentry'),
(3, 'Can delete log entry', 1, 'delete_logentry'),
(4, 'Can view log entry', 1, 'view_logentry'),
(5, 'Can add permission', 2, 'add_permission'),
(6, 'Can change permission', 2, 'change_permission'),
(7, 'Can delete permission', 2, 'delete_permission'),
(8, 'Can view permission', 2, 'view_permission'),
(9, 'Can add group', 3, 'add_group'),
(10, 'Can change group', 3, 'change_group'),
(11, 'Can delete group', 3, 'delete_group'),
(12, 'Can view group', 3, 'view_group'),
(13, 'Can add user', 4, 'add_user'),
(14, 'Can change user', 4, 'change_user'),
(15, 'Can delete user', 4, 'delete_user'),
(16, 'Can view user', 4, 'view_user'),
(17, 'Can add content type', 5, 'add_contenttype'),
(18, 'Can change content type', 5, 'change_contenttype'),
(19, 'Can delete content type', 5, 'delete_contenttype'),
(20, 'Can view content type', 5, 'view_contenttype'),
(21, 'Can add session', 6, 'add_session'),
(22, 'Can change session', 6, 'change_session'),
(23, 'Can delete session', 6, 'delete_session'),
(24, 'Can view session', 6, 'view_session'),
(25, 'Can add historial chatbot', 7, 'add_historialchatbot'),
(26, 'Can change historial chatbot', 7, 'change_historialchatbot'),
(27, 'Can delete historial chatbot', 7, 'delete_historialchatbot'),
(28, 'Can view historial chatbot', 7, 'view_historialchatbot'),
(29, 'Can add mensaje chat', 8, 'add_mensajechat'),
(30, 'Can change mensaje chat', 8, 'change_mensajechat'),
(31, 'Can delete mensaje chat', 8, 'delete_mensajechat'),
(32, 'Can view mensaje chat', 8, 'view_mensajechat');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_user`
--

CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(150) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_user_groups`
--

CREATE TABLE `auth_user_groups` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auth_user_user_permissions`
--

CREATE TABLE `auth_user_user_permissions` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cita`
--

CREATE TABLE `cita` (
  `id` bigint(20) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `psicologo_id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `estado` varchar(20) DEFAULT 'PENDIENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`id`, `paciente_id`, `psicologo_id`, `fecha`, `estado`) VALUES
(1, 3, 6, '2026-11-25', 'APROBADA'),
(2, 7, 6, '2026-03-21', 'APROBADA'),
(3, 9, 6, '2026-04-11', 'APROBADA'),
(4, 6, 12, '2026-05-07', 'APROBADA'),
(5, 1, 12, '2026-04-25', 'PENDIENTE'),
(6, 1, 12, '2026-06-24', 'PENDIENTE'),
(7, 1, 12, '2026-06-19', 'PENDIENTE'),
(8, 1, 12, '2026-06-23', 'PENDIENTE'),
(9, 1, 12, '2026-06-19', 'PENDIENTE'),
(10, 1, 12, '2026-06-19', 'PENDIENTE'),
(11, 1, 12, '2026-06-20', 'PENDIENTE'),
(12, 1, 12, '2026-06-26', 'PENDIENTE'),
(13, 1, 12, '2026-06-23', 'PENDIENTE'),
(14, 1, 12, '2026-07-11', 'APROBADA'),
(15, 1, 12, '2026-07-10', 'APROBADA'),
(16, 1, 12, '2026-08-26', 'PENDIENTE'),
(17, 1, 12, '2026-07-09', 'PENDIENTE'),
(18, 1, 12, '2026-09-04', 'PENDIENTE'),
(19, 1, 12, '2026-09-25', 'PENDIENTE'),
(20, 1, 12, '2026-11-24', 'PENDIENTE'),
(21, 1, 12, '2026-12-10', 'PENDIENTE'),
(22, 1, 12, '2026-07-09', 'PENDIENTE'),
(23, 1, 12, '2026-12-01', 'PENDIENTE'),
(24, 1, 12, '2026-07-11', 'PENDIENTE'),
(25, 1, 12, '2026-07-03', 'APROBADA'),
(26, 1, 12, '2026-07-04', 'APROBADA'),
(27, 1, 12, '2026-10-30', 'APROBADA'),
(28, 1, 12, '2026-07-03', 'APROBADA'),
(29, 1, 12, '2026-07-11', 'APROBADA'),
(30, 1, 12, '2026-12-29', 'APROBADA'),
(31, 1, 12, '2026-06-27', 'PENDIENTE'),
(32, 1, 12, '2026-06-25', 'PENDIENTE'),
(33, 1, 12, '2026-07-10', 'PENDIENTE'),
(34, 1, 12, '2026-06-26', 'APROBADA'),
(35, 1, 12, '2026-06-27', 'APROBADA'),
(36, 1, 12, '2027-12-15', 'APROBADA'),
(37, 1, 12, '2026-07-09', 'APROBADA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `codigo_recuperacion`
--

CREATE TABLE `codigo_recuperacion` (
  `id` bigint(20) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `expiracion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE `comentarios` (
  `codigo` int(11) NOT NULL,
  `foro_codigo_foro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `debate`
--

CREATE TABLE `debate` (
  `codigo` int(11) NOT NULL,
  `foro_codigo_foro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `desempeño`
--

CREATE TABLE `desempeño` (
  `codigo_desempeño` bigint(20) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `calificacion` int(11) NOT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `condicion` varchar(600) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL,
  `psicologo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `desempeño`
--

INSERT INTO `desempeño` (`codigo_desempeño`, `descripcion`, `calificacion`, `fecha_hora`, `condicion`, `id_usuario`, `psicologo_id`) VALUES
(1, NULL, 60, '2026-06-08 15:28:30', 'xd', 6, 12),
(2, NULL, 60, '2026-06-08 15:30:54', 'xd', 6, 12),
(3, NULL, 20, '2026-06-08 15:40:54', 'xd', 6, 12),
(4, NULL, 10, '2026-06-08 15:46:42', 'xdd', 6, 12),
(5, NULL, 20, '2026-06-08 15:49:16', 'xd', 6, 12),
(6, NULL, 23, '2026-06-08 15:52:37', 'xdd', 6, 12),
(7, 'el paciente mostro mejoria', 40, '2026-06-08 15:53:10', 'ninguna', 6, 12),
(8, NULL, 10, '2026-06-19 18:15:02', 'ninguna', 6, 12),
(9, NULL, 10, '2026-06-19 18:15:02', 'ninguna', 6, 12),
(10, 'no se', 10, '2026-06-19 18:15:25', 'ninguna', 6, 12),
(11, 'de', 23, '2026-06-19 18:27:04', 'nose', 1, 12),
(12, NULL, 100, '2026-06-19 18:28:16', 'ni idea', 1, 12),
(13, 'ni idea', 100, '2026-06-19 18:28:33', 'nose', 6, 12),
(14, 'messi', 35, '2026-06-23 19:08:53', 'nose', 6, 12),
(15, 'DX', 100, '2026-06-23 19:10:30', 'pelusa', 6, 12),
(16, 'dc', 45, '2026-06-23 19:32:13', 'nose', 6, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `django_admin_log`
--

CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext DEFAULT NULL,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) UNSIGNED NOT NULL CHECK (`action_flag` >= 0),
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `django_content_type`
--

CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `django_content_type`
--

INSERT INTO `django_content_type` (`id`, `app_label`, `model`) VALUES
(1, 'admin', 'logentry'),
(3, 'auth', 'group'),
(2, 'auth', 'permission'),
(4, 'auth', 'user'),
(7, 'chat', 'historialchatbot'),
(8, 'chat', 'mensajechat'),
(5, 'contenttypes', 'contenttype'),
(6, 'sessions', 'session');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `django_migrations`
--

CREATE TABLE `django_migrations` (
  `id` bigint(20) NOT NULL,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `django_migrations`
--

INSERT INTO `django_migrations` (`id`, `app`, `name`, `applied`) VALUES
(1, 'contenttypes', '0001_initial', '2026-03-20 16:46:10.508403'),
(2, 'auth', '0001_initial', '2026-03-20 16:46:10.730040'),
(3, 'admin', '0001_initial', '2026-03-20 16:46:10.797828'),
(4, 'admin', '0002_logentry_remove_auto_add', '2026-03-20 16:46:10.804391'),
(5, 'admin', '0003_logentry_add_action_flag_choices', '2026-03-20 16:46:10.812886'),
(6, 'contenttypes', '0002_remove_content_type_name', '2026-03-20 16:46:10.847295'),
(7, 'auth', '0002_alter_permission_name_max_length', '2026-03-20 16:46:10.873900'),
(8, 'auth', '0003_alter_user_email_max_length', '2026-03-20 16:46:10.889511'),
(9, 'auth', '0004_alter_user_username_opts', '2026-03-20 16:46:10.897607'),
(10, 'auth', '0005_alter_user_last_login_null', '2026-03-20 16:46:10.916073'),
(11, 'auth', '0006_require_contenttypes_0002', '2026-03-20 16:46:10.916073'),
(12, 'auth', '0007_alter_validators_add_error_messages', '2026-03-20 16:46:10.929963'),
(13, 'auth', '0008_alter_user_username_max_length', '2026-03-20 16:46:10.980691'),
(14, 'auth', '0009_alter_user_last_name_max_length', '2026-03-20 16:46:10.997561'),
(15, 'auth', '0010_alter_group_name_max_length', '2026-03-20 16:46:11.006939'),
(16, 'auth', '0011_update_proxy_permissions', '2026-03-20 16:46:11.016021'),
(17, 'auth', '0012_alter_user_first_name_max_length', '2026-03-20 16:46:11.035082'),
(18, 'sessions', '0001_initial', '2026-03-20 16:46:11.056240'),
(19, 'chat', '0001_initial', '2026-03-20 16:48:12.959891');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `django_session`
--

CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `enlaces`
--

CREATE TABLE `enlaces` (
  `codigo` int(11) NOT NULL,
  `foro_codigo_foro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudios`
--

CREATE TABLE `estudios` (
  `codigo` int(11) NOT NULL,
  `usuario_id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro_social`
--

CREATE TABLE `foro_social` (
  `codigo_foro` int(11) NOT NULL,
  `estado_mensaje` varchar(100) DEFAULT NULL,
  `reacciones` varchar(10) DEFAULT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `comentarios` varchar(1000) DEFAULT NULL,
  `enlaces` varchar(255) DEFAULT NULL,
  `grupo_codigo_grupo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `id_grupo` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `num_miembros` int(11) NOT NULL,
  `motivo_salida` varchar(255) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `id_usuario_creador` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`id_grupo`, `nombre`, `num_miembros`, `motivo_salida`, `descripcion`, `id_usuario_creador`) VALUES
(1, 'grupo de relajacion', 15, 'nolose', 'xd', NULL),
(2, 'paz', 21, 'a', 'para la paz', NULL),
(3, 'Prueba inicial', 1, '', 'Test de inserción', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_usuario`
--

CREATE TABLE `grupo_usuario` (
  `id_grupo_usuario` bigint(20) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_union` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `grupo_usuario`
--

INSERT INTO `grupo_usuario` (`id_grupo_usuario`, `id_grupo`, `id_usuario`, `fecha_union`) VALUES
(1, 3, 1, '2026-05-26 15:35:22'),
(2, 1, 1, '2026-05-26 15:48:56'),
(3, 2, 1, '2026-06-02 17:56:58');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_chatbot`
--

CREATE TABLE `historial_chatbot` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `mensaje_usuario` text NOT NULL,
  `respuesta_bot` text NOT NULL,
  `fecha_envio` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial_chatbot`
--

INSERT INTO `historial_chatbot` (`id`, `usuario_id`, `mensaje_usuario`, `respuesta_bot`, `fecha_envio`) VALUES
(1, 1, 'HOLA', '¡Hola! Bienvenido/a a **Tranquil Connect**. Es un gusto saludarte. 🌿\n\nEstoy aquí para acompañarte y brindarte el apoyo o la información que necesites para tu bienestar y tranquilidad. Mi objetivo es que este sea un espacio seguro y sereno para ti.\n\n¿En qué puedo ayudarte hoy? Tú marcas el ritmo.', '2026-03-20 22:12:19'),
(2, 1, 'me siento mal', 'Hola. Soy tu asistente de **Tranquil Connect** y quiero que sepas que aquí tienes un espacio seguro para expresarte. Lamento mucho que te estés sintiendo así en este momento.\n\nSentirse \"mal\" puede significar muchas cosas: a veces es una carga en el pecho, un cansancio profundo, ansiedad o simplemente una sensación de que algo no está bien. \n\nPara poder apoyarte mejor, ¿podrías decirme un poco más sobre cómo te sientes? Por ejemplo:\n*   ¿Es un malestar físico o emocional?\n*   ¿Sucedió algo específico o es un sentimiento que ha ido creciendo?\n\n**Mientras me respondes, intentemos un pequeño ejercicio de calma juntos:**\nDetente un momento, coloca una mano en tu abdomen y respira profundo por la nariz contando hasta cuatro... mantén el aire dos segundos... y suéltalo lentamente por la boca. Repítelo una vez más. A veces, darle un respiro al cuerpo ayuda a que la mente se aclare un poco.\n\nRecuerda que en **Tranquil Connect** nuestro objetivo es conectarte con el bienestar. Si sientes que este malestar es muy fuerte, puedo ayudarte a revisar la disponibilidad de nuestros especialistas para que hables con un profesional hoy mismo.\n\n*Nota: Si sientes que estás en una situación de crisis inmediata o en peligro, por favor comunícate con los servicios de emergencia de tu localidad o una línea de prevención al suicidio. Tu seguridad es lo más importante.*\n\n**Estoy aquí para escucharte. ¿Qué tienes en mente?**', '2026-03-20 22:20:02'),
(3, 3, 'hola', '¡Hola! Bienvenid@ a **Tranquil Connect**. Es un gusto saludarte.\n\nMi propósito es acompañarte y brindarte un espacio de calma, apoyo y bienestar. Estoy aquí para escucharte y ayudarte a encontrar la serenidad que necesitas hoy.\n\n¿En qué puedo apoyarte en este momento? Ya sea que busques información sobre nuestros servicios, recursos para tu tranquilidad o simplemente alguien con quien conversar, estoy a tu disposición.', '2026-03-20 22:54:14'),
(4, 3, 'm', '¡Hola! Bienvenido/a a **Tranquil Connect**. Soy tu asistente virtual, y estoy aquí para acompañarte en tu camino hacia el bienestar emocional y la serenidad.\n\n¿En qué puedo ayudarte hoy? Ya sea que busques información sobre nuestras sesiones, necesites ayuda para conectar con un profesional o simplemente quieras explorar herramientas para tu paz mental, estoy a tu disposición.\n\n¿Cómo te sientes hoy? Estamos aquí para escucharte.', '2026-03-20 23:58:32'),
(5, 3, 'como estas', '¡Hola! Soy tu asistente de **Tranquil Connect**. Me encuentro muy bien, con mucha calma y energía positiva para apoyarte hoy, gracias por preguntar. ✨\n\n¿Y tú cómo te sientes en este momento? Recuerda que estoy aquí para escucharte, ayudarte a encontrar herramientas para tu bienestar o brindarte cualquier información que necesites sobre nuestros servicios de conexión y apoyo emocional.\n\n¿Hay algo en lo que pueda ayudarte hoy?', '2026-03-20 23:58:43'),
(6, 3, 'me siento algo mal', 'Hola, soy tu asistente de **Tranquil Connect**. Lamento mucho escuchar que no te sientes bien en este momento, pero quiero que sepas que estoy aquí para acompañarte y escucharte.\n\nA veces, simplemente reconocer que algo no va bien es el primer paso más importante. Para poder apoyarte mejor, ¿te gustaría intentar identificar qué es lo que sientes? Por ejemplo:\n\n1.  **¿Es un malestar físico?** (Cansancio, tensión, dolor).\n2.  **¿Es un malestar emocional?** (Tristeza, ansiedad, estrés o quizás te sientes abrumado/a).\n3.  **¿O simplemente sientes una sensación de incomodidad que no logras definir?**\n\nCualquiera que sea tu respuesta, este es un **espacio seguro y libre de juicios**. Si te sientes con fuerzas para hablar de ello, te escucho. Si prefieres que hagamos un breve ejercicio de respiración o relajación para intentar calmar la mente, dímelo y lo haremos juntos.\n\n**¿Qué necesitas de mí en este momento?**', '2026-03-20 23:59:02'),
(7, 7, 'hola me siento triste', 'Hola, bienvenido/a a **Tranquil Connect**. Lamento mucho escuchar que te sientes así hoy, pero quiero agradecerte por tener la confianza de compartirlo conmigo.\n\nEs completamente válido sentirse triste; todos pasamos por momentos grises y no tienes que cargar con ello tú solo/a. En Tranquil Connect estamos aquí para ofrecerte un espacio seguro y sin juicios.\n\nSi te sientes cómodo/a, ¿te gustaría contarme un poco más sobre qué crees que te hace sentir así? A veces, poner los sentimientos en palabras puede ayudar a aliviar un poco el peso que llevas. \n\nRecuerda que estoy aquí para escucharte y acompañarte. ¿Hay algo en particular que necesites en este momento? Tal vez solo desahogarte o buscar alguna estrategia para sentirte un poco mejor.', '2026-03-21 03:28:48'),
(8, 7, 'tengo vitiligo y me siento muy enferma', 'Hola, soy tu asistente de **Tranquil Connect**. Primero que nada, quiero que sepas que lamento mucho que estés pasando por este momento tan difícil. No estás sola y estamos aquí para escucharte y apoyarte.\n\nCuando dices que te sientes \"muy enferma\", es importante que analicemos esto con calma para entender cómo podemos ayudarte mejor. El vitíligo es una condición que afecta la piel, pero sabemos que su impacto va mucho más allá de lo físico.\n\nAquí te comparto algunos puntos importantes para considerar:\n\n### 1. El malestar físico y el vitíligo\nAunque el vitíligo es principalmente una condición cutánea (pérdida de pigmento), es una enfermedad de origen **autoinmune**. A veces, las personas con vitíligo pueden experimentar otras condiciones autoinmunes (como problemas de tiroides o anemia) que podrían hacerte sentir físicamente fatigada o enferma.\n*   **Recomendación:** Si sientes debilidad, mareos o dolores físicos reales, es fundamental que consultes con un médico para descartar cualquier otra condición de salud que pueda estar afectándote.\n\n### 2. El peso emocional (Salud Mental)\nEn Tranquil Connect entendemos que el vitíligo puede generar una carga emocional muy pesada. El estrés, la ansiedad o la depresión pueden manifestarse como \"sentirse enferma\" (somatización). El agotamiento mental por enfrentar el diagnóstico o el estigma social puede sentirse como una enfermedad física real.\n*   **¿Sientes náuseas, falta de aire o un nudo en el estómago?** Esto podría ser una respuesta de tu cuerpo ante el estrés que te genera la condición.\n\n### 3. Pasos que podemos tomar juntos ahora mismo:\n1.  **Describe tus síntomas:** Si te sientes cómoda, ¿podrías decirme si tienes fiebre, dolor o si es más un sentimiento de tristeza y cansancio extremo?\n2.  **Busca atención profesional:** Si el malestar físico es intenso, por favor acude a un centro de salud o contacta a tu médico de cabecera de inmediato.\n3.  **Hablemos:** En Tranquil Connect contamos con especialistas y grupos de apoyo que entienden exactamente por lo que estás pasando. Hablar con alguien que comparta tu experiencia puede aliviar mucho esa sensación de malestar.\n\n**Tu bienestar es nuestra prioridad.** Si en este momento sientes que estás en una crisis emocional profunda o tienes pensamientos de hacerte daño, por favor busca ayuda de emergencia o llama a una línea de prevención de crisis en tu país.\n\n**Estoy aquí para seguir conversando cuando te sientas lista. ¿Hay algo específico que te duela o te preocupe más en este momento?**', '2026-03-21 03:40:06'),
(9, 7, 'xd', '¡Hola! Qué alegría recibirte con ese ánimo. 😄\n\nSoy tu asistente de **Tranquil Connect**. Mi misión es ayudarte a encontrar un espacio de calma, equilibrio y bienestar en tu día a día. 🌿✨\n\n¿En qué puedo apoyarte hoy? Ya sea que busques consejos para reducir el estrés, técnicas de meditación, o simplemente alguien que te escuche con serenidad, aquí estoy para ti.\n\n¡Dime cómo puedo hacer que tu momento sea más tranquilo!', '2026-03-21 03:44:05'),
(10, 1, 'me das consejos para despejar mi mente', '¡Hola! Qué gusto saludarte. Soy tu asistente de **Tranquil Connect**, y estoy aquí para acompañarte a encontrar ese espacio de calma que tanto necesitas.\n\nA veces, el ruido externo y las responsabilidades pueden saturar nuestra mente, pero recuerda que despejarla es un proceso suave, no una tarea más en tu lista de pendientes. Aquí tienes algunos consejos prácticos para liberar carga mental hoy mismo:\n\n### 1. La \"Descarga Mental\" (Brain Dump)\nToma una hoja de papel y escribe todo lo que tengas en la cabeza: preocupaciones, tareas pendientes, ideas sueltas o sentimientos. No busques orden ni perfección. Al pasarlo al papel, le dices a tu cerebro que ya no necesita \"cargar\" con esa información, lo que reduce la ansiedad de inmediato.\n\n### 2. Aplica la técnica 5-4-3-2-1\nPara salir de un ciclo de pensamientos repetitivos, vuelve al presente usando tus sentidos:\n*   Observa **5** cosas que puedas ver.\n*   Reconoce **4** cosas que puedas tocar.\n*   Escucha **3** sonidos diferentes.\n*   Identifica **2** olores.\n*   Nota **1** sabor (o una sensación en tu boca).\n\n### 3. Respiración Consciente (Caja)\nHaz una pausa y sigue este ritmo:\n*   Inhala profundamente por la nariz contando **4** segundos.\n*   Mantén el aire por **4** segundos.\n*   Exhala lentamente por la boca en **4** segundos.\n*   Quédate sin aire por **4** segundos.\nRepite esto 3 o 4 veces. Esto reinicia tu sistema nervioso.\n\n### 4. Desconexión Digital Estratégica\nDate el permiso de alejarte de las pantallas por al menos 20 minutos. El flujo constante de información (notificaciones, redes sociales) mantiene tu mente en estado de alerta. Deja el teléfono en otra habitación y permite que tu mente simplemente \"esté\".\n\n### 5. Movimiento Suave\nA veces, para despejar la mente, hay que mover el cuerpo. No tiene que ser un ejercicio intenso; un estiramiento lento, una caminata corta o simplemente sacudir tus brazos y hombros ayuda a liberar la tensión física acumulada por el estrés mental.\n\n### 6. La Regla de los dos minutos\nSi algo te está rondando la cabeza y puedes resolverlo en menos de dos minutos (como responder un correo corto o lavar un plato), hazlo ahora. Si toma más tiempo, agéndalo. Eliminar esos \"pequeños ruidos\" libera mucho espacio cognitivo.\n\n---\n\n**Recuerda:** En **Tranquil Connect** creemos que tu bienestar empieza con un pequeño respiro. No intentes hacerlo todo a la vez; elige uno de estos consejos y observa cómo te sientes.\n\n¿Te gustaría que profundizáramos en alguno de estos puntos o prefieres que hagamos un breve ejercicio de meditación guiada por aquí? Estoy para escucharte.', '2026-03-24 18:33:33'),
(11, 1, 'holi', '¡Hola! Me alegra verte hoy. ¿Cómo estás? ¿Qué te trae hoy a nuestra conversación? Estoy aquí para escucharte y ayudarte de cualquier manera que pueda. ¿Te apetece hablar sobre algo en particular o simplemente compartir un poco sobre tu día? Estoy aquí para ti.', '2026-05-25 20:39:07'),
(12, 1, 'me siento mal', 'Lo siento mucho. ¿Quieres hablar sobre lo que te está sucediendo? A veces compartir tus sentimientos y pensamientos puede ayudarte a sentirte mejor. Estoy aquí para escucharte y ofrecerte apoyo. ¿Quieres comenzar a hablar sobre lo que te está molestando?', '2026-05-25 20:39:17'),
(13, 1, 'dame consejos me siento solo', 'Entiendo que estás pasando por un momento difícil. Sentirse solo puede ser muy abrumador. Te quiero recordar que no estás solo en esto. Aquí te dejo algunos consejos que pueden ayudarte a sentirte un poco más conectado:\n\n1. **Busca apoyo en personas de confianza**: Habla con un amigo o familiar cercano sobre cómo te sientes. A menudo, solo necesitamos hablar sobre nuestras emociones para sentirnos mejor.\n2. **Conecta con tus pasiones**: Haz algo que te guste y te haga sentir vivo. Puede ser leer, escuchar música, practicar un deporte o cualquier otra actividad que te apasione.\n3. **Mantén una rutina**: Establece una rutina diaria que te permita sentir un sentido de control y seguridad. Esto puede incluir cosas como dormir a la misma hora cada noche, comer comidas saludables y hacer ejercicio regularmente.\n4. **Practica la auto-compasión**: Sé amable contigo mismo. Recuerda que es normal sentirse solo de vez en cuando y que no eres defectuoso por sentirlo.\n5. **Busca ayuda profesional**: Si el sentimiento de soledad es persistente y te está afectando significativamente, considera buscar ayuda de un profesional de la salud mental. Un terapeuta puede ayudarte a explorar las causas subyacentes y desarrollar estrategias para superarlas.\n6. **Conecta con la comunidad**: Únete a grupos de apoyo en línea o en persona, o participa en actividades comunitarias. Esto te puede ayudar a sentirte conectado con otros y a encontrar apoyo.\n7. **Practica la gratitud**: Cada día, toma un momento para reflexionar sobre las cosas que te gustan y que estás agradecido por. Esto puede ayudarte a mantener una perspectiva positiva y a sentirte más conectado con el mundo que te rodea.\n\nRecuerda, no estás solo. Hay personas que se preocupan por ti y que quieren ayudarte.', '2026-05-25 20:39:30'),
(14, 1, 'muchas gracias te lo agradezco', 'De nada, estoy aquí para ayudarte. ¿Hay algo específico con lo que necesitas hablar o necesitas una charla? Estoy aquí para escucharte y brindarte apoyo.', '2026-05-25 20:41:49'),
(15, 1, 'holis', '¡Hola! Me alegra verte. ¿En qué puedo ayudarte hoy? ¿Necesitas hablar sobre algo en particular o simplemente necesitas escuchar? Estoy aquí para ti.', '2026-05-26 19:39:54'),
(16, 1, 'como vas', 'Estoy bien, gracias por preguntar. Estoy aquí para escucharte y ayudarte en lo que necesites. ¿En qué puedo ayudarte hoy?', '2026-06-02 22:13:27'),
(17, 1, 'que puedo hacer me siento mal', 'Lo siento mucho que te sientas mal. ¿Te gustaría hablar sobre lo que está pasando? A veces compartir tus pensamientos y sentimientos puede ayudarte a sentirte un poco mejor. ¿Quieres hablar sobre algún tema en particular o simplemente necesitas un espacio para expresarte?\n\nSi lo prefieres, también puedo sugerirte algunas actividades que pueden ayudarte a relajarte y mejorar tu estado de ánimo, como:\n\n- Practicar técnicas de respiración profunda\n- Escuchar música suave\n- Tomar un baño relajante\n- Hacer ejercicio ligero\n- Leer un libro o mirar una película tranquila\n\n¿Te gustaría que te sugiera algo en particular o prefieres hablar sobre lo que te está afectando? Estoy aquí para escucharte sin juzgarte.', '2026-06-02 22:13:45'),
(18, 1, 'que tipo der musica', 'Disfruto de hablar sobre música. ¿Te gustaría hablar sobre un género en particular o tienes un gusto musical específico? ¿Te apasiona la música clásica, el rock, la música electrónica, el jazz, el pop o algo más?\n\nTambién puedo sugerirte algunos géneros musicales basados en tu estado de ánimo o preferencias. ¿Te sientes relajado y tranquilo, o estás buscando algo más energético y motivador?', '2026-06-02 22:13:56'),
(19, 1, 'wicked game de chris isaak sirve', '\"Wicked Game\" de Chris Isaak es una canción emotiva y romántica que puede evocar sensaciones intensas. Su letra habla sobre el riesgo de enamorarse y el dolor que puede resultar de una relación amorosa. La melodía y la voz de Chris Isaak crean un ambiente íntimo y sensual que puede hacer que te sientas inmerso en el estado de ánimo de la canción.\n\n¿Te gustaría hablar sobre qué te hace sentir esta canción o si hay algo específico que te llama la atención en ella? Estoy aquí para escucharte y brindarte apoyo emocional.', '2026-06-02 22:14:15'),
(20, 1, 'o pink floyd', 'Pink Floyd, una banda de rock legendaria conocida por sus temas profundos y poéticos. Sus canciones suelen explorar temas como la alienación, la sociedad, la conciencia y la espiritualidad.\n\n¿Cuál es tu canción favorita de Pink Floyd? ¿Te gusta más su época de psicodelia con \"The Dark Side of the Moon\" o su período de rock progresivo con \"The Wall\"?', '2026-06-02 22:14:34'),
(21, 1, 'breathe in the air', '\"De la vida\" me recuerdas. \"De la vida\" es la canción de la banda ABBA, ¿te gusta la música de ABBA?', '2026-06-02 22:14:43'),
(22, 1, 'sad but true', 'Entiendo que estás pasando por un momento difícil. La frase \"sad but true\" sugiere que hay algo que te hace sentir triste, pero también reconoces que es una realidad. ¿Quieres hablar sobre lo que está sucediendo? Estoy aquí para escucharte sin juzgarte.', '2026-06-03 21:46:13'),
(23, 1, 'es la cancion de metallica', 'Creo que estás pensando en \"Enter Sandman\". ¿Es esa la canción de Metallica que te viene a la mente? Es una de las más conocidas y emblemáticas de la banda, ¿verdad? ¿Quieres hablar sobre ella o hay algo más en lo que pueda ayudarte?', '2026-06-03 21:46:30'),
(24, 1, '[📷 IMAGEN ADJUNTA - Formato: JPEG, Tamaño: 3456x2160px]', 'Lo siento, pero no puedo acceder a la imagen adjunta. Sin embargo, puedo sugerirte algunas posibilidades si deseas compartirla y obtener mi opinión sobre ella:\n\n1. **Describir la imagen**: Puedes describirme la imagen que has adjuntado, y yo puedo intentar imaginar lo que ves y ofrecerte una respuesta basada en tu descripción.\n2. **Subir la imagen a un servidor**: Puedes subir la imagen a un servidor como Google Drive, Dropbox o Flickr, y luego compartir conmigo la URL o el enlace para que pueda verla.\n3. **Usar una plataforma de chat con soporte para imágenes**: Si estamos en una plataforma de chat que soporta imágenes, puedo intentar ver la imagen directamente.\n\nSi deseas compartir la imagen y obtener mi opinión, ¿cómo prefieres proceder?', '2026-06-03 21:48:59'),
(25, 1, 'q', 'Parece que solo has escrito una letra. ¿Hay algo en particular en lo que puedo ayudarte o de lo que quieras hablar? Estoy aquí para escucharte con atención y empatía. ¿Qué pasa?', '2026-06-03 22:02:08'),
(26, 1, 'bueno adios', 'Hasta luego. Que tengas un buen día y que la tranquilidad te acompañe. Si necesitas hablar o compartir algo, estoy aquí para escucharte cuando quieras regresar. ¡Cuídate!', '2026-06-24 00:48:49');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes_chat`
--

CREATE TABLE `mensajes_chat` (
  `id` bigint(20) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `contenido` text DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `fecha_envio` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensajes_chat`
--

INSERT INTO `mensajes_chat` (`id`, `id_grupo`, `id_usuario`, `contenido`, `imagen_url`, `fecha_envio`) VALUES
(1, 1, 1, 'q', '', '2026-03-19 21:25:24'),
(2, 1, 1, 'so', '', '2026-03-19 21:45:09'),
(3, 1, 1, 'a', '', '2026-03-19 21:51:43'),
(4, 1, 1, '', 'chat_fotos/wyatt-sicks-wallpaper-retina.jpg', '2026-03-19 21:53:31'),
(5, 1, 1, 'y', '', '2026-03-19 21:56:30'),
(6, 2, 1, 'holi', '', '2026-03-19 21:57:44'),
(7, 1, 1, 'a', '', '2026-03-19 21:59:57'),
(8, 1, 1, 'xd', '', '2026-03-20 16:49:02'),
(9, 1, 1, 'o', '', '2026-03-20 16:50:55'),
(10, 1, 1, '', 'chat_fotos/WhatsApp_Image_2026-02-17_at_7.13.24_PM.jpeg', '2026-03-20 16:51:03'),
(11, 1, 1, 'w', '', '2026-03-20 18:12:36'),
(12, 1, 1, 'que', '', '2026-03-20 18:22:28'),
(13, 1, 3, 'so', '', '2026-03-20 18:27:46'),
(14, 1, 1, 'rra', '', '2026-03-20 18:28:40'),
(15, 1, 7, 'jl', '', '2026-03-20 22:44:51'),
(16, 2, 1, 'jk', '', '2026-03-27 18:22:12'),
(17, 1, 1, '', 'chat_fotos/Captura_de_pantalla_2026-01-04_145943.png', '2026-04-09 20:07:43'),
(18, 1, 3, '', 'chat_fotos/Captura_de_pantalla_2026-01-04_165722.png', '2026-04-09 20:14:22'),
(19, 1, 3, '', 'chat_fotos/Captura_de_pantalla_2026-01-04_170034.png', '2026-04-09 20:16:14'),
(21, 2, 1, 'xd', '', '2026-05-20 16:20:59'),
(22, 3, 1, 'xd', '', '2026-05-25 14:23:01'),
(24, 1, 1, '', 'chat_fotos/wyatt-sicks-wallpaper-retina.jpg', '2026-05-25 15:20:54'),
(26, 1, 1, 'xd', 'chat_fotos/ssstik.io_gabrieleufrz_1778861935474.mp4', '2026-06-03 16:06:23'),
(27, 1, 1, 'jajaj', 'chat_fotos/grabacion-1780503984597.webm', '2026-06-03 16:26:38');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `codigo` int(11) NOT NULL,
  `telefono` bigint(20) NOT NULL,
  `metodo` varchar(255) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `fecha` datetime NOT NULL,
  `monto` int(11) NOT NULL,
  `moneda` varchar(255) NOT NULL,
  `codigo_agenda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pertenece`
--

CREATE TABLE `pertenece` (
  `id_usuario` int(11) NOT NULL,
  `codigo_grupo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `psicologo`
--

CREATE TABLE `psicologo` (
  `psicologo_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `especialidad` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicaciones`
--

CREATE TABLE `publicaciones` (
  `id` int(11) NOT NULL,
  `contenido` text NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `fecha_publicacion` datetime DEFAULT current_timestamp(),
  `usuario_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicaciones`
--

INSERT INTO `publicaciones` (`id`, `contenido`, `imagen`, `fecha_publicacion`, `usuario_id`) VALUES
(6, 'pelusa', 'uploads/Captura_de_pantalla_2026-03-26_093956.png', '2026-03-05 17:42:22', 3),
(7, 'sicks', 'uploads/wyatt-sicks-wallpaper-retina_oS0uxKF.jpg', '2026-03-06 17:32:38', 1),
(8, 'guts', 'uploads/ssstik.io_1775768839906.mp4', '2026-03-06 17:32:51', 1),
(10, '(l)mirl-Deftones', 'uploads/ssstik.io_gabrieleufrz_1778861935474.mp4', '2026-05-20 16:44:39', 1),
(17, '', 'e8b5361a-081d-4ad2-a6a3-d004bde716e2_ssstik.io_1778861863952.mp3', '2026-06-03 15:58:42', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reacciones`
--

CREATE TABLE `reacciones` (
  `id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `tipo_reaccion` varchar(20) NOT NULL,
  `fecha_reaccion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reacciones`
--

INSERT INTO `reacciones` (`id`, `publicacion_id`, `usuario_id`, `tipo_reaccion`, `fecha_reaccion`) VALUES
(8, 7, 1, 'love', '2026-05-25 16:01:21'),
(10, 8, 1, 'haha', '2026-06-02 18:31:57'),
(11, 6, 1, 'haha', '2026-06-03 15:54:03'),
(13, 10, 1, 'love', '2026-06-02 18:24:58'),
(15, 10, 13, 'care', '2026-06-02 19:10:41'),
(19, 17, 1, 'love', '2026-06-03 16:02:04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `genero` varchar(255) NOT NULL,
  `experiencia` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `especialidad` varchar(255) DEFAULT NULL,
  `tarjeta_profesional` varchar(255) DEFAULT NULL,
  `estado_validacion` varchar(255) NOT NULL DEFAULT 'PENDIENTE',
  `experiencia_resumen` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `correo`, `telefono`, `genero`, `experiencia`, `rol`, `contrasena`, `especialidad`, `tarjeta_profesional`, `estado_validacion`, `experiencia_resumen`) VALUES
(1, 'Julian', 'jmunoztorrijosxd@gmail.com', '3001858527', 'Masculino', NULL, 'ADMIN', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(2, 'esteban', 'esteban@gmail.com', '4375047504', 'masculino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(3, 'pelusa23xd', 'pelusaxd1@gmail.com', '8509860945', 'Femenino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(4, 'pato', 'pato@gmail.com', '94540964569', 'masculino', NULL, 'USUARIO', '12345678i', NULL, NULL, 'PENDIENTE', NULL),
(5, 'mana', 'mana@gmail.com', '4375047504', 'femenino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(6, 'Julian Andres Muñoz Torrijos', 'skipper@gmail.com', '4375047504', 'Masculino', NULL, 'PSICOLOGO\n', '12345678', NULL, NULL, 'APROBADO', NULL),
(7, 'tatiana torrijos', 'tatianatorrijos39@gmail.com', '3123189092', 'femenino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(9, 'marselagoso', 'brayansamaca51@gmail.com', '3197054345', 'masculino', NULL, 'USUARIO', 'edison0909', NULL, NULL, 'PENDIENTE', NULL),
(10, 'dd', 'julian@gmail.com', '3106587658', 'no_binario', NULL, 'USUARIO', 'abcdefgh', NULL, NULL, 'PENDIENTE', NULL),
(11, 'quaker', 'brayanquimbayg21@gmail.com', '9454096459', 'no_binario', NULL, 'USUARIO', '123456789', NULL, NULL, 'PENDIENTE', NULL),
(12, 'pelusa', 'jmunoztorrijosxd12@gmail.com', '945409645699', 'Masculino', 'dknmknmj ', 'PSICOLOGO', '12345678', 'Familiar', 'TP-847243-COL', 'APROBADO', NULL),
(13, 'skipper xdd', 'jmunoztorrijosxd67@gmail.com', '3203929002', 'otro', NULL, 'USUARIO', 'Pelusaxd07*', NULL, NULL, 'PENDIENTE', NULL),
(14, 'julian muñoz', 'camokerry@gmail.com', '94540964569', 'masculino', 'no se', 'PSICOLOGO', '12345678', 'Familiar', 'TP-847493-COL', 'APROBADO', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`codigo_agenda`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD KEY `fk_agenda_solicita` (`id_usuario_solicita`),
  ADD KEY `fk_agenda_elige` (`usuario_usuario_elige`);

--
-- Indices de la tabla `apoyo`
--
ALTER TABLE `apoyo`
  ADD PRIMARY KEY (`codigo_apoyo`),
  ADD KEY `fk_apoyo_agenda` (`codigo_agenda`);

--
-- Indices de la tabla `auth_group`
--
ALTER TABLE `auth_group`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indices de la tabla `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  ADD KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`);

--
-- Indices de la tabla `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`);

--
-- Indices de la tabla `auth_user`
--
ALTER TABLE `auth_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indices de la tabla `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  ADD KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`);

--
-- Indices de la tabla `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  ADD KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`);

--
-- Indices de la tabla `cita`
--
ALTER TABLE `cita`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_paciente` (`paciente_id`),
  ADD KEY `fk_psicologo` (`psicologo_id`);

--
-- Indices de la tabla `codigo_recuperacion`
--
ALTER TABLE `codigo_recuperacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_comentarios_foro` (`foro_codigo_foro`);

--
-- Indices de la tabla `debate`
--
ALTER TABLE `debate`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_debate_foro` (`foro_codigo_foro`);

--
-- Indices de la tabla `desempeño`
--
ALTER TABLE `desempeño`
  ADD PRIMARY KEY (`codigo_desempeño`),
  ADD KEY `fk_desempeño_usuario` (`id_usuario`),
  ADD KEY `fk_desempeno_psicologo` (`psicologo_id`);

--
-- Indices de la tabla `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  ADD KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`);

--
-- Indices de la tabla `django_content_type`
--
ALTER TABLE `django_content_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`);

--
-- Indices de la tabla `django_migrations`
--
ALTER TABLE `django_migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `django_session`
--
ALTER TABLE `django_session`
  ADD PRIMARY KEY (`session_key`),
  ADD KEY `django_session_expire_date_a5c62663` (`expire_date`);

--
-- Indices de la tabla `enlaces`
--
ALTER TABLE `enlaces`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_enlaces_foro` (`foro_codigo_foro`);

--
-- Indices de la tabla `estudios`
--
ALTER TABLE `estudios`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_estudios_usuario` (`usuario_id_usuario`);

--
-- Indices de la tabla `foro_social`
--
ALTER TABLE `foro_social`
  ADD PRIMARY KEY (`codigo_foro`),
  ADD KEY `fk_foro_grupo` (`grupo_codigo_grupo`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id_grupo`),
  ADD KEY `fk_grupo_usuario` (`id_usuario_creador`);

--
-- Indices de la tabla `grupo_usuario`
--
ALTER TABLE `grupo_usuario`
  ADD PRIMARY KEY (`id_grupo_usuario`),
  ADD UNIQUE KEY `id_grupo` (`id_grupo`,`id_usuario`),
  ADD KEY `fk_grupo_usuario_usuario` (`id_usuario`);

--
-- Indices de la tabla `historial_chatbot`
--
ALTER TABLE `historial_chatbot`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuario_chatbot` (`usuario_id`);

--
-- Indices de la tabla `mensajes_chat`
--
ALTER TABLE `mensajes_chat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_grupo_chat` (`id_grupo`),
  ADD KEY `fk_usuario_chat` (`id_usuario`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_pago_agenda` (`codigo_agenda`);

--
-- Indices de la tabla `pertenece`
--
ALTER TABLE `pertenece`
  ADD PRIMARY KEY (`id_usuario`,`codigo_grupo`),
  ADD KEY `fk_pertenece_grupo` (`codigo_grupo`);

--
-- Indices de la tabla `psicologo`
--
ALTER TABLE `psicologo`
  ADD PRIMARY KEY (`psicologo_id`);

--
-- Indices de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `reacciones`
--
ALTER TABLE `reacciones`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario_publicacion_unica` (`usuario_id`,`publicacion_id`),
  ADD KEY `fk_reaccion_publicacion` (`publicacion_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `auth_group`
--
ALTER TABLE `auth_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `auth_permission`
--
ALTER TABLE `auth_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cita`
--
ALTER TABLE `cita`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `codigo_recuperacion`
--
ALTER TABLE `codigo_recuperacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `desempeño`
--
ALTER TABLE `desempeño`
  MODIFY `codigo_desempeño` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `django_admin_log`
--
ALTER TABLE `django_admin_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `django_content_type`
--
ALTER TABLE `django_content_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `django_migrations`
--
ALTER TABLE `django_migrations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id_grupo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `grupo_usuario`
--
ALTER TABLE `grupo_usuario`
  MODIFY `id_grupo_usuario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `historial_chatbot`
--
ALTER TABLE `historial_chatbot`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `mensajes_chat`
--
ALTER TABLE `mensajes_chat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `psicologo`
--
ALTER TABLE `psicologo`
  MODIFY `psicologo_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `reacciones`
--
ALTER TABLE `reacciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `fk_agenda_elige` FOREIGN KEY (`usuario_usuario_elige`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `fk_agenda_solicita` FOREIGN KEY (`id_usuario_solicita`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `apoyo`
--
ALTER TABLE `apoyo`
  ADD CONSTRAINT `fk_apoyo_agenda` FOREIGN KEY (`codigo_agenda`) REFERENCES `agenda` (`codigo_agenda`);

--
-- Filtros para la tabla `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`);

--
-- Filtros para la tabla `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`);

--
-- Filtros para la tabla `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  ADD CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Filtros para la tabla `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Filtros para la tabla `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `fk_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `fk_psicologo` FOREIGN KEY (`psicologo_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `fk_comentarios_foro` FOREIGN KEY (`foro_codigo_foro`) REFERENCES `foro_social` (`codigo_foro`);

--
-- Filtros para la tabla `debate`
--
ALTER TABLE `debate`
  ADD CONSTRAINT `fk_debate_foro` FOREIGN KEY (`foro_codigo_foro`) REFERENCES `foro_social` (`codigo_foro`);

--
-- Filtros para la tabla `desempeño`
--
ALTER TABLE `desempeño`
  ADD CONSTRAINT `fk_desempeno_psicologo` FOREIGN KEY (`psicologo_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `fk_desempeño_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  ADD CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Filtros para la tabla `enlaces`
--
ALTER TABLE `enlaces`
  ADD CONSTRAINT `fk_enlaces_foro` FOREIGN KEY (`foro_codigo_foro`) REFERENCES `foro_social` (`codigo_foro`);

--
-- Filtros para la tabla `estudios`
--
ALTER TABLE `estudios`
  ADD CONSTRAINT `fk_estudios_usuario` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `foro_social`
--
ALTER TABLE `foro_social`
  ADD CONSTRAINT `fk_foro_grupo` FOREIGN KEY (`grupo_codigo_grupo`) REFERENCES `grupo` (`id_grupo`);

--
-- Filtros para la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `fk_grupo_usuario` FOREIGN KEY (`id_usuario_creador`) REFERENCES `usuario` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `grupo_usuario`
--
ALTER TABLE `grupo_usuario`
  ADD CONSTRAINT `fk_grupo_usuario_grupo` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_grupo_usuario_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `historial_chatbot`
--
ALTER TABLE `historial_chatbot`
  ADD CONSTRAINT `fk_usuario_chatbot` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `mensajes_chat`
--
ALTER TABLE `mensajes_chat`
  ADD CONSTRAINT `fk_grupo_chat` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_usuario_chat` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `fk_pago_agenda` FOREIGN KEY (`codigo_agenda`) REFERENCES `agenda` (`codigo_agenda`);

--
-- Filtros para la tabla `pertenece`
--
ALTER TABLE `pertenece`
  ADD CONSTRAINT `fk_pertenece_grupo` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`id_grupo`),
  ADD CONSTRAINT `fk_pertenece_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD CONSTRAINT `publicaciones_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `reacciones`
--
ALTER TABLE `reacciones`
  ADD CONSTRAINT `fk_reaccion_publicacion` FOREIGN KEY (`publicacion_id`) REFERENCES `publicaciones` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_reaccion_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

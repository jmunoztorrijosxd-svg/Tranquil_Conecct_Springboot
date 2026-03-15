-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-03-2026 a las 10:25:32
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
-- Estructura de tabla para la tabla `cita`
--

CREATE TABLE `cita` (
  `id` int(11) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `psicologo_id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `estado` varchar(20) DEFAULT 'PENDIENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`id`, `paciente_id`, `psicologo_id`, `fecha`, `estado`) VALUES
(1, 3, 6, '2026-11-25', 'PENDIENTE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `codigo_recuperacion`
--

CREATE TABLE `codigo_recuperacion` (
  `id` bigint(20) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `codigo` varchar(6) NOT NULL,
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
  `codigo_desempeño` int(11) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `calificacion` int(11) NOT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `condicion` varchar(600) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL,
  `codigo_apoyo` int(11) NOT NULL
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
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`id_grupo`, `nombre`, `num_miembros`, `motivo_salida`, `descripcion`) VALUES
(1, 'grupo de relajacion', 15, 'nolose', 'xd');

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
(1, 'awa', '', '2026-03-05 16:32:03', 1),
(2, 'xd\r\n', NULL, '2026-03-05 16:47:51', 1),
(3, 'hola a todos', NULL, '2026-03-05 16:53:24', 3),
(4, 'hola', NULL, '2026-03-05 17:23:55', 1),
(5, 'hola', '58dbd9f5-82c8-4df7-a495-0a5de5625ace_paz-e1548425305186.jpg', '2026-03-05 17:35:28', 1),
(6, 'pelusa', 'ab63b9f9-27d5-4f18-981f-3bbaeb3025f7_fastapi-logo.CrXoa3Er.png', '2026-03-05 17:42:22', 3),
(7, 'A', 'af17a532-8ce5-44bb-b0e9-ee3eaf0c8033_WhatsApp Image 2026-02-17 at 7.13.24 PM.jpeg', '2026-03-06 17:32:38', 1),
(8, 'xd', '68f2be30-8312-450f-b4ba-5886f6ec3cdc_ssstik.io_@rodrigox.5_1772818126540.mp4', '2026-03-06 17:32:51', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reacciones`
--

CREATE TABLE `reacciones` (
  `codigo` int(11) NOT NULL,
  `foro_codigo_foro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `telefono` bigint(20) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `experiencia` varchar(255) DEFAULT NULL,
  `rol` varchar(20) NOT NULL,
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
(1, 'Julian', 'jmunoztorrijosxd@gmail.com', 3001858527, '', 'null', 'ADMIN', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(2, 'esteban', 'esteban@gmail.com', 4375047504, 'masculino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(3, 'pelusa23xd', 'pelusaxd1@gmail.com', 8509860945, '', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(4, 'pato', 'pato@gmail.com', 94540964569, 'masculino', NULL, 'USUARIO', '12345678i', NULL, NULL, 'PENDIENTE', NULL),
(5, 'mana', 'mana@gmail.com', 4375047504, 'femenino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL),
(6, 'Julian Andres Muñoz Torrijos', 'skipperxd@gmail.com', 4375047504, 'masculino', 'se trabajar con niños muy bien', 'PSICOLOGO', '12345678', 'Infantil', 'TP-847293-COL', 'APROBADO', NULL),
(7, 'tatiana torrijos', 'tatianatorrijos39@gmail.com', 3123189092, 'femenino', NULL, 'USUARIO', '12345678', NULL, NULL, 'PENDIENTE', NULL);

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
  ADD KEY `fk_desempeño_apoyo` (`codigo_apoyo`);

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
  ADD PRIMARY KEY (`id_grupo`);

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
-- Indices de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `reacciones`
--
ALTER TABLE `reacciones`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_reacciones_foro` (`foro_codigo_foro`);

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
-- AUTO_INCREMENT de la tabla `cita`
--
ALTER TABLE `cita`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `codigo_recuperacion`
--
ALTER TABLE `codigo_recuperacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
  ADD CONSTRAINT `fk_desempeño_apoyo` FOREIGN KEY (`codigo_apoyo`) REFERENCES `apoyo` (`codigo_apoyo`),
  ADD CONSTRAINT `fk_desempeño_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

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
  ADD CONSTRAINT `fk_reacciones_foro` FOREIGN KEY (`foro_codigo_foro`) REFERENCES `foro_social` (`codigo_foro`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

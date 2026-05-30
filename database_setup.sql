-- Script de configuración inicial para MySQL Workbench
-- Ejecuta este script para crear la base de datos necesaria para la aplicación

CREATE DATABASE IF NOT EXISTS db_inventario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_inventario;

-- Nota: Las tablas 'categorias' y 'camisas' se crearán de forma automática
-- por Hibernate al arrancar la aplicación por primera vez (debido a ddl-auto=update).
-- No es necesario ejecutar sentencias CREATE TABLE manualmente.

SHOW TABLES;

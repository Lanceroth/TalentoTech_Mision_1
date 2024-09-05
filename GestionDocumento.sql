-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS BD_SISTEMA_GESTION_DOCUMENTAL;

-- Usar la base de datos
USE BD_SISTEMA_GESTION_DOCUMENTAL;

-- Tabla usuario
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Tabla autenticacion
CREATE TABLE autenticacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sesion_activa BOOLEAN DEFAULT FALSE,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla documento
CREATE TABLE documento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    año_publicacion INT,
    tipo_documento VARCHAR(50) NOT NULL,
    precio_compra DECIMAL(10, 2),
    precio_alquiler DECIMAL(10, 2),
    disponible BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

-- Tabla ensayo
CREATE TABLE ensayo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    tema VARCHAR(255),
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla libroelectronico
CREATE TABLE libroelectronico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    formato VARCHAR(50),
    tamaño_archivo DECIMAL(10, 2),
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla monografia
CREATE TABLE monografia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    tema VARCHAR(255),
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla investigacion
CREATE TABLE investigacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    campo_estudio VARCHAR(255),
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla iconografico
CREATE TABLE iconografico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    tipo_iconografia VARCHAR(100),
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla revista
CREATE TABLE revista (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento_id BIGINT,
    numero_edicion INT,
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla inventario
CREATE TABLE inventario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad_disponible INT NOT NULL,
    documento_id BIGINT,
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla venta
CREATE TABLE venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT,
    documento_id BIGINT,
    precio_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL,
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tabla alquiler
CREATE TABLE alquiler (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_alquiler DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATETIME,
    usuario_id BIGINT,
    documento_id BIGINT,
    precio_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL,
    FOREIGN KEY (documento_id) REFERENCES documento(id) ON DELETE SET NULL
) ENGINE=InnoDB;
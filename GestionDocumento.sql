CREATE TABLE Usuario (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    nombre VARCHAR(255),

    email VARCHAR(255) UNIQUE,

    contraseña VARCHAR(255),

    tipo_usuario VARCHAR(255)

);


CREATE TABLE Documento (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    titulo VARCHAR(255),

    autor VARCHAR(255),

    año_publicacion INT,

    tipo_documento VARCHAR(255),

    precio_compra DOUBLE,

    precio_alquiler DOUBLE,

    disponible BOOLEAN

);


CREATE TABLE Inventario (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    cantidad_disponible INT,

    documento_id BIGINT,

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Venta (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    fecha_venta DATETIME,

    usuario_id BIGINT,

    documento_id BIGINT,

    precio_total DOUBLE,

    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Alquiler (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    fecha_alquiler DATETIME,

    fecha_devolucion DATETIME,

    usuario_id BIGINT,

    documento_id BIGINT,

    precio_total DOUBLE,

    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Autenticacion (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    sesion_activa BOOLEAN,

    usuario_id BIGINT,

    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)

);


CREATE TABLE LibroElectronico (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    formato VARCHAR(255),

    tamaño_archivo DOUBLE,

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);

CREATE TABLE Revista (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    numero_edicion INT,

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Iconografico (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    tipo_iconografia VARCHAR(255),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Monografia (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    tema VARCHAR(255),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Ensayo (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    tema VARCHAR(255),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);


CREATE TABLE Investigacion (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    documento_id BIGINT,

    campo_estudio VARCHAR(255),

    FOREIGN KEY (documento_id) REFERENCES Documento(id)

);
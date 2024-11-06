-- Crear el esquema 2
CREATE SCHEMA IF NOT EXISTS "Esquema2";

-- Establecer el esquema de búsqueda
SET search_path = 'Esquema2';

-- Tabla Cliente (común en ambos esquemas)
CREATE TABLE Cliente (
    nro_cliente INT NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (nro_cliente)
);

-- Tabla Producto (común en ambos esquemas)
CREATE TABLE Producto (
    cod_producto INT NOT NULL,
    descripcion VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT pk_producto PRIMARY KEY (cod_producto)
);

-- Tabla Empleado (diferente en ambos esquemas)
CREATE TABLE Empleado (
    id_empleado INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_ingreso TIMESTAMP NOT NULL,
    CONSTRAINT pk_empleado PRIMARY KEY (id_empleado)
);

-- Tabla Departamento (común en ambos esquemas)
CREATE TABLE Departamento (
    id_departamento INT NOT NULL,
    nombre_departamento VARCHAR(100) NOT NULL,
    presupuesto DECIMAL(12 ,2) NOT NULL,
    CONSTRAINT pk_departamento PRIMARY KEY (id_departamento)
);

-- Tabla Factura (diferente en ambos esquemas)
CREATE TABLE Factura (
    nro_factura INT NOT NULL,
    nro_cliente INT NOT NULL,
    fecha_emision TIMESTAMP NOT NULL,
    monto_total DECIMAL(12,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT pk_factura PRIMARY KEY (nro_factura),
    CONSTRAINT fk_cliente_factura FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
);


CREATE TABLE Negocio (
    nro_negocio INT NOT NULL,
    nro_cliente INT NOT NULL,
    CONSTRAINT pk_negocio PRIMARY KEY (nro_negocio),
    CONSTRAINT fk_cliente_negocio FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
);



-- Índice en la tabla Producto
CREATE INDEX idx_producto_descripcion ON Producto (descripcion);

-- Función para calcular el monto total de una factura
CREATE OR REPLACE FUNCTION calcular_monto_total(p_nro_factura INT)
RETURNS DECIMAL AS $$
DECLARE
    monto_total DECIMAL;
BEGIN
    SELECT SUM(monto_total) INTO monto_total FROM Factura WHERE nro_factura = p_nro_factura;
    RETURN monto_total;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento para insertar un nuevo producto
CREATE OR REPLACE PROCEDURE insertar_producto(
    p_cod_producto INT,
    p_descripcion VARCHAR,
    p_precio DECIMAL,
    p_stock INT
) AS $$
BEGIN
    INSERT INTO Producto (cod_producto, descripcion, precio, stock)
    VALUES (p_cod_producto, p_descripcion, p_precio, p_stock);
END;
$$ LANGUAGE plpgsql;

-- Trigger para convertir el estado de la factura a mayúsculas
CREATE OR REPLACE FUNCTION trg_uppercase_factura()
RETURNS TRIGGER AS $$
BEGIN
    NEW.estado := UPPER(NEW.estado);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_factura_uppercase
BEFORE INSERT ON Factura
FOR EACH ROW
EXECUTE FUNCTION trg_uppercase_factura();


-- Crear el procedimiento actualizar_cliente en Esquema2
CREATE OR REPLACE PROCEDURE actualizar_cliente(
    INT,
    VARCHAR,
    VARCHAR,
	VARCHAR
) AS $$
BEGIN
    UPDATE Cliente
    SET apellido = $2,
        nombre = $3
    WHERE nro_cliente = $1;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION func_actualizar_cliente(
    INT,
    VARCHAR,
    VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Cliente
    SET apellido = $2,
        nombre = $3
    WHERE nro_cliente = $1;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION actualizar_direccion_telefono(
    INT,
    VARCHAR,
    VARCHAR
) RETURNS VOID AS $$
BEGIN
    UPDATE Cliente
    SET direccion = $2,
        telefono = $3
    WHERE nro_cliente = $1;
END;
$$ LANGUAGE plpgsql;

-- Crear el esquema 1
CREATE SCHEMA IF NOT EXISTS "Esquema1";

-- Establecer el esquema de búsqueda
SET search_path = 'Esquema1';


-- Tabla Cliente (común en ambos esquemas)
CREATE TABLE Cliente (
    nro_cliente INT NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (nro_cliente)
);

CREATE TABLE Propiedad (
    nro_cliente INT NOT NULL,
    nro_propiedad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    CONSTRAINT pk_propiedad primary key (nro_propiedad),
    CONSTRAINT fk_cliente FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
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
    id_empleado SERIAL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    CONSTRAINT pk_empleado PRIMARY KEY (id_empleado)
);

-- Tabla Departamento (común en ambos esquemas)
CREATE TABLE Departamento (
    id_departamento INT NOT NULL,
    nombre_departamento VARCHAR(50) NOT NULL,
    presupuesto DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_departamento PRIMARY KEY (id_departamento)
);

-- Tabla Factura (diferente en ambos esquemas)
CREATE TABLE Factura (
    nro_factura INT NOT NULL,
    nro_cliente INT NOT NULL,
    fecha_emision DATE NOT NULL,
    monto_total DECIMAL(12,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT pk_factura PRIMARY KEY (nro_factura)
);

CREATE TABLE Negocio (
    nro_negocio INT NOT NULL,
    nro_factura INT NOT NULL,
    CONSTRAINT pk_negocio PRIMARY KEY (nro_negocio),
    CONSTRAINT fk_nro_factura FOREIGN KEY (nro_factura) REFERENCES Factura(nro_factura)
);

-- Índice en la tabla Cliente
CREATE INDEX idx_cliente_nombre ON Cliente (nombre);

-- Función para calcular el salario anual de un empleado
CREATE OR REPLACE FUNCTION calcular_salario_anual(p_id_empleado INT)
RETURNS DECIMAL AS $$
DECLARE
    salario_anual DECIMAL;
BEGIN
    SELECT salario * 12 INTO salario_anual FROM Empleado WHERE id_empleado = p_id_empleado;
    RETURN salario_anual;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento para insertar un nuevo cliente
CREATE OR REPLACE PROCEDURE insertar_cliente(
    p_nro_cliente INT,
    p_apellido VARCHAR,
    p_nombre VARCHAR,
    p_direccion VARCHAR,
    p_telefono VARCHAR
) AS $$
BEGIN
    INSERT INTO Cliente (nro_cliente, apellido, nombre, direccion, telefono)
    VALUES (p_nro_cliente, p_apellido, p_nombre, p_direccion, p_telefono);
END;
$$ LANGUAGE plpgsql;

-- Trigger para convertir el nombre del cliente a mayúsculas
CREATE OR REPLACE FUNCTION trg_uppercase_cliente()
RETURNS TRIGGER AS $$
BEGIN
    NEW.nombre := UPPER(NEW.nombre);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_cliente_uppercase
BEFORE INSERT ON Cliente
FOR EACH ROW
EXECUTE FUNCTION trg_uppercase_cliente();



-- Crear el procedimiento actualizar_cliente en Esquema1
CREATE OR REPLACE PROCEDURE actualizar_cliente(
    p_nro_cliente INT,
    p_direccion VARCHAR,
    p_telefono VARCHAR
) AS $$
BEGIN
    UPDATE Cliente
    SET direccion = p_direccion,
        telefono = p_telefono
    WHERE nro_cliente = p_nro_cliente;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION actualizar_direccion_telefono(
    p_nro_cliente INT,
    p_direccion VARCHAR,
    p_telefono VARCHAR,
    p_nuevoparametro INT
) RETURNS VOID AS $$
BEGIN
    UPDATE Cliente
    SET direccion = p_direccion,
        telefono = p_telefono
    WHERE nro_cliente = p_nro_cliente;
END;
$$ LANGUAGE plpgsql;


-- tablas de esquema 1

set search_path = 'Esquema1';

CREATE OR REPLACE FUNCTION uppercase_cliente()
RETURNS TRIGGER AS $$
BEGIN
    NEW.cliente := UPPER(NEW.cliente);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_uppercase_cliente BEFORE INSERT ON cliente
FOR EACH ROW
EXECUTE PROCEDURE uppercase_cliente();

CREATE OR REPLACE Procedure exploushon(in par1 integer ,out par2 VARCHAR(10)) AS $$
BEGIN 
    IF par1 = 1 THEN
        par2 := 'hola';
    ELSE
        par2 := 'no es 1';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION saludar(p_numero integer)
RETURNS text AS $$
BEGIN IF p_numero = 1 THEN
        RETURN 'hola';
    ELSE
        RETURN 'no es 1';
    END IF;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE Cliente (
    nro_cliente INT NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    CONSTRAINT pk_client primary key (nro_cliente)
);

CREATE TABLE Propiedad (
    nro_propiedad INT NOT NULL,
    nro_cliente INT NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_propiedad primary key (nro_propiedad),
    CONSTRAINT fk_cliente FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
);

CREATE TABLE Producto (
    cod_producto INT NOT NULL,
    descripcion VARCHAR(50) NOT NULL,
    precio INT,
    stock_minimo INT,
    stock_maximo INT,
    cantidad INT,
    CONSTRAINT min_st check (stock_minimo < stock_maximo),
    CONSTRAINT pr check (precio > 0),
    CONSTRAINT pk_produc primary key (cod_producto)
);



--  tablas de esquema 2

set search_path = 'Esquema2';

CREATE TABLE Cliente (
    nro_cliente INT NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    CONSTRAINT pk_client primary key (nro_cliente)
);

CREATE TABLE Propiedad (
    nro_cliente INT NOT NULL,
    nro_propiedad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    CONSTRAINT pk_propiedad primary key (nro_propiedad),
    CONSTRAINT fk_cliente FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
);

CREATE TABLE categoria (
    nro_categoria int NOT NULL,
    tasa int,
    constraint pk_categoria PRIMARY KEY (nro_categoria),
    constraint ck_tasa check (tasa > 0)
);

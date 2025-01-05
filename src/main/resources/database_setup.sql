-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS chaucheritaweb;
USE chaucheritaweb;

-- Tabla Usuario
CREATE TABLE Usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
) ENGINE=InnoDB;

-- Tabla Categoria (simplificada)
CREATE TABLE Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla Cuenta (simplificada)
CREATE TABLE Cuenta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    balance DECIMAL(10,2) DEFAULT 0,
    ultimo_movimiento DATETIME,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
) ENGINE=InnoDB;

-- Tabla Movimiento (simplificada)
CREATE TABLE Movimiento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cuenta_id INT,
    categoria_id INT,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),  -- Campo agregado
    valor DECIMAL(10,2),
    tipo VARCHAR(50),
    fecha DATETIME,
    cuenta_destino_id INT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES Cuenta(id),
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES Cuenta(id)
) ENGINE=InnoDB;

-- Insertar categorías base (simplificadas)
INSERT INTO Categoria (nombre) VALUES
    ('Salario'),
    ('Alimentación'),
    ('Transporte'),
    ('Entretenimiento'),
    ('Servicios');

-- Usuario demo (ID fijo = 1)
INSERT INTO Usuario (id, nombre, email, password) VALUES
    (1, 'Usuario Demo', 'demo@example.com', 'demo123');

-- Cuentas demo (IDs fijos = 1 y 2)
INSERT INTO Cuenta (id, usuario_id, nombre, descripcion, balance) VALUES
    (1, 1, 'Cuenta Principal', 'Cuenta para gastos diarios', 1000.00),
    (2, 1, 'Cuenta Ahorros', 'Cuenta para ahorros a largo plazo', 500.00);

-- Movimientos demo
INSERT INTO Movimiento (cuenta_id, categoria_id, nombre, descripcion, valor, tipo, fecha, cuenta_destino_id) VALUES
    -- Movimientos Cuenta Principal
    (1, 1, 'Salario Enero', 'Pago mensual de salario', 1000.00, 'INGRESO', '2024-01-01 09:00:00', NULL),
    (1, 2, 'Compras supermercado', 'Compras semanales de víveres', 150.00, 'EGRESO', '2024-01-02 15:30:00', NULL),
    (1, 3, 'Taxi', 'Viaje al centro comercial', 25.00, 'EGRESO', '2024-01-03 18:45:00', NULL),
    (1, 4, 'Cine', 'Entrada película del fin de semana', 30.00, 'EGRESO', '2024-01-04 20:00:00', NULL),
    (1, 5, 'Luz', 'Pago mensual de electricidad', 45.00, 'EGRESO', '2024-01-05 14:20:00', NULL),
    (1, 1, 'Transferencia a Ahorros', 'Transferencia mensual para ahorros', 200.00, 'TRANSFERENCIA', '2024-01-06 10:00:00', 2),
    
    -- Movimientos Cuenta Ahorros
    (2, 1, 'Depósito inicial', 'Apertura de cuenta de ahorros', 500.00, 'INGRESO', '2024-01-01 10:00:00', NULL),
    (2, 1, 'Transferencia desde Principal', 'Transferencia recibida de cuenta principal', 200.00, 'TRANSFERENCIA', '2024-01-06 10:00:00', 1);

-- Establecer valores iniciales para AUTO_INCREMENT
ALTER TABLE Usuario AUTO_INCREMENT = 100;
ALTER TABLE Cuenta AUTO_INCREMENT = 100;
ALTER TABLE Movimiento AUTO_INCREMENT = 100;
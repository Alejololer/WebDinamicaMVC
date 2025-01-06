-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS chaucheritaweb;
USE chaucheritaweb;

-- Tabla Categoria 
CREATE TABLE Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla Cuenta 
CREATE TABLE Cuenta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    balance DECIMAL(10,2) DEFAULT 0,
    ultimo_movimiento DATETIME
) ENGINE=InnoDB;

-- Tabla Movimiento 
CREATE TABLE Movimiento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cuenta_id INT,
    categoria_id INT,
    concepto VARCHAR(100),
    valor DECIMAL(10,2),
    tipo VARCHAR(50),
    fecha DATETIME,
    cuenta_destino_id INT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES Cuenta(id),
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES Cuenta(id)
) ENGINE=InnoDB;

-- Insertar categorías base 
INSERT INTO Categoria (nombre) VALUES
    ('Salario'),
    ('Alimentación'),
    ('Transporte'),
    ('Entretenimiento'),
    ('Servicios');

-- Cuentas demo (IDs fijos = 1 y 2)
INSERT INTO Cuenta (id, nombre, descripcion, balance) VALUES
    (1, 'Cuenta Principal', 'Cuenta para gastos diarios', 1000.00),
    (2, 'Cuenta Ahorros', 'Cuenta para ahorros a largo plazo', 500.00);

-- Movimientos demo
INSERT INTO Movimiento (cuenta_id, categoria_id, concepto, valor, tipo, fecha, cuenta_destino_id) VALUES
    -- Movimientos Cuenta Principal
    (1, 1, 'Pago mensual de salario', 1000.00, 'INGRESO', '2024-01-01 09:00:00', NULL),
    (1, 2, 'Compras semanales de víveres', 150.00, 'EGRESO', '2024-01-02 15:30:00', NULL),
    (1, 3, 'Viaje al centro comercial', 25.00, 'EGRESO', '2024-01-03 18:45:00', NULL),
    (1, 4, 'Entrada película del fin de semana', 30.00, 'EGRESO', '2024-01-04 20:00:00', NULL),
    (1, 5, 'Pago mensual de electricidad', 45.00, 'EGRESO', '2024-01-05 14:20:00', NULL),
    (1, 1, 'Transferencia mensual para ahorros', 200.00, 'TRANSFERENCIA', '2024-01-06 10:00:00', 2),
    
    -- Movimientos Cuenta Ahorros
    (2, 1, 'Apertura de cuenta de ahorros', 500.00, 'INGRESO', '2024-01-01 10:00:00', NULL),
    (2, 1, 'Transferencia recibida de cuenta principal', 200.00, 'TRANSFERENCIA', '2024-01-06 10:00:00', 1);

-- Establecer valores iniciales para AUTO_INCREMENT
ALTER TABLE Cuenta AUTO_INCREMENT = 100;
ALTER TABLE Movimiento AUTO_INCREMENT = 100;
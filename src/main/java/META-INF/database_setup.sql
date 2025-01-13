DROP DATABASE IF EXISTS chaucheritaweb;
CREATE DATABASE IF NOT EXISTS chaucheritaweb;
USE chaucheritaweb;

-- Tabla Categoria 
CREATE TABLE IF NOT EXISTS Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla Cuenta 
CREATE TABLE IF NOT EXISTS Cuenta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    balance DECIMAL(10,2) DEFAULT 0,
    ultimo_movimiento DATETIME
) ENGINE=InnoDB;

-- Tabla Movimiento 
CREATE TABLE IF NOT EXISTS Movimiento (
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

-- Initial data
INSERT INTO Categoria (nombre) VALUES
    ('Salario'),
    ('Alimentación'),
    ('Transporte'),
    ('Entretenimiento'),
    ('Servicios');

INSERT INTO Cuenta (id, nombre, descripcion, balance) VALUES
    (1, 'Cuenta Principal', 'Cuenta para gastos diarios', 1000.00),
    (2, 'Cuenta Ahorros', 'Cuenta para ahorros a largo plazo', 500.00);

-- Sample Movements
INSERT INTO Movimiento (cuenta_id, categoria_id, concepto, valor, tipo, fecha, cuenta_destino_id) VALUES
    -- Ingresos
    (1, 1, 'Salario Enero', 1200.00, 'INGRESO', '2024-01-15 09:00:00', NULL),
    (1, 1, 'Salario Febrero', 1200.00, 'INGRESO', '2024-02-15 09:00:00', NULL),
    (1, 1, 'Bono Anual', 500.00, 'INGRESO', '2024-02-28 10:00:00', NULL),

    -- Gastos varios
    (1, 2, 'Supermercado', 150.00, 'EGRESO', '2024-02-01 15:30:00', NULL),
    (1, 2, 'Restaurante', 45.00, 'EGRESO', '2024-02-05 13:20:00', NULL),
    (1, 3, 'Gasolina', 40.00, 'EGRESO', '2024-02-06 18:45:00', NULL),
    (1, 4, 'Cine', 25.00, 'EGRESO', '2024-02-10 20:00:00', NULL),
    (1, 5, 'Luz', 80.00, 'EGRESO', '2024-02-12 09:30:00', NULL),
    (1, 5, 'Agua', 35.00, 'EGRESO', '2024-02-12 09:35:00', NULL),
    (1, 5, 'Internet', 45.00, 'EGRESO', '2024-02-13 14:20:00', NULL),

    -- Transferencias
    (1, 1, 'Ahorro mensual', 200.00, 'TRANSFERENCIA_SALIENTE', '2024-02-15 10:00:00', 2),
    (2, 1, 'Ahorro mensual', 200.00, 'TRANSFERENCIA_ENTRANTE', '2024-02-15 10:00:00', 1),
    
    -- Más gastos recientes
    (1, 2, 'Mercado semanal', 85.00, 'EGRESO', '2024-02-20 11:15:00', NULL),
    (1, 3, 'Taxi', 12.00, 'EGRESO', '2024-02-21 19:30:00', NULL),
    (1, 4, 'Netflix', 15.99, 'EGRESO', '2024-02-22 00:00:00', NULL),
    (1, 2, 'Farmacia', 32.50, 'EGRESO', '2024-02-23 16:45:00', NULL),
    
    -- Marzo
    (1, 1, 'Salario Marzo', 1200.00, 'INGRESO', '2024-03-15 09:00:00', NULL),
    (1, 2, 'Supermercado', 142.30, 'EGRESO', '2024-03-02 16:20:00', NULL),
    (1, 3, 'Gasolina', 42.00, 'EGRESO', '2024-03-05 08:15:00', NULL);

-- Reset auto-increment
ALTER TABLE Cuenta AUTO_INCREMENT = 100;
ALTER TABLE Movimiento AUTO_INCREMENT = 100;
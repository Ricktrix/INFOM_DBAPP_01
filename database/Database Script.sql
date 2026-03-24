SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';


-- Schema transport_db

DROP SCHEMA IF EXISTS `transport_db` ;
CREATE SCHEMA IF NOT EXISTS `transport_db` DEFAULT CHARACTER SET utf8 ;
USE `transport_db` ;

-- 1. Table: operator
CREATE TABLE IF NOT EXISTS `transport_db`.`operator` (
  `operator_ID` INT NOT NULL,
  `operatorName` VARCHAR(100) NOT NULL,
  `operatorType` VARCHAR(50) NULL,
  `contactNumber` VARCHAR(20) NULL,
  `email` VARCHAR(100) NULL,
  `activeStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`operator_ID`))
ENGINE = InnoDB;

-- 2. Table: vehicle (Using plateNumber as PK)
CREATE TABLE IF NOT EXISTS `transport_db`.`vehicle` (
  `plateNumber` VARCHAR(20) NOT NULL,
  `operator_ID` INT NOT NULL,
  `vehicleType` VARCHAR(50) NULL,
  `capacity` INT NULL,
  `vehicleStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`plateNumber`),
  CONSTRAINT `fk_vehicle_operator`
    FOREIGN KEY (`operator_ID`)
    REFERENCES `transport_db`.`operator` (`operator_ID`))
ENGINE = InnoDB;

-- 3. Table: passenger
CREATE TABLE IF NOT EXISTS `transport_db`.`passenger` (
  `passenger_ID` INT NOT NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `firstName` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NULL,
  `contactNumber` VARCHAR(20) NULL,
  `registrationDate` DATE NULL,
  PRIMARY KEY (`passenger_ID`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;

-- 4. Table: route
CREATE TABLE IF NOT EXISTS `transport_db`.`route` (
  `route_ID` INT NOT NULL,
  `origin` VARCHAR(100) NULL,
  `destination` VARCHAR(100) NULL,
  `distanceKm` DECIMAL(6,2) NULL,
  `baseFare` DECIMAL(10,2) NULL,
  `routeStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`route_ID`))
ENGINE = InnoDB;

-- 5. Table: schedule
CREATE TABLE IF NOT EXISTS `transport_db`.`schedule` (
  `schedule_ID` INT NOT NULL,
  `route_ID` INT NOT NULL,
  `plateNumber` VARCHAR(20) NOT NULL,
  `departureDate` DATE NULL,
  `departureTime` TIME NULL,
  `arrivalTime` TIME NULL,
  `driverName` VARCHAR(100) NULL,
  `availableSeats` INT NULL,
  PRIMARY KEY (`schedule_ID`),
  CONSTRAINT `fk_schedule_vehicle`
    FOREIGN KEY (`plateNumber`)
    REFERENCES `transport_db`.`vehicle` (`plateNumber`),
  CONSTRAINT `fk_schedule_route`
    FOREIGN KEY (`route_ID`)
    REFERENCES `transport_db`.`route` (`route_ID`))
ENGINE = InnoDB;

-- 6. Table: reservation
CREATE TABLE IF NOT EXISTS `transport_db`.`reservation` (
  `reservation_ID` INT NOT NULL,
  `passenger_ID` INT NOT NULL,
  `schedule_ID` INT NOT NULL,
  `bookingDate` DATE NULL,
  `seatNumber` VARCHAR(10) NULL,
  `reservationStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`reservation_ID`),
  CONSTRAINT `fk_reservation_passenger`
    FOREIGN KEY (`passenger_ID`)
    REFERENCES `transport_db`.`passenger` (`passenger_ID`),
  CONSTRAINT `fk_reservation_schedule`
    FOREIGN KEY (`schedule_ID`)
    REFERENCES `transport_db`.`schedule` (`schedule_ID`))
ENGINE = InnoDB;

-- 7. Table: payment (1:1 with Reservation)
CREATE TABLE IF NOT EXISTS `transport_db`.`payment` (
  `reservation_ID` INT NOT NULL,
  `paymentDate` DATE NULL,
  `paymentMethod` VARCHAR(30) NULL,
  `amountPaid` DECIMAL(10,2) NULL,
  `paymentStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`reservation_ID`),
  CONSTRAINT `fk_payment_reservation`
    FOREIGN KEY (`reservation_ID`)
    REFERENCES `transport_db`.`reservation` (`reservation_ID`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



-- Sample Data 
USE `transport_db`;
-- OPERATORS 
INSERT INTO `operator`
    (`operator_ID`, `operatorName`, `operatorType`, `contactNumber`, `email`, `activeStatus`)
VALUES
(1,  'Genesis Transport Service', 'Bus', '09171234001', 'genesis@transport.ph',  'Active'),
(2,  'Victory Liner Inc.',        'Bus', '09171234002', 'victory@liner.ph',      'Active'),
(3,  'Partas Transport Co.',      'Bus', '09171234003', 'partas@transport.ph',   'Active'),
(4,  'Solid North Transit',       'Bus', '09171234004', 'solidnorth@transit.ph', 'Active'),
(5,  'Five Star Bus Co.',         'Bus', '09171234005', 'fivestar@bus.ph',       'Active'),
(6,  'Raymond Transport',         'Van', '09171234006', 'raymond@transport.ph',  'Active'),
(7,  'Jam Liner Inc.',            'Bus', '09171234007', 'jam@liner.ph',          'Active'),
(8,  'Dagupan Bus Co.',           'Bus', '09171234008', 'dagupan@bus.ph',        'Active'),
(9,  'PhilTranco Service',        'Bus', '09171234009', 'philtranco@service.ph', 'Inactive'),
(10, 'Batangas Star Transport',   'Van', '09171234010', 'batangas@star.ph',      'Active');



-- VEHICLES
INSERT INTO `vehicle`
    (`plateNumber`, `operator_ID`, `vehicleType`, `capacity`, `vehicleStatus`)
VALUES
('ABC-1234', 1,  'Bus',     55, 'Available'),
('DEF-5678', 1,  'Bus',     55, 'In Service'),
('GHI-9012', 2,  'Bus',     60, 'Available'),
('JKL-3456', 3,  'Bus',     50, 'Available'),
('MNO-7890', 4,  'Minibus', 35, 'Available'),
('PQR-1122', 5,  'Bus',     55, 'In Service'),
('STU-3344', 6,  'Van',     15, 'Available'),
('VWX-5566', 7,  'Bus',     60, 'Maintenance'),
('YZA-7788', 8,  'Bus',     50, 'Available'),
('BCD-9900', 10, 'Van',     14, 'Available');



-- PASSENGER
INSERT INTO `passenger`
    (`passenger_ID`, `lastName`, `firstName`, `email`, `contactNumber`, `registrationDate`)
VALUES
(1, 'Valenzuela', 'Andrei', 'andrei.valenzuela@gmail.com', '09171234502', '2024-01-15'),
(2, 'Delos Santos', 'Julliana', 'juls_ds@email.com', '09189934809', '2024-01-20'),
(3, 'Cruz', 'Clarence', 'c.cruz21@yahoo.com', '09171234503', '2024-02-01'),
(4, 'Sarmiento', 'Kenneth', 'ken.sarmiento@gmail.com', '09171234504', '2024-02-14'),
(5, 'Valencia', 'Enzo', 'e.valencia@yahoo.com', '09171234505', '2024-03-05'),
(6, 'Guevarra', 'Ramon', 'ramon2.guevarra@gmail.com', '09171234506', '2024-03-22'),
(7, 'Salvador', 'Gabriel', 'g.salvador@yahoo.com', '09171234507', '2024-04-02'),
(8, 'Fernandez', 'Hannah', 'hannah.fernandez@gmail.com', '09171234508', '2024-04-18'),
(9, 'Villafuerte', 'Ivan', 'i.villafuerte@gmail.com', '09171234509', '2024-05-07'),
(10, 'Bautista', 'Miguel', 'mbautista@gmail.com', '09171234510', '2024-05-22');   



-- ROUTES
INSERT INTO `route`
    (`route_ID`, `origin`, `destination`, `distanceKm`, `baseFare`, `routeStatus`)
VALUES
(1,  'Manila', 'Batangas',     110.00, 180.00, 'Active'),
(2,  'Manila', 'Laoag',        488.00, 750.00, 'Active'),
(3,  'Manila', 'San Fernando', 133.00, 220.00, 'Active'),
(4,  'Manila', 'Dagupan',      230.00, 350.00, 'Active'),
(5,  'Manila', 'Baguio',       250.00, 450.00, 'Active'),
(6,  'Manila', 'Naga',         377.00, 550.00, 'Active'),
(7,  'Manila', 'Vigan',        408.00, 650.00, 'Active'),
(8,  'Manila', 'Tuguegarao',   486.00, 700.00, 'Active'),
(9,  'Manila', 'Legaspi',      546.00, 700.00, 'Active'),
(10, 'Baguio', 'Manila',       250.00, 450.00, 'Active');



-- SCHEDULES (availableSeats reflects confirmed bookings already deducted)
INSERT INTO `schedule` 
    (`schedule_ID`, `route_ID`, `plateNumber`, `departureDate`, `departureTime`, `arrivalTime`, `driverName`, `availableSeats`)
VALUES
-- Nov 2024 schedules
(1,  1,  'NCR-1234', '2024-11-01', '06:00:00', '12:00:00', 'Roberto Lim',      53), -- Route 1 (55 cap) - 2 confirmed
(2,  5,  'NFX-5678', '2024-11-01', '08:00:00', '14:00:00', 'Eduardo Tan',      59), -- Route 5 (60 cap) - 1 confirmed
(3,  2,  'NBC-9012', '2024-11-02', '07:00:00', '15:30:00', 'Fernando Cruz',    54), -- Route 2 (55 cap) - 1 confirmed
(4,  3,  'TNW-3456', '2024-11-02', '09:00:00', '18:00:00', 'Alfredo Santos',   49), -- Route 3 (50 cap) - 1 confirmed
(5,  4,  'NCR-7890', '2024-11-03', '10:00:00', '12:30:00', 'Danilo Reyes',     34), -- Route 4 (35 cap) - 1 confirmed
(6,  1,  'NFX-1122', '2024-11-05', '06:00:00', '12:00:00', 'Roberto Lim',      55), -- Route 1 (55 cap) - 1 pending
(7,  8,  'NBC-3344', '2024-11-05', '07:30:00', '12:00:00', 'Mariano Garcia',   49), -- Route 8 (50 cap) - 1 confirmed
(8,  6,  'TNW-5566', '2024-11-06', '08:00:00', '16:30:00', 'Rodrigo Bautista', 54), -- Route 6 (55 cap) - 1 confirmed
(9,  4,  'NCR-7788', '2024-11-07', '06:30:00', '15:00:00', 'Eduardo Tan',      59), -- Route 4 (60 cap) - 1 confirmed
(10, 9,  'NFX-9900', '2024-11-08', '09:00:00', '11:30:00', 'Nestor Aquino',    13), -- Route 9 (14 cap) - 1 confirmed
-- Dec 2024 schedules
(11, 5,  'NBC-1212', '2024-12-01', '06:00:00', '12:00:00', 'Roberto Lim',      54), -- Route 5 (55 cap) - 1 confirmed
(12, 1,  'TNW-3434', '2024-12-05', '10:00:00', '12:30:00', 'Nestor Aquino',    13); -- Route 1 (14 cap) - 1 confirmed


-- RESERVATION
INSERT INTO `reservation` 
    (`reservation_ID`, `passenger_ID`, `schedule_ID`, `bookingDate`, `seatNumber`, `reservationStatus`)
VALUES
(1,  1,  1,  '2024-10-25', '1A', 'Confirmed'),
(2,  2,  1,  '2024-10-26', '1B', 'Confirmed'),
(3,  3,  2,  '2024-10-27', '2A', 'Confirmed'),
(4,  4,  3,  '2024-10-28', '3A', 'Confirmed'),
(5,  5,  3,  '2024-10-28', '3B', 'Cancelled'), 
(6,  6,  4,  '2024-10-29', '1A', 'Confirmed'),
(7,  7,  5,  '2024-10-30', '1A', 'Confirmed'),
(8,  8,  6,  '2024-11-01', '1A', 'Pending'),   
(9,  9,  7,  '2024-11-02', '2A', 'Confirmed'),
(10, 10, 8,  '2024-11-03', '4A', 'Confirmed'),
(11, 1,  9,  '2024-11-04', '5A', 'Confirmed'), 
(12, 2,  10, '2024-11-05', '1A', 'Confirmed'),
(13, 3,  11, '2024-11-15', '1A', 'Confirmed'),
(14, 4,  12, '2024-11-20', '1A', 'Confirmed');


-- PAYMENT
INSERT INTO `payment` 
    (`reservation_ID`, `paymentDate`, `paymentMethod`, `amountPaid`, `paymentStatus`)
VALUES
(1,  '2024-10-25', 'GCash',         180.00, 'Paid'),      -- Rt 1
(2,  '2024-10-26', 'Cash',          180.00, 'Paid'),      -- Rt 1
(3,  '2024-10-27', 'Maya',          450.00, 'Paid'),      -- Rt 5
(4,  '2024-10-28', 'GCash',         750.00, 'Paid'),      -- Rt 2
(5,  '2024-10-28', 'Cash',          750.00, 'Refunded'),  -- Rt 2
(6,  '2024-10-29', 'Bank Transfer', 220.00, 'Paid'),      -- Rt 3
(7,  '2024-10-30', 'Cash',          350.00, 'Paid'),      -- Rt 4
(8,  NULL,          NULL,             0.00, 'Pending'),   -- Pending
(9,  '2024-11-02', 'GCash',         700.00, 'Paid'),      -- Rt 8
(10, '2024-11-03', 'GrabPay',       550.00, 'Paid'),      -- Rt 6
(11, '2024-11-04', 'Cash',          350.00, 'Paid'),      -- Rt 4
(12, '2024-11-05', 'GCash',         700.00, 'Paid'),      -- Rt 9
(13, '2024-11-15', 'Cash',          450.00, 'Paid'),      -- Rt 5
(14, '2024-11-20', 'Maya',          180.00, 'Paid');      -- Rt 1

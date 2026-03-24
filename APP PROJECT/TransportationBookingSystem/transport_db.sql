-- ========================================
-- TRANSPORATION BOOKING MANAGEMENT SYSTEM
-- ========================================

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';


-- Schema transport_db

DROP SCHEMA IF EXISTS `transport_db` ;
CREATE SCHEMA IF NOT EXISTS `transport_db` DEFAULT CHARACTER SET utf8 ;
USE transport_db ;

-- 1. Table: operator
CREATE TABLE IF NOT EXISTS `operator` (
  `operator_ID` INT NOT NULL,
  `operatorName` VARCHAR(100) NOT NULL,
  `operatorType` VARCHAR(50) NULL,
  `contactNumber` VARCHAR(20) NULL,
  `email` VARCHAR(100) NULL,
  `activeStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`operator_ID`))
ENGINE = InnoDB;

-- 2. Table: vehicle (Using plateNumber as PK)
CREATE TABLE IF NOT EXISTS `vehicle` (
  `plateNumber` VARCHAR(20) NOT NULL,
  `operator_ID` INT NOT NULL,
  `vehicleType` VARCHAR(50) NULL,
  `capacity` INT NULL,
  `vehicleStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`plateNumber`),
  CONSTRAINT `fk_vehicle_operator`
    FOREIGN KEY (`operator_ID`)
    REFERENCES `operator` (`operator_ID`))
ENGINE = InnoDB;


-- 3. Table: passenger
CREATE TABLE IF NOT EXISTS `passenger` (
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
CREATE TABLE IF NOT EXISTS `route` (
  `route_ID` INT NOT NULL,
  `origin` VARCHAR(100) NULL,
  `destination` VARCHAR(100) NULL,
  `distanceKm` DECIMAL(6,2) NULL,
  `baseFare` DECIMAL(10,2) NULL,
  `routeStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`route_ID`))
ENGINE = InnoDB;

-- 5. Table: schedule
CREATE TABLE IF NOT EXISTS `schedule` (
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
    REFERENCES `vehicle` (`plateNumber`),
  CONSTRAINT `fk_schedule_route`
    FOREIGN KEY (`route_ID`)
    REFERENCES `route` (`route_ID`))
ENGINE = InnoDB;

-- 6. Table: reservation
CREATE TABLE IF NOT EXISTS `reservation` (
  `reservation_ID` INT NOT NULL,
  `passenger_ID` INT NOT NULL,
  `schedule_ID` INT NOT NULL,
  `bookingDate` DATE NULL,
  `seatNumber` VARCHAR(10) NULL,
  `reservationStatus` VARCHAR(20) NULL,
  PRIMARY KEY (`reservation_ID`),
  CONSTRAINT `fk_reservation_passenger`
    FOREIGN KEY (`passenger_ID`)
    REFERENCES `passenger` (`passenger_ID`),
  CONSTRAINT `fk_reservation_schedule`
    FOREIGN KEY (`schedule_ID`)
    REFERENCES `schedule` (`schedule_ID`))
ENGINE = InnoDB;

-- 7. Table: payment (1:1 with Reservation)
CREATE TABLE IF NOT EXISTS `payment` (
  `reservation_ID` INT NOT NULL,
  `paymentDate` DATE NULL,
  `paymentMethod` VARCHAR(30) NULL,
  `amountPaid` DECIMAL(10,2) NULL,
  `paymentStatus` ENUM('Paid', 'Refunded', 'Pending', 'Cancelled') NULL,
  PRIMARY KEY (`reservation_ID`),
  CONSTRAINT `fk_payment_reservation`
    FOREIGN KEY (`reservation_ID`)
    REFERENCES `reservation` (`reservation_ID`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



-- Sample Data 
USE transport_db;
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
('NCR-1234', 1,  'Bus',     55, 'Available'),
('NFX-5678', 1,  'Bus',     55, 'In Service'),
('NBC-9012', 2,  'Bus',     60, 'Available'),
('TNW-3456', 3,  'Bus',     50, 'Available'),
('NCR-7890', 4,  'Minibus', 35, 'Available'),
('NFX-1122', 5,  'Bus',     55, 'In Service'),
('NBC-3344', 6,  'Van',     15, 'Available'),
('TNW-5566', 7,  'Bus',     60, 'Maintenance'),
('NCR-7788', 8,  'Bus',     50, 'Available'),
('NFX-9900', 10, 'Van',     14, 'Available'),
('NBC-1212', 1,  'Bus',     55, 'Available'),
('TNW-3434', 1,  'Van',     14, 'Available');



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
(2,  5,  'NFX-5678', '2024-11-01', '08:00:00', '14:00:00', 'Eduardo Tan',      54), -- Route 5 (55 cap) - 1 confirmed
(3,  2,  'NBC-9012', '2024-11-02', '07:00:00', '15:30:00', 'Fernando Cruz',    59), -- Route 2 (60 cap) - 1 confirmed
(4,  3,  'TNW-3456', '2024-11-02', '09:00:00', '18:00:00', 'Alfredo Santos',   49), -- Route 3 (50 cap) - 1 confirmed
(5,  4,  'NCR-7890', '2024-11-03', '10:00:00', '12:30:00', 'Danilo Reyes',     34), -- Route 4 (35 cap) - 1 confirmed
(6,  1,  'NFX-1122', '2024-11-05', '06:00:00', '12:00:00', 'Roberto Lim',      55), -- Route 1 (55 cap) - 1 pending
(7,  8,  'NBC-3344', '2024-11-05', '07:30:00', '12:00:00', 'Mariano Garcia',   14), -- Route 8 (15 cap) - 1 confirmed
(8,  6,  'TNW-5566', '2024-11-06', '08:00:00', '16:30:00', 'Rodrigo Bautista', 59), -- Route 6 (60 cap) - 1 confirmed
(9,  4,  'NCR-7788', '2024-11-07', '06:30:00', '15:00:00', 'Eduardo Tan',      49), -- Route 4 (50 cap) - 1 confirmed
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


-- Transportation Booking Management

USE transport_db;

DELIMITER $$

-- TRANSACTION 1: Transaction Booking
-- a) verify passenger exists 
-- b)read available routes and schedules 
-- c) insert reservation record 
-- d) insert payment record 
-- e) decrement availableSeats; mark full schedules

DROP PROCEDURE IF EXISTS `sp_BookTransportation`$$
CREATE PROCEDURE `sp_BookTransportation`(
    IN  p_passenger_ID   INT,
    IN  p_schedule_ID    INT,
    IN  p_seatNumber     VARCHAR(10),
    IN  p_paymentMethod  VARCHAR(30),
    OUT p_reservation_ID INT,
    OUT p_message        VARCHAR(255)
)
BEGIN
    DECLARE v_availableSeats  INT DEFAULT 0;
    DECLARE v_baseFare        DECIMAL(10,2) DEFAULT 0.00;
    DECLARE v_passengerExists INT DEFAULT 0;
    DECLARE v_seatTaken       INT DEFAULT 0;
    DECLARE v_newResID        INT DEFAULT 0;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_message        = 'ERROR: Transaction failed and was rolled back.';
        SET p_reservation_ID = -1;
    END;
 
    START TRANSACTION;
 
    -- a) Verify passenger is registered
    SELECT COUNT(*) INTO v_passengerExists
    FROM `passenger`
    WHERE `passenger_ID` = p_passenger_ID;
 
    IF v_passengerExists = 0 THEN
        SET p_message        = 'ERROR: Passenger not found.';
        SET p_reservation_ID = -1;
        ROLLBACK;
    ELSE
        -- b) Check available seats (lock row)
        SELECT `availableSeats` INTO v_availableSeats
        FROM `schedule`
        WHERE `schedule_ID` = p_schedule_ID
        FOR UPDATE;
 
        IF v_availableSeats <= 0 THEN
            SET p_message        = 'ERROR: No available seats on this schedule.';
            SET p_reservation_ID = -1;
            ROLLBACK;
        ELSE
            -- Check seat not already taken
            SELECT COUNT(*) INTO v_seatTaken
            FROM `reservation`
            WHERE `schedule_ID`        = p_schedule_ID
              AND `seatNumber`         = p_seatNumber
              AND `reservationStatus` != 'Cancelled';
 
            IF v_seatTaken > 0 THEN
                SET p_message        = 'ERROR: Seat number is already taken on this schedule.';
                SET p_reservation_ID = -1;
                ROLLBACK;
            ELSE
                -- Generate next reservation_ID
                SELECT COALESCE(MAX(`reservation_ID`), 0) + 1 INTO v_newResID FROM `reservation`;
 
                -- c) Insert reservation record
                INSERT INTO `reservation`
                    (`reservation_ID`, `passenger_ID`, `schedule_ID`,
                     `bookingDate`, `seatNumber`, `reservationStatus`)
                VALUES
                    (v_newResID, p_passenger_ID, p_schedule_ID,
                     CURDATE(), p_seatNumber, 'Confirmed');
 
                -- Retrieve base fare from linked route
                SELECT r.`baseFare` INTO v_baseFare
                FROM `schedule` s
                JOIN `route` r ON s.`route_ID` = r.`route_ID`
                WHERE s.`schedule_ID` = p_schedule_ID;
 
                -- d) Insert payment record (reservation_ID is PK, 1:1 with reservation)
                INSERT INTO `payment`
                    (`reservation_ID`, `paymentDate`,
                     `paymentMethod`, `amountPaid`, `paymentStatus`)
                VALUES
                    (v_newResID, CURDATE(),
                     p_paymentMethod, v_baseFare, 'Paid');
 
                -- e) Decrement available seats
                UPDATE `schedule`
                SET `availableSeats` = `availableSeats` - 1
                WHERE `schedule_ID` = p_schedule_ID;
 
                -- Set OUT params before COMMIT
                SET p_reservation_ID = v_newResID;
                SET p_message = CONCAT(
                    'SUCCESS: Booking confirmed. reservation_ID=', v_newResID,
                    ', AmountPaid=', v_baseFare,
                    ', PaymentMethod=', p_paymentMethod
                );
 
                COMMIT;
            END IF;
        END IF;
    END IF;
END$$
  

-- TRANSACTION 2: Reservation Modification
--   a) Read existing reservation
--   b) Update schedule and/or seat
--   c) Recalculate fare difference
--   d) Update payment record for extra fees / adjustments

DROP PROCEDURE IF EXISTS `sp_ModifyReservation`$$
CREATE PROCEDURE `sp_ModifyReservation`(
    IN  p_reservation_ID  INT,
    IN  p_new_schedule_ID INT,
    IN  p_newSeatNumber   VARCHAR(10),
    OUT p_message         VARCHAR(255)
)
BEGIN
    DECLARE v_old_schedule_ID   INT;
    DECLARE v_oldFare           DECIMAL(10,2);
    DECLARE v_newFare           DECIMAL(10,2);
    DECLARE v_fareDiff          DECIMAL(10,2);
    DECLARE v_availableSeats    INT;
    DECLARE v_reservationStatus VARCHAR(20);
    DECLARE v_seatTaken         INT DEFAULT 0;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_message = 'ERROR: Modification failed and was rolled back.';
    END;
 
    START TRANSACTION;
 
    -- a) Read existing reservation (lock row)
    SELECT `schedule_ID`, `reservationStatus`
    INTO v_old_schedule_ID, v_reservationStatus
    FROM `reservation`
    WHERE `reservation_ID` = p_reservation_ID
    FOR UPDATE;
 
    IF v_reservationStatus = 'Cancelled' THEN
        SET p_message = 'ERROR: Cannot modify a cancelled reservation.';
        ROLLBACK;
    ELSE
        -- Check new schedule capacity (lock row)
        SELECT `availableSeats` INTO v_availableSeats
        FROM `schedule`
        WHERE `schedule_ID` = p_new_schedule_ID
        FOR UPDATE;
 
        -- Fixed: Always enforce capacity regardless of whether schedule changed
        IF v_availableSeats <= 0 THEN
            SET p_message = 'ERROR: Target schedule has no available seats.';
            ROLLBACK;
        ELSE
            -- b) Check new seat is not taken on target schedule
            SELECT COUNT(*) INTO v_seatTaken
            FROM `reservation`
            WHERE `schedule_ID`        = p_new_schedule_ID
              AND `seatNumber`         = p_newSeatNumber
              AND `reservationStatus` != 'Cancelled'
              AND `reservation_ID`    != p_reservation_ID;
 
            IF v_seatTaken > 0 THEN
                SET p_message = 'ERROR: Seat is already taken on the target schedule.';
                ROLLBACK;
            ELSE
                -- c) Get old and new fares
                SELECT r.`baseFare` INTO v_oldFare
                FROM `schedule` s
                JOIN `route` r ON s.`route_ID` = r.`route_ID`
                WHERE s.`schedule_ID` = v_old_schedule_ID;
 
                SELECT r.`baseFare` INTO v_newFare
                FROM `schedule` s
                JOIN `route` r ON s.`route_ID` = r.`route_ID`
                WHERE s.`schedule_ID` = p_new_schedule_ID;
 
                SET v_fareDiff = v_newFare - v_oldFare;
 
                -- b) Update reservation
                UPDATE `reservation`
                SET `schedule_ID`       = p_new_schedule_ID,
                    `seatNumber`        = p_newSeatNumber,
                    `reservationStatus` = 'Confirmed'
                WHERE `reservation_ID` = p_reservation_ID;
 
                -- d) Update payment record
                UPDATE `payment`
                SET `amountPaid`    = `amountPaid` + v_fareDiff,
                    `paymentStatus` = IF(v_fareDiff >= 0, 'Paid', 'Refunded')
                WHERE `reservation_ID` = p_reservation_ID;
 
                -- Restore seat on old schedule; consume seat on new schedule
                IF p_new_schedule_ID != v_old_schedule_ID THEN
                    UPDATE `schedule`
                    SET `availableSeats` = `availableSeats` + 1
                    WHERE `schedule_ID` = v_old_schedule_ID;
 
                    UPDATE `schedule`
                    SET `availableSeats` = `availableSeats` - 1
                    WHERE `schedule_ID` = p_new_schedule_ID;
                END IF;
 
                SET p_message = CONCAT(
                    'SUCCESS: Reservation modified. Fare difference=', v_fareDiff,
                    ', New amountPaid=', v_oldFare + v_fareDiff
                );
 
                COMMIT;
            END IF;
        END IF;
    END IF;
END$$
 



-- TRANSACTION 3: Reservation Cancellation
--   a) Read reservation and check cancellation is allowed
--   b) Set reservationStatus = 'Cancelled'
--   c) Set paymentStatus = 'Refunded' (if was Paid) / 'Cancelled'
--   d) Restore availableSeats on the schedule


DROP PROCEDURE IF EXISTS `sp_CancelReservation`$$
CREATE PROCEDURE `sp_CancelReservation`(
    IN  p_reservation_ID INT,
    OUT p_message        VARCHAR(255)
)
BEGIN
    DECLARE v_schedule_ID       INT;
    DECLARE v_reservationStatus VARCHAR(20);
    DECLARE v_paymentStatus     VARCHAR(20);
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_message = 'ERROR: Cancellation failed and was rolled back.';
    END;
 
    START TRANSACTION;
 
    -- a) Read reservation (lock row)
    SELECT `schedule_ID`, `reservationStatus`
    INTO v_schedule_ID, v_reservationStatus
    FROM `reservation`
    WHERE `reservation_ID` = p_reservation_ID
    FOR UPDATE;
 
    IF v_reservationStatus = 'Cancelled' THEN
        SET p_message = 'ERROR: Reservation is already cancelled.';
        ROLLBACK;
    ELSE
        -- b) Cancel the reservation
        UPDATE `reservation`
        SET `reservationStatus` = 'Cancelled'
        WHERE `reservation_ID` = p_reservation_ID;
 
        -- c) Update payment status
        SELECT `paymentStatus` INTO v_paymentStatus
        FROM `payment`
        WHERE `reservation_ID` = p_reservation_ID;
 
        UPDATE `payment`
        SET `paymentStatus` = IF(v_paymentStatus = 'Paid', 'Refunded', 'Cancelled')
        WHERE `reservation_ID` = p_reservation_ID;
 
        -- d) Restore seat only if reservation was consuming one (not Pending)
        IF v_reservationStatus != 'Pending' THEN
            UPDATE `schedule`
            SET `availableSeats` = `availableSeats` + 1
            WHERE `schedule_ID` = v_schedule_ID;
        END IF;
 
        SET p_message = CONCAT(
            'SUCCESS: Reservation ', p_reservation_ID,
            ' cancelled. Seat restored on schedule ', v_schedule_ID, '.'
        );
 
        COMMIT;
    END IF;
END$$



-- TRANSACTION 4: Schedule Assignment and Capacity Update
--   a) Assign an available vehicle to a route and schedule
--   b) Assign transportation operator (implicit via vehicle FK)
--   c) Set available seats = vehicle capacity
--   d) Prevent overbooking: disable schedules at full capacity

DROP PROCEDURE IF EXISTS `sp_AssignSchedule`$$
CREATE PROCEDURE `sp_AssignSchedule`(
    IN  p_route_ID      INT,
    IN  p_plateNumber   VARCHAR(20),
    IN  p_departureDate DATE,
    IN  p_departureTime TIME,
    IN  p_arrivalTime   TIME,
    IN  p_driverName    VARCHAR(100),
    OUT p_schedule_ID   INT,
    OUT p_message       VARCHAR(255)
)
BEGIN
    DECLARE v_vehicleStatus VARCHAR(20);
    DECLARE v_capacity      INT DEFAULT 0;
    DECLARE v_routeStatus   VARCHAR(20);
    DECLARE v_conflict      INT DEFAULT 0;
    DECLARE v_newID         INT DEFAULT 0;
 
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_message     = 'ERROR: Schedule assignment failed and was rolled back.';
        SET p_schedule_ID = -1;
    END;
 
    START TRANSACTION;
 
    -- a) Check vehicle is available (lock row)
    SELECT `vehicleStatus`, `capacity`
    INTO v_vehicleStatus, v_capacity
    FROM `vehicle`
    WHERE `plateNumber` = p_plateNumber
    FOR UPDATE;
 
    IF v_vehicleStatus != 'Available' THEN
        SET p_message     = CONCAT('ERROR: Vehicle ', p_plateNumber,
                                   ' is not available. Status: ', v_vehicleStatus);
        SET p_schedule_ID = -1;
        ROLLBACK;
    ELSE
        -- Check route is active
        SELECT `routeStatus` INTO v_routeStatus
        FROM `route`
        WHERE `route_ID` = p_route_ID;
 
        IF v_routeStatus != 'Active' THEN
            SET p_message     = 'ERROR: Selected route is not active.';
            SET p_schedule_ID = -1;
            ROLLBACK;
        ELSE
            -- Check no scheduling conflict (same vehicle, same date & time)
            SELECT COUNT(*) INTO v_conflict
            FROM `schedule`
            WHERE `plateNumber`   = p_plateNumber
              AND `departureDate` = p_departureDate
              AND `departureTime` = p_departureTime;
 
            IF v_conflict > 0 THEN
                SET p_message     = 'ERROR: Vehicle already scheduled at this date and time.';
                SET p_schedule_ID = -1;
                ROLLBACK;
            ELSE
                -- Generate next schedule_ID
                SELECT COALESCE(MAX(`schedule_ID`), 0) + 1 INTO v_newID FROM `schedule`;
 
                -- b+c) Insert schedule; availableSeats = vehicle capacity
                INSERT INTO `schedule`
                    (`schedule_ID`, `route_ID`, `plateNumber`, `departureDate`,
                     `departureTime`, `arrivalTime`, `driverName`, `availableSeats`)
                VALUES
                    (v_newID, p_route_ID, p_plateNumber, p_departureDate,
                     p_departureTime, p_arrivalTime, p_driverName, v_capacity);
 
                -- d) Mark vehicle as In Service to prevent double-scheduling
                UPDATE `vehicle`
                SET `vehicleStatus` = 'In Service'
                WHERE `plateNumber` = p_plateNumber;
 
                SET p_schedule_ID = v_newID;
                SET p_message = CONCAT(
                    'SUCCESS: Schedule created. schedule_ID=', v_newID,
                    ', Vehicle=', p_plateNumber,
                    ', Capacity=', v_capacity
                );
 
                COMMIT;
            END IF;
        END IF;
    END IF;
END$$
 
DELIMITER ;
 



-- DEMO CALLS

-- Transaction 1: Passenger 3 books schedule 6, seat 2A, pays via GCash
CALL `sp_BookTransportation`(3, 6, '2A', 'GCash', @resID, @msg);
SELECT @resID AS new_reservation_ID, @msg AS result_message;
 
-- Transaction 2: Modify reservation 3 -> move to schedule 9, seat 3B
CALL `sp_ModifyReservation`(3, 9, '3B', @msg);
SELECT @msg AS result_message;
 
-- Transaction 3: Cancel reservation 4
CALL `sp_CancelReservation`(4, @msg);
SELECT @msg AS result_message;
 
-- Transaction 4: Assign vehicle NCR-7890 to route 2 on 2024-12-25
CALL `sp_AssignSchedule`(
    2, 'NCR-7890', '2024-12-25',
    '07:00:00', '15:30:00', 'Alfredo Santos',
    @schedID, @msg
);
SELECT @schedID AS new_schedule_ID, @msg AS result_message;


-- REPORTS

-- Report 1: Monthly Passenger Activity
-- Shows total reservations made and total amount spent per passenger for a given month/year.
-- Filter by month and year in the application layer:
-- SELECT * FROM view_MonthlyPassengerActivity WHERE Year=2024 AND Month=11;

CREATE OR REPLACE VIEW `view_MonthlyPassengerActivity` AS
SELECT
    p.`passenger_ID`,
    p.`firstName`,
    p.`lastName`,
    YEAR(r.`bookingDate`)  AS `Year`,
    MONTH(r.`bookingDate`) AS `Month`,
    COUNT(r.`reservation_ID`)  AS `TotalReservations`,
    SUM(pay.`amountPaid`)      AS `TotalAmountSpent`
FROM `passenger` p
JOIN `reservation` r   ON p.`passenger_ID`   = r.`passenger_ID`
JOIN `payment`    pay  ON r.`reservation_ID`  = pay.`reservation_ID`
WHERE pay.`paymentStatus` = 'Paid'
GROUP BY p.`passenger_ID`, `Year`, `Month`;


-- Report 2: Annual Route Revenue
-- Shows total bookings and total revenue per route for a given year.
-- Filter by year in the application layer:
-- SELECT * FROM view_AnnualRouteRevenue WHERE Year=2024;


CREATE OR REPLACE VIEW `view_AnnualRouteRevenue` AS
SELECT
    ro.`route_ID`,
    ro.`origin`,
    ro.`destination`,
    YEAR(pay.`paymentDate`)        AS `Year`,
    COUNT(r.`reservation_ID`)      AS `TotalReservations`,
    SUM(pay.`amountPaid`)          AS `TotalRevenue`
FROM `route` ro
JOIN `schedule`    s   ON ro.`route_ID`       = s.`route_ID`
JOIN `reservation` r   ON s.`schedule_ID`     = r.`schedule_ID`
JOIN `payment`     pay ON r.`reservation_ID`  = pay.`reservation_ID`
WHERE pay.`paymentStatus` = 'Paid'
GROUP BY ro.`route_ID`, `Year`;


-- Report 3: Monthly Payment Transaction Summary
-- Shows total and average payment amount for a given month/year.
-- Filter by month and year in the application layer:
-- SELECT * FROM view_MonthlyPaymentSummary WHERE Year=2024 AND Month=11;


CREATE OR REPLACE VIEW `view_MonthlyPaymentSummary` AS
SELECT
    YEAR(`paymentDate`)  AS `Year`,
    MONTH(`paymentDate`) AS `Month`,
    SUM(`amountPaid`)    AS `TotalRevenue`,
    AVG(`amountPaid`)    AS `AvgTransaction`
FROM `payment`
WHERE `paymentStatus` = 'Paid'
GROUP BY `Year`, `Month`;



-- Report 4: Schedule Occupancy Report
-- Shows occupied seats and remaining availability per schedule.
-- Filter by date or route in the application layer:
-- SELECT * FROM view_ScheduleOccupancy WHERE departureDate = '2024-11-01';
-- SELECT * FROM view_ScheduleOccupancy WHERE route_ID = 1;

CREATE OR REPLACE VIEW `view_ScheduleOccupancy` AS
SELECT
    s.`schedule_ID`,
    s.`route_ID`,
    ro.`origin`,
    ro.`destination`,
    s.`plateNumber`,
    s.`departureDate`,
    s.`departureTime`,
    s.`driverName`,
    v.`capacity`                        AS `TotalSeats`,
    (v.`capacity` - s.`availableSeats`) AS `OccupiedSeats`,
    s.`availableSeats`                  AS `RemainingSeats`
FROM `schedule` s
JOIN `vehicle` v  ON s.`plateNumber` = v.`plateNumber`
JOIN `route`   ro ON s.`route_ID`    = ro.`route_ID`;
 

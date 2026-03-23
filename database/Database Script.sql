SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema transport_db
-- -----------------------------------------------------
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
-- ========================================
-- TRANSPORATION BOOKING MANAGEMENT SYSTEM
-- ========================================

DROP DATABASE IF EXISTS transport_db;
CREATE DATABASE transport_db;
USE transport_db;

/* Passnger Table */
CREATE TABLE passenger (
	passengerID INT AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(50),
    firstname VARCHAR(50),
    email VARCHAR(100),
    contactNumber VARCHAR(20),
    registrationDate DATE
);

/* Operator Table */
CREATE TABLE operator (
	operatorID INT AUTO_INCREMENT PRIMARY KEY,
    operatorName VARCHAR(100),
    operatorType VARCHAR(50),
    contactNumber VARCHAR(20),
    email VARCHAR(100),
    activeStatus VARCHAR(20)
);

/* Vehicle Table */
CREATE TABLE vehicle (
	plateNumber VARCHAR(20) PRIMARY KEY,
    vehicleID INT AUTO_INCREMENT,
    operatorID INT,
    vehicleType VARCHAR(50),
    capacity INT,
    vehicleStatus VARCHAR(20),
    FOREIGN KEY (operatorID) REFERENCES operator(operatorID)
);

/* Route Table */
CREATE TABLE route (
	routeID INT AUTO_INCREMENT PRIMARY KEY,
    origin VARCHAR(100),
    destination VARCHAR(100),
    distanceKm DECIMAL(10,2),
    baseFare DECIMAL(10,2),
    routeStatus VARCHAR(20)
);

/* Schedule Table */
CREATE TABLE schedule (
	scheduleID INT AUTO_INCREMENT PRIMARY KEY,
    routeID INT,
    plateNumber VARCHAR(20),
    departureDate DATE,
    departureTime TIME,
    arrivalTime TIME,
    driverName VARCHAR(100),
    availableSeats INT,
    FOREIGN KEY (routeID) REFERENCES route(routeID),
    FOREIGN KEY (plateNumber) REFERENCES vehicle(plateNumber)
);

/* Reservation Table */
CREATE TABLE reservation (
	reservationID INT AUTO_INCREMENT PRIMARY KEY,
    passengerID INT,
    scheduleID INT,
    bookingDate DATE,
    seatNumber VARCHAR(10),
    reservationStatus VARCHAR(20),
    FOREIGN KEY (passengerID) REFERENCES passenger(passengerID),
    FOREIGN KEY (scheduleID) REFERENCES schedule(scheduleID)
);

/* Payment Table */
CREATE TABLE payment (
	paymentID INT AUTO_INCREMENT PRIMARY KEY,
    reservationID INT,
	paymentDate DATE,
    paymentMethod VARCHAR(50),
	amountPaid DECIMAL(10,2),
    paymentStatus ENUM('PENDING', 'COMPLETED', 'REFUNDED'),
    FOREIGN KEY (reservationID) REFERENCES reservation(reservationID)
);
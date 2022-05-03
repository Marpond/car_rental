USE master GO

CREATE DATABASE DB_AutoCamperRental GO

USE DB_AutoCamperRental GO

CREATE TABLE tbl_Category(
                             fld_CategoryID INT PRIMARY KEY IDENTITY,
                             fld_CategoryName VARCHAR(30),
                             fld_MainSeasonPrice DOUBLE PRECISION,
                             fld_LowSeasonPrice DOUBLE PRECISION
)GO

CREATE TABLE tbl_InsurancePackage(
                                     fld_InsurancePackageID INT PRIMARY KEY IDENTITY,
                                     fld_Price DOUBLE PRECISION,
                                     fld_Description VARCHAR(50)
)GO

CREATE TABLE tbl_Camper(
                           fld_CamperID INT PRIMARY KEY IDENTITY,
                           fld_LicensePlate NCHAR(10),
                           fld_CategoryID INT FOREIGN KEY REFERENCES tbl_Category
)GO

CREATE TABLE tbl_Reservation(
                                fld_ReservationID INT PRIMARY KEY IDENTITY,
                                fld_DateOfReservation DATE,
                                fld_RentalStart DATE,
                                fld_RentalEnd DATE,
                                fld_WarningActive BIT,
                                fld_IsCancelled BIT,
                                fld_CamperID INT FOREIGN KEY REFERENCES tbl_Camper,
                                fld_InsurancePackageID INT FOREIGN KEY REFERENCES tbl_InsurancePackage
)GO

CREATE TABLE tbl_Payments(
                             fld_PaymentID INT PRIMARY KEY IDENTITY,
                             fld_Deposit10PercentPaid BIT,
                             fld_Deposit90PercentPaid BIT,
                             fld_CurrentDeadline DATE,
                             fld_ReservationID INT FOREIGN KEY REFERENCES tbl_Reservation
)GO

CREATE TABLE tbl_Discount(
                             fld_DiscountID INT PRIMARY KEY IDENTITY,
                             fld_MaxRentalCount INT,
                             fld_DiscountPercent INT
)GO

CREATE TABLE tbl_Receipt(
                            fld_ReceiptID INT PRIMARY KEY IDENTITY,
                            fld_DistanceDriven DOUBLE PRECISION,
                            fld_ReturnedFuel DOUBLE PRECISION,
                            fld_FuelPrice DOUBLE PRECISION,
                            fld_FinalPrice DOUBLE PRECISION,
                            fld_MileagePrice DOUBLE PRECISION,
                            fld_ReservationID INT FOREIGN KEY REFERENCES tbl_Reservation,
                            fld_DiscountID INT FOREIGN KEY REFERENCES tbl_Discount
)GO

CREATE TABLE tbl_Account(
    fld_AccountID INT PRIMARY KEY IDENTITY,
    fld_PhoneNoNumber INT,
    fld_Name VARCHAR(35),
    fld_Address VARCHAR(40),
    fld_Username VARCHAR(20),
    fld_Password VARCHAR(20),
    fld_ISAdmin BIT
)GO




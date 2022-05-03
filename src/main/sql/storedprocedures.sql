use DB_AutoCamperRental go

CREATE PROCEDURE createNewReceipt @distanceDriven DOUBLE PRECISION,
                                  @returnedFuel DOUBLE PRECISION,
                                  @fuelPrice DOUBLE PRECISION,
                                  @finalPrice DOUBLE PRECISION,
                                  @mileagePrice DOUBLE PRECISION,
                                  @reservationID INT,
                                  @discountID INT
AS
    INSERT INTO tbl_Receipt
    (fld_DistanceDriven,
     fld_ReturnedFuel,
     fld_FuelPrice,
     fld_FinalPrice,
     fld_MileagePrice,
     fld_ReservationID,
     fld_DiscountID)
    VALUES (@distanceDriven, @returnedFuel, @fuelPrice, @finalPrice, @mileagePrice, @reservationID, @discountID)
GO

CREATE PROCEDURE CreateNewReservation @dateOfReservation DATE,
                                      @rentalStart INT,
                                      @rentalEnd INT,
                                      @isCancelled BIT,
                                      @licensePlate NCHAR(10),
                                      @insurancePackageID INT,
                                      @accountID INT
AS
    INSERT INTO tbl_Reservation(fld_DateOfReservation,
                                fld_RentalStart,
                                fld_RentalEnd,
                                fld_IsCancelled,
                                fld_LicensePlate,
                                fld_InsurancePackageID,
                                fld_AccountID)
    VALUES (@dateOfReservation,
            @rentalStart,
            @rentalEnd,
            @isCancelled,
            @licensePlate,
            @insurancePackageID,
            @accountID);
GO

CREATE PROCEDURE InsertInitialCamperEntries
AS
INSERT INTO tbl_Camper(fld_LicensePlate, fld_CategoryID)
VALUES ('LP001', 1),
       ('LP002', 1),
       ('LP003', 1),
       ('LP004', 2),
       ('LP005', 2),
       ('LP006', 2),
       ('LP007', 3),
       ('LP008', 3),
       ('LP009', 3),
       ('LP010', 1),
       ('LP011', 2),
       ('LP012', 3)
GO

CREATE PROCEDURE insertInitialDiscountEntries
AS
    INSERT INTO tbl_Discount(fld_MaxRentalCount, fld_DiscountPercent)
    VALUES (2, 0),
           (5, 5),
           (10000, 10)
GO

CREATE PROCEDURE InsertInitialCategories
AS
    INSERT INTO tbl_Category(fld_CategoryName, fld_MainSeasonPrice, fld_LowSeasonPrice)
    VALUES ('Luxury', 3000, 2500),
           ('Standard', 2000, 1500),
           ('Basic', 1000, 500)
GO

CREATE PROCEDURE InsertInitialInsurancePackageEntries
as
    INSERT INTO tbl_InsurancePackage(fld_Price, fld_Description)
    VALUES (2000, 'Basic. Collision & Theft'),
           (4000, 'All included');
go

CREATE PROCEDURE CreateNewPaymentEntry
    @dep10 BIT,
	@dep90 BIT,
	@warningActive BIT,
	@currentDeadline DATE,
	@reservationID INT
		AS
            BEGIN
                INSERT INTO tbl_Payments
                (
                    fld_Deposit10PercentPaid,
                    fld_Deposit90PercentPaid,
                    fld_WarningActive,
                    fld_CurrentDeadline,
                    fld_ReservationID
                )
                VALUES
                (
                    @dep10,
                    @dep90,
                    @warningActive,
                    @currentDeadline,
                    @reservationID
                )
            END
GO


CREATE PROCEDURE CreateReceiptEntry
    @distanceDriven DOUBLE PRECISION,
	@returnedFuel DOUBLE PRECISION,
	@fuelPrice DOUBLE PRECISION,
	@finalPrice DOUBLE PRECISION,
	@mileagePrice DOUBLE PRECISION,
	@reservationID INT,
	@discountID INT
		AS
            BEGIN
            INSERT INTO tbl_Receipt
            (
                fld_DistanceDriven,
                fld_ReturnedFuel,
                fld_FuelPrice,
                fld_FinalPrice,
                fld_MileagePrice,
                fld_ReservationID,
                fld_DiscountID
            )
            VALUES
                (
                    @distanceDriven,
                    @returnedFuel,
                    @fuelPrice,
                    @finalPrice,
                    @mileagePrice,
                    @reservationID,
                    @discountID
                )
            END
Go


CREATE PROCEDURE CreateNewAccount
    @phoneNumber INT,
	@name VARCHAR(35),
	@address VARCHAR(40),
	@username VARCHAR(20),
	@password VARCHAR(20)
		AS
            BEGIN
                INSERT INTO tbl_Account
                (
                    fld_PhoneNoNumber,
                    fld_Name,
                    fld_Address,
                    fld_Username,
                    fld_Password
                )
                VALUES
                    (
                        @phoneNumber,
                        @name,
                        @address,
                        @username,
                        @password
                    )
            END
GO

CREATE PROCEDURE InsertInitialAccounts
AS
INSERT INTO tbl_Account(fld_PhoneNoNumber, fld_Name, fld_Address, fld_Username, fld_Password)
values
('12345678', 'John Doe', '123 Main Street', 'jdoe', 'password'),
('12345679', 'Jane Doe', '456 Main Street', 'jdoe', 'password'),
('12345680', 'John Smith', '789 Main Street', 'jsmith', 'password'),
('12345681', 'Jane Smith', '012 Main Street', 'jsmith', 'password');
go


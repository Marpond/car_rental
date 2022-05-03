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
                                      @rentalStart DATE,
                                      @rentalEnd DATE,
                                      @isCancelled BIT,
                                      @camperID INT,
                                      @insurancePackageID INT,
                                      @accountID INT
AS
    INSERT INTO tbl_Reservation(fld_DateOfReservation,
                                fld_RentalStart,
                                fld_RentalEnd,
                                fld_IsCancelled,
                                fld_CamperID,
                                fld_InsurancePackageID,
                                fld_AccountID)
    VALUES (@dateOfReservation,
            @rentalStart,
            @rentalEnd,
            @isCancelled,
            @camperID,
            @insurancePackageID,
            @accountID);
GO

CREATE PROCEDURE InsertInitialCamperEntries
AS
INSERT INTO tbl_Camper(fld_LicensePlate)
VALUES ('LP001'),
       ('LP002'),
       ('LP003'),
       ('LP004'),
       ('LP005'),
       ('LP006'),
       ('LP007'),
       ('LP008'),
       ('LP009'),
       ('LP010'),
       ('LP011'),
       ('LP012')
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


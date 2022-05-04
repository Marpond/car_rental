use DB_AutoCamperRental go

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

CREATE PROCEDURE InsertInitialDiscountEntries
AS
    INSERT INTO tbl_Discount(fld_MaxRentalCount, fld_DiscountPercent)
    VALUES (2, 0),
           (5, 5),
           (10000, 10)
GO

CREATE PROCEDURE InsertInitialCategoryEntries
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

CREATE PROCEDURE InsertInitialAccountEntries
AS
INSERT INTO tbl_Account(fld_PhoneNumber, fld_Name, fld_Address, fld_Username, fld_Password)
values
('12345678', 'John Doe', '123 Main Street', 'jhdoe', 'password'),
('12345679', 'Jane Doe', '456 Main Street', 'jndoe', 'password'),
('12345680', 'John Smith', '789 Main Street', 'jhsmith', 'password'),
('12345681', 'Jane Smith', '012 Main Street', 'jnsmith', 'password'),
('12345678', 'Bob','idk','acc','ps');
go

create procedure GetCamperCategoryDetails @fld_LicensePlate nchar(10) as
    select fld_CategoryName, fld_MainSeasonPrice, fld_LowSeasonPrice
    from tbl_Category category
    inner join tbl_Camper camper
    on category.fld_CategoryID = camper.fld_CategoryID
    where fld_LicensePlate = @fld_LicensePlate;
go

CREATE PROCEDURE InsertReservation(
    @fld_DateOfReservation DATE,
    @fld_RentalStart DATE,
    @fld_RentalEnd DATE,
    @fld_LicensePlate NCHAR(10),
    @fld_InsurancePackageID INT,
    @fld_AccountID INT
) AS
INSERT INTO tbl_Reservation(
    fld_DateOfReservation,
    fld_RentalStart,
    fld_RentalEnd,
    fld_LicensePlate,
    fld_InsurancePackageID,
    fld_AccountID)
VALUES(
          @fld_DateOfReservation,
          @fld_RentalStart,
          @fld_RentalEnd,
          @fld_LicensePlate,
          @fld_InsurancePackageID,
          @fld_AccountID);
GO

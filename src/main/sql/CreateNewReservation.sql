USE [DB_AutoCamperRental]
GO

CREATE PROCEDURE CreateNewReservation
	@dateOfReservation DATE,
	@rentalStart DATE,
	@rentalEnd DATE,
	@warningActive BIT,
	@isCancelled BIT,
	@customerID INT,
	@camperID INT,
	@insurancePackageID INT

		AS
			BEGIN
				INSERT INTO
				tbl_Reservation(
				fld_DateOfReservation,
				fld_RentalStart,
				fld_RentalEnd,
				fld_WarningActive,
				fld_IsCancelled,
				fld_CustomerID,
				fld_CamperID,
				fld_InsurancePackageID
				)
				VALUES(@dateOfReservation, @rentalStart, @rentalEnd, @warningActive, @isCancelled, @customerID, @camperID, @insurancePackageID);
			END


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
	@insurancePackageID INT,
    @accountID INT

		AS
			BEGIN
				INSERT INTO
				tbl_Reservation(
				fld_DateOfReservation,
				fld_RentalStart,
				fld_RentalEnd,
				fld_WarningActive,
				fld_IsCancelled,
				fld_CamperID,
				fld_InsurancePackageID,
			    fld_AccountID
				)
				VALUES(@dateOfReservation,
				       @rentalStart,
				       @rentalEnd,
				       @warningActive,
				       @isCancelled,
				       @camperID,
				       @insurancePackageID,
				       @accountID);
			END


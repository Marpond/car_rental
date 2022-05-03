USE DB_AutoCamperRental;
GO

CREATE PROCEDURE createNewReceipt
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
				VALUES(@distanceDriven, @returnedFuel, @fuelPrice, @finalPrice, @mileagePrice, @reservationID, @discountID)
			END







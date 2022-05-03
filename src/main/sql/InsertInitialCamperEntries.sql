USE DB_AutoCamperRental;
GO

CREATE PROCEDURE InsertInitialCamperEntries
	AS
		BEGIN
			INSERT INTO tbl_Camper(fld_LicensePlate)
			VALUES('LP001'),
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
		END


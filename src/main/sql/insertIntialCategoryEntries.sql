USE DB_AutoCamperRental;
GO

CREATE PROCEDURE InsertInitialCategories
	AS
		BEGIN
			INSERT INTO tbl_Category(fld_CategoryName, fld_MainSeasonPrice, fld_LowSeasonPrice)
			VALUES('Luxury', 3000, 2500),
			('Standard', 2000, 1500),
			('Basic', 1000, 500)

		END



USE DB_AutoCamperRental;
GO

CREATE PROCEDURE insertInitialDiscountEntries
	AS
		BEGIN
            INSERT INTO tbl_Discount(fld_NumberOfRentals, fld_DiscountPercent)
            VALUES(2, 0),
            (5, 5),
            (10000, 10)
		END



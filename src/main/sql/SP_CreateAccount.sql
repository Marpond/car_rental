USE DB_AutoCamperRental;
GO

CREATE PROCEDURE CreateAccount @userName varchar(20), @password varchar(20), @isAdmin bit
	AS
		BEGIN
			INSERT INTO tbl_Accounts(fld_Username, fld_Password, fld_ISAdmin)
			VALUES(@userName, @password, @isAdmin)
		END

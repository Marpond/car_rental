USE DB_AutoCamperRental;
GO

CREATE PROCEDURE searchForCustomer @phoneNumber INT, @name VARCHAR(35)
	AS
		BEGIN
			SELECT fld_CustomerID from tbl_Customer;
		END


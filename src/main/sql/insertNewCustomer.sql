USE DB_AutoCamperRental;
GO

CREATE PROCEDURE InsertNewCustomer @phoneNumber INT, @name VARCHAR(35), @address VARCHAR(40)
	AS
		BEGIN
			INSERT INTO tbl_Customer(fld_PhoneNoNumber, fld_Name, fld_Address)
			VALUES(@phoneNumber, @name, @address);
		END
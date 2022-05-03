CREATE PROCEDURE InsertInitialInsurancePackageEntries
	AS
		BEGIN
			INSERT INTO tbl_InsurancePackage(fld_Price, fld_Description)
			VALUES(2000, 'Basic. Collision & Theft'),
			(4000, 'All included');
		END




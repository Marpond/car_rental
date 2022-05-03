USE DB_AutoCamperRental;
GO

CREATE PROCEDURE CreateAccount @userName varchar(20), @password varchar(20)
    AS
        BEGIN
            INSERT INTO tbl_Account(fld_Username, fld_Password)
            VALUES(@userName, @password)
        END
GO

package carrental.car_rental;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class DatabaseHandler {
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    private static boolean isCreated = false;
    private static DatabaseHandler instance;

    public static void DBConstructor(){
        if(!isCreated){
            instance = new DatabaseHandler();
            isCreated = true;
        }else{
            //Don't allow creation
            System.out.println("Already created");
        }
    }

    public static DatabaseHandler getInstance(){
        return instance;
    }

    private DatabaseHandler(){
        try {
            // Load the properties file
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/carrental/car_rental/database.properties"));
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Connect to the database
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;" +
                            "trustServerCertificate=true;" +
                            "database=" + properties.getProperty("database") + ";" +
                            "user=" + properties.getProperty("username") + ";" +
                            "password=" + properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try{
            ps.close();
            con.close();
            System.out.println("CONNECTION CLOSED");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getAccounts() {
        HashMap<String, String> accounts = new HashMap<>();
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_Account");
            rs = ps.executeQuery();
            while(rs.next()) {
                accounts.put(rs.getString("fld_Username"), rs.getString("fld_Password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<String> getCamperLicensePlates() {
        List<String> campers = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT fld_LicensePlate FROM tbl_Camper");
            rs = ps.executeQuery();
            while(rs.next()) {
                campers.add(rs.getString("fld_LicensePlate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campers;
    }

    public List<List<String>> getInsuranceDetails() {
        List<List<String>> insurance = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT fld_Price, fld_Description FROM tbl_InsurancePackage");
            rs = ps.executeQuery();
            while(rs.next()) {
                insurance.add(List.of(rs.getString("fld_Price"), rs.getString("fld_Description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insurance;
    }

    public List<String> getCamperCategoryDetails(String licensePlate) {
        List<String> categoryDetails = new ArrayList<>();
        try {
            ps = con.prepareStatement("GetCamperCategoryDetails ?");
            ps.setString(1, licensePlate);
            rs = ps.executeQuery();
            while(rs.next()) {
                categoryDetails.add(rs.getString("fld_CategoryName"));
                categoryDetails.add(rs.getString("fld_MainSeasonPrice"));
                categoryDetails.add(rs.getString("fld_LowSeasonPrice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryDetails;
    }

}

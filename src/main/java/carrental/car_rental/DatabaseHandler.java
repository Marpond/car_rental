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

    public static void DBConstructor() {
        if (!isCreated) {
            instance = new DatabaseHandler();
            isCreated = true;
        } else {
            //Don't allow creation
            System.out.println("Already created");
        }
    }

    public static DatabaseHandler getInstance() {
        return instance;
    }

    private DatabaseHandler() {
        try {
            // Load the properties file
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/carrental/car_rental/database.properties"));
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Connect to the database
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;" + "trustServerCertificate=true;" + "database=" + properties.getProperty("database") + ";" + "user=" + properties.getProperty("username") + ";" + "password=" + properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            con.close();
            ps.close();
        } catch (Exception ignored) {
        } finally {
            System.out.println("CONNECTION CLOSED");
        }
    }

    public HashMap<String, String> getAccounts() {
        HashMap<String, String> accounts = new HashMap<>();
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_Account");
            rs = ps.executeQuery();
            while (rs.next()) {
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
            while (rs.next()) {
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
            while (rs.next()) {
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
            while (rs.next()) {
                categoryDetails.add("Type: " + rs.getString("fld_CategoryName"));
                categoryDetails.add("Main season: " + rs.getString("fld_MainSeasonPrice"));
                categoryDetails.add("Low season: " + rs.getString("fld_LowSeasonPrice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryDetails;
    }

    public void createNewAccount(int phoneNumber, String name, String address, String username, String password){
        try{
            ps = con.prepareCall("{CALL InsertAccount (?,?,?,?,?)}");
            ps.setInt(1, phoneNumber);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

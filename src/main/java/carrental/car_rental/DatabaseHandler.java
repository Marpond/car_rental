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

    public void closeConnection(){
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

}

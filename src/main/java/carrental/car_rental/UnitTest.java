package carrental.car_rental;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UnitTest {

    DatabaseHandler db = DatabaseHandler.getInstance();

    @Test
    public void testDatabaseConstructor() {
        DatabaseHandler.DBConstructor();
        Assert.assertNotNull(DatabaseHandler.getInstance());
    }

    @Test
    public void testDatabaseSingleton() {
        DatabaseHandler db1 = DatabaseHandler.getInstance();
        DatabaseHandler db2 = DatabaseHandler.getInstance();
        Assert.assertEquals(db1, db2);
    }

    @Test
    public void testGetCamperLicensePlates() {
        List<String> expected = List.of("LP001", "LP002", "LP003", "LP004", "LP005", "LP006", "LP007", "LP008", "LP009", "LP010", "LP011", "LP012");
        List<String> actual = db.getCamperLicensePlates();
        Assert.assertEquals(expected, actual);
    }
}

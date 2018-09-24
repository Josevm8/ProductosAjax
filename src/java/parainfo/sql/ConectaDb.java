package parainfo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author parainformaticos.com
 */
public class ConectaDb {

    private String database;

    public Connection getConnection() {
        Connection cn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database,
                    "root", "56789");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        return cn;
    }

    public ConectaDb() {
        this.database = "prod";
    }

    public ConectaDb(String database) {
        this.database = database;
    }
}


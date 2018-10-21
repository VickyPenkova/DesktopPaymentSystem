package sample.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    //Declare JDBC Driver
    private static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    //Connection
    private static Connection conn = null;

    private static final String connectionUrl = "jdbc:sqlserver://localhost:1433;database=DesktopPaymentSystem;integratedSecurity=true;";

    public void dbConnect() throws ClassNotFoundException, SQLException {
        //com.microsoft.sqlserver:mssql-jdbc:7.0.0.jre8

        //Setting SQL SERVER JDBC Driver
        try {
            Class.forName(SQLSERVER_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your SQL SERVER JDBC Driver?");
            e.printStackTrace();
            throw e;
        }

        System.out.println("SQL SERVER JDBC Driver Registered!");

        //Establish the SQL SERVER Connection using Connection String
        try {
            conn = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
        System.out.println("SQL SERVER Driver Registered!");
    }
}

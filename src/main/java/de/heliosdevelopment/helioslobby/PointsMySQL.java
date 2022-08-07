package de.heliosdevelopment.helioslobby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PointsMySQL {
    private final String user;
    private final String database;
    private final String password;
    private final int port;
    private final String host;

    private Connection connection;

    public PointsMySQL(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        connect();
        createTable();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
            System.out.println("[HeliosPointsAPI] Connected to mysql databse.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        updateSQL(
                "CREATE TABLE IF NOT EXISTS `heliospoints` (`player_uuid` VARCHAR(40) NOT NULL,`points` int(20) NOT NULL,PRIMARY KEY (`player_uuid`)) ");
    }

    public void setPoints(String uuid, int points) {
        if (getInt(uuid) == -1)
            updateSQL("INSERT INTO `heliospoints` (`player_uuid`, `points`) VALUES ('" + uuid + "', '" + points + "')");
        else
            updateSQL("UPDATE `heliospoints` SET `points`='" + points + "' WHERE `player_uuid`='" + uuid + "'");
    }

    public int getInt(String uuid) {
        int back = -1;
        try {
            ResultSet rs = select("SELECT * FROM `heliospoints` WHERE `player_uuid` = '" + uuid + "'");
            assert rs != null;
            while (rs.next()) {
                back = rs.getInt("points");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return back;
    }

    private void updateSQL(String statment) {

        // create a Statement from the connection
        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(statment);
        } catch (SQLException e) {
            // connect();
            // updateSQL(statment);
        }
    }

    private ResultSet select(String sql) {

        if (connection != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {

                query = connection.createStatement();
                return query.executeQuery(sql);

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}

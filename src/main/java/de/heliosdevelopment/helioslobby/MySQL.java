package de.heliosdevelopment.helioslobby;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.heliosdevelopment.sqlconnector.SQLClient;
import de.heliosdevelopment.sqlconnector.SQLInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.Visibility;

public class MySQL {

    private final SQLClient client;

    public MySQL() throws Exception {
        SQLInfo sqlInfo = new SQLInfo(
                "127.0.0.1", 3306, "lobby", "root", "1v5z6j7c");
        this.client = new SQLClient(sqlInfo, "com.mysql.jdbc.Driver", "jdbc:mysql", 1);
        createTable();
    }

    /*private void connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Lobby Treiber nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Lobby Verbindung nicht möglich");
            System.out.println("Lobby SQLException: " + e.getMessage());
            System.out.println("Lobby SQLState: " + e.getSQLState());
            System.out.println("Lobby VendorError: " + e.getErrorCode());
        }

    }*/

    public void close() {
            try {
                client.getConnection().close();
            } catch (SQLException e) {
                System.out.println("Lobby Fehler: " + e.getMessage());
            }

    }

    /*public boolean isConnected() {
        return connection != null;
    }*/

    private void createTable() {
        updateSQL("CREATE TABLE IF NOT EXISTS `locations` ( `name` VARCHAR(99) NOT NULL,\n"
                + "  `world` text NOT NULL, `x` double NOT NULL, `y` double NOT NULL,\n"
                + "  `z` double NOT NULL, `yaw` float NOT NULL, `pitch` float NOT NULL)"
                + " ENGINE=InnoDB DEFAULT CHARSET=latin1;");

        updateSQL("CREATE TABLE IF NOT EXISTS `cosmetics` (`uuid` VARCHAR(99) NOT NULL,\n"
                + "  `petId` INT(10) NOT NULL, `petName` VARCHAR(99) NOT NULL, `petAge` INT(10) NOT NULL,\n"
                + "  `particleId` INT(10) NOT NULL, `headId` INT(10) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        updateSQL("CREATE TABLE IF NOT EXISTS `playerLocation` ( `uuid` VARCHAR(99) NOT NULL,\n"
                + "  `world` text NOT NULL, `x` double NOT NULL, `y` double NOT NULL,\n"
                + "  `z` double NOT NULL, `yaw` float NOT NULL, `pitch` float NOT NULL)"
                + " ENGINE=InnoDB DEFAULT CHARSET=latin1;");

        updateSQL("CREATE TABLE IF NOT EXISTS `players` ( `uuid` VARCHAR(99) NOT NULL,\n"
                + "  `visibility` VARCHAR(20) NOT NULL)"
                + " ENGINE=InnoDB DEFAULT CHARSET=latin1;");


    }

    private void updateSQL(String statment) {
        try (Connection connection = client.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(statment);
        } catch (SQLException e) {
            System.out.println("Lobby MySQL-UpdateSQL:" + e.getMessage());
        }
    }

    private ResultSet select(String statement) {
        try (Connection connection = client.getConnection()) {
            Statement query = connection.createStatement();
            return query.executeQuery(statement);
        } catch (SQLException e) {
            System.out.println("Lobby MySQL-Select:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Location getSpawn(String name) {
        ResultSet result = select("SELECT * FROM `locations` WHERE `name` = '" + name + "'");
        Location loc = null;
        if (result == null) {
            return loc;
        }

        try {
            while (result.next()) {

                loc = new Location(Bukkit.getWorld(result.getString("world")), result.getDouble("x"),
                        result.getDouble("y"), result.getDouble("z"));

                loc.setPitch(result.getFloat("pitch"));
                loc.setYaw(result.getFloat("yaw"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return loc;

    }

    public LobbyPlayer getLobbyPlayer(String uuid) {
        ResultSet result = select("SELECT * FROM `players` WHERE `uuid` = '" + uuid + "'");
        LobbyPlayer player = null;
        if (result == null) {
            return player;
        }

        try {
            while (result.next()) {
                player = new LobbyPlayer(UUID.fromString(result.getString("uuid")), Visibility.valueOf(result.getString("visibility")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return player;

    }

    public void addPlayer(String uuid, Visibility visibility) {
        if (getLobbyPlayer(uuid) != null) {
            updateSQL("UPDATE `players` SET `visibility`='" + visibility.toString() + "' WHERE `uuid`='" + uuid + "'");
        } else {
            updateSQL("INSERT INTO `players` (`uuid`, `visibility`) VALUES ('" + uuid
                    + "', '" + visibility.toString() + "')");
        }
    }

    public Location getLocation(String uuid) {
        ResultSet result = select("SELECT * FROM `playerLocation` WHERE `uuid` = '" + uuid + "'");
        Location loc = null;
        if (result == null) {
            return loc;
        }

        try {
            while (result.next()) {

                loc = new Location(Bukkit.getWorld(result.getString("world")), result.getDouble("x"),
                        result.getDouble("y"), result.getDouble("z"));

                loc.setPitch(result.getFloat("pitch"));
                loc.setYaw(result.getFloat("yaw"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return loc;

    }

    public void addPlayerLocation(String uuid, Location loc) {
        if (getLocation(uuid) != null) {
            updateSQL("UPDATE `playerLocation` SET `world`='" + loc.getWorld().getName() + "',`x`='" + loc.getX()
                    + "', `y`='" + loc.getY() + "', `z`='" + loc.getZ() + "', `yaw`='" + loc.getYaw() + "', `pitch`='"
                    + loc.getPitch() + "' WHERE `uuid`='" + uuid + "'");
        } else {
            updateSQL("INSERT INTO `playerLocation` (`uuid`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUES ('" + uuid
                    + "', '" + loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ()
                    + "', '" + loc.getYaw() + "','" + loc.getPitch() + "')");
        }
    }

    public void addSpawn(String name, Location loc) {
        if (getSpawn(name) != null) {
            updateSQL("UPDATE `locations` SET `world`='" + loc.getWorld().getName() + "',`x`='" + loc.getX()
                    + "', `y`='" + loc.getY() + "', `z`='" + loc.getZ() + "', `yaw`='" + loc.getYaw() + "', `pitch`='"
                    + loc.getPitch() + "' WHERE `name`='" + name + "'");
        } else {
            updateSQL("INSERT INTO `locations` (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUES ('" + name
                    + "', '" + loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ()
                    + "', '" + loc.getYaw() + "','" + loc.getPitch() + "')");
        }
    }

    public void removeSpawn(String name) {
        updateSQL("DELETE FROM `locations` WHERE `name`='" + name + "'");
    }

    public void insertCosmetics(String uuid, int petId, String petName, int petAge, int particleId, int headId) {
        updateSQL("INSERT INTO `cosmetics` (`uuid`, `petId`, `petName`, `petAge`, `particleId`, `headId`) "
                + " VALUES ('" + uuid + "', '" + petId + "', '" + petName + "', '" + petAge + "', '" + particleId
                + "', '" + headId + "')");
    }

    public void updateCosmetics(String uuid, int petId, String petName, int petAge, int particleId, int headId) {
        updateSQL("UPDATE `cosmetics` SET `petId`='" + petId + "', `petName`='" + petName + "',`petAge`='" + petAge
                + "',`particleId`='" + particleId + "',`headId`='" + headId + "' WHERE `uuid`='" + uuid + "'");
    }

    public List<Object> getCosmetics(String uuid) {
        ResultSet result = select("SELECT * FROM `cosmetics` WHERE `uuid` = '" + uuid + "'");
        List<Object> cosmetics = new ArrayList<>();
        if (result == null) {
            return cosmetics;
        }
        try {
            while (result.next()) {
                cosmetics.add(result.getInt("petId"));
                cosmetics.add(result.getString("petName"));
                cosmetics.add(result.getInt("petAge"));
                cosmetics.add(result.getInt("particleId"));
                cosmetics.add(result.getInt("headId"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cosmetics;
    }
}

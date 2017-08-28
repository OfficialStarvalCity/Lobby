package de.heliosdevelopment.helioslobby;

import java.sql.*;
import java.util.*;

import de.heliosdevelopment.helioslobby.friends.FriendPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.Visibility;
import org.bukkit.entity.Player;

public class MySQL {

    //private final SQLClient client;
    private Connection connection;

    MySQL() throws Exception {
       /* SQLInfo sqlInfo = new SQLInfo(
                "localhost", 3306, "lobby", "root", "1v5z6j7c");
        this.client = new SQLClient(sqlInfo, "com.mysql.jdbc.Driver", "jdbc:mysql", 1);*/
        connect();
        createTable();
    }

    private void connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bungee?autoReconnect=true", "root", "1v5z6j7c");
        } catch (ClassNotFoundException e) {
            System.out.println("Lobby Treiber nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Lobby Verbindung nicht möglich");
            System.out.println("Lobby SQLException: " + e.getMessage());
            System.out.println("Lobby SQLState: " + e.getSQLState());
            System.out.println("Lobby VendorError: " + e.getErrorCode());
        }

    }

    public void close() {
        try {
            // client.doShutdown();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lobby Fehler: " + e.getMessage());
        }

    }

    public boolean isConnected() {
        return connection != null;
    }

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

        updateSQL("CREATE TABLE IF NOT EXISTS `players` ( `uuid` VARCHAR(99) NOT NULL, `name` VARCHAR(30) NOT NULL, \n"
                + "  `visibility` VARCHAR(20) NOT NULL, `cosmetics` text NOT NULL, `lastDailyReward` VARCHAR(99) NOT NULL, `lastPremiumReward` VARCHAR(99) NOT NULL)"
                + " ENGINE=InnoDB DEFAULT CHARSET=latin1;");


    }

    private void updateSQL(String statment) {
        // try {
        //   Connection connection = client.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(statment);
        } catch (SQLException e) {
            System.out.println("Lobby MySQL-UpdateSQL:" + e.getMessage());
        }
    }

    private ResultSet select(String statement) {
        //  try {
        // Connection connection = client.getConnection();
        try {
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
            return null;
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
            return null;
        }

        try {
            while (result.next()) {
                Set<Integer> cosmetics = new HashSet<>();
                for (String s : result.getString("cosmetics").split(";")) {
                    if (!s.equals(""))
                        cosmetics.add(Integer.valueOf(s));
                }
                player = new LobbyPlayer(UUID.fromString(result.getString("uuid")),
                        result.getString("name"), Visibility.valueOf(result.getString("visibility")),
                        cosmetics, Long.valueOf(result.getString("lastDailyReward")),
                        Long.valueOf(result.getString("lastPremiumReward")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return player;

    }

    public void addPlayer(String uuid, String name, Visibility visibility, Set<Integer> cosmetics, long lastDailyReward, long lastPremiumReward) {
        StringBuilder cosmeticString = new StringBuilder();
        for (Integer cosmeticInteger : cosmetics)
            cosmeticString.append(cosmeticInteger.toString()).append(";");
        if (getLobbyPlayer(uuid) != null) {
            updateSQL("UPDATE `players` SET `name`='" + name + "', `visibility`='" + visibility.toString() + "', `cosmetics`='" + cosmeticString + "', `lastDailyReward`='" + String.valueOf(lastDailyReward) + "', `lastPremiumReward`='" + String.valueOf(lastPremiumReward) + "' WHERE `uuid`='" + uuid + "'");

        } else {
            updateSQL("INSERT INTO `players` (`uuid`, `name`, `visibility`, `cosmetics`, `lastDailyReward`, `lastPremiumReward`) VALUES ('" + uuid + "', '" + name
                    + "', '" + visibility.toString() + "', '" + cosmeticString + "', '" + String.valueOf(lastDailyReward) + "', '" + String.valueOf(lastPremiumReward) + "')");
        }
    }

    public UUID getUuid(String name) {
        return null;
    }

    public String getPlayerName(UUID uuid) {
        return null;
    }

    public Location getLocation(String uuid) {
        ResultSet result = select("SELECT * FROM `playerLocation` WHERE `uuid` = '" + uuid + "'");
        Location loc = null;
        if (result == null) {
            return null;
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

    public FriendPlayer getFriendPlayer(Player player) {
        ResultSet result = select("SELECT `playerFriends` FROM `friends` WHERE `uuid` = '" + player.getUniqueId() + "'");
        List<Object> cosmetics = new ArrayList<>();
        if (result == null) {
            return null;
        }
        try {
            if (result.next()) {
                List<String> friends = new ArrayList<>();
                String friendsResult = result.getString("playerFriends");
                if (friendsResult != null)
                    for (String s : friendsResult.split(";"))
                        friends.add(getName(UUID.fromString(s)));

                return new FriendPlayer(player, friends);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public String getName(UUID uuid) {
        ResultSet result = select("SELECT `name` FROM `friends` WHERE `uuid` = '" + uuid.toString() + "'");
        if (result == null) {
            return null;
        }

        try {
            result.next();
            return result.getString("name");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}

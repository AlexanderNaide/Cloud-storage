package com.gb;

import com.gb.classes.command.UserConnect;
import com.gb.classes.command.UserCreate;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class OperatorBD {
    private static Statement statement;
    private static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\GVoichuk\\IdeaProjects\\Cloud-Storage\\server\\src\\main\\resources\\CloudStorageDB.db");
//            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\al121\\IdeaProjects\\Cloud-Storage\\server\\src\\main\\resources\\CloudStorageDB.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            log.error("e=", e);
        }
    }

    public static void userConnections(ChannelHandlerContext channel, UserConnect userConnect) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select userID from users where login = ? and password = ?");
            preparedStatement.setString(1, userConnect.getLogin());
            preparedStatement.setString(2, userConnect.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            int userID = resultSet.getInt(1);
            String ch = String.valueOf(channel.channel().id());

            statement.executeUpdate(String.format("insert into connect (userID, channelID) values (%d, '%s')", userID, ch));


/*            if (userID != 0) {
                System.out.println("userID = " + userID);
                ResultSet rs = statement.executeQuery(String.format("select channelID from connect where userID = %d", userID));
                System.out.println(rs.getInt(1));

                if (rs.getInt(1) != 0){
                    statement.executeUpdate(String.format("insert into connect (userID, channelID) values (%d, '%s')", userID, ch));
                    System.out.println("Этот юзер залогинен");
                    return rs.getString(1);
                } else {
                    statement.executeUpdate(String.format("insert into connect (userID, channelID) values (%d, '%s')", userID, ch));
                    System.out.println("Этот юзер НЕзалогинен");
                    return "0";
                }
            }*/


        } catch (SQLException e) {
            log.error("e=", e);
        }
//        return null;
    }

    public static String userCatalog(ChannelHandlerContext channel) {
        try {
            String ch = String.valueOf(channel.channel().id());
            ResultSet resultSet = statement.executeQuery(String.format("select login" +
                    "    from users as u " +
                    "    join connect as c" +
                    "    on u.userID = c.userID" +
                    "    where channelID = '%s'", ch));
            return resultSet.getString(1);
        } catch (SQLException e) {
            log.error("e=", e);
        }
        return null;
    }

    public static String userRootCatalog(ChannelHandlerContext channel) {
        try {
            String ch = String.valueOf(channel.channel().id());
            ResultSet resultSet = statement.executeQuery(String.format("select directory" +
                    "    from users as u " +
                    "    join connect as c" +
                    "    on u.userID = c.userID" +
                    "    where channelID = '%s'", ch));
            return resultSet.getString(1);
        } catch (SQLException e) {
            log.error("e=", e);
        }
        return null;
    }

    public static void clearAllConnects() {
        try {
            statement.execute("delete from connect");
        } catch (SQLException e) {
            log.error("e=", e);
        }
    }

    public static boolean newUser(UserCreate userCreate, String root) {
        try {
            PreparedStatement isFree = connection.prepareStatement("select count (*) from users where login = ?");
            isFree.setString(1, userCreate.getLogin());
            ResultSet resultIsFree = isFree.executeQuery();
            int userID = resultIsFree.getInt(1);
            if (userID == 0) {
                PreparedStatement prepareStatement = connection.prepareStatement("insert into users (login, password, directory) values (?, ?, ?)");
                prepareStatement.setString(1, userCreate.getLogin());
                prepareStatement.setString(2, userCreate.getPassword());
                prepareStatement.setString(3, root);
                prepareStatement.execute();
                return true;
            }
        } catch (SQLException e) {
            log.error("e=", e);
        }
        return false;
    }

    public static void clearConnect(ChannelHandlerContext channel) {
        String ch = String.valueOf(channel.channel().id());
        try {
            statement.execute(String.format("delete from connect where channelID = '%s'", ch));
        } catch (SQLException e) {
            log.error("e=", e);
        }
    }


}

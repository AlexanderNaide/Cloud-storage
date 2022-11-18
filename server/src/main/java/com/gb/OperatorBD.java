package com.gb;

import com.gb.classes.command.UserConnect;
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
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            log.error("e=", e);
        }
    }

    public static boolean userConnections(ChannelHandlerContext channel, UserConnect userConnect){
        try {
            PreparedStatement preparedStatement = preparedStatement = connection.prepareStatement("select userID from users where login = ? and password = ?");
            preparedStatement.setString(1, userConnect.getLogin());
            preparedStatement.setString(2, userConnect.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            int userID = resultSet.getInt(1);
            String ch = String.valueOf(channel.channel().id());
            if (userID != 0){
                statement.executeUpdate(String.format("insert into connect (userID, channelID) values (%d, '%s')", userID, ch));
                return true;
            }
        } catch (SQLException e) {
            log.error("e=", e);
        }
        return false;
    }

    public static String userCatalog(ChannelHandlerContext channel){
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

    public static void clearAllConnects(){
        try {
            statement.execute("delete from connect");
        } catch (SQLException e) {
            log.error("e=", e);
        }
    }

    public static void clearConnect(ChannelHandlerContext channel){
        String ch = String.valueOf(channel.channel().id());
        try {
            statement.execute(String.format("delete from connect where channelID = '%s'", ch));
        } catch (SQLException e) {
            log.error("e=", e);
        }
    }


}

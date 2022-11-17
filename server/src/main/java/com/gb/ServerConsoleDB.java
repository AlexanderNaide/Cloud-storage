package com.gb;

import java.sql.*;

public class ServerConsoleDB {
    private static Statement statement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();

        statement.executeUpdate("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='users'"); // обнулить автоинкремент
        statement.executeUpdate("insert into users (login, password, directory) values ('log1', 'pass1', 'user1')");
        ResultSet resultSet = statement.executeQuery("select * from users where login = 'log1'");
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + resultSet.getString(2) + resultSet.getString(3));
        }


    }

    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\GVoichuk\\IdeaProjects\\Cloud-Storage\\server\\src\\main\\resources\\CloudStorageDB.db");
        statement = connection.createStatement();
    }
}

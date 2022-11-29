package com.gb;

import java.sql.*;

public class ServerConsoleDB {
    private static Statement statement;
    private static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();

//        statement.executeUpdate("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='users'"); // обнулить автоинкремент
//        statement.executeUpdate("insert into users (login, password, directory) values ('log1', 'pass1', 'user1')");
        ResultSet resultSet = statement.executeQuery("select * from users where login = 'log1'");
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + resultSet.getString(2) + resultSet.getString(3));
        }

        System.out.println("*****************");

        String l = "log1";
        String p = "pass1";

        PreparedStatement preparedStatement = connection.prepareStatement("select directory from users where login = ? and password = ?");
        preparedStatement.setString(1, l);
        preparedStatement.setString(2, p);
        ResultSet resultSet2 = preparedStatement.executeQuery();

//        System.out.println(resultSet2.getString(1));

        do {
            System.out.println("555");
            System.out.println(resultSet2.getString(1));
        } while (resultSet2.next());

        preparedStatement = connection.prepareStatement("select userID from users where login = ? and password = ?");
        preparedStatement.setString(1, "log1");
            preparedStatement.setString(2, "pass1");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        int userID = resultSet.next().getInt(1);
//        System.out.println(userID);


    }

    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
//        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\GVoichuk\\IdeaProjects\\Cloud-Storage\\server\\src\\main\\resources\\CloudStorageDB.db");
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\al121\\IdeaProjects\\Cloud-Storage\\server\\src\\main\\resources\\CloudStorageDB.db");
        statement = connection.createStatement();
    }
}

package com.example.vocabularybook.util;

public class AppWords {
    public static String SQL_CREATE_DATABASE =
            "create table note(id INTEGER PRIMARY KEY AUTOINCREMENT, English VARCHAR(256), Chinese VARCHAR(256), time varchar(20))";
    public static String SQL_DROP_DATABASE = "drop table note";
    public static String SQL_DELETE_DATABASE = "delete from note where English = ?";
    public static String SQL_INSERT_DATABASE = "insert into note(English,Chinese,time) values(?,?,?)";
    public static String SQL_UPDATE_DATABASE = "update note set Chinese = ? where id = ?";
    public static String SQL_SELECT_ALL = "select * from note";
}

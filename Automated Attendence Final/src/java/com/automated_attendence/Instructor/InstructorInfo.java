/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automated_attendence.Instructor;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author shuvo
 */
public class InstructorInfo {

    java.sql.Connection connection;
    Statement statement;
    ResultSet resultset;
    public static int count = 0;
    public static String query, name;

    public InstructorInfo() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public String InstructorName(String id, String password) throws SQLException {
        statement = connection.createStatement();
        resultset = statement.executeQuery(String.format("select name from inst_info where inst_id='%s' and pass='%s'",id,password));
        if(resultset.next()){
        name=resultset.getString("name");
        }
        return name;
    }
    public String InstructorDept(String id) throws SQLException{
        String dept=null;
        statement = connection.createStatement();
        resultset = statement.executeQuery(String.format("select dept from inst_info where inst_id='%s' ",id));
        if(resultset.next()){
        dept=resultset.getString("dept");
        }
        return dept;
    }

}

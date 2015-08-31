/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shuvo
 */
package com.automated_attendence.student;

import static com.automated_attendence.services.services.course;
import static com.automated_attendence.services.services.dept;
import static com.automated_attendence.services.services.year_id;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class student {

    java.sql.Connection connection;
    Statement statement;
    ResultSet resultset;
    public static String dept, course;
    public String[] result = new String[1000];
    public static int i, year_id, year;
    public static int count = 0;
    public static String query;

    public student() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public float[] attendence(String course, int year_id,String semester_id,String dept, int month, int year) throws SQLException {
        float[] data = new float[36];
        float[] id = new float[36];
        for (int i = 0; i < 36; ++i) {
            data[i] = 0;
        }
        query = String.format("SELECT count(*) as total,day,month from std_time where course_id like '%s' and year_id=%d and semester_id  like '%s' and dept like '%s' and month=%d and year=%d group by day,month,year", course, year_id,semester_id, dept, month, year);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        count = 0;
        while (resultset.next()) {
            data[resultset.getInt("day")] += resultset.getInt("total");
        }
        return data;
    }

    public int registered(String course, int year_id, String dept) throws SQLException {
        int n = 0;
        query = String.format("SELECT count(*) as total from registered_std_info where course_id like '%s' and year_id=%d ", course, year_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        while (resultset.next()) {
            n = resultset.getInt("total");
        }
        return n;
    }
}

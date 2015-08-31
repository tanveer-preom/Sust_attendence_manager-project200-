/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shuvo
 */
package com.automated_attendence.Instructor;

import static com.automated_attendence.services.services.course;
import static com.automated_attendence.services.services.dept;
import static com.automated_attendence.services.services.year_id;
import static com.automated_attendence.student.student.count;
import static com.automated_attendence.student.student.query;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class PrevClass {

    java.sql.Connection connection;
    Statement statement;
    ResultSet resultset;
    public String[] result = new String[1000];
    public static int i;
    public static int count = 0;
    public static String query;
    public static String course_id, semester_id,dept;
    public static int year_id, day, month, year,class_id;

    public PrevClass() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public int informationlast(String inst_id) throws SQLException {
        query = String.format("SELECT * from inst_time where course_id in (select course_id from inst_teach where inst_id='%s') order by year,month,day,id", inst_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        while (resultset.next()) {
            day = resultset.getInt("day");
            month = resultset.getInt("month");
            year = resultset.getInt("year");
            dept = resultset.getString("accepting_dept");
            course_id = resultset.getString("course_id");
            semester_id = resultset.getString("semester_id");
            year_id = resultset.getInt("year_id");
            class_id = resultset.getInt("id");
        }
        int total = 0;
        query = String.format("SELECT count(*) as total from std_time where success=1 and course_id like '%s' and year_id=%d and semester_id like '%s' and day=%d and month=%d and"
                + " year=%d and id= %d ", course_id, year_id, semester_id, day, month, year,class_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        if (resultset.next()) {
            total =resultset.getInt("total");
        }
        return total;

    }
    
     public int informationfirst(String inst_id) throws SQLException {
        query = String.format("SELECT * from inst_time where course_id like '%s' and accepting_dept like '%s' and"
                + " semester_id like '%s' and year_id=%d order by year,month,day,id",course_id,dept,semester_id,year_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        while (resultset.next()) {
            day = resultset.getInt("day");
            month = resultset.getInt("month");
            year = resultset.getInt("year");
            dept = resultset.getString("accepting_dept");
            course_id = resultset.getString("course_id");
            semester_id = resultset.getString("semester_id");
            year_id = resultset.getInt("year_id");
            class_id = resultset.getInt("id");
            break;
        }
        int total = 0;
        query = String.format("SELECT count(*) as total from std_time where success=1 and course_id like '%s' and year_id=%d and semester_id like '%s' and day=%d and month=%d and"
                + " year=%d and id= %d ", course_id, year_id, semester_id, day, month, year,class_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        if (resultset.next()) {
            total =resultset.getInt("total");
        }
        return total;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shuvo
 */
package com.automated_attendence.services;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class services {

    java.sql.Connection connection;
    Statement statement;
    ResultSet resultset;
    public static String inst_id, inst_pass, dept, course;
    public String[] result = new String[1000];
    public String[] courses = new String[1000];
    public static String fulldept[] =new String[1000];
    public static int i, year_id, year;
    public static String query;

    public services() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public boolean login(String id, String pass) throws SQLException {
        inst_id = id;
        inst_pass = pass;
        statement = connection.createStatement();
        query = String.format("SELECT * from inst_info where inst_id='%s' and pass='%s'", id, pass);
        resultset = statement.executeQuery(query);
        while (resultset.next()) {
            return true;
        }
        return false;
    }

    public String[] courses(String id,int year_id,String semester_id, String dept) throws SQLException {
        inst_id=id;
        query = String.format("SELECT * from inst_teach where inst_id='%s'  and year_id=%d and semester_id like '%s' and accepting_dept='%s'",id,  year_id,semester_id, dept);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        i = 0;
        while (resultset.next()) {
             courses[i] = resultset.getString("course_id");
            ++i;
        }
        return  courses;
    }
    
    public String[] semester() throws SQLException {

        query = String.format("SELECT distinct(semester_id) from inst_teach");
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        i = 0;
        String[] semester=new String[10];
        while (resultset.next()) {
             semester[i] = resultset.getString("semester_id");
            ++i;
        }
        return  semester;
    }

    public String[] department() throws SQLException {
        query = String.format("SELECT * from department");
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        i = 0;
        while (resultset.next()) {
            result[i] = resultset.getString("dept");
            fulldept[i] = resultset.getString("dept_name");
            ++i;
        }
        return result;
    }

    public int[] monthlimit(String course, int year_id,String semester_id, String dept) throws SQLException {
        int i;
        int[] data = new int[100];
        i = 0;
        services.course = course;
        services.year_id = year_id;
        services.dept = dept;
        query = String.format("SELECT * from inst_time where course_id like '%s' and year_id=%d and semester_id like '%s' and accepting_dept like '%s'  order by year,month", course, year_id,semester_id,dept);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        while (resultset.next()) {
            if (i == 0) {
                data[0] = resultset.getInt("month");
                data[1] = resultset.getInt("year");
            }
            data[2] = resultset.getInt("month");
            data[3] = resultset.getInt("year");
            ++i;
        }
        return data;
    }
}

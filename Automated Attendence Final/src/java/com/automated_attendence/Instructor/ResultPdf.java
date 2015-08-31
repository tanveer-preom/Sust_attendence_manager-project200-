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
//import com.itextpdf.text.*;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class ResultPdf {

    java.sql.Connection connection;
    Statement statement, statement1;
    ResultSet resultset, resultset1;
    public static String dept, course;
    public String[] result = new String[1000];
    public static int i, year_id, year, tclass = 0;
    public static int count = 0;
    public static String query;

    public static String _reg[] = new String[100000];
    public static int _marks[] = new int[100000];

    public ResultPdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public int publishpdf(String dept, String course, int year_id,String semester_id) throws SQLException {
        String table1;
        table1 = String.format("%s_%s_%s_%s", dept, course, year_id,semester_id);
        table1 = table1.replaceAll("\\s", "");
        String rstring = String.format("select reg_no,count(*) as marks from std_time natural join registered_std_info where success!=0 and course_id='%s' and year_id=%d and semester_id='%s' and dept='%s' group by reg_no order by reg_no", course, year_id,semester_id, dept);

        String create = String.format("create table %s "
                + "(reg_no varchar(32), "
                + "marks integer)", table1);
        statement = connection.createStatement();
        statement1 = connection.createStatement();
        try {
            resultset = statement.executeQuery(create);

        } catch (Exception err) {

        }
        String s = String.format("select count(*) as totals from inst_time where course_id='%s' and year_id=%d and accepting_dept='%s' group by course_id",course,year_id,dept);
        resultset = statement.executeQuery(s);
        if (resultset.next()) {
            tclass = resultset.getInt("totals");
        }
        resultset = statement.executeQuery(String.format("delete %s", table1));
        resultset = statement.executeQuery(String.format("(select reg_no from registered_std_info where course_id='%s' and year_id=%d and semester_id='%s')intersect(select reg_no from student_info where dept='%s')", course, year_id,semester_id,dept));
        while (resultset.next()) {
            resultset1 = statement1.executeQuery(String.format("insert into %s (reg_no,marks) values('%s',0)", table1, resultset.getString("reg_no")));
        }
        resultset = statement.executeQuery(rstring);
        while (resultset.next()) {
            statement1.executeQuery(String.format("Update %s set marks=%d where reg_no='%s'", table1, resultset.getInt("marks"), resultset.getString("reg_no")));
        }
        int i = -1;
        resultset = statement.executeQuery(String.format("select * from %s order by reg_no", table1));
        while (resultset.next()) {
            ++i;
            _reg[i] = resultset.getString("reg_no");
            _marks[i] = resultset.getInt("marks");
        }
        return i;
    }

}

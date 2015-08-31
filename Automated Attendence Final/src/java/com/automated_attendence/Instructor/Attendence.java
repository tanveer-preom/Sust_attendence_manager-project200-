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

public class Attendence {

    java.sql.Connection connection;
    Statement statement;
    ResultSet resultset;
    public static String dept, course, semester_id;
    public String[] result = new String[1000];
    public static int i, year_id, year,max_id;
    public static int count = 0;
    public static String query;

    public static String _reg[] = new String[100000];
    public static String _dept[] = new String[100000];
    public static int _id[] = new int[100000];
    public static String _course[] = new String[10000];
    public static int _day[] = new int[100000];
    public static int _month[] = new int[100000];
    public static int _year[] = new int[100000];
    public static int _yearid[] = new int[100000];
    public static int _done[] = new int[100000];
    public static int _late[] = new int[100000];
    public static int _latemin[] = new int[100000];
    public static int _success[] = new int[100000];
    public static int _delete[] = new int[100000];

    public Attendence() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
            DatabaseMetaData dbmd = connection.getMetaData();
        } catch (SQLException ex) {
        }
    }

    public void update(int total) throws SQLException {
        for (int i = 0; i <= total; ++i) {
            if (_delete[i] == 0) {
                query = String.format("UPDATE std_time set success=%d ,late=%d,done=%d,late_min=%d where semester_id like '%s' and id=%d and dept like '%s' and course_id like '%s' and year_id=%d and reg_no='%s' and day=%d and month=%d and year=%d ",
                        _success[i], _late[i], _done[i], _latemin[i], semester_id, _id[i], _dept[i], _course[i], _yearid[i], _reg[i], _day[i], _month[i], _year[i]);
            } else {
                query = String.format("DELETE from std_time where semester_id like '%s' and id=%d and dept like '%s' and course_id like '%s' and year_id=%d and reg_no='%s' and day=%d and month=%d and year=%d ",
                        semester_id, _id[i], _dept[i], _course[i], _yearid[i], _reg[i], _day[i], _month[i], _year[i]);
            }
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }
    }

    public void insert(int total) throws SQLException {
        for (int i = 0; i <=total; ++i) {
            if(_reg[i]!=null&&_id[i]!=-1){
            query = String.format("insert into std_time (id,reg_no,course_id,day,month,year,semester_id,year_id,dept,late,late_min,done,success) "
                    + "values(%d,'%s','%s',%d,%d,%d,'%s',%d,'%s',0,0,%d,%d)",_id[i],_reg[i],_course[i],_day[i],_month[i],_year[i],semester_id,_yearid[i],_dept[i],1,1);
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            }
        }
    }

    public String[] studentlist(String course, int year_id, String semester_id) throws SQLException {
        String[] data = new String[10000];
        int i = 0;
        query = String.format("SELECT * from registered_std_info where course_id like '%s' and year_id=%d and semester_id like '%s'order by reg_no ", course, year_id, semester_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        i = 0;
        while (resultset.next()) {
            ++i;
            data[i] = resultset.getString("reg_no");
        }
        data[0] = i + "";
        return data;
    }

    public int AddInstructorClass(String course, String semester_id, int year_id, String dept, int day, int month, int year) throws SQLException {
        query = String.format("select count(*) as total from inst_time where course_id like '%s' and day=%d and month=%d and year=%d and year_id=%d and accepting_dept like '%s' and semester_id like '%s'", course, day, month, year, year_id, dept, semester_id);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        int id = 1;
        if (resultset.next()) {
            id = resultset.getInt("total") + 1;
        }
        query = String.format("insert into inst_time (id,course_id,day,month,year,semester_id,year_id,accepting_dept) values(%d,'%s',%d,%d,%d,'%s',%d,'%s')", id, course, day, month, year, semester_id, year_id, dept);
        statement = connection.createStatement();
        statement.executeUpdate(query);
        return id;
    }

    public boolean AddStudentClass(int id, int total, String course, String semester_id, int year_id, String dept, int day, int month, int year, int[] attendence, String[] reg) throws SQLException {
        statement = connection.createStatement();
        for (int i = 1; i <= total; ++i) {
            query = String.format("insert into std_time (id,reg_no,course_id,day,month,year,semester_id,year_id,dept,late,late_min,done,success) "
                    + "values(%d,'%s','%s',%d,%d,%d,'%s',%d,'%s',0,0,%d,%d)", id, reg[i], course, day, month, year, semester_id, year_id, dept, attendence[i], attendence[i]);
            statement.executeUpdate(query);
        }
        return true;
    }

    public int statistics(String course, String semester_id, int year_id, String dept, int day, int month, int year) throws SQLException {
        Arrays.fill(_reg, null);
        Arrays.fill(_dept, null);
        Arrays.fill(_course, null);
        Arrays.fill(_day, 0);
        Arrays.fill(_month, 0);
        Arrays.fill(_year, 0);
        Arrays.fill(_yearid, 0);
        Arrays.fill(_done, 0);
        Arrays.fill(_late, 0);
        Arrays.fill(_latemin, 0);
        Arrays.fill(_success, 0);
        query = String.format("SELECT * from std_time where course_id like '%s' and semester_id like '%s' and year_id=%d and dept like '%s' and day=%d and month=%d and year=%d order by year,month,id,reg_no", course, semester_id, year_id, dept, day, month, year);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        count = -1;
        while (resultset.next()) {
            ++count;
            _reg[count] = resultset.getString("reg_no");
            _dept[count] = resultset.getString("dept");
            _id[count] = resultset.getInt("id");
            _course[count] = resultset.getString("course_id");
            _day[count] = resultset.getInt("day");
            _month[count] = resultset.getInt("month");
            _year[count] = resultset.getInt("year");
            _yearid[count] = resultset.getInt("year_id");
            _done[count] = resultset.getInt("done");
            _late[count] = resultset.getInt("late");
            _latemin[count] = resultset.getInt("late_min");
            _success[count] = resultset.getInt("success");
        }
        return count;
    }

    public void autoedit(String course, String dept, int year_id, String semester_id, int from_d, int from_m, int from_y, int to_d, int to_m, int to_y, int minute, int done) throws SQLException {
        query = String.format("UPDATE std_time set success=1 where (dept like '%s' and course_id like '%s' and year_id=%d and semester_id like '%s'and late_min<=%d and done=%d) and "
                + "(( day>=%d and month>=%d and year>=%d )or(month>%d and year>=%d )) and"
                + "(( day<=%d and month<=%d and year<=%d )or(month<%d and year<=%d )) ",
                dept, course, year_id, semester_id, minute, done, from_d, from_m, from_y, from_m, from_y, to_d, to_m, to_y, to_m, to_y
        );
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);

    }

    public int changes(String course, int class_id, String semester_id, int year_id, String dept, int day, int month, int year) throws SQLException {
        Arrays.fill(_reg, null);
        Arrays.fill(_dept, null);
        Arrays.fill(_course, null);
        Arrays.fill(_day, 0);
        Arrays.fill(_month, 0);
        Arrays.fill(_year, 0);
        Arrays.fill(_yearid, 0);
        Arrays.fill(_done, 0);
        Arrays.fill(_late, 0);
        Arrays.fill(_latemin, 0);
        Arrays.fill(_success, 0);
        query = String.format("SELECT * from std_time where course_id like '%s' and id=%d and semester_id like '%s' and year_id=%d and dept like '%s' and day=%d and month=%d and year=%d order by year,month,id,reg_no", course, class_id, semester_id, year_id, dept, day, month, year);
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
        count = -1;
        while (resultset.next()) {
            ++count;
            _reg[count] = resultset.getString("reg_no");
            _dept[count] = resultset.getString("dept");
            _id[count] = resultset.getInt("id");
            _course[count] = resultset.getString("course_id");
            _day[count] = resultset.getInt("day");
            _month[count] = resultset.getInt("month");
            _year[count] = resultset.getInt("year");
            _yearid[count] = resultset.getInt("year_id");
            _done[count] = resultset.getInt("done");
            _late[count] = resultset.getInt("late");
            _latemin[count] = resultset.getInt("late_min");
            _success[count] = resultset.getInt("success");
        }
        return count;
    }
}

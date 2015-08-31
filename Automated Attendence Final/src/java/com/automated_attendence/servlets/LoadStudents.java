/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automated_attendence.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author udoy
 */
public class LoadStudents extends HttpServlet {
  private Connection connection;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sust_attendance_db", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    /*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("pass");
        String course_id = req.getParameter("course_id");
        String year_id = req.getParameter("year_id");
        String semister_id = req.getParameter("semister_id");
        
        PrintWriter writer;
        writer =resp.getWriter();
        if(connection == null)
        {
            
            writer.print("Error in server");
        }
        else if(username==null || password == null || username.equals("")|| password.equals("")||
                course_id==null || course_id == null || year_id.equals("")|| year_id.equals("")
                   ||     semister_id==null || semister_id == null )
        {
            writer.println("Username or password or course_id or year_id or semister_id field is empty");
        }
        else
        {
            try {
                Statement statement = connection.createStatement();
                String query = "select inst_id,name,pass,dept_name from inst_info,department where inst_id = '"+username+"' and pass = '"+password+"' and inst_info.dept=department.dept";
                
                ResultSet result=statement.executeQuery(query);
                
                if(!Static_Functions.is_a_valid_login(result))
                {
                   throw new Throwable("Not a valid login");
                }
                
                query= "select * from registered_std_info";
                result=statement.executeQuery(query);
                String respond =(new JArrayConverter(result)).getRespond();
                writer.println(respond);
                writer.println(123);
                
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                writer.println(ex.getMessage());
            }
            
        }
        writer.close();
        //To change body of generated methods, choose Tools | Templates.
    }
    
    */
    
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("pass");
        String dept = req.getParameter("dept");
        String courseid= req.getParameter("course_id");
        String year_id =req.getParameter("year_id");
        String semester_id=req.getParameter("sem_id");
        String classid =req.getParameter("class_id");
        String day =req.getParameter("day");
        String month = req.getParameter("month");
        String year = req.getParameter("year");
        String give_all=req.getParameter("give_all");
        PrintWriter writer;
        writer =resp.getWriter();
        if(connection == null)
        {
            
            writer.print("Error in server");
        }
        else if(username==null || password == null || username.equals("")|| password.equals(""))
        {
            writer.println("Username or password field is empty");
        }
        else
        {
            try {
                Statement statement = connection.createStatement();
                String query = "select inst_id,name,pass,dept_name from inst_info,department where inst_id = '"+username+"' and pass = '"+password+"' and inst_info.dept=department.dept";
                ResultSet result=statement.executeQuery(query);
                query ="select * from registered_std_info where course_id='"+courseid+"' and year_id="+year_id+" and semester_id="+semester_id;
                result=statement.executeQuery(query);
                String success="0",done="0";
                if(give_all!=null)
                if(give_all.equals("yes"))
                {
                    success="1";
                    done="1";
                }
                while(result.next())
                {
                    Statement statement2 =connection.createStatement();
                    query="insert ignore into std_time(id,reg_no,course_id,day,month,year,year_id,semester_id,dept,success,done) values("+classid+",'"+result.getString("reg_no")+"','"+courseid+"',"+day+","+month+","+year+","+year_id+",'"+semester_id+"','"+dept+"',"+success+","+done+")";
                    statement2.executeUpdate(query);
                }
                
                
                query="select st.reg_no,name,session_no,late,done,success,late_min,year_id,semester_id,day,month,year,id,course_id from student_info st,std_time rst where st.reg_no=rst.reg_no and course_id='"+courseid+"' and year_id='"+year_id+"' and semester_id='"+semester_id+"' and rst.dept='"+dept+"' and id="+classid+" and day="+day+" and month= "+month+" and year="+year+" order by session_no desc,st.reg_no";
                result =statement.executeQuery(query);
                String respond =(new JArrayConverter(result)).getRespond();
                writer.println(respond);
                //writer.println(123);
                
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                writer.println(ex.getMessage());
            }
            
        }
        writer.close();
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("http://localhost:8080/Automated_Attendence_Final/login.jsp");
    }
    

    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    

}

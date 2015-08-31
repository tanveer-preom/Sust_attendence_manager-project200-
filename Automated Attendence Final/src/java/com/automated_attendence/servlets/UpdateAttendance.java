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
public class UpdateAttendance extends HttpServlet {

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

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("pass");
        String id= req.getParameter("id");
        String reg_no=req.getParameter("reg_no");
        String course_id=req.getParameter("course_id");
        String day=req.getParameter("day");
        String month=req.getParameter("month"); 
        String year=req.getParameter("year");
        String year_id= req.getParameter("year_id");
        String semester_id=req.getParameter("semester_id");
        String late=req.getParameter("late");
        String done=req.getParameter("done");
        String late_min=req.getParameter("late_min");
        String success=req.getParameter("success");
        
        
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
                // String query = "select reg_no,name from inst_info,department,student_info where inst_id = '"+username+"' and pass = '"+password+"' and inst_info.dept=department.dept and student_info.dept= department.dept";
                ResultSet result=statement.executeQuery(query);
                
                if(!Static_Functions.is_a_valid_login(result))
                {
                   throw new Throwable("Not a valid login");
                }
                 
                query= "update  std_time "
                        + "set " 
                        
                        
                        +"late='"+late+"', "
                        + "done= '"+done+"', "
                        + "late_min= '"+late_min+"', "
                        + "success= '"+success+"' "
                       
                        
                        + "where "
                        + "reg_no='"+reg_no+"' and "
                        + "id='"+id+"'  and  "
                        
                        + "course_id='"+course_id+"' and "
                        + "day='"+day+"' and "
                        + "month='"+month+"' and "
                        + "year= '"+year+"' and "
                        + "year_id= '"+year_id+"'  and " 
                        + "semester_id= '"+semester_id+"'";
                statement.executeUpdate(query);
                //String respond =(new JArrayConverter(result)).getRespond();
                writer.println("Success");
                
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
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
public class LoadClasses extends HttpServlet {

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
        String year_id=req.getParameter("year_id");
        String sem_id=req.getParameter("sem_id");
        String course_id =req.getParameter("course_id");
        String dept =req.getParameter("dept");
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
                query="select id,course_id,year,month,day,year_id,semester_id,accepting_dept from inst_time where year_id='"+year_id+"' and semester_id='"+sem_id+"' and course_id ='"+course_id+"' and accepting_dept='"+dept+"' order by year desc,month desc,day desc,id desc";
                result=statement.executeQuery(query);
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
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param  servlet request
     * @param  servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
}

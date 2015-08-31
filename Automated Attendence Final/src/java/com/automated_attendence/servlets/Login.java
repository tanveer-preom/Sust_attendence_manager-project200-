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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mesbah
 */
public class Login extends HttpServlet {
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
                String respond =(new JArrayConverter(result)).getRespond();
       
                query ="select distinct accepting_dept,dept_name from inst_teach,department where inst_id ='"+username+"' and dept= accepting_dept";
                result=statement.executeQuery(query);
                JSONArray respondArray =new JSONArray(respond);
                respond =(new JArrayConverter(result)).getRespond();
                JSONObject obj =new JSONObject();
                
                obj.put("Courses", new JSONArray(respond));
                respondArray.put(obj);
                writer.println(respondArray);
                //writer.println(123);
                
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                //writer.println(ex);
                ex.printStackTrace();
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
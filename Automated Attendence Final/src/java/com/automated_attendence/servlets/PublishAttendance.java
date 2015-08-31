/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automated_attendence.servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
 * @author tanveer
 */
public class PublishAttendance extends HttpServlet {
    private String student_reg_arrary[],student_present_day[];
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("pass");
        String course_id=req.getParameter("course_id");
        String year_id= req.getParameter("year_id");
        String semester_id=req.getParameter("semester_id");
        
        
        String dept=req.getParameter("dept");
        String considered_time=req.getParameter("considered_time");
        
       // PrintWriter writer;
       // writer =resp.getWriter();
        if(connection == null)
        {
            
         //   writer.print("Error in server");
        }
        else if(username==null || password == null || username.equals("")|| password.equals(""))
        {
        //    writer.println("Username or password field is empty");
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
                int total_class=0;
                String totalClass="";
                
                
                query= "select count( *) from inst_time where "
                        + "course_id='"+course_id+"' and "
                        + "year_id='"+year_id+"' and "
                        + "semester_id='"+semester_id+"' and "
                        + "accepting_dept= '"+dept+"'";
                
                Statement statement22 = connection.createStatement();
                ResultSet result3=statement22.executeQuery(query);
                if(result3.next()){
                //writer.println("totlal clss.... "+result3.getString(1));
                totalClass=result3.getString(1);
                total_class=Integer.parseInt(result3.getString(1));
                }
                //writer.println(considered_time+ " considered_time//// total class"+total_class);
                
                
                String registered_std_table_query= "SELECT distinct reg_no FROM `registered_std_info` where course_id='"+course_id+"' and year_id='"+year_id+"' and semester_id='"+semester_id+"'";
                
                query= "Select * from std_time where "
                        + "course_id='"+course_id+"' and "
                        + "year_id='"+year_id+"' and "
                        + "semester_id='"+semester_id+"' and "
                        + "dept= '"+dept+"' and "
                        + "late_min<= '"+considered_time+"' ";
                        
                 
                
                
                ResultSet registered_std_table=statement.executeQuery(registered_std_table_query);
                
                ResultSetMetaData rsmd2 = registered_std_table.getMetaData();
                int studentNumber = rsmd2.getColumnCount();
                int Total_students=0;
                 student_reg_arrary= new String[300];
                 student_present_day= new String[300];
                
                while (registered_std_table.next())
                {
                    Total_students++;
                    for(int i=1;i<=studentNumber;i++){
                    String reg_no = registered_std_table.getString(i);
                    
                    query= "Select count(*) as cnt from std_time where "
                        + "course_id='"+course_id+"' and "
                        + "reg_no='"+reg_no+"' and "
                        + "year_id='"+year_id+"' and "
                        + "semester_id='"+semester_id+"' and "
                        + "dept= '"+dept+"' and "
                        + "late_min<= '"+considered_time+"' ";
                    
                    Statement statement2 = connection.createStatement();
                    ResultSet result2=statement2.executeQuery(query);
                    //writer.println(""+reg_no+" count");
                    result2.next();
                    //writer.println("has........"+);
                    //writer.println("resul of "+reg_no+"  "+result2.getString(i));
                    student_reg_arrary[Total_students]=new String(reg_no);
                    student_present_day[Total_students]=new String(result2.getString(i));
                   // writer.println(i+"i  resul of "+ student_reg_arrary[i]+"  "+student_present_day[i]);
                    
                    }
                }
               /* result=statement.executeQuery(query);
                rsmd = result.getMetaData();
                
                int total=0;
                columnsNumber = rsmd.getColumnCount();
                while (result.next()) {
                  total++;
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) writer.print(",  ");
                        System.err.println("one");
                        String columnValue = result.getString(i);
                        writer.print(columnValue + " " + rsmd.getColumnName(i));
                    
                    }
                    writer.println("");
                }
                
                
                */
                //writer.println("......."+Total_students+"......");
                createPdf(course_id+"_"+dept+"_"+year_id+"_"+semester_id+".pdf", Total_students,course_id, dept, year_id, semester_id, total_class);
                //writer.println(" Success");
                
                OutputStream out = resp.getOutputStream();
                 FileInputStream in = new FileInputStream(course_id+"_"+dept+"_"+year_id+"_"+semester_id+".pdf");
                byte[] buffer = new byte[4096];
                    int length;
                while ((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                 }
                 in.close();
                 out.flush();
                //writer.println(123);
                
            } catch (SQLException ex) {
             //   writer.print(ex);
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
           //     writer.println(ex.getMessage());
            }
            
        }
       // writer.close();
        //To change body of generated methods, choose Tools | Templates.
    }

    
    

    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
     public void createPdf(String filename,int loop,String course,String dept,String year_id,String semester_id,int totalClass)
	throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        //document.add(new Paragraph("Hello World!"));
        // step 5
        document.addHeader("name", "yeamin");
        document.addTitle("Attendence");
        document.add(new Paragraph(String.format("              ResultSheet of Course id %s,Department %s,Year ID  %s,Semester ID %s :", course, dept, year_id, semester_id)));
        document.add(new Paragraph(String.format("\n                                                                Total Class : %d ",totalClass)));
        document.add(new Paragraph("      "));
        PdfPTable table = new PdfPTable(2);
        PdfPCell c1;

        c1 = new PdfPCell(new Phrase("Reg no"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Attendence"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        for (int i = 1; i <= loop; ++i) {
        //table.addCell(i+"");
        //table.addCell(i*i+"");
        table.addCell(student_reg_arrary[i]);
           // System.err.println(student_reg_arrary[i]);
            table.addCell(student_present_day[i]);
           // System.err.println(student_present_day[i]);
             
        }
        //document.addAuthor("me");
        document.add(table);
        document.close();
    }
    
}

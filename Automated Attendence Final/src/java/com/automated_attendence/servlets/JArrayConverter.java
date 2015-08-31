/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automated_attendence.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mesbah
 */
public class JArrayConverter{
    private JSONArray respondArray;
    private JSONObject temp;
    private ResultSet result;
    private ResultSetMetaData metadata;
    private boolean wrongChecker;
    public JArrayConverter(ResultSet result) throws SQLException
    {
        this.result=result;
        metadata = result.getMetaData();
        wrongChecker = true;
        respondArray =new JSONArray();
        initObj();
    }
    private void initObj() throws SQLException
    {
        while(result.next())
        {
            wrongChecker =false;
            temp =new JSONObject();
            for(int i =0;i<metadata.getColumnCount();i++)
            {
                temp.put(metadata.getColumnName(i+1), result.getString(i+1));
            }
            respondArray.put(temp);
        }
        
        
    }
    public String getRespond() throws Throwable
    {
        if(wrongChecker)
        throw new Throwable("No Data Found");
        return respondArray.toString();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automated_attendence.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author udoy
 */
public class Static_Functions  {
    
    public static boolean is_a_valid_login(ResultSet result)throws SQLException
    {
        if(result.next())
            return true;
        else return false;
        
    }
}

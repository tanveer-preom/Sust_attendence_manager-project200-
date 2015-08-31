<%-- 
    Document   : security
    Created on : Mar 7, 2015, 11:34:36 AM
    Author     : shuvo
--%>

<%@page import="com.automated_attendence.Instructor.InstructorInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loading..</title>
        <% session.setAttribute("attempt", 1);
           int year_id = Integer.parseInt(request.getParameter("year_id"));
            String id = request.getParameter("id"), password = request.getParameter("password"), dept_selected = request.getParameter("dept");
            session.setAttribute("id",id);
            session.setAttribute("password",password);
            InstructorInfo info=new InstructorInfo();
            String name=info.InstructorName(id, password);
            session.setAttribute("name",name);
        %>
        <meta http-equiv="refresh" content="0; url=index.jsp?year_id=<%=year_id%>&dept=<%=dept_selected%>" />
    </head>
    <body>
    </body>
</html>

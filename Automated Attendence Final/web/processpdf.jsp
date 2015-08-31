<%-- 
    Document   : pdf
    Created on : Feb 21, 2015, 2:53:34 PM
    Author     : shuvo
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.automated_attendence.services.services"%>
<%@page import="com.automated_attendence.Instructor.InstructorInfo"%>
<%@page import="com.automated_attendence.Instructor.InstructorInfo"%>
<%@page import="com.automated_attendence.Instructor.Attendence"%>
<%@page import="com.automated_attendence.Instructor.ResultPdf"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="com.itextpdf.text.Phrase"%>
<%@page import="com.itextpdf.text.Phrase"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="com.itextpdf.text.BaseColor"%>
<%@page import="com.itextpdf.text.Font"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>    
    <head>
        <meta charset="UTF-8">
        <title>AdminLTE 2 | Dashboard</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- Bootstrap 3.3.2 -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />    
        <!-- FontAwesome 4.3.0 -->
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons 2.0.0 -->
        <link href="http://code.ionicframework.com/ionicons/2.0.0/css/ionicons.min.css" rel="stylesheet" type="text/css" />    
        <!-- Theme style -->
        <link href="dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
        <!-- AdminLTE Skins. Choose a skin from the css/skins 
             folder instead of downloading all of them to reduce the load. -->
        <link href="dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />
        <!-- iCheck -->
        <link href="plugins/iCheck/flat/blue.css" rel="stylesheet" type="text/css" />
        <!-- Morris chart -->
        <link href="plugins/morris/morris.css" rel="stylesheet" type="text/css" />
        <!-- jvectormap -->
        <link href="plugins/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
        <!-- Date Picker -->
        <link href="plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
        <!-- Daterange picker -->
        <link href="plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
        <!-- bootstrap wysihtml5 - text editor -->
        <link href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
        <%if(session.getAttribute("id")==null){%>
        <meta http-equiv="refresh" content="2; url=login.jsp" />
        <%}else{%>
    </head>
    <%
        try {
            ResultPdf db = new ResultPdf();
            Attendence st = new Attendence();
            int year_id = Integer.parseInt(request.getParameter("year_id"));
            String course = request.getParameter("course_id"), dept = request.getParameter("dept");
            String FILE = "Result.pdf", semester_id = request.getParameter("semester_id"), consider = request.getParameter("consider");
            String datefrom = request.getParameter("datefrom"), dateto = request.getParameter("dateto");
            if (consider.equals("1")) {
                int from_day, from_month, from_year, to_day, to_month, to_year, minute;
                int done = Integer.parseInt(request.getParameter("done"));
                minute = Integer.parseInt(request.getParameter("time"));

                from_day = Integer.parseInt(datefrom.substring(8, 10));
                from_year = Integer.parseInt(datefrom.substring(0, 4));
                from_month = Integer.parseInt(datefrom.substring(6, 7));

                to_day = Integer.parseInt(dateto.substring(8, 10));
                to_year = Integer.parseInt(dateto.substring(0, 4));
                to_month = Integer.parseInt(dateto.substring(6, 7));

                st.autoedit(course, dept, year_id, semester_id, from_day, from_month, from_year, to_day, to_month, to_year, minute, done);
            }

    %>

    <%Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);
        Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);
        response.setContentType("application/pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        int t = db.publishpdf(dept, course, year_id, semester_id);
        document.open();
        document.addTitle("Document Title");
        document.add(new Paragraph(String.format("              ResultSheet of Course id %s,Department %s, Semester ID  %s,%s :", course, dept, year_id, semester_id)));
        document.add(new Paragraph(String.format("\n                                                                Total Class : %d ", db.tclass)));
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
        for (int i = 0; i <= t; ++i) {
            table.addCell(db._reg[i]);
            table.addCell(db._marks[i] + " ");
        }
        document.add(table);
        document.close();
    } catch (Exception err) {
    %>

    <%  String[] course_list, dept_list;
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        String year_id = request.getParameter("year_id");
        String semester_id = request.getParameter("semester_id"), day, month, yyyy;
        String id = (String) session.getAttribute("id"), password = (String) session.getAttribute("password"), dept = request.getParameter("dept");
        String course = request.getParameter("course_id"), date = request.getParameter("date");
        String[] semester;
        if (year_id == null) {
            year_id = year + "";
        } else if (year_id.equals("")) {
            year_id = year + "";
        }
        if (semester_id == null) {
            semester_id = "JUN";
        } else if (semester_id.equals("")) {
            semester_id = "JUN";
        }
        if (dept == null) {
            InstructorInfo inst = new InstructorInfo();
            dept = inst.InstructorDept(id);
        } else if (dept.equals("")) {
            InstructorInfo inst = new InstructorInfo();
            dept = inst.InstructorDept(id);
        }
        services db = new services();
        dept_list = db.department();
        semester = db.semester();
        course_list = db.courses(id, Integer.parseInt(year_id), semester_id, dept);
        dept_list = db.department();
        if (date == null) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            date = sdfDate.format(cal.getTime());
            day = date.substring(8, 10);
            yyyy = date.substring(0, 4);
            month = date.substring(5, 7);
        } else if (!date.equals("")) {
            day = date.substring(8, 10);
            yyyy = date.substring(0, 4);
            month = date.substring(5, 7);
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            date = sdfDate.format(cal.getTime());
            day = date.substring(8, 10);
            yyyy = date.substring(0, 4);
            month = date.substring(5, 7);
        }
        session.setAttribute("day", day);
        session.setAttribute("month", month);
        session.setAttribute("year", yyyy);
    %>
    <body class="skin-blue">
        <div class="wrapper">
            <header class="main-header">
                <!-- Logo -->
                <a href="" class="logo"><b>Admin Panel</b></a>
                <!-- Header Navbar: style can be found in header.less -->
                <nav class="navbar navbar-static-top" role="navigation">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">

                            <!-- User Account: style can be found in dropdown.less -->
                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image"/>
                                    <span class="hidden-xs"><%=session.getAttribute("name")%></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- User image -->
                                    <li class="user-header">
                                        <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                                        <p>
                                            <%=session.getAttribute("name")%>, Instructor
                                        </p>
                                    </li>

                                    <!-- Menu Footer-->
                                    <li class="user-footer">

                                        <div class="pull-right">
                                            <a href="login.jsp" class="btn btn-default btn-flat">Sign out</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                        </div>
                        <div class="pull-left info">
                            <p><%=session.getAttribute("name")%></p>

                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>

                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul class="sidebar-menu">
                        <li class="header">MAIN NAVIGATION</li>
                        <li class="treeview">
                            <a href="index.jsp?year=<%= year%>&year_id=<%=year_id%>">
                                <i class="fa fa-dashboard"></i> <span>Home</span> <i></i>
                            </a>
                        </li>
                        <li class="treeview">
                            <a href="courses-step-1.jsp?year=<%=year%>&year_id=<%=year_id%>">
                                <i class="fa fa-files-o"></i>
                                <span>Courses</span>               
                            </a>

                        </li>

                        <li class="active treeview">
                            <a href="chooseoption.jsp?jsp=publishpdf&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>">
                                <i class="fa fa-th"></i> <span>Publish Result</span>
                            </a>
                        </li>
                        <li class="treeview">
                            <a href="chooseoption.jsp?jsp=newclass&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>">
                                <i class="fa fa-pie-chart"></i>
                                <span>Add Class</span>
                            </a>
                        </li>

                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Dashboard
                        <small>Control panel</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Dashboard</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <center>
                        <div class="box box-info">
                            <div class="login-box-body">
                                <h2><b>Consideration Process And Publishing PDF failed :(</b></h2>
                                <h2><b>Give valid data in all input fields.</b></h2>
                            </div>
                        </div>
                    </center>
                </section><!-- /.content -->
            </div><!-- /.content-wrapper -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 1.0
                </div>
                <strong>Copyright &copy; 2014-2015 <a href="https://www.facebook.com/subrata.nath.750">Subrata Nath</a>&<a href="https://www.facebook.com/tanvir2010">Tanvir Mahbub</a>.</strong> All rights reserved.
            </footer>
        </div><!-- ./wrapper -->

        <!-- jQuery 2.1.3 -->
        <script src="plugins/jQuery/jQuery-2.1.3.min.js"></script>
        <!-- jQuery UI 1.11.2 -->
        <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.min.js" type="text/javascript"></script>
        <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
        <script>
            $.widget.bridge('uibutton', $.ui.button);
        </script>
        <!-- Bootstrap 3.3.2 JS -->
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>    
        <!-- Morris.js charts -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
        <script src="plugins/morris/morris.min.js" type="text/javascript"></script>
        <!-- Sparkline -->
        <script src="plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
        <!-- jvectormap -->
        <script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
        <script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
        <!-- jQuery Knob Chart -->
        <script src="plugins/knob/jquery.knob.js" type="text/javascript"></script>
        <!-- daterangepicker -->
        <script src="plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
        <!-- datepicker -->
        <script src="plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <!-- Bootstrap WYSIHTML5 -->
        <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
        <!-- iCheck -->
        <script src="plugins/iCheck/icheck.min.js" type="text/javascript"></script>
        <!-- Slimscroll -->
        <script src="plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <!-- FastClick -->
        <script src='plugins/fastclick/fastclick.min.js'></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js" type="text/javascript"></script>

        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="dist/js/pages/dashboard.js" type="text/javascript"></script>

        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js" type="text/javascript"></script>
    </body>
    <%}%>
    <%}%>
</html>

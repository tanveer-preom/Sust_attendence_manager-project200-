
<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.automated_attendence.Instructor.Attendence"%>
<%@page import="com.automated_attendence.services.services"%>
<%@page import="com.automated_attendence.Instructor.InstructorInfo"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.lang.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>AdminPANEL | Dashboard</title>
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
        <%if (session.getAttribute("id") == null) {%>
        <meta http-equiv="refresh" content="2; url=login.jsp" />
        <%} else {%>
    </head>
    <%  String[] course_list, dept_list;
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1, year_id = Integer.parseInt(request.getParameter("year_id"));
        String semester_id = request.getParameter("semester_id"), day, month, yyyy;
        String id = (String) session.getAttribute("id"), password = (String) session.getAttribute("password"), dept = request.getParameter("dept");
        String course = request.getParameter("course_id"), date = request.getParameter("date");
        String[] semester;
        services db = new services();
        dept_list = db.department();
        semester = db.semester();
        course_list = db.courses(id, year_id, semester_id, dept);
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
                <a href="" class="logo"><b>Admin</b>LTE</a>
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

                        <li>
                            <a href="chooseoption.jsp?jsp=publishpdf&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>">
                                <i class="fa fa-th"></i> <span>Publish Result</span>
                            </a>
                        </li>
                        <li class="active treeview">
                            <a href="">
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
                        <div class="box box-warning">
                            <div class="login-box-body">
                                <form action="addclass.jsp" id="info">
                                    <h3>  <b> Accepting Department:</b></h3>
                                    <select class="form-control" style="width:100px;" name="dept" onchange="JavaScript:reload()">
                                        <% for (int i = 0;
                                                    dept_list[i] != null; ++i) {
                                                if (dept.equals(dept_list[i])) {%>
                                        <option value="<%=dept_list[i]%>" id="input" selected> <%=dept_list[i]%> </option> <%} else {%>
                                        <option value="<%=dept_list[i]%>" id="input"> <%=dept_list[i]%> </option> 
                                        <%}
                                            }%>
                                    </select> <br><br>

                                    <h3>  <b>Semester ID:</b></h3>
                                    <select class="form-control" style="width:100px;" name="year_id" onchange="JavaScript:reload()" class="small_row">
                                        <%
                                            for (int i = year_id - 5;
                                                    i <= year_id + 5; ++i) {
                                                if (i == year_id) {
                                        %>
                                        <option value="<%=i%>" name="year_id" id="input" selected> <%=i%> </option> 
                                        <%} else {%>
                                        <option value="<%=i%>" name="year_id" id="input"> <%=i%> </option> 
                                        <%}
                                            }%>
                                    </select>
                                    <select class="form-control" style="width:100px;" name="semester_id" onchange="JavaScript:reload()">
                                        <% for (int i = 0;
                                                    semester[i] != null; ++i) {
                                                if (semester_id.equals(semester[i])) {%>
                                        <option value="<%=semester[i]%>" id="input" selected> <%=semester[i]%> </option> <%} else {%>
                                        <option value="<%=semester[i]%>" id="input"> <%=semester[i]%> </option> 
                                        <%}
                                            }%>
                                    </select> <br><br>
                                    <h3>  <b>Course ID:</b></h3>
                                    <select class="form-control" style="width:150px;" name="course_id" >
                                        <%
                                            if (course == null) {
                                                course = "CSE";
                                            }
                                            for (int i = 0;
                                                    course_list[i] != null; ++i) {
                                                if (course.equals(course_list[i])) {%>
                                        <option value="<%=course_list[i]%>" id="input" selected> <%=course_list[i]%> </option> <%} else {%>
                                        <option value="<%=course_list[i]%>" id="input"> <%=course_list[i]%> </option> 
                                        <%}
                                            }%>
                                    </select><br>
                                    <%if (course_list[0] != null) {%>
                                    <button class="btn btn-info btn-flat" onclick="JavaScript:next()" > Go </button>
                                    <%} else {%>
                                    <button class="btn btn-info btn-flat"  > Select All Field </button>
                                    <%}%>
                                </form>
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
        <script>
                                        function reload() {
                                            document.getElementById("info").submit();
                                        }
                                        function next() {
                                            document.getElementById("info").action = "newclass.jsp";
                                            document.getElementById("info").submit();
                                        }


        </script>
    </body>
    <%}%>
</html>

<%@page import="com.automated_attendence.Instructor.Attendence"%>
<%@page import="com.automated_attendence.services.services"%>
<%@page import="com.automated_attendence.Instructor.InstructorInfo"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.lang.*"%>
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
        <%if (session.getAttribute("id") == null) {%>
        <meta http-equiv="refresh" content="2; url=login.jsp" />
        <%} else {%>
        <%
            {
                int year = Integer.parseInt(request.getParameter("year")), year_id = Integer.parseInt(request.getParameter("year_id"));
                String semester_id = request.getParameter("semester_id"), course = request.getParameter("course_id"), dept = request.getParameter("dept");
                int day = Integer.parseInt(request.getParameter("day")), month = Integer.parseInt(request.getParameter("month")), i, total = Integer.parseInt(request.getParameter("total"));
                int insert = Integer.parseInt(request.getParameter("insert"));
                Attendence db = new Attendence();
                String status = "Data Processing...";
                if (insert == 0) {
                    for (i = 0; i <= total; ++i) {
                        db._id[i] = Integer.parseInt(request.getParameter(i + "id"));
                        db._course[i] = course;
                        db.semester_id = semester_id;
                        db._yearid[i] = year_id;
                        db._dept[i] = dept;
                        db._day[i] = day;
                        db._month[i] = month;
                        db._year[i] = year;
                        db._reg[i] = request.getParameter(i + "reg");
                        db._done[i] = Integer.parseInt(request.getParameter(i + "done"));
                        db._late[i] = Integer.parseInt(request.getParameter(i + "late"));
                        db._latemin[i] = Integer.parseInt(request.getParameter(i + "latemin"));
                        db._success[i] = Integer.parseInt(request.getParameter(i + "success"));
                        db._delete[i] = Integer.parseInt(request.getParameter(i + "delete"));
                    }
                    db.update(total);
                    status = "Wait for a while...";%>
        <script type="text/javascript">
            alert("Update Successfully Done!!!");
        </script>
        <%} else if (insert == 1) {
            try {
                for (i = 0; i <= 9; ++i) {
                    if (request.getParameter("insertid" + i) == "") {
                        db._id[i] = -1;
                        continue;
                    }
                    if (request.getParameter("insertreg" + i) == "") {
                        db._reg[i] = null;
                        continue;
                    }
                    db._id[i] = Integer.parseInt(request.getParameter("insertid" + i));
                    db._course[i] = course;
                    db.semester_id = semester_id;
                    db._yearid[i] = year_id;
                    db._dept[i] = dept;
                    db._day[i] = day;
                    db._month[i] = month;
                    db._year[i] = year;
                    db._reg[i] = request.getParameter("insertreg" + i);
                    db._done[i] = 1;
                    db._late[i] = 0;
                    db._latemin[i] = 0;
                    db._success[i] = 1;
                }
                db.insert(9);
            } catch (Exception e) {
                status = "Wait for a while...";%>
        <script type="text/javascript">
            alert("May be invalid registration-no or registration-no already exists in the table with  class id you have given!!!!");
        </script>
        <%}
            }
            String dd = day + "", mm = month + "", yyyy = year + "";
            if (dd.length() == 1) {
                dd = "0" + dd;
            }
            if (mm.length() == 1) {
                mm = "0" + mm;
            }
            String date = yyyy + "-" + mm + "-" + dd;
            session.setAttribute("success", "1");
        %>
        <meta http-equiv="refresh" content="2; url=attendencetable.jsp?date=<%=date%>&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>&day=<%=day%>&month=<%= month%>&year=<%=year%>" />
    </head>
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
                        <li class="active treeview">
                            <a href="index.jsp?year=<%=year%>&year_id=<%=year_id%>">
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
                            <a href="">
                                <i class="fa fa-th"></i> <span>Publish Result</span>
                            </a>
                        </li>
                        <li class="treeview">
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
                    <br><br><br><br><br>
                    <center><h2><b> <%=status%> </b></h2></center>
                </section><!-- /.content -->
            </div>
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
        <%
            }
        %>
    </body>
    <%}%>
</html>
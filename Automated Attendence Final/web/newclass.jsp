
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
    </head>
    <%  String[] course_list, dept_list;
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1, year_id = Integer.parseInt(request.getParameter("year_id"));
        String semester_id = request.getParameter("semester_id"), day, month, yyyy;
        String id = (String) session.getAttribute("id"), password = (String) session.getAttribute("password"), dept = request.getParameter("dept");
        String course = request.getParameter("course_id"), date = request.getParameter("date");
        String[] semester;
        String[] data;
        String class_id = request.getParameter("class_id");
        Attendence db = new Attendence();
        data = db.studentlist(course, year_id, semester_id);
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
        String select = request.getParameter("select");
        if (select == null) {
            select = "none";
        } else if (select.equals("all")) {
            select = "all";
        } else {
            select = "none";
        }
        session.setAttribute("class_id", class_id);
    %>
    <body class="skin-blue">
        <%
            if (class_id != null) {
                Attendence st = new Attendence();
                int total = st.changes(course, Integer.parseInt(class_id), semester_id, year_id, dept, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(yyyy));
        %>
        <div class="modal fade" id="myModal">
            <div class="modal-dialog" style="width:702px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">New Class Added in <%=day%>/<%=month%>/<%=yyyy%></h4>
                    </div>
                    <div class="modal-body">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>Class ID</th>
                                    <th>Registration No</th>
                                    <th>Late</th>
                                    <th>Latency time(in mins)</th>
                                    <th>Present till class end?</th>
                                    <th>Succesful Attendence</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%for (int i = 0; i <= total; ++i) {%>
                                <tr>
                                    <td><%=st._id[i]%></td>
                                    <td><%=st._reg[i]%></td>
                                    <td><%=st._late[i]%></td>
                                    <td><%=st._latemin[i]%></td>
                                    <td><% if (st._done[i] == 0) {%>NO
                                        <%} else {%>Yes
                                        <%}%></td>
                                    <td><% if (st._success[i] == 0) {%>NO
                                        <%} else {%>Yes
                                        <%}%></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
        <%}%>
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

                        <li>
                            <a href="chooseoption.jsp?jsp=publishpdf&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>">
                                <i class="fa fa-th"></i> <span>Publish Result</span>
                            </a>
                        </li>
                        <li class="active treeview">
                            <a href="chooseoption.jsp?jsp=newclass&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>">
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
                    <form action="confirmclass.jsp" id="info" style="text-align:center;">
                        <input name="total" value="<%=Integer.parseInt(data[0])%>" style="display:none">
                        <input name="course_id" value="<%=course%>" style="display:none">
                        <input name="semester_id" value="<%=semester_id%>" style="display:none">
                        <input name="year_id" value="<%=year_id%>" style="display:none">
                        <input name="dept" value="<%=dept%>" style="display:none">
                        <center><h3><b> Accepting Department : <%=dept%>,Semester ID: <%=year_id%>,<%=semester_id%><br> Course ID: <%=course%><br> 
                                </b></h3></center>
                        <div class="form-group">
                            <label>Choose Date:</label>
                            <div class="input-group" style="max-width:189px;margin-left:auto;margin-right:auto;">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                                <input type="date" name="date" id="myDate" />
                            </div><!-- /.input group -->
                        </div>
                        <script>
                            var x = document.getElementById("myDate");
                            var day = "<%= session.getAttribute("day")%>";
                            var month = "<%= session.getAttribute("month")%>";
                            var year = "<%= session.getAttribute("year")%>";
                            x.setAttribute("value", year + "-" + month + "-" + day);
                        </script>

                        <br>
                        <div style="clear:both;"></div>
                        <div>

                            <h3> List of registered students :</h3>
                            <div>
                                <a class="btn btn-info btn-flat" data-dismiss="modal" href="newclass.jsp?select=none&date=<%=date%>&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>&day=<%=day%>&month=<%=month%>&year=<%=yyyy%>">None</a>
                                <a class="btn btn-info btn-flat" data-dismiss="modal" href="newclass.jsp?select=all&date=<%=date%>&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>&day=<%=day%>&month=<%=month%>&year=<%=yyyy%>">Select all</a>
                            </div>
                            <div style="clear:both;"></div>
                            <div style="max-height:400px;overflow:auto;">
                                <table id="example1" class="table table-bordered table-striped" >
                                    <thead>
                                        <tr>
                                            <th style="width:50%;text-align:center"><input  value="Registration No" readonly="" style="border:none;background-color:transparent"></th>
                                            <th style="width:50%;text-align:center">Attendence</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (int i = 1; i <= Integer.parseInt(data[0]); ++i) {%>
                                        <tr>
                                            <td><input name="reg<%=i%>" value="<%=data[i]%>" readonly="" style="border:none;background-color:transparent"></td>
                                                <%if (select.equals("none")) {%>
                                            <td>No
                                                <input type="radio" name="attendence<%=i%>" value="0" checked="" >
                                                Yes
                                                <input type="radio" name="attendence<%=i%>" value="1" >
                                            </td>
                                            <%} else {%>
                                            <td>No
                                                <input type="radio" name="attendence<%=i%>" value="0">
                                                Yes
                                                <input type="radio" name="attendence<%=i%>" value="1"  checked="" >
                                            </td>
                                            <%}%>
                                        </tr>
                                        <%}%> 
                                    </tbody>
                                </table>
                            </div>
                            <input class="btn btn-info btn-flat" type="submit" value="Add Class With New Class ID">
                        </div>
                    </form>
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

        </script>

        <script type="text/javascript">
            $(window).load(function() {
                $('#myModal').modal('show');
            });
        </script>

        <%session.setAttribute("class_id", null);%>

    </body>
    <%}%>
</html>
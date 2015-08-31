<%@page import="com.automated_attendence.Instructor.PrevClass"%>
<%@page import="com.automated_attendence.student.student"%>
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
        <link href="plugins/style.css" rel="stylesheet" type="text/css" />

        <%
            String[] course;
            Calendar calendar;
            int year = Calendar.getInstance().get(Calendar.YEAR), year_id = Integer.parseInt(request.getParameter("year_id"));
            String id = (String) session.getAttribute("id"), password = (String) session.getAttribute("password"), dept = request.getParameter("dept");
            services db = new services();
            student st = new student();
            float data[] = st.attendence("CSE 200", 2014, "JUN", "CSE", 3, 2015);
            for (int i = 0; i <= 31; ++i) {%>
    <input value="<%=i%>" id="<%=i%>" style="display:none;">
    <%}
        session.setAttribute("id", id);
        if (session.getAttribute("attempt") != null) {
            session.setAttribute("password", password);
        }
        if (!db.login(id, password)) {%>
    <meta http-equiv="refresh" content="2; url=login.jsp" />
    <%} else {%>
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
                        <a href="chooseoption.jsp?jsp=publishpdf">
                            <i class="fa fa-th"></i> <span>Publish Result</span>
                        </a>
                    </li>
                    <li class="treeview">
                        <a href="chooseoption.jsp?jsp=newclass">
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


                <!-- Main row -->
                <div class="row">
                    <div class="info-box">
                        <div class="info-box-content">
                            <span class="info-box-text" style="text-align: -webkit-center;font-size: 30px;  margin-top: 15px;">Welcome To Automated Attendence System </span>
                        </div><!-- /.info-box-content -->
                    </div>
                </div>

                <%
                    PrevClass info = new PrevClass();
                    int totallast = info.informationlast(id);
                %>
                <div class="col-sm-6">
                    <div class="example-modal">
                        <div class="">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title">Last Class held in : <%=info.day%>/<%=info.month%>/<%=info.year%></h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Accepting Department : <%=info.dept%></p>
                                        <p>Semester : <%=info.semester_id%>,<%=info.year_id%></p>
                                        <p>Course Code : <%=info.course_id%></p>
                                        <p>Class ID : <%=info.class_id%></p>
                                        <p>Present : <%=totallast%></p>
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                    </div><!-- /.example-modal -->
                </div>
                <%
                int totalfirst = info.informationfirst(id);
                %>                    
                <div class="col-sm-6" style="overflow:hidden">
                    <div class="example-modal">
                        <div class="">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title">First Class held in : <%=info.day%>/<%=info.month%>/<%=info.year%></h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Accepting Department : <%=info.dept%></p>
                                        <p>Semester : <%=info.semester_id%>,<%=info.year_id%></p>
                                        <p>Course Code : <%=info.course_id%></p>
                                        <p>Class ID : <%=info.class_id%></p>
                                        <p>Present : <%=totalfirst%></p>
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                    </div><!-- /.example-modal -->
                </div>
                <div style="clear:both"></div>
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
    <script type="text/javascript">
        $(function() {

            // AREA CHART
            var area = new Morris.Area({
                element: 'revenue-chart',
                resize: true,
                data: [
                ],
                xkey: 'y',
                ykeys: ['item1', 'item2'],
                labels: ['Item 1', 'Item 2'],
                lineColors: ['#a0d0e0', '#3c8dbc'],
                hideHover: 'auto'
            });

            // LINE CHART
            var line = new Morris.Line({
                element: 'line-chart',
                data: [
                    {y: '1', a: document.getElementById("1").value},
                    {y: '2', a: document.getElementById("2").value},
                    {y: '3', a: document.getElementById("3").value},
                    {y: '4', a: document.getElementById("4").value},
                    {y: '5', a: document.getElementById("5").value},
                    {y: '6', a: document.getElementById("6").value},
                    {y: '7', a: document.getElementById("7").value},
                    {y: '8', a: document.getElementById("8").value},
                    {y: '9', a: document.getElementById("9").value},
                    {y: '10', a: document.getElementById("10").value},
                    {y: '11', a: document.getElementById("1").value},
                    {y: '12', a: document.getElementById("2").value},
                    {y: '13', a: document.getElementById("3").value},
                    {y: '14', a: document.getElementById("4").value},
                    {y: '15', a: document.getElementById("5").value},
                    {y: '16', a: document.getElementById("6").value},
                    {y: '17', a: document.getElementById("7").value},
                    {y: '18', a: document.getElementById("8").value},
                    {y: '19', a: document.getElementById("9").value},
                    {y: '20', a: document.getElementById("10").value},
                    {y: '21', a: document.getElementById("21").value},
                    {y: '22', a: document.getElementById("22").value},
                    {y: '23', a: document.getElementById("23").value},
                    {y: '24', a: document.getElementById("24").value},
                    {y: '25', a: document.getElementById("25").value},
                    {y: '26', a: document.getElementById("26").value},
                    {y: '27', a: document.getElementById("27").value},
                    {y: '28', a: document.getElementById("28").value},
                    {y: '29', a: document.getElementById("29").value},
                    {y: '30', a: document.getElementById("30").value},
                    {y: '31', a: document.getElementById("31").value},
                ],
                xkey: 'y',
                ykeys: ['a'],
                labels: ['Series A', 'Series B']
            });

            //DONUT CHART
            var donut = new Morris.Donut({
                element: 'sales-chart',
                resize: true,
                colors: ["#3c8dbc", "#f56954", "#00a65a"],
                data: [
                ],
                hideHover: 'auto'
            });
            //BAR CHART
            var bar = new Morris.Bar({
                element: 'bar-chart',
                resize: true,
                data: [
                ],
                barColors: ['#00a65a', '#f56954'],
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['CPU', 'DISK'],
                hideHover: 'auto'
            });
        });
    </script>
</body>
<%}%>
</html>
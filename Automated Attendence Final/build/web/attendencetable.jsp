<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.automated_attendence.Instructor.Attendence"%>
<%@page import="com.automated_attendence.services.services"%>
<%@page import="java.util.Calendar"%>
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
        <%if (session.getAttribute("id") == null) {%>
        <meta http-equiv="refresh" content="2; url=login.jsp" />
        <%} else {%>
    </head>
    <%
        String[] course_list;
        int max_id = 0;
        String date = request.getParameter("date");
        int year = Calendar.getInstance().get(Calendar.YEAR), year_id = Integer.parseInt(request.getParameter("year_id")), mm = Integer.parseInt(request.getParameter("month")), day = Integer.parseInt(request.getParameter("day"));
        String semester_id = request.getParameter("semester_id");
        String id = (String) session.getAttribute("id"), password = (String) session.getAttribute("password"), dept = request.getParameter("dept");
        String course = request.getParameter("course_id");
        services db = new services();
        course_list = db.courses(id, year_id, semester_id, dept);
        int[] data;
        String[] month = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int from_m, from_y, to_m, to_y, total = -1;
        services service = new services();
        data = service.monthlimit(course, year_id, semester_id, dept);
        Attendence st = new Attendence();
        from_m = data[0];
        from_y = data[1];
        to_m = data[2];
        to_y = data[3];
        String dd = null, mmm = null, yyyy = null;

        if (from_m != 0) {
            if (mm == 0) {
                mm = from_m;
                year = from_y;
            }
            if (request.getParameter("attemptdate") == null) { 
                if(request.getParameter("year")!=null)
                year = Integer.parseInt(request.getParameter("year"));
                date = year + "-" + mmm + "-" + dd;
            } else {
                if(date.length()>=1){
                day = Integer.parseInt(date.substring(8, 10));
                year = Integer.parseInt(date.substring(0, 4));
                mm = Integer.parseInt(date.substring(5, 7));
                }
                else{
                year=Calendar.getInstance().get(Calendar.YEAR);   
                }
            }
            dd = day + "";
            mmm = mm + "";
            yyyy = year + "";

            total = st.statistics(course, semester_id, year_id, dept, day, mm, year);
            for (int i = 0; i <= total; ++i) {
                if (st._id[i] > max_id) {
                    max_id = st._id[i];
                }
            }

            session.setAttribute("day", day);
            session.setAttribute("month", mm);
            session.setAttribute("year", year);
            if (day < 10) {
                session.setAttribute("day", "0" + day);
            }
            if (mm < 10) {
                session.setAttribute("month", "0" + mm);
            }
        }
    %>

    <%session.setAttribute("success", null);%>
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
                            <a href="index.jsp?year=<%=year%>&year_id=<%=year_id%>">
                                <i class="fa fa-dashboard"></i> <span>Home</span> <i></i>
                            </a>
                        </li>
                        <li class="active treeview">
                            <a href="courses-step-1.jsp?year=<%=year%>&year_id=<%=year_id%>">
                                <i class="fa fa-files-o"></i>
                                <span>Courses</span>               
                            </a>

                        </li>
                        <li>
                            <a href="chooseoption.jsp?jsp=publishpdf&year_id=<%=year_id%>&semester_id=<%=semester_id%>&dept=<%=dept%>&course_id=<%=course%>">
                                <i class="fa fa-th"></i> <span>Publish Result</span> 
                            </a>
                        </li>
                        <li class="treeview">
                            <a href="addclass.jsp?year_id=<%=year_id%>&semester_id=<%=semester_id%>&dept=<%=dept%>&course_id=<%=course%>">
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
                <section class="row content">


                    <!-- Main row -->

                    <div class="col-md-10">
                        <center><h3>Attendence Table of <br><%=course%> of semester id : <%=year_id%>,<%=semester_id%> at <%=day%>/<%=mm%>/<%=yyyy%><br>Accepting Department : <%=dept%></h3></center>
                        <form action="attendencetable.jsp">
                            <div class="form-group">
                                <label>Date Picker:</label>
                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="date" name="date" id="myDate"/>
                                    <input class="btn btn-info btn-flat" type="submit" value="Filter" style="height:40px;margin-top:-3px;"/>
                                </div><!-- /.input group -->
                            </div>

                            <script>
                                var x = document.getElementById("myDate");
                                var day = "<%= session.getAttribute("day")%>";
                                var month = "<%= session.getAttribute("month")%>";
                                var year = "<%= session.getAttribute("year")%>";
                                x.setAttribute("value", year + "-" + month + "-" + day);
                            </script>

                            <div style="display:none;">
                                <input type="text" name="dept" value="<%=dept%>" style="visibility: hidden" />
                                <input type="text" name="semester_id" value="<%=semester_id%>" style="visibility: hidden"/>
                                <input type="text" name="year_id" value="<%=year_id%>" style="visibility: hidden"/>
                                <input type="text" name="course_id" value="<%=course%>" style="visibility: hidden"/>
                                <input type="text" name="day" value="<%=day%>" style="visibility: hidden"/>
                                <input type="text" name="month" value="<%=mm%>" style="visibility: hidden"/>
                                <input type="text" name="attemptdate" value="<%=1%>" style="visibility: hidden"/>
                            </div>
                        </form>

                        <a href="#" class="btn btn-lg btn-success"
                           data-toggle="modal"
                           data-target="#basicModal" style="width: 128px;float:right;">Add student</a>
                        <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title" id="myModalLabel">Insert class ID and registration no.</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form action="tableedit.jsp" class="contact" name="contact" >
                                            <div style="display:none">
                                                <input type="text" name="dept" value="<%=dept%>" style="visibility: hidden" />
                                                <input type="text" name="semester_id" value="<%=semester_id%>" style="visibility: hidden"/>
                                                <input type="text" name="year_id" value="<%=year_id%>" style="visibility: hidden"/>
                                                <input type="text" name="course_id" value="<%=course%>" style="visibility: hidden"/>
                                                <input type="text" name="day" value="<%=day%>" style="visibility: hidden"/>
                                                <input type="text" name="month" value="<%=mm%>" style="visibility: hidden"/>
                                                <input type="text" name="year" value="<%=yyyy%>" style="visibility: hidden"/>
                                                <input type="text" name="total" value="<%=total%>" style="visibility: hidden">
                                                <input type="text" name="insert" value="1" style="visibility: hidden">
                                            </div>
                                            <table  class="table table-bordered" style="table-layout:fixed">
                                                <thead>
                                                    <tr>
                                                        <th>Class ID</th>
                                                        <th>Registration No</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (int j = 0; j <= 9; ++j) {%>
                                                    <tr>
                                                        <td><input min="1" max="<%= max_id%>" type="number" name="insertid<%=j%>" /></td>
                                                        <td><input type="number" name="insertreg<%=j%>" /></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                            <input type="submit" class="btn btn-primary" value="save"> 
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form action="tableedit.jsp"> 
                            <div style="display:none">
                                <input type="text" name="dept" value="<%=dept%>" style="visibility: hidden" />
                                <input type="text" name="semester_id" value="<%=semester_id%>" style="visibility: hidden"/>
                                <input type="text" name="year_id" value="<%=year_id%>" style="visibility: hidden"/>
                                <input type="text" name="course_id" value="<%=course%>" style="visibility: hidden"/>
                                <input type="text" name="day" value="<%=day%>" style="visibility: hidden"/>
                                <input type="text" name="month" value="<%=mm%>" style="visibility: hidden"/>
                                <input type="text" name="year" value="<%=yyyy%>" style="visibility: hidden"/>
                                <input type="text" name="total" value="<%=total%>" style="visibility: hidden">
                                <input type="text" name="insert" value="0" style="visibility: hidden">

                            </div>
                            <table  class="table table-bordered" style="table-layout:fixed">
                                <thead>
                                    <tr>
                                        <th>Class ID</th>
                                        <th>Registration No</th>
                                        <th>Late</th>
                                        <th>Latency time(in mins)</th>
                                        <th>Present till class end?</th>
                                        <th>Succesful Attendence</th>
                                        <th> </th>
                                    </tr>
                                </thead>
                            </table>
                            <div style="max-height:512px;overflow-y:auto;overflow-x:hidden;">

                                <table id="example1" class="table table-bordered table-striped" style="table-layout:fixed">

                                    <tbody>
                                        <%for (int i = 0; i <= total; ++i) {%>
                                        <tr id="table<%=i%>">
                                            <td><input readonly="" type="text" name="<%=i%>id" value="<%=st._id[i]%>" style=" border:none;background-color: transparent ; "></td>
                                            <td><input readonly="" type="text" name="<%=i%>reg" value="<%=st._reg[i]%>" style=" border:none;background-color: transparent ; "></td>
                                            <td><select  name="<%=i%>late" class="small_row" style=" border:none;background-color: transparent ; ">
                                                    <% if (st._late[i] == 0) {%>
                                                    <option value="<%=0%>" id="input" selected> NO</option> 
                                                    <option value="<%=1%>" id="input"> Yes </option> 
                                                    <%} else {%>
                                                    <option value="<%=0%>" id="input" > NO</option> 
                                                    <option value="<%=1%>" id="input" selected> Yes </option> 
                                                    <%}%>
                                                </select></td>
                                            <td><input type="number" name="<%=i%>latemin" value="<%=st._latemin[i]%>" style=" border:none;background-color: transparent ; "></td>
                                            <td><select  name="<%=i%>done" class="small_row" style=" border:none;background-color: transparent ; ">
                                                    <% if (st._done[i] == 0) {%>
                                                    <option value="<%=0%>" id="input" selected> NO</option> 
                                                    <option value="<%=1%>" id="input"> Yes </option> 
                                                    <%} else {%>
                                                    <option value="<%=0%>" id="input" > NO</option> 
                                                    <option value="<%=1%>" id="input" selected> Yes </option> 
                                                    <%}%>
                                                </select></td>
                                            <td><select  name="<%=i%>success" class="small_row" style=" border:none;background-color: transparent ; ">
                                                    <% if (st._success[i] == 0) {%>
                                                    <option value="<%=0%>" id="input" selected> NO</option> 
                                                    <option value="<%=1%>" id="input"> Yes </option> 
                                                    <%} else {%>
                                                    <option value="<%=0%>" id="input" > NO</option> 
                                                    <option value="<%=1%>" id="input" selected> Yes </option> 
                                                    <%}%>
                                                </select></td>
                                            <td style="background:#fff;">
                                                <input  class="btn btn-danger" type="button" value="delete"  onclick="JavaScript:deleterow(<%=i%>)" style="width:95px;"></td>
                                    <input name="<%=i%>delete" value="0" id="del<%=i%>" onclick="JavaScript:deleterow(<%=i%>)" style="visibility:hidden;display:none;">
                                    </tr>
                                    <%}%>
                                    </tbody>

                                </table>
                            </div>
                            <input type="submit" value="confirm" class="btn btn-info btn-flat"/>
                            <script>
                                var x = document.getElementById("confirm");
                                function confirm() {
                                    x.setAttribute("value", "Confirm");
                                }
                            </script>
                        </form>
                    </div>
                    <div class="col-md-2">
                        <b>Month list while <br>class occured:<br></b>
                            <% for (int i = from_m, j = from_y; from_m != 0; ++i) {%>
                        <a href="attendencetable.jsp?date=<%=date%>&dept=<%=dept%>&semester_id=<%=semester_id%>&year_id=<%=year_id%>&course_id=<%=course%>&day=<%=day%>&month=<%=i%>&year=<%=j%>"><%=month[i]%> <%=j%></a><br>
                        <% if (i == to_m && j == to_y) {
                                break;
                            }
                            if (i == 12) {
                                j += 1;
                                i = 0;
                            }%>
                        <% }%>
                    </div>
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
                                var j = 0;

                                function deleterow(i) {
                                    j = document.getElementById("del" + i).value;
                                    if (j == 0) {
                                        document.getElementById("table" + i).style.color = "red";
                                        document.getElementById("del" + i).value = "1";
                                    }
                                    else
                                    {
                                        document.getElementById("table" + i).style.color = "black";
                                        document.getElementById("del" + i).value = "0";
                                    }
                                }
        </script>
    </body>
    <%}%>
</html>
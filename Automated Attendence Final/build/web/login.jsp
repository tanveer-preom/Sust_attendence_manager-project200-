<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>AdminLTE 2 | Log in</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- Bootstrap 3.3.2 -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- Font Awesome Icons -->
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
        <!-- iCheck -->
        <link href="plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
        <link href="plugins/style.css" rel="stylesheet" type="text/css" />
    </head>
    <%
        Calendar calendar;
        int year = Calendar.getInstance().get(Calendar.YEAR);
    %>
    <body class="login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href=""><b>Admin</b>Panel</a>
            </div><!-- /.login-logo -->

            <div class="login-box-body">
                <p class="login-box-msg">Sign in to start your session</p>

                <%
                    session.setAttribute("year", year + "");
                    if (session.getAttribute("password") != null) {%>
                <center style="color:red" >Wrong password/id</center>
                    <%session.setAttribute("password", null);
                            session.setAttribute("id", null);
                            session.setAttribute("attempt", null);
                        }%>
                <form action="security.jsp" method="post">
                    <div style=" display: none; ">
                        <input type="text" name="year_id"  value="<%=year%>" style="visibility: hidden" >
                        <input type="text" name="dept"  value="CSE" style="visibility: hidden" >
                    </div>
                    <div class="form-group has-feedback">
                        <input name="id" type="text" class="form-control" placeholder="ID"/>
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input name="password" type="password" class="form-control" placeholder="Password"/>
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="row">
                        <div class="col-xs-8">    
                            <div class="checkbox icheck">
                                <label>
                                    <input type="checkbox"> Remember Me
                                </label>
                            </div>                        
                        </div><!-- /.col -->
                        <div class="col-xs-4">
                            <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                        </div><!-- /.col -->
                    </div>
                </form>

            </div><!-- /.login-box-body -->

        </div><!-- /.login-box -->

        <!-- jQuery 2.1.3 -->
        <script src="plugins/jQuery/jQuery-2.1.3.min.js"></script>
        <!-- Bootstrap 3.3.2 JS -->
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- iCheck -->
        <script src="plugins/iCheck/icheck.min.js" type="text/javascript"></script>
        <script>
            $(function() {
                $('input').iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%' // optional
                });
            });
        </script>
    </body>
</html>
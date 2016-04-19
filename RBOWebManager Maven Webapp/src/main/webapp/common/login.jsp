<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="AllenHu">

    <title>Rbo User login</title>

    <link href="adminex/css/style.css" rel="stylesheet">
    <link href="adminex/css/style-responsive.css" rel="stylesheet">

</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" action="../sec/anonymous/j_spring_security_check" method="post" >
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">Sign In</h1>
        </div>
        <div class="login-wrap">
            <input type="text" name="j_username" class="form-control" placeholder="用户ID" autofocus>
            <input type="password" name="j_password" class="form-control" placeholder="密码">

            <button class="btn btn-lg btn-login btn-block" type="submit">
                <i class="fa fa-check"></i>
            </button>

        </div>

        <c:if test="${param.error==true}">
		<p>
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			
			<h5>登录异常</h5>
			<p>${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</p>
		</div>
		</p>
		</c:if>

    </form>

</div>



<!-- Placed js at the end of the document so the pages load faster -->

<!-- Placed js at the end of the document so the pages load faster -->
<script src="adminex/js/jquery-1.10.2.min.js"></script>
<script src="adminex/js/bootstrap.min.js"></script>
<script src="adminex/js/modernizr.min.js"></script>

</body>
</html>

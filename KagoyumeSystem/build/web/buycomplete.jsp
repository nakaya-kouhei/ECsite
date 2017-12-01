<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.YahooDataBeans"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>購入-完了画面</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.mydata(udd, login)%>　<%= kh.cart(login)%>　<%= kh.login(login)%></div></div>
            <div class="col-sm-1" style=""></div>
        </div><br><br>
        
        <h1>購入が完了しました！</h1>
    </body>
</html>

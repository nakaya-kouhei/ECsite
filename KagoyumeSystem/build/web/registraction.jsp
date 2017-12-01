<%@page import="javax.servlet.http.HttpSession"
        import="java.util.List"
        import="java.util.HashMap"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    String re = request.getParameter("return");
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    String usercheck = (String)request.getAttribute("usercheck");
    UserData ud = (UserData)session.getAttribute("ud");
    UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>新規会員登録</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.login(login)%></div></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        <h1>籠夢へようこそ!</h1><br><br>
        <form action="RegistractionConfirm" method="POST">
            お名前<br>
            <input type="text" name="name" value="<%= ud.getName()%>" required><br>
            パスワード<br>
            <input type="password" name="password1" value="" required><br>
            もう一度パスワードを入力して下さい<br>
            <input type="password" name="password2" value="" required><br>
            メールアドレス<br>
            <input type="email" name="mail" value="<%= ud.getMail()%>" autocomplete="on" required><br>
            住所<br>
            <input type="text" name="address" value="<%= ud.getAddress()%>" required><br><br>
            <% if(re != null && re.equals("return")){%>
            <input type="hidden" name="return" value="return">
            <%}%>
            <input type="submit" name="submit" value="次へ" class="btn btn-success btn-sm">
        </form>
    </body>
</html>

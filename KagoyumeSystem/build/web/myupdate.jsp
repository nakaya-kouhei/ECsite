<%@page import="java.util.List"
        import="java.util.HashMap"
        import="javax.servlet.http.HttpSession"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserData ud = (UserData)hs.getAttribute("ud");
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    List<String> checklist = (List<String>)request.getAttribute("checklist");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>会員情報-更新画面</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.mydata(udd, login)%>　<%= kh.cart(login)%>　<%= kh.login(login)%></div><br></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        <h1>登録情報を更新します</h1>
        <br>
        <br>
        <% if(checklist != null){for(String check : checklist){out.print(check + "<br>");}} %>
        <form action="MyUpdateResult" method="POST">
            お名前：<br>
            <input type="text" name="name" value="<% out.print(ud.getName()); %>" required><br>
            パスワード：<br>
            <input type="password" name="password1" value="" required><br>
            もう一度パスワードを入力して下さい：<br>
            <input type="password" name="password2" value="" required><br>
            メールアドレス：<br>
            <input type="email" name="mail" value="<% out.print(ud.getMail()); %>" autocomplete="on" required><br>
            ご住所：<br>
            <input type="text" name="address" value="<% out.print(ud.getAddress()); %> required"><br>
            <br>
            <input type="submit" name="update" value="更新" class="btn btn-primary btn-sm">
        </form>
        <form action="MyData" method="POST">
            <input type="submit" name="return" value="戻る" class="btn btn-default btn-sm">
            <input type="hidden" name="REINPUT" value="reinput">
        </form>
    </body>
</html>

<%@page import="javax.servlet.http.HttpSession"
        import="java.util.List"
        import="java.util.HashMap"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    String usercheck = (String)request.getAttribute("usercheck");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>会員情報-削除画面</title>
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
        </div><br>
        <h1>このユーザーをマジで削除しますか？</h1><br><br>
        ID：<br>
        <% out.print(udd.getUserID()); %><br>
        お名前：<br>
        <% out.print(udd.getName()); %><br>
        パスワード：<br>
        <% out.print(udd.getPassword()); %><br>
        メールアドレス：<br>
        <% out.print(udd.getMail()); %><br>
        ご住所：<br>
        <% out.print(udd.getAddress()); %><br>
        購入総額：<br>
        <% out.print(udd.getTotal()); %><br>
        登録日：<br>
        <% out.print(udd.getNewDate()); %><br>
        削除フラグ：<br>
        <% out.print(udd.getDeleteFlg()); %><br>
        <br>
        <a href="MyDeleteResult">はい</a><br>
        <a href="top.jsp">いいえ</a><br>
    </body>
</html>

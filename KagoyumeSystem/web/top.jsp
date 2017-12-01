<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    String reinput = "";
    String keyword = "";
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>籠夢 トップページ</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.mydata(udd, login)%>　<%= kh.cart(login)%>　<%= kh.login(login)%></div><br></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        <div align="center"><h1>籠夢</h1></div><br>
        <div align="center"><h3>ここでは、あなたが望むままに買い物（のようなもの）ができます。</h3></div><br>
        <div align="center"><h3>心ゆくまでお楽しみ下さい！</h3></div><br><br><br>
        <form action="Search" method="GET">
            <div align="center">
                <input type="search" name="keyword" value="<%if(!reinput.equals("")){out.print(keyword);}%>" autocomplete="on" required>
                <input type="submit" name="submit" value="検索" class="btn btn-primary btn-sm">
            </div><br>
        </form>
    </body>
</html>

<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.YahooDataBeans"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    List<YahooDataBeans> myhistory = (List<YahooDataBeans>)request.getAttribute("myhistory");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>会員情報-購入履歴</title>
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
        <h1>あなたの買い物リストです！</h1><br><br>
        
        <% if(myhistory == null){ %>
        <%   out.print("まだ何も買っていません。");%><br>
        <% }else{ %>
        <%   for(YahooDataBeans ydb : myhistory){ %>
        <%       out.print("<img src=" + ydb.getThumbnail() +" alt=\"\" title=\"\">" + ydb.getName() + "　" + ydb.getPrice() + "円");%><br>
        <%   } %>
        <% } %>
    </body>
</html>

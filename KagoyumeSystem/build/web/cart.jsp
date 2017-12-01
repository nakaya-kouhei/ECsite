<%@page import="javax.servlet.http.HttpSession"
        import="java.util.List"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.YahooDataBeans"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserData ud = (UserData)hs.getAttribute("ud");
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    List<YahooDataBeans> cart = (List<YahooDataBeans>)hs.getAttribute("cartlist");
    int i = 0;
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>cart</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.mydata(udd, login)%>　<%= kh.login(login)%></div><br></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        
        <% if(cart == null){ %>
            <div align="center"><h1>カートは空です！</h1></div>
        <% }else{ %>
            <div align="center"><h1>買い物籠</h1></div><br><br>
            <% for(YahooDataBeans ydb : cart){%>
            <form action="Item" method="Post" name="item">
                <a href="javascript:document.getElementsByName('item')[<%=i%>].submit()"><img src="<%= ydb.getThumbnail()%>" alt="" title=""></a>
                <a href="javascript:document.getElementsByName('item')[<%=i%>].submit()"><%= ydb.getName()%></a>
            <%  out.print(ydb.getPrice() + "円"); %>
                <input type="hidden" name="i" value="<%= i%>">
                <input type="hidden" name="ydb" value="<%= ydb%>">
                <input type="hidden" name="itemcode" value="<%= ydb.getItemCode()%>">
            </form>
            <form action="Cart" method="POST">
                <input type="submit" name="delete" value="削除" class="btn btn-default btn-sm"><br>
                <input type="hidden" name="index" value="<%= cart.indexOf(ydb)%>">
            </form>
            <% i++; %>
            <br>
            <% } %>
            <br>
            合計金額：<%= udd.getTotal()%>円<br>
            <form action="BuyConfirm" method="POST">
                <input type="submit" name="buy" value="購入する" class="btn btn-primary btn-sm"><br>
            </form>
        <% } %>
    </body>
</html>

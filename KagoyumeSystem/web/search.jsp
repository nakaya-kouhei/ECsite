<%@page import="java.util.List"
        import="java.util.HashMap"
        import="javax.servlet.http.HttpSession"
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
    List<YahooDataBeans> searchlist = (List<YahooDataBeans>)hs.getAttribute("searchlist");
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
        <title>検索結果一覧</title>
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
        <% if(searchlist.isEmpty()){ %><h1>申し訳ありませんが、その商品は取り扱っておりません。</h1>
        <% }else{ %>
            <% for(YahooDataBeans ydb : searchlist){ %>
            <form action="Item" method="POST" name="item">
            <div class="container">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>
                                <a href="javascript:document.getElementsByName('item')[<%=i%>].submit();"><img src="<%= ydb.getThumbnail()%>" alt="" title=""></a><br>
                                <a href="javascript:document.getElementsByName('item')[<%=i%>].submit();"><%= ydb.getName()%></a><br>
                                <%  out.print(ydb.getPrice()); %>円<br>
                                <input type="hidden" name="i" value="<%= i%>">
                            <%  i++; %>
                            <th>
                        </tr>
                    </thead>
                </table>
            </div>
            </form>
            <% } %>
        <% } %>
    </body>
</html>

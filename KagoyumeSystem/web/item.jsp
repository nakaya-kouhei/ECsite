<%@page import="javax.servlet.http.HttpSession"
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
    YahooDataBeans ydb = (YahooDataBeans)hs.getAttribute("ydb");
    boolean availability = (Boolean)request.getAttribute("availability");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>商品詳細</title>
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
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-11" style=""><div align="left"><h1>商品はこちらです</h1></div></div>
        </div><br><br>
            
            <br>
            <div class="row">
                <div class="col-sm-1" style=""></div>
                <div class="col-sm-3" style=""><div align="left"><% out.print(ydb.getName());%></div></div>
                <div class="col-sm-8" style=""></div>
            </div>
            <div class="row">
                <div class="col-sm-1" style=""></div>
                <div class="col-sm-2" style=""><img src="<%= ydb.getThumbnail()%>" alt="" title=""></div>
                <div class="col-sm-2" style=""><div align="left"><% out.print(ydb.getHeadline());%></div></div>
                <div class="col-sm-7" style=""><div align="left"><% out.print(ydb.getDescription());%></div></div>
            </div><br><br>
            <div class="row">
                <div class="col-sm-1" style=""></div>
                <div class="col-sm-2" style="">金額：<% out.print(ydb.getPrice());%>円</div>
                <div class="col-sm-2" style=""><div align="left">評価：<% out.print(ydb.getReviewRate());%>点/<% out.print(ydb.getReviewCount());%>人</div></div>
                <div class="col-sm-2" style=""><div align="left"><%if(availability){out.print("在庫有り");}else{out.print("品切れです");}%></div></div>
                <div class="col-sm-5" style=""></div>
            </div><br><br>
        <% if(availability){ %>
            <div class="row">
                <div class="col-sm-1" style=""></div>
                <div class="col-sm-11" style="">
                    <div align="left">
                        <form action="Add" method="GET">
                            <input type="submit" name="add" value="カートに追加する" class="btn btn-primary btn-sm">
                            <input type="hidden" name="itemcode" value="<%= ydb.getItemCode()%>">
                        </form>
                    </div>
                </div>
            </div>
        <% } %>
    </body>
</html>

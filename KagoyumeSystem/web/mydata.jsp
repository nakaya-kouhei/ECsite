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
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>会員情報-閲覧画面</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.cart(login)%>　<%= kh.login(login)%></div><br></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-11" style=""><div align="left"><h1>お客様の情報を表示します</h1></div></div>
        </div><br><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">お名前：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getName()); %></div></div>
            <div class="col-sm-7" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">パスワード：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getPassword()); %></div></div>
            <div class="col-sm-7" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">メールアドレス：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getMail()); %></div></div>
            <div class="col-sm-7" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">ご住所：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getAddress()); %></div></div>
            <div class="col-sm-7" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">購入総額：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getTotal()); %>円</div></div>
            <div class="col-sm-7" style=""></div>
        </div><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-2" style="">登録日：</div>
            <div class="col-sm-2" style=""><div align="left"><% out.print(udd.getNewDate()); %></div></div>
            <div class="col-sm-7" style=""></div>
        </div><br><br>
        <div class="row">
            <div class="col-sm-1" style=""></div>
            <div class="col-sm-1" style="">
                <form action="MyHistory" method="POST">
                    <input type="submit" name="history" value="購入履歴" class="btn btn-success btn-sm">
                </form>
            </div>
            <div class="col-sm-1" style="">
                <form action="MyUpdate" method="POST">
                    <div align="right">
                        <input type="submit" name="update" value="更新" class="btn btn-primary btn-sm">
                    </div>
                </form>
            </div>
            <div class="col-sm-1" style="">
                <form action="MyDelete" method="POST">
                    <div align="left">
                        <input type="submit" name="delete" value="削除" class="btn btn-danger btn-sm">
                    </div>
                </form>
            </div>
            <div class="col-sm-8" style=""></div>
        </div>
    </body>
</html>

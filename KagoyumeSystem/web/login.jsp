<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.YahooDataBeans"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    String login = (String)hs.getAttribute("login");
    String usercheck = (String)request.getAttribute("usercheck");
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
        <title>ログインページ</title>
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-11" style=""><div align="right"><%= kh.home()%>　<%= kh.mydata(udd, login)%>　<%= kh.cart(login)%></div><br></div>
            <div class="col-sm-1" style=""></div>
        </div><br>
        <% if(login.equals("logout")){%>
            <% if(usercheck == null || usercheck.isEmpty() || usercheck.equals("error")){ %>
                <form action="Login" method="POST">
                    <h1>会員の方はログインして下さい！</h1><br>
                    <% if(checklist != null){for(String check : checklist){out.print(check + "<br>");}%>
                    <% }else if(usercheck != null && usercheck.equals("error")){out.print("ログインできませんでした。<br>お名前かパスワードが間違っています。<br>");}%>
                    お名前<br>
                    <input type="text" name="name" value="kakaka" required><br>
                    パスワード<br>
                    <input type="password" name="password" value="kakaka" required><br><br>
                    <input type="hidden" name="next" value="top.jsp">
                    <input type="hidden" name="usercheck" value="on">
                    <input type="submit" name="login" value="ログイン" class="btn btn-primary btn-sm">
                </form>
                <br>
                <form action="Registraction" method="POST">
                    <a href="Registraction">新規会員登録</a>
                </form>
            <% }else if(usercheck.equals("delete")){ %>
                <div class="row">
                    <div class="col-sm-1" style=""></div>
                    <div class="col-sm-11" style=""><div align="left"><h1>すでに退会されています<br>ご利用いただく場合は、お手数ですが再登録をお願いします。</h1></div></div>
                </div><br><br>
                <div class="row">
                    <div class="col-sm-1" style=""></div>
                    <div class="col-sm-2" style="">
                        <div align="center">
                            <form action="Registraction" method="POST" name="registraction">
                                <a href="javascript:document.registraction.submit();"><h3>再登録</h3></a>
                                <input type="hidden" name="return" value="return">
                            </form>
                        </div>
                    </div>
                    <div class="col-sm-9" style=""></div>
                </div>
                
            <% } %>
        <% }else if(login.equals("login")){ %>
            <div align="center"><h1>ログアウトしますか？</h1></div><br><br>
            <div class="row">
                <div class="col-sm-4" style=""></div>
                <div class="col-sm-2" style="">
                    <div align="center">
                        <form action="Logout" method="POST" name="logout">
                            <a href="javascript:document.logout.submit();"><h3>はい</h3></a>
                            <input type="hidden" name="out" value="out">
                        </form>
                    </div>
                </div>
                <div class="col-sm-2" style="">
                    <div align="center">
                        <form action="top.jsp" method="POST" name="login">
                            <a href="javascript:document.login.submit();"><h3>いいえ</h3></a>
                        </form>
                    </div>
                </div>
                <div class="col-sm-4" style=""></div>
            </div>
        <% } %>
    </body>
</html>

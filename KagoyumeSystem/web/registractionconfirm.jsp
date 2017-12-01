<%@page import="javax.servlet.http.HttpSession"
        import="java.util.List"
        import="java.util.HashMap"
        import="kagoyume.KagoyumeHelper"
        import="kagoyume.UserData"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    String login = (String)hs.getAttribute("login");
    UserData ud = (UserData)hs.getAttribute("ud");
    UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
    KagoyumeHelper kh = KagoyumeHelper.getInstance();
    String re = (String)request.getAttribute("return");
    List<String> checklist = (List<String>)request.getAttribute("checklist");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>登録確認</title>
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
        </div><br><br>
        <% if(!checklist.isEmpty()){ for(String check : checklist){out.print(check + "<br>");}%>
        <% }else{ %>
            <% if(re != null && re.equals("return")){ %>
            <h1>再登録します</h1>
            <% }else{ %>
            <h1>会員登録します</h1>
            <% } %>
                お名前<br>
                <%= ud.getName()%><br>
                パスワード<br>
                <%= ud.getPassword()%><br>
                メールアドレス<br>
                <%= ud.getMail()%><br>
                住所<br>
                <%= ud.getAddress()%><br><br>
                内容に間違いなければ、登録ボタンを押してください。<br>
            <form action="RegistractionComplete" method="POST">
                <input type="submit" name="next" value="登録" class="btn btn-primary btn-sm">
                <input type="hidden" name="return" value="<% if(re != null && re.equals("return")){out.print("on");}else{out.print("off");} %>">
            </form>
        <% } %>
        <form action="Registraction" method="POST">
            <input type="submit" name="back" value="戻る" class="btn btn-default btn-sm">
            <input type="hidden" name="reinput" value="reinput">
        </form>
    </body>
</html>

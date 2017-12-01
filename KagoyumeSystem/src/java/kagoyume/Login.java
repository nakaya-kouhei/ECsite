package kagoyume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nakaya-k
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try{
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更
            
            String login = (String)session.getAttribute("login");
            if(login == null || login.trim().length() == 0){
                session.setAttribute("login", "logout");
            }
            
            //ユーザーの新規・既存・退会済みを判定
            String usercheck = request.getParameter("usercheck");
            UserDataDTO udd = new UserDataDTO();
            UserData ud = new UserData();
            if(usercheck != null && usercheck.equals("on")){
                //フォームの入力情報を確認
                ud.setName(request.getParameter("name"));
                ud.setPassword(request.getParameter("password"));
                List<String> checklist = ud.UDCheck2();
                if(!checklist.isEmpty()){
                    request.setAttribute("checklist", checklist);
                }else{
                    //入力があれば処理を開始
                    udd.setName(ud.getName());
                    udd.setPassword(ud.getPassword());
                    udd = UserDataDAO.getInstance().search(udd);
                    if(udd == null){
                        //未登録ユーザー
                        request.setAttribute("usercheck", "error");
                        session.setAttribute("ud", ud);
                    }else if(udd.getDeleteFlg() == 1){
                        //退会済みユーザー
                        request.setAttribute("usercheck", "delete");
                        ud.UDMapping(udd);
                        session.setAttribute("ud", ud);
                        session.setAttribute("udd", udd);
                    }else{
                        //遷移元の情報を保持
                        String next = (String)session.getAttribute("next");
                        List<YahooDataBeans> searchlist = new ArrayList<>();
                        YahooDataBeans ydb = new YahooDataBeans();
                        switch (next) {
                            case "Search":
                                searchlist = (List<YahooDataBeans>)session.getAttribute("searchlist");
                                break;
                            case "Item":
                                ydb = (YahooDataBeans)session.getAttribute("ydb");
                                break;
                            default:
                                break;
                        }
                        
                        //セッションとクッキーからカートを取得
                        String cname = ud.CartName();
                        Cookie co = null;
                        Cookie[] ck = request.getCookies();
                        CookieOperation cop = new CookieOperation();
                        co = cop.CookieSerch(co, ck, cname, "Cookie");
                        List<String> cart;
                        if(session.getAttribute(cname) != null){
                            cart = ((List<String>)session.getAttribute(cname));
                        }else{
                            cart = cop.CartList(co, ck, cname);
                        }
                        cname = "guestcart";
                        List<String> buycart = (List<String>)session.getAttribute(cname);

                        //セッションとクッキーを破棄して再取得
                        co = cop.CookieSerch(co, ck, cname, "Cookie");
                        if(co != null){
                            response = cop.CookieDelete(cname, co, ck, response, true);
                        }
                        co = null;
                        cname = ud.CartName();
                        co = cop.CookieSerch(co, ck, cname, "Cookie");
                        if(co != null){
                            response = cop.CookieDelete(cname, co, ck, response, true);
                        }
                        session.invalidate();
                        HttpSession resession = request.getSession();

                        //ログインしてユーザー情報を保存
                        request.setAttribute("usercheck", "success");
                        resession.setAttribute("login", "login");
                        ud.UDMapping(udd);
                        resession.setAttribute("ud", ud);
                        resession.setAttribute("udd", udd);
                        switch (next) {
                            case "Search":
                                resession.setAttribute("searchlist", searchlist);
                                resession.setAttribute("reinput", "reinput");
                                break;
                            case "Item":
                                resession.setAttribute("ydb", ydb);
                                resession.setAttribute("reinput", "reinput");
                                break;
                            default:
                                break;
                        }
                        
                        //ユーザーカートを検索・生成してセッションとクッキーに格納
                        co = cop.CookieSerch(co, ck, cname, "Cookie");
                        int num;
                        if(buycart == null){
                            buycart = new ArrayList<>();
                            if(co == null){
                                co = cop.CookieGenerate(co, cname + "Cookie", String.valueOf(0));
                                response.addCookie(co);
                            }
                        //クッキーのユーザーカートにゲストカートを格納
                        }else if(co != null){
                            num = Integer.valueOf(co.getValue());
                            for(int i=1; i<=buycart.size(); i++){
                                num += i;
                                co = cop.CookieGenerate(co, cname + String.valueOf(num), buycart.get(i-1));
                                response.addCookie(co);
                            }
                            co = cop.CookieGenerate(co, cname + "Cookie", String.valueOf(num));
                            response.addCookie(co);
                        //クッキーのユーザーカートを生成してゲストカートを格納
                        }else{
                            num = buycart.size();
                            co = cop.CookieGenerate(co, cname + "Cookie", String.valueOf(num));
                            response.addCookie(co);
                            for(int i=1; i<=num; i++){
                                co = cop.CookieGenerate(co, cname + String.valueOf(i), buycart.get(i-1));
                                response.addCookie(co);
                            }
                        }
                        cart.addAll(buycart);
                        resession.setAttribute(cname, cart);

                        //ユーザー名とパスワードを鍵としてセッションIDをクッキーに保存
                        co = cop.CookieGenerate(co, ud.getName()+ud.getPassword(), resession.getId());
                        response.addCookie(co);

                        //遷移元へ遷移
                        request.getRequestDispatcher(next).forward(request, response);
                    }
                }
            }
            
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

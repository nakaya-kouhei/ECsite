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
public class Add extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        try{
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更
            session.setAttribute("next", "add.jsp");//ログイン画面からの遷移先指定
            
            String login = (String)session.getAttribute("login");
            YahooDataBeans ydb = (YahooDataBeans)session.getAttribute("ydb");
            UserData ud = (UserData)session.getAttribute("ud");
            CookieOperation cop = new CookieOperation();
            Cookie co = null;
            Cookie[] ck = request.getCookies();
            String cname;
            List<String> cart;
            List<String> buycart = new ArrayList<>();
            //ログアウト状態の処理
            if(login == null || login.equals("logout") || ud == null){
                ud = new UserData();
                cname = ud.CartName();
                cart = (List<String>)session.getAttribute(cname);
                //クッキーからゲストカートを取得
                if(cart == null){
                    cart = new ArrayList<>();
                    if(ck != null){//クッキーが存在する場合
                        /*co = cop.CookieSerch(co, ck, cname, "Cookie");
                        if(co != null){
                            buycart = cop.CartList(co, ck, cname);
                        }else{//ゲストカートが無い場合
                            co = cop.CookieGenerate(co, cname + "Cookie", "1");
                            response.addCookie(co);
                            co = cop.CookieGenerate(co, cname + "1", ydb.getItemCode());
                            response.addCookie(co);
                        }*/
                    }else{
                        //ゲストカートを生成
                        co = cop.CookieGenerate(co, cname + "Cookie", "1");
                        response.addCookie(co);
                        //ゲストカートに商品IDを入力
                        co = cop.CookieGenerate(co, cname + "1", ydb.getItemCode());
                        response.addCookie(co);
                    }
                }
                cart.add(ydb.getItemCode());
                buycart.addAll(cart);
                session.setAttribute(cname, buycart);
                
            //ログイン状態の処理
            }else if(login.equals("login")){
                cname = ud.CartName();
                buycart = (List<String>)session.getAttribute(cname);
                if(buycart == null){
                    buycart = new ArrayList<>();
                }
                buycart.add(ydb.getItemCode());
                session.setAttribute(cname, buycart);

                //クッキーにユーザーカートの中身を格納
                if(session.getAttribute(cname) == null){
                    if(ck != null){
                        co = cop.CookieSerch(co, ck, cname, "Cookie");
                        if(co != null){
                            //ユーザーカートに商品IDを格納
                            int num = Integer.valueOf(co.getValue()) + 1;
                            co = cop.CookieGenerate(co, cname + String.valueOf(num), ydb.getItemCode());
                            response.addCookie(co);
                            co = cop.CookieGenerate(co, cname + "Cookie", String.valueOf(num));
                            response.addCookie(co);
                        }
                    }
                }
            }

            request.getRequestDispatcher("add.jsp").forward(request, response);
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

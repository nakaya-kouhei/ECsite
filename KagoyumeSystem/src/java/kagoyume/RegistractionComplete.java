package kagoyume;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guest1Day
 */
public class RegistractionComplete extends HttpServlet {

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
            
            UserData ud = (UserData)session.getAttribute("ud");
            UserDataDTO udd = new UserDataDTO();
            String re = request.getParameter("return");
            if(re != null && re.equals("off")){//新規登録
                ud.UDDMapping(udd);
                UserDataDAO.getInstance().insert(udd);
                udd = UserDataDAO.getInstance().search(udd);
            }else if(re != null && re.equals("on")){//再登録
                udd = (UserDataDTO)session.getAttribute("udd");
                ud.UDDMapping(udd);
                UserDataDAO.getInstance().update(udd);
            }
            
            //ユーザーカートを生成
            String cname = ud.CartName();
            Object guestcart = session.getAttribute("guestcart");
            if(guestcart != null){
                session.setAttribute(cname, guestcart);
            }else{
                session.setAttribute(cname, null);
            }
            Cookie co = null;
            Cookie[] ck = request.getCookies();
            CookieOperation cop = new CookieOperation();
            co = cop.CookieSerch(co, ck, cname, "Cookie");
            if(co != null){
                co = cop.CookieGenerate(co, cname+"Cookie", "0");
                response.addCookie(co);
            }
            
            //ゲストカートを削除
            cname = "guestcart";
            session.removeAttribute(cname);
            co = cop.CookieSerch(co, ck, cname, "Cookie");
            response = cop.CookieDelete(cname, co, ck, response, true);
            
            session.setAttribute("udd", udd);
            session.setAttribute("login", "login");
            
            request.getRequestDispatcher("registractioncomplete.jsp").forward(request, response);
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

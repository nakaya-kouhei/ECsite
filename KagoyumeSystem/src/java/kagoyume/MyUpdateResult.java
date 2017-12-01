package kagoyume;

import java.io.IOException;
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
public class MyUpdateResult extends HttpServlet {

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
            
            UserData ud = new UserData();
            ud.setName(request.getParameter("name"));
            ud.setPassword(request.getParameter("password1"));
            ud.setMail(request.getParameter("mail"));
            ud.setAddress(request.getParameter("address"));
            String password = request.getParameter("password2");
            session.setAttribute("ud", ud);
            //入力値が適切でなければ、MyUpdateに戻る
            List<String> checklist = ud.UDCheck(password);
            if(!checklist.isEmpty()){
                request.setAttribute("checklist", checklist);
                request.getRequestDispatcher("myupdate.jsp").forward(request, response);
            //DBとカートを更新
            }else{
                //カートの中身を退避・削除
                UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
                Cookie co = null;
                Cookie[] ck = request.getCookies();
                CookieOperation cop = new CookieOperation();
                String cname = "Kagoyume-" + udd.getName() + "Cart";
                co = cop.CookieSerch(co, ck, cname, "Cookie");
                List<String> usercart = (List<String>)session.getAttribute(cname);
                session.removeAttribute(cname);
                session.removeAttribute(udd.getName() + udd.getPassword());
                //DB・カートを更新
                ud.UDDMapping(udd);
                UserDataDAO.getInstance().update(udd);
                session.setAttribute("udd", udd);
                session.setAttribute(cname, usercart);
                session.setAttribute(udd.getName() + udd.getPassword(), session.getId());
                if(co != null){
                    int num = Integer.valueOf(co.getValue());
                    List<String> cartlist = cop.CartList(co, ck, cname);
                    response = cop.CookieDelete(cname, co, ck, response, true);
                    cname = ud.CartName();
                    co = cop.CookieGenerate(co, cname + "Cookie", String.valueOf(num));
                    response.addCookie(co);
                    for(int i=1; i<=num; i++){
                        co = cop.CookieGenerate(co, cname + String.valueOf(i), cartlist.get(i-1));
                        response.addCookie(co);
                    }
                }

                request.getRequestDispatcher("myupdateresult.jsp").forward(request, response);
            }
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

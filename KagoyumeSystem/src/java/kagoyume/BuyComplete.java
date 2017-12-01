package kagoyume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nakaya-k
 */
public class BuyComplete extends HttpServlet {

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
            
            if(request.getParameter("type") == null){
                session.setAttribute("error", "error");
                request.getRequestDispatcher("BuyConfirm").forward(request, response);
            }else{
                //購入管理テーブルを更新
                List<YahooDataBeans> cart = (List<YahooDataBeans>)session.getAttribute("cartlist");
                UserData ud = (UserData)session.getAttribute("ud");
                UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
                List<BuyDataDTO> bddList = new ArrayList<>();
                int type = Integer.valueOf(request.getParameter("type"));
                for(YahooDataBeans ydb : cart){
                    BuyDataDTO bdd = new BuyDataDTO();
                    bdd.setUserID(udd.getUserID());
                    bdd.setItemCode(ydb.getItemCode());
                    bdd.setType(type);
                    bddList.add(bdd);
                }
                BuyDataDAO.getInstance().insert(bddList);

                //会員情報管理テーブルを更新(購入総額)
                int money = udd.getTotal();
                udd = UserDataDAO.getInstance().search(udd);
                money += udd.getTotal();
                udd.setTotal(money);
                UserDataDAO.getInstance().updateTotal(udd);

                //ユーザーカートの中身を削除
                String cname = ud.CartName();
                CookieOperation cop = new CookieOperation();
                Cookie[] ck = request.getCookies();
                Cookie co = null;
                co = cop.CookieSerch(co, ck, cname, "Cookie");
                response = cop.CookieDelete(cname, co, ck, response, false);
                session.setAttribute("cartlist", null);
                session.setAttribute(cname, null);

                request.getRequestDispatcher("buycomplete.jsp").forward(request, response);
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

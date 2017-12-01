package kagoyume;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nakaya-k
 */
public class Item extends HttpServlet {

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
            
        //セッションの開始
        HttpSession session = request.getSession();

        try{
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更
            //ログイン画面からの遷移かを判定
            if(session.getAttribute("reinput") != null && session.getAttribute("reinput").equals("reinput")){
                session.removeAttribute("reinput");
            }else{
                //検索結果画面から遷移した場合の処理
                String next = (String)session.getAttribute("next");
                int i = Integer.valueOf(request.getParameter("i"));
                List<YahooDataBeans> list;
                if(next != null && next.equals("Search")){
                    list = (List<YahooDataBeans>)session.getAttribute("searchlist");
                }else if(next != null && next.equals("Cart")){
                    list = (List<YahooDataBeans>)session.getAttribute("cartlist");
                }else{
                    throw new Exception("不正なアクセスです");
                }
                YahooDataBeans ydb = list.get(i);
                session.setAttribute("ydb", ydb);
                //在庫状況を確認
                if(ydb.getAvailability() != null && ydb.getAvailability().equals("instock")){
                    request.setAttribute("availability", true);
                }else{
                    request.setAttribute("availability", false);
                }
            }
            
            session.setAttribute("next", "Item");//ログイン画面からの遷移先指定
            request.getRequestDispatcher("item.jsp").forward(request, response);
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

package kagoyume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.net.URLCodec;

/**
 *
 * @author nakaya-k
 */
public class Search extends HttpServlet {

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
                //検索ワードが未入力の場合はエラー画面へ遷移
                String keyword = request.getParameter("keyword");
                if(keyword == null || keyword.trim().length() == 0){
                        request.setAttribute("error", "検索ワードが未入力です");
                        request.getRequestDispatcher("/error.jsp").forward(request, response);
                }else{
                    session.setAttribute("next", "Search");//ログイン画面からの遷移先指定
                    response.setContentType("application/json; charset=utf-8");//JSONの文字コードをUTF-8に変更

                    //検索ワードを用いてYahooAPIを検索 結果をMapに格納
                    String baseurl = "https://shopping.yahooapis.jp/ShoppingWebService/V1/json/itemSearch?";
                    String appid = "dj00aiZpPVZ5WmxCeUQ0U09jZyZzPWNvbnN1bWVyc2VjcmV0Jng9OTg-";
                    URLCodec codec = new URLCodec("UTF-8");
                    keyword = codec.encode(keyword, "UTF-8");
                    int hits = 10;
                    String urlString = baseurl + "appid=" + appid + "&query=" + keyword + "&hits=" + hits;
                    URL url = new URL(urlString);
                    HttpURLConnection con = null;
                    BufferedReader br = null;
                    try{
                        con = (HttpURLConnection)url.openConnection();
                        con.connect();
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String newdata;
                        String json = "";
                        while((newdata = br.readLine()) != null){
                            json += (String)URLDecoderMultibyte.decode(newdata, "UTF-8");
                        }
                        //Json ⇒ Map ⇒ List<YahooDataBeans>に変換
                        List<YahooDataBeans> searchlist = JsonDecode.getInstace().JsonResult(json);
                        session.setAttribute("searchlist", searchlist);
                        br.close();
                        con.disconnect();
                    }catch(Exception e){
                        request.setAttribute("error", e.getMessage());
                        request.getRequestDispatcher("/error.jsp").forward(request, response);
                    }finally{
                        if(br != null){
                            br.close();
                        }
                        if(con != null){
                            con.disconnect();
                        }
                    }
                }
            }
            request.getRequestDispatcher("/search.jsp").forward(request, response);
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

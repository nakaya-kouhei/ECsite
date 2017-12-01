package kagoyume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
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
public class Cart extends HttpServlet {

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
            session.setAttribute("next", "Cart");
            
            //ログイン状態でなければログイン画面へ遷移
            String login = (String)session.getAttribute("login");
            if(login == null || login.equals("logout")){
                request.setAttribute("usercheck", "cart");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
            UserData ud = (UserData)session.getAttribute("ud");
            String cname = ud.CartName();
            List<String> buycart = (List<String>)session.getAttribute(cname);
            
            //削除ボタンから遷移した場合は、指定要素を削除
            if(request.getParameter("index") != null){
                int index = Integer.valueOf(request.getParameter("index"));
                buycart.remove(index);
                session.setAttribute(cname, buycart);
                CookieOperation cop = new CookieOperation();
                Cookie co = null;
                Cookie[] ck = request.getCookies();
                co = cop.CookieSerch(co, ck, cname, "Cookie");
                response = cop.CookieDelete(cname, co, ck, response, false);
                for(int i=1; i<=buycart.size(); i++){
                    co = cop.CookieGenerate(co, cname + String.valueOf(i), buycart.get(i-1));
                    response.addCookie(co);
                }
            }
            
            //カート内の商品IDでYahooAPIを検索
            if(buycart != null && !buycart.isEmpty()){
                List<YahooDataBeans> resultlist = new ArrayList<>();
                for(String buy : buycart){
                    //ItemCodeでAPI検索
                    String baseurl = "https://shopping.yahooapis.jp/ShoppingWebService/V1/json/itemLookup?";
                    String appid = "dj00aiZpPVZ5WmxCeUQ0U09jZyZzPWNvbnN1bWVyc2VjcmV0Jng9OTg-";
                    String itemcode = "";
                    String responsegroup = "medium";
                    itemcode += "&itemcode=" + buy;
                    int image_size = 106;

                    String urlString = baseurl + "appid=" + appid + itemcode + "+&responsegroup=" + responsegroup + "&image_size=" + image_size;                
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
                            json += URLDecoder.decode(newdata, "UTF-8");
                        }

                        //Json ⇒ Map ⇒ List<YahooDataBeans>に変換
                        resultlist.addAll(JsonDecode.getInstace().JsonResult(json));
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
                session.setAttribute("cartlist", resultlist);
                //カート内商品の合計金額を算出
                int total = 0;
                for(YahooDataBeans ydb : resultlist){
                    total += Integer.parseInt(ydb.getPrice());
                }
                UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
                udd.setTotal(total);
                session.setAttribute("udd", udd);
            }else{
                session.setAttribute("cartlist", null);
            }
            
            request.getRequestDispatcher("cart.jsp").forward(request, response);
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

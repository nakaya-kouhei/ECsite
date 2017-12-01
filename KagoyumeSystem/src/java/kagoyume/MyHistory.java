package kagoyume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.arnx.jsonic.JSON;

/**
 *
 * @author nakaya-k
 */
public class MyHistory extends HttpServlet {

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
            response.setContentType("application/json; charset=utf-8");//JSONの文字コードをUTF-8に変更
            
            //購入管理テーブルを検索
            UserDataDTO udd = (UserDataDTO)session.getAttribute("udd");
            List<BuyDataDTO> bddlist = BuyDataDAO.getInstance().search(udd);
            
            if(bddlist == null){
                request.setAttribute("myhistory", null);
            }else{
                //ItemCodeでAPI検索
                String baseurl = "https://shopping.yahooapis.jp/ShoppingWebService/V1/json/itemLookup?";
                String appid = "dj00aiZpPVZ5WmxCeUQ0U09jZyZzPWNvbnN1bWVyc2VjcmV0Jng9OTg-";
                String itemcode = "";
                String responsegroup = "medium";
                for(BuyDataDTO bdd : bddlist){
                    itemcode += "&itemcode=" + bdd.getItemCode();
                }
                int image_size = 106;

                if(!itemcode.isEmpty()){
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

                        HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, Object>>>>>> resultset = JSON.decode(json);
                        List<YahooDataBeans> resultlist = new ArrayList<>();
                        int i = 0;
                        while(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)) != null){
                            YahooDataBeans resultdata = new YahooDataBeans();
                            resultdata.setItemCode(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get("ItemCode").get(String.valueOf(i)).get("Code")));
                            resultdata.setName(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Name")));
                            resultdata.setHeadline(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Headline")));
                            resultdata.setThumbnail(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Image").get("Small")));
                            resultdata.setPrice(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("PriceLabel").get("DefaultPrice")));
                            resultlist.add(resultdata);
                            i++;
                        }
                        request.setAttribute("myhistory", resultlist);
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
            
            request.getRequestDispatcher("myhistory.jsp").forward(request, response);
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

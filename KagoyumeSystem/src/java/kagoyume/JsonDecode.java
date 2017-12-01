package kagoyume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.arnx.jsonic.JSON;

/**
 *
 * @author nakaya-k
 */
public class JsonDecode  implements Serializable{
    
    //インスタンスオブジェクトを返却させてコードの簡略化
    public static JsonDecode getInstace(){
        return new JsonDecode();
    }
    
    public List<YahooDataBeans> JsonResult(String json){
        HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, Object>>>>>> resultset = JSON.decode(json);
        List<YahooDataBeans> resultlist = new ArrayList<>();
        if(Integer.valueOf(String.valueOf(resultset.get("ResultSet").get("totalResultsReturned"))) > 0){
            int i = 0;
            while(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)) != null){
                YahooDataBeans resultdata = new YahooDataBeans();
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Code") != null){
                    resultdata.setItemCode(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Code")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Image").get("Small") != null){
                    resultdata.setThumbnail(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Image").get("Small")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Name") != null){
                    resultdata.setName(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Name")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Description") != null){
                    resultdata.setDescription(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Description")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Headline") != null){
                    resultdata.setHeadline(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Headline")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("PriceLabel").get("DefaultPrice") != null){
                    resultdata.setPrice(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("PriceLabel").get("DefaultPrice")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Review").get("Rate") != null){
                    resultdata.setReviewRate(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Review").get("Rate")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Review").get("Count") != null){
                    resultdata.setReviewCount(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Review").get("Count")));
                }
                if(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Availability") != null){
                    resultdata.setAvailability(String.valueOf(resultset.get("ResultSet").get("0").get("Result").get(String.valueOf(i)).get("Availability")));
                }
                resultlist.add(resultdata);
                i++;
            }
        }
        return resultlist;
    }
    
}

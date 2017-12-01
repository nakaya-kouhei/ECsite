package kagoyume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nakaya-k
 */
public class CookieOperation  implements Serializable{
    
    public void CookieOperation(){
    }
    
    //クッキーを生成するメソッド
    public Cookie CookieGenerate(Cookie co, String name, String value){
        co = new Cookie(name, value);
        int maxage = 60 * 60 * 24;
        co.setMaxAge(maxage);
        co.setPath("/");
        co.setSecure(true);
        co.setHttpOnly(true);
        return co;
    }
    
    //クッキーを削除するメソッド
    public HttpServletResponse CookieDelete(String cname, Cookie co, Cookie[] ck, HttpServletResponse response, Boolean select){
        for(Cookie c : ck){
            if(c.getName().equals(cname + "Cookie") && select){
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
            if(co != null){
                for(int i=1; i<=Integer.valueOf(co.getValue()); i++){
                    if(c.getName().equals(cname + String.valueOf(i))){
                        c.setValue(null);
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    }
                }
            }
        }
        return response;
    }
    
    //クッキーを検索するメソッド
    public Cookie CookieSerch(Cookie co, Cookie[] ck, String cname, String word){
        for(Cookie c : ck){
            if(c.getName().equals(cname + word)){
                co = c;
                break;
            }
        }
        return co;
    }
    
    //カートの中身を取得するメソッド
    List<String> CartList(Cookie co, Cookie[] ck, String cname){
        List<String> buycart = new ArrayList<>();
        if(co != null){
            int num = Integer.valueOf(co.getValue());
            for(Cookie c : ck){
                for(int i=1; i<=num; i++){
                    if(c.getName().equals(cname + String.valueOf(i))){
                        buycart.add(c.getValue());
                    }
                }
            }
        }
        return buycart;
    }
    
}

package kagoyume;

/**
 *画面系の処理や表示を簡略化するためのヘルパークラス。定数なども保存されます
 * @author nakaya-k
 */
public class KagoyumeHelper {

    //トップへのリンクを定数として設定
    private final String homeURL = "Top";
    
    public static KagoyumeHelper getInstance(){
        return new KagoyumeHelper();
    }
    
    //トップへのリンクを返却
    public String home(){
        return "<a href=\""+homeURL+"\">トップページ</a>　";
    }
    
    //ログインへのリンクを返却
    public String login(String login){
        if(login != null && login.equals("login")){
            return "<a href=\"Login\">ログアウト</a>　";
        }else{
            return "<a href=\"Login\">ログイン</a>";
        }
    }
    
    //カートへのリンクを返却
    public String cart(String login){
        if(login != null && login.equals("login")){
            return "<a href=\"Cart\">買い物かご</a>　";
        }else{
            return "";
        }
    }
    
    //ユーザー情報へのリンクを返却
    public String mydata(UserDataDTO udd, String login){
        if(login != null && login.equals("login")){
            return "ようこそ<a href=\"MyData\">" + udd.getName() + "</a>さん";
        }else{
            return "";
        }
    }
    
    //ユーザー情報へのリンクを返却
    public String type(int i){
        switch(i){
            case 1:
                return "宅配便";
            case 2:
                return "航空便";
            case 3:
                return "クール便";
            default:
                return "";
        }
    }
    
}

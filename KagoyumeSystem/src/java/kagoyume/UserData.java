
package kagoyume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nakaya-k
 */
public class UserData implements Serializable{
    
    private String name;
    private String password;
    private String mail;
    private String address;
    
    public UserData(){
        this.name = "";
        this.password = "";
        this.mail = "";
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //空文字(未入力)の場合空文字をセット
        if(name.trim().length()==0){
            this.name = "";
        }else{
            this.name = name;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.trim().length()==0){
            this.password = "";
        }else{
            this.password = password;
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if(mail.trim().length()==0){
            this.mail = "";
        }else{
            this.mail = mail;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address.trim().length()==0){
            this.address = "";
        }else{
            this.address = address;
        }
    }
    
    public void UDDMapping(UserDataDTO udd){
        udd.setName(this.name);
        udd.setPassword(this.password);
        udd.setMail(this.mail);
        udd.setAddress(this.address);
    }
    
    public void UDMapping(UserDataDTO udd){
        this.name = udd.getName();
        this.password = udd.getPassword();
        this.mail = udd.getMail();
        this.address = udd.getAddress();
    }
    
    public List<String> UDCheck(String password){
        List<String> checklist = new ArrayList<>();
        if(this.name.equals("")){
            checklist.add("お名前を入力して下さい。");
        }
        if(this.password.equals("") || password.trim().length() == 0){
            checklist.add("パスワードを入力して下さい。");
        }else if(!password.equals(this.password)){
            checklist.add("パスワードが間違っています。");
        }else if(password.length() < 8){
            checklist.add("パスワードが短すぎます。<br>8文字以上入力して下さい。");
        }
        if(this.mail.equals("")){
            checklist.add("メールアドレスを入力して下さい。");
        }else{
            String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
            String dotAtom = aText + "+" + "(\\." + aText + "+)*";
            String mailpattern = "^" + dotAtom + "@" + dotAtom + "$";
            boolean mailcheck = this.mail.matches(mailpattern);
            if(!mailcheck){
                checklist.add("メールアドレスが正しくありません。");
            }
        }
        if(this.address.equals("")){
            checklist.add("住所を入力して下さい。");
        }
        return checklist;
    }
    
    public List<String> UDCheck2(){
        List<String> checklist = new ArrayList<>();
        if(this.name.equals("")){
            checklist.add("お名前を入力して下さい。");
        }
        if(this.password.equals("")){
            checklist.add("パスワードを入力して下さい。");
        }
        return checklist;
    }
    
    public String CartName(){
        String cartname;
        if(this.name.trim().length()==0){
            cartname = "guestcart";
        }else{
            cartname = "Kagoyume-" + this.getName() + "Cart";
        }
        return cartname;
    }
    
}

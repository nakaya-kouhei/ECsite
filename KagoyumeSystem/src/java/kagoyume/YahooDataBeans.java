package kagoyume;

import java.io.Serializable;

/**
 *
 * @author nakaya-k
 */
public class YahooDataBeans implements Serializable{
    private String itemcode;
    private String thumbnail;
    private String name;
    private String description;
    private String headline;
    private String price;
    private String reviewrate;
    private String reviewcount;
    private String availability;
    
    public YahooDataBeans(){
        this.itemcode = "";
        this.thumbnail = "";
        this.name = "";
        this.description = "";
        this.headline = "";
        this.price = "";
        this.reviewrate = "";
        this.reviewcount = "";
        this.availability = "";
    }
    
    public String getItemCode() {
        return itemcode;
    }
    public void setItemCode(String itemcode) {
        if(itemcode.trim().length()==0){
            this.itemcode = "";
        }else{
            this.itemcode = itemcode;
        }
    }
    
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        //空文字(データがない)の場合空文字をセット
        if(thumbnail.trim().length()==0){
            this.thumbnail = "";
        }else{
            this.thumbnail = thumbnail;
        }
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(name.trim().length()==0){
            this.name = "";
        }else{
            this.name = name;
        }
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        if(headline.trim().length()==0){
            this.headline = "";
        }else{
            this.headline = headline;
        }
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if(description.trim().length()==0){
            this.description = "";
        }else{
            this.description = description;
        }
    }
    
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        if(price.trim().length()==0){
            this.price = "";
        }else{
            this.price = price;
        }
    }

    public String getReviewRate() {
        return reviewrate;
    }

    public void setReviewRate(String reviewrate) {
        if(reviewrate.trim().length()==0){
            this.reviewrate = "";
        }else{
            this.reviewrate = reviewrate;
        }
    }

    public String getReviewCount() {
        return reviewcount;
    }

    public void setReviewCount(String reviewcount) {
        if(reviewcount.trim().length()==0){
            this.reviewcount = "";
        }else{
            this.reviewcount = reviewcount;
        }
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        if(availability.trim().length()==0){
            this.availability = "";
        }else{
            this.availability = availability;
        }
    }
    
}

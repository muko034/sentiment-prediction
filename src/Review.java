/**
 * Created by Zieciu on 2015-05-09.
 */

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "review")
@XmlAccessorType(XmlAccessType.FIELD)
public class Review {

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

//    public String getAsin() {
//        return asin;
//    }
//
//    public void setAsin(String asin) {
//        this.asin = asin;
//    }
//
//    public String getProduct_name() {
//        return product_name;
//    }
//
//    public void setProduct_name(String product_name) {
//        this.product_name = product_name;
//    }
//
//    public String getProduct_type() {
//        return product_type;
//    }
//
//    public void setProduct_type(String product_type) {
//        this.product_type = product_type;
//    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getReviewer() {
//        return reviewer;
//    }
//
//    public void setReviewer(String reviewer) {
//        this.reviewer = reviewer;
//    }
//
//    public String getReviewer_location() {
//        return reviewer_location;
//    }
//
//    public void setReviewer_location(String reviewer_location) {
//        this.reviewer_location = reviewer_location;
//    }
//
    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    @XmlElement(name = "unique_id")
    String unique_id;

//    String asin;
//    String product_name;
//    String product_type;
////    String helpful;

    @XmlElement(name = "rating")
    double rating;

//    String title;
////    String date;
//    String reviewer;
//    String reviewer_location;
    String review_text;
}

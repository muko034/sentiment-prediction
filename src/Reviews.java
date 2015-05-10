/**
 * Created by Zieciu on 2015-05-09.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlRootElement(name = "reviews")
@XmlAccessorType (XmlAccessType.FIELD)
public class Reviews {

    @XmlElement(name = "review")
    private List<Review> reviews = null;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

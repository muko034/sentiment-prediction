package plny.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zieciu on 2015-05-10.
 */
public class Input {

    public void append(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Reviews.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Reviews revs = (Reviews) jaxbUnmarshaller.unmarshal( new File(path) );

        List<Review> list = null;
        for (Review rev : revs.getReviews()) {
            Integer rating = new Integer(new Double(rev.getRating()).intValue());
            if (!this.reviews.containsKey(rating)) {
                list = new ArrayList<Review>();
                list.add(rev);
                this.reviews.put(rating, list);
            } else {
                list = this.reviews.get(rating);
                list.add(rev);
            }
        }
    }

    public Map< Integer, List<Review> > getReviews() {
        return reviews;
    }

    public void setReviews(Map< Integer, List<Review> > reviews) {
        this.reviews = reviews;
    }

    private Map< Integer, List<Review> > reviews = new HashMap< Integer, List<Review> >(5, 1.0f);
}

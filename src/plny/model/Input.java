package plny.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * Created by Zieciu on 2015-05-10.
 */
public class Input {

    public void read(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Reviews.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Reviews revs = (Reviews) jaxbUnmarshaller.unmarshal( new File(path) );

        this.reviews = revs.getReviews();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private List<Review> reviews = null;

}

package plny;

import plny.model.*;

import javax.xml.bind.JAXBException;

public class Main {

    public static void main(String[] args) {

        final String PATH = "sorted_data_acl/kitchen_&_housewares/negative.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/positive.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/unlabeled.review";

        System.out.println("Hello World!");

        Input input = new Input();

        try {
            input.read(PATH);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println(input.getReviews().get(5).getReview_text());
    }
}

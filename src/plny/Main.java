package plny;

import plny.model.*;

import javax.xml.bind.JAXBException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        final String PATH = "sorted_data_acl/kitchen_&_housewares/negative.review";
        final String PATH1 = "sorted_data_acl/kitchen_&_housewares/positive.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/unlabeled.review";

        System.out.println("Hello World!");

        Input input = new Input();
        PolarityBasic pb = new  PolarityBasic();

        try {
            input.append(PATH);
            input.append(PATH1);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Set<Integer> keys = input.getReviews().keySet();

        System.out.println("START TRAIN");

        for(Integer i : keys)
        {
            int amountToTrain= (int)(input.getReviews().get(i). size()* 0.75) ;
            pb.train(input.getReviews().get(i).subList(0, amountToTrain));
        }

        System.out.println("START EVALUATE");
        for(Integer i : keys)
        {
            int firstToEvaluate= (int)(input.getReviews().get(i). size()* 0.75)+1 ;
            pb.evaluate(input.getReviews().get(i).subList(firstToEvaluate,input.getReviews().get(i).size()));
        }

        System.out.println(input.getReviews().size());
        System.out.println(input.getReviews().get(1).size());
        System.out.println(input.getReviews().get(1).get(5).getReview_text());

        int sum = 0;
        for (Integer i : keys) {
            sum += input.getReviews().get(i).size();
        }
        System.out.println(sum);
    }
}

package plny;

import plny.model.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        final String PATH = "sorted_data_acl/books/negative.review";
        final String PATH1 = "sorted_data_acl/books/positive.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/unlabeled.review";

        System.out.println("Hello World!");

        Input input = new Input();
        //PolarityBasic pb = new  PolarityBasic();

        try {
            input.append(PATH);
            input.append(PATH1);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        PrintWriter zapis = new PrintWriter("rating/books_training.txt");
        PrintWriter zapis2 = new PrintWriter("rating/books_evaluate.txt");

        for(Integer i : input.getReviews().keySet())
        {

            int amountToTrain= (int)(input.getReviews().get(i). size()* 0.75) ;
            for(Review review : input.getReviews().get(i).subList(0, amountToTrain))
            {
                if(review == null)
                    continue;
                String result = review.getReview_text().replaceAll("[\\t\\n\\r]+", " ");
                zapis.println(i.toString()+".0" + "\t"+result) ;
            }
            for(Review review : input.getReviews().get(i).subList(amountToTrain+1,input.getReviews().get(i).size()))
            {
                if(review == null)
                    continue;
                String result = review.getReview_text().replaceAll("[\\t\\n\\r]+", " ");
                zapis2.println(i.toString()+".0" + "\t"+result) ;
            }
        }

       /* input = new Input();
        //PolarityBasic pb = new  PolarityBasic();

        try {
            input.append(PATH1);
            //input.append(PATH1);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        for(Integer i : new Integer[]{4,5})
        {
            int amountToTrain= (int)(input.getReviews().get(i). size()* 0.75) ;
            for(Review review : input.getReviews().get(i).subList(0, amountToTrain))
            {
                String result = review.getReview_text().replaceAll("[\\t\\n\\r]+", " ");
                zapis.println("positive\t"+result) ;
            }
            for(Review review : input.getReviews().get(i).subList(amountToTrain+1,input.getReviews().get(i).size()))
            {
                String result = review.getReview_text().replaceAll("[\\t\\n\\r]+", " ");
                zapis2.println("positive\t"+result) ;
            }
        }*/
        zapis.close();
        zapis2.close();
    }
}

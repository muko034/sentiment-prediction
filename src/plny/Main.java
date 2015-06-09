package plny;

import plny.model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        final String books = "books/";
        final String dvd = "dvd/";
        final String electronics = "electronics/";
        final String kitchen = "kitchen/";
        final String dataDir = "data/xml/" ;
        final String positive = "positive.review" ;
        final String negative = "negative.review";
        final String [] domainsPositive = {dataDir+books+positive,dataDir+dvd+positive,dataDir+electronics+positive,
                                            dataDir+kitchen+positive} ;
        final String [] domainsNegative = {dataDir+books+negative,dataDir+dvd+negative,dataDir+electronics+negative,
                                            dataDir+kitchen+negative} ;
        if(args.length != 1)
        {
            System.out.println("Error wrong number of arguments!") ;
            return ;
        }
        int domain = Integer.parseInt(args[0]) ;
        if(domain < 0 || domain > 3)
        {
            System.out.println("Error parametr out of range! [0,1,2,3]") ;
            return ;
        }
        

        Input input = new Input();
        PolarityBasic pb = new PolarityBasic();

        try {
            input.append(domainsPositive[domain]);
            input.append(domainsNegative[domain]);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
        List<Review> trainSet = new ArrayList<Review>();
        List<Review> testSet = new ArrayList<Review>();
        
        for(Integer i : input.getReviews().keySet())
        {
            //Collections.shuffle(input.getReviews().get(i)) ;
            int amountToTrain= (int)(input.getReviews().get(i). size()* 0.75) ;
            trainSet.addAll(input.getReviews().get(i).subList(0, amountToTrain)) ;
        }
        
        for(Integer i : input.getReviews().keySet())
        {
            int amountToTrain= (int)(input.getReviews().get(i). size()* 0.75) ;
            testSet.addAll(input.getReviews().get(i).subList(amountToTrain+1,input.getReviews().get(i).size())) ;
        }
        
        pb.train(trainSet);
        pb.evaluate(testSet);
    }
}

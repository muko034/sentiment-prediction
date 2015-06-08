package plny.model;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.NaiveBayesClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import java.util.List;

public class PolarityBasic {
    String[] mCategories;
    //DynamicLMClassifier<NGramProcessLM> mClassifier;
    NaiveBayesClassifier mClassifier;
    public PolarityBasic() {
        mCategories = new String[]{"positive", "negative"};
        int nGram = 8;
        //mClassifier = DynamicLMClassifier.createNGramProcess(mCategories,nGram);
        mClassifier = new NaiveBayesClassifier(mCategories,new IndoEuropeanTokenizerFactory());
    }

    public void train(List<Review> list){
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        System.out.println("\nTraining.");
        for (Review r : list) {
            //String category = Double.toString(r.getRating());
            Classification classification ;
            if(r.getRating() < 3)
                classification = new Classification("negative");
            else
                classification = new Classification("positive");
            ++numTrainingCases;
            numTrainingChars += r.getReview_text().length();
            Classified<CharSequence> classified
                    = new Classified<CharSequence>(r.getReview_text(),classification);
            mClassifier.handle(classified);
        }

        System.out.println("  # Training Cases=" + numTrainingCases);
        System.out.println("  # Training Chars=" + numTrainingChars);
    }

    public void evaluate(List<Review> list) {
        System.out.println("\nEvaluating.");
        int numTests = 0;
        int numCorrect = 0;
        for (Review r : list) {
            String category ;
            if(r.getRating() < 3)
                category = "negative";
            else
                category = "positive";
            ++numTests;
            Classification classification
                    = mClassifier.classify(r.getReview_text());
            if (classification.bestCategory().equals(category))
                ++numCorrect;
        }

        System.out.println("  # Test Cases=" + numTests);
        System.out.println("  # Correct=" + numCorrect);
        System.out.println("  % Correct="+ ((double)numCorrect)/(double)numTests);
    }


}


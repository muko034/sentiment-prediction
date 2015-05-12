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
        mCategories = new String[]{"1.0", "2.0", "3.0", "4.0", "5.0"};
        int nGram = 8;
        //mClassifier = DynamicLMClassifier.createNGramProcess(mCategories,nGram);
        mClassifier = new NaiveBayesClassifier(mCategories,new IndoEuropeanTokenizerFactory());
    }

    public void train(List<Review> list){
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        System.out.println("\nTraining.");
        for (Review r : list) {
            String category = Double.toString(r.getRating());
            Classification classification
                    = new Classification(category);
            ++numTrainingCases;;
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
            String category = Double.toString(r.getRating());
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


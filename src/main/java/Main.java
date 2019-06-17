import exceptions.WordIsNotInTheChainException;
import markovchain.MarkovChainText;
import utils.TextPreprocessing;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, WordIsNotInTheChainException {
        MarkovChainText markovChainText = TextPreprocessing.loadDataFromTextIntoMarkovChainOfTheOrder(2, "Yudkowsky_-_Harry_Potter_and_the_Methods_of_Rationality.txt");
        for (int i = 0; i < 100; i++) {
            String sentence = markovChainText.buildASentence("Harry");
            System.out.println(sentence);
        }
    }
}

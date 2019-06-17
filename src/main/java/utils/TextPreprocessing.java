package utils;

import markovchain.MarkovChainText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class TextPreprocessing {

    public static MarkovChainText loadDataFromTextIntoMarkovChainOfTheOrder(int order, String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        String data = new String(Files.readAllBytes(file.toPath()));
        data = data.replace("�", "'");
        data = data.replace("\n", " ");
        String[] words = data.split(" ");
        MarkovChainText markovChainText = new MarkovChainText(order);
        markovChainText.addFirstWord(words[0]);
        markovChainText.addElement(words[0]);
        for (int i = 1; i < words.length; i++) {
            markovChainText.addElement(words[i]);
            if (words[i-1].contains("."))
                markovChainText.addFirstWord(words[0]);
        }
        return markovChainText;
    }
}

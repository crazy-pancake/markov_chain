package markovchain;

import exceptions.WordIsNotInTheChainException;

import java.util.ArrayList;
import java.util.List;

public class MarkovChainText extends MarkovChain<String> {
    private List<String> firstWords;
    private static int MAX_SENTENCE_LENGTH = 25;

    public MarkovChainText(int order) {
        super(order);
        this.firstWords = new ArrayList<>();
    }

    public String buildASentence(String firstWord) throws WordIsNotInTheChainException {
        if (!root.hasChild(firstWord))
            throw new WordIsNotInTheChainException(firstWord);
        StringBuilder sentence = new StringBuilder();
        sentence.append(firstWord);
        int len = 1;
        String previousWord;
        List<String> lastUsedWords = new ArrayList<>();
        lastUsedWords.add(firstWord);
        int firstLen = 0;
        ChainElement<String> firstParent = root.getChild(lastUsedWords.get(0));
        do{
            firstParent = firstParent.getRandomChild();
            previousWord = firstParent.getData();
            lastUsedWords.add(previousWord);
            sentence.append(" ").append(previousWord);
            len++;
            firstLen++;
        }while (firstLen < order-1);
        if (lastUsedWords.size() > order)
            lastUsedWords.remove(0);

        do{
            ChainElement<String> parent = root.getChild(lastUsedWords.get(0));
            int wordsUsed = lastUsedWords.size()-1;
            for(int i = 0; i < order; i++){
                if (i < wordsUsed)
                    parent = parent.getChild(lastUsedWords.get(i+1));
                else if (parent.getChildren().size() != 0){
                    parent = parent.getRandomChild();
                    lastUsedWords.add(parent.getData());
                }
                else {
                    parent = root.getRandomChild();
                    lastUsedWords.add(parent.getData());
                    break;
                }
            }
            if (lastUsedWords.size() > order)
                lastUsedWords.remove(0);
            previousWord = parent.getData();
            sentence.append(" ").append(previousWord);
            if (previousWord.contains(".")){
                break;
            }
            len++;
        } while(len < MAX_SENTENCE_LENGTH);

        return sentence.toString();
    }

    public List<String> getFirstWords() {
        return firstWords;
    }

    public void addFirstWord(String word) {
        firstWords.add(word);
    }
}

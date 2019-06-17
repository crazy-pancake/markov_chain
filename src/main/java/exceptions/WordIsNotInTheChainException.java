package exceptions;

public class WordIsNotInTheChainException extends Exception {
    public WordIsNotInTheChainException(String word){
        super("This word: "+word);
    }
}

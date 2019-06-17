package markovchain;

import java.util.ArrayList;
import java.util.List;

public class MarkovChain<E> {
    protected int order;
    protected ChainElement<E> root;
    private List<E> previousElements;

    public MarkovChain(int order){
        if (order < 1)
            throw new IllegalArgumentException("Order should be positive. Try 1 or 2.");
        this.order = order;
        root = new ChainElement<>(0);
        previousElements = new ArrayList<>();
    }

    public void addElement(E data){
        ChainElement<E> parent = root;
        for (int i = 0; i <= order; i ++){
            if (!parent.hasChild(data))
                parent.addChild(new ChainElement<>(i+1, data, parent));
            else {
                parent.getChild(data).increaseOccurrence();
            }
            if (previousElements.size() > i) {
                if (i == 0) {
                    parent = parent.getChild(previousElements.get(previousElements.size()-1));
                } else {
                    parent = root;
                    for (int k = i; k >= 0; k--)
                        parent = parent.getChild(previousElements.get(previousElements.size() - 1 - k));
                }
            }
            else {
                break;
            }

        }
        if (previousElements.size() == order)
            previousElements.remove(0);
        previousElements.add(data);
    }


    public void toPrettyString(){
        toPrettyString(root);
    }
    public void toPrettyString(ChainElement<E> element){
        if (element.getChildren().size() == 0) {
            String tab = "    ";
            System.out.println(tab.repeat(element.getPosition()) + element.getData() + "\n");
        }
        else {
            String tab = "    ";
            System.out.println(tab.repeat(element.getPosition()) + element.getData());
            element.getChildren().forEach(this::toPrettyString);
        }
    }

}

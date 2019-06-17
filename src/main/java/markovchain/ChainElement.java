package markovchain;

import java.util.*;

public class ChainElement<E> {
    private int position;
    private E data;
    private ChainElement<E> parent;
    private List<ChainElement<E>> children;
    private int occurrence;

    public ChainElement(int position){
        this(position, null, null);
    }

    public ChainElement(int position, E data, ChainElement<E> parent) {
        this.position = position;
        this.data = data;
        children = new ArrayList<>();
        this.parent = parent;
        occurrence=1;
    }

    public void setParent(ChainElement<E> parent) {
        this.parent = parent;
    }

    public void addChild(ChainElement<E> child){
        children.add(child);
    }

    public boolean hasChild(E data){
        return children.stream().anyMatch(child -> child.data.equals(data));
    }

    public void increaseOccurrence(){
        occurrence++;
    }

    public ChainElement<E> getChild(E data) {
        return children.stream().filter(child -> child.data.equals(data)).findFirst().get();
    }

    public List<ChainElement<E>> getChildren() {
        return children;
    }

    public E getData() {
        return data;
    }

    public int getPosition() {
        return position;
    }

    public ChainElement<E> getRandomChild(){
        Map<Integer, ChainElement<E>> indexToChild = new HashMap<>();
        int index = 0;
        List<Integer> indices = new ArrayList<>();
        for (ChainElement<E> child : children) {
            indexToChild.put(index, child);
            for (int i = 0; i < child.occurrence; i++) {
             indices.add(index);
            }
            index++;
        }
        Collections.shuffle(indices);
        int result = indices.get(new Random().nextInt(indices.size()));
        return indexToChild.get(result);
    }

    public ChainElement<E> getChildWithBiggestProbability(){
        return children.stream().max(new OccurrenceComparator()).get();
    }

    private class OccurrenceComparator implements Comparator<ChainElement<E>> {

        @Override
        public int compare(ChainElement<E> o1, ChainElement<E> o2) {
            return Integer.compare(o1.occurrence, o2.occurrence);
        }
    }
}

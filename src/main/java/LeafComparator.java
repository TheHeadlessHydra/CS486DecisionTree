import java.util.Comparator;

/**
 * @author Serj
 * Compares and sorts leaves using their Ivalues
 */
public class LeafComparator implements Comparator<Leaf> {
    public int compare(Leaf o1, Leaf o2) {
        return o1.getiWordPair().getIValue() > o2.getiWordPair().getIValue() ? -1 : 1;
    }
}

import java.util.Comparator;

/**
 * Created by Serj on 11/11/2015.
 */
public class LeafComparator implements Comparator<Leaf> {
    public int compare(Leaf o1, Leaf o2) {
        return o1.getInformationGainOfWordToSplitOn() > o2.getInformationGainOfWordToSplitOn() ? 1 : -1;
    }
}

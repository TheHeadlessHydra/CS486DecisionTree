import java.util.Comparator;

/**
 * Created by Serj on 14/11/2015.
 */
public class DiscriminatoryWordComparator implements Comparator<DiscriminatoryWord> {
    public int compare(DiscriminatoryWord o1, DiscriminatoryWord o2) {
        return o1.getMeasure() > o2.getMeasure() ? -1 : 1;
    }
}

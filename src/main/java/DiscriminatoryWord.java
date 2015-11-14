/**
 * Created by Serj on 14/11/2015.
 */
public class DiscriminatoryWord {
    private final int word;
    private final double measure;

    public DiscriminatoryWord(int word, double measure) {
        this.word = word;
        this.measure = measure;
    }

    public int getWord() {
        return word;
    }

    public double getMeasure() {
        return measure;
    }

    @Override
    public String toString() {
        return "DiscriminatoryWord{" +
                "label=" + word +
                ", measure=" + measure +
                '}';
    }
}

/**
 * @author Serj
 * Represents a feature and its associated I(E) value
 */
public class IWordPair {
    private final double IValue;
    private final int word;

    public IWordPair(double IValue, int word) {
        this.IValue = IValue;
        this.word = word;
    }

    public double getIValue() {
        return IValue;
    }

    public int getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "IWordPair{" +
                "IValue=" + IValue +
                ", word=" + word +
                '}';
    }
}

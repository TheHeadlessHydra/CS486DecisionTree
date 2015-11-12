/**
 * POJO for storing data
 * word wordID is present in document docID
 */
public class EvidenceData {
    private final int label;
    private final int wordId;

    public EvidenceData(int label, int wordId) {
        this.label = label;
        this.wordId = wordId;
    }

    public int getLabel() {
        return label;
    }

    public int getWordId() {
        return wordId;
    }

    @Override
    public String toString() {
        return "EvidenceData{" +
                "label=" + label +
                ", wordId=" + wordId +
                '}';
    }
}

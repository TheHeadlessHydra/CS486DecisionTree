import java.util.ArrayList;

/**
 * POJO for storing data
 * word wordID is present in document docID
 */
public class Document {
    private final int newsgroupId;
    private final ArrayList<Boolean> isWordInDocument;

    public Document(int newsgroupId, ArrayList<Boolean> isWordInDocument) {
        this.newsgroupId = newsgroupId;
        this.isWordInDocument = isWordInDocument;
    }

    public int getNewsgroupId() {
        return newsgroupId;
    }

    public ArrayList<Boolean> getIsWordInDocument() {
        return isWordInDocument;
    }

    @Override
    public String toString() {
        return "Document{" +
                "newsgroupId=" + newsgroupId +
                ", isWordInDocument=" + isWordInDocument +
                '}';
    }
}

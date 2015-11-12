import java.util.ArrayList;

/**
 * POJO for storing data
 * word wordID is present in document docID
 */
public class DocumentEvidence {
    public static final int NUMBER_OF_WORDS_IN_DICT = 3567;

    private final int newsgroupId;
    private ArrayList<Boolean> isWordInDocument = null;

    public DocumentEvidence(int newsgroupId) {
        this.newsgroupId = newsgroupId;

        // initialize array to size of dictionary
        isWordInDocument = new ArrayList<Boolean>();
        for(int i = 0; i < NUMBER_OF_WORDS_IN_DICT; i++) {
            isWordInDocument.add(false);
        }
    }

    public int getNewsgroupId() {
        return newsgroupId;
    }

    public ArrayList<Boolean> getIsWordInDocument() {
        return isWordInDocument;
    }

    public void setIsWordInDocument(ArrayList<Boolean> isWordInDocument) {
        this.isWordInDocument = isWordInDocument;
    }

    @Override
    public String toString() {
        return "Document{" +
                "newsgroupId=" + newsgroupId +
                ", isWordInDocument=TooLargeToDisplay" +
                '}' + '\n';
    }
}

/**
 * POJO for storing data
 * word wordID is present in document docID
 */
public class Data {
    int docId;
    int wordId;

    public Data(int docId, int wordId) {
        this.docId = docId;
        this.wordId = wordId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (docId != data.docId) return false;
        return wordId == data.wordId;

    }

    @Override
    public int hashCode() {
        int result = docId;
        result = 31 * result + wordId;
        return result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "docId=" + docId +
                ", wordId=" + wordId +
                '}';
    }
}

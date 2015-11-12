/**
 * Decision Tree for splitting on words appearing in documents. The decision tree should
 * predict what newsgroup a document is in.
 */
public class DecisionTree {
    private String word = null;
    private DecisionTree wordExists = null;
    private DecisionTree wordDoesNotExist = null;
    private float prediction = 0;

    /**
     * Constructor that produces a leaf node
     * @param prediction the prediction of the type of newsgroup
     */
    public DecisionTree(float prediction) {
        this.prediction = prediction;
    }

    /**
     * Constructor for a node in the Decision Tree. This is an attribute/feature.
     * @param word the word being split on
     * @param wordExists the Decision Tree for if the word exists
     * @param wordDoesNotExist the Decision Tree for if the word does not exist
     */
    public DecisionTree(String word, DecisionTree wordExists, DecisionTree wordDoesNotExist) {
        this.word = word;
        this.wordExists = wordExists;
        this.wordDoesNotExist = wordDoesNotExist;
    }

    /**
     * A constructor for a node in the Decision Tree. This is an attribute/feature.
     * @param word the word being split on
     */
    public DecisionTree(String word) {
        throw new UnsupportedOperationException("Currently do not think this should be needed for the algorithm");
    }

    private boolean isLeaf() {
        return word == null;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public DecisionTree getWordExists() {
        return wordExists;
    }

    public void setWordExists(DecisionTree wordExists) {
        this.wordExists = wordExists;
    }

    public DecisionTree getWordDoesNotExist() {
        return wordDoesNotExist;
    }

    public void setWordDoesNotExist(DecisionTree wordDoesNotExist) {
        this.wordDoesNotExist = wordDoesNotExist;
    }

    public float getPrediction() {
        return prediction;
    }

    public void setPrediction(float prediction) {
        this.prediction = prediction;
    }

    @Override
    public String toString() {
        return "DecisionTree{" +
                "word='" + word + '\'' +
                ", wordExists=" + wordExists +
                ", wordDoesNotExist=" + wordDoesNotExist +
                ", prediction=" + prediction +
                '}';
    }
}

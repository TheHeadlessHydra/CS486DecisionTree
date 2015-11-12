/**
 * @author Serj
 * Decision Tree for splitting on words appearing in documents. The decision tree should
 * predict what newsgroup a document is in.
 */
public class DecisionTree {
    private Integer word = null;
    private DecisionTree wordExists = null;
    private DecisionTree wordDoesNotExist = null;
    private final int prediction;
    private final double informationGain;

    /**
     * Constructor that produces a leaf node
     * @param prediction the prediction of the type of newsgroup
     */
    public DecisionTree(int prediction, double informationGain) {
        this.prediction = prediction;
        this.informationGain = informationGain;
    }

    public Integer getWord() {
        return word;
    }

    public void setWord(Integer word) {
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

    public int getPrediction() {
        return prediction;
    }

    public double getInformationGain() {
        return informationGain;
    }

    @Override
    public String toString() {
        return "DecisionTree{" +
                "word=" + word +
                ", wordExists=" + wordExists +
                ", wordDoesNotExist=" + wordDoesNotExist +
                ", prediction=" + prediction +
                ", informationGain=" + informationGain +
                '}';
    }

    public void print(DecisionTree root, int level, String prefix) {
        if(root==null)
            return;
        print(root.getWordExists(), level+1, "( Exists)");
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
            System.out.println("|-------"+prefix+" "+root.getPrediction()+", IG: " + root.getInformationGain());
        }
        else {
            System.out.println(root.getPrediction() + ", IG: " + root.getInformationGain());
        }
        print(root.getWordDoesNotExist(), level+1, "(!Exists)");

    }
}

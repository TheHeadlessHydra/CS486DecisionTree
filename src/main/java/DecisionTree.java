/**
 * Decision Tree for splitting on words appearing in documents. The decision tree should
 * predict what newsgroup a document is in.
 */
public class DecisionTree {
    private Integer word = null;
    private DecisionTree wordExists = null;
    private DecisionTree wordDoesNotExist = null;
    private final double prediction;

    /**
     * Constructor that produces a leaf node
     * @param prediction the prediction of the type of newsgroup
     */
    public DecisionTree(double prediction) {
        this.prediction = prediction;
    }

    private boolean isLeaf() {
        return word == null;
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

    public double getPrediction() {
        return prediction;
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

    //public void print(String prefix, boolean isTail) {
        /*
        System.out.println(prefix + (isTail ? "|___" : "|---") + prediction);

        if(wordExists != null && wordDoesNotExist != null) {
            wordExists.print(prefix + (isTail ? "   " : "|   "), false);
            wordDoesNotExist.print(prefix + (isTail ? "   " : "|   "), false);
        } else if(wordExists != null) {
            wordExists.print(prefix + (isTail ? "   " : "|   "), true);
        } else if (wordDoesNotExist != null) {
            wordDoesNotExist.print(prefix + (isTail ?"   " : "|   "), true);
        }
        */

    public void print(DecisionTree root, int level, String prefix) {
        if(root==null)
            return;
        print(root.getWordExists(), level+1, "( Exists)");
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
            System.out.println("|-------"+prefix+" "+root.getPrediction());
        }
        else {
            System.out.println(root.getPrediction());
        }
        print(root.getWordDoesNotExist(), level+1, "(!Exists)");

    }
}

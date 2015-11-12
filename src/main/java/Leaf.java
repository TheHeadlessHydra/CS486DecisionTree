import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class Leaf {
    private final DecisionTree decisionTree;
    private final ArrayList<Document> evidenceDatas;
    private final IWordPair iWordPair;

    public Leaf(DecisionTree decisionTree, ArrayList<Document> evidenceDatas, IWordPair iWordPair) {
        this.decisionTree = decisionTree;
        this.evidenceDatas = evidenceDatas;
        this.iWordPair = iWordPair;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public ArrayList<Document> getEvidenceDatas() {
        return evidenceDatas;
    }

    public IWordPair getiWordPair() {
        return iWordPair;
    }
}

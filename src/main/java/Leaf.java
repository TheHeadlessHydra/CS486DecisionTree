import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class Leaf {
    private final DecisionTree decisionTree;
    private final ArrayList<DocumentEvidence> evidenceDatas;
    private final IWordPair iWordPair;

    public Leaf(DecisionTree decisionTree, ArrayList<DocumentEvidence> evidenceDatas, IWordPair iWordPair) {
        this.decisionTree = decisionTree;
        this.evidenceDatas = evidenceDatas;
        this.iWordPair = iWordPair;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public ArrayList<DocumentEvidence> getEvidenceDatas() {
        return evidenceDatas;
    }

    public IWordPair getiWordPair() {
        return iWordPair;
    }

    @Override
    public String toString() {
        return "Leaf{" +
                "decisionTree=" + decisionTree +
                ", evidenceDatas=TooLargeToPrint" +
                ", iWordPair=" + iWordPair +
                '}';
    }
}

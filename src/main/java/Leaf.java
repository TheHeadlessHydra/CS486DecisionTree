import java.util.ArrayList;

/**
 * @author Serj
 * A leaf node representation that is stored in the pririty queue
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

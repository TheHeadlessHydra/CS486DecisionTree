import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class Leaf {
    private final DecisionTree decisionTree;
    private final ArrayList<EvidenceData> evidenceDatas;
    private final Integer wordToSplitOn;
    private final Double informationGainOfWordToSplitOn;


    public Leaf(DecisionTree decisionTree, ArrayList<EvidenceData> evidenceDatas, Integer wordToSplitOn, Double informationGainOfWordToSplitOn) {
        this.decisionTree = decisionTree;
        this.evidenceDatas = evidenceDatas;
        this.wordToSplitOn = wordToSplitOn;
        this.informationGainOfWordToSplitOn = informationGainOfWordToSplitOn;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public ArrayList<EvidenceData> getEvidenceDatas() {
        return evidenceDatas;
    }

    public Integer getWordToSplitOn() {
        return wordToSplitOn;
    }

    public Double getInformationGainOfWordToSplitOn() {
        return informationGainOfWordToSplitOn;
    }
}

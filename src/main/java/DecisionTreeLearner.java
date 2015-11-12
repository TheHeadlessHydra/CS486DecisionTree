import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Serj
 * This is the main class that implements the decision tree algorithms.
 */
public class DecisionTreeLearner {
    private static final int ATHEISM = 1;
    private static final int GRAPHICS = 2;
    private static final int NUMBER_OF_WORDS = 3567;

    private final int numberOfIterations;

    public DecisionTreeLearner(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public DecisionTree decisionTreeLearn(ArrayList<DocumentEvidence> documentEvidences) {
        PriorityQueue<Leaf> priorityQueue = new PriorityQueue<Leaf>(numberOfIterations, new LeafComparator());

        int estimate = pointEstimate(documentEvidences);
        IWordPair iWordPair = getBestFeature(documentEvidences);
        DecisionTree decisionTree = new DecisionTree(estimate, iWordPair.getIValue());

        priorityQueue.add(new Leaf(decisionTree, documentEvidences, iWordPair));
        for(int i = 0; i < numberOfIterations; i++) {
            Leaf bestInformationGain = priorityQueue.poll();
            DecisionTree localDecisionTree = bestInformationGain.getDecisionTree();

            // if there is nothing left to split on
            if(bestInformationGain.getEvidenceDatas().isEmpty()) {
                continue;
            }

            localDecisionTree.setWord(bestInformationGain.getiWordPair().getWord());

            // child not in document
            ArrayList<DocumentEvidence> limitedEvidenceIsNotIn = limitEvidenceList(
                    bestInformationGain.getEvidenceDatas(),
                    bestInformationGain.getiWordPair().getWord(),
                    false);
            int childEstimateIsNotIn = pointEstimate(limitedEvidenceIsNotIn);
            IWordPair iWordPairChildIsNotIn = getBestFeature(limitedEvidenceIsNotIn);
            DecisionTree childDecisionTreeIsNotIn = new DecisionTree(childEstimateIsNotIn, iWordPairChildIsNotIn.getIValue());
            localDecisionTree.setWordDoesNotExist(childDecisionTreeIsNotIn);
            Leaf childLeafIsNotIn = new Leaf(childDecisionTreeIsNotIn, limitedEvidenceIsNotIn, iWordPairChildIsNotIn);
            priorityQueue.add(childLeafIsNotIn);

            // child in document
            ArrayList<DocumentEvidence> limitedEvidenceIsIn = limitEvidenceList(
                    bestInformationGain.getEvidenceDatas(),
                    bestInformationGain.getiWordPair().getWord(),
                    true);
            int childEstimateIsIn = pointEstimate(limitedEvidenceIsIn);
            IWordPair iWordPairChildIsIn = getBestFeature(limitedEvidenceIsIn);
            DecisionTree childDecisionTreeIsIn = new DecisionTree(childEstimateIsIn, iWordPairChildIsIn.getIValue());
            localDecisionTree.setWordExists(childDecisionTreeIsIn);
            Leaf childLeafIsIn = new Leaf(childDecisionTreeIsIn, limitedEvidenceIsIn, iWordPairChildIsIn);
            priorityQueue.add(childLeafIsIn);
        }
        return decisionTree;
    }

    public double testAgainstDecisionTree(DecisionTree decisionTree, ArrayList<Boolean> evidence) {
        if(decisionTree.getWord() == null){
            return decisionTree.getPrediction();
        }
        int wordId = decisionTree.getWord();
        boolean isInDocument = evidence.get(wordId);
        if(isInDocument) {
            DecisionTree childDecisionTree = decisionTree.getWordExists();
            if(childDecisionTree == null) {
                return decisionTree.getPrediction();
            } else {
                return testAgainstDecisionTree(childDecisionTree, evidence);
            }
        } else {
            DecisionTree childDecisionTree = decisionTree.getWordDoesNotExist();
            if(childDecisionTree == null) {
                return decisionTree.getPrediction();
            } else {
                return testAgainstDecisionTree(childDecisionTree, evidence);
            }
        }
    }

    private IWordPair getBestFeature(ArrayList<DocumentEvidence> documentEvidences) {
        Integer maxWord = 0;
        Double maxIValue = 0d;
        for(int i = 0; i < NUMBER_OF_WORDS; i++) {
            Double IValue = algorithmB(documentEvidences, i);
            if(IValue > maxIValue) {
                maxWord = i;
                maxIValue = IValue;
            }
        }
        return new IWordPair(maxIValue, maxWord);
    }

    private ArrayList<DocumentEvidence> limitEvidenceList(ArrayList<DocumentEvidence> documentEvidences, int wordToSplitOn, boolean valueOfWordToSplitOn) {
        ArrayList<DocumentEvidence> limitedEvidence = new ArrayList<DocumentEvidence>();
        for(DocumentEvidence documentEvidence : documentEvidences) {
            if(documentEvidence.getIsWordInDocument().get(wordToSplitOn).equals(valueOfWordToSplitOn)) {
                limitedEvidence.add(documentEvidence);
            }
        }
        return limitedEvidence;
    }

    // I(E) - I(Esplit)
    public double algorithmA(ArrayList<DocumentEvidence> evidenceData, int wordToSplitOn) {
        if(evidenceData.isEmpty()) return 0;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordA(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) - I(Esplit)
    public double algorithmB(ArrayList<DocumentEvidence> evidenceData, int wordToSplitOn) {
        if(evidenceData.isEmpty()) return 0;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordB(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) = -P(atheism)*log(P(atheism)) - P(graphics)*log(P(graphics))
    private double informationGain(ArrayList<DocumentEvidence> documentEvidences) {
        if(documentEvidences.isEmpty()) return 1d;
        double IeP1 = -totalTimesNewsgroupAppears(documentEvidences, ATHEISM)*Math.log(totalTimesNewsgroupAppears(documentEvidences, ATHEISM));
        double IeP2 = -totalTimesNewsgroupAppears(documentEvidences, GRAPHICS)*Math.log(totalTimesNewsgroupAppears(documentEvidences, GRAPHICS));
        return IeP1 + IeP2;
    }

    // 0.5*I(E1) + 0.5*I(E2)
    private double informationGainOnWordA(ArrayList<DocumentEvidence> documentEvidences, int wordToSplitOn) {
        ArrayList<DocumentEvidence> E1 = limitEvidenceList(documentEvidences, wordToSplitOn, false);
        ArrayList<DocumentEvidence> E2 = limitEvidenceList(documentEvidences, wordToSplitOn, true);
        double IE1 = informationGain(E1);
        double IE2 = informationGain(E2);
        return ((0.5d*IE1) + (0.5d*IE2));
    }

    // N1/N*I(E1) + N2/N*I(E2)
    private double informationGainOnWordB(ArrayList<DocumentEvidence> documentEvidences, int wordToSplitOn) {
        ArrayList<DocumentEvidence> E1 = limitEvidenceList(documentEvidences, wordToSplitOn, false);
        ArrayList<DocumentEvidence> E2 = limitEvidenceList(documentEvidences, wordToSplitOn, true);
        double IE1 = informationGain(E1);
        double IE2 = informationGain(E2);
        double N1 = ((double)E1.size())/((double)documentEvidences.size());
        double N2 = ((double)E2.size())/((double)documentEvidences.size());
        return ((N1*IE1) + (N2*IE2));
    }

    private double totalTimesNewsgroupAppears(ArrayList<DocumentEvidence> documentEvidences, Integer newsgroupToCheck) {
        int number = 0;
        for(DocumentEvidence documentEvidence : documentEvidences) {
            Integer newsgroup = documentEvidence.getNewsgroupId();
            if(newsgroup.equals(newsgroupToCheck)) {
                number++;
            }
        }
        return (number/(double)documentEvidences.size());
    }

    private int pointEstimate(ArrayList<DocumentEvidence> documentEvidences) {
        int numberOfAtheism = ATHEISM;
        int numberOfGraphics = GRAPHICS;

        for(DocumentEvidence documentEvidence : documentEvidences) {
            Integer newsgroup = documentEvidence.getNewsgroupId();
            if(newsgroup.equals(ATHEISM)) {
                numberOfAtheism++;
            } else if(newsgroup.equals(GRAPHICS)) {
                numberOfGraphics++;
            } else {
                throw new IllegalArgumentException("Got an integer in a label that was not 1 or 2");
            }
        }

        return (numberOfAtheism > numberOfGraphics ? ATHEISM : GRAPHICS);
    }
}

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Serj on 11/11/2015.
 */
public class DecisionTreeLearner {
    private static final int ATHEISM = 1;
    private static final int GRAPHICS = 2;
    private static final int NUMBER_OF_WORDS = 3567;
    private static final int NUMBER_OF_ITERATIONS = 10;

    public DecisionTree decisionTreeLearn(ArrayList<DocumentEvidence> documentEvidences) {
        PriorityQueue<Leaf> priorityQueue = new PriorityQueue<Leaf>(NUMBER_OF_ITERATIONS, new LeafComparator());

        int estimate = pointEstimate(documentEvidences);
        DecisionTree decisionTree = new DecisionTree(estimate);
        IWordPair iWordPair = getBestFeature(documentEvidences);

        System.out.println("DecisionTreeLearner.decisionTreeLearn INITIAL best word pair: " + iWordPair);
        priorityQueue.add(new Leaf(decisionTree, documentEvidences, iWordPair));
        for(int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            Leaf bestInformationGain = priorityQueue.poll();
            DecisionTree localDecisionTree = bestInformationGain.getDecisionTree();

            System.out.println("DecisionTreeLearner.decisionTreeLearn ================================================================= ");
            System.out.println("DecisionTreeLearner.decisionTreeLearn getEvidenceDatas: "+ bestInformationGain.getEvidenceDatas().size());
            System.out.println("DecisionTreeLearner.decisionTreeLearn getiWordPair: "+ bestInformationGain.getiWordPair());

            localDecisionTree.setWord(bestInformationGain.getiWordPair().getWord());

            // child not in document
            ArrayList<DocumentEvidence> limitedEvidenceIsNotIn = limitEvidenceList(
                    bestInformationGain.getEvidenceDatas(),
                    bestInformationGain.getiWordPair().getWord(),
                    false);
            System.out.println("DecisionTreeLearner.decisionTreeLearn limitedEvidenceIsNotIn: " + limitedEvidenceIsNotIn.size());
            int childEstimateIsNotIn = pointEstimate(limitedEvidenceIsNotIn);
            DecisionTree childDecisionTreeIsNotIn = new DecisionTree(childEstimateIsNotIn);
            localDecisionTree.setWordDoesNotExist(childDecisionTreeIsNotIn);
            IWordPair iWordPairChildIsNotIn = getBestFeature(limitedEvidenceIsNotIn);
            System.out.println("DecisionTreeLearner.decisionTreeLearn iWordPairChildIsNotIn: " + iWordPairChildIsNotIn);
            Leaf childLeafIsNotIn = new Leaf(childDecisionTreeIsNotIn, limitedEvidenceIsNotIn, iWordPairChildIsNotIn);
            priorityQueue.add(childLeafIsNotIn);

            // child in document
            ArrayList<DocumentEvidence> limitedEvidenceIsIn = limitEvidenceList(
                    bestInformationGain.getEvidenceDatas(),
                    bestInformationGain.getiWordPair().getWord(),
                    true);
            System.out.println("DecisionTreeLearner.decisionTreeLearn limitedEvidenceIsIn: " + limitedEvidenceIsIn.size());
            int childEstimateIsIn = pointEstimate(limitedEvidenceIsIn);
            DecisionTree childDecisionTreeIsIn = new DecisionTree(childEstimateIsIn);
            localDecisionTree.setWordExists(childDecisionTreeIsIn);
            IWordPair iWordPairChildIsIn = getBestFeature(limitedEvidenceIsIn);
            System.out.println("DecisionTreeLearner.decisionTreeLearn iWordPairChildIsIn: " + iWordPairChildIsIn);
            Leaf childLeafIsIn = new Leaf(childDecisionTreeIsIn, limitedEvidenceIsIn, iWordPairChildIsIn);
            priorityQueue.add(childLeafIsIn);
        }
        return decisionTree;
    }

        //Leaf leaf = new Leaf()

        /*
         get the pointEstimate using all evidence
         go through all words and find the one with the highest I value
         create priority queue with a single element, the one chosen above plus the DT
         while (havent expanded 100 times)
            get head of the PQ
            set the DT to be the chosen word to split on (from the chosen leaf head of PQ)
            create two children, one for each type: word is NOT and word IS in document
                get new evidence list that has word 0/1
                do a pointEstimate of this new evidence list, and set the child probability to be this number
                go through entire evidence list and find the best mximize split word using the I function
                create leaf that has:
                     store decision tree itself in the "leaf" construct so that you can keep building off of it
                     next best word and its associated I value
                     the new, smaller evidence list
                put the leaf in the PQ
        return the DT
        */

            /*
    procedure DecisionTreeLearner(X,Y,E)
        DT = pointEstimate(Y , E) = initial decision tree
        {X0, I } ? best feature and value for E
        PQ ? {DT, E, X0, I } = priority queue of leaves ranked by I
        while stopping criteria is not met do:
            {Sl, El, Xl, Il} ? leaf at the head of PQ
            for each value xi of Xl do
                Ei = all examples in El where Xl = xi
                {Xj, Ij} = best feature and value for Ei
                Ti ? pointEstimate(Y , Ei)
                insert {Ti, Ei, Xj, Ij} into PQ according to Ij
            end for
            Sl ?< Xl,T1, . . . , TN >
        end while
        return DT
    end procedure
     */

    private IWordPair getBestFeature(ArrayList<DocumentEvidence> documentEvidences) {
        Integer maxWord = 0;
        Double maxIValue = 0d;
        for(int i = 0; i < NUMBER_OF_WORDS; i++) {
            Double IValue = algorithmB(documentEvidences, i);
            //System.out.println("DecisionTreeLearner.getBestFeature IValue: " + IValue);
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
        if(evidenceData.isEmpty()) return 1d;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordA(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) - I(Esplit)
    public double algorithmB(ArrayList<DocumentEvidence> evidenceData, int wordToSplitOn) {
        if(evidenceData.isEmpty()) return 1d;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordB(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) = -P(atheism)*log(P(atheism)) - P(graphics)*log(P(graphics))
    private double informationGain(ArrayList<DocumentEvidence> documentEvidences) {
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
        double N1 = ((double)E1.size())/((double)documentEvidences.size());
        double N2 = ((double)E2.size())/((double)documentEvidences.size());
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

    /**
     * The number of times the given newsgroup appears given as a fraction of the entire evidence list
     */
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

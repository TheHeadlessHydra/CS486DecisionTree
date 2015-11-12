import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Serj on 11/11/2015.
 */
public class DecisionTreeLearner {
    private static final int ATHEISM = 1;
    private static final int GRAPHICS = 2;
    private static final int NUMBER_OF_WORDS = 3567;

    private final ArrayList<EvidenceData> evidenceData;
    private DecisionTree decisionTree = null;
    private PriorityQueue<Leaf> priorityQueue = new PriorityQueue<Leaf>(100, new LeafComparator());

    public DecisionTreeLearner(ArrayList<EvidenceData> evidenceData) {
        this.evidenceData = evidenceData;
    }

    public void learn() {

        int estimate = pointEstimate(evidenceData);
        DecisionTree decisionTree = new DecisionTree(estimate);



        //Leaf leaf = new Leaf()



        /*
         pointEstimate(E) to get the initial guess. This is done by just getting the one thats higher.
         go through all words and find the one with the highest I value
         priority queue =



         */

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

    }

    private IWordPair getBestFeature(ArrayList<EvidenceData> evidenceData) {
        for(int i = 0; i < NUMBER_OF_WORDS; i++) {
            
        }

    }

    // I(E) - I(Esplit)
    public double algorithmA(ArrayList<EvidenceData> evidenceData, int wordToSplitOn) {
        if(evidenceData.isEmpty()) return 1d;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordA(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) - I(Esplit)
    public double algorithmB(ArrayList<EvidenceData> evidenceData, int wordToSplitOn) {
        if(evidenceData.isEmpty()) return 1d;
        double Ie = informationGain(evidenceData);
        double IeSplit = informationGainOnWordB(evidenceData, wordToSplitOn);
        return Ie - IeSplit;
    }

    // I(E) = -P(atheism)*log(P(atheism)) - P(graphics)*log(P(graphics))
    private double informationGain(ArrayList<EvidenceData> evidenceData) {
        double IeP1 = -totalTimesLabelAppears(evidenceData, ATHEISM)*Math.log(totalTimesLabelAppears(evidenceData, ATHEISM));
        double IeP2 = -totalTimesLabelAppears(evidenceData, GRAPHICS)*Math.log(totalTimesLabelAppears(evidenceData, GRAPHICS));
        return IeP1 + IeP2;
    }

    // 0.5*I(E1) + 0.5*I(E2)
    private double informationGainOnWordA(ArrayList<EvidenceData> evidenceData, int wordToSplitOn) {
        double IeP1 = -timesWordAppears(evidenceData, wordToSplitOn, ATHEISM)*Math.log(timesWordAppears(evidenceData, wordToSplitOn, ATHEISM));
        double IeP2 = -timesWordAppears(evidenceData, wordToSplitOn, GRAPHICS)*Math.log(timesWordAppears(evidenceData, wordToSplitOn, GRAPHICS));
        return ((0.5d*IeP1) + (0.5d*IeP2));
    }

    // N1/N*I(E1) + N2/N*I(E2)
    private double informationGainOnWordB(ArrayList<EvidenceData> evidenceData, int wordToSplitOn) {
        double N1 = timesWordAppears(evidenceData, wordToSplitOn, ATHEISM);
        double IeP1 = -N1*Math.log(N1);
        double N2 = timesWordAppears(evidenceData, wordToSplitOn, GRAPHICS);
        double IeP2 = -N2*Math.log(N2);
        return ((N1*IeP1) + (N2*IeP2));
    }

    /**
     * The number of times the given label appears given as a fraction of the entire evidence list
     */
    private double totalTimesLabelAppears(ArrayList<EvidenceData> evidence, Integer label) {
        int number = 0;
        for(EvidenceData evidenceData : evidence) {
            Integer newsgroup = evidenceData.getLabel();
            if(newsgroup.equals(label)) {
                number++;
            }
        }
        return (number/(double)evidence.size());
    }

    /**
     * The number of times the wordToCheckFor appears under the labelToCheckAgainst given as a fraction of the entire
     * evidence list
     */
    private double timesWordAppears(ArrayList<EvidenceData> evidence, Integer wordToSplitOn, Integer labelToCheckAgainst) {
        int number = 0;
        for(EvidenceData evidenceData : evidence) {
            Integer word = evidenceData.getWordId();
            Integer label = evidenceData.getLabel();
            if(word.equals(wordToSplitOn)) {
                if(label.equals(labelToCheckAgainst)) {
                    number++;
                }
            }
        }
        return (number/(double)evidence.size());
    }

    private int pointEstimate(ArrayList<EvidenceData> evidence) {
        int numberOfAtheism = ATHEISM;
        int numberOfGraphics = GRAPHICS;

        for(EvidenceData evidenceData : evidence) {
            Integer newsgroup = evidenceData.getLabel();
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

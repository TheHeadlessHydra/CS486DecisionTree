import java.util.ArrayList;

/**
 * Created by Serj on 14/11/2015.
 */
public class NaiveBayesModel {
    public final static int NUMBER_OF_WORDS = 3567;
    public final static int ATHEISM = 1;
    public final static int GRAPHICS = 2;

    private Double probabilityOfAtheism = null;
    private ArrayList<Double> probabilityAtheismIfWordPresent = new ArrayList<Double>(NUMBER_OF_WORDS);
    private ArrayList<Double> probabilityGraphicsIfWordPresent = new ArrayList<Double>(NUMBER_OF_WORDS);

    public NaiveBayesModel() {
        resetProbabilities();
    }

    public void calculateBayesModel(ArrayList<DocumentEvidence> documentEvidences) {
        resetProbabilities();

        int numberOfAtheism = 0;
        ArrayList<Integer> numberOfTimesWordPresent = new ArrayList<Integer>(NUMBER_OF_WORDS);
        for(DocumentEvidence documentEvidence : documentEvidences) {
            ArrayList<Boolean> isWordInDocument = documentEvidence.getIsWordInDocument();
            boolean isAtheism = false;
            if(documentEvidence.getNewsgroupId() == ATHEISM) {
                isAtheism = true;
                numberOfAtheism++;
            }
            for(int i = 0; i < isWordInDocument.size(); i++) {
                if(isWordInDocument.get(i)) {
                    if(isAtheism) {
                        probabilityAtheismIfWordPresent.set(i, probabilityAtheismIfWordPresent.get(i)+1);
                    } else {
                        probabilityGraphicsIfWordPresent.set(i, probabilityGraphicsIfWordPresent.get(i)+1);
                    }
                }
            }
        }
        int numberOfGraphics = documentEvidences.size() - numberOfAtheism;
        for(int i = 0; i < probabilityAtheismIfWordPresent.size(); i++) {
            probabilityAtheismIfWordPresent.set(i, probabilityAtheismIfWordPresent.get(i)/(double)numberOfAtheism);
            probabilityGraphicsIfWordPresent.set(i, probabilityGraphicsIfWordPresent.get(i)/(double)numberOfGraphics);
        }
        probabilityOfAtheism = (double)numberOfAtheism/(double)documentEvidences.size();
    }

    public double testSpecific(boolean isTestingAtheism, DocumentEvidence documentEvidence) {
        if(probabilityOfAtheism == null) {
            throw new IllegalArgumentException("Must call calculateBayesModel with a model first");
        }

        double finalProbability = isTestingAtheism ? probabilityOfAtheism : (1-probabilityOfAtheism);
        for(int i = 0; i < documentEvidence.getIsWordInDocument().size(); i++) {
            double currentProbability = isTestingAtheism ? probabilityAtheismIfWordPresent.get(i) : probabilityGraphicsIfWordPresent.get(i);

            // if word is not present, must take 1 - theta
            if(!documentEvidence.getIsWordInDocument().get(i)) { // means word is in document evidence
                currentProbability = 1d - currentProbability;
            }

            finalProbability = finalProbability*currentProbability;
        }
        return finalProbability;
    }

    public int predictLabelGivenEvidence(DocumentEvidence documentEvidence) {
        if(probabilityOfAtheism == null) {
            throw new IllegalArgumentException("Must call calculateBayesModel with a model first");
        }

        double finalProbabilityAtheism = 1;
        double finalProbabilityGraphics = 1;
        for(int i = 0; i < documentEvidence.getIsWordInDocument().size(); i++) {
            finalProbabilityAtheism = finalProbabilityAtheism*probabilityAtheismIfWordPresent.get(i);
            finalProbabilityGraphics = finalProbabilityGraphics*(1-probabilityAtheismIfWordPresent.get(i));
        }

        double probabilityAtheism = probabilityOfAtheism*finalProbabilityAtheism;
        double probabilityGraphics = (1-probabilityOfAtheism)*finalProbabilityGraphics;

        return (probabilityAtheism > probabilityGraphics) ? ATHEISM : GRAPHICS;
    }

    private void resetProbabilities() {
        probabilityOfAtheism = null;
        probabilityAtheismIfWordPresent.clear();
        probabilityGraphicsIfWordPresent.clear();
        for(int i = 0; i < NUMBER_OF_WORDS; i++) {
            probabilityAtheismIfWordPresent.add(0d);
            probabilityGraphicsIfWordPresent.add(0d);
        }
    }


}

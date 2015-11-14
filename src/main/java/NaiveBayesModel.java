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
        System.out.println("NaiveBayesModel.calculateBayesModel probabilityAtheismIfWordPresent: " + probabilityAtheismIfWordPresent);
        System.out.println("NaiveBayesModel.calculateBayesModel probabilityGraphicsIfWordPresent: " + probabilityGraphicsIfWordPresent);
        int numberOfGraphics = documentEvidences.size() - numberOfAtheism;
        for(int i = 0; i < probabilityAtheismIfWordPresent.size(); i++) {
            probabilityAtheismIfWordPresent.set(i, (probabilityAtheismIfWordPresent.get(i)+1)/((double)numberOfAtheism + 2d));
            probabilityGraphicsIfWordPresent.set(i, (probabilityGraphicsIfWordPresent.get(i)+1)/((double)numberOfGraphics + 2d));
        }
        probabilityOfAtheism = (double)numberOfAtheism/(double)documentEvidences.size();

        System.out.println("NaiveBayesModel.calculateBayesModel probabilityOfAtheism: " + probabilityOfAtheism);
        System.out.println("NaiveBayesModel.calculateBayesModel probabilityAtheismIfWordPresent: " + probabilityAtheismIfWordPresent);
        System.out.println("NaiveBayesModel.calculateBayesModel probabilityGraphicsIfWordPresent: " + probabilityGraphicsIfWordPresent);
    }

    public int predictLabelGivenEvidence(DocumentEvidence documentEvidence) {
        if(probabilityOfAtheism == null) {
            throw new IllegalArgumentException("Must call calculateBayesModel with a model first");
        }

        double finalProbabilityAtheism = 0;
        double finalProbabilityGraphics = 0;
        for(int i = 0; i < documentEvidence.getIsWordInDocument().size(); i++) {
            double currentProbabilityAtheism = probabilityAtheismIfWordPresent.get(i);
            double currentProbabilityGraphics = probabilityGraphicsIfWordPresent.get(i);
            if(documentEvidence.getIsWordInDocument().get(i)) {
                finalProbabilityAtheism += Math.log(currentProbabilityAtheism);
                finalProbabilityGraphics += Math.log(currentProbabilityGraphics);
            } else {
                finalProbabilityAtheism += Math.log(1-currentProbabilityAtheism);
                finalProbabilityGraphics += Math.log(1-currentProbabilityGraphics);
            }
        }

        double probabilityAtheism = Math.log(probabilityOfAtheism)+finalProbabilityAtheism;
        double probabilityGraphics = Math.log(1 - probabilityOfAtheism)+finalProbabilityGraphics;

        return (probabilityAtheism > probabilityGraphics) ? ATHEISM : GRAPHICS;
    }

    public int 

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

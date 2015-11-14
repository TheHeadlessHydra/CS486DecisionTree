import java.io.*;
import java.util.ArrayList;

/**
 * @author Serj
 * Loads and tests the decision tree learner
 */
public class LoadAndTestData {
    public static void main(String[] args) {
        /**
         * Load the train data
         */
        InputStreamReader trainDataInput = new InputStreamReader(LoadAndTestData.class.getResourceAsStream("trainData.txt"));
        InputStreamReader trainLabelInput = new InputStreamReader(LoadAndTestData.class.getResourceAsStream("trainLabel.txt"));
        ArrayList<DocumentEvidence> documentEvidences = new ArrayList<DocumentEvidence>();
        try {
            BufferedReader labelReader = new BufferedReader(trainLabelInput);

            String line;
            while ((line = labelReader.readLine()) != null) {
                documentEvidences.add(new DocumentEvidence(Integer.parseInt(line)));
            }
        } catch(Exception e) {
            System.out.println("Failure when loading in label set: " + e);
            return;
        }
        try {
            BufferedReader dataReader = new BufferedReader(trainDataInput);

            String line;
            while ((line = dataReader.readLine()) != null) {
                String[] split = line.split("\t");
                int docId = Integer.parseInt(split[0]);
                int wordId = Integer.parseInt(split[1]);
                DocumentEvidence documentEvidence = documentEvidences.get(docId-1);
                documentEvidence.getIsWordInDocument().set(wordId, true);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failure when loading in data set: " + e);
            return;
        }

        /**
         * Load the test data
         */
        InputStreamReader testDataInput = new InputStreamReader(LoadAndTestData.class.getResourceAsStream("testData.txt"));
        InputStreamReader testLabelData = new InputStreamReader(LoadAndTestData.class.getResourceAsStream("testLabel.txt"));
        ArrayList<DocumentEvidence> testDocumentEvidences = new ArrayList<DocumentEvidence>();
        try {
            BufferedReader labelReader = new BufferedReader(testLabelData);

            String line;
            while ((line = labelReader.readLine()) != null) {
                testDocumentEvidences.add(new DocumentEvidence(Integer.parseInt(line)));
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failure when loading in label set: " + e);
            return;
        }
        try {
            BufferedReader dataReader = new BufferedReader(testDataInput);

            String line;
            while ((line = dataReader.readLine()) != null) {
                String[] split = line.split("\t");
                int docId = Integer.parseInt(split[0]);
                int wordId = Integer.parseInt(split[1]);
                DocumentEvidence documentEvidence = testDocumentEvidences.get(docId-1);
                documentEvidence.getIsWordInDocument().set(wordId, true);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failure when loading in data set: " + e);
            return;
        }

        /**
         * Below will print the decision tree up to 10 nodes. You must manually go into
         * DecisionTreeLearner and change the algorithm from algorithmA to algorithmB depending on which
         * one you wish to test.
         */
        /*
        DecisionTreeLearner decisionTreeLearner = new DecisionTreeLearner(10);
        DecisionTree decisionTree = decisionTreeLearner.decisionTreeLearn(documentEvidences);
        decisionTree.print(decisionTree, 0, "");
*/
        /**
         * Below is the code to generate the data needed for the graphs. You must manually go into
         * DecisionTreeLearner and change the algorithm from algorithmA to algorithmB depending on which
         * one you wish to test.
         */
/*
        // trained data tested against test data
        System.out.println("Against test data --------------------------------------------");
        ArrayList<Double> percentageCorrectTest= new ArrayList<Double>(100);
        for(int i = 1; i < 101; i++) {
            DecisionTreeLearner decisionTreeLearner = new DecisionTreeLearner(i);
            DecisionTree decisionTree = decisionTreeLearner.decisionTreeLearn(documentEvidences);
            //decisionTree.print(decisionTree, 0, "");
            int numberCorrect = 0;
            for(DocumentEvidence documentEvidence : testDocumentEvidences) {
                double prediction = decisionTreeLearner.testAgainstDecisionTree(decisionTree, documentEvidence.getIsWordInDocument());
                if(prediction == documentEvidence.getNewsgroupId()) {
                    numberCorrect++;
                }
            }
            double percCorrect = (double)numberCorrect/(double)documentEvidences.size();
            System.out.println(i + "  " + percCorrect);
            percentageCorrectTest.add(percCorrect);
        }

        System.out.println("==========================================================================================" );

        // trained data tested against train data
        System.out.println("Against train data --------------------------------------------");
        ArrayList<Double> percentageCorrectTraining = new ArrayList<Double>(100);
        for(int i = 1; i < 101; i++) {
            DecisionTreeLearner decisionTreeLearner = new DecisionTreeLearner(i);
            DecisionTree decisionTree = decisionTreeLearner.decisionTreeLearn(documentEvidences);
            //decisionTree.print(decisionTree, 0, "");
            int numberCorrect = 0;
            for(DocumentEvidence documentEvidence : documentEvidences) {
                double prediction = decisionTreeLearner.testAgainstDecisionTree(decisionTree, documentEvidence.getIsWordInDocument());
                if(prediction == documentEvidence.getNewsgroupId()) {
                    numberCorrect++;
                }
            }
            double percCorrect = (double)numberCorrect/(double)documentEvidences.size();
            System.out.println(i + "  " + percCorrect);
            percentageCorrectTest.add(percCorrect);
        }
        System.out.println("DONE---------------------------------------------------------------------------------------");
        */

        {
            NaiveBayesModel naiveBayesModel = new NaiveBayesModel();
            naiveBayesModel.calculateBayesModel(documentEvidences);
            int numberCorrect = 0;
            for (DocumentEvidence documentEvidence : documentEvidences) {
                int prediction = naiveBayesModel.predictLabelGivenEvidence(documentEvidence);
                if (prediction == documentEvidence.getNewsgroupId()) {
                    numberCorrect++;
                }
            }
            double percCorrect = (double) numberCorrect / (double) documentEvidences.size();
            System.out.println("perc correct train data: " + percCorrect);
        }
        {
            NaiveBayesModel naiveBayesModel = new NaiveBayesModel();
            naiveBayesModel.calculateBayesModel(documentEvidences);
            int numberCorrect = 0;
            for (DocumentEvidence documentEvidence : testDocumentEvidences) {
                int prediction = naiveBayesModel.predictLabelGivenEvidence(documentEvidence);
                if (prediction == documentEvidence.getNewsgroupId()) {
                    numberCorrect++;
                }
            }
            double percCorrect = (double) numberCorrect / (double) documentEvidences.size();
            System.out.println("perc correct test data: " + percCorrect);
        }

    }
}

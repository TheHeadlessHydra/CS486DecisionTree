import java.io.*;
import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class LoadData {
    public static void main(String[] args) {
        InputStreamReader testDataInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("trainData.txt"));
        InputStreamReader testLabelInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("trainLabel.txt"));

        ArrayList<DocumentEvidence> documentEvidences = new ArrayList<DocumentEvidence>();


        try {
            BufferedReader labelReader = new BufferedReader(testLabelInputStreamReader);

            String line;
            while ((line = labelReader.readLine()) != null) {
                documentEvidences.add(new DocumentEvidence(Integer.parseInt(line)));
            }
        } catch(Exception e) {
            System.out.println("Failure when loading in label set: " + e);
            return;
        }

        //System.out.println("LoadData.main documentEvidences.size: " + documentEvidences.size());

        try {
            BufferedReader dataReader = new BufferedReader(testDataInputStreamReader);

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
/*
        for(DocumentEvidence documentEvidence : documentEvidences) {
            System.out.println("LoadData.main document evidence type: " + documentEvidence.getNewsgroupId());
            System.out.println("LoadData.main documentEvidence.array.size:" + documentEvidence.getIsWordInDocument().size());
        }
*/

        DecisionTreeLearner decisionTreeLearner = new DecisionTreeLearner();
        DecisionTree decisionTree = decisionTreeLearner.decisionTreeLearn(documentEvidences);

        decisionTree.print(decisionTree, 0, "");
        System.out.println("LoadData.main decisionTree: " + decisionTree);



    }

}

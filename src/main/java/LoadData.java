import java.io.*;
import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class LoadData {
    public static void main(String[] args) {
        InputStreamReader testDataInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("testData.txt"));
        InputStreamReader testLabelInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("testLabel.txt"));

        ArrayList<Document> testEvidenceData = new ArrayList<Document>();
        ArrayList<Integer> testLabel = new ArrayList<Integer>();

        try {
            BufferedReader labelReader = new BufferedReader(testLabelInputStreamReader);

            String line;
            while ((line = labelReader.readLine()) != null) {
                testLabel.add(Integer.parseInt(line));
            }
        } catch(Exception e) {
            System.out.println("Failure when loading in test label set: " + e);
        }

        try {
            BufferedReader testDataReader = new BufferedReader(testDataInputStreamReader);

            String line;
            while ((line = testDataReader.readLine()) != null) {
                String[] split = line.split("\t");
                int docId = Integer.parseInt(split[0]);
                int wordId = Integer.parseInt(split[1]);
            }
        } catch(Exception e) {
            System.out.println("Failure when loading in test data set: " + e);
        }

        System.out.println("LoadData.main testEvidenceData: " + testEvidenceData);
        System.out.println("LoadData.main testLabel: " + testLabel);

    }

}

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Serj on 11/11/2015.
 */
public class LoadData {
    public static void main(String[] args) {
        InputStreamReader testDataInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("testData.txt"));
        InputStreamReader testLabelInputStreamReader = new InputStreamReader(LoadData.class.getResourceAsStream("testLabel.txt"));

        ArrayList<Data> testData = new ArrayList<Data>();

        try {
            BufferedReader testDataReader = new BufferedReader(testDataInputStreamReader);

            String line;
            while ((line = testDataReader.readLine()) != null) {
                String[] split = line.split("\t");
                int wordId = Integer.parseInt(split[0]);
                int docId = Integer.parseInt(split[1]);

                testData.add(new Data(wordId, docId));
            }
        } catch(Exception e) {
            System.out.println("Failure when loading in data set: " + e);
        }

        System.out.println("LoadData.main: " + testData);

    }

}

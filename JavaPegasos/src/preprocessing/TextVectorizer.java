//Purpose: Converts categorical values into numerical values

package preprocessing;

import data.DataPoint;
import java.util.*;

public class TextVectorizer {

    private Map<String, Integer> vocabulary =
            new LinkedHashMap<>(); //Stores key value pairs, and preserves the order

    private final int MAX_WORDS = 2000;

    public void buildVocabulary(List<DataPoint> data) { //from the values stored in DataPoint

        for (DataPoint point : data) {

            String text = point.getText().toLowerCase();

            String[] words = text.split("[^a-z0-9]+"); //splitting the sentence into words

            for (String word : words) {

                if (word.isEmpty()) {
                    continue;
                }

                if (!vocabulary.containsKey(word)
                        && vocabulary.size() < MAX_WORDS) {

                    vocabulary.put(word, vocabulary.size()); //word is not in vocab and does not exceed max length
                }
            }
        }
    }

    public double[] vectorize(String text) { //feature vector based on frequency

        double[] vector = new double[vocabulary.size()];

        String[] words =
                text.toLowerCase().split("[^a-z0-9]+");

        for (String word : words) {

            Integer index = vocabulary.get(word);

            if (index != null) {
                vector[index]++;
            }
        }

        return vector;
    }

    public int getVocabularySize() {

        return vocabulary.size();
    }
}
import java.io.*;
import java.util.*;

/**
 * @author PUT YOUR NAME HERE
 * @date Fall 2025, Compsci 201
 * 
 * Find related words via cosine similarity
 * of associated word vectors
 */

public class FindSimilarities {

    public FindSimilarities(){
        // initialize appropriate instance variables
    }

    /**
     * Find n words that are the closet/semantically similar to key
     * @param key is the word used for similarity search
     * @param n is number of words returned
     * @return top n WordDouble values (string and cosine similarity) ordered
     * by closeness (closest first in returned list)
     */
    public List<WordDouble> getSimilar(String key, int n) {
        return Collections.emptyList();
    }
    
    /**
     * Read the fileName which is in csv format, first value on a line
     * is a String, next N values are the vector for this word. Every line
     * in the file will be formatted with a String and then N values, all
     * comma separated.
     * 
     * Store values read in instance variables to make calling
     * getSimilar possible.
     * 
     * @param fileName contains properly formatted data
     * @throws IOException if file cannot be read
     */
    public void read(String fileName) throws IOException {
        // write code 
    }

    public static void main(String[] args) throws IOException {
        FindSimilarities rpp = new FindSimilarities();
        String fname = "data/w2vemo.csv";
        rpp.read(fname);
        List<WordDouble> list = rpp.getSimilar("worried", 3);
        System.out.println(list);
    }
}

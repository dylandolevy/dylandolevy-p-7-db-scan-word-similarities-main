
import java.io.*;
import java.util.*;

/**
 * Find related words via cosine similarity of associated word vectors
 */
public class FindSimilarities {

    // map from word to embedding vector
    private Map<String,double[]> embeddings;
    private int dimension;

    public FindSimilarities(){
        embeddings = new HashMap<>();
        dimension = -1;
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
        embeddings.clear();
        dimension = -1;
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.length() == 0) continue;
                String[] toks = line.split(",");
                if (toks.length < 2) continue;
                String word = toks[0].trim();
                int vecLen = toks.length - 1;
                if (dimension == -1) {
                    dimension = vecLen;
                } else if (vecLen != dimension) {
                    continue;
                }
                double[] v = new double[dimension];
                boolean ok = true;
                for (int i = 1; i < toks.length; i++) {
                    try {
                        v[i-1] = Double.parseDouble(toks[i].trim());
                    } catch (NumberFormatException nfe) {
                        ok = false;
                        break;
                    }
                }
                if (ok) embeddings.put(word, v);
            }
        }
    }

    /**
     * Return the top n most similar words to key (by cosine similarity).
     * If key not present, return an empty list.
     * If n <= 0 return empty list.
     *
     * @param key word to compare
     * @param n number of neighbors to return
     * @return list of WordDouble objects sorted by descending similarity
     */
    public List<WordDouble> getSimilar(String key, int n) {
        if (n <= 0) return Collections.emptyList();
        if (!embeddings.containsKey(key)) return Collections.emptyList();
        double[] kval = embeddings.get(key);
        PriorityQueue<WordDouble> pq = new PriorityQueue<>(Comparator.comparingDouble(w -> w.measure)); // min-heap
        for (Map.Entry<String,double[]> e : embeddings.entrySet()) {
            String w = e.getKey();
            if (w.equals(key)) continue;
            double[] vec = e.getValue();
            double sim = VectorUtils.cosineSimilarity(kval, vec);
            WordDouble wd = new WordDouble(w, sim);
            if (pq.size() < n) {
                pq.add(wd);
            } else if (sim > pq.peek().measure) {
                pq.poll();
                pq.add(wd);
            }
        }
        List<WordDouble> res = new ArrayList<>(pq);
        
        res.sort((a,b) -> Double.compare(b.measure, a.measure));
        return res;
    }

    public static void main(String[] args) throws IOException {
        FindSimilarities rpp = new FindSimilarities();
        String fname = "data/w2vemo.csv";
        rpp.read(fname);
        List<WordDouble> list = rpp.getSimilar("worried", 3);
        System.out.println(list);
    }
}

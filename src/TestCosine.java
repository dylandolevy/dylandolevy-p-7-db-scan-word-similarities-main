import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.IOException;

public class TestCosine {
    Map<String,WordDouble[]> testCases = new HashMap<>();

    @BeforeEach
    public void setup(){
        testCases.put("worried", 
                      new WordDouble[] {WordDouble.of("concerned",0.848),
                                        WordDouble.of("fearful",0.713), 
                                        WordDouble.of("apprehensive",0.692),
                                        WordDouble.of("alarmed",0.673), 
                                        WordDouble.of("scared",0.665)});
        testCases.put("fearful", new WordDouble[]{
                                 WordDouble.of("frightened",0.766),
                                 WordDouble.of("worried",0.713),
                                 WordDouble.of("scared",0.701),
                                 WordDouble.of("afraid",0.699),
                                 WordDouble.of("wary",0.654)});

        testCases.put("frightened", new WordDouble[]{
                        WordDouble.of("scared",0.827),
                        WordDouble.of("fearful",0.766),
                        WordDouble.of("afraid",0.648),
                        WordDouble.of("alarmed",0.641),
                        WordDouble.of("angry",0.592)});

        testCases.put("mad", new WordDouble[]{
                        WordDouble.of("crazy",0.739),
                        WordDouble.of("stupid",0.613),
                        WordDouble.of("angry",0.606),
                        WordDouble.of("scared",0.567),
                        WordDouble.of("jealous",0.563)});

        testCases.put("nervous", new WordDouble[]{
                        WordDouble.of("apprehensive",0.654),
                        WordDouble.of("anxious",0.642),
                        WordDouble.of("jumpy",0.634),
                        WordDouble.of("jittery",0.629),
                        WordDouble.of("scared",0.591)});
    }

    
    @Test
    public void testFive() throws IOException{
        FindSimilarities fs = new FindSimilarities();
        fs.read("data/w2vadjectives.csv");
        for(String key : testCases.keySet()) {
            WordDouble[] expected = testCases.get(key);
            List<WordDouble> v = fs.getSimilar(key,5);
            System.out.printf("*** got %s\n",v);
            for(int k=0; k < 5; k++) {
                assertEquals(expected[k].word,v.get(k).word,"mismatch string");
                assertEquals(expected[k].measure,v.get(k).measure,1e-3,"mismatch double");
                System.out.printf("got for %s, %s\n",key,v.get(k));
            } 
        }
    }

     @Test
    public void testTwo() throws IOException{
        FindSimilarities fs = new FindSimilarities();
        fs.read("data/w2vadjectives.csv");
        for(String key : testCases.keySet()) {
            WordDouble[] expected = testCases.get(key);
            List<WordDouble> v = fs.getSimilar(key,2);
            for(int k=0; k < 2; k++) {
                assertEquals(expected[k].word,v.get(k).word,"mismatch string");
                assertEquals(expected[k].measure,v.get(k).measure,1e-3,"mismatch double");
            } 
        }
    }

    @Test
    public void testAbsent() throws IOException{
        FindSimilarities fs = new FindSimilarities();
      
        fs.read("data/w2vadjectives.csv");
        String[] absent = {"abcd123","CRAZY","123abc456"};
        for(String s : absent) {
            List<WordDouble> list = fs.getSimilar(s, 20);
            assertEquals(list.size(), 0,"should be no entries for "+s);
        }
    }
}

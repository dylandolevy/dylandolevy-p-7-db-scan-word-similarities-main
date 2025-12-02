import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.IOException;

public class TestDBScan {

    @BeforeEach
    public void setup(){
        
    }

    
    @Test
    public void testA() throws IOException{
        DBScanClusters db = new DBScanClusters();
        Person201[] people = Person201Utilities.readFile("data/dataPerson201.txt");
        int[] expected = {5,6,6,6,7,8,10,11,21,25,31};
        List<Set<Person201>> list = db.getClusters(people, 100, 5);
        assertEquals(expected.length, list.size(), "wrong number of clusters");
        Collections.sort(list,(a,b)->a.size()-b.size());
        for(int k=0; k < list.size(); k++){
            assertEquals(expected[k], list.get(k).size(),"cluster size not correct");
        }    
    }

    @Test
    public void testB() throws IOException{
        DBScanClusters db = new DBScanClusters();
        Person201[] people = Person201Utilities.readFile("data/dataPerson201.txt");
        int[] expected = {10,17,29,31,42};
        List<Set<Person201>> list = db.getClusters(people, 200, 10);
        assertEquals(expected.length, list.size(), "wrong number of clusters");
        Collections.sort(list,(a,b)->a.size()-b.size());
        for(int k=0; k < list.size(); k++){
            assertEquals(expected[k], list.get(k).size(),"cluster size not correct");
        }
    }

    @Test
    public void testC() throws IOException{
        DBScanClusters db = new DBScanClusters();
        Person201[] people = Person201Utilities.readFile("data/dataPerson201.txt");
        int[] expected = {};
        List<Set<Person201>> list = db.getClusters(people, 10, 30);
        assertEquals(expected.length, list.size(), "wrong number of clusters");
        Collections.sort(list,(a,b)->a.size()-b.size());
        for(int k=0; k < list.size(); k++){
            assertEquals(expected[k], list.get(k).size(),"cluster size not correct");
        }
    }
}

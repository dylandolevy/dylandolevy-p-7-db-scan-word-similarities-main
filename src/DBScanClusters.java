import java.io.IOException;
import java.util.*;

/**
 * @author PUT YOUR NAME HERE 
 * @date Fall 2025, Compsci 201
 * 
 * Use the DBSCAN algorithm to find clusters of
 * geographic data
 */

public class DBScanClusters {

    
    public DBScanClusters(){
        // initialize appropriate instance variables
    }

    /**
     * Return list of clusters, each cluster is a List<Person201> object
     * of core and connected points given parameters.
     * @param people is source of geographic data
     * @param epsilon is minimum distance from core points to others in cluster
     * @param minPoints is minimum number of points to create a cluster
     * @return List of each cluster, each cluster contains core and edge-connected points
     */

    public List<Set<Person201>> getClusters(Person201[] people, 
                                             double epsilon, int minPoints) {

        return null;                                     
    }


    public static void main(String[] args) throws IOException {
        DBScanClusters ncc = new DBScanClusters();
        String fileName = "data/dataPerson201.txt";
        Person201[] people = Person201Utilities.readFile(fileName);
        
        System.out.printf("processing %d people\n",people.length);
        double epsilon = 100;
        int minPoints = 10;
        List<Set<Person201>> list = ncc.getClusters(people, epsilon, minPoints);
        for(int k=0; k < list.size(); k++) {
            Set<Person201> cluster = list.get(k);
            System.out.printf("%d:\t %d\n",k,cluster.size());
        }
        System.out.printf("----\n#clusters = %d\n",list.size());
    }
}

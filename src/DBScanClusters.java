
import java.io.IOException;
import java.util.*;

/**
 * Use the DBSCAN algorithm to find clusters of geographic data
 */
public class DBScanClusters {

    public DBScanClusters(){
        
    }

    /**
     * Return a list of clusters (each cluster is a Set of Person201 objects)
     * found using DBSCAN with parameters epsilon (distance threshold, same units
     * as returned by distance(...) which is kilometers) and minPoints.
     *
     * @param people array of Person201 points
     * @param epsilon distance threshold (kilometers)
     * @param minPoints minimum number of points to form a core point
     * @return list of clusters
     */
    public List<Set<Person201>> getClusters(Person201[] people, double epsilon, int minPoints) {
        List<Set<Person201>> clusters = new ArrayList<>();
        if (people == null || people.length == 0) return clusters;
        int n = people.length;
        final int UNVISITED = 0;
        final int NOISE = -1;
        int[] labels = new int[n]; 
        Arrays.fill(labels, UNVISITED);
        boolean[] visited = new boolean[n];
        int clusterId = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            List<Integer> neighbors = regionQuery(i, people, epsilon);
            if (neighbors.size() < minPoints) {
                labels[i] = NOISE;
            } else {
                clusterId++;
                Set<Person201> cluster = new HashSet<>();
                expandCluster(i, neighbors, clusterId, cluster, labels, visited, people, epsilon, minPoints);
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    private void expandCluster(int index, List<Integer> neighbors, int clusterId,
                               Set<Person201> cluster, int[] labels, boolean[] visited,
                               Person201[] people, double epsilon, int minPoints) {
        
        labels[index] = clusterId;
        cluster.add(people[index]);
        Queue<Integer> queue = new LinkedList<>();
        for (int nb : neighbors) queue.add(nb);

        while (!queue.isEmpty()) {
            int j = queue.poll();
            if (!visited[j]) {
                visited[j] = true;
                List<Integer> neighborsJ = regionQuery(j, people, epsilon);
                if (neighborsJ.size() >= minPoints) {
                    
                    for (int nj : neighborsJ) {
                        if (!queue.contains(nj)) queue.add(nj);
                    }
                }
            }
            if (labels[j] == 0 || labels[j] == -1) {
                labels[j] = clusterId;
                cluster.add(people[j]);
            }
        }
    }

    /**
     * Return list of indices of points within epsilon (inclusive) of point at index i.
     * Includes the index i itself.
     */
    private List<Integer> regionQuery(int i, Person201[] people, double epsilon) {
        List<Integer> neighbors = new ArrayList<>();
        Person201 p = people[i];
        for (int j = 0; j < people.length; j++) {
            double d = distance(p, people[j]);
            if (d <= epsilon) neighbors.add(j);
        }
        return neighbors;
    }

    /**
     * Haversine distance in kilometers between two Person201 points.
     */
    private double distance(Person201 a, Person201 b) {
        double lat1 = Math.toRadians(a.latitude());
        double lon1 = Math.toRadians(a.longitude());
        double lat2 = Math.toRadians(b.latitude());
        double lon2 = Math.toRadians(b.longitude());
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double sinDlat = Math.sin(dlat / 2.0);
        double sinDlon = Math.sin(dlon / 2.0);
        double aa = sinDlat * sinDlat + Math.cos(lat1) * Math.cos(lat2) * sinDlon * sinDlon;
        double c = 2 * Math.atan2(Math.sqrt(aa), Math.sqrt(1 - aa));
        double R = 6371.0;
        return R * c;
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

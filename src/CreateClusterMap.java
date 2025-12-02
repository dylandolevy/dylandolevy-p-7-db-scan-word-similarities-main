import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.google.gson.*;

/**
 * @author Owen Astrachan
 * @date Fall 2025 for Compsci 201
 */


public class CreateClusterMap {
    

     /**
     * Exports data in JSON format for visualization. Gson JSON format
     * is like a map (keys,values) so map created for each object
     * @param clusters is the clusters written in JSON format
     * @param fname is name of file being written
     * @throws IOException if writing to file fails
     */
    public static void exportJSON(List<Set<Person201>> clusters, String fname) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, Object>> output = new ArrayList<>();

        int clusterID = 1;
        for(int k=0; k < clusters.size(); k++) {
            for(Person201 p : clusters.get(k)) {
                Map<String,Object> entry = new HashMap<>();
                entry.put("cluster",clusterID);
                entry.put("name",p.name());
                entry.put("latitude",p.latitude());
                entry.put("longitude",p.longitude());
                output.add(entry);
            }
            clusterID += 1;         
        }
        try(Writer writer = new FileWriter(fname)){
            gson.toJson(output,writer);
        }
    }

    public static void main(String[] args) throws IOException {
       DBScanClusters dbscan = new DBScanClusters();
        String fileName = "data/dataPerson201.txt";
        Person201[] people = Person201Utilities.readFile(fileName);
        List<Set<Person201>> list = dbscan.getClusters(people, 200, 20);
        String visualizeFile = "ola.json"; 
        exportJSON(list, visualizeFile);
        int total = 0;
        for(int k=0; k < list.size(); k++) {
            System.out.printf("%d: %d\n",k,list.get(k).size());
            total += list.get(k).size();
        }
        System.out.printf("wrote for %d regions, total = %d\n",list.size(),total);
    }
}

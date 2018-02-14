package readCsvFiles;

import java.util.*;

public class StackOverflow {
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList<String>();
	public static ArrayList<Row> rowsFromCsvFile = new ArrayList<Row>();
	public static ArrayList<Row> rowsProcessed = new ArrayList<Row>();

	public static List<Set<Long>> Clusters = new ArrayList<>();
    public static int AnwserLength = 7;

	public static void main(String[] args) {

	    //Read CSV files
		String fileName = "With_Dup_IDs.csv";
        //String fileName = "test.csv";
		rowsFromCsvFile=CsvFileReader.readCsvFile(fileName);
		rowsProcessed=CodeParser.convertToSource(rowsFromCsvFile);

		// iterate all records
		for (Row row:rowsProcessed) {
		    // 1. Answer Filter
            if(!Answer_Filter(row.answersBody))
                continue;

            // 2. Cluster Locator
            Cluster_Locator(row.questionId, row.duplicate_Ids);


            // 3. Storage / Write result

		}


		for(Set<Long> cluster : Clusters){
		    System.out.println(cluster.toString());
		    System.out.println("====================================");
        }
        System.out.println(Clusters.size());
	}

	private static boolean Answer_Filter(String answer){
	    return answer.split("\\r?\\n").length >= AnwserLength;
    }

    private static void Cluster_Locator(long qID, String relateIDs){
        if (relateIDs.isEmpty()) {
            boolean found = false;
            for(Set<Long> cluster : Clusters){
                if(cluster.contains((qID))){
                    found = true;
                    break;
                }
            }

            if(!found) {
                Set<Long> tmp = new HashSet<>();
                tmp.add(qID);
                Clusters.add(tmp);
            }

        }else{
            String[] ID = relateIDs.split(",");
            Long[] IDs = new Long[ID.length];
            for (int i=0;i<ID.length;i++){
                IDs[i] = Long.parseLong(ID[i].trim());
            }

            for(Set<Long> cluster : Clusters){
                if(cluster.contains((qID))) {
                    cluster.addAll(new LinkedList(Arrays.asList(IDs)));
                    return;
                }
            }

            Set<Long> tmp = new HashSet<>();
            tmp.add(qID);
            boolean found = false;
            for(int i = 0; i< IDs.length; i++) {
                Iterator<Set<Long>> iter = Clusters.iterator();
                while (iter.hasNext()) {
                    Set<Long> cluster = iter.next();
                    if (cluster.contains(IDs[i])) {
                        found = true;
                        tmp.addAll(cluster);
                        Clusters.remove(cluster);
                        break;
                    }
                }

                if (!found)
                    tmp.add(IDs[i]);
            }
            Clusters.add(tmp);
        }
    }

}
package readCsvFiles;

import java.util.*;
import java.io.*;
import java.util.List;

class Answer{
    public Long id;
    public String body;

    Answer(Long ID, String BODY){
        id = ID;
        body = BODY;
    }
}

class Cluster{
    public String title;
    public Set<Answer> answer;

    Cluster(String Title){
        title = Title;
        answer = new HashSet<>();
    }
}

public class StackOverflow{
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList();
	public static ArrayList<Row> rowsFromCsvFile = new ArrayList();
	public static ArrayList<Row> rowsProcessed = new ArrayList();

	public static Map<Long, Cluster> questions = new HashMap();

    public static int AnwserLength = 7;
    public static List<String> Files = new ArrayList();

	public static void main(String[] args) {
        LoadFiles("./Query/C_2008-2017/");
        //LoadFiles("./Query/C#_2008-2017/");
        //LoadFiles("./Query/Java_2008-2017/");
        //LoadFiles("./Query/Python_2008-2017/");

        int total_download = 0;
        int filtered_answer = 0;

        for(String file : Files) {

            //Read CSV files
            String fileName = file;
            rowsFromCsvFile = CsvFileReader.readCsvFile(fileName);
            rowsProcessed = CodeParser.convertToSource(rowsFromCsvFile);

            // iterate all records

            for (Row row : rowsProcessed) {
                total_download++;

                // 0. Answer Filter
                if (!Answer_Filter(row.answersBody))
                    continue;

                // 1. Store answers to Cluster
                if (!questions.containsKey(row.questionId))
                    questions.put(row.questionId,new Cluster(row.question_title));

                questions.get(row.questionId).answer.add(new Answer(row.answerId,row.answersBody));
                filtered_answer++;
            }
        }

        // 3. Solution with 2 or more answers only
        filtered_answer = AnswerCounterFilter(2,filtered_answer);
        // 4. Storage / Write result
        StoreResults("Class","./Results/CFunction/","c");

        System.out.println("Total Questions: " + total_download);
        System.out.println("Filtered Questions: " + filtered_answer);
        System.out.println("Cluster Count: " + questions.size());
	}

	private static boolean Answer_Filter(String answer){
	    return answer.split("\\r?\\n").length >= AnwserLength;
    }

    private static void StoreResults(String type, String folder, String extension){
	    if(type.equals("CSV")){
            System.out.println("Under Construction");
        }

        if(type.equals("XML")){
	        ExportXML();
        }

        if(type.equals("Class")){
            ExportClass(folder,extension);
        }

        if(type.equals("SQL")){
            System.out.println("Under Construction");
        }
    }

    private static void ExportXML(){
	    /*
	    try {
            XMLBuilder builder = XMLBuilder.create("Clusters");
            for(Set<Long> cluster :Clusters){
                XMLBuilder c = builder.element("cluster");
                for(Long question : cluster){
                    XMLBuilder q = c.element("question");
                    q.attribute("questionID", question.toString());
                    q.attribute("questionTitle", ID_Question.get(question));

                    if(!question_answer.containsKey(question)) continue;
                    for(Long answer_ID : question_answer.get(question)){
                        q.element("Answer").cdata(answer_body.get(answer_ID).replaceAll("[^\\p{ASCII}]", ""));
                        //q.cdata("Answer").text(answer_body.get(answer_ID));
                        //replaceAll("\\r?\\n","\r\n")
                    }
                }
            }
            Properties outputProperties = new Properties();
            outputProperties.put(OutputKeys.INDENT, "yes");
            PrintWriter writer = new PrintWriter(new FileOutputStream("Clusters.xml"));
            builder.toWriter(writer,outputProperties);
        }
        catch(Exception e){System.out.println(e.toString());}
        */
    }

    private static int AnswerCounterFilter(int minimal, int count){
	    List<Long> found = new ArrayList<>();

	    for (Map.Entry<Long,Cluster> tmp: questions.entrySet()){
	        if(tmp.getValue().answer.size() < minimal)
	            found.add(tmp.getKey());
        }

        for(Long tmp :found){
            count -= questions.get(tmp).answer.size();
	        questions.remove(tmp);
        }

        return count;
    }

    private static void LoadFiles(String dir){
	    File folder = new File(dir);
	    File[] ListFiles = folder.listFiles();
	    for(File tmp :ListFiles){
	        if(tmp.isFile()){
                Files.add(dir+tmp.getName());
            }
        }
    }

    private static void ExportClass(String folder, String extension){
	    try {
            for (Map.Entry<Long, Cluster> tmp : questions.entrySet()) {
                for (Answer a : tmp.getValue().answer) {
                    String file = folder + tmp.getKey().toString() + "_" + a.id.toString() + "." + extension;
                    String path = file;
                    File f = new File(path);
                    f.getParentFile().mkdirs();
                    f.createNewFile();

                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    writer.println(a.body);
                    writer.close();
                }
            }
        }catch(Exception e){System.out.println(e.getMessage());};

    }
}
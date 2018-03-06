package readCsvFiles;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.jamesmurty.utils.XMLBuilder;
import javafx.util.Pair;
import org.omg.CORBA.Environment;

import javax.swing.*;
import javax.xml.transform.OutputKeys;
import java.awt.*;
import java.lang.reflect.Executable;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.util.List;



public class StackOverflow{
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList();
	public static ArrayList<Row> rowsFromCsvFile = new ArrayList();
	public static ArrayList<Row> rowsProcessed = new ArrayList();

	public static List<Set<Long>> Clusters = new ArrayList();
	public static Map<Long, String> answer_body = new HashMap();
    public static Map<Long, Set<Long>> question_answer = new HashMap();
    public static Map<Long, String> ID_Question = new HashMap<>();

    public static int AnwserLength = 20;
    public static int index = 0;

	public static void main(String[] args) {

	    //Read CSV files
		String fileName = "SO_2010-2017.csv";
        //String fileName = "test.csv";
		rowsFromCsvFile=CsvFileReader.readCsvFile(fileName);
		rowsProcessed=CodeParser.convertToSource(rowsFromCsvFile);

		// iterate all records

		for (Row row:rowsProcessed) {
            // 0. Answer Filter
            if(!Answer_Filter(row.answersBody))
                continue;

            // 1. Store answers to List
            FetchAnswer(row);

            // 2. Cluster Locator
            Cluster_Locator(row.questionId, row.duplicate_Ids);
		}

		// 3. Solution with 2 or more answers only
        AnswerCounterFilter(2);

        // 4. Storage / Write result
        StoreResults("XML");

		// 5. Create GUI
        //viewPane();
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

    private static void StoreResults(String type){
	    if(type.equals("CSV")){
	        Export2CSV();
        }

        if(type.equals("XML")){
	        ExportXML();
        }

        if(type.equals("SQL")){
            System.out.println("Under Construction");
        }
    }

    private static void Export2CSV(){
	    if (Clusters.size() == 0){
	        System.out.println("No Clusters");
	        return;
        }

        try {
            PrintWriter pw = new PrintWriter(new File("out.csv"));
            StringBuilder sb = new StringBuilder();
            for(Set<Long> cluster : Clusters){
                for (Long num : cluster){
                    sb.append(num);
                    sb.append(',');
                }
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        }catch(Exception e){
	        System.out.println(e.getMessage());
        }
    }

    private static void ExportXML(){
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
                        q.element("Answer").cdata(answer_body.get(answer_ID));
                    }
                }
            }
            Properties outputProperties = new Properties();
            outputProperties.put(OutputKeys.INDENT, "yes");
            PrintWriter writer = new PrintWriter(new FileOutputStream("Clusters.xml"));
            builder.toWriter(writer,outputProperties);
        }
        catch(Exception e){System.out.println(e.toString());}

    }

    private static String GetAnswers(Long questionID){
	    String rtv = "";
        for( Long tmp:question_answer.get(questionID)){
            rtv += tmp.toString() +",";
        }
        return rtv;
    }

    private static Pair ClusterGetAnswer(Set<Long> cluster){
	    //System.out.println();
        String rtv = "";
	    for(Long tmp : cluster){
            rtv += GetAnswers(tmp);
        }
        return new Pair<String, Set<Long>>(rtv,cluster);
    }

    private static String AnsersCodeFragment(Pair<String, Set<Long>> pair){
	    String[] l = pair.getKey().split(",");

	    String rtv = "Questions in Cluster: \n";
	    for (Long q : pair.getValue()){
            rtv+=(ID_Question.get(q)+"\n");
        }
	    for (String tmp : l){
            if(!Answer_Filter(answer_body.get(Long.parseLong(tmp))))
                continue;
            rtv += "\n======================Answer "+tmp+"=============================\n";
            try {
                //CompilationUnit cu = JavaParser.parse(answer_body.get(Long.parseLong(tmp)));
                //rtv += cu.toString();
                rtv += answer_body.get(Long.parseLong(tmp));

            }
            catch(Exception e){
                rtv += "**Filtered by JavaParser";
            }

        }
        return rtv;
    }

    private static void AnswerCounterFilter(int minimal){
	    List<Set<Long>> found = new ArrayList<>();
        for(Set<Long> cluster : Clusters){
            int count = 0;
            for (Long question : cluster){
                if(question_answer.containsKey((question)))
                    count += question_answer.get(question).size();
            }
            if(count < minimal){
                found.add(cluster);
            }
        }
        Clusters.removeAll(found);
    }

    private static void FetchAnswer(Row row){
        ID_Question.put(row.questionId,row.question_title);
        answer_body.put(row.answerId,row.answersBody);

        if(question_answer.containsKey(row.questionId)){
            question_answer.get(row.questionId).add(row.answerId);
        }else{
            Set<Long> tmp = new HashSet();
            tmp.add(row.answerId);
            question_answer.put(row.questionId,tmp);
        }
    }

    private static void viewPane(){

        JFrame f = new JFrame("Cluster View");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea text = new JTextArea(AnsersCodeFragment(ClusterGetAnswer(Clusters.get(index))));
        JScrollPane scroll = new JScrollPane (text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        text.setLineWrap(true);
        JButton btn_next = new JButton("Next");
        JButton btn_last = new JButton("Last");
        btn_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index+1 == Clusters.size()){return;}
                index += 1;
                text.setText("");
                text.append(AnsersCodeFragment(ClusterGetAnswer(Clusters.get(index))));
            }
        });

        btn_last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index == 0){return;}
                index -= 1;
                text.setText("");
                text.append(AnsersCodeFragment(ClusterGetAnswer(Clusters.get(index))));
            }
        });


        //f.add(text, BorderLayout.CENTER);
        f.add(scroll,BorderLayout.CENTER);
        f.add(btn_last, BorderLayout.LINE_START);
        f.add(btn_next, BorderLayout.LINE_END);

        f.setSize(800, 500);
        f.setVisible(true);
    }

}
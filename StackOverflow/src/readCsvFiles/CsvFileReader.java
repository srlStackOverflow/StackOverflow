package readCsvFiles;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
/**
 * @author Farouq
 *
 */
public class CsvFileReader {
    
    //CSV file header
    private static final String [] FILE_HEADER_MAPPING = {"Answeres", "Score", "Post Link", "Question Tags", "Body", "AnswerCount", "Id", "Dup_IDs", "Title"};
    
    //Student attributes
    private static final String answer = "Answeres";
    private static final String score = "Score";
    private static final String answer_Id = "Post Link";
    private static final String question_Tags = "Question Tags"; 
    private static final String Question_Body = "Body";
    private static final String answer_Count = "AnswerCount"; 
    private static final String question_Id = "Id";
    private static final String duplicate_Ids = "Dup_IDs";
    private static final String question_title = "Title";
    
    // Read csv file and return its contenet in an array list
    // define a row, a class to store row content from the csv file 
    public static   ArrayList<Row> readCsvFile(String fileName) {

        ArrayList<Row> rows = new ArrayList<Row>();
        
        FileReader fileReader = null;
        
        CSVParser csvFileParser = null;
        
        //Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
     
        try {
            
            //Create a new list of student to be filled by CSV file data 
 
            
            //initialize FileReader object
            fileReader = new FileReader(fileName);
            
            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            
            //Get a list of CSV file records
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
           
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                //Create a new student object and fill his data
                Row row = new Row(record.get(answer), Integer.parseInt(record.get(score)),
                        Long.parseLong(record.get(answer_Id)), record.get(question_Tags), 
                                     record.get(Question_Body),Integer.parseInt(record.get(answer_Count)),Long.parseLong(record.get(question_Id)),record.get(duplicate_Ids), record.get(question_title) );
                rows.add(row);  
            }

            System.out.println("Number of record extracted from the CSV file(s): "+ rows.size());
//            
//            for (int i = 0; i < 2; i++) {
//              
//               System.out.println("Row number: "+i+"  answer Id: "+  rows.get(i).answerId + " Answer body: \n "+rows.get(i).answersBody);
//            }

          
        } 
        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }
        return rows;

    }

}
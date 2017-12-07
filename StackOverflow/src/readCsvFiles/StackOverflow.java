package readCsvFiles;




import java.util.ArrayList;



public class StackOverflow {
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList<String>(); 
	
	public static ArrayList<Row> rows = new ArrayList<Row>();
	public static ArrayList<Row> rowsProcessed = new ArrayList<Row>();

	
	//

	public static void main(String[] args) {

	

		String fileName= "java Answers.csv";
		
		rows=CsvFileReader.readCsvFile(fileName);
		rowsProcessed=CodeParser.convertToSource(rows);
		


	}

public static void start() throws Exception
{

	files=getFileNames();

	//
	step1_Read_Table_Data(files);
	//step2_CleanUp();
}




public static ArrayList<String> getFileNames()
{
	
	ArrayList<String> files= new ArrayList<String>();
	return files;
}

public static void step1_Read_Table_Data(ArrayList<String> files)
{


}

}
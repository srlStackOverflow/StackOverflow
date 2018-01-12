package readCsvFiles;




import java.util.ArrayList;



public class StackOverflow {
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList<String>(); 
	
	public static ArrayList<Row> rowsFromCsvFile = new ArrayList<Row>();
	public static ArrayList<Row> rowsProcessed = new ArrayList<Row>();

	
	//

	public static void main(String[] args) {

	

		String fileName= "java Answers.csv";
		rowsFromCsvFile=CsvFileReader.readCsvFile(fileName);
		


		rowsProcessed=CodeParser.convertToSource(rowsFromCsvFile);
		
		System.out.println(rowsProcessed.size());
		
		int i=0;
		for (Row row:rowsProcessed) {
			System.out.println(row.answerId+"  "+ row.questionId+"  \n"+ row.getAnswerBody());
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
			i++;
			if (i>30) break;
		}
		


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
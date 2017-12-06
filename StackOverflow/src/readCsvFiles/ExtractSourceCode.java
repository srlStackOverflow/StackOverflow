package readCsvFiles;

import org.apache.commons.csv.*;


import java.util.ArrayList;



public class ExtractSourceCode {
	
	//List of All csv and excel files in the folder
	public static ArrayList<String> files= new ArrayList<String>(); 
	// Asked questions on SO, We save Question ID and Title
	public static ArrayList<ArrayList<String>> Questions = new ArrayList<ArrayList<String>>(); 
	// All answers that contain answers. For each question entry we maintain answers and scores. 
	public static ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
	
	//

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test test
		//Second test
		//Hello there 
		System.out.println("Test");
		Answers answer1= new Answers();
		answer1.id=100;
		answer1.code="Java code";
		ArrayList<Answers> answers = new ArrayList<Answers>();
		answers.add(answer1);
		Questions question1= new Questions(12,"my question",23,answers);
		
		System.out.println(question1.id);
		System.out.println(question1.title);
		System.out.println(question1.answerCount);
		System.out.println(question1.answers.get(0).code);
	

		

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
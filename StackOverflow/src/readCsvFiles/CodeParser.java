package readCsvFiles;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CodeParser {

	public static 	ArrayList<Row> convertToSource(ArrayList<Row> rows) {
		ArrayList<Row> rowsProcessed = new ArrayList<Row>();

		int i=0;
		for (Row row:rows) {
			Document doc = Jsoup.parse(row.getAnswerBody());
			Elements codeElements = doc.select("code");
		//	String allCodes = codeElements.text();
//			i++;

		//	if (i==9) break;
		
//			System.out.print("Answer number = "+i +"   Answer score= "+row.answerScore);
//			System.out.println("  number of code fragments: "+codeElements.size()); 
//			System.out.println("========================================================================================");

// print all answers
//			for( int c=0; c<codeElements.size();c++) {
//				int f=c+1;
//				System.out.println("-------------------------------Code fragment number "+f +"---------------------------------");
//				System.out.println(codeElements.get(c).text());
//			}
			
			String longestCode=findLongestElement(codeElements);
//			System.out.println(longestCode);
//			System.out.println();

			row.setAnswerBody(longestCode);
			rowsProcessed.add(row);

		}


		return rowsProcessed;
	}

	
	
	public static 	String findLongestElement(Elements codeElements) {
		
		String longestCode="";
		int length=0;
			
		for(int c=0; c<codeElements.size();c++) {
			if ( length < codeElements.get(c).toString().length() ) {
				length=codeElements.get(c).toString().length();
				longestCode=codeElements.get(c).text();
			}
		}	
		
		return longestCode;
	}
	
	
}

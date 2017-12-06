/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readCsvFiles;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Rumali
 * Please import jxl.jar to run this script.
 */
public class ExcelParser {
    private String inputFile;
//    String type array for storing all the values having tag <code>
    String[] data = null;
//    String type array for storing corresponding questions of the codes
    String[] data2 = null;
    
    public void setInputFile(String inputFile) {
        
        this.inputFile = inputFile;
    }
    
    public String[] read() throws IOException {
        
        File inputWorkbook = new File(inputFile);
        Workbook w;
        
        try {
            
            w = Workbook.getWorkbook(inputWorkbook);
//            Getting the first sheet
            Sheet sheet = w.getSheet(0);
//            Gettting the number of rows in data and creating array of same size
            data = new String[sheet.getRows()];
            data2 = new String[sheet.getRows()];
//            ===================================================================================
//            Tried few Regex with your data, but did not work. But worked on any self-made data.
//            ===================================================================================
//            String pattern = "(?i)(<code.*?>)(.+?)()";
//            String pattern = ".*\\b<code>\\b.*";
//            String pattern = "(?i).*?\\b<code>\\b.*?";
            String pattern = ".*?\\bcode\\b.*?";
            String pattern2 = "/.*/";
            for(int j=0; j<sheet.getRows(); j++) {
                int k = j;
               
//                Getting values of first column. 
//                *Takes value as column, row*
                Cell cell = sheet.getCell(0, j);
                String s = cell.getContents();
//                Getting values of fourth column
                Cell cell1 = sheet.getCell(3, j);
//                Checking whether the strings from first cell have name code
                int indexChecker = s.indexOf("code");
//                ======================================
//                Below portion used for several testing
//                ======================================
//                System.out.println(s);
//                System.out.println("code index: "+s.indexOf("code"));
                
//                System.out.println("new string");
//                boolean result = s.matches(".*?\\bcode\\b.*?");
//                System.out.println(result);
//                if (cell.getContents().matches(pattern)) {

//                =========================================================
                
//                If 'code' tag exists, taking the string into data array
                if (indexChecker>=0) {
                    data[j] = cell.getContents(); 
                    
                }
//                Taking the corresponding question of the code
                if (data[j]!=null)
                    data2[j] = cell1.getContents();
                else data2[j] = null;               
            }
//            Showing the result
            for (int j=0; j<data.length; j++){
                System.out.println(j+1+". Answer: "+data[j]);
                System.out.println("Question: "+data2[j]);
                System.out.println("");
                
            }
        }
        catch (BiffException e) {
            e.printStackTrace();
        }
    return data;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ExcelParser test = new ExcelParser();
        test.setInputFile("JavaAnswers.xls");
        test.read();

    }
    
}

package readCsvFiles;

import java.util.ArrayList;

public class Questions {
	public long id;
	public String title;
	public int answerCount;
	
  public  ArrayList<Answers> answers;
	
  public Questions() {
	  this.id=0;
	  this.title=null;
	  this.answerCount=0;
	  this.answers=new ArrayList<Answers>(); 
  }
  
  public Questions(long id, String title, int count, ArrayList<Answers> answers ) {
	  this.id=id;
	  this.title=title;
	  this.answerCount=count;
	  this.answers=answers; 
  }
  
}

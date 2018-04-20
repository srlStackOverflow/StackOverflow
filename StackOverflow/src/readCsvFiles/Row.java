package readCsvFiles;

public class Row {
	public String answersBody;
	//public int answerScore;
	public long answerId;
	//public String questionTags;
	//public String questionBody;
	//public int answerCount;
	public long questionId;
	//public String duplicate_Ids;
	public String question_title;

	public Row(String answers, long answer_Id, long q_Id, String question_title) {
		super();
		this.answersBody=answers;
		this.answerId=answer_Id;
		this.questionId=q_Id;
		this.question_title = question_title;

	}

	public Row(Object object, Object object2, Object object3, Object object4, Object object5, Object object6,
			Object object7, Object object8, Object object9) {
		// TODO Auto-generated constructor stub
		this.answersBody=object.toString();
		//this.answerScore=Integer.parseInt(object2.toString());
		this.answerId=Long.parseLong(object3.toString());
		//this.questionTags=object4.toString();
		//this.questionBody=object5.toString();
		//this.answerCount=Integer.parseInt(object6.toString());
		this.questionId=Long.parseLong(object7.toString());
		//this.duplicate_Ids = object8.toString();
		this.question_title = object9.toString();
		
	}
	
	public  String getAnswerBody() {
		return answersBody;
		
	}
	public  void setAnswerBody(String body) {
		answersBody=body;
		
	}
	

}

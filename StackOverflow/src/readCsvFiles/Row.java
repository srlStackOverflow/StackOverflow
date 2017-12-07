package readCsvFiles;

public class Row {
	public String answersBody;
	public int answerScore;
	public long answerId;
	public String questionTags;
	public String questionBody;
	public int answerCount;
	public long questionId;
	
	public Row(String answers, int score, long answer_Id, String tags, String body,int answerCount,long q_Id ) {
		super();
		this.answersBody=answers;
		this.answerScore=score;
		this.answerId=answer_Id;
		this.questionTags=tags;
		this.questionBody=body;
		this.answerCount=answerCount;
		this.questionId=q_Id;		
		
	}

	public Row(Object object, Object object2, Object object3, Object object4, Object object5, Object object6,
			Object object7) {
		// TODO Auto-generated constructor stub
		this.answersBody=object.toString();
		this.answerScore=Integer.parseInt(object2.toString());
		this.answerId=Long.parseLong(object3.toString());
		this.questionTags=object4.toString();
		this.questionBody=object5.toString();
		this.answerCount=Integer.parseInt(object6.toString());
		this.questionId=Long.parseLong(object7.toString());
		
	}
	
	public  String getAnswerBody() {
		return answersBody;
		
	}
	public  void setAnswerBody(String body) {
		answersBody=body;
		
	}
	

}

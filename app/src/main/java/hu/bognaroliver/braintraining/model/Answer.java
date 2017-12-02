package hu.bognaroliver.braintraining.model;

/**
 *  Answer class representing an answer in Question object.
 */
public class Answer
{

	/**
		* @param answerId shows which answer is example selected
		* @param questionId shows which question is contain this answer
	 	* @param trueAnswer currently answer is right or not by the value is set in answer text file.
		* @param answerContent the content of the answer
	*/
	private int answerId;
	private int questionId;
	private int trueAnswer;
	private String answerContent;

	public Answer()
	{
		this.answerId = 0;
		this.questionId = 0;
		this.answerContent = null;
		this.trueAnswer = 0;
	}
	public Answer(int answerId, int questionId, int trueAnswer, String answerContent)
	{
		setAnswerId(answerId);
		setQuestionId(questionId);
		setAnswerContent(answerContent);
		setTrueAnswer(trueAnswer);
	}

	public int getAnswerId()
	{
		return answerId;
	}

	public void setAnswerId(int answerId)
	{
		if (answerId >= 0 && answerId <= 100)
			this.answerId = answerId;
	}

	public int getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(int questionId)
	{
		if (questionId >= 0 && questionId <= 100)
			this.questionId = questionId;
	}

	public String getAnswerContent()
	{
		return answerContent;
	}

	public void setAnswerContent(String answerContent)
	{
		if (!answerContent.isEmpty())
			this.answerContent = answerContent;
	}

	public boolean isTrueAnswer()
	{
		if (trueAnswer == 1)
			return true;
		else
			return false;
	}

	public void setTrueAnswer(int trueAnswer)
	{
		this.trueAnswer = trueAnswer;
	}

}

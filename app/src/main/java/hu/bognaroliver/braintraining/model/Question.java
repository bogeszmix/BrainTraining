package hu.bognaroliver.braintraining.model;

import java.util.ArrayList;

/**
 * 	Question class represting a question and charged with answers.
 */
public class Question
{

	/**
		* @param questionId shows which question is selected
		* @param questionContent shows the content of the question
		* @param answer the list of answer of the question
	*/
	private int questionId;
	private String questionContent;
	private ArrayList<Answer> answers;

	public Question()
	{
		this.questionId = 0;
		this.questionContent = null;
		this.answers = null;
	}

	public Question(int questionId, String questionContent, ArrayList<Answer> answers)
	{
		setQuestionId(questionId);
		setQuestionContent(questionContent);
		setAnswers(answers);
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

	public String getQuestionContent()
	{
		return questionContent;
	}

	public void setQuestionContent(String questionContent)
	{
		if (!questionContent.isEmpty())
			this.questionContent = questionContent;
	}

	public ArrayList<Answer> getAnswers()
	{
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers)
	{
		if (!answers.equals(null))
			this.answers = answers;
	}
}

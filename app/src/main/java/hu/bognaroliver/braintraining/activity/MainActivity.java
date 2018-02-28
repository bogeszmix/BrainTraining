package hu.bognaroliver.braintraining.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import hu.bognaroliver.braintraining.R;
import hu.bognaroliver.braintraining.model.Answer;
import hu.bognaroliver.braintraining.model.Question;

import static hu.bognaroliver.braintraining.data.FileScanner.readAnswerTxtFile;
import static hu.bognaroliver.braintraining.data.FileScanner.readQuestionTxtFile;


public class MainActivity extends AppCompatActivity
{
	//Java variables
	private ArrayList<Question> quizList = new ArrayList<Question>();
	private ArrayList<Question> questionList = null;
	private ArrayList<Answer> answers = null;
	private int i = 0;    //Current question number
	private int rightAnswerScore = 0;

	//Reference to views
	private LinearLayout mainSceneLayout;
	private LinearLayout firstAnswerTap;
	private LinearLayout secondAnswerTap;
	private LinearLayout thirdAnswerTap;
	private LinearLayout fourthAnswerTap;

	private TextView roundCounter;
	private TextView questionNumber;
	private TextView questionTxt;

	private TextView firstAnswerTxt;
	private TextView secondAnswerTxt;
	private TextView thirdAnswerTxt;
	private TextView fourthAnswerTxt;

	private RadioButton firstAnswerRadioBtn;
	private RadioButton secondAnswerRadioBtn;
	private RadioButton thirdAnswerRadioBtn;
	private RadioButton fourthAnswerRadioBtn;

	private Button btnBack;
	private Button btnNext;
	private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		inflateViewWithElements();
		setValuesForViews();
	}

	/**
		* Add new questions to the quiz list. The function merge the quests to their answers.
	 	* Then make a new final list filled with Question objects.
	 */
	private void setQuizList()
	{
		questionList = readQuestionTxtFile(getApplicationContext(), "texts/questions.txt");
		answers = readAnswerTxtFile(getApplicationContext(), "texts/answers.txt");

		for (int i = 0; i < questionList.size(); i++)
		{
			ArrayList<Answer> aList = new ArrayList<Answer>();

			for (int j = 0; j < answers.size(); j++)
			{
				if (questionList.get(i).getQuestionId() == answers.get(j).getQuestionId())
				{
					aList.add(answers.get(j));
				}
			}

			questionList.get(i).setAnswers(aList);
			quizList.add(questionList.get(i));
		}

		//Randomize the items order in the array, and maximize the item quantity at 10
		long seed = System.nanoTime();
		Collections.shuffle(quizList, new Random(seed));
		quizList.subList(0, 9);

	}

	/**
		* Inflate the views and get reference for them
	 */
	private void inflateViewWithElements()
	{
		mainSceneLayout = (LinearLayout) findViewById(R.id.main_scene_layout);
		firstAnswerTap = (LinearLayout) findViewById(R.id.first_answer_tap);
		secondAnswerTap = (LinearLayout) findViewById(R.id.second_answer_tap);
		thirdAnswerTap = (LinearLayout) findViewById(R.id.third_answer_tap);
		fourthAnswerTap = (LinearLayout) findViewById(R.id.fourth_answer_tap);
		roundCounter = (TextView) findViewById(R.id.round_counter);
		questionNumber = (TextView) findViewById(R.id.question_number);
		questionTxt = (TextView) findViewById(R.id.question_txt);
		firstAnswerTxt = (TextView) findViewById(R.id.first_answer_txt);
		secondAnswerTxt = (TextView) findViewById(R.id.second_answer_txt);
		thirdAnswerTxt = (TextView) findViewById(R.id.third_answer_txt);
		fourthAnswerTxt = (TextView) findViewById(R.id.fourth_answer_txt);
		firstAnswerRadioBtn = (RadioButton) findViewById(R.id.first_answer_radio_btn);
		secondAnswerRadioBtn = (RadioButton) findViewById(R.id.second_answer_radio_btn);
		thirdAnswerRadioBtn = (RadioButton) findViewById(R.id.third_answer_radio_btn);
		fourthAnswerRadioBtn = (RadioButton) findViewById(R.id.fourth_answer_radio_btn);
		btnBack = (Button) findViewById(R.id.btn_back);
		btnNext = (Button) findViewById(R.id.btn_next);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
	}

	/**
		*Check if the button is usable regarding to the round number
		* because back is not available at the first question and
		* next button is not available too at the last question
		* @param i the number of the round
	 */
	private void btnCheckPositon(int i)
	{
		if (i == 0)
			btnBack.setVisibility(View.INVISIBLE);
		if (i > 0 && i <= quizList.size() - 1)
			btnBack.setVisibility(View.VISIBLE);
		if (i > 0 && i <= quizList.size() - 1)
			btnNext.setVisibility(View.VISIBLE);
		if (i == (quizList.size() - 1))
			btnNext.setVisibility(View.INVISIBLE);
		if (i < (quizList.size() - 1))
			btnSubmit.setVisibility(View.INVISIBLE);
		if (i == (quizList.size() - 1))
			btnSubmit.setVisibility(View.VISIBLE);
	}

	/**
		*Set the text dynamically to each round of question (and answer)
	 	* @param i the number of the round
	 */
	private void setTextAllViews(int i)
	{
		roundCounter.setText((i + 1) + "/" + quizList.size());
		questionNumber.setText(getString(R.string.main_scene_question_number_default_txt));
		questionTxt.setText(quizList.get(i).getQuestionContent());
		firstAnswerTxt.setText(quizList.get(i).getAnswers().get(0).getAnswerContent());
		secondAnswerTxt.setText(quizList.get(i).getAnswers().get(1).getAnswerContent());
		thirdAnswerTxt.setText(quizList.get(i).getAnswers().get(2).getAnswerContent());
		fourthAnswerTxt.setText(quizList.get(i).getAnswers().get(3).getAnswerContent());
	}

	/**
		*Set values for the views. If the user press next button we increase the loop by 1
		* If the user press back button we decrease the loop by 1
	 */
	private void setValuesForViews()
	{
		setQuizList();
		btnCheckPositon(i);
		setTextAllViews(i);
		setTheRightAnswer(i);

		//Iterate the loop with buttons

		btnNext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				i++;
				btnCheckPositon(i);
				setTextAllViews(i);
				setTheRightAnswer(i);
			}

		});
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				i--;
				btnCheckPositon(i);
				setTextAllViews(i);
				setTheRightAnswer(i);
			}

		});

		//Submit the filled quiz, and show the final score
		btnSubmit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String text = "You've finished the quiz. You've reached " + rightAnswerScore + " score(s) from " +
						quizList.size();
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			}
		});

	}

	/**
		* Select the right answer with touch the whole linearlayout / answer item.
	 	* Only one answer choosable, not more. And the user get point for the right answer at once.
	 	* And tick programitically the radiobutton, if the user click on answers' layout
	 	* @param i  the number of the round. Final because the app use this variable in an inner class
	 */
	public void setTheRightAnswer(final int i)
	{
		firstAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				firstAnswerRadioBtn.setChecked(true);
				secondAnswerRadioBtn.setChecked(false);
				thirdAnswerRadioBtn.setChecked(false);
				fourthAnswerRadioBtn.setChecked(false);

				if (isRightAnswer(i, 0) && !isAddedRightAnswerPoint)
				{
					rightAnswerScore++;
					isAddedRightAnswerPoint = true;
				}
			}
		});
		secondAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{

				firstAnswerRadioBtn.setChecked(false);
				secondAnswerRadioBtn.setChecked(true);
				thirdAnswerRadioBtn.setChecked(false);
				fourthAnswerRadioBtn.setChecked(false);

				if (isRightAnswer(i, 1) && !isAddedRightAnswerPoint)
				{
					rightAnswerScore++;
					isAddedRightAnswerPoint = true;
				}
			}
		});
		thirdAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				firstAnswerRadioBtn.setChecked(false);
				secondAnswerRadioBtn.setChecked(false);
				thirdAnswerRadioBtn.setChecked(true);
				fourthAnswerRadioBtn.setChecked(false);

				if (isRightAnswer(i, 2) && !isAddedRightAnswerPoint)
				{
					rightAnswerScore++;
					isAddedRightAnswerPoint = true;
				}
			}
		});
		fourthAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				firstAnswerRadioBtn.setChecked(false);
				secondAnswerRadioBtn.setChecked(false);
				thirdAnswerRadioBtn.setChecked(false);
				fourthAnswerRadioBtn.setChecked(true);

				if (isRightAnswer(i, 3) && !isAddedRightAnswerPoint)
				{
					rightAnswerScore++;
					isAddedRightAnswerPoint = true;
				}
			}
		});
	}

	/**
	 *	The function can decide is the clicked answer right or not.
	 * @param i the number of the round.
	 * @param answerNumber contain the clicked answer's reference.
	 * @return True if the answer is right. False if the answer is not right.
	 */
	private boolean isRightAnswer(int i, int answerNumber)
	{
		if (quizList.get(i).getAnswers().get(answerNumber).isTrueAnswer())
		{
			return true;
		} else
		{
			return false;
		}

	}
}

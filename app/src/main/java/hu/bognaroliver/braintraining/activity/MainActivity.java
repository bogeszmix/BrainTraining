package hu.bognaroliver.braintraining.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
	//Java variables
	private ArrayList<Question> quizList = new ArrayList<Question>();
	private ArrayList<Question> questionList = null;
	private ArrayList<Answer> answers = null;
	private int i = 0;    //Current question number
	private int rightAnswerScore = 0;
	private String[] quizQuestionOrderBlueprint = new String[quizList.size()]; //We recreate the list when orientation is changed with this order.

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
		Log.d("#Process:OnCreate: ","Successful");
		setContentView(R.layout.activity_main);

		if (savedInstanceState != null)
		{
			quizQuestionOrderBlueprint = savedInstanceState.getStringArray("quizListBluePrint");
			i = savedInstanceState.getInt("currentQuestionNumber");
			rightAnswerScore = savedInstanceState.getInt("overallScore");
			Log.d("#Process:LoadInstance ","Successful");
		}

		Log.d("#Process:setView: ","Successful");
		inflateViewWithElements();
		setValuesForViews();
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle)
	{
		super.onSaveInstanceState(bundle);
		bundle.putStringArray("quizListBluePrint",getBluePrint());
		bundle.putInt("currentQuestionNumber",i);
		bundle.putInt("overallScore",rightAnswerScore);

		Log.d("#Process:SaveInstance ","Successful");
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

		Log.d("#Process:SetQuizlist ","Successful");

		randomizeQuizList();

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
		Log.d("#Process:setVReference ","Successful");
	}

	/**
	 * 	Randomize quiz list's questions and reinitialize by saved quiz element order
	 */
	private void randomizeQuizList()
	{
		ArrayList<Question> temp = new ArrayList<Question>();

		if (getBluePrint().length == 0)
		{
			Log.d("#Process:Blueprint ","Not Already Exist");
			long seed = System.nanoTime();
			Collections.shuffle(quizList, new Random(seed));
			quizList.subList(0, 9);

			Log.d("#Process:invokeSetBP ","Start");
			setBluePrint();
			Log.d("#Process:invokeSetBP ","Finish");
			Log.d("#Process:QListRandom ","Successful");
		}

		//Randomize the items order in the array, and maximize the item quantity at 10
		if (getBluePrint().length > 0 )
		{
			Log.d("#Process:Blueprint ","Already Exist");


			for(int i = 0; i < getBluePrint().length; i++)
			{

				for(int j = 0; j < quizList.size(); j++)
				{
					if ( Integer.valueOf(getBluePrint()[i]) == quizList.get(j).getQuestionId())
					{
						temp.add(quizList.get(j));
					}
				}
			}
		}
	}

	/**
	 * Set blueprint of the item order which is needed to reinitalize the quiz
	 * list when the orientation is changed.
	 * @return Only generate the new blueprint, when
	 * quizQuestionOrderBlueprint is not set. When it is already set the method return the setted
	 * value itself.
	 */
	private void setBluePrint()
	{
		String newBluePrint = "";
		String[] temp = new String[quizList.size()];

		//Set the blueprint of the new shuffled list

		if (! (quizQuestionOrderBlueprint.length > 0))
		{
			Log.d("#Process:setNewBprint ","Start");
			int i = 0;
			while (i < quizList.size())
			{
				newBluePrint += String.valueOf(quizList.get(i).getQuestionId());
				i++;
			}

			//Instead of return quizQuestionOrderBlueprint = temp.split("");
			for (int j = 0; j<newBluePrint.length(); j++)
			{
				temp[j] = String.valueOf(newBluePrint.charAt(j));
			}

			Log.d("#Process:setNewBprint ","Finish");

			quizQuestionOrderBlueprint = temp;
		}
	}

	/**
		*Return the blueprint of the list order
		* @return blueprint of the list order
	 */
	private String[] getBluePrint(){return quizQuestionOrderBlueprint;}

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

		Log.d("#Process:buildBtnStep ","Successful");
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
		Log.d("#Process:allVal2TextV ","Successful");
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
		Log.d("#Process:answerOptionCh","Start");
		firstAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				setRadioButtonTrueOrFalse(0);
			}
		});
		secondAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				setRadioButtonTrueOrFalse(1);
			}
		});
		thirdAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				setRadioButtonTrueOrFalse(2);
			}
		});
		fourthAnswerTap.setOnClickListener(new View.OnClickListener()
		{
			private boolean isAddedRightAnswerPoint = false; //To not add point twice or more

			@Override
			public void onClick(View view)
			{
				setRadioButtonTrueOrFalse(3);
			}
		});

		Log.d("#Process:answerOptionCh","Finish");
	}

	/**
	 *	The function can decide is the clicked answer right or not.
	 * @param i the number of the round.
	 * @param answerNumber contain the clicked answer's reference.
	 * @return True if the answer is right. False if the answer is not right.
	 */
	private boolean isRightAnswer(int i, int answerNumber)
	{
		Log.d("#Process:checkRAnsw","Start");

		if (quizList.get(i).getAnswers().get(answerNumber).isTrueAnswer())
		{
			Log.d("#Process:checkTRAnsw","Finish");
			return true;
		} else
		{
			Log.d("#Process:checkFRAnsw","Finish");
			return false;
		}


	}

	/**
	 *  Set the correct radiobutton marker true, and this works too with layout click
	 *  @param btnRadioNumber which radio button is clicked
	 */
	private void setRadioButtonTrueOrFalse(int btnRadioNumber)
	{
		Log.d("#Process:rBtnToF","Start");
		boolean isAddedRightAnswerPoint = false;
		ArrayList<RadioButton> radioBtnList = new ArrayList<RadioButton>();
		radioBtnList.add(firstAnswerRadioBtn);
		radioBtnList.add(secondAnswerRadioBtn);
		radioBtnList.add(thirdAnswerRadioBtn);
		radioBtnList.add(fourthAnswerRadioBtn);



		int i = 0;
		while (i < radioBtnList.size())
		{
			radioBtnList.get(i).setOnCheckedChangeListener(this);

			if (! (i == btnRadioNumber))
			{
				if ( isRightAnswer(this.i, i) && !isAddedRightAnswerPoint)
				{
					rightAnswerScore++;
					Log.d("#Process:crntPoint: ", String.valueOf(rightAnswerScore));
					isAddedRightAnswerPoint = true;
				}
				radioBtnList.get(i).setChecked(false);
			}

			if( i == btnRadioNumber)
			{
				radioBtnList.get(i).setChecked(true);
			}

			i++;
		}

		Log.d("#Process:rBtnToF","Finish");
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
	{
		if (isChecked)
		{
			switch (compoundButton.getId())
			{
				case R.id.first_answer_radio_btn:
					setRadioButtonTrueOrFalse(0);
					break;

				case R.id.second_answer_radio_btn:
					setRadioButtonTrueOrFalse(1);
					break;

				case R.id.third_answer_radio_btn:
					setRadioButtonTrueOrFalse(2);
					break;

				case R.id.fourth_answer_radio_btn:
					setRadioButtonTrueOrFalse(3);
					break;

				default: break;
			}
		}
	}

}

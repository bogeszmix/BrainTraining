package hu.bognaroliver.braintraining.data;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import hu.bognaroliver.braintraining.model.Answer;
import hu.bognaroliver.braintraining.model.Question;

/*
	*Read the question and answer text source files
 */
public class FileScanner
{
	public FileScanner(){}

	/**
	 * Read question txt file and return a list of object
	 * @return Return a list of questions what are generated from questions' text file.
 	 */

	public static ArrayList<Question>  readQuestionTxtFile(Context context, String fileNode)
	{
		AssetManager aManager = context.getAssets();
		ArrayList<Question> list = null;
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new InputStreamReader(aManager.open(fileNode)));
			list = new ArrayList<Question>();
			String mLine;
			while ((mLine = reader.readLine()) != null)
			{
				String[] lineArray = mLine.split(";");
				Question question = new Question();
				question.setQuestionId(Integer.parseInt(lineArray[0]));
				question.setQuestionContent(lineArray[1]);

				list.add(question);
			}
		} catch(IOException e)
		{
			Toast.makeText(context.getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e)
				{
					Log.d("#FileClose: ", "The question file can not be closed!");
				}
			}
		}

		return list;
	}

	/**
	 * Read answer txt file and return a list of object
	 * @return Return a list of answers what are generated from answers' text file.
	 */
	public static ArrayList<Answer> readAnswerTxtFile(Context context, String fileNode)
	{
		AssetManager aManager = context.getAssets();
		ArrayList<Answer> list = null;
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new InputStreamReader(aManager.open(fileNode)));
			list = new ArrayList<Answer>();
			String mLine;
			while ((mLine = reader.readLine()) != null)
			{
				String[] lineArray = mLine.split(";");

				Answer answer = new Answer();
				answer.setAnswerId(Integer.parseInt(lineArray[0]));
				answer.setQuestionId(Integer.parseInt(lineArray[1]));
				answer.setTrueAnswer(Integer.parseInt(lineArray[2]));
				answer.setAnswerContent(lineArray[3]);

				list.add(answer);
			}
		} catch(IOException e)
		{
			Toast.makeText(context.getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e)
				{
					Log.d("#FileClose: ", "The question file can not be closed!");
				}
			}
		}

		return list;
	}
}

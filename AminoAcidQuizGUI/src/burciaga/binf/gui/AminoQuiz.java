package burciaga.binf.gui;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;


/*
 * Imported by burciaga.binf.gui.AminoGUI
 */
public class AminoQuiz {
	
	// One letter amino acid abbreviations
	public static String[] SHORT_NAMES = {
			"A","R", "N", "D", "C", "Q", "E",
			"G", "H", "I", "L", "K", "M", "F", "P",
			"S", "T", "W", "Y", "V"
		};
	
	// Full name amino acids
	public static String[] FULL_NAMES = { 
		"alanine","arginine", "asparagine",
		"aspartic acid", "cysteine",
		"glutamine", "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine", "lysine", "methionine",
		"phenylalanine", "proline",
		"serine","threonine","tryptophan",
		"tyrosine", "valine"
	};	
	
	// used for counting correct answers
	static int count = 0;

	// Counts the number of times a question is answered correctly by
	// using a counter and a hashmap of name to count
	public void setCount(String question, HashMap<String, Integer> map) {
		
		if (map.containsKey(question)) {
			count++;
			map.put(question, count);
		}
		else {
			count = 1;
			map.put(question, count);
		}
	}

	// Checks answer by comparing indicies of short and long names for AA
	public boolean checkAnswer(String question, String answer) {
		int fullAnswerIndex = Arrays.asList(FULL_NAMES).indexOf(question);
		int shortAnswerIndex = Arrays.asList(SHORT_NAMES).indexOf(answer.toUpperCase());
		if( shortAnswerIndex == fullAnswerIndex ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Returns a one AA abbreviation as a question
	// Uses random number generator to ensure random questions
	public String newQuestion() {
		Random random_generator = new Random();
		int random_num = random_generator.nextInt(20);	
		String one_amino = (FULL_NAMES[random_num]);
		String question = (one_amino + "...?");
		return question;
	}
	
	// Runs countdown for the quiz at time selected
	public void runTimer(Timer countdown, int seconds) {
		int milis = seconds * 1000;
		countdown.schedule(new TimerTask() {
			public void run() {
				System.out.println("done");
			}
		}, milis);
	}
}

package com.hackon.ex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.hackon.entity.ChooseTheVerb;
import com.hackon.entity.Exercise;
import com.hackon.tools.ParserText;

public class ExGen {

	public static final String DOTS = "....." ;
	// this is ex number 2
	public String[] exercise2MixWords(Exercise ex) {
		String[] text = ex.getSentences();
		String[] result = new String[text.length];

		for (int i = 0; i < text.length; i++) {
			List<String> words = Arrays.asList(text[i].split("\\s+"));
			Collections.shuffle(words);
			String[] tempResult = (String[]) words.toArray();
			String sent = Arrays.stream(tempResult).collect(
					Collectors.joining(" "));

			result[i] = sent;
		}

		return result;
	}

	// this is ex number 3
	public ChooseTheVerb exercise3ChooseTheVerb(Exercise ex) {
		String[] text = ex.getSentences();
		Map<Integer, String[]> preResult = new TreeMap<Integer, String[]>();

		ParserText pt = new ParserText();

		for (int i = 0; i < text.length; i++) {
			String[] verbs;
			try {
				verbs = pt.findVerbs(text[i]);
				preResult.put(i, verbs);
			} catch (Exception e) {
				// DO SMTH
				e.printStackTrace();
			}
		}
		
		ChooseTheVerb chooseTheVerb = new ChooseTheVerb();
		
		for(int i = 0; i < text.length; i++) {
			Map<String, String[]> tempResult = swapAndFindVerbs(i, text[i], preResult.get(i));
		}
		
		return null;
	}

	private Map<String, String[]> swapAndFindVerbs(int i, String sentence,
			String[] verbs) {
		Random random = new Random();
		String randomVerb = verbs[random.nextInt(verbs.length)];
		
		sentence = sentence.replaceAll(randomVerb, DOTS);
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	

	// test for exercise2MixWords
	/*
	 * public static void main(String[] args) { ExGen ex = new ExGen(); String
	 * one = "Hi, how is your day?"; String two = "Let's hanging out"; String[]
	 * arr = new String[2]; arr[0] = one; arr[1] = two; String[] res =
	 * ex.exercise2MixWords(arr); for(String s: res){ System.out.println(s); }
	 */
}

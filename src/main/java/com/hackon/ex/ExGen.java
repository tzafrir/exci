package com.hackon.ex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import opennlp.tools.parser.ParserModel;

import com.hackon.entity.ChooseTheVerb;
import com.hackon.entity.Exercise;
import com.hackon.tools.FindFormsOfVerb;
import com.hackon.tools.ParserText;

public class ExGen {

	private ParserModel model;

	public static final String DOTS = ".....";

	public ExGen(ParserModel model) {
		this.model = model;
	}

	// this is ex number 2
	public Exercise exercise2MixWords(Exercise ex) {
		String kind = "mix_words";
		String[] text = ex.getSentences();
		int len = text.length;
		if (len > 10) {
			len = 10;
		}

		String[] result = new String[len];

		for (int i = 0; i < len; i++) {
			List<String> words = Arrays.asList(text[i].split("\\s+"));
			Collections.shuffle(words);
			String[] tempResult = (String[]) words.toArray();
			String sent = Arrays.stream(tempResult).collect(
					Collectors.joining(" "));

			result[i] = sent;
		}

		ex.setSentences(result);
		ex.setKind(kind);
		return ex;
	}

	// this is ex number 3
	public ChooseTheVerb exercise3ChooseTheVerb(Exercise ex) {
		String kind = "choose_correct_verb";
		String[] text = ex.getSentences();
		int len = text.length;
		if (len > 10) {
			len = 10;
		}

		Map<Integer, String[]> preResult = new TreeMap<Integer, String[]>();

    ParserText pt = new ParserText();

		for (int i = 0; i < len; i++) {
			String[] verbs;
			try {
				verbs = pt.findVerbs(text[i], model);
				preResult.put(i, verbs);
			} catch (Exception e) {
				// DO SMTH
				e.printStackTrace();
			}
		}

		ChooseTheVerb chooseTheVerb = new ChooseTheVerb();
		List<String> sent = new ArrayList<String>();
		// String[] sent = new String[text.length];
		Map<Integer, String[]> verbss = new TreeMap<Integer, String[]>();

		for (int i = 0; i < len; i++) {
			Map<String, String[]> tempResult = swapAndFindVerbs(text[i],
					preResult.get(i));
			if (tempResult != null) {
				for (Map.Entry<String, String[]> ent : tempResult.entrySet()) {
					// sent[i] = ent.getKey();
					sent.add(ent.getKey());
					verbss.put(i, ent.getValue());
				}
			}
		}

		chooseTheVerb.setSentences(sent.toArray(new String[sent.size()]));
		chooseTheVerb.setVerbs(verbss);
		chooseTheVerb.setKind(kind);

		return chooseTheVerb;
	}

	private Map<String, String[]> swapAndFindVerbs(String sentence,
			String[] verbs) {
		Map<String, String[]> result = new TreeMap<String, String[]>();
		Random random = new Random();
		if (verbs.length == 0) {
			return null;
		}
		int randomNum = random.nextInt(verbs.length);
		System.out.println(randomNum);
		String randomVerb = verbs[randomNum];

		sentence = sentence.replaceAll(randomVerb, DOTS);
		String[] verbsYouChoose = FindFormsOfVerb.findAllForms(randomVerb);
		result.put(sentence, verbsYouChoose);
		return result;
	}

	
	//Tests
	//-----------------------------------------------------------------------------------
	// test for exercise3


	// test for exercise2MixWords
	/*
	 * public static void main(String[] args) { ExGen ex = new ExGen(); String
	 * one = "Hi, how is your day?"; String two = "Let's hanging out"; String[]
	 * arr = new String[2]; arr[0] = one; arr[1] = two; String[] res =
	 * ex.exercise2MixWords(arr); for(String s: res){ System.out.println(s); }
	 */
}

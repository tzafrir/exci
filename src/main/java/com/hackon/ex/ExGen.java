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

	// public static void main(String[] args) throws IOException {
	// ChooseTheVerb ch = new ChooseTheVerb();
	// String one = "I want to play with you";
	// String two = "Run and run and make this world better";
	// String three = "Sababa";
	// String four =
	// "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ";
	// String five =
	// "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.";
	// String six =
	// "When an unknown printer took a galley of type and scrambled it to make a type specimen book.";
	// String seven = "It has survived not only five centuries";
	// String eight =
	// "But also the leap into electronic typesetting, remaining essentially unchanged.";
	// String nine =
	// "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.";
	// String ten = "and more recently with desktop publishing software.";
	// String eleven =
	// "Software like Aldus PageMaker including versions of Lorem Ipsum.";
	// String twelve =
	// " Lorem ipsum dolor sit amet.., comes from a line in section 1.10.32.";
	// String[] arr = new String[14];
	// arr[0] = one;
	// arr[1] = two;
	// arr[2] = three;
	// arr[3] = four;
	// arr[4] = five;
	// arr[5] = six;
	// arr[6] = seven;
	// arr[7] = eight;
	// arr[8] = nine;
	// arr[9] = ten;
	// arr[10] = eleven;
	// arr[11] = one;
	// arr[12] = one;
	// arr[13] = one;
	// ch.setSentences(arr);
	// ExGen e = new ExGen();
	// ch = e.exercise3ChooseTheVerb(ch);
	//
	// String[] res1 = ch.getSentences();
	// for (String s : res1) {
	// System.out.println(s);
	// }
	// Map<Integer, String[]> v = ch.getVerbs();
	// for (Map.Entry<Integer, String[]> ee : v.entrySet()) {
	// for (String s : ee.getValue()) {
	// System.out.print(s + "  ");
	// }
	// }
	// }
	// test for exercise2MixWords
	/*
	 * public static void main(String[] args) { ExGen ex = new ExGen(); String
	 * one = "Hi, how is your day?"; String two = "Let's hanging out"; String[]
	 * arr = new String[2]; arr[0] = one; arr[1] = two; String[] res =
	 * ex.exercise2MixWords(arr); for(String s: res){ System.out.println(s); }
	 */
}

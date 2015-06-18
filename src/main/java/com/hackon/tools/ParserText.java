package com.hackon.tools;

import java.util.HashSet;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

//Class for making an array of Verbs or Nouns
public class ParserText {

	private static Set<String> verbPhrases;
	private static Set<String> nounPhrases;

	private static String line;

	private static void getVerbs(Parse p) {

		if (p.getType().equals("VB") || p.getType().equals("VBP")
				|| p.getType().equals("VBG") || p.getType().equals("VBD")
				|| p.getType().equals("VBN")) {
			verbPhrases.add(p.getCoveredText());
		}

		for (Parse child : p.getChildren()) {
			getVerbs(child);
		}
	}

	private static void getNouns(Parse p) {
		if (p.getType().equals("NN") || p.getType().equals("NNS")
				|| p.getType().equals("NNP") || p.getType().equals("NNPS")) {
			nounPhrases.add(p.getCoveredText());
		}

		for (Parse child : p.getChildren()) {
			getNouns(child);
		}
	}

	private static void parserActionForVerbs(ParserModel model) throws Exception {
		Parser parser = ParserFactory.create(model);
		Parse topParses[] = ParserTool.parseLine(line, parser, 1);
		for (Parse p : topParses) {
			// p.show();
			getVerbs(p);
		}
	}

	private static void parserActionForNouns(ParserModel model) {
		Parser parser = ParserFactory.create(model);
		Parse topParses[] = ParserTool.parseLine(line, parser, 1);
		for (Parse p : topParses) {
			getNouns(p);
		}
	}

	public String[] findNouns(String sentence, ParserModel model) {
		line = sentence;
		nounPhrases = new HashSet<String>();
		verbPhrases = new HashSet<String>();
		parserActionForNouns(model);
		String[] nouns = nounPhrases.toArray(new String[nounPhrases.size()]);
		return nouns;
	}

	public String[] findVerbs(String sentence, ParserModel model)
			throws Exception {
		line = sentence;
		nounPhrases = new HashSet<>();
		verbPhrases = new HashSet<String>();
		parserActionForVerbs(model);
		String[] verbs = verbPhrases.toArray(new String[verbPhrases.size()]);
		return verbs;
	}

}

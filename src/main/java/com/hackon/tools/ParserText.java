package com.hackon.tools;

import java.util.HashSet;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class ParserText {

	static Set<String> verbPhrases; 

	private static String line;

	public static void getWordPhrases(Parse p) {

		if (p.getType().equals("VB") || p.getType().equals("VBP")
				|| p.getType().equals("VBG") || p.getType().equals("VBD")
				|| p.getType().equals("VBN")) {
			verbPhrases.add(p.getCoveredText());
		}

		for (Parse child : p.getChildren()) {
			getWordPhrases(child);
		}
	}

	public static void parserAction(ParserModel model) throws Exception {
		Parser parser = ParserFactory.create(model);
		Parse topParses[] = ParserTool.parseLine(line, parser, 1);
		for (Parse p : topParses) {
			// p.show();
			getWordPhrases(p);
		}
	}

	public String[] findVerbs(String sentence, ParserModel model) throws Exception {
		line = sentence;
		verbPhrases = new HashSet<String>();
		parserAction(model);
		String[] verbs = verbPhrases.toArray(new String[verbPhrases.size()]);
		return verbs;
	}
}

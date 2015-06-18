package com.hackon.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class ParserText {

	static Set<String> verbPhrases = new HashSet<String>();

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

	public static void parserAction() throws Exception {
		InputStream is = new FileInputStream("en-parser-chunking.bin");
		ParserModel model = new ParserModel(is);
		Parser parser = ParserFactory.create(model);
		Parse topParses[] = ParserTool.parseLine(line, parser, 1);
		for (Parse p : topParses) {
			// p.show();
			getWordPhrases(p);
		}
	}

	public String[] findVerbs(String sentence) throws Exception {
		line = sentence;
		parserAction();
		// System.out.println("List of Verb Parse : " + verbPhrases);
		String[] verbs = verbPhrases.toArray(new String[verbPhrases.size()]);
		return verbs;
	}
}

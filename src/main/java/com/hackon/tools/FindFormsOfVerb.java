package com.hackon.tools;

import java.util.HashSet;
import java.util.Set;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.XMLLexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.impl.file.Morphology;

public class FindFormsOfVerb {

	public static final String SUBJECT = "I";
	public static final String OBJECT = "the tower";

	public static String[] findAllForms(String verb) {
		Set<String> resultSet = new HashSet<String>();
		
		System.setProperty("wordnet.database.dir", "dict");

		Morphology id = Morphology.getInstance();

		String[] arr = id.getBaseFormCandidates(verb, SynsetType.VERB);
		System.out.println(arr.length);

		XMLLexicon lexicon = new XMLLexicon("default-lexicon.xml");
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		SPhraseSpec p = nlgFactory.createClause();
		p.setSubject(SUBJECT);
		p.setVerb(verb);
		p.setObject(OBJECT);
		p.setFeature(Feature.TENSE, Tense.PAST);
		Realiser realiser = new Realiser(lexicon);
		String past = realiser.realise(p).getRealisation();
		String[] pastArray = past.split(" ");
		resultSet.add(pastArray[1]);

		if (arr.length == 0) {

			SPhraseSpec q = nlgFactory.createClause();
			q.setSubject(SUBJECT);
			q.setVerb(verb);
			q.setObject(OBJECT);
			q.setFeature(Feature.PERFECT, Tense.PAST);
			realiser = new Realiser(lexicon);
			String pastParticiple = realiser.realise(q).getRealisation();
			String[] pastParticipleArray = pastParticiple.split(" ");
			resultSet.add(pastParticipleArray[1]);

		} else {
			p.setFeature(Feature.TENSE, Tense.PRESENT);
			realiser = new Realiser(lexicon);
			String present = realiser.realise(p).getRealisation();
			String[] presentArray = present.split(" ");
			resultSet.add(presentArray[1]);
		}
		
		resultSet.add(verb);
		return resultSet.toArray(new String[resultSet.size()]);
	}
	
// test code
//	public static void main(String[] args) {
//		findAllForms("do");
//	}
}

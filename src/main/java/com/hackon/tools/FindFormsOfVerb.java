package com.hackon.tools;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.XMLLexicon;
import simplenlg.realiser.english.Realiser;

public class FindFormsOfVerb {

	public static String[] findAllForms(String verb){
		XMLLexicon lexicon = new XMLLexicon("default-lexicon.xml");
		WordElement word = lexicon.getWord(verb, LexicalCategory.VERB);
		InflectedWordElement infl1 = new InflectedWordElement(word);
		InflectedWordElement infl2 = new InflectedWordElement(word);
		InflectedWordElement infl3 = new InflectedWordElement(word);
//		infl1.setFeature(Feature., Tense.PAST);
//		infl2.setFeature(Feature., Tense.PRESENT);
		infl3.setFeature(Feature.TENSE, Tense.FUTURE);
		
		Realiser realiser = new Realiser(lexicon);
		String past = realiser.realise(infl1).getRealisation();
		String present = realiser.realise(infl2).getRealisation();
		String future = realiser.realise(infl3).getRealisation();
		
		System.out.println(past);
		System.out.println(present);
		System.out.println(future);
		
		return null;
	}
	
	public static void main(String[] args) {
		findAllForms("play");
	}
}

package com.hackon;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

// import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.literalReplacement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackon.entity.ChooseTheVerb;
import com.hackon.entity.Exercise;
import com.hackon.entity.IdentifyPicture;
import com.hackon.entity.IdentifyWord;
import com.hackon.entity.SeePicture;
import com.hackon.ex.ExGen;
import com.hackon.tools.ParserText;

import opennlp.tools.parser.ParserModel;

import com.hackon.tools.ParserText;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.*;
import com.flickr4java.flickr.*;
import com.hackon.entity.*;

@RestController
public class ExciController {
  // 'look at me!
  // look at me now!' said the cat.
  // 'with a cup and a cake
  // on the top of my hat!
  // i can hold up TWO books!
  // i can hold up the fish!
  // and a little toy ship!
  // and some milk on a dish!
  // and look!
  // i can hop up and down on the ball!
  // but that is not all!
  // oh, no.
  // that is not all...
	
	private static Random rnd = new Random();

  private static ParserModel model;
  static {
    InputStream is;

    try {
      is = new FileInputStream("en-parser-chunking.bin");
      model = new ParserModel(is);
    } catch (IOException e1) {
      // DO_SMTH
    }
  }

	private String[] getSlice(String[] a, int m) {
		// Floyd's Algorithm apparently
		HashSet<String> res = new HashSet<String>(m);
		int n = a.length;
		for (int i = n - m; i < n; i++) {
			int pos = rnd.nextInt(i + 1);
			String noun = a[pos];
			if (res.contains(noun)) {
				res.add(a[i]);
			} else {
				res.add(noun);
			}
		}
		return res.toArray(new String[0]);
	}

  @RequestMapping("/")
  public String getRoot() {
    return "<meta http-equiv=\"refresh\" content=\"0; url=index.html\">";
  }

	@RequestMapping("/exercise/picture")
	public Exercise picture(List<String> input) {
		return new SeePicture(getSlice(input.toArray(new String[0]), 1)[0]);
	}

	@RequestMapping("/exercise/identify_picture")
	public Exercise identifyPicture(List<String> input) {
		final int NUMBER = 4;
		String[] nouns = getSlice(input.toArray(new String[0]), NUMBER);
		String[] slice = new String[NUMBER - 1];
		for (int i = 0; i < NUMBER - 1; ++i) {
			slice[i] = nouns[i + 1];
		}
		return new IdentifyPicture(nouns[0], slice);
	}

	@RequestMapping("/exercise/identify_word")
	public Exercise identifyWord(List<String> input) {
		final int NUMBER = 4;
		String[] nouns = getSlice(input.toArray(new String[0]), NUMBER);
    System.out.println(nouns[0]);
		String[] slice = new String[NUMBER - 1];
		for (int i = 0; i < NUMBER - 1; ++i) {
			slice[i] = nouns[i + 1];
		}
		return new IdentifyWord(nouns[0], slice);
	}

	@RequestMapping("/exercise/mix_words")
	public Exercise mixWords(String[] sentences) {
		ExGen instance = new ExGen(model);
    ChooseTheVerb ex = new ChooseTheVerb();
    ex.setSentences(sentences);
		return instance.exercise2MixWords(ex);
	}

	@RequestMapping("/exercise/choose_correct_verb")
	public Exercise chooseCorrectVerb(String[] sentences) {
    ExGen instance = new ExGen(model);
    ChooseTheVerb ex = new ChooseTheVerb();
    ex.setSentences(sentences);
		return instance.exercise3ChooseTheVerb(ex);
	}

  
  //JUST TEST METHOD TO FILL EXERCISE OBJECT
  public static ChooseTheVerb test1(){
    ChooseTheVerb ex = new ChooseTheVerb();
    String[] sentences = {"Hello, how are you doing?", "The long night", "Paint it black"};
    ex.setSentences(sentences);
    return ex;
  }

  @RequestMapping(value = "/exercises")
  public List<Exercise> exercises(@RequestParam(value="sentences") String sentencesJson) {
    JsonElement sentencesElement = new JsonParser().parse(sentencesJson);
    JsonArray sentences = sentencesElement.getAsJsonArray();

    ParserText pt = new ParserText();
    List<String> nouns = new ArrayList<String>();
    List<String> verbs = new ArrayList<String>();
    
    
    for (int i=0; i < sentences.size(); ++i) {
      String sentence = sentences.get(i).toString().replace('"', ' ');
      String[] n = pt.findNouns(sentence, model);

      System.out.println(n.length);
      for(int k = 0; k < n.length; k++){
    	  nouns.add(n[k]);
      }
      String[] v = pt.findVerbs(sentence, model);
      for(int k = 0; k < v.length; k++){
    	  verbs.add(v[k]);
      }
    }

    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    exercises.add(identifyPicture(nouns));
    exercises.add(identifyWord(nouns));

    // exercises.add(picture(nouns));
    
    String[] sents = new String[sentences.size()];
    for (int i=0; i<sentences.size(); ++i) {
      sents[i] = sentences.get(i).toString();
    }
    // for (int i=0; i<3; ++i) {
      exercises.add(chooseCorrectVerb(sents));
    // }
    // exercises.add(mixWords(sents));
    
    return exercises;
  }
}

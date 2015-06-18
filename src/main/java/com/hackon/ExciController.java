package com.hackon;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Random;

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
	private final String[] nouns = new String[] { "cat", "cup", "cake", "hat",
			"books", "fish", "ship", "milk", "dish", "ball" };

	private static Random rnd = new Random();

	private String[] getNouns(int m) {
		// Floyd's Algorithm apparently
		HashSet<String> res = new HashSet<String>(m);
		int n = nouns.length;
		for (int i = n - m; i < n; i++) {
			int pos = rnd.nextInt(i + 1);
			String noun = nouns[pos];
			if (res.contains(noun)) {
				res.add(nouns[i]);
			} else {
				res.add(noun);
			}
		}
		return res.toArray(new String[0]);
	}

	@RequestMapping("/exercise/picture")
	public Exercise picture() {
		return new SeePicture(getNouns(1)[0]);
	}

	@RequestMapping("/exercise/identify_picture")
	public Exercise identifyPicture() {
		final int NUMBER = 4;
		String[] nouns = getNouns(NUMBER);
		String[] slice = new String[NUMBER - 1];
		for (int i = 0; i < NUMBER - 1; ++i) {
			slice[i] = nouns[i + 1];
		}
		return new IdentifyPicture(nouns[0], slice);
	}

	@RequestMapping("/exercise/identify_word")
	public Exercise identifyWord() {
		final int NUMBER = 4;
		String[] nouns = getNouns(NUMBER);
		String[] slice = new String[NUMBER - 1];
		for (int i = 0; i < NUMBER - 1; ++i) {
			slice[i] = nouns[i + 1];
		}
		return new IdentifyWord(nouns[0], slice);
	}

	@RequestMapping("/exercise/mix_words")
	public Exercise mixWords() {
		ExGen instance = new ExGen();
		return instance.exercise2MixWords(test1());
	}

	@RequestMapping("/exercise/choose_correct_verb")
	public Exercise chooseCorrectVerb() {
		ExGen instance = new ExGen();
		return instance.exercise3ChooseTheVerb(test1());
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

    for (int i=0; i < sentences.size(); ++i) {
      String sentence = sentences.get(i).toString();
      // Create array of nouns
      // Create array of verbs
    }

    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    exercises.add(picture());
    for (int i=0; i < 2; ++i) {
      exercises.add(mixWords());
    }
    for (int i=0; i < 2; ++i) {
      exercises.add(chooseCorrectVerb());
    }
    exercises.add(identifyPicture());
    exercises.add(identifyWord());
    return exercises;
  }
}

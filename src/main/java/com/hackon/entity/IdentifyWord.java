package com.hackon.entity;

import java.util.List;
import java.util.Random;

import com.hackon.FlickrSearch;

public class IdentifyWord extends Exercise {
  protected String word;
  protected String[] otherWords;

  public IdentifyWord(String word, String[] otherWords) {
    this.word = word;
    this.otherWords = otherWords;
  }

  @Override
  public String getKind() {
    return "identify_word";
  }

  @Override
  public String[] getSentences() {
    return new String[] {word};
  }

  @Override
  public String[] getImages() {
    String[] allWords = new String[otherWords.length + 1];
    String[] result = new String[allWords.length];
    allWords[0] = word;
    for (int i = 0; i < otherWords.length; ++i) {
      allWords[i+1] = otherWords[i];
    }
    Random rand = new Random();
    for (int i = 0; i < allWords.length; ++i) {
      List<String> results = new FlickrSearch().search(word);
      result[i] = results.get(rand.nextInt(Math.min(results.size(), 3)));
    }
    return result;
  }
}
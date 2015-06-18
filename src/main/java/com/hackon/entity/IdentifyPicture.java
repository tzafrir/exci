package com.hackon.entity;

import java.util.List;
import java.util.Random;

import com.hackon.FlickrSearch;

public class IdentifyPicture extends Exercise {
  protected String word;
  protected String[] otherWords;

  public IdentifyPicture(String word, String[] otherWords) {
    this.word = word;
    this.otherWords = otherWords;
  }

  @Override
  public String getKind() {
    return "identify_picture";
  }

  @Override
  public String[] getSentences() {
    String[] result = new String[otherWords.length + 1];
    result[0] = word;
    for (int i = 0; i < otherWords.length; ++i) {
      result[i+1] = otherWords[i];
    }
    return result;
  }

  @Override
  public String[] getImages() {
    Random rand = new Random();
    List<String> results = new FlickrSearch().search(word);
    return new String[] {results.get(rand.nextInt(Math.min(results.size(), 3)))};
  }
}
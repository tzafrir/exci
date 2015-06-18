package com.hackon.entity;

import java.util.List;
import java.util.Random;

import com.hackon.FlickrSearch;

public class SeePicture extends Exercise {
  protected String word;

  public SeePicture(String word) {
    this.word = word;
  }

  @Override
  public String getKind() {
    return "see_picture";
  }

  @Override
  public String[] getSentences() {
    return new String[] {word};
  }

  @Override
  public String[] getImages() {
    Random rand = new Random();
    List<String> results = new FlickrSearch().search(word);
    return new String[] {results.get(rand.nextInt(Math.min(results.size(), 3)))};
  }
}
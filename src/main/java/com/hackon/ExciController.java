package com.hackon;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final String[] nouns = new String[]{
      "cat",
      "cup",
      "cake",
      "hat",
      "books",
      "fish",
      "ship",
      "milk",
      "dish",
      "ball"
    };

    private static Random rnd = new Random();

    private String[] getNouns(int m) {
      // Floyd's Algorithm apparently
      HashSet<String> res = new HashSet<String>(m);
      int n = nouns.length;
      for (int i=n-m; i<n; i++) {
        int pos = rnd.nextInt(i+1);
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
        slice[i] = nouns[i+1];
      }
      return new IdentifyPicture(nouns[0], slice);
    }

    @RequestMapping("/exercise/identify_word")
    public Exercise identifyWord() {
      final int NUMBER = 4;
      String[] nouns = getNouns(NUMBER);
      String[] slice = new String[NUMBER - 1];
      for (int i = 0; i < NUMBER - 1; ++i) {
        slice[i] = nouns[i+1];
      }
      return new IdentifyWord(nouns[0], slice);
    }

    @RequestMapping("/exercises")
    public Exercise[] exercises() {
      return new Exercise[] {
        picture(),
        identifyPicture(),
        identifyWord()
      };
    }
}

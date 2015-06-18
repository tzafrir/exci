package com.hackon;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flickr4java.flickr.*;

@RestController
public class ExciController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/search")
    public String search(@RequestParam(value="q", defaultValue="cat") String query) {
      Random rand = new Random();
      List<String> results = new FlickrSearch().search(query);
      String url = results.get(rand.nextInt(Math.min(results.size(), 10)));
      return "<img src=\"" + (url) + "\">";
    }
}

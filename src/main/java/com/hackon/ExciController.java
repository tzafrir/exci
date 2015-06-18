package com.hackon;

import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flickr4java.flickr.*;

import com.hackon.entity.*;

@RestController
public class ExciController {
    @RequestMapping("/exercise/picture")
    public Exercise picture(@RequestParam(value="q", defaultValue="cat") String query) {
      return new SeePicture(query);
    }
}

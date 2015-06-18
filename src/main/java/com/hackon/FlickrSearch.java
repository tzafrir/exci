package com.hackon;

import com.flickr4java.flickr.*;
import com.flickr4java.flickr.activity.*;
import com.flickr4java.flickr.auth.*;
import com.flickr4java.flickr.blogs.*;
import com.flickr4java.flickr.cameras.*;
import com.flickr4java.flickr.collections.*;
import com.flickr4java.flickr.commons.*;
import com.flickr4java.flickr.contacts.*;
import com.flickr4java.flickr.favorites.*;
import com.flickr4java.flickr.galleries.*;
import com.flickr4java.flickr.groups.*;
import com.flickr4java.flickr.interestingness.*;
import com.flickr4java.flickr.machinetags.*;
import com.flickr4java.flickr.panda.*;
import com.flickr4java.flickr.people.*;
import com.flickr4java.flickr.photos.*;
import com.flickr4java.flickr.photosets.*;
import com.flickr4java.flickr.places.*;
import com.flickr4java.flickr.prefs.*;
import com.flickr4java.flickr.push.*;
import com.flickr4java.flickr.reflection.*;
import com.flickr4java.flickr.stats.*;
import com.flickr4java.flickr.tags.*;
import com.flickr4java.flickr.test.*;
import com.flickr4java.flickr.uploader.*;
import com.flickr4java.flickr.urls.*;
import com.flickr4java.flickr.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlickrSearch {
  private Flickr flickr;

  public class FlickrSearchException extends RuntimeException {
    public FlickrSearchException(Exception e) {
      super(e);
    }
  }

  public FlickrSearch() {
    final String KEY = "60935d58216abc77cf19d8e0ed53abf1";
    final String SECRET = "333793829b82b4fc";
    this.flickr = new Flickr(KEY, SECRET, new REST());
  }

  public List<String> search(String keyword) {
    try {
      SearchParameters searchParameters = new SearchParameters();
      searchParameters.setText(keyword);
      searchParameters.setTags(new String[] {keyword});
      PhotoList<Photo> photos = flickr.getPhotosInterface().search(searchParameters, 0, 0);
      List<String> results = photos.stream().map(p -> p.getMediumUrl()).collect(Collectors.toList());
      return results;
    } catch (FlickrException e) {
      throw new FlickrSearchException(e); 
    }
  }
}
package com.hackon;

import java.util.List;
import java.util.stream.Collectors;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;

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
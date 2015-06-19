package com.hackon.entity;


public class Exercise {

	private String kind;
	private String[] sentences;
	private String[] images;

	public Exercise() {
	}

	public Exercise(String kind, String[] sentences, String[] images) {
		this.kind = kind;
		this.sentences = sentences;
		this.images = images;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String[] getSentences() {
		return sentences;
	}

	public void setSentences(String[] sentences) {
		this.sentences = sentences;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

}

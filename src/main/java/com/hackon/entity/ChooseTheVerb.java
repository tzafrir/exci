package com.hackon.entity;

import java.util.Map;

public class ChooseTheVerb extends Exercise {

	private String kind;
	private String[] sentences;
	private String[] images;
	private Map<Integer, String[]> verbs;

	public ChooseTheVerb() {
	}

	public ChooseTheVerb(String kind, String[] sentences, String[] images,
			Map<Integer, String[]> verbs) {
		super(kind, sentences, images);
		this.verbs = verbs;
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

	public Map<Integer, String[]> getVerbs() {
		return verbs;
	}

	public void setVerbs(Map<Integer, String[]> verbs) {
		this.verbs = verbs;
	}

}

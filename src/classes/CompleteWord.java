package classes;

import java.io.Serializable;
import java.util.ArrayList;

public class CompleteWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Save information about a word as well as information about every word in
	 * the window
	 * 
	 */

	private Word word;
	private ArrayList<Window> window;

	/**
	 * Constructor
	 * 
	 */
	public CompleteWord() {
		word = new Word();
		window = new ArrayList<Window>();
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public ArrayList<Window> getWindow() {
		return window;
	}

	public void setWindow(ArrayList<Window> window) {
		this.window = window;
	}

}

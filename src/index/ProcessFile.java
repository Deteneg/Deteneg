package index;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import classes.CompleteWord;
import classes.Window;
import classes.Word;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

public class ProcessFile {

	/** Constructor */
	public ProcessFile() {
	}

	public List<Word> ProcessWords(String sentence) throws Exception {

		// To normalize the sentence
		String normalized = Normalizer.normalize(sentence, Normalizer.Form.NFD);
		sentence = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		sentence = sentence.toLowerCase();

		// List containing the information about the words
		List<Word> words = new ArrayList<Word>();

		// First or last word in the sentence
		boolean beginS = false;
		boolean endS = false;

		// To split the sentence into words
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos");
		props.setProperty("tokenize.language", "es");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// To create an empty annotation just with the given text
		Annotation document = new Annotation(sentence);

		// To run all annotators on this text
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sent : sentences) {

			for (CoreLabel token : sent.get(TokensAnnotation.class)) {
				beginS = false;
				endS = false;
				String palabra = token.originalText();
				Word word = new Word();
				palabra = palabra.toLowerCase();

				// POS of the token
				word.setPOS(token.get(PartOfSpeechAnnotation.class));

				word.setType("o");
				word.setbeginSentence(beginS);
				word.setendSentence(endS);
				word.setToken(palabra);
				words.add(word);

			}

		}
		words.get(0).setbeginSentence(true);
		words.get(words.size() - 1).setendSentence(true);
		return words;
	}

	
	/**
	 * To process the words index and add the relevant information to the window
	 * 
	 * @param words:
	 *            words index
	 * @return list containing the information about the words and the window.
	 */
	public List<CompleteWord> ProcessWindow(List<Word> words) {

		// Information about the window of each word
		ArrayList<Window> window;

		// List containing the information about each word and its window
		List<CompleteWord> list = new ArrayList<CompleteWord>();

		Window wi = null;

		// Size of the window
		int left = 1;
		int right = 1;

		// To process the words index
		for (int i = 0; i < words.size(); i++) {

			window = new ArrayList<Window>();
			List<Window> aux = new ArrayList<Window>();

			// To process the words on the left
			for (int j = 1; j <= left; j++) {

				if ((i - j < 0) || (words.get(i - j).getendSentence() == true)) {
					for (int g = j; g <= left; g++) {
						wi = new Window();
						aux.add(wi);
					}
					j = left + 1;
				} else {
					wi = copyWordinWindow(words.get(i - j));
					aux.add(wi);
				}
			}
			// To reverse the order of the words on the left
			for (int f = aux.size(); f > 0; f--) {
				window.add(aux.get(f - 1));

			}

			// To process the words on the right of the window
			for (int j = 1; j <= right; j++) {
				if ((i + j >= words.size()) || (words.get(i + j).getbeginSentence() == true)) {
					for (int g = j; g <= right; g++) {
						wi = new Window();
						window.add(wi);

					}
					j = right + 1;
				} else {
					wi = copyWordinWindow(words.get(i + j));
					window.add(wi);

				}

			}
			// To add into the list the word and the words in its window
			CompleteWord c = new CompleteWord();
			c.setWord(words.get(i));
			c.setWindow(window);
			list.add(c);
		}
		return list;
	}

	/**
	 * To copy the values of an object of type Word into an object of type
	 * Window
	 * 
	 * @param w:
	 *            word
	 * @return data of the object w stored in one of type window
	 */

	private Window copyWordinWindow(Word w) {

		Window result = new Window();

		result.setbeginSentence(w.getbeginSentence());
		result.setendSentence(w.getendSentence());
		result.setPOS(w.getPOS());
		result.setToken(w.getToken());
		result.setType(w.getType());

		return result;
	}

}
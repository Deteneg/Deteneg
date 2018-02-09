package classify;

import java.util.ArrayList;
import java.util.List;

import classes.Word;
import classes.info;
import config.Config;
import classes.Utils;

public class ClassifySignals {

	/** Constructor */
	public ClassifySignals() {
	}


	/** Classify the words in a sentence as negation and no negation */
	public List<info> classify(Config conf, List<Word> words) {
		List<info> lf = new ArrayList<info>();

		// List with the negation cues with a frequency > 90% in the collection
		List<String> negaciones = new ArrayList<String>();		
		negaciones.addAll(Utils.readFileByLine(conf.getPathData().concat("negaciones.txt"))); // Read usual negations from a database in order to improve the classification
		

		// If the word is a negation, set its type to "bn"
		for (int i = 0; i < words.size(); i++) {

			Word pal = new Word();
			info inf = new info();
			pal = words.get(i);
			inf.setToken(pal.getToken());

			if (negaciones.contains(pal.getToken())) {
				inf.setType("bn");
			}
			// If the word is not a negation
			else {
				inf.setType("o");
			}

			lf.add(inf);

		}

		return lf;
	}

}

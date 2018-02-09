package classify;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import classes.CompleteWord;
import classes.info;

import config.Config;

public class FromFileWekaToIndex {

	/** Constructor */
	public FromFileWekaToIndex() {
	}


	/**
	 * Transform the Weka classifier's output file into a list containing the type
	 * in which the word has been classified
	 * 
	 * @param conf:
	 *            configuration file
	 * @param nameFile:
	 *            name of the classifier´s file which is wanted to be transformed
	 * @return list containing the classification of each word (as many times as
	 *         there are different scopes - cues)
	 * @throws IOException
	 */
	public List<info> ConversionWekascopes(Config conf, String nameFile, List<CompleteWord> originalIndexTest)
			throws IOException {

		List<info> index = new ArrayList<info>();
		int cont = 0;

		// To analyse the document
		StreamTokenizer st = new StreamTokenizer(
				new FileReader(conf.getPathClassification().concat(nameFile)));
		st.whitespaceChars('\'', '/');

		while (st.nextToken() != StreamTokenizer.TT_EOF) {

			// It it is a word or a number
			if (st.ttype == StreamTokenizer.TT_WORD || st.ttype == StreamTokenizer.TT_NUMBER) {

				if (st.ttype == StreamTokenizer.TT_WORD) {
					if (st.sval.equals("?") == false) {

						info f = new info();

						f.setToken("null");
						f.setType(st.sval);
						index.add(f);

						cont++;

					}
				}

				if (st.ttype == StreamTokenizer.TT_NUMBER) {
				}

			}

		}

		return index;
	}

}

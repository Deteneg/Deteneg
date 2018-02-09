package output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import classes.info;

public class VisualizeSentence {

	/**
	 * To show the sentence classified
	 * 
	 * @param indexClasify:
	 *            index with the tagged cues
	 * @param indexClasifyScopes:
	 *            index with the tagged scopes through Weka
	 * @throws IOException
	 */
	public String vualize(List<info> indexClassify, List<info> indexClassifyScopes) throws IOException {

		String sentence = "";
		int numSignals = 0;
		List<String> scopes = new ArrayList<String>();
		List<info> indexClassifyCopy = new ArrayList<info>();

		info iff;
		for (int i = 0; i < indexClassify.size(); i++) {
			iff = new info();
			iff.setToken(indexClassify.get(i).getToken());
			iff.setType(indexClassify.get(i).getType());
			indexClassifyCopy.add(iff);
		}

		// Postprocessing removing "os isn os" "os ise os"
		for (int i = 1; i < indexClassify.size() - 1; i++) {
			if (indexClassify.get(i).getType().contains("is") && indexClassify.get(i - 1).getType().contains("os")
					&& indexClassify.get(i + 1).getType().contains("os"))
				indexClassify.get(i).setType("os");

		}
		if (indexClassify.get(0).getType().contains("is") && indexClassify.get(1).getType().contains("os"))
			indexClassify.get(0).setType("os");

		if (indexClassify.get(indexClassify.size() - 1).getType().contains("is")
				&& indexClassify.get(indexClassify.size() - 2).getType().contains("os"))
			indexClassify.get(indexClassify.size() - 1).setType("os");

		// To check the number of cues in the sentence
		for (int i = 0; i < indexClassify.size(); i++)
			if (indexClassify.get(i).getType().equals("bn"))
				numSignals++;

		int index = 0;
		int longitud = 0;
		int longitudes[] = new int[numSignals];
		int longitudSignals[] = new int[numSignals];

		for (int i = 0; i < numSignals; i++) {
			while (index < indexClassify.size() - 1 && (indexClassify.get(index).getType().equals("bn") == false))
				index++;
			if (indexClassify.get(index).getType().equals("bn")) {
				longitud = 1;
				index++;
				while (index < indexClassify.size() && indexClassify.get(index).getType().equals("o") == false
						&& indexClassify.get(index).getType().equals("bn") == false) {
					longitud++;
					index++;
				}
				longitudes[i] = indexClassify.size() - longitud;
				longitudSignals[i] = longitud;
			}

		}

		// To save the scopesv according to the cues
		int c = 0;

		int it = 0;
		for (int i = 0; i < numSignals; i++) {

			for (int j = 0; j < indexClassifyCopy.size(); j++) {

				if (indexClassifyCopy.get(j).getType().equals("bn") && it == i) {
					scopes.add("bn");
					indexClassifyCopy.get(j).setType("o");
					it++;

					for (int g = 1; g < longitudSignals[i]; g++) {
						j++;
						scopes.add("in");
						indexClassifyCopy.get(j).setType("o");
					}
				}

				else {
					scopes.add(indexClassifyScopes.get(c).getType());
					c++;

				}

			}
		}

		// To tag the cues and the scopes
		int pals = scopes.size() / numSignals;

		for (int h = 0; h < numSignals; h++) {

			int scope = numSignals - h;
			int tamend = scopes.size() - (pals * h) - 1;
			int tambegin = scopes.size() - (pals * (h + 1));

			int in = -1;

			for (int m = tambegin; m <= tamend; m++) {
				in++;

				if (m == tambegin) {
					if (scopes.get(m).contains("is"))
						if (scopes.get(m + 1).contains("os"))
							indexClassify.get(in).setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken()
									+ "</SCOPE" + scope + ">");
						else
							indexClassify.get(in).setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken());
					if (scopes.get(m).contains("bn"))
						if (scopes.get(m + 1).contains("in") == false)
							indexClassify.get(in).setToken(
									"<NEG" + scope + ">" + indexClassify.get(in).getToken() + "</NEG" + scope + ">");
						else
							indexClassify.get(in).setToken("<NEG" + scope + ">" + indexClassify.get(in).getToken());
				}

				if (m == tamend) {
					if (scopes.get(m).contains("bn"))
						indexClassify.get(in).setToken(
								"<NEG" + scope + ">" + indexClassify.get(in).getToken() + "</NEG" + scope + ">");
					if (scopes.get(m).contains("in"))
						indexClassify.get(in).setToken(indexClassify.get(in).getToken() + "</NEG" + scope + ">");
					if (scopes.get(m).contains("is"))
						if (tamend - tambegin == 1 || ((tamend - tambegin == 2) || scopes.get(m - 1).contains("in")))
							indexClassify.get(in).setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken()
									+ "</SCOPE" + scope + ">");
						else
							indexClassify.get(in).setToken(indexClassify.get(in).getToken() + "</SCOPE" + scope + ">");
				}

				if (m != tambegin && m != tamend) {

					if (scopes.get(m).contains("bn")) {
						if (scopes.get(m + 1).contains("in"))
							indexClassify.get(in).setToken("<NEG" + scope + ">" + indexClassify.get(in).getToken());
						else
							indexClassify.get(in).setToken(
									"<NEG" + scope + ">" + indexClassify.get(in).getToken() + "</NEG" + scope + ">");
					}

					else if (scopes.get(m).contains("in")) {
						indexClassify.get(in).setToken(indexClassify.get(in).getToken() + "</NEG" + scope + ">");

					}

					if (scopes.get(m).contains("is")) {
						if (scopes.get(m + 1).contains("os"))
							indexClassify.get(in).setToken(indexClassify.get(in).getToken() + "</SCOPE" + scope + ">");
						if (scopes.get(m - 1).contains("os"))
							indexClassify.get(in).setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken());

						if (scopes.get(m + 1).contains("b")) {
							if (m + 2 < scopes.size()) {
								if (scopes.get(m + 2).contains("os") || scopes.get(m + 2).contains("in")
										|| scopes.get(m + 2).contains("ie"))
									indexClassify.get(in)
											.setToken(indexClassify.get(in).getToken() + "</SCOPE" + scope + ">");
							} else
								indexClassify.get(in)
										.setToken(indexClassify.get(in).getToken() + "</SCOPE" + scope + ">");
						}

						if (scopes.get(m - 1).contains("b")) {
							if (m - 2 > 0) {
								if (scopes.get(m - 2).contains("os") || scopes.get(m - 2).contains("in"))
									indexClassify.get(in)
											.setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken());
							} else
								indexClassify.get(in)
										.setToken("<SCOPE" + scope + ">" + indexClassify.get(in).getToken());
						}

					}

				}
			}

		}

		for (int h = 0; h < indexClassify.size(); h++)
			sentence = sentence + " " + indexClassify.get(h).getToken();

		return sentence;
	}

}

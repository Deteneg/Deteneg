package test;

import index.ProcessFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import output.VisualizeSentence;
import config.Config;
import classes.CompleteWord;
import classes.Word;
import classes.info;
import classify.ClassifySignals;
import classify.FromFileWekaToIndex;
import files.GenerateInputClassificationARFF_Scopexotherx;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class Test {

	/** Constructor */
	public Test() {
	};

	/**
	 * Class that receives a sentence and returns it with its labeled negations
	 */
	public String test(String sentence) throws Exception {

		// Needed structures
		String clasificacion = new String(); // String for the classified sentence
		List<Word> words = new ArrayList<Word>(); // List of words
		List<CompleteWord> index = new ArrayList<CompleteWord>(); // Index of words
		List<info> index_signals_clasificadas = new ArrayList<info>(); 	// List of classified words
		List<info> index_signals_clasificadas_scopes = new ArrayList<info>(); // List of classified words (scopes)

		// Configuration options
		Config conf = new Config();

		// Index with sentence data
		ProcessFile f = new ProcessFile();
		words = f.ProcessWords(sentence);
		index = f.ProcessWindow(words);

		// To identify the cues of the sentence
		ClassifySignals cs = new ClassifySignals();
		index_signals_clasificadas = new ArrayList<info>();
		index_signals_clasificadas = cs.classify(conf, words);

		// To check if it exists negations in the sentence
		boolean neges = false;
		int h = 0;
		while (h < index_signals_clasificadas.size() && neges == false) {
			if (index_signals_clasificadas.get(h).getType().contains("bn"))
				neges = true;
			else
				h++;
		}

		// If the sentence does not contain negations, save the sentence
		if (neges == false) {
			clasificacion = sentence;
		}
		// Otherwise, to classify the scope
		else {

			// Generate .arff scope
			GenerateInputClassificationARFF_Scopexotherx s = new GenerateInputClassificationARFF_Scopexotherx();
			s.GenerateARFF_test(conf, index_signals_clasificadas, index);

			// To classify the scope
			// The decision tree model file, created using the Weka software
			String model_file = conf.getPathClassification().concat("modelo_scope.model");

			// Instances which we want to classify
			String descriptors_file = conf.getPathClassification().concat("Scope_clinical_test.arff");

			// Instances labeled using the decision tree
			String output_file = conf.getPathClassification().concat("c_scopes.txt");

			// To load the classifier
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(model_file));
			Classifier cls = (Classifier) ois.readObject();
			ois.close();

			// To load the input instances
			Instances unlabeled = new Instances(new BufferedReader(new FileReader(descriptors_file)));

			// To create a copy
			Instances labeled = new Instances(unlabeled);

			double clsLabel;

			BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));

			// to load the classifier
			ois = new ObjectInputStream(new FileInputStream(model_file));
			cls = (Classifier) ois.readObject();
			ois.close();

			// To load the input instances
			unlabeled = new Instances(new BufferedReader(new FileReader(descriptors_file)));

			// To set class attribute
			unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

			// To create a copy
			labeled = new Instances(unlabeled);

			// To label the instances
			for (int k = 0; k < unlabeled.numInstances(); k++) {
				clsLabel = cls.classifyInstance(unlabeled.instance(k));
				labeled.instance(k).setClassValue(clsLabel);
			}

			writer = new BufferedWriter(new FileWriter(output_file));

			for (int k = 0; k < unlabeled.numInstances(); k++) {
				clsLabel = labeled.instance(k).classValue();
				writer.write(labeled.classAttribute().value((int) clsLabel) + "\n");
			}
			writer.flush();
			writer.close();

			// To transform the result of the classification of the scopes in an
			// index
			FromFileWekaToIndex ffws = new FromFileWekaToIndex();
			index_signals_clasificadas_scopes = ffws.ConversionWekascopes(conf, "c_scopes.txt", index);

			// To save the classification of the sentence in a file
			VisualizeSentence v = new VisualizeSentence();
			clasificacion = v.vualize(index_signals_clasificadas, index_signals_clasificadas_scopes);

		}
		return clasificacion;
	}

}

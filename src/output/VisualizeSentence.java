package output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classes.info;

public class VisualizeSentence {
	
	
	/** Muestra por pantalla la frase de entrada etiquetada con las neg/esp asi como el alcance asociado
	 * @param indexClasify: indice fruto de la identificación de las negaciones/especulaciones mediante Weka
	 * @param indexClasifyScopes: indice fruto de la identificación del alcance de las negaciones/especulaciones mediante Weka
	 * @throws IOException
	 */
	public String vualize(List<info> indexClassify, List<info> indexClassifyScopes) throws IOException{
		
		
		String ret = "";
		List<String> sentence = new ArrayList<String>();
		indexClassify.forEach(i -> { sentence.add(i.getToken()); } ); //Set sentence into a List		
		int sizeSentence= indexClassify.size();
		
		
		int numNegs=0;
		
		//For each negation particle
		for( info aux : indexClassify ){
			if(aux.getType().trim().equals("bn")){
				numNegs++; //Count number of negation particles
				int index = sentence.indexOf(aux.getToken());
				sentence.add(index+1, "</NEG"+numNegs+">"); //Close tag
				sentence.add(index, "<NEG"+numNegs+">");  //Open tag
			}
		}
		
		
		//Modify indexClassifyScopes for each negation
		int cont=0;
		for(int i=1; i <= numNegs; i++){
			List<String> aux = getListWordsNoBN(indexClassify, i);
			for(String s:aux){
				indexClassifyScopes.get(cont).setToken(s);
				cont++;
			}
		}
		
		
		//For each in-out words
		int scope = 1;
		for(int i=0; i< indexClassifyScopes.size(); i++){
			
			if(indexClassifyScopes.get(i).getType().trim().equals("isn")){
				int index = sentence.indexOf(indexClassifyScopes.get(i).getToken());
				sentence.add(index+1, "</SCOPE"+scope+">"); //Close tag
				sentence.add(index, "<SCOPE"+scope+">");  //Open tag
			}			
			
			if( (i+1) % (sizeSentence-1) == 0){  //If it is divisible by numNegs
				scope++;
			}			
		}
		
	
		//Copy sentence List to String
		for(String aux : sentence){
			ret = ret + " " + aux;
		}
	
		
		return ret;	
	}	
	
	
	public List<String> getListWordsNoBN(List<info> indexClassify, Integer bn){
		List<String> ret = new ArrayList<String>();;
		
		Integer numBN = 0;
		
		for(info aux : indexClassify){
			if(aux.getType().trim().equals("bn")){
				numBN++;
				if(!numBN.equals(bn)){
					ret.add(aux.getToken());
				}
			}else{
				ret.add(aux.getToken());
			}
			
		}	
		
		
		return ret;
	}

}

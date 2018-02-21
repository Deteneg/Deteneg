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
	public List<String> visualize(List<info> indexClassify, List<info> indexClassifyScopes) throws IOException{
		
		List<String> ret = new ArrayList<String>();
		
		String line = "";		
		int sizeSentence= indexClassify.size();				
		int numNegs=0;	
			
		//Get number of neg tags
		for( info aux : indexClassify ){
			if(aux.getType().trim().equals("bn")){
				numNegs++; //Count number of negation particles
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
		
		//For each negation, we need to build a tagged string 
		for(int i=0; i<numNegs; i++){
			List<String> sentence = new ArrayList<String>();
			indexClassify.forEach(t -> { sentence.add(t.getToken()); } ); //Set sentence into a List	
			
			//For each negation particle
			cont = 0;
			for( info aux : indexClassify ){
				if(aux.getType().trim().equals("bn")){
					cont++;
					if(i+1==cont){
						int index = sentence.indexOf(aux.getToken());
						sentence.add(index+1, "</NEG"+ (i+1) +">"); //Close tag
						sentence.add(index, "<NEG"+ (i+1) +">");  //Open tag
						break;
					}
				}
			}
			
			//For each in-out words
			int scope = i+1;
			for(int j=i*(sizeSentence-1); j<(i*(sizeSentence-1))+sizeSentence-1; j++){
				
				if(indexClassifyScopes.get(j).getType().trim().equals("is")){
					int index = sentence.indexOf(indexClassifyScopes.get(j).getToken());
					sentence.add(index+1, "</SCOPE"+scope+">"); //Close tag
					sentence.add(index, "<SCOPE"+scope+">");  //Open tag
				}					
			}	
			
			//Copy sentence List to String
			line = "";
			for(String aux : sentence){
				line = line + " " + aux;
			}
			
			//Delete duplicated tags
			line = line.replaceAll("</SCOPE"+scope+">" + " " + "<SCOPE"+scope+">" ,"");
			
			ret.add(line);	
			
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

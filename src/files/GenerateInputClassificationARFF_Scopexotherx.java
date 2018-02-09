package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import classes.CompleteWord;
import classes.info;
import config.Config;



public class GenerateInputClassificationARFF_Scopexotherx {
	

	/** Constructor */
	public GenerateInputClassificationARFF_Scopexotherx(){}
	
	
	/** To generate the input files for the classification algorithm
	 * @param conf: 
	 * @param indexClasify: index with the information from the classification through Weka
	 * @param wordsTest: index with the test info
	 * @throws IOException
	 */
	public void GenerateARFF_test(Config conf, List<info> indexClassify, List<CompleteWord> words) throws IOException{
		
		// Output file
		String output = conf.getPathClassification().concat("Scope_clinical_test.arff"); 
				
		FileWriter file = new FileWriter(output);
        PrintWriter f = new PrintWriter(file);
        
        FileWriter fileD = new FileWriter( conf.getPathClassification().concat("datos.txt") );
        PrintWriter fDatos = new PrintWriter(fileD);
        
         	
           
        // To obtain the data to generate the .arff file
        
        /* To store the negation particles and its position in the global index and
           the number of words which form each particle        */
        
        // Attributes token of the particles 
        List<String> tokensP=new ArrayList<String>();
     // Attributes POS of the particles  
        List<String> POSP=new ArrayList<String>();
     // Attributes token "in focus" of the particles
        List<String> tokens=new ArrayList<String>();
     // Attributes POS of tokens "in focus" of the particles
        List<String> POST=new ArrayList<String>();
  
            
        
        List<String[]> signals=new ArrayList<String[]>();
    	String s[]=new String[3];
        int indexini=0;
        int indexfin=0;
        int numSentence=0;
        
                
        
        //Header
        String file1=conf.getPathClassification().concat("Scope_clinical_train.arff");
		// Train document
		FileReader fr = new FileReader(new File(file1));
		BufferedReader f1 = new BufferedReader(fr);
		
				
		String leido;
		
		while ((leido=f1.readLine()).contains("@data")== false ){
			f.println(leido);
		}
			
		
		f1.close();
  
        String line="";
     // Attributes token of the particles 
        List<String> tokensPtrain=new ArrayList<String>();
     // Attributes POS of the particles 
        List<String> POSPtrain=new ArrayList<String>();
     // Attributes token "in focus" of the particles 
        List<String> tokenstrain=new ArrayList<String>();
     // Attributes POS of tokens "in focus" of the particles 
        List<String> POSTtrain=new ArrayList<String>();
    
        
        FileReader ftrain = new FileReader(new File(conf.getPathClassification().concat("Scope_clinical_train.arff")));
        BufferedReader ftr = new BufferedReader(ftrain);
        
		while((line=ftr.readLine()).contains("@data")==false)
	    {
	        	
			// To obtain the particles being in the train document
	        if (line.contains("attribute tokensP")==true) 
	        { 
	        	line=line.substring(line.indexOf('{')+1, line.indexOf('}'));
	        		
	        	StringTokenizer z=new StringTokenizer(line,",");
	        	int num=z.countTokens();
	        	for (int i=0;i<num;i++){
	        		String token=z.nextToken();
	        		if (this.is(tokensPtrain, token)==false)
	        				tokensPtrain.add(token);
	        	}
	        	
	        }
	     
	     // To obtain the POS of the particles being in the train document
	        if (line.contains("attribute POSP")==true) 
	        { 
	        	line=line.substring(line.indexOf('{')+1, line.indexOf('}'));
	        		
	        	StringTokenizer z=new StringTokenizer(line,",");
	        	int num=z.countTokens();
	        	for (int i=0;i<num;i++){
	        		String token=z.nextToken();
	        		if (this.is(POSPtrain, token)==false)
	        			POSPtrain.add(token);
	        	}
	        }
	        
	     // To obtain the tokens being in the train document
	        if (line.contains("attribute Token")==true) 
	        { 
	        	line=line.substring(line.indexOf('{')+1, line.indexOf('}'));
	        		
	        	StringTokenizer z=new StringTokenizer(line,",");
	        	int num=z.countTokens();
	        	for (int i=0;i<num;i++){
	        		String token=z.nextToken();
	        		if (this.is(tokenstrain, token)==false)
	        			tokenstrain.add(token);
	        	}
	        }
	        
	        
	     // To obtain the POS of the tokens being in the train document
	        if (line.contains("attribute POST")==true) 
	        { 
	        	line=line.substring(line.indexOf('{')+1, line.indexOf('}'));
	        		
	        	StringTokenizer z=new StringTokenizer(line,",");
	        	int num=z.countTokens();
	        	for (int i=0;i<num;i++){
	        		String token=z.nextToken();
	        		if (this.is(POSTtrain, token)==false)
	        			POSTtrain.add(token);
	        	}
	        }
	        
	        
	        
	    }
	
		// Index with the information about the document to generate the .arff file
        int i=0; 
        while(i<words.size()){
        	signals.clear();
        	signals=new ArrayList<String[]>();
        	// To obtain each sentence of the document
        	if (words.get(i).getWord().getbeginSentence()==true){
        		numSentence++;
        		indexini=i;
        		indexfin=i;
        		while(words.get(indexfin).getWord().getendSentence()==false)
        			indexfin++;	
        	}
        	
        	// To obtain the negations being in the sentence
        	for (int j=indexini;j<=indexfin;j++){
        		//Negations come from the previous classification of the test document
        		if (indexClassify.get(j).getType().equals("bn")){
    				int a=j;
    				int b=j+1;
    				int size=1;
    				s=new String[3];
    				s[0]=indexClassify.get(a).getToken();
    				s[1]=Integer.toString(a);
    			
    				if (indexClassify.get(a).getType().equals("bn")){
    					while(b<indexClassify.size() && indexClassify.get(b).getType().equals("in")){
    						size++;	
    						s[0]=s[0]+("_")+indexClassify.get(b).getToken();
    						b++;
    					}
    				}
    				
    				s[2]=Integer.toString(size);
    				signals.add(s);		
    			}	
        		
        	} 
       
        	// To write the .arff file of data, a line for each token, for each negation in the sentence 
        	//(Ex: 3 negations -> 3 lines for each token in the sentence)
        	for (int k=0;k<signals.size();k++){
        	
        		// To write the number of the sentence where the particle is
        		fDatos.println("% "+numSentence);
        		
        		// Position of the particle in the index
        		int pos=Integer.parseInt(signals.get(k)[1]);
        		// Size of the particle
        		int tam=Integer.parseInt(signals.get(k)[2]);
        		  
        		// To obtain and to store the information about the words being in the sentence before the particle 
        		for (int l=indexini;l<pos;l++){
        			//Negation particle
        			if (tokensPtrain.contains(signals.get(k)[0]))
        				fDatos.print(signals.get(k)[0]);
        			else
        				fDatos.print("xotherx");
        			if (this.is(tokensP, signals.get(k)[0])==false)
        				tokensP.add(signals.get(k)[0]);
        			
        			// Negation
        			if (indexClassify.get(pos).getType().equals("bn"))
        				fDatos.print(",neg");
        			else
        				fDatos.print(",esp");
        			// POS de la partícula. Si partícula de más de una palabra: POS de cada una de las
        			// palabras que forman la partícula
        			if (tam==1){
        				if (POSPtrain.contains(words.get(pos).getWord().getPOS()))
        					fDatos.print(","+words.get(pos).getWord().getPOS());
        				else
        					fDatos.print(",xotherx");
        				if (this.is(POSP, words.get(pos).getWord().getPOS())==false)
        					POSP.add(words.get(pos).getWord().getPOS());
        			}
        			else{
        				fDatos.print(",");
        				String p="";
        				for (int m=pos;m<pos+tam;m++)
        					if (m<pos+tam-1){
        						p=p+words.get(m).getWord().getPOS()+"_";
        					}
        					else{
        						p=p+words.get(m).getWord().getPOS();
        						if (POSPtrain.contains(p))
        							fDatos.print(p);
        						else
        							fDatos.print(",xotherx");
        						if (this.is(POSP, p)==false)
        							POSP.add(p);
        					}
        			}		
        			
        			// Token "in focus"
        			if (tokenstrain.contains(indexClassify.get(l).getToken()))
        				fDatos.print(","+indexClassify.get(l).getToken());
        			else
        				fDatos.print(",xotherx");
        			if (this.is(tokens, indexClassify.get(l).getToken())==false)
        				tokens.add(indexClassify.get(l).getToken());
        		
        			// POS del token "in focus"
        			if (POSTtrain.contains(words.get(l).getWord().getPOS()))
        				fDatos.print(","+words.get(l).getWord().getPOS());
        			else
        				fDatos.print(",xotherx");
        			if (this.is(POST, words.get(l).getWord().getPOS())==false)
        				POST.add(words.get(l).getWord().getPOS());
		
        			// Clase
        			fDatos.println(",?");       			
        			
        		}
        		// Obtener y almacenar la información referente a las PALABRAS POSTERIORES
        		// a la partícula en la frase
        		for (int l=pos+tam;l<=indexfin;l++){
        			// Partícula de negación/especulación
        			if (tokensPtrain.contains(signals.get(k)[0]))
        				fDatos.print(signals.get(k)[0]);
        			else
        				fDatos.print("xotherx");
        			if (this.is(tokensP, signals.get(k)[0])==false)
        				tokensP.add(signals.get(k)[0]);
        			
        			// Tipo: Negación ó Especulación
        			if (indexClassify.get(pos).getType().equals("bn"))
        				fDatos.print(",neg");
        			else
        				fDatos.print(",esp");
        			// POS de la partícula. Si partícula de más de una palabra: POS de cada una de las
        			// palabras que forman la partícula
        			if (tam==1){
        				if (POSPtrain.contains(words.get(pos).getWord().getPOS()))
        					fDatos.print(","+words.get(pos).getWord().getPOS());
        				else
        					fDatos.print(",xotherx");
        				if (this.is(POSP, words.get(pos).getWord().getPOS())==false)
        					POSP.add(words.get(pos).getWord().getPOS());
        			}
        			else{
        				fDatos.print(",");
        				String p="";
        				for (int m=pos;m<pos+tam;m++)
        					if (m<pos+tam-1){
        						p=p+words.get(m).getWord().getPOS()+"_";
        					}
        					else{
        						p=p+words.get(m).getWord().getPOS();
        						if (POSPtrain.contains(p))
        							fDatos.print(p);
        						else
        							fDatos.print(",xotherx");
        						if (this.is(POSP, p)==false)
        							POSP.add(p);
        					}
        			}		

        			// Token "in focus"
        			if (tokenstrain.contains(indexClassify.get(l).getToken()))
        				fDatos.print(","+indexClassify.get(l).getToken());
        			else
        				fDatos.print(",xotherx");
        			if (this.is(tokens, indexClassify.get(l).getToken())==false)
        				tokens.add(indexClassify.get(l).getToken());
        			
        			// POS del token "in focus"
        			if (POSTtrain.contains(words.get(l).getWord().getPOS()))
        				fDatos.print(","+words.get(l).getWord().getPOS());
        			else
        				fDatos.print(",xotherx");
        			if (this.is(POST, words.get(l).getWord().getPOS())==false)
        				POST.add(words.get(l).getWord().getPOS());

        			// Clase
        			fDatos.println(",?");
        		}
        		
        	}
	
        	i=indexfin+1;	
        }// Fin while
        
        fDatos.close();
        
       
        //Datos
        f.println("@data");
        
        String linea=null;
        FileReader fR=new FileReader( conf.getPathClassification().concat("datos.txt") );
        BufferedReader bfR=new BufferedReader(fR);
        
        while ((linea=bfR.readLine())!=null)
        	f.println(linea);
        
        
        f.close();
        fR.close();
	}
	
	/** Devuelve verdadero en caso de que el string pasado como
	 *  parámetro se encuentre en la lista y falso en caso contrario
	 * @param list: tabla en la que buscar el string
	 * @param string: String a buscar
	 * @return verdadero o falso
	 */
	private boolean is(List<String> list,String string){
		boolean result= false;
		int i=0;
		
		while(i<list.size() && result==false){
			if (list.get(i).equals(string))
				result=true;
			i++;
		}
		
		return result;
	}
	
	
	

}// Fin de la clase

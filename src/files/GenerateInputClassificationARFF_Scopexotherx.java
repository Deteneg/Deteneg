package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import classes.CompleteWord;
import classes.Window;
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
		

		// Obtener los datos con los que generar el fichero .arff

		// Almacena las particulas de negación como la posición que ocupan dentro del
		// índice global y el número de palabras por el que está formada cada partícula


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
			//System.out.println(leido);
			f.println(leido);
		}


		f1.close();

		String line="";
		// Atributos token de las particulas especificado como tipo nominal
		List<String> tokensPtrain=new ArrayList<String>();
		// Atributos POS de las particulas especificado como tipo nominal
		List<String> POSPtrain=new ArrayList<String>();
		// Atributos token "in focus" especificado como tipo nominal
		List<String> tokenstrain=new ArrayList<String>();
		// Atributo POS de tokens "in focus" especificado como tipo nominal
		List<String> POSTtrain=new ArrayList<String>();
		// Atributo distance especificado como tipo nominal
		List<String> distancetrain=new ArrayList<String>();
		// Atributo location especificado como tipo nominal
		List<String> locationtrain=new ArrayList<String>();
		// Atributo POSS especificado como tipo nominal
		List<String> POSStrain=new ArrayList<String>();
		// Atributo Token0 especificado como tipo nominal
		List<String> token0train=new ArrayList<String>();
		// Atributo Token1 especificado como tipo nominal
		List<String> token1train=new ArrayList<String>();
		// Atributo POSW0 especificado como tipo nominal
		List<String> POS0train=new ArrayList<String>();
		// Atributo POSW1 especificado como tipo nominal
		List<String> POS1train=new ArrayList<String>();
		// Atributo TypeS especificado como tipo nominal
		List<String> typeStrain=new ArrayList<String>();
		// Atributo PlaceS especificado como tipo nominal
		List<String> placeStrain=new ArrayList<String>();
		// Atributo PlaceT especificado como tipo nominal
		List<String> placeTtrain=new ArrayList<String>();



        FileReader ftrain = new FileReader(new File(conf.getPathClassification().concat("Scope_clinical_train.arff")));
        BufferedReader ftr = new BufferedReader(ftrain);
		

		while((line=ftr.readLine()).contains("@data")==false){

			// Obtener las particulas presentes en el documento de train
			if (line.contains("@attribute tokensP")==true) {
				tokensPtrain.addAll(getCategories(line));
			}


			// Obtener el pos de las particulas presentes en el documento de train
			if (line.contains("@attribute POSP")==true) {
				POSPtrain.addAll(getCategories(line));
			}


			// Obtener los tokens presentes en el documento de train
			if (line.contains("@attribute Token")==true) {
				tokenstrain.addAll(getCategories(line));
			}


			// Obtener los pos de los tokens presentes en el documento de train
			if (line.contains("@attribute POST")==true) { 
				POSTtrain.addAll(getCategories(line));
			}


			// Obtener la distancia de los tokens presentes en el documento de train 
			if (line.contains("@attribute distance")==true) { 
				distancetrain.addAll(getCategories(line));
			}


			// Obtener la localización de los tokens presentes en el documento de train 
			if (line.contains("@attribute Location")==true) {
				locationtrain.addAll(getCategories(line));
			}
			
			// Obtener la cadena de POS de los tokens presentes en el documento de train 
			if (line.contains("@attribute POSS")==true) {
				POSStrain.addAll(getCategories(line));
			}


			// Obtener los tokens 0 presentes en el documento de train 
			if (line.contains("@attribute Token0")==true) {
				token0train.addAll(getCategories(line));
			}


			// Obtener los tokens 1 presentes en el documento de train 
			if (line.contains("@attribute Token1")==true) {
				token1train.addAll(getCategories(line));
			}


			// Obtener los POS de los tokens 0 presentes en el documento de train 
			if (line.contains("@attribute POSW0")==true) { 
				POS0train.addAll(getCategories(line));
			}

			// Obtener los POS de los tokens 1 presentes en el documento de train 
			if (line.contains("@attribute POSW1")==true) { 
				POS1train.addAll(getCategories(line));
			}

			// Obtener los TypeS presentes en el documento de train 
			if (line.contains("@attribute TypeS")==true) { 
				typeStrain.addAll(getCategories(line));
			}

			// Obtener los PlaceS presentes en el documento de train 
			if (line.contains("@attribute PlaceS")==true) { 
				placeStrain.addAll(getCategories(line));
			}


			// Obtener los PlaceT presentes en el documento de train 
			if (line.contains("@attribute PlaceT")==true) {
				placeTtrain.addAll(getCategories(line));
			}     

		}
		


		// Recorrer el índice con la información del documento con el que generar el fichero .arff
		int i=0; 
		while(i<words.size()){
			signals.clear();
			signals=new ArrayList<String[]>();
			// Obtener cada una de las frases del documento
			if (words.get(i).getWord().getbeginSentence()==true){
				numSentence++;
				indexini=i;
				indexfin=i;
				while(words.get(indexfin).getWord().getendSentence()==false)
					indexfin++;	
			}

			// Obtener las negaciones/especulaciones presentes en la frase
			for (int j=indexini;j<=indexfin;j++){
				// Al generar el test, las negaciones provienen de la clasificación previa del documento de test        	
				if (indexClassify.get(j).getType().equals("bn")){
					int a=j;
					int b=j+1;
					int size=1;
					s=new String[3];
					s[0]=indexClassify.get(a).getToken();
					s[1]=Integer.toString(a);  				
					s[2]=Integer.toString(size);
					signals.add(s);		
				}	

			} // Fin "for obtener negaciones"

			// Escribir en el fichero .arff de datos, una línea por cada token, por
			// negación en la frase (Ej. 3 negaciones -> 3 líneas por cada token en la frase)
			for (int k=0;k<signals.size();k++){

				// Escribir el número de frase en el que se encuentra la partícula
				fDatos.println("% "+numSentence);

				// Posición de la particula en el índice
				int pos=Integer.parseInt(signals.get(k)[1]);
				// Tamaño de la partícula
				int tam=Integer.parseInt(signals.get(k)[2]);

				// Obtener y almacenar la información referente a las PALABRAS ANTERIORES
				// a la partícula en la frase
				for (int l=indexini;l<pos;l++){
					// Partícula de negación
					if (tokensPtrain.contains(signals.get(k)[0]))
						fDatos.print(signals.get(k)[0]);
					else
						fDatos.print("?");


					// POS de la partícula

					if (POSPtrain.contains(words.get(pos).getWord().getPOS()))
						fDatos.print(","+words.get(pos).getWord().getPOS());
					else
						fDatos.print(",?");


					// Token "in focus"
					if (tokenstrain.contains(indexClassify.get(l).getToken()))
						fDatos.print(","+indexClassify.get(l).getToken());
					else
						fDatos.print(",?");

					// POS del token "in focus"
					if (POSTtrain.contains(words.get(l).getWord().getPOS()))
						fDatos.print(","+words.get(l).getWord().getPOS());
					else
						fDatos.print(",?");
					
					
					// Distancia desde el token "in focus" a la partícula (posición partícula - posición token)
					String dis=Integer.toString(pos-l);
					if (distancetrain.contains(dis))
						fDatos.print(","+dis);
					else
						fDatos.print(",?");
					
					
					// Localización relativa del token "in focus" respecto de la partícula
					fDatos.print(",pre");
					
					// POS desde el token "in focus" hasta la partícula
					String cadenaposs="";
					String aux="";
					
					for (int m=l;m<pos;m++){
						aux=words.get(indexini+m).getWord().getPOS();
						
						if (m==l)
							cadenaposs=cadenaposs+aux;
					
						else
							cadenaposs=cadenaposs+"_"+aux;	
					}
					if (POSStrain.contains(cadenaposs))
						fDatos.print(","+cadenaposs);
					else
						fDatos.print(",?");
					
					
					
					// Información asociada a la ventana 
					// Token de las palabras de la ventana (token a izq y token a dcha)
					// Izquierda
					Window w=new Window();
					w=words.get(indexini+l).getWindow().get(0);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getToken();
						if (token0train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}


					// Derecha
					w=new Window();
					w=words.get(indexini+l).getWindow().get(1);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getToken();
						if (token1train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}
					
					
					
					// POS de las palabras de la ventana (token a izq y token a dcha)
					// Izquierda
					w=new Window();
					w=words.get(indexini+l).getWindow().get(0);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getPOS();
						if (POS0train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}


					// Derecha
					w=new Window();
					w=words.get(indexini+l).getWindow().get(1);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getPOS();
						if (POS1train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}
					
					
					// Tipos desde el token "in focus" hasta la partícula			        			
					String p="";
					String type="";
					for (int m=l;m<pos;m++){
						type=words.get(indexini+m).getWord().getType();
						if (m==l)	
							p=p+type;
						
						else
							p=p+"_"+type;
									   
					}

					if (typeStrain.contains(p))
						fDatos.print(","+type);
					else
						fDatos.print(","+'?');

					
					
					//Posición partícula en la frase
					if (placeStrain.contains(((float)pos)/((float)indexfin)))
						fDatos.print(","+(((float)pos)/((float)indexfin)));	
					else
						fDatos.print(","+'?');

					
					// Posición token en la frase
					if (placeTtrain.contains(((float)l)/((float)indexfin)))
						fDatos.print(","+(((float)l)/((float)indexfin)));	
					else
						fDatos.print(","+'?');


					// Clase
					fDatos.println(",?");       			

				}
				// Obtener y almacenar la información referente a las PALABRAS POSTERIORES
				// a la partícula en la frase
				for (int l=pos+tam;l<=indexfin;l++){
					// Partícula de negación
					if (tokensPtrain.contains(signals.get(k)[0]))
						fDatos.print(signals.get(k)[0]);
					else
						fDatos.print("?");


					// POS de la partícula		
					if (POSPtrain.contains(words.get(pos).getWord().getPOS()))
						fDatos.print(","+words.get(pos).getWord().getPOS());
					else
						fDatos.print(",?");
					
					// Token "in focus"
					if (tokenstrain.contains(indexClassify.get(l).getToken()))
						fDatos.print(","+indexClassify.get(l).getToken());
					else
						fDatos.print(",?");

					// POS del token "in focus"
					if (POSTtrain.contains(words.get(l).getWord().getPOS()))
						fDatos.print(","+words.get(l).getWord().getPOS());
					else
						fDatos.print(",?");

					
					// Distancia desde el token "in focus" a la partícula (posición partícula - posición token)
					String dis=Integer.toString(l-pos);
					if (distancetrain.contains(dis))
						fDatos.print(","+dis);
					else
						fDatos.print(",?");
					
					// Localización relativa del token "in focus" respecto de la partícula
					fDatos.print(",post");
					
					// POS desde el token "in focus" hasta la partícula
					String cadenaposs="";
					String aux="";
					
					for (int m=l;m>pos;m--){
						aux=words.get(indexini+m).getWord().getPOS();
						
						if (m==l)
							cadenaposs=cadenaposs+aux;
					
						else
							cadenaposs=cadenaposs+"_"+aux;	
					}
					if (POSStrain.contains(cadenaposs))
						fDatos.print(","+cadenaposs);
					else
						fDatos.print(",?");
					
					
					
					// Información asociada a la ventana 
					// Token de las palabras de la ventana (token a izq y token a dcha)
					// Izquierda
					Window w=new Window();
					w=words.get(indexini+l).getWindow().get(0);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getToken();
						if (token0train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}


					// Derecha
					w=new Window();
					w=words.get(indexini+l).getWindow().get(1);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getToken();
						if (token1train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}
					
					
					
					// POS de las palabras de la ventana (token a izq y token a dcha)
					// Izquierda
					w=new Window();
					w=words.get(indexini+l).getWindow().get(0);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getPOS();
						if (POS0train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}


					// Derecha
					w=new Window();
					w=words.get(indexini+l).getWindow().get(1);
					if (w.getType()==null){
						fDatos.print(","+'?');
					}
					else{
						aux=w.getPOS();
						if (POS1train.contains(aux))
							fDatos.print(","+aux);
						else
							fDatos.print(","+'?');

					}
					
					
					// Tipos desde el token "in focus" hasta la partícula			        			
					String p="";
					String type="";
					for (int m=l;m>pos;m--){
						type=words.get(indexini+m).getWord().getType();
						if (m==l)	
							p=p+type;
						
						else
							p=p+"_"+type;
									   
					}

					if (typeStrain.contains(p))
						fDatos.print(","+type);
					else
						fDatos.print(","+'?');

					
					
					//Posición partícula en la frase
					if (placeStrain.contains(((float)pos)/((float)indexfin)))
						fDatos.print(","+(((float)pos)/((float)indexfin)));	
					else
						fDatos.print(","+'?');

					
					// Posición token en la frase
					if (placeTtrain.contains(((float)l)/((float)indexfin)))
						fDatos.print(","+(((float)l)/((float)indexfin)));	
					else
						fDatos.print(","+'?');
					
					
					

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
		FileReader fR = new FileReader( conf.getPathClassification().concat("datos.txt") );
		BufferedReader bfR=new BufferedReader(fR);

		while ((linea=bfR.readLine())!=null){
			f.println(linea);
		}


		f.close();
		fR.close();
	}

		
	private Set<String> getCategories(String line){
		Set<String> ret = new HashSet<String>();
		
		line = line.trim();
		line = line.substring(line.indexOf('{')+1, line.indexOf('}'));

		StringTokenizer z = new StringTokenizer(line,",");
		int num = z.countTokens();
		
		for (int i=0; i<num; i++){
			String token=z.nextToken();
			ret.add(token.trim());			
		}
				
		return ret;
	}




}// Fin de la clase

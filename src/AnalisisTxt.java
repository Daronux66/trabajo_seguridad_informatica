package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AnalisisTxt {

	//private StringBuffer datosAux;
	private String datosBase;
	private Map<String, Integer> mapaDatos;
	private int numCaracteres;
	private String alfabeto;
	//private int lenghtPalabara;


	public AnalisisTxt(boolean conPath, String path) {
		StringBuffer strbuff= new StringBuffer();
		try {
			File file = new File(path);
			Scanner myReader = new Scanner(file);
			strbuff =new StringBuffer();
			while (myReader.hasNextLine()) {
				strbuff.append(myReader.nextLine());
				if(myReader.hasNextLine()) strbuff.append("  ");
			}
			myReader.close();
			this.datosBase=strbuff.toString();
			System.out.println("Datos brutos con nuevos espacios:\n"+this.datosBase);
			this.alfabeto=setMap(1);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	public AnalisisTxt (int sinpath, String datosBase) {
		this.datosBase=datosBase;
		//this.datosAux=new StringBuffer(datosBase);
		this.alfabeto=setMap(1);
	}

	public AnalisisTxt (String datosBase, int lenghtPalabara) {
		this.datosBase=datosBase;
		//this.datosAux=new StringBuffer(datosBase);
		this.alfabeto=setMap(lenghtPalabara);
		//this.lenghtPalabara=lenghtPalabara;
	}


	/**
	 * @return the alfabeto
	 */
	public String getAlfabeto() {
		return alfabeto;
	}
	/**
	 * @param alfabeto the alfabeto to set
	 */
	public void setAlfabeto(String alfabeto) {
		this.alfabeto = alfabeto;
	}
	/**
	 * @return the datosBase
	 */
	public String getDatosBase() {
		return datosBase;
	}

	/**
	 * @param datosBase the datosBase to set
	 */
	public void setDatosBase(String datosBase) {
		this.datosBase = datosBase;
	}

	/**
	 * @return the mapaDatos
	 */
	public Map<String, Integer> getMapaDatos() {
		return mapaDatos;
	}

	/**
	 * @param mapaDatos the mapaDatos to set
	 */
	public void setMapaDatos(int tamanioCaracteres) {
		setMap(tamanioCaracteres);
	}

	/**
	 * @return the numCaracteres
	 */
	public int getNumCaracteres() {
		return numCaracteres;
	}

	/**
	 * @param numCaracteres the numCaracteres to set
	 */
	public void setNumCaracteres(int numCaracteres) {
		this.numCaracteres = numCaracteres;
	}

	public String setMap(int lenghtPalabara) {
		mapaDatos=new HashMap<String, Integer>();
		StringBuffer datosAux=new StringBuffer(datosBase);

		String caracteres_aux;
		int contAux;
		numCaracteres=0;
		StringBuffer sb= new StringBuffer();
		System.out.println("Caracteres por orden de llegada:");
		while(!datosAux.isEmpty()&&datosAux.length()>=lenghtPalabara) {
			caracteres_aux = datosAux.substring(0, lenghtPalabara);

			if(mapaDatos.containsKey(caracteres_aux)) {
				contAux = mapaDatos.get(caracteres_aux);
				mapaDatos.replace(caracteres_aux, contAux+1);
			}
			else {
				System.out.print(caracteres_aux);
				sb.append(caracteres_aux);
				mapaDatos.put(caracteres_aux, 1);
			}

			numCaracteres++;
			datosAux.delete(0, lenghtPalabara);
			//System.out.println(datosAux.toString());
			//if(datosAux.length()<lenghtPalabara);
		}
		System.out.println("mapa size = "+mapaDatos.size());
		printCantidadInfo();
		return sb.toString();
		//System.out.println(mapaDatos);
	}

	/**
	 * 
	 * @param i
	 * @return
	 * 		-1 error
	 * 		frecuencia del caracter mandado por parametro
	 */
	public int getFrecChar(String caracter) {
		if(mapaDatos.isEmpty()&&!mapaDatos.containsKey(caracter)) return -1;
		return mapaDatos.get(caracter);
	}

	public Float getProbChar(String caracter) {
		if(mapaDatos.isEmpty()&&!mapaDatos.containsKey(caracter)) return (float) -1;
		Float prob=(float)(mapaDatos.get(caracter))/(float)(numCaracteres);
		return prob;
	}

	public float printCantidadInfo() {
		float cantInf=0;
		for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			float prob=getProbChar(entry.getKey());
			cantInf+=log((1f/prob), 2);
		}
		System.out.println("Cantidad información = "+cantInf);
		return cantInf;
	}

	public Float getEntropia() {
		// H(f)=log2(tot) - [ (1/tot) * sum<m,i=1>( f(i)*log2(f(i)) ) ]
		// H(f)=a-(bc)
		if(mapaDatos.isEmpty()) return (float) -1;
		float tot;
		float a = log(numCaracteres, 2);
		float b= (float)1/ (float)numCaracteres;
		float c=0;
		float frecAux;
		for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			frecAux=entry.getValue();
			c+=(frecAux*log(frecAux, 2));
		}

		tot=a-(b*c);

		return tot;
	}

	public float getEficacia(int lenghtPalabara) {
		float h=this.getEntropia();
		float log2q=this.log(this.mapaDatos.size(), 2);
		float res=h/(log2q*(float)lenghtPalabara);
		return res;		
	}

	public Map<String, Integer> sortedMap() {
		Map<String, Integer> sortedMap = mapaDatos.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						Map.Entry::getValue, 
						(viejo, nuevo) -> viejo, LinkedHashMap::new));
		return sortedMap;
	}

	private Float log(float num, int base) {
		return ( ((float) Math.log10(num)) / ((float) (Math.log10(base)) ) );
	}


	public String mapToString(Map<String, Integer> sortedMap) {
		StringBuffer buff=new StringBuffer("      Símbolo\t|   Frecuencia\t|\tProbabilidad\n"
				+ "-----------------------------------------------------\n");
		for (Map.Entry<String, Integer> entry : sortedMap().entrySet()) {
			buff.append("\t'"+ entry.getKey() +"'\t|\t"+entry.getValue()+"\t|\t"+getProbChar(entry.getKey()));
			buff.append("\n");
		}
		buff.delete(buff.length()-1, buff.length());
		return buff.toString();
	}

	public String mapUnOrderedToString() {
		StringBuffer buff=new StringBuffer("      Símbolo\t|   Frecuencia\t|\tProbabilidad\n"
				+ "-----------------------------------------------------\n");
		for (Map.Entry<String, Integer> entry : this.mapaDatos.entrySet()) {
			buff.append("\t'"+ entry.getKey() +"'\t|\t"+entry.getValue()+"\t|\t"+getProbChar(entry.getKey()));
			buff.append("\n");
		}
		buff.delete(buff.length()-1, buff.length());
		return buff.toString();
	}

	public String mapOrderedToString() {
		StringBuffer buff=new StringBuffer("      Símbolo\t|   Frecuencia\t|\tProbabilidad\n"
				+ "-----------------------------------------------------\n");
		for (Map.Entry<String, Integer> entry : this.sortedMap().entrySet()) {
			buff.append("\t'"+ entry.getKey() +"'\t|\t"+entry.getValue()+"\t|\t"+getProbChar(entry.getKey()));
			buff.append("\n");
		}
		buff.delete(buff.length()-1, buff.length());
		return buff.toString();
	}

	public static void main(String[] args) {
		StringBuffer strbuff =new StringBuffer();
		try {
			File myObj = new File("datos_1.txt");
			Scanner myReader = new Scanner(myObj);
			strbuff =new StringBuffer();
			while (myReader.hasNextLine()) {
				strbuff.append(myReader.nextLine());
				if(myReader.hasNextLine()) strbuff.append("  ");
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//System.out.println("Input: " + strbuff.toString());
		if(strbuff.isEmpty())return;

		AnalisisTxt txt=new AnalisisTxt(strbuff.toString(),1);

		StringBuffer buff= new StringBuffer();
		for (Map.Entry<String, Integer> entry : txt.getMapaDatos().entrySet()) {
			buff.append(entry.getKey());
		}
		System.out.println(buff.toString());
		//System.out.println("-1 = error\nFrec ', ': "+txt.getFrecChar(", ")+" / Prob ', ': "+txt.getProbChar(", ") + " /Entropia de F: "+txt.getEntropia());


		/*AnalisisTxt txt=new AnalisisTxt(strbuff.toString());
		System.out.println("-1 = error\nFrec d: "+txt.getFrecChar("d")+" / Prob d: "+txt.getProbChar("d") + " /Entropia de F: "+txt.getEntropia());
		 */
		//System.out.println(txt.mapToString(txt.sortedMap()));
	}
}

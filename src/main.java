import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws FileNotFoundException {
        List<Transicoes> list = new ArrayList<>();
        List<Transicoes> auxList = new ArrayList<>(); // Criação da lista auxiliar
        List<String> estados = new ArrayList<>();
        List<Transicoes> entradaA = new ArrayList<>();
        List<Transicoes> entradaB = new ArrayList<>();
        
        int cont=0;
        String estInicial = null;
		
        FileInputStream entradaArquivo = new FileInputStream(new File("c:\\Curso Java\\test.dat"));
        Scanner lerArquivo = new Scanner (entradaArquivo, "UTF-8");
        int i=0;
        String[] conjuntoEstados = null;
        String[] alfabeto = null;
        while(lerArquivo.hasNext()) {
        	 i++;
            String linha = lerArquivo.nextLine();
            if(i==1 && (linha!=null)) {
            	conjuntoEstados =  linha.split(",");
    
            }else if(i==2 && (linha!=null)) {
            	alfabeto = linha.split(",");
            }
            else {
                if(linha!=null && !linha.isEmpty()) {
                    String[] dados = linha.split("\\,");
                    Transicoes trans = new Transicoes(linha, linha, linha);

                    if (dados.length >= 3 && !linha.startsWith("*") ) {
                        trans.setOrigin(dados[0]);
                        trans.setSymbol(dados[1]);
                        trans.setDestiny(dados[2]);
                        list.add(trans);
                        
                    }else if (linha.startsWith(">")) {
                        estInicial = linha.substring(1);
                    }else if (linha.startsWith("*")) {
                    	String[] dadosLinhaDelimitada = linha.substring(1).split("\\,");
                        for (String dado : dadosLinhaDelimitada) {
                            estados.add(dado);
                        }
                    }
                
                }
            }

        }
        
       System.out.println("TRANSICOES LIDAS DO ARQUIVO:   ");
       for(Transicoes trans: list) {
    	   System.out.println(trans);
       }
       
       System.out.println("Estado inicial: " + estInicial);
       System.out.println("");
       for (String estado : estados) {
    	    System.out.println("Estado final: " + estado);
       }
      System.out.println("<------------------------------------>"); 
      System.out.println("Testando equivalencias de cada transicao");
      //verificarEquivalencia(list, estInicial, estados);
      for (Transicoes trans : list) {
    	   	
    	    System.out.println(trans);
    	    if (trans.getOrigin().equals(estInicial) || estados.contains(trans.getOrigin())) {
    	        //System.out.println("Estado de origem " + trans.getOrigin() + " encontrado!");
    	        cont++;
    	    }
    	    if (trans.getDestiny().equals(estInicial) || estados.contains(trans.getDestiny())) {
    	       // System.out.println("Estado de destino " + trans.getDestiny() + " encontrado!");
    	        cont++;
    	    }
    	    if(cont%2==0) {
    	    	System.out.println("São equivalentes");
    	    	auxList.add(trans); // Adiciona o objeto na lista auxiliar
    	    	
    	    }else System.out.println("Nao sao equivalentes");
    	    
    	   cont = 0;
    	}
    	
      System.out.println("<------------------------------------>"); 
       System.out.println("Transições Equivalentes encontradas :");
       
       for (Transicoes transEquivalente : auxList) { // itera sobre a lista auxiliar
           System.out.println(transEquivalente.getOrigin() + " -> " + transEquivalente.getSymbol() + " -> " + transEquivalente.getDestiny());
       }
       
       boolean isAFD = temTransicoesRepetida(list);
       
       System.out.println("É uma AFD? " + isAFD);
       
       System.out.println("Conjunto de estados: ");
       for(String x: conjuntoEstados) {
    	   System.out.print(x + " ");
       }
       System.out.println("");
       System.out.println("Alfabeto: ");
       for(String x: alfabeto) {
    	   System.out.print(x + " ");
       }
       System.out.println("");
       int TAM = conjuntoEstados.length ;
       String[][] matriz = new String[TAM][TAM];
       preencheMatriz(matriz,conjuntoEstados,TAM);
       
       for (Transicoes trans : auxList) {
    	    String origin = trans.getOrigin();
    	    String destiny = trans.getDestiny();
    	    int linha = -1; // posição da linha na matriz
    	    int coluna = -1; // posição da coluna na matriz

    	    // procura a posição da linha da origem
    	    for (i = 0; i < matriz.length; i++) {
    	        if (origin.equals(matriz[i][0])) {
    	            linha = i;
    	            break;
    	        }
    	    }

    	    // procura a posição da coluna do destino
    	    for (int j = 0; j < matriz[0].length; j++) {
    	        if (destiny.equals(matriz[matriz.length-1][j])) {
    	            coluna = j;
    	            break;
    	        }
    	    }

    	    matriz[linha][coluna] = " O";
    	}
       int teste = 1 ;
		       for(i=0; i<TAM; i++) {
		         	if(teste<TAM)
		       		teste++;
		           for(int j=0; j<teste; j++) {
		
		             System.out.print(matriz[i][j] + "|");
		           }
		           System.out.println();
		         }
     }
       
       
       /*alteraSimbolos(auxList, 'a'); 
       
       
       System.out.println("<------------------------------------>"); 
       System.out.println("Testando a entrada 'a':");
       
       for (Transicoes transEquivalente : auxList) { // itera sobre a lista auxiliar
           System.out.println(transEquivalente.getOrigin() + " -> " + transEquivalente.getSymbol() + " -> " + transEquivalente.getDestiny());
       }
       
       
       System.out.println("Transicoes encontradas':");
       adicionarTransicoes(auxList, list,entradaA);
       imprimirLista(entradaA);
       
       System.out.println("<------------------------------------>"); 
       System.out.println("Testando equivalencias");
       System.out.println(""); 
       verificarEquivalencia(entradaA, estInicial, estados);
       
       /*System.out.println("Testando a entrada 'b':");
       
       System.out.println("Transicoes encontradas':");
       
       alteraSimbolos(auxList, 'b'); 
       
       adicionarTransicoes(auxList, list,entradaB);
       compararListas(list,entradaB);
       imprimirLista(entradaB);
       System.out.println("<------------------------------------>"); 
       System.out.println("Testando equivalencias"); 
       System.out.println(""); 
       verificarEquivalencia(entradaB, estInicial, estados);
       System.out.println("LISTA ORIGINAL"); 
      */

       
       
    
    
    
    
    
    public static boolean temTransicoesRepetida(List<Transicoes> transicoes) {
        Map<String, Map<String, Integer>> estadoSimboloContagem = new HashMap<>();
        for (Transicoes trans : transicoes) {
            String estado = trans.getOrigin();
            String simbolo = trans.getSymbol();
            if (!estadoSimboloContagem.containsKey(estado)) {
                estadoSimboloContagem.put(estado, new HashMap<String, Integer>());
            }
            Map<String, Integer> simboloContagem = estadoSimboloContagem.get(estado);
            if (!simboloContagem.containsKey(simbolo)) {
                simboloContagem.put(simbolo, 0);
            }
            int count = simboloContagem.get(simbolo);
            simboloContagem.put(simbolo, count + 1);
        }
        for (Map.Entry<String, Map<String, Integer>> entry : estadoSimboloContagem.entrySet()) {
            Map<String, Integer> simboloContagem = entry.getValue();
            for (int count : simboloContagem.values()) {
                if (count > 1) {
                    return false; // há mais de uma transição com o mesmo símbolo para um estado, portanto é AFND
                }
            }
        }
        return true; // nenhuma transição possui o mesmo estado de origem e símbolo, portanto é AFD
    }
    
    
    public static void alteraSimbolos(List<Transicoes> lista, char caractere) {
        for (Transicoes trans : lista) {
            trans.setSymbol(String.valueOf(caractere)); // Altera o símbolo da transição para o caractere desejado
        }
    }
    
    public static void verificarEquivalencia(List<Transicoes> entradaA, String estInicial, List<String> estados) {
        List<Transicoes> auxList = new ArrayList<>();
        int cont = 0;

        for (Transicoes trans : entradaA) {
            System.out.println(trans);
            if (trans.getOrigin().equals(estInicial) || estados.contains(trans.getOrigin())) {
                cont++;
            }
            if (trans.getDestiny().equals(estInicial) || estados.contains(trans.getDestiny())) {
                cont++;
            }
            if (cont % 2 == 0) {
                System.out.println("São equivalentes");
                 auxList.add(trans);
            } else {
                System.out.println("Não são equivalentes");
            }
            cont = 0;
        }
    }
    
    public static void adicionarTransicoes(List<Transicoes> auxList, List<Transicoes> list, List<Transicoes> entradaA) {
        for (Transicoes transAux : auxList) {
            for (Transicoes transList : list) {
                if ((transAux.getOrigin().equals(transList.getOrigin()) && transAux.getSymbol().equals(transList.getSymbol())) ||
                        (transAux.getDestiny().equals(transList.getOrigin()) && transAux.getSymbol().equals(transList.getSymbol()))) {
                    //System.out.println(transList);
                    entradaA.add(transList);
                }
            }
        }
    }
    
    public static <T> void compararListas(List<T> lista1, List<T> lista2) {
    	List<T> listaRemover = new ArrayList<>();
        for (T obj : lista2) {
            if (!lista1.contains(obj)) {
                listaRemover.add(obj);
            }
        }
        lista2.removeAll(listaRemover);
    }
    
    public static <T> void imprimirLista(List<T> lista) {
        for (T obj : lista) {
            System.out.println(obj);
        }
    }
    
    public static void preencheMatriz(String[][] matriz, String[] conjuntoEstados, int TAM) {
        int aux = 1;
        int i, j;
        
        for (i = 0; i < TAM; i++) {
            for (j = 0; j < TAM; j++) {
                if (j == 0 && i != TAM-1) {
                    matriz[i][j] = conjuntoEstados[aux++];
                } else if (i == TAM-1 && j == 0) {
                    matriz[i][j] = "  ";
                } else {
                    matriz[i][j] = " X";
                }
            }
        }

        int b = 0;
        for (i = 0; i < TAM; i++) {
            b = 0;
            for (j = 0; j < TAM; j++, b++) {
                if (i == TAM-1 && j >= 1) {
                    matriz[i][j] = conjuntoEstados[b-1];
                  
                }
            }
        }
        int teste=1;
        for(i=0; i<TAM; i++) {
          	if(teste<TAM)
        		teste++;
            for(j=0; j<teste; j++) {

              System.out.print(matriz[i][j] + "|");
            }
            System.out.println();
          }
    }
}
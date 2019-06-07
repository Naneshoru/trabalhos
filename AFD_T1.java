/*
 *  Este programa simula autômatos finitos 
 *  Dada uma lista de cadeias, discrimina quais pertencem a linguagem 
 *  (aceita/rejeita)
 */

import java.util.Scanner;
/**
 * @author  Ricardo Atakiama    10262482
 *          Felipe Rabachute    10349529
 *          Victor Grecca       10392185
 */

class AutomatoFinito {

    protected int numEstados;                  //1 a 10    quantidade máxima = 10 estados
    protected int numTerminais;
    protected char[] listaTerminais;  		   //ex: a,b,c quantidade máxima = 10 simbolos terminais (Σ)
    protected int numEstadosIniciais;		   //AFD -> 1  AFN-> de 2 a 10 estados iniciais 
    protected int numEstadosAceitacao;
    protected int[] estadosAceitacao;         //ex: q0, q1, q2... 
    protected int numTransicoes;
    protected int[][] listaTransicoes;
    protected int numCadeias;
    protected char[][] listaCadeias;
    protected int[] listaCorrecao;
    
    public AutomatoFinito(){
        
    }
    public int getNumEstados() { 
        return numEstados;
    }
    public void setNumEstados(int numEstados) { 
        this.numEstados = numEstados;       
    }
    public String[] getlistaSimbolos(String[] listaSimbolos) {
        return listaSimbolos;
    }
    public void setListaSimbolos(char[] listaSimbolos) { 
        this.numTerminais = listaSimbolos.length;
        this.listaTerminais = new char[numTerminais];
        for(int i=0; i<numTerminais; i++){
        	this.listaTerminais[i] = listaSimbolos[i];
        }
    }
    public int getNumEstadosIniciais() { 
        return numEstadosIniciais;
    }
    public void setNumEstadosIniciais(int numEstadosIniciais) { 
    	this.numEstadosIniciais = numEstadosIniciais;
//    	System.out.println(numEstadosIniciais);
    }
    public int[] getEstadosAceitacao() {
        return estadosAceitacao;
    }
    public void setEstadosAceitacao(int[] estadosAceitacao) {
        this.numEstadosAceitacao = estadosAceitacao.length;
        this.estadosAceitacao = new int[numEstadosAceitacao];
        for(int i = 0; i < numEstadosAceitacao; i++) {
        	this.estadosAceitacao[i] = estadosAceitacao[i];
        }
    }
    public int getNumTransicoes() {
        return numTransicoes;
    }

    public void setTransicoes(int numTransicoes, Scanner leitura) { // le as transicoes, Matriz[origem][simbolo] = destino

    	this.listaTransicoes = new int[numEstados][numTerminais];
        
        for(int i=0;i<listaTransicoes.length; i++){     // primeiro, inicializa matriz com -1, evitando posição 0 default
            for(int j=0; j<listaTransicoes[i].length; j++){
                listaTransicoes[i][j] = -1;
//                System.out.printf("%d ", listaTransicoes[i][j]);
            }
//            System.out.println("");
        }
        
        this.numTransicoes = numTransicoes;
        int origem,destino;
        char simbolo;
        
        for(int i = 0; i < numTransicoes; i++) {
        	origem = leitura.nextInt();
        	simbolo = leitura.next().charAt(0);
        	destino = leitura.nextInt();
        	this.listaTransicoes[origem][(int)simbolo%numTerminais] = destino;
//        	System.out.println(origem + " " + (int)simbolo%numTerminais);
        }
    }
    public void setCadeias(int numCadeias,Scanner ler) {    // le as cadeias de entrada, transformando em Matriz de char M[numCadeias][20]
    	this.numCadeias = numCadeias;
    	this.listaCadeias = new char[numCadeias][20];
    	String buffer = ler.nextLine();
    	for(int i = 0; i < numCadeias; i++) {
    		this.listaCadeias[i] = ler.nextLine().toCharArray();    
//    		System.out.println(i + " " + String.valueOf(listaCadeias[i]));
    	}
    }
    public void testaEntradas() {   // testa o final de cada cadeia, se está em algum estado de aceitacao = aceita
    	int posAtual = 0;
    	this.listaCorrecao =  new int[numCadeias];
    	for(int i = 0;i < numCadeias;i++) {
    		for(int j = 0;j < listaCadeias[i].length; j++) {
                    if(listaCadeias[i][j] == '-'){
//                        System.out.println("Lambda, posição atual é a mesma");
//                        System.out.println("posAtual = " + posAtual);
                        continue;
                    }
//                    System.out.println("posAtual("+ posAtual +") = listaTransicoes["+ posAtual + "][(int)listaCadeias["+i+"]["+j+"]%"+numTerminais+" = "+ listaTransicoes[posAtual][(int)listaCadeias[i][j]%numTerminais]+", (int)listaCadeias[i][j]%numTerminais ="+ (int)listaCadeias[i][j]%numTerminais);
                    posAtual = listaTransicoes[posAtual][(int)listaCadeias[i][j]%numTerminais];  // posicao atual é a final da transicao feita
                    if(posAtual == -1){
//                        System.out.println("rejeição por regra não definida"); // qdo não há regra definida, basta acontecer uma vez, cadeia será recusada
                        break;
                    }
    		}
    		listaCorrecao[i] = 0;
    		for(int k = 0;k < estadosAceitacao.length; k++) {   // 
    			if(posAtual == estadosAceitacao[k]){        
                            listaCorrecao[i] = 1;                            
//                            System.out.println("aceita para estado " + estadosAceitacao[k]);
                            break;              // Só precisa ser aceita em um caso (estado) para valer como cadeia aceita
                        }
//                        else{
//                            System.out.println("posAtual = " + posAtual);
//                            System.out.println("rejeitado por não alcançou estado de aceitação " + estadosAceitacao[k]);
//                        }
    		}           
                posAtual = 0;   // zera a posicao atual, para processar a proxima cadeia	
    	}
    }
}

class Main{
    public static void main(String[] args) {
        AutomatoFinito af = new AutomatoFinito();
        Scanner ler = new Scanner(System.in);
        
//        System.out.println("\t\tSimulador de automato finito\n");
        
        int numEstados; //Linha 1
        do{
//            System.out.println("Número de estados possiveis = __ (Mín = 1 | Máx = 10)");
            numEstados = ler.nextInt();
            af.setNumEstados(numEstados);
        }while(numEstados<1 || numEstados>10); // restringe de 1 a 10
        
        int numSimbolos; // Linha 2
        char[] listaSimbolos;
        do{
//            System.out.println("Número de simbolos terminai + elementos = __ _ _ _ (Mín = 1 | Máx = 10)");
            numSimbolos = ler.nextInt();
            listaSimbolos = new char[numSimbolos];
            for(int i=0; i<numSimbolos; i++){
                    listaSimbolos[i] = ler.next().charAt(0);
//                    System.out.println(listaSimbolos[i]);
            }
            af.setListaSimbolos(listaSimbolos);
        }while(numSimbolos<1 || numSimbolos>10); // restringe de 1 a 10
        						
        int numEstadosIniciais; // Linha 3
//        System.out.println("Número de estados iniciais = __ (Mín = 1 | Máx = 10)"); 
        numEstadosIniciais = ler.nextInt();
        af.setNumEstadosIniciais(numEstadosIniciais);
            
        int numEstadosAceitacao; // Linha 4
        int[] estadosAceitacao;
//        System.out.println("Número de estados de aceitacao + elementos = __ _ _ _ (Mín = 1 | Máx = 10)");
        numEstadosAceitacao = ler.nextInt();
        estadosAceitacao = new int[numEstadosAceitacao];
        for(int i = 0; i < numEstadosAceitacao; i++) {
        	estadosAceitacao[i] = ler.nextInt();
        }
        af.setEstadosAceitacao(estadosAceitacao);
        
        int numTransicoes; // Linha 5 - transicoes
//        System.out.println("Número de transicoes da máquina = __ (Máx = 50)");
        numTransicoes = ler.nextInt();
        af.setTransicoes(numTransicoes,ler);

        int numCadeias; // linha 6 - cadeias de entrada
//        System.out.println("Número de cadeias de entrada + elemetos = __ _ _ _ (Comprimento Máx = 20)");
        numCadeias = ler.nextInt();
        af.setCadeias(numCadeias,ler);
        
        af.testaEntradas();	//Faz a analise do automato
        
        for(int i = 0; i < af.numCadeias;i++) {
        	if(af.listaCorrecao[i] == 1)  System.out.println("aceita");
        	else System.out.println("rejeita");
        }
        ler.close();
    }
}
    


    


/*
* Este programa simula uma Máquina de Turing Universal (Determinística)
* Dada entrada de uma MT com suas instruções, discriminar se as cadeias fornecidas são aceitas ou rejeitadas 
*/

/**
* @author  Ricardo Atakiama - 10262482
*     
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class MaquinaDeTuring {
    
    int numEstados;
    int numTerminais;
    int numAlfabetoExtendido;
    int numTransicoes;
    int numCadeias;
    String estadoAceitacao;        
    char[] listaTerminais; 
    char[] listaAlfabetoExtendido;
    String[] listaCadeias;
    String[][] listaTransicoes;

    public MaquinaDeTuring() {
    }

    public int getNumEstados() { 
        return numEstados;
    }
    public void setNumEstados(int numEstados) { 
        this.numEstados = numEstados;       
    }
    
    public int getNumTerminais() {
        return numTerminais;
    }
    
    public void setNumTerminais(int numTerminais) {
        this.numTerminais = numTerminais;
    }
    
    public int getNumAlfabetoExtendido() {
        return numAlfabetoExtendido;
    }

    public void setNumAlfabetoExtendido(int numAlfabetoExtendido) {
        this.numAlfabetoExtendido = numAlfabetoExtendido;
    }
     
    public int getNumTransicoes() {
        return numTransicoes;
    }

    public void setNumTransicoes(int numTransicoes) {
        this.numTransicoes = numTransicoes;
    }
      
    public int getNumCadeias() {
        return numCadeias;
    }

    public void setNumCadeias(int numCadeias) {
        this.numCadeias = numCadeias;
    }
    
    public String getEstadoAceitacao() {
        return estadoAceitacao;
    }
    public void setEstadoAceitacao(String estadoAceitacao) {
        this.estadoAceitacao = estadoAceitacao;
    }
    
    public char[] getListaTerminais() {
        return listaTerminais;
    }
    
    public void setListaTerminais(char[] listaSimbolos) { 
        this.numTerminais = listaSimbolos.length;
        this.listaTerminais = new char[numTerminais];
        System.arraycopy(listaSimbolos, 0, this.listaTerminais, 0, numTerminais);
    }
    
    public char[] getListaAlfabetoExtendido() {
        return listaAlfabetoExtendido;
    }

    public void setListaAlfabetoExtendido(char[] listaAlfabetoExtendido) {
        this.listaAlfabetoExtendido = listaAlfabetoExtendido;
    }
    
    public String[] getListaCadeias() {
        return listaCadeias;
    }
    
    public void setListaCadeias(String[] listaCadeias) {
        this.listaCadeias = listaCadeias;
    }
    
    public String[][] getListaTransicoes() {
        return listaTransicoes;
    }
    
    public void setListaTransicoes(String[][] listaTransicoes) {
        this.listaTransicoes = listaTransicoes;
    }
    
    public void lerNotacao() throws IOException {
        ArrayList<String> transicoes = new ArrayList<>();
        ArrayList<String> cadeias = new ArrayList<>();
        
        //Ler por arquivo
//        FileReader arq = new FileReader(path);
//        BufferedReader input = new BufferedReader(arq);

        //Ler por teclado
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String linha = input.readLine();   // lê a primeira linha: o numero de estados
        setNumEstados(Integer.parseInt(linha));
//        System.out.println("n estados " + numEstados);
        
        linha = input.readLine();          // lê a segunda linha: o numero de terminais seguidos dos próprios terminais
        linha = linha.replaceAll(" ", "");
        setNumTerminais(Integer.parseInt(linha.substring(0, 1)));
//        System.out.println("n terminais " + numTerminais);
        
        char[] alfabeto = new char[numTerminais];
        
        for(int i=0; i<numTerminais; i++){
            alfabeto[i] = linha.charAt(i+1);
//            System.out.println(i+1);
//            System.out.println("alfabeto terminal = " + alfabeto[i]);
        }
        
        setListaTerminais(alfabeto);
//        System.out.println(getListaTerminais());
        
        linha = input.readLine(); // lê a  terceira linha: o alfabeto extendido da fita
        linha = linha.replaceAll(" ", "");
        setNumAlfabetoExtendido(Integer.parseInt(linha.substring(0, 1)));
//        System.out.println(numAlfabetoExtendido);
        
        char[] extendido = new char[numAlfabetoExtendido];
        
        for(int i=0; i<numAlfabetoExtendido; i++){
            extendido[i] = linha.charAt(i+1);
//            System.out.println(i+1);
//            System.out.println("alfabeto extendido = " + extendido[i]);
        }
        
        setListaAlfabetoExtendido(extendido);
//        System.out.println(getListaAlfabetoExtendido());
        
        linha = input.readLine();   // lê a quarta linha: o numero de estados
        setEstadoAceitacao(linha);
//        System.out.println("estado de aceitação = " + getEstadoAceitacao());
        
        linha = input.readLine();   // lê a quinta linha: o numero de transições
        setNumTransicoes(Integer.parseInt(linha));
//        System.out.println("número de transições = " + getNumTransicoes());
        
        for(int i=0; i<getNumTransicoes(); i++){
            linha = input.readLine(); 
            linha = linha.replaceAll(" ", "");
            transicoes.add(linha);
        }
//        System.out.println("arraylist de transicoes = "  + transicoes); 
        
        String[][] transicoesStr = new String[getNumTransicoes()][5]; // transforma a arraylist em matriz
        String aux;
//        System.out.println("matriz transicoes ok");
        for (int i = 0; i < getNumTransicoes(); i++) {
            if (transicoes.get(i).isEmpty() == false) {
                aux = transicoes.get(i);
                transicoesStr[i][0] = aux.substring(0, 1);
//                System.out.print(transicoesStr[i][0]);
                transicoesStr[i][1] = aux.substring(1, 2);
//                System.out.print(transicoesStr[i][1]);
                transicoesStr[i][2] = aux.substring(2, 3);
//                System.out.print(transicoesStr[i][2]);
                transicoesStr[i][3] = aux.substring(3, 4);
//                System.out.print(transicoesStr[i][3]);
                transicoesStr[i][4] = aux.substring(4, 5);
//                System.out.print(transicoesStr[i][4]);
//                System.out.println("");
            }
        }
        setListaTransicoes(transicoesStr);
        
        linha = input.readLine();   // lê a sexta linha: o numero de cadeias a serem analisadas
        setNumCadeias(Integer.parseInt(linha));
//        System.out.println(getNumCadeias());
        
        for(int i=0; i<getNumCadeias(); i++){
            linha = input.readLine();
            cadeias.add(linha);
        }
//        System.out.println("arraylist de cadeias = " + cadeias);
        
        String[] cadeiasStr = new String[numCadeias];
        
        for(int i = 0; i < numCadeias; i++) {
            cadeiasStr[i] = cadeias.get(i);    
//            System.out.println(i + "  " + cadeiasStr[i]);
        }
        
        setListaCadeias(cadeiasStr);

    }

    

    public String processar(String [][] transicoes, String fitaStr, String estadoAtual, String estadoFinal) {

        fitaStr = "B" + fitaStr;
        fitaStr += "&&B";
        
        Integer posicaoFita = 1;
        boolean vazio = false;
        boolean achou;
        
        StringBuilder fita = new StringBuilder(fitaStr);

        // Enquanto nao sair dos limites...
        while (posicaoFita < fitaStr.length() + 2 && posicaoFita >= 0) { 
            achou = false;
//            System.out.println("1");
            for (int i = 0; i < getNumTransicoes(); i++) {
                if (estadoAtual.equals(transicoes[i][0])) { // procura o estado atual na lista de transicoes
//                    System.out.println("entrei");
                    if (fitaStr.substring(posicaoFita, posicaoFita + 1).equals("-")) { // entrada lambda
                        posicaoFita += 1;
                    }

                    if (fitaStr.substring(posicaoFita, posicaoFita + 1).equals("&")) { 
                        vazio = true;
                        fita.replace(posicaoFita, posicaoFita + 1, "B");
                        fitaStr = fita.toString();
//                        System.out.println(fitaStr);
//                        System.out.println("posFita = " + posicaoFita);
//                        System.out.println("estado Atual = " + estadoAtual);
//                        System.out.println("2");
                    }
                    if (fitaStr.substring(posicaoFita, posicaoFita + 1).equals(transicoes[i][1])) {// se achou o terminal nas transicoes
//                        System.out.println("3");
                        achou = true;
                        
                        fita.replace(posicaoFita, posicaoFita + 1, transicoes[i][3]); //sobreescreve na fita
//                        System.out.println(fitaStr);
                        fitaStr = fita.toString();
//                        System.out.println("fita = fita.toString = " + fitaStr);
                        if (transicoes[i][4].equalsIgnoreCase("R")) {   //atualiza posicao da fita
//                            System.out.println("posFita = " + posicaoFita);
                            posicaoFita += 1;
                        } else if (transicoes[i][4].equalsIgnoreCase("L")) {
//                            System.out.println("posFita = " + posicaoFita);
                            posicaoFita -= 1;
                        }
//                        System.out.println("estado Atual = " + estadoAtual);
                        estadoAtual = transicoes[i][2]; // estado atual passa a ser o antigo estado final
                        break;
                    }
                }
            }
            
            if (getEstadoAceitacao().equals(estadoAtual)) {
//                System.out.println("4");
                return "aceita";
            }
            
            if (fitaStr.substring(posicaoFita, posicaoFita + 1).equals("&") && vazio == true) {
//                System.out.println("5");
                return "rejeita";
            }
            
            if (achou == false) {
//                System.out.println("6");
                return "rejeita";
            }
            
        }
        return "rejeita";
    }

}   


class Main {

  
    public static void main(String[] args) throws IOException {
        MaquinaDeTuring t = new MaquinaDeTuring();
        
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        String nomeArq = input.readLine();
        
        try{
            t.lerNotacao();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        for(int i=0; i<t.getNumCadeias(); i++){
            System.out.println(t.processar(t.getListaTransicoes(), t.listaCadeias[i], "0", t.getEstadoAceitacao()));
        }
    }
}

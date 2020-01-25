/*
 * 
 * 
 * 
 */
package Model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Ricardo Atakiama
 */
public class ModelTabuleiro implements Observer, Serializable, Cloneable{

    private boolean whiteBotton;    // se as peças brancas estao em baixo
    private boolean whiteToMove;    // se é a vez das brancas
    private boolean whiteWins;
    private boolean blackWins;
    private final ArrayList<Peca> pecasPretas;
    private final ArrayList<Peca> pecasBrancas;
    private Rei reiBranco;
    private Rei reiPreto;

    public ModelTabuleiro()  {
        this.pecasPretas = new ArrayList<Peca>();
        this.pecasBrancas  = new ArrayList<Peca>();
        this.whiteWins = false;
        this.blackWins = false;
        init();
    }
    /*  Tabuleiro
    
        T C B RA RE B C T   PRETAS  (0,0 - 7,0)
        P P P P  P  P P P           (0,1 - 7,1)
        X X X X  X  X X X
        X X X X  X  X X X
        X X X X  X  X X X
        X X X X  X  X X X
        P P P P  P  P P P           (0,6 - 7,6)
        T C B RA RE B C T   BRANCAS (0,7 - 7,7)
    */
    
    public void init(){
        
        //inicializa time branco
        
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,0,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,1,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,2,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,3,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,4,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,5,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,6,6));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,7,6));
        getPecasBrancas().add(new Torre(this, Peca.Cor.BRANCO,0,7));
        getPecasBrancas().add(new Cavalo(this, Peca.Cor.BRANCO,1,7));
        getPecasBrancas().add(new Bispo(this, Peca.Cor.BRANCO,2,7));
        getPecasBrancas().add(new Rainha(this, Peca.Cor.BRANCO,3,7));
        getPecasBrancas().add(new Rei(this, Peca.Cor.BRANCO,4,7));
        getPecasBrancas().add(new Bispo(this, Peca.Cor.BRANCO,5,7));
        getPecasBrancas().add(new Cavalo(this, Peca.Cor.BRANCO,6,7));
        getPecasBrancas().add(new Torre(this, Peca.Cor.BRANCO,7,7));
        
        //inicializa time preto
        
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,0,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,1,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,2,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,3,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,4,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,5,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,6,1));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,7,1));
        getPecasPretas().add(new Torre(this, Peca.Cor.PRETO,0,0));
        getPecasPretas().add(new Cavalo(this, Peca.Cor.PRETO,1,0));
        getPecasPretas().add(new Bispo(this, Peca.Cor.PRETO,2,0));
        getPecasPretas().add(new Rainha(this, Peca.Cor.PRETO,3,0));
        getPecasPretas().add(new Rei(this, Peca.Cor.PRETO,4,0));
        getPecasPretas().add(new Bispo(this, Peca.Cor.PRETO,5,0));
        getPecasPretas().add(new Cavalo(this, Peca.Cor.PRETO,6,0));
        getPecasPretas().add(new Torre(this, Peca.Cor.PRETO,7,0));
        
        setWhiteBotton(true);
        setWhiteToMove(true);
        
        //Armazenando referencias
        setReiBranco(findReiBranco());
        getReiBranco().setTorre1(findPeca(0, 0));
        getReiBranco().setTorre2(findPeca(7, 0));
        getReiBranco().setTorre3(findPeca(0, 7));
        getReiBranco().setTorre4(findPeca(7, 7));
        setReiPreto(findReiPreto());
        getReiPreto().setTorre1(findPeca(0, 0));
        getReiPreto().setTorre2(findPeca(7, 0));
        getReiPreto().setTorre3(findPeca(0, 7));
        getReiPreto().setTorre4(findPeca(7, 7));
    }
    
    public void initBlack(){
        
        //inicializa time branco
        
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,0,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,1,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,2,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,3,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,4,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,5,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,6,1));
        getPecasBrancas().add(new Peao(this, Peca.Cor.BRANCO,7,1));
        getPecasBrancas().add(new Torre(this, Peca.Cor.BRANCO,0,0));
        getPecasBrancas().add(new Cavalo(this, Peca.Cor.BRANCO,1,0));
        getPecasBrancas().add(new Bispo(this, Peca.Cor.BRANCO,2,0));
        getPecasBrancas().add(new Rei(this, Peca.Cor.BRANCO,3,0));
        getPecasBrancas().add(new Rainha(this, Peca.Cor.BRANCO,4,0));
        getPecasBrancas().add(new Bispo(this, Peca.Cor.BRANCO,5,0));
        getPecasBrancas().add(new Cavalo(this, Peca.Cor.BRANCO,6,0));
        getPecasBrancas().add(new Torre(this, Peca.Cor.BRANCO,7,0));
        
        //inicializa time preto
        
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,0,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,1,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,2,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,3,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,4,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,5,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,6,6));
        getPecasPretas().add(new Peao(this, Peca.Cor.PRETO,7,6));
        getPecasPretas().add(new Torre(this, Peca.Cor.PRETO,0,7));
        getPecasPretas().add(new Cavalo(this, Peca.Cor.PRETO,1,7));
        getPecasPretas().add(new Bispo(this, Peca.Cor.PRETO,2,7));
        getPecasPretas().add(new Rei(this, Peca.Cor.PRETO,3,7));
        getPecasPretas().add(new Rainha(this, Peca.Cor.PRETO,4,7));
        getPecasPretas().add(new Bispo(this, Peca.Cor.PRETO,5,7));
        getPecasPretas().add(new Cavalo(this, Peca.Cor.PRETO,6,7));
        getPecasPretas().add(new Torre(this, Peca.Cor.PRETO,7,7));
        
        setWhiteBotton(false);
        setWhiteToMove(true);
        
        //Armazena referencias
        setReiBranco(findReiBranco());
        getReiBranco().setTorre1(findPeca(0, 0));
        getReiBranco().setTorre2(findPeca(7, 0));
        getReiBranco().setTorre3(findPeca(0, 7));
        getReiBranco().setTorre4(findPeca(7, 7));
        setReiPreto(findReiPreto());
        getReiPreto().setTorre1(findPeca(0, 0));
        getReiPreto().setTorre2(findPeca(7, 0));
        getReiPreto().setTorre3(findPeca(0, 7));
        getReiPreto().setTorre4(findPeca(7, 7));
    }

    public Rei getReiBranco() {
        return reiBranco;
    }

    public void setReiBranco(Rei reiBranco) {
        this.reiBranco = reiBranco;
    }

    public Rei getReiPreto() {
        return reiPreto;
    }

    public void setReiPreto(Rei reiPreto) {
        this.reiPreto = reiPreto;
    }
    

    public boolean isWhiteBotton() {
        return whiteBotton;
    }

    public void setWhiteBotton(boolean whiteBotton) {
        this.whiteBotton = whiteBotton;
    }

    public boolean isWhiteToMove() {
        return whiteToMove;
    }

    public void setWhiteToMove(boolean whiteToMove) {
        this.whiteToMove = whiteToMove;
    }

    public boolean isWhiteWins() {
        return whiteWins;
    }

    public void setWhiteWins(boolean whiteWins) {
        this.whiteWins = whiteWins;
    }

    public boolean isBlackWins() {
        return blackWins;
    }

    public void setBlackWins(boolean blackWins) {
        this.blackWins = blackWins;
    }
    
    @Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  
    
    public ArrayList<Peca> getPecasPretas() {
        return pecasPretas;
    }

    public ArrayList<Peca> getPecasBrancas() {
        return pecasBrancas;
    }
    
    public void reset(){
        getPecasBrancas().clear();
        getPecasPretas().clear();
    }
    
    public void remove(Peca peca){
        if(getPecasBrancas().contains(peca))
            getPecasBrancas().remove(peca);
        else if(getPecasPretas().contains(peca))
            getPecasPretas().remove(peca);
    }
    
    public Peca findPeca(int x, int y) {
        Peca peca = null;
        
        //desenha pecas Brancas
        for(Peca p : getPecasBrancas()){
            if(p.inSquare(x,y)){
                return p;
            }
        }
        
        //desenha pecas pretas
        for(Peca p : getPecasPretas()){
            if(p.inSquare(x,y)){
                return p;
            }
        }
        
        return peca;
    }
    
    public Rei findReiBranco(){
        for(Peca p: getPecasBrancas())
            if(p instanceof Rei){
                return (Rei) p;
            }
        return null;
    }
    
    public Rei findReiPreto(){
        for(Peca p: getPecasPretas())
            if(p instanceof Rei){
                return (Rei) p;
            }
        return null;
    }
    
    public Peao VerificaSeCoroou() {
        Peao peao = null;
        
        //confere se algum peao branco coroou
        for(Peca p : getPecasBrancas()){
            if(p instanceof Peao){
                if((p.getQuadrante().getY() == 0 && isWhiteBotton()) || 
                   (p.getQuadrante().getY() == 7 && !isWhiteBotton()) ){
                    return (Peao) p;
                }
            }
        }
        
        //confere se algum peao preto coroou
        for(Peca p : getPecasPretas()){
            if(p instanceof Peao){
                if(p.getQuadrante().getY() == 7 && isWhiteBotton() ||
                   p.getQuadrante().getY() == 0 && !isWhiteBotton()){
                    return (Peao) p;
                }
            }
        }
        
        return peao;
    }
    
    public void draw(Graphics2D g){
        //desenha pecas Brancas
        for(Peca p : getPecasBrancas()){
            p.draw(g);
        }
        
        //desenha pecas pretas
        for(Peca p : getPecasPretas()){
            p.draw(g);
        }
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        draw((Graphics2D) arg);
    }
    
}

/*
 * 
 * 
 * 
 */

package Model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo Atakiama
 */
public class Rei extends Peca{

    private boolean inCheck; // Se rei está ameaçado em cheque
    private boolean podeRocart1; // se pode fazer roque com torre 1 (esquerda de cima)
    private boolean podeRocart2; // se pode fazer roque com torre 2 (direita de cima)
    private boolean podeRocart3; // se pode fazer roque com torre 3 (esquerda de baixo)
    private boolean podeRocart4; // se pode fazer roque com torre 4 (direita de baixo)
    //Posições das torres
    private Point t1_pos;
    private Point t2_pos;
    private Point t3_pos;
    private Point t4_pos; 
    //Referências das torres
    private Peca torre1;
    private Peca torre2;
    private Peca torre3;
    private Peca torre4;

    public Rei(ModelTabuleiro model, Cor cor, int x, int y)  {
        super(model, cor, x, y);
        this.inCheck = false;
        this.podeRocart1 = false;
        this.podeRocart2 = false;
        this.podeRocart3 = false;
        this.podeRocart4 = false;
    }

    public boolean getPodeRocart1() {
        return podeRocart1;
    }

    public void setPodeRocart1(boolean podet1) {
        this.podeRocart1 = podet1;
    }

    public boolean getPodeRocart2() {
        return podeRocart2;
    }

    public void setPodeRocart2(boolean podet2) {
        this.podeRocart2 = podet2;
    }

    public boolean getPodeRocart3() {
        return podeRocart3;
    }

    public void setPodeRocart3(boolean podet3) {
        this.podeRocart3 = podet3;
    }

    public boolean getPodeRocart4() {
        return podeRocart4;
    }

    public void setPodeRocart4(boolean podet4) {
        this.podeRocart4 = podet4;
    }

    public Peca getTorre1() {
        return torre1;
    }

    public void setTorre1(Peca torre1) {
        this.torre1 = torre1;
    }

    public Peca getTorre2() {
        return torre2;
    }

    public void setTorre2(Peca torre2) {
        this.torre2 = torre2;
    }

    public Peca getTorre3() {
        return torre3;
    }

    public void setTorre3(Peca torre3) {
        this.torre3 = torre3;
    }

    public Peca getTorre4() {
        return torre4;
    }

    public void setTorre4(Peca torre4) {
        this.torre4 = torre4;
    }

    public boolean isPodeRocart1() {
        return podeRocart1;
    }

    public boolean isPodeRocart2() {
        return podeRocart2;
    }

    public boolean isPodeRocart3() {
        return podeRocart3;
    }

    public boolean isPodeRocart4() {
        return podeRocart4;
    }

    public Point getT1_pos() {
        return t1_pos;
    }

    public Point getT2_pos() {
        return t2_pos;
    }

    public Point getT3_pos() {
        return t3_pos;
    }

    public Point getT4_pos() {
        return t4_pos;
    }

    
    
    public void setT1_pos(Point t1_pos) {
        this.t1_pos = t1_pos;
    }

    public void setT2_pos(Point t2_pos) {
        this.t2_pos = t2_pos;
    }

    public void setT3_pos(Point t3_pos) {
        this.t3_pos = t3_pos;
    }

    public void setT4_pos(Point t4_pos) {
        this.t4_pos = t4_pos;
    }
    
    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        System.out.println(this + "estou em check");
        this.inCheck = inCheck;
    }
    
    @Override
    public boolean isPossibleMove(Point p) {
        try {
            inBoard(p);// Dentro dos limites do 8x8
            //Movimento genérico de Rei    
            if(p.getX() == getQuadrante().getX()   && p.getY() == getQuadrante().getY()-1   ||  // movimento norte
               p.getX() == getQuadrante().getX()+1 && p.getY() == getQuadrante().getY()-1   ||  // movimento nordeste
               p.getX() == getQuadrante().getX()+1 && p.getY() == getQuadrante().getY()     ||  // movimento leste
               p.getX() == getQuadrante().getX()+1 && p.getY() == getQuadrante().getY()+1   ||  // movimento sudeste
               p.getX() == getQuadrante().getX()   && p.getY() == getQuadrante().getY()+1   ||  // movimento sul
               p.getX() == getQuadrante().getX()-1 && p.getY() == getQuadrante().getY()+1   ||  // movimento sudoeste
               p.getX() == getQuadrante().getX()-1 && p.getY() == getQuadrante().getY()     ||  // movimento oeste
               p.getX() == getQuadrante().getX()-1 && p.getY() == getQuadrante().getY()-1 )     // movimento noroeste
                return true;

            //Se for rei de baixo branco ou rei de cima preto 
            if(getCor().equals(Peca.Cor.BRANCO) && getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && getModel().isWhiteBotton()){
                setT1_pos(new Point(3, 0)); 
                setT2_pos(new Point(5, 0)); 
                setT3_pos(new Point(3, 7)); 
                setT4_pos(new Point(5, 7)); 

                //Se vai fazer o roque esquerdo em cima
                if(p.getX() == 2 && p.getY() == 0 && this.isFirtMove() && getTorre1().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(1, 0) == null && getModel().findPeca(2, 0) == null && getModel().findPeca(3, 0) == null){
                        setPodeRocart1(true);
                        setPodeRocart2(false);
                        setPodeRocart3(false);
                        setPodeRocart4(false);
                        return true;
                    }//Se vai fazer o roque direita em cima
                }else if(p.getX() == 6 && p.getY() == 0 && this.isFirtMove() && getTorre2().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(5, 0) == null && getModel().findPeca(6, 0) == null){
                        setPodeRocart2(true);
                        setPodeRocart1(false);
                        setPodeRocart3(false);
                        setPodeRocart4(false);
                        return true;
                    }//Se vai fazer o roque esquerda em baixo
                }if(p.getX() == 2 && p.getY() == 7 && this.isFirtMove() && getTorre3().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(1, 7) == null && getModel().findPeca(2, 7) == null && getModel().findPeca(3, 7) == null){
                        setPodeRocart3(true);
                        setPodeRocart1(false);
                        setPodeRocart2(false);
                        setPodeRocart4(false);
                        return true;
                    }//Se vai fazer o roque direita em baixo
                }else if(p.getX() == 6 && p.getY() == 7 && this.isFirtMove() && getTorre4().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(5, 7) == null && getModel().findPeca(6, 7) == null){
                        setPodeRocart4(true);
                        setPodeRocart1(false);
                        setPodeRocart2(false);
                        setPodeRocart3(false);
                        return true;
                    }
                }
            //Se for rei de cima branco ou rei de baixo preto 
            }else if(getCor().equals(Peca.Cor.BRANCO) && !getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && !getModel().isWhiteBotton()){
                setT1_pos(new Point(2, 0)); 
                setT2_pos(new Point(4, 0)); 
                setT3_pos(new Point(2, 7)); 
                setT4_pos(new Point(4, 7)); 

                //Se vai fazer o roque esquerdo em cima
                if(p.getX() == 1 && p.getY() == 0 && this.isFirtMove() && getTorre1().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(1, 0) == null && getModel().findPeca(2, 0) == null){
                        setPodeRocart1(true);
                        setPodeRocart2(false);
                        setPodeRocart3(false);
                        setPodeRocart4(false);
                        return true;
                    }//Se vai fazer o roque direita em cima
                }else if(p.getX() == 5 && p.getY() == 0 && this.isFirtMove() && getTorre2().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(4, 0) == null && getModel().findPeca(5, 0) == null && getModel().findPeca(6, 0) == null){
                        setPodeRocart2(true);
                        setPodeRocart1(false);
                        setPodeRocart3(false);
                        setPodeRocart4(false);
                        return true;
                    }
                }//Se vai fazer o roque esquerda em baixo
                if(p.getX() == 1 && p.getY() == 7 && this.isFirtMove() && getTorre3().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(1, 7) == null && getModel().findPeca(2, 7) == null){
                        setPodeRocart3(true);
                        setPodeRocart1(false);
                        setPodeRocart2(false);
                        setPodeRocart4(false);
                        return true;
                    }//Se vai fazer o roque direita em baixo
                }else if(p.getX() == 5 && p.getY() == 7 && this.isFirtMove() && getTorre4().isFirtMove()){
                    //Não pode ter peça nestes quadrantes
                    if(getModel().findPeca(4, 7) == null && getModel().findPeca(5, 7) == null && getModel().findPeca(6, 7) == null){
                        setPodeRocart4(true);
                        setPodeRocart1(false);
                        setPodeRocart2(false);
                        setPodeRocart3(false);
                        return true;
                    }
                }
            }
        }catch (OutOfBoardException ex) {
            Logger.getLogger(Rainha.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Se for rei de cima branco ou rei de baixo preto



        return false; // não encontrou lance


    }
    
    @Override
    public Point move(Point p) {
        
        System.out.println("Posicao inicial do rei = ("+ this.getQuadrante()+ ")");
        
        if(getPodeRocart1() && (p.getX() == 1 && p.getY() == 0 && getCor().equals(Peca.Cor.BRANCO)|| p.getX() == 2 && p.getY() == 0 && getCor().equals(Peca.Cor.PRETO))) {
            getTorre1().move(getT1_pos());
            if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);    // como está usando move da torre, está trocando a vez 2x
            else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true); // então estas linhas consertam isso
        }
        else if(getPodeRocart2() && (p.getX() == 5 && p.getY() == 0 && getCor().equals(Peca.Cor.BRANCO) || p.getX() == 6 && p.getY() == 0 && getCor().equals(Peca.Cor.PRETO))){
            getTorre2().move(getT2_pos());
            if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
            else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        }
        else if(getPodeRocart3() && (p.getX() == 1 && p.getY() == 7 && getCor().equals(Peca.Cor.PRETO) || p.getX() == 2 && p.getY() == 7 && getCor().equals(Peca.Cor.BRANCO))){
            getTorre3().move(getT3_pos());
            if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
            else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);   
        }
        else if(getPodeRocart4() && (p.getX() == 5 && p.getY() == 7 && getCor().equals(Peca.Cor.PRETO) || p.getX() == 6 && p.getY() == 7 && getCor().equals(Peca.Cor.BRANCO))) {
            getTorre4().move(getT4_pos());
            if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
            else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        }
        
        setPodeRocart1(false);
        setPodeRocart2(false);
        setPodeRocart3(false);
        setPodeRocart4(false);
        
        setQuadrante((int)p.getX(), (int)p.getY());
        if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
        else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        setFirstMove(false);
        
        System.out.println("Posicao final do rei = ("+ this.getQuadrante()+ ")");
        
        return getQuadrante();
    }
    
    @Override
    public String toString() {
        if(this.getCor() == Peca.Cor.PRETO){
            return "Rei Preto";
        } else {
            return "Rei Branco";
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        int squareWidth = g.getClip().getBounds().width / 8;
        int squareHeight = g.getClip().getBounds().height / 8;
        
        int x0 = quadrante.x * (squareWidth + 1);
        int y0 = quadrante.y * (squareHeight + 1);
        int x1 = x0 + squareWidth;
        int y1 = y0 + squareHeight;
        
        if(this.getCor() == Peca.Cor.PRETO){
            g.drawImage(pecasImg, x0, y0, x1, y1, 20, 20, 60, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 20, 74, 60, 114, null);
        }
    }

}

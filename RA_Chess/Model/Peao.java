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
public class Peao extends Peca{
    
    private boolean enPassant;
    private boolean coroar;
    
    public Peao(ModelTabuleiro model, Cor cor, int x, int y)  {
        super(model, cor, x, y);
        this.coroar = false;
    }
    
    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }
    
    public boolean getCoroar() {
        return coroar;
    }

    public void setCoroar(boolean coroar) {
        this.coroar = coroar;
    }
    
    @Override
    public boolean isPossibleMove(Point p) {
        
        try {
            inBoard(p);
            
            Peca peca_no_quadrante = getModel().findPeca((int)p.getX(), (int)p.getY());
            //Jogador do lado inferior, pode ser branco ou preto
            if(getCor().equals(Peca.Cor.BRANCO) && getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && !getModel().isWhiteBotton()){

                //Movimendo de andar 1 casa: não pode ter peça nesta casa
                if(peca_no_quadrante == null && p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY() -1 ){
                    return true;

                //Movimento de andar 2 casas:     
                }else if(peca_no_quadrante == null && 
                    getModel().findPeca((int)getQuadrante().getX(), (int)getQuadrante().getY() -1) == null &&    // não tem peça no caminho 
                    isFirtMove() &&                                                                         // é primeiro lance da peça
                    p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY() -2 ){            // anda 2 casas
                        //procura possível passant (OBS: Este lance só ocorre qdo um peão anda 2 casas!)
                        Peca passant1 = getModel().findPeca((int)getQuadrante().getX() - 1, (int)getQuadrante().getY() - 2);
                        Peca passant2 = getModel().findPeca((int)getQuadrante().getX() + 1, (int)getQuadrante().getY() - 2);

                         //peça que realiza o movimento especial é da outra cor
                        if(passant1 != null && passant1 instanceof Peao && this.getCor() != passant1.getCor())
                            ((Peao) passant1).setEnPassant(true);
                        if(passant2 != null && passant2 instanceof Peao && this.getCor() != passant2.getCor())
                            ((Peao) passant2).setEnPassant(true);
                        this.setEnPassant(true); // todas as 3 possiveis peças ficam enPassant
                        return true;
                //Movimento de tomar outra peça na diagonal
                }else if( (peca_no_quadrante != null) &&  // Se tem peça, toma (diagonal esquerda ou direita)
                        (((p.getX() == getQuadrante().getX() -1) && (p.getY() == getQuadrante().getY() -1)) ||
                        ((p.getX() == getQuadrante().getX() +1) && (p.getY() == getQuadrante().getY() -1))) ){   
                    return true;
                //Movimento en-Passant 
                }else if(isEnPassant() ){
                    Peca pecaSofrendoPassant1 = getModel().findPeca((int)getQuadrante().getX() + 1, (int)getQuadrante().getY());
                    Peca pecaSofrendoPassant2 = getModel().findPeca((int)getQuadrante().getX() - 1, (int)getQuadrante().getY());
                    if(pecaSofrendoPassant1 != null && pecaSofrendoPassant1.getCor() != getCor() )
                        if((p.getX() == getQuadrante().getX() +1) && (p.getY() == getQuadrante().getY() -1)){
                            getModel().getPecasBrancas().remove(pecaSofrendoPassant1);
                            getModel().getPecasPretas().remove(pecaSofrendoPassant1);
                            return true;
                        }
                    if(pecaSofrendoPassant2 != null && pecaSofrendoPassant2.getCor() != getCor() )
                        if((p.getX() == getQuadrante().getX() -1) && (p.getY() == getQuadrante().getY() -1)){
                            getModel().getPecasBrancas().remove(pecaSofrendoPassant2);
                            getModel().getPecasPretas().remove(pecaSofrendoPassant2);
                            return true;
                        }
                }
            }//Jogador do lado superior
            if(getCor().equals(Peca.Cor.BRANCO) && !getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && getModel().isWhiteBotton()){

                //Movimendo de andar 1 casa: não pode ter peça nesta casa
                if(peca_no_quadrante == null && p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY() +1 ){
                    return true;

                //Movimento de andar 2 casas:     
                }else if(peca_no_quadrante == null && 
                    getModel().findPeca((int)getQuadrante().getX(), (int)getQuadrante().getY() +1) == null &&    // não tem peça no caminho 
                    isFirtMove() &&                                                                         // é primeiro lance da peça
                    p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY() +2 ){            // anda 2 casas
                        //procura possível passant (OBS: Este lance só ocorre qdo um peão anda 2 casas!)
                        Peca passant1 = getModel().findPeca((int)getQuadrante().getX() - 1, (int)getQuadrante().getY() + 2);
                        Peca passant2 = getModel().findPeca((int)getQuadrante().getX() + 1, (int)getQuadrante().getY() + 2);

                         //peça que realiza o movimento especial é da outra cor
                        if(passant1 != null && passant1 instanceof Peao && this.getCor() != passant1.getCor())
                            ((Peao) passant1).setEnPassant(true);
                        if(passant2 != null && passant2 instanceof Peao && this.getCor() != passant2.getCor())
                            ((Peao) passant2).setEnPassant(true);
                        this.setEnPassant(true); // todas as 3 possiveis peças ficam enPassant
                        return true;
                //Movimento de tomar outra peça na diagonal
                }else if( (peca_no_quadrante != null) &&  // Se tem peça, toma (diagonal esquerda ou direita)
                        (((p.getX() == getQuadrante().getX() -1) && (p.getY() == getQuadrante().getY() +1)) ||
                        ((p.getX() == getQuadrante().getX() +1) && (p.getY() == getQuadrante().getY() +1))) ){   
                    return true;
                //Movimento en-Passant 
                }else if(isEnPassant() ){
                    Peca pecaSofrendoPassant1 = getModel().findPeca((int)getQuadrante().getX() + 1, (int)getQuadrante().getY());
                    Peca pecaSofrendoPassant2 = getModel().findPeca((int)getQuadrante().getX() - 1, (int)getQuadrante().getY());
                    if(pecaSofrendoPassant1 != null && pecaSofrendoPassant1.getCor() != getCor() )
                        if((p.getX() == getQuadrante().getX() +1) && (p.getY() == getQuadrante().getY() +1)){
                            getModel().getPecasBrancas().remove(pecaSofrendoPassant1);
                            getModel().getPecasPretas().remove(pecaSofrendoPassant1);
                            return true;
                        }
                    if(pecaSofrendoPassant2 != null && pecaSofrendoPassant2.getCor() != getCor() )
                        if((p.getX() == getQuadrante().getX() -1) && (p.getY() == getQuadrante().getY() +1)){
                            getModel().getPecasBrancas().remove(pecaSofrendoPassant2);
                            getModel().getPecasPretas().remove(pecaSofrendoPassant2);
                            return true;
                        }
                }
            }
        } catch (OutOfBoardException ex) {
            Logger.getLogger(Peao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    @Override
    public Point move(Point p) {
        System.out.println("Posicao inicial do peao = ("+ this.getQuadrante());
        
        setQuadrante( (int) p.getX(), (int)( p.getY() ) );
        setEnPassant(false);
        setFirstMove(false);
        if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
        else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        
        System.out.println("Posicao final do peao = ("+ this.getQuadrante());
//        System.out.println("Posicao final do peao = ("+ getQuadrante().getX() +","+ getQuadrante().getY() +")");
        
        //Procura se o rei adversário será ameaçado (check)
        if(getCor().equals(Peca.Cor.BRANCO) && getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && !getModel().isWhiteBotton()){
            Peca peca_ameacada1 = getModel().findPeca((int)(p.getX()-1), (int)(p.getY()-1));
            Peca peca_ameacada2 = getModel().findPeca((int)(p.getX()+1), (int)(p.getY()-1));
            if(peca_ameacada1 instanceof Rei){
                Rei rei_pos1 = (Rei)peca_ameacada1;
                rei_pos1.setInCheck(true);
                this.setCheckinPiece(true);
            }else if(peca_ameacada2 instanceof Rei){
                Rei rei_pos2 = (Rei)peca_ameacada2;
                rei_pos2.setInCheck(true);
                this.setCheckinPiece(true);
            }else{
                this.setCheckinPiece(false);
            }
        }
        if(getCor().equals(Peca.Cor.BRANCO) && !getModel().isWhiteBotton() || getCor().equals(Peca.Cor.PRETO) && getModel().isWhiteBotton()){
            Peca peca_ameacada1 = getModel().findPeca((int)(p.getX()-1), (int)(p.getY()+1));
            Peca peca_ameacada2 = getModel().findPeca((int)(p.getX()+1), (int)(p.getY()+1));
            if(peca_ameacada1 instanceof Rei){
                Rei rei_pos1 = (Rei)peca_ameacada1;
                rei_pos1.setInCheck(true);
                this.setCheckinPiece(true);
            }else if(peca_ameacada2 instanceof Rei){
                Rei rei_pos2 = (Rei)peca_ameacada2;
                rei_pos2.setInCheck(true);
                this.setCheckinPiece(true);
            }else{
                this.setCheckinPiece(false);
            }
        }
        

        return quadrante;
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 322, 20, 362, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 322, 74, 362, 114, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.getCor() == Peca.Cor.PRETO){
            return "Peao Preto";
        } else {
            return "Peao Branco";
        }
    }
}

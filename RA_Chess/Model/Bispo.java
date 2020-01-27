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
public class Bispo extends Peca{
    
    public Bispo(ModelTabuleiro model, Cor cor, int x, int y)  {
        super(model, cor, x, y);
    }
    
    @Override
    public boolean isPossibleMove(Point p) {
        try {
            inBoard(p);// Dentro dos limites do 8x8
                
            boolean pode_nordeste = true;
            boolean pode_sudeste =  true;
            boolean pode_sudoeste = true;
            boolean pode_noroeste = true;

            for(int i=1; i<8; i++){
                // Procura peças em todos os lances adjacentes (distancia i=1, i=2...i=7) 7*4 = 28 vezes
                Peca peca_nordeste = getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY()-i);
                Peca peca_sudeste = getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY()+i);
                Peca peca_sudoeste = getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY()+i);
                Peca peca_noroeste = getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY()-i);

                if(pode_nordeste){
                    if(peca_nordeste != null ){//se tem peça
                        pode_nordeste = false;//nao pode continuar indo para direção
                    }if(p.getX() == getQuadrante().getX()+i && p.getY() == getQuadrante().getY()-i)// pode ir para quadrante
                        return true;

                }if(pode_sudeste){
                    if(peca_sudeste != null){
                        pode_sudeste = false;
                    }if(p.getX() == getQuadrante().getX()+i && p.getY() == getQuadrante().getY()+i)
                        return true;

                }if(pode_sudoeste){
                    if(peca_sudoeste != null){
                        pode_sudoeste = false;
                    }if(p.getX() == getQuadrante().getX()-i && p.getY() == getQuadrante().getY()+i)
                        return true;

                }if(pode_noroeste){
                    if(peca_noroeste != null){
                        pode_noroeste = false;
                    }if(p.getX() == getQuadrante().getX()-i && p.getY() == getQuadrante().getY()-i)
                        return true;
                }
            }
            return false; //nao encontrou lance possivel
        } catch (OutOfBoardException ex) {
            Logger.getLogger(Bispo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; // lance fora do tabuleiro
    }

    @Override
    public Point move(Point p) {
        System.out.println("Posicao inicial do bispo = ("+ this.getQuadrante()+ ")");
        
        setQuadrante((int)p.getX(), (int)p.getY());
        if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
        else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        
        System.out.println("Posicao final do bispo = ("+ this.getQuadrante()+ ")");
        
        //Procura se o rei adversário será ameaçado (check)
        
        boolean pode_nordeste = true;
        boolean pode_sudeste =  true;
        boolean pode_sudoeste = true;
        boolean pode_noroeste = true;
        
        for(int i=1; i<8; i++){

            Peca peca_nordeste = getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY()-i);
            Peca peca_sudeste = getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY()+i);
            Peca peca_sudoeste = getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY()+i);
            Peca peca_noroeste = getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY()-i);

            if(pode_nordeste){
                if(peca_nordeste != null){
                    pode_nordeste = false;
                }if(peca_nordeste instanceof Rei && peca_nordeste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_nordeste = (Rei)peca_nordeste;
                    rei_nordeste.setInCheck(true);
                    this.setCheckinPiece(true);
                    break;
                }else{
                    this.setCheckinPiece(false);
                    System.out.println(isCheckinPiece());
                }
            }
            if(pode_sudeste){
                if(peca_sudeste != null){
                    pode_sudeste = false;
                }if(peca_sudeste instanceof Rei && peca_sudeste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_sul = (Rei)peca_sudeste;
                    rei_sul.setInCheck(true);
                    this.setCheckinPiece(true);
                    break;
                }else{
                    this.setCheckinPiece(false);
                    System.out.println(isCheckinPiece());
                }
            }
            if(pode_sudoeste){
                if(peca_sudoeste != null){
                    pode_sudoeste = false;
                }if(peca_sudoeste instanceof Rei && peca_sudoeste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_leste = (Rei)peca_sudoeste;
                    rei_leste.setInCheck(true);
                    this.setCheckinPiece(true);
                    break;
                }else{
                    this.setCheckinPiece(false);
                    System.out.println(isCheckinPiece());
                }
            }

            if(pode_noroeste){
                if(peca_noroeste != null){
                    pode_noroeste = false;
                }if(peca_noroeste instanceof Rei && peca_noroeste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_oeste = (Rei)peca_noroeste;
                    rei_oeste.setInCheck(true);
                    this.setCheckinPiece(true);
                    break;
                }else{
                    this.setCheckinPiece(false);
                    System.out.println(isCheckinPiece());
                }
            }

        }
        return getQuadrante();
    }

    @Override
    public String toString() {
        if(this.getCor() == Peca.Cor.PRETO){
            return "Bispo Preto";
        } else {
            return "Bispo Branco";
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 200, 20, 240, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 200, 74, 240, 114, null);
        }
    }
}

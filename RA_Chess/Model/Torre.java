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
public class Torre extends Peca{

     public Torre(ModelTabuleiro model, Cor cor, int x, int y)  {
        super(model, cor, x, y);
    }
     
    @Override
    public boolean isPossibleMove(Point p) {
        try {
            inBoard(p);// Dentro dos limites do 8x8

            boolean pode_norte = true;
            boolean pode_sul =  true;
            boolean pode_leste = true;
            boolean pode_oeste = true;

            for(int i=1; i<8; i++){
                // Procura peças em todos os lances adjacentes (distancia i=1, i=2...i=7) 7*4 = 28 vezes
                Peca peca_norte =   getModel().findPeca((int)getQuadrante().getX()  , (int)getQuadrante().getY()-i);
                Peca peca_sul =     getModel().findPeca((int)getQuadrante().getX()  , (int)getQuadrante().getY()+i);
                Peca peca_leste =   getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY());
                Peca peca_oeste =   getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY());

                if(pode_norte){
                    if(peca_norte != null ){//se tem peça
                        pode_norte = false;//nao pode continuar indo para direção
                    }if(p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY()-i)
                        return true;
                }if(pode_sul){
                    if(peca_sul != null){
                        pode_sul = false;
                    }if(p.getX() == getQuadrante().getX() && p.getY() == getQuadrante().getY()+i)
                        return true;
                }if(pode_leste){
                    if(peca_leste != null){
                        pode_leste = false;
                    }if(p.getX() == getQuadrante().getX()+i && p.getY() == getQuadrante().getY())
                        return true;
                }if(pode_oeste){
                    if(peca_oeste != null){
                        pode_oeste = false;
                    }if(p.getX() == getQuadrante().getX()-i && p.getY() == getQuadrante().getY())
                        return true;
                }
            }
            return false; //nao encontrou lance possivel
            
        } catch (OutOfBoardException ex) {
            Logger.getLogger(Torre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; // lance fora do tabuleiro
    }


    @Override
    public Point move(Point p) {
        
        System.out.println("Posicao inicial da torre = ("+ this.getQuadrante()+ ")");
        
        setQuadrante((int)p.getX(), (int)p.getY());
        if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
        else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        setFirstMove(false);
        
        System.out.println("Posicao final da torre = ("+ this.getQuadrante()+ ")");
        
        //Procura se o rei adversário será ameaçado (check)
        
        boolean pode_norte = true;
        boolean pode_sul =  true;
        boolean pode_leste = true;
        boolean pode_oeste = true;
        
    for(int i=1; i<8; i++){
            Peca peca_norte =   getModel().findPeca((int)getQuadrante().getX()  , (int)getQuadrante().getY()-i);
            Peca peca_sul =     getModel().findPeca((int)getQuadrante().getX()  , (int)getQuadrante().getY()+i);
            Peca peca_leste =   getModel().findPeca((int)getQuadrante().getX()+i, (int)getQuadrante().getY());
            Peca peca_oeste =   getModel().findPeca((int)getQuadrante().getX()-i, (int)getQuadrante().getY());

            if(pode_norte){
                if(peca_norte != null){
                    pode_norte = false;
                }if(peca_norte instanceof Rei && peca_norte.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_nordeste = (Rei)peca_norte;
                    rei_nordeste.setInCheck(true);
                    break;   
                }else{
                    this.setCheckinPiece(false);
                }
            }if(pode_sul){
                if(peca_sul != null){
                    pode_sul = false;
                }if(peca_sul instanceof Rei && peca_sul.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_sul = (Rei)peca_sul;
                    rei_sul.setInCheck(true);
                    break;   
                }else{
                    this.setCheckinPiece(false);
                }
            }if(pode_leste){
                if(peca_leste != null){
                    pode_leste = false;
                }if(peca_leste instanceof Rei && peca_leste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_leste = (Rei)peca_leste;
                    rei_leste.setInCheck(true);
                    break;   
                }else{
                    this.setCheckinPiece(false);
                }
            }if(pode_oeste){
                if(peca_oeste != null){
                    pode_oeste = false;
                }if(peca_oeste instanceof Rei && peca_oeste.getCor() != this.getCor()){ // check se tiver rei
                    Rei rei_oeste = (Rei)peca_oeste;
                    rei_oeste.setInCheck(true);
                    break;   
                }else{
                    this.setCheckinPiece(false);
                }
            }
        }
        
        return getQuadrante();
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 180, 20, 140, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 140, 74, 180, 114, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.getCor() == Peca.Cor.PRETO){
            return "Torre Preta";
        } else {
            return "Torre Branca";
        }
    }
    
}

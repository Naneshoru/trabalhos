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
public class Cavalo extends Peca{

    Point []possibleMove = new Point[8];// cria um vetor de jogadas possíveis
        
    public Cavalo(ModelTabuleiro model, Cor cor, int x, int y)  {
        super(model, cor, x, y);
        possibleMove[0] = new Point(1,-2);
        possibleMove[1] = new Point(2,-1);
        possibleMove[2] = new Point(2, 1);
        possibleMove[3] = new Point(1, 2); 
        possibleMove[4] = new Point(-1,2); 
        possibleMove[5] = new Point(-2,1);
        possibleMove[6] = new Point(-2,-1); 
        possibleMove[7] = new Point(-1,-2);
    }

    public Point[] getMove() {
        return possibleMove;
    }

    public void setMove(Point[] move) {
        this.possibleMove = move;
    }

    @Override
    public boolean isPossibleMove(Point p) {
        try {
            inBoard(p);// Dentro dos limites do 8x8
            for(Point knightMove: getMove()){
                if(p.getX() == this.getQuadrante().getX() + knightMove.getX() &&
                   p.getY() == this.getQuadrante().getY() + knightMove.getY()){

                    return true;
                }
            }
        } catch (OutOfBoardException ex) {
            Logger.getLogger(Bispo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Point move(Point p) {
        System.out.println("Posicao inicial do cavalo = ("+ this.getQuadrante()+ ")");
        
        setQuadrante((int)p.getX(), (int)p.getY());
        if(getModel().isWhiteToMove()) getModel().setWhiteToMove(false);
        else if(!getModel().isWhiteToMove()) getModel().setWhiteToMove(true);
        
        System.out.println("Posicao final do cavalo = ("+ this.getQuadrante()+ ")");
        
        //Procura se o rei adversário será ameaçado (check)
        Peca peca;
        for(Point knightMove: getMove()){
            peca = getModel().findPeca((int)p.getX()+ (int)knightMove.getX(), (int)p.getY() + (int)knightMove.getY());
            
                if(peca instanceof Rei && peca.getCor() != this.getCor()){ // check se tiver rei
                        Rei rei = (Rei)peca;
                        rei.setInCheck(true);
                        this.setCheckinPiece(true);
                        break;
                }else{
                    this.setCheckinPiece(false);
                    System.out.println(isCheckinPiece());
                }
        }
        return getQuadrante();
    }
    
    @Override
    public String toString() {
        if(this.getCor() == Peca.Cor.PRETO){
            return "Cavalo Preto";
        } else {
            return "Cavalo Branco";
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 260, 20, 300, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 260, 74, 300, 114, null);
        }
    }
}

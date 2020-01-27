/*
 * 
 * 
 * 
 */
package Model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo Atakiama
 */
public abstract class Peca implements Serializable{
    
    private final static String IMGPATH = "src/Img/pecasr.png";
    protected static BufferedImage pecasImg = null;    
    private ModelTabuleiro model;
    private Cor cor;
    protected Point quadrante;
    private boolean firstMove;
    private boolean checkinPiece;
    
    public enum Cor{
        PRETO,
        BRANCO
    }
    
    public Peca(ModelTabuleiro model, Cor cor, int x, int y)  {
        this.model = model;
        this.cor = cor;
        this.quadrante = new Point(x,y);
        this.firstMove = true;
        if(pecasImg == null){   // Todas as pecas sao desenhadas com base em uma única figura com todas elas contidas
            try {
                pecasImg = ImageIO.read(new File(IMGPATH));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao ler imagem! \n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
            }
        }
    }

    public ModelTabuleiro getModel() {
        return model;
    }

    public void setModel(ModelTabuleiro model) {
        this.model = model;
    }
    
    
    public Cor getCor() {
        return cor;
    }
     
    public boolean isFirtMove(){
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
    
    
    
    public boolean inSquare(int x, int y){
        return x == quadrante.x && y == quadrante.y;
    }
    
    public boolean inBoard(Point p) throws OutOfBoardException{
        if(!(p.getX() >= 0 && p.getX() < 8 &&
               p.getY() >= 0 && p.getY() < 8)){
            
            throw new OutOfBoardException("Peca fora do tabuleiro");
        }
        return false;
    }
    
    public void setQuadrante(int x, int y){
        quadrante.setLocation(x, y);
    }
    
    public Point getQuadrante(){
        return quadrante.getLocation();
    }

    public boolean isCheckinPiece() {
        return checkinPiece;
    }

    public void setCheckinPiece(boolean checkinPiece) {
        this.checkinPiece = checkinPiece;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peca other = (Peca) obj;
        if (this.firstMove != other.firstMove) {
            return false;
        }
        if (this.model != other.model && (this.model == null || !this.model.equals(other.model))) {
            return false;
        }
        if (this.cor != other.cor) {
            return false;
        }
        return !(this.quadrante != other.quadrante && (this.quadrante == null || !this.quadrante.equals(other.quadrante)));
    }
    
    public abstract boolean isPossibleMove(Point p);
    
    public abstract Point move(Point p);
    
    public abstract void draw(Graphics2D g);
 
}

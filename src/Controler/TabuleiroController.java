/*
 *  
 */
package Controler;


import Model.AutoSave;
import Model.ModelTabuleiro;
import Model.Peca;
import Model.Rei;
import View.JDCoroar;
import View.JDVitoria;
import View.Tabuleiro;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;


/**
 *
 * @author Ricardo Atakiama
 */
public class TabuleiroController implements  MouseListener, MouseMotionListener{
    private ModelTabuleiro model;
    private Tabuleiro view;
    private int mouseX, mouseY, newMouseX, newMouseY;   //Posições x e y dos mouse clicked e released
    private final Point oldPoint;                       //Ponto mouse clicked
    private final Point newPoint;                       //Ponto mouse released
    private Peca pecaEscolhida;                         //Peca que foi clicada
    private Peca PecaCoroada;
    private Point posicao_coroar;                       //Posição do coroamento do peão
    private final ArrayList<ModelTabuleiro> ultimoLance;
//    private final Relogio timerBrancas;
//    private final Relogio timerPretas;
    
    public TabuleiroController() {
        oldPoint = new Point();
        newPoint = new Point();
        ultimoLance = new ArrayList<ModelTabuleiro>();
//        timerBrancas = new Relogio("timerBrancas3m", view);
//        timerPretas = new Relogio("timerPretas3m", view);
    }
    
    public ModelTabuleiro getModel() {
        return model;
    }

    public Tabuleiro getView() {
        return view;
    }
    
    public void addView (Tabuleiro view){
        this.view = view;
    }
    
    public void addModel (ModelTabuleiro model){
        this.model = model;
    }
    
    public Peca getPecaEscolhida() {
        return pecaEscolhida;
    }

    public void setPecaEscolhida(Peca pecaEscolhida) {
        this.pecaEscolhida = pecaEscolhida;
    }
    
    public Peca getCoroada() {
        return PecaCoroada;
    }

    public void setPecaCoroada(Peca PecaCoroada) {
        this.PecaCoroada = PecaCoroada;
    }
    
    public Point getPos_coroar() {
        return posicao_coroar;
    }

    public void setPosicao_coroar(Point posicao_coroar) {
        this.posicao_coroar = posicao_coroar;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getNewMouseX() {
        return newMouseX;
    }

    public int getNewMouseY() {
        return newMouseY;
    }

    public Point getOldPoint() {
        return oldPoint;
    }

    public Point getNewPoint() {
        return newPoint;
    }

    public Peca getPecaCoroada() {
        return PecaCoroada;
    }

    public Point getPosicao_coroar() {
        return posicao_coroar;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void setNewMouseX(int newMouseX) {
        this.newMouseX = newMouseX;
    }

    public void setNewMouseY(int newMouseY) {
        this.newMouseY = newMouseY;
    }
    
    public ArrayList<ModelTabuleiro> getUltimoLance() {
        return ultimoLance;
    }
    
    public void runTabuleiro() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getView().getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getView().getHeight()) / 2);
        getView().setLocation(x, y);
        getView().setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        setMouseX(e.getX());//pega as coordenadas do mouse
        setMouseY(e.getY());
//        view.getClickLabel().setText("x:"+mouseX+"  y:"+mouseY+"   -   Quadrante: ["+mouseX/60+","+mouseY/60+"]");

        getOldPoint().setLocation(getMouseX()/60, getMouseY()/60); // O ponto onde clicou
        setPecaEscolhida(getModel().findPeca(getMouseX()/60, getMouseY()/60)); // Tem peça?
        
//        System.out.println(oldPoint);        
        System.out.println("@");
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        setNewMouseX(e.getX());
        setNewMouseY(e.getY());
        getNewPoint().setLocation(getNewMouseX()/60, getNewMouseY()/60); //O ponto onde soltou  (newPoint)
        
        System.out.println(pecaEscolhida);
        
        Peca pecaTomada = getModel().findPeca(getNewMouseX()/60, getNewMouseY()/60);
        // tem  que clicar, peca escolhida existe e pode ser movida
        // model diz de que cor é a vez e compara com peça escolhida       
        
        if(e.getButton() == MouseEvent.BUTTON1 && getPecaEscolhida() != null && getPecaEscolhida().isPossibleMove(getNewPoint())){
            if( (getModel().isWhiteToMove() && getPecaEscolhida().getCor().equals(Peca.Cor.BRANCO) ||
                !getModel().isWhiteToMove() && getPecaEscolhida().getCor().equals(Peca.Cor.PRETO) ) && 
                (pecaTomada == null || getPecaEscolhida().getCor() != pecaTomada.getCor())){
                // se peça tomada não existe ou é de cor diferente da peça escolhida, move
                
                //Tratamento p/ salvar o lance anterior (para a opção voltar lance, q precisa salvar antes de executar mov.)
                if(!getUltimoLance().isEmpty()) getUltimoLance().remove(0);
                getUltimoLance().add(getModel());
                AutoSave.save(getUltimoLance(), "lastMove");
                
                //Controle de check e mate
                Rei reiBranco = getModel().getReiBranco();
                Rei reiPreto = getModel().getReiPreto();
                
                if(reiBranco.isInCheck() && getModel().isWhiteToMove() || //Se o rei branco está em check e é a vez das brancas
                   reiPreto.isInCheck() && !getModel().isWhiteToMove() ){ //ou se o rei preto está em check e é a vez das pretas
                    //Caso 1: se está em check pode tomar a peça que está dando check 
                    if(pecaTomada != null && pecaTomada.isCheckinPiece() ) {
                        System.out.println("caso 1: tomar a peça que está dando check");
                        getModel().remove(pecaTomada);
                        getPecaEscolhida().move(getNewPoint());
                        setPecaEscolhida(null);
                        if(reiBranco.isInCheck()) reiBranco.setInCheck(false);
                        if(reiPreto.isInCheck()) reiPreto.setInCheck(false);
                        getView().repaint();
                    }//Caso 2: se está em check pode mover o rei para o lado
                    //Condição: para todas as peças adversárias, se nenhuma ataca a nova casa, pode mover
                    else if(getPecaEscolhida().equals(reiBranco) || getPecaEscolhida().equals(reiPreto)){
                       System.out.println("caso 2:  mover o rei para o lado");
                       boolean pode_norte = true;      Point norte = null;
                       boolean pode_nordeste = true;   Point nordeste = null;
                       boolean pode_leste = true;      Point leste = null;
                       boolean pode_sudeste = true;    Point sudeste = null;  
                       boolean pode_sul = true;        Point sul = null;  
                       boolean pode_sudoeste = true;   Point sudoeste = null; 
                       boolean pode_oeste = true;      Point oeste = null;  
                       boolean pode_noroeste = true;   Point noroeste = null;

                       boolean []pode = {pode_norte, pode_nordeste, pode_leste, pode_sudeste, pode_sul, pode_sudoeste, pode_oeste, pode_noroeste};

                        if(reiBranco.isInCheck()){
                            norte =       new Point((int)reiBranco.getQuadrante().getX()  , (int)reiBranco.getQuadrante().getY()-1);
                            nordeste =    new Point((int)reiBranco.getQuadrante().getX()+1, (int)reiBranco.getQuadrante().getY()-1);
                            leste =       new Point((int)reiBranco.getQuadrante().getX()+1, (int)reiBranco.getQuadrante().getY());
                            sudeste =     new Point((int)reiBranco.getQuadrante().getX()+1, (int)reiBranco.getQuadrante().getY()+1);
                            sul =         new Point((int)reiBranco.getQuadrante().getX()  , (int)reiBranco.getQuadrante().getY()+1);
                            sudoeste =    new Point((int)reiBranco.getQuadrante().getX()-1, (int)reiBranco.getQuadrante().getY()+1);
                            oeste =       new Point((int)reiBranco.getQuadrante().getX()-1, (int)reiBranco.getQuadrante().getY());
                            noroeste =    new Point((int)reiBranco.getQuadrante().getX()-1, (int)reiBranco.getQuadrante().getY()-1);

                             //Verifica todos os possíveis lances
                            for(int j=0; j<getModel().getPecasPretas().size(); j++){    //Para todas as peças pretas
                                if(getModel().getPecasPretas().get(j).isPossibleMove(norte)    && pode[0]) pode[0] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(nordeste) && pode[1]) pode[1] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(leste)    && pode[2]) pode[2] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(sudeste)  && pode[3]) pode[3] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(sul)      && pode[4]) pode[4] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(sudoeste) && pode[5]) pode[5] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(oeste)    && pode[6]) pode[6] = false;
                                if(getModel().getPecasPretas().get(j).isPossibleMove(noroeste) && pode[7]) pode[6] = false;
                            }
                        }


                        else if(reiPreto.isInCheck()){
                            norte =       new Point((int)reiPreto.getQuadrante().getX()  , (int)reiPreto.getQuadrante().getY()-1);
                            nordeste =    new Point((int)reiPreto.getQuadrante().getX()+1, (int)reiPreto.getQuadrante().getY()-1);
                            leste =       new Point((int)reiPreto.getQuadrante().getX()+1, (int)reiPreto.getQuadrante().getY());
                            sudeste =     new Point((int)reiPreto.getQuadrante().getX()+1, (int)reiPreto.getQuadrante().getY()+1);
                            sul =         new Point((int)reiPreto.getQuadrante().getX()  , (int)reiPreto.getQuadrante().getY()+1);
                            sudoeste =    new Point((int)reiPreto.getQuadrante().getX()-1, (int)reiPreto.getQuadrante().getY()+1);
                            oeste =       new Point((int)reiPreto.getQuadrante().getX()-1, (int)reiPreto.getQuadrante().getY());
                            noroeste =    new Point((int)reiPreto.getQuadrante().getX()-1, (int)reiPreto.getQuadrante().getY()-1);

                             //Verifica todos os possíveis lances
                            for(int j=0; j<getModel().getPecasBrancas().size(); j++){    //Para todas as peças pretas
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(norte)    && pode[0]) pode[0] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(nordeste) && pode[1]) pode[1] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(leste)    && pode[2]) pode[2] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(sudeste)  && pode[3]) pode[3] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(sul)      && pode[4]) pode[4] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(sudoeste) && pode[5]) pode[5] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(oeste)    && pode[6]) pode[6] = false;
                                if(getModel().getPecasBrancas().get(j).isPossibleMove(noroeste) && pode[7]) pode[6] = false;
                            }
                        }

                        // se a casa está segura pode mover
                        if( (pode[0] && getNewPoint().equals(norte)) || (pode[1] && getNewPoint().equals(nordeste)) ||
                            (pode[2] && getNewPoint().equals(leste)) || (pode[3] && getNewPoint().equals(sudeste)) ||
                            (pode[4] && getNewPoint().equals(sul)) || (pode[5] && getNewPoint().equals(sudoeste)) ||
                            (pode[6] && getNewPoint().equals(oeste)) || (pode[7] && getNewPoint().equals(noroeste)) ){
                            getPecaEscolhida().move(getNewPoint());
                            setPecaEscolhida(null);
                            if(reiBranco.isInCheck()) reiBranco.setInCheck(false);
                            if(reiPreto.isInCheck()) reiPreto.setInCheck(false);
                            getView().repaint();
                        }
                     //Caso 3: se está em check, pode mover uma peça para bloquear o check
                     //Condição: para todas as peças adversárias, se nenhuma atacar o rei após este movimento, pode mover
//                    }else if(reiPreto.isInCheck() && (!getPecaEscolhida().equals(reiBranco) || !getPecaEscolhida().equals(reiPreto))){
//                        System.out.println("Caso 3: mover uma peça para bloquear o check");
//                        Peca pecaChecando = null;
//                        for(int i=0; i<getModel().getPecasBrancas().size(); i++){
//                            if(getModel().getPecasBrancas().get(i).isCheckinPiece()){
//                                pecaChecando = getModel().getPecasBrancas().get(i);
//                                System.out.println("peça checkando é : " + pecaChecando);
//                                ArrayList<ModelTabuleiro> caso3 = new ArrayList<ModelTabuleiro>();
//                                caso3.add(getModel());
//                                AutoSave.save(caso3, "caso3");
//                            }
//                        }
//                        getPecaEscolhida().move(getNewPoint());
//                        pecaChecando.move(pecaChecando.getQuadrante());
//                            if(pecaChecando.isCheckinPiece()){
//                                 ArrayList<ModelTabuleiro> lista = AutoSave.load("caso3");
//
//                                 ModelTabuleiro novoModel = lista.get(lista.size()-1);
//
//                                 getModel().reset();
//
//                                 addModel(novoModel);
//                                 getView().getCanvas().getObservers().remove(getView().getCanvas().getObservers().size()-1); // remove o ultimo model
//                                 getView().getCanvas().registerObserver(novoModel);
//                                 addModel(novoModel);
//                        }


                    }
                     // Se não é nenhum dos casos anteriores, alguém perdeu
                     JDVitoria jdvitoria = new JDVitoria(getView(), true);
                 }

                //Controle do movimento caso comum                
                else{
                    
                    // se existe peça tomada, remove
                    if(pecaTomada != null){
                        getModel().remove(pecaTomada);
                    }
                    System.out.println("caso normal");
                    getPecaEscolhida().move(getNewPoint());
                    setPecaEscolhida(null);
                    getView().repaint();

                    // verifica se algum peão coroou
                    setPecaCoroada(model.VerificaSeCoroou());
                    if(getPecaCoroada() != null){
                        setPosicao_coroar(getPecaCoroada().getQuadrante());

                        JDCoroar jdcoroar = new JDCoroar(getView(), true);

                        if(getPecaCoroada().getCor() == Peca.Cor.BRANCO){ 
                            jdcoroar.getJb_knight().setIcon(new ImageIcon("src/Img/wKnight.png"));
                            jdcoroar.getJb_bishop().setIcon(new ImageIcon("src/Img/wBishop.png"));
                            jdcoroar.getJb_rook().setIcon(new ImageIcon("src/Img/wRook.png"));
                            jdcoroar.getJb_queen().setIcon(new ImageIcon("src/Img/wQueen.png"));
                        }else if(getPecaCoroada().getCor() == Peca.Cor.PRETO){ // troca os botões se for preto
                            jdcoroar.getJb_knight().setIcon(new ImageIcon("src/Img/bKnight.png"));
                            jdcoroar.getJb_bishop().setIcon(new ImageIcon("src/Img/bBishop.png"));
                            jdcoroar.getJb_rook().setIcon(new ImageIcon("src/Img/bRook.png"));
                            jdcoroar.getJb_queen().setIcon(new ImageIcon("src/Img/bQueen.png"));
                        }

                        jdcoroar.setVisible(true);

                        getModel().remove(getPecaCoroada()); // remove o peão coroado para depois adicionar a peça escolhida pelo usuário no lugar
                        setPosicao_coroar(null);
                    }
                }    
                 
            } 
        }
        
        
        System.out.println("#");
    }
    

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();//pega as coordenadas do mouse
        int y = e.getY();
//        view.getCoordenadaLabel().setText("["+x/60+","+y/60+"]");
        view.getMouseCoord().setLocation(x, y);
        view.repaint();
    }

}

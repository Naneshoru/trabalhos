/*
 * 
 * 
 */
package Xadrez;

import Controler.TabuleiroController;
import Model.AutoSave;
import Model.ModelTabuleiro;
import View.Tabuleiro;

/**
 *
 * @author Ricardo Atakiama
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        //MVC  
        ModelTabuleiro model = new ModelTabuleiro();  
          
        Tabuleiro viewTabuleiro = new Tabuleiro(model);  
        
        TabuleiroController tabuleiroController = new TabuleiroController(); 
        //MVC
        
        //o view vê o controller, além do model
        viewTabuleiro.setController(tabuleiroController);
        // define o tratamento de eventos dos atributos do view para o controller
        viewTabuleiro.addController(tabuleiroController);
        
        
        //o controller vê o model e o view
        tabuleiroController.addModel(model);
        tabuleiroController.addView(viewTabuleiro);
        
        //ajusta posição do tabuleiro
        tabuleiroController.runTabuleiro();
        
        
        tabuleiroController.getUltimoLance().add(model);
        AutoSave.save(tabuleiroController.getUltimoLance(), "lastMove");
      }
    });
  }
}

/*
 * Esta classe possui os métodos estaticos para salvar e carregar o tabuleiro (será usado o model no caso) 
 * 
 * 
 */

package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author Ricardo Atakiama
 */
public class AutoSave {
    
    public static void save(ArrayList lista, String nomeArq){
        FileOutputStream out = null;
        ObjectOutputStream objOut = null;
        try {
            out = new FileOutputStream(nomeArq);
            objOut = new ObjectOutputStream(out);
            
            objOut.writeObject(lista);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar jogo! \n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
        } finally{
            if (objOut != null) {
                try {
                    objOut.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Não foi possível fechar o gravador! \n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Não foi possível fechar o arquivo! \n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
                }
            }
            
        }
    }
    
    public static <T> ArrayList<T> load(String nomeArq){
        ArrayList<T> lista = null;
        
        try {
            FileInputStream in = new FileInputStream(nomeArq);
            ObjectInputStream objIn = new ObjectInputStream(in);
            
            lista = (ArrayList<T>) objIn.readObject();
            
        } catch (FileNotFoundException e ) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o jogo! Arquivo não encontrado.\n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
        } catch (ClassNotFoundException e ) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o jogo! Classe não encontrada\n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o jogo! \n " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);         
        }
        
        return lista;
    }
    
    
    
}

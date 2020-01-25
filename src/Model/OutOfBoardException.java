/*
 * 
 * 
 * 
 */

package Model;

/**
 *
 * @author Ricardo Atakiama
 */
public class OutOfBoardException extends Exception{

    public OutOfBoardException(String msg) {
        super(msg);
    }
    
    public OutOfBoardException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}

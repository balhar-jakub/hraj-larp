package cz.hrajlarp.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 21.4.13
 * Time: 22:42
 */
public class DuplicatePlayerException extends Exception {
    public DuplicatePlayerException(){
        super();
    }

    public DuplicatePlayerException(String info){
        super(info);
    }
}

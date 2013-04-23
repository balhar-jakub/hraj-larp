package cz.hrajlarp.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 21.4.13
 * Time: 18:44
 */
public class TooManyPlayersException extends Exception {
    public TooManyPlayersException() {
        super();
    }

    public TooManyPlayersException(String info){
        super(info);
    }
}

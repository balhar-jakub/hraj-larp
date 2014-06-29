package cz.hrajlarp.exceptions;

/**
 *
 */
public class TooManyPlayersException extends Exception {
    public TooManyPlayersException(String info){
        super(info);
    }
}

package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jakub Balhar on 9.9.2014.
 */
@Service
public class SubstitutionService {
    @Autowired
    private UserAttendedGameDAO userAttendance;

    /**
     * In all emails there are certain types of substitute variables which can be used by editors.
     * {jmenoUzivatele}
     * {jmenoHry}
     * {datumHry}
     * {casHry}
     * {adresaHry}
     * {variabilniSymbol}
     *
     * @param text Text to be modified if present
     * @param game Actual Game which should be used for modification
     * @param user User which shall be used for modifications
     * @return Modified string.
     */
    public String replaceSubstitutes(String text, GameEntity game, HrajUserEntity user){
        String USER_NAME = "{jmenoUzivatele}";
        String GAME_NAME = "{jmenoHry}";
        String GAME_DATE = "{datumHry}";
        String GAME_TIME = "{casHry}";
        String GAME_ADDRESS = "{adresaHry}";
        String VARIABLE_SYMBOL = "{variabilniSymbol}";

        String USER_NAME_R = "\\{jmenoUzivatele\\}";
        String GAME_NAME_R = "\\{jmenoHry\\}";
        String GAME_DATE_R = "\\{datumHry\\}";
        String GAME_TIME_R = "\\{casHry\\}";
        String GAME_ADDRESS_R = "\\{adresaHry\\}";
        String VARIABLE_SYMBOL_R = "\\{variabilniSymbol\\}";

        if(text.contains(USER_NAME)){
            text = text.replaceAll(USER_NAME_R, user.getName() + " " + user.getLastName());
        }
        if(text.contains(GAME_NAME)) {
            text = text.replaceAll(GAME_NAME_R, game.getName());
        }
        if(text.contains(GAME_DATE)){
            text = text.replaceAll(GAME_DATE_R, game.getDateAsDMY());
        }
        if(text.contains(GAME_TIME)){
            text = text.replaceAll(GAME_TIME_R, game.getDateTime());
        }
        UserAttendedGameEntity userAttendanceEntity = userAttendance.getLogged(game.getId(), user.getId());
        if(text.contains(VARIABLE_SYMBOL)) {
            text = text.replaceAll(VARIABLE_SYMBOL_R, userAttendanceEntity.getVariableSymbol());
        } else {
            text += "\n Variabilni symbol je: " + userAttendanceEntity.getVariableSymbol();
        }
        if(text.contains(GAME_ADDRESS)) {
            text = text.replaceAll(GAME_ADDRESS_R, game.getPlace());
        }

        return text;
    }
}
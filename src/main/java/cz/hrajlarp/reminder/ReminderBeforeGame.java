package cz.hrajlarp.reminder;

import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * It reminds user that the game is happening next day.
 */
@Component
public class ReminderBeforeGame {
    private final int MINUTE = 60000;

    @Autowired private UserAttendedGameDAO playersOfUpcomingGame;
    @Autowired private MailService mailService;

    @Scheduled(fixedRate = MINUTE)
    public void remind(){
        // It must happen day before the game is happening.
        Collection<UserAttendedGameEntity> tommorowGames = playersOfUpcomingGame.tomorrow();
        for(UserAttendedGameEntity game: tommorowGames) {
            //mailService.sendMsgBeforeGame();
        }
    }
}

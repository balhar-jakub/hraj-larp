package cz.hrajlarp.model;

import cz.hrajlarp.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 24.4.13
 * Time: 17:41
 */
@Service
public class Notification {
    @Autowired
    MailService mailService;
    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;

    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendBeforeGameMail() {
        List<UserAttendedGameEntity> allPlayers = userAttendedGameDAO.getAllFuture();
        for(UserAttendedGameEntity player: allPlayers) {
            HrajUserEntity user = player.getUserAttended();
            if(player.getPayed() == null || !player.getPayed()) {
                mailService.sendMsgBeforeGame(user, player.getAttendedGame());
            }
        }
    }
}

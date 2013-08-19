package cz.hrajlarp.model;

import cz.hrajlarp.model.dao.GameDAO;
import cz.hrajlarp.model.dao.PreRegNotificationDAO;
import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.PreRegNotificationEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import cz.hrajlarp.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    PreRegNotificationDAO preRegNotificationDAO;
    @Autowired
    GameDAO gameDAO;
    @Autowired
    UserDAO userDAO;

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
    
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendRegStartNotification() {
        List<PreRegNotificationEntity> allPreRegs = preRegNotificationDAO.getAllPreRegNotifications();
        Date now = Calendar.getInstance().getTime();
        for(PreRegNotificationEntity preReg: allPreRegs) {
        	if(preReg.getNotifyDate() != null && preReg.getNotifyDate().before(now)) {
                mailService.sendMsgDayToRegStart(userDAO.getUserById(preReg.getUserId()), 
                		gameDAO.getGameById(preReg.getGameId()));
                preRegNotificationDAO.deletePreRegNotification(preReg);
            }
        }
    }

    // Send if there is no game scheduled in month
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendNoGameNotification(){
        // There is no game in date after today plus one month.
        gameDAO.getFutureGames();
    }

    // Send if there is no place at game two weeks before tha game
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendNoPlaceNotification(){

    }

    // Send if there is not accepted at the game detail in administration.
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendUnfinishedAccountNotification(){

    }
}

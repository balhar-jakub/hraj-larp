package cz.hrajlarp.reminder;

import cz.hrajlarp.model.dao.GameDAO;
import cz.hrajlarp.model.dao.PreRegNotificationDAO;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.entity.PreRegNotificationEntity;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * It should remind people day before the game sign in is opening.
 */
@Component
public class ReminderBeforeSignIn {
    private final int MINUTE = 60000;

    @Autowired
    private PreRegNotificationDAO preRegNotificationDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GameDAO gameDAO;

    @Scheduled(fixedRate = MINUTE)
    public void remind(){
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
}

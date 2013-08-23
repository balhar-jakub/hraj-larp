package cz.hrajlarp.model;

import cz.hrajlarp.model.dao.*;
import cz.hrajlarp.model.entity.*;
import cz.hrajlarp.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.*;

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
    @Autowired
    SchedulerDAO schedulerDAO;
    @Autowired
    PlaceFinderDAO placeFinderDAO;

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
        List<GameEntity> games = gameDAO.GetGamesMonthInFuture();
        List<SchedulerEntity> schedulers = schedulerDAO.getAll();
        if(games.size() == 0){
            for(SchedulerEntity scheduler: schedulers){
                mailService.sendInfoAboutNoGame(
                        userDAO.getUserById(scheduler.getId()).getEmail());
            }
        }
    }

    // Send if there is no place at game two weeks before tha game
    // Every game that is due in less than two weeks must have place assigned
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendNoPlaceNotification(){
        List<GameEntity> gamesWithoutPlace = gameDAO.GetGamesTwoWeeksInFuture();
        List<PlaceFinderEntity> placeFinders = placeFinderDAO.getAll();
        for(GameEntity game: gamesWithoutPlace){
            if(game.getPlace() != null && !game.getPlace().equals("")){
                continue;
            }
            for(PlaceFinderEntity placeFinder : placeFinders) {
                mailService.sendInfoAboutNoPlace(
                        userDAO.getUserById(placeFinder.getId()).getEmail(), game);
            }
        }
    }

    // Send if there is not accepted at the game detail in administration.
    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void sendUnfinishedAccountNotification(){
        List<GameEntity> gamesWithUnfinishedAccount = gameDAO.getTwoWeeksPast();
        for(GameEntity game: gamesWithUnfinishedAccount){
            if(game.getPaymentFinished() != null && game.getPaymentFinished()) {
                continue;
            }
            Map<Object, UserIsEditorEntity> gameEditors = game.getEditedByUsers();
            Collection<UserIsEditorEntity> editors = gameEditors.values();
            for(UserIsEditorEntity user: editors ){
                HrajUserEntity hrajUser = userDAO.getUserById(user.getUserId());
                mailService.sendInfoAboutUnfinishedAccount(hrajUser.getEmail(), game);
            }
        }

    }
}

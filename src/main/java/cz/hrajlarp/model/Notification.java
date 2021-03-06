package cz.hrajlarp.model;

import cz.hrajlarp.model.dao.*;
import cz.hrajlarp.model.entity.*;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.text.SimpleDateFormat;
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

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Transient
    public void sendRegStartNotification() {
        System.out.println("HRAJLARP - sendRegStartNotification. " + dateFormat.format(new Date()));
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
    @Transient
    public void sendNoGameNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification. "  + dateFormat.format(new Date()));
        // There is no game in date after today plus one month.
        List<GameEntity> games = gameDAO.GetGamesMonthInFuture();
        List<SchedulerEntity> schedulers = schedulerDAO.getAll();

        boolean[] weeks = new boolean[4];
        Calendar[] starts = new Calendar[4];
        Calendar[] ends = new Calendar[4];
        starts[0] = getStartOfWeek(0,Calendar.MONDAY);
        starts[1] = getStartOfWeek(1,Calendar.MONDAY);
        starts[2] = getStartOfWeek(2,Calendar.MONDAY);
        starts[3] = getStartOfWeek(3,Calendar.MONDAY);
        ends[0] = getStartOfWeek(0,Calendar.FRIDAY);
        ends[1] = getStartOfWeek(1,Calendar.FRIDAY);
        ends[2] = getStartOfWeek(2,Calendar.FRIDAY);
        ends[3] = getStartOfWeek(3,Calendar.FRIDAY);
        for(GameEntity game: games){
            for(int i= 0 ; i < 4; i++){
                if(game.getDate().after(starts[i].getTime()) &&
                        game.getDate().before(ends[i].getTime())){
                     weeks[i] = true;
                }
            }
        }
        String mailText = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        for(int i= 0 ; i < 4; i++){
            if(!weeks[i]){
                mailText += "V týdnu od " + sdf.format(starts[i].getTime()) + "do " +
                        sdf.format(ends[i].getTime()) + " není zadaná žádná hra.";
            }
        }
        if(!mailText.equals("")){
            for(SchedulerEntity scheduler: schedulers){
                HrajUserEntity user = userDAO.getUserById(scheduler.getId());
                mailService.sendInfoAboutNoGame(user.getEmail(), mailText);
            }
        }
    }

    private Calendar getStartOfWeek(int week, int field){
        Calendar firstWeekStart = Calendar.getInstance();
        firstWeekStart.set(Calendar.DAY_OF_WEEK, field);
        firstWeekStart.add(Calendar.WEEK_OF_MONTH, week);
        return firstWeekStart;
    }

    // Send if there is no place at game two weeks before tha game
    // Every game that is due in less than two weeks must have place assigned
    @Transient
    public void sendNoPlaceNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification. " + dateFormat.format(new Date()));
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
    @Transient
    public void sendUnfinishedAccountNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification. " + dateFormat.format(new Date()));
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
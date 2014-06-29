package cz.hrajlarp.service;

import cz.hrajlarp.dao.*;
import cz.hrajlarp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
public class NotificationService {
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

    @Scheduled(fixedRate=24*60*60*1000)
    @Transient
    public void sendBeforeGameMail() {
        System.out.println("HRAJLARP - sendBeforeGameMail.");
        List<UserAttendedGame> allPlayers = userAttendedGameDAO.getAllFuture();
        for(UserAttendedGame player: allPlayers) {
            HrajUser user = player.getUserAttended();
            if(!player.isSubstitute()){
                if(!player.getPayed()) {
                    mailService.sendMsgBeforeGame(user, player.getAttendedGame());
                }
            }
        }
    }
    
    @Scheduled(fixedRate=24*60*60*1000)
    @Transient
    public void sendRegStartNotification() {
        System.out.println("HRAJLARP - sendRegStartNotification.");
        List<PreRegNotification> allPreRegs = preRegNotificationDAO.findAll();
        Date now = Calendar.getInstance().getTime();
        for(PreRegNotification preReg: allPreRegs) {
        	if(preReg.getNotifyDate() != null && preReg.getNotifyDate().before(now)) {
                mailService.sendMsgDayToRegStart(userDAO.findById(preReg.getUserId()),
                		gameDAO.findById(preReg.getGameId()));
                preRegNotificationDAO.makeTransient(preReg);
            }
        }
    }

    // Send if there is no game scheduled in month
    @Scheduled(fixedRate=24*60*60*1000)
    @Transient
    public void sendNoGameNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification.");
        // There is no game in date after today plus one month.
        List<Game> games = gameDAO.GetGamesMonthInFuture();
        List<HrajUser> schedulers = userDAO.getSchedulers();

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
        for(Game game: games){
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
            for(HrajUser scheduler: schedulers){
                mailService.sendInfoAboutNoGame(scheduler.getEmail(), mailText);
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
    @Scheduled(fixedRate=24*60*60*1000)
    @Transient
    public void sendNoPlaceNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification.");
        List<Game> gamesWithoutPlace = gameDAO.GetGamesTwoWeeksInFuture();
        List<HrajUser> placeFinders = userDAO.getPlaceFinders();
        for(Game game: gamesWithoutPlace){
            if(game.getPlace() != null && !game.getPlace().equals("")){
                continue;
            }
            for(HrajUser placeFinder : placeFinders) {
                mailService.sendInfoAboutNoPlace(
                        placeFinder.getEmail(), game);
            }
        }
    }

    // Send if there is not accepted at the game detail in administration.
    @Scheduled(fixedRate=24*60*60*1000)
    @Transient
    public void sendUnfinishedAccountNotification(){
        System.out.println("HRAJLARP - sendNoGameNotification.");
        List<Game> gamesWithUnfinishedAccount = gameDAO.getTwoWeeksPast();
        for(Game game: gamesWithUnfinishedAccount){
            if(game.getPaymentFinished()) {
                continue;
            }
            Map<Object, UserIsEditor> gameEditors = game.getEditedByUsers();
            Collection<UserIsEditor> editors = gameEditors.values();
            for(UserIsEditor user: editors ){
                HrajUser hrajUser = userDAO.findById(user.getUserId());
                mailService.sendInfoAboutUnfinishedAccount(hrajUser.getEmail(), game);
            }
        }

    }
}

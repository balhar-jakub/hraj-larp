package cz.hrajlarp.controller;

import cz.hrajlarp.model.Rights;
import cz.hrajlarp.model.dao.GameDAO;
import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jbalhar on 21. 10. 2015.
 */
@Controller
public class WeekendController {
    @Autowired
    private GameDAO games;
    @Autowired
    private UserAttendedGameDAO players;
    @Autowired
    private UserDAO users;
    @Autowired
    private Rights rights;
    @Autowired
    private MailService mailService;

    private String action = "HRAJ LARP víkend";

    @RequestMapping(value = "/vikend", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String festivalInformation() {
        return "weekend/info";
    }


    @RequestMapping(value = "/vikend/hry", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfFestivalGames(
            Model model
    ) {
        List<GameEntity> weekendGames = games.getGamesWithAction(action);

        List<GameEntity> fridayGames = new ArrayList<>();
        List<GameEntity> saturdayMorningGames = new ArrayList<>();
        List<GameEntity> saturdayAfternoonGames = new ArrayList<>();
        List<GameEntity> sundayGames = new ArrayList<>();
        List<GameEntity> sleepOver = new ArrayList<>();

        for (GameEntity game : weekendGames) {
            try {
                game.countPlayers(players);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (game.getName().equals("Přespání Pátek/Sobota")) {
                sleepOver.add(game);
                continue;
            } else if (game.getName().equals("Přespání Sobota/Neděle")) {
                sleepOver.add(game);
                continue;
            }

            if (game.getDateAsDM().equals("04.12")) {
                fridayGames.add(game);
            } else if (game.getDateAsDM().equals("06.12")) {
                sundayGames.add(game);
            } else if (game.getDateAsDM().equals("05.12")) {
                if (game.getTimeAsHM().equals("09:00")) {
                    saturdayMorningGames.add(game);
                } else {
                    saturdayAfternoonGames.add(game);
                }
            }
        }

        model.addAttribute("games1", fridayGames);
        model.addAttribute("games2", saturdayMorningGames);
        model.addAttribute("games3", saturdayAfternoonGames);
        model.addAttribute("games4", sundayGames);
        model.addAttribute("sleepOver", sleepOver);

        return "weekend/games";
    }

    @RequestMapping(value = "/vikend/hraci", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfPlayers(
            Model model
    ) {
        List<HrajUserEntity> attendingUsers = users.getEveryoneAttendingAction(action);
        List<AttendersDto> participants = new ArrayList<>();

        for(HrajUserEntity participant: attendingUsers) {
            List<GameEntity> attendedGames = games.attendedOnActionByPlayer(participant.getId(), action);
            List<PaymentDto> payments = new ArrayList<>();
            for(GameEntity game: attendedGames) {
                UserAttendedGameEntity uag = players.getLogged(game.getId(), participant.getId());
                payments.add(new PaymentDto(game, uag.getAutomatic(), uag.getPayed(), uag.isSubstitute(), uag.getVariableSymbol()));
            }
            participants.add(new AttendersDto(participant, payments));
        }

        model.addAttribute("participants", participants);
        return "weekend/players";
    }

    @RequestMapping(value = "/vikend/odhlaseni", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String logoutPlayersWithoutPayment() {
        HrajUserEntity loggedUser = rights.getLoggedUser();
        if(loggedUser == null || loggedUser.getId() != 14) {
            return "error";
        }

        List<UserAttendedGameDAO.GamePlayer> toLogout = players.actionWithoutPayment(action);
        toLogout.stream()
                .forEach(
                        gamePlayer -> gamePlayer.getGame().logoutAndMailNewRegularPlayer(mailService, players, gamePlayer.getUser()));

        return "/weekend/players";
    }

    public class AttendersDto {
        private HrajUserEntity player;
        private List<PaymentDto> attendedGames;

        public AttendersDto(HrajUserEntity player, List<PaymentDto> attendedGames) {
            this.player = player;
            this.attendedGames = attendedGames;
        }

        public HrajUserEntity getPlayer() {
            return player;
        }

        public List<PaymentDto> getAttendedGames() {
            return attendedGames;
        }
    }

    public class PaymentDto {
        private GameEntity game;
        private boolean automatic = false;
        private boolean payed = false;
        private boolean replacement = false;
        private String dateToShow;
        private String vs;

        public PaymentDto(GameEntity game, boolean automatic, Boolean payed, boolean replacement, String vs) {
            this.game = game;
            this.automatic = automatic;
            this.payed = payed != null ? payed: false;
            this.replacement = replacement;
            this.dateToShow = game.getDateAsDM() + " " + game.getTimeAsHM();
            this.vs = vs;
        }

        public GameEntity getGame() {
            return game;
        }

        public boolean isAutomatic() {
            return automatic;
        }

        public boolean isPayed() {
            return payed;
        }

        public boolean isReplacement() {
            return replacement;
        }

        public String getDateToShow() {
            return dateToShow;
        }

        public String getVs() {
            return vs;
        }
    }
}

package cz.hrajlarp.service;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dto.PlayerDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class PlayerService {
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    private GameService gameService;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private DateService dateService;

    public List<PlayerDto> getRegularPlayersOfGame(int gameId) {
        List<UserAttendedGame> players = userAttendedGameDAO.getActualPlayersOnly(gameId);
        List<PlayerDto> playerDtos = new ArrayList<>();
        for(UserAttendedGame player: players) {
            playerDtos.add(transformToDto(player));
        }
        return playerDtos;
    }

    public List<PlayerDto> getSubstitutesForGame(int gameId) {
        List<UserAttendedGame> substitutes = userAttendedGameDAO.getSubstitutesByGameId(gameId);
        List<PlayerDto> playerDtos = new ArrayList<>();
        for(UserAttendedGame player: substitutes) {
            playerDtos.add(transformToDto(player));
        }
        return playerDtos;
    }

    public List<PlayerDto> getAllSigned(Integer gameId) {
        List<PlayerDto> players = new ArrayList<>();
        players.addAll(getRegularPlayersOfGame(gameId));
        players.addAll(getSubstitutesForGame(gameId));
        return players;
    }

    private PlayerDto transformToDto(UserAttendedGame userAttendedGame) {
        PlayerDto player = new PlayerDto();
        HrajUser user = userAttendedGame.getUserAttended();

        player.setEmail(user.getEmail());
        player.setFullName(user.getName() + " " + user.getLastName());
        if(user.getGender() == 0){
            player.setGender("Muž");
        } else {
            player.setGender("Žena");
        }
        player.setId(user.getId());
        player.setPhone(user.getPhone());
        player.setUserName(user.getUserName());

        player.setPayed(userAttendedGame.getPayed());
        player.setAutomatic(userAttendedGame.getAutomatic());
        player.setPayedText(userAttendedGame.getPayed() ? "Ano" : "");

        return player;
    }

    public String logOutFromGame(int gameId) {
        if (rightsService.isLogged()) {
            int userId = rightsService.getLoggedUser().getId();
            UserAttendedGame userAttendedGame = userAttendedGameDAO.getSpecificOne(userId, gameId);

            if(userAttendedGame != null) {
                gameService.signOutFromGame(userAttendedGame);
            }
            return "redirect:/game/detail";
        } else {
            return "/error";
        }
    }

    public String logIntoGame(int gameId) {
        if (rightsService.isLogged()) {
            HrajUser user = rightsService.getLoggedUser();

            if (gameId > 0) {
                if (!user.getActivated()) {
                    return "/error";
                }
                Game game = gameService.getGameById(gameId);
                if (game != null && dateService.isDateInFuture(game.getDate())) {
                    try {
                        gameService.signUserAsPlayer(game, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "/error";
                    }
                }
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }
}

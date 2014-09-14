package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.GameDAO;
import cz.hrajlarp.model.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andilek on 14.9.2014.
 */
@Service
public class GameService {
    @Autowired
    private GameDAO gameDAO;

    public List<GameEntity> getFutureGames(int limit){
        List<GameEntity> futureGames = gameDAO.getFutureGames();
        if(futureGames.size() > limit) {
            return futureGames.subList(0, limit);
        } else {
            return futureGames;
        }
    }
}

package cz.hrajlarp.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import junit.framework.TestCase;
import org.junit.Test;

@DatabaseSetup("games.xml")
public class GameDAOTest extends AbstractDaoTest {
    @Test
    public void testGamesInFuture(){

    }
}
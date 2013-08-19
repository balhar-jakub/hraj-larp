package cz.hrajlarp.model.entity;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 2.4.13
 * Time: 16:15
 */
public class GameEntityTest {

    GameEntity game_0_0_0, game_2_0_0, game_1_0_1, game_1_1_1;
    HrajUserEntity male1, male2, male3;
    HrajUserEntity female1, female2, female3;

    List<HrajUserEntity> assignedUsers, substitutes;

    public GameEntityTest() {

        male1 = new HrajUserEntity();
        male1.setGender(0);
        male1.setId(1);

        male2 = new HrajUserEntity();
        male2.setGender(0);
        male2.setId(2);

        male3 = new HrajUserEntity();
        male3.setGender(0);
        male3.setId(3);


        female1 = new HrajUserEntity();
        female1.setGender(1);
        female1.setId(4);

        female2 = new HrajUserEntity();
        female2.setGender(1);
        female2.setId(5);

        female3 = new HrajUserEntity();
        female3.setGender(1);
        female3.setId(6);
    }

    @Before
    public void setUp() {
        game_0_0_0 = new GameEntity();
        game_0_0_0.setMenRole(0);
        game_0_0_0.setWomenRole(0);
        game_0_0_0.setBothRole(0);

        game_2_0_0 = new GameEntity();
        game_2_0_0.setMenRole(2);
        game_2_0_0.setWomenRole(0);
        game_2_0_0.setBothRole(0);

        game_1_0_1 = new GameEntity();
        game_1_0_1.setMenRole(1);
        game_1_0_1.setWomenRole(0);
        game_1_0_1.setBothRole(1);

        game_1_1_1 = new GameEntity();
        game_1_1_1.setMenRole(1);
        game_1_1_1.setWomenRole(1);
        game_1_1_1.setBothRole(1);

        assignedUsers = new ArrayList<HrajUserEntity>();
        substitutes = new ArrayList<HrajUserEntity>();
    }

    /* tests for getMenFreeRoles() method */

    @Test
    public void testGetMenFreeRoles1() {
        assertEquals(0, game_0_0_0.getMenFreeRoles());
        assertEquals(1, game_1_0_1.getMenFreeRoles());
        assertEquals(1, game_1_1_1.getMenFreeRoles());
    }

    @Test
    public void testGetMenFreeRoles2() {
        assignedUsers.add(male1);

        try {
            game_2_0_0.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_2_0_0.getMenFreeRoles());
    }

    @Test
    public void testGetMenFreeRoles3() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_2_0_0.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_2_0_0.getMenFreeRoles());
    }

    @Test
    public void testGetMenFreeRoles4() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getMenFreeRoles());
    }

    @Test
    public void testGetMenFreeRoles5() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(male3);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getMenFreeRoles());
    }

    /* tests for getWomenFreeRoles() method */

    @Test
    public void testGetWomenFreeRoles1() {
        assertEquals(0, game_0_0_0.getWomenFreeRoles());
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
        assertEquals(1, game_1_1_1.getWomenFreeRoles());
    }

    @Test
    public void testGetWomenFreeRoles2() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_1_1_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_1_1.getWomenFreeRoles());
    }

    @Test
    public void testGetWomenFreeRoles3() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_1_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_1_1.getWomenFreeRoles());
    }

    @Test
    public void testGetWomenFreeRoles4() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
    }

    @Test
    public void testGetWomenFreeRoles5() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
    }

    /* tests for getBothFreeRoles() method */

    @Test
    public void testGetBothFreeRoles1() {
        assertEquals(0, game_0_0_0.getBothFreeRoles());
        assertEquals(1, game_1_1_1.getBothFreeRoles());
        assertEquals(1, game_1_0_1.getBothFreeRoles());
    }

    @Test
    public void testGetBothFreeRoles2() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_1_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_1_1.getBothFreeRoles());
    }

    @Test
    public void testGetBothFreeRoles3() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getBothFreeRoles());
    }

    @Test
    public void testGetBothFreeRoles4() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getBothFreeRoles());
    }

    @Test
    public void testGetBothFreeRoles5() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
    }

    /* tests for getMenSubstitutes() method */

    @Test
    public void testGetMenSubstitutes1() {
        assertEquals(0, game_1_1_1.getMenSubstitutes());
    }

    @Test
    public void testGetMenSubstitutes2() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getMenSubstitutes());
    }

    @Test
    public void testGetMenSubstitutes3() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getMenSubstitutes());
    }

    @Test
    public void testGetMenSubstitutes4() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(male3);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_0_1.getMenSubstitutes());
    }

    /* tests for getWomenSubstitutes() method */

    @Test
    public void testGetWomenSubstitutes1() {
        assertEquals(0, game_1_1_1.getWomenSubstitutes());
    }

    @Test
    public void testGetWomenSubstitutes2() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getWomenSubstitutes());
    }

    @Test
    public void testGetWomenSubstitutes3() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);
        substitutes.add(male2);
        substitutes.add(female2);

        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_0_1.getWomenSubstitutes());
    }

    /* tests for isFull() method */

    @Test
    public void testSetAssignedUsers6(){
        assignedUsers.add(male1);
        assignedUsers.add(male1);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void testSetAssignedUsers7(){
        assignedUsers.add(male1);
        substitutes.add(male1);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void testSetAssignedUsers8(){
        assignedUsers.add(male1);
        assignedUsers.add(female1);
        substitutes.add(male2);
        substitutes.add(male2);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void testSetAssignedUsers9(){
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        assignedUsers.add(male3);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void testSetAssignedUsers10(){
        assignedUsers.add(male1);
        substitutes.add(male2);
        substitutes.add(male3);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(assignedUsers, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void testSetAssignedUsers11(){
        substitutes.add(male3);

        Exception ex = null;
        try {
            game_1_0_1.countPlayers(null, substitutes);
        } catch (Exception e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    /* tests for methods returning date in specified format */

    @Test
    public void testGetDateAsDMY(){
        game_0_0_0.setDate(Timestamp.valueOf("2000-01-02 12:13:14.15"));
        assertEquals("02.01.2000", game_0_0_0.getDateAsDMY());
    }

    @Test
    public void testGetDateAsDM(){
        game_0_0_0.setDate(Timestamp.valueOf("2000-01-02 12:13:14.15"));
        assertEquals("02.01", game_0_0_0.getDateAsDM());
    }

    @Test
    public void testGetDateTime(){
        game_0_0_0.setDate(Timestamp.valueOf("2000-01-02 12:13:14.15"));
        assertEquals("12:13", game_0_0_0.getDateTime());
    }
}

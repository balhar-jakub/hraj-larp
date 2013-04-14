package cz.hrajlarp.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

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

    /* tests for isFullForAnyOne() method */

    @Test
    public void testIsFullForAnyone1() {
        assertEquals(true, game_0_0_0.isFullForAnyone());
        assertEquals(false, game_2_0_0.isFullForAnyone());
    }

    @Test
    public void testIsFullForAnyone2() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(true, game_2_0_0.isFullForAnyone());
    }

    @Test
    public void testIsFullForAnyone3() {
        assignedUsers.add(male1);

        try {
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(false, game_2_0_0.isFullForAnyone());
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
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
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
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
    }

    /* tests for getMenAssignedRoles() method */

    @Test
    public void testMenAssignedRoles1() {
        assertEquals(0, game_1_1_1.getMenAssignedRoles());
    }

    @Test
    public void testMenAssignedRoles2() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_0_1.getMenAssignedRoles());
    }

    @Test
    public void testMenAssignedRoles3() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(2, game_1_0_1.getMenAssignedRoles());
    }

    @Test
    public void testMenAssignedRoles4() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(male3);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(2, game_1_0_1.getMenAssignedRoles());
    }

    /* tests for getWomenAssignedRoles() method */

    @Test
    public void testWomenAssignedRoles1() {
        assertEquals(0, game_1_1_1.getWomenAssignedRoles());
    }

    @Test
    public void testWomenAssignedRoles2() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_0_1.getWomenAssignedRoles());
    }

    @Test
    public void testWomenAssignedRoles3() {
        assignedUsers.add(female1);
        assignedUsers.add(female2);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(0, game_1_1_1.getMenAssignedRoles());
    }

    @Test
    public void testWomenAssignedRoles4() {
        assignedUsers.add(female1);
        assignedUsers.add(female2);
        substitutes.add(female3);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(2, game_1_1_1.getWomenAssignedRoles());
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(1, game_1_0_1.getWomenSubstitutes());
    }

    /* tests for isFull() method */

    @Test
    public void testIsFull1() {
        assertEquals(true, game_0_0_0.isFull());
        assertEquals(false, game_1_0_1.isFull());
        assertEquals(false, game_1_1_1.isFull());
    }

    @Test
    public void testIsFull2() {
        game_0_0_0.setTargetUser(female1);
        assertEquals(true, game_0_0_0.isFull());

        game_2_0_0.setTargetUser(female1);
        assertEquals(true, game_2_0_0.isFull());
    }

    @Test
    public void testIsFull3() {
        game_2_0_0.setTargetUser(male1);
        assertEquals(false, game_2_0_0.isFull());

        game_1_0_1.setTargetUser(male1);
        assertEquals(false, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(female1);
        assertEquals(false, game_1_0_1.isFull());
    }

    @Test
    public void testIsFull4() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        /* one unassigned role (gender both) */

        game_1_1_1.setTargetUser(male1);
        assertEquals(false, game_1_1_1.isFull());

        game_1_1_1.setTargetUser(female1);
        assertEquals(false, game_1_1_1.isFull());
    }

    @Test
    public void testIsFull5() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(male3);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        /* one unassigned role (gender female) */

        game_1_1_1.setTargetUser(male1);
        assertEquals(true, game_1_1_1.isFull());

        game_1_1_1.setTargetUser(female1);
        assertEquals(false, game_1_1_1.isFull());
    }

    /* tests for isAvailableToUser() method */

    @Test
    public void testIsAvailableToUser1() {

        /* assert false because method setAssignedUsers(...) was not called yet
        * so it is not possible to tell if user can sign up for this game
        * (list of assigned users was not set, user can be on it) */

        assertEquals(false, game_0_0_0.isAvailableToUser(male1));
        assertEquals(false, game_2_0_0.isAvailableToUser(male1));
        assertEquals(false, game_1_1_1.isAvailableToUser(male1));
        assertEquals(false, game_1_1_1.isAvailableToUser(female1));
    }

    @Test
    public void testIsAvailableToUser2() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        assignedUsers.add(female1);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(false, game_1_1_1.isAvailableToUser(male1)); // already assigned
        assertEquals(false, game_1_1_1.isAvailableToUser(male3)); // no free role
        assertEquals(false, game_1_1_1.isAvailableToUser(female1));
        assertEquals(false, game_1_1_1.isAvailableToUser(female2));
    }

    @Test
    public void testIsAvailableToUser3() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(false, game_1_1_1.isAvailableToUser(male1));
        assertEquals(false, game_1_1_1.isAvailableToUser(female1));
        assertEquals(true, game_1_1_1.isAvailableToUser(male2));
        assertEquals(true, game_1_1_1.isAvailableToUser(male3));
        assertEquals(true, game_1_1_1.isAvailableToUser(female2));
    }

    @Test
    public void testIsAvailableToUser4() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(male3);

        try {
            game_1_1_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(false, game_1_1_1.isAvailableToUser(male1));
        assertEquals(false, game_1_1_1.isAvailableToUser(male3));
        assertEquals(true, game_1_1_1.isAvailableToUser(female1));
    }

    /* tests for setAssignedUsers() method */

    @Test
    public void testSetAssignedUsers1() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);

        try {
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(true, game_2_0_0.isFullForAnyone());

        assertEquals(0, game_2_0_0.getMenFreeRoles());
        assertEquals(0, game_2_0_0.getWomenFreeRoles());
        assertEquals(0, game_2_0_0.getBothFreeRoles());

        assertEquals(2, game_2_0_0.getMenAssignedRoles());
        assertEquals(0, game_2_0_0.getWomenAssignedRoles());

        assertEquals(0, game_2_0_0.getMenSubstitutes());
        assertEquals(0, game_2_0_0.getWomenSubstitutes());

        assertEquals(true, game_2_0_0.isFull());

        game_2_0_0.setTargetUser(male1);
        assertEquals(true, game_2_0_0.isFull());

        game_2_0_0.setTargetUser(female1);
        assertEquals(true, game_2_0_0.isFull());

        assertEquals(false, game_2_0_0.isAvailableToUser(male1));
        assertEquals(false, game_2_0_0.isAvailableToUser(male2));
        assertEquals(false, game_2_0_0.isAvailableToUser(male3));
        assertEquals(false, game_2_0_0.isAvailableToUser(female1));
    }

    @Test
    public void testSetAssignedUsers2() {
        assignedUsers.add(male1);

        try {
            game_2_0_0.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(false, game_2_0_0.isFullForAnyone());

        assertEquals(1, game_2_0_0.getMenFreeRoles());
        assertEquals(0, game_2_0_0.getWomenFreeRoles());
        assertEquals(0, game_2_0_0.getBothFreeRoles());

        assertEquals(1, game_2_0_0.getMenAssignedRoles());
        assertEquals(0, game_2_0_0.getWomenAssignedRoles());

        assertEquals(0, game_2_0_0.getMenSubstitutes());
        assertEquals(0, game_2_0_0.getWomenSubstitutes());

        assertEquals(false, game_2_0_0.isFull()); /* there are free roles (gender not determined) */

        game_2_0_0.setTargetUser(male1);
        assertEquals(false, game_2_0_0.isFull()); /* there are still free man roles */

        game_2_0_0.setTargetUser(female1);
        assertEquals(true, game_2_0_0.isFull()); /* is full for any woman */

        assertEquals(false, game_2_0_0.isAvailableToUser(male1));  /* already assigned */
        assertEquals(true, game_2_0_0.isAvailableToUser(male2));   /* not assigned, free men roles */
        assertEquals(false, game_2_0_0.isAvailableToUser(female1)); /* no woman roles */
    }

    @Test
    public void testSetAssignedUsers3() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(true, game_1_0_1.isFullForAnyone());

        assertEquals(0, game_1_0_1.getMenFreeRoles());
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
        assertEquals(0, game_1_0_1.getBothFreeRoles());

        assertEquals(1, game_1_0_1.getMenAssignedRoles());
        assertEquals(1, game_1_0_1.getWomenAssignedRoles());

        assertEquals(0, game_1_0_1.getMenSubstitutes());
        assertEquals(0, game_1_0_1.getWomenSubstitutes());

        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(male2);
        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(female1);
        assertEquals(true, game_1_0_1.isFull());

        assertEquals(false, game_1_0_1.isAvailableToUser(male1));
        assertEquals(false, game_1_0_1.isAvailableToUser(male2));
        assertEquals(false, game_1_0_1.isAvailableToUser(male3));
        assertEquals(false, game_1_0_1.isAvailableToUser(female1));
    }

    @Test
    public void testSetAssignedUsers4() {
        assignedUsers.add(male1);
        assignedUsers.add(female1);
        substitutes.add(male2);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(true, game_1_0_1.isFullForAnyone());

        assertEquals(0, game_1_0_1.getMenFreeRoles());
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
        assertEquals(0, game_1_0_1.getBothFreeRoles());

        assertEquals(1, game_1_0_1.getMenAssignedRoles());
        assertEquals(1, game_1_0_1.getWomenAssignedRoles());

        assertEquals(1, game_1_0_1.getMenSubstitutes());
        assertEquals(0, game_1_0_1.getWomenSubstitutes());

        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(male3);
        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(female1);
        assertEquals(true, game_1_0_1.isFull());

        assertEquals(false, game_1_0_1.isAvailableToUser(male1));
        assertEquals(false, game_1_0_1.isAvailableToUser(male2));
        assertEquals(false, game_1_0_1.isAvailableToUser(male3));
        assertEquals(false, game_1_0_1.isAvailableToUser(female1));
    }

    @Test
    public void testSetAssignedUsers5() {
        assignedUsers.add(male1);
        assignedUsers.add(male2);
        substitutes.add(female1);

        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(true, game_1_0_1.isFullForAnyone());

        assertEquals(0, game_1_0_1.getMenFreeRoles());
        assertEquals(0, game_1_0_1.getWomenFreeRoles());
        assertEquals(0, game_1_0_1.getBothFreeRoles());

        assertEquals(2, game_1_0_1.getMenAssignedRoles());
        assertEquals(0, game_1_0_1.getWomenAssignedRoles());

        assertEquals(0, game_1_0_1.getMenSubstitutes());
        assertEquals(1, game_1_0_1.getWomenSubstitutes());

        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(male3);
        assertEquals(true, game_1_0_1.isFull());

        game_1_0_1.setTargetUser(female1);
        assertEquals(true, game_1_0_1.isFull());

        assertEquals(false, game_1_0_1.isAvailableToUser(male1));
        assertEquals(false, game_1_0_1.isAvailableToUser(male2));
        assertEquals(false, game_1_0_1.isAvailableToUser(male3));
        assertEquals(false, game_1_0_1.isAvailableToUser(female1));
    }

    @Test
    public void testSetAssignedUsers6(){
        assignedUsers.add(male1);
        assignedUsers.add(male1);

        Exception ex = null;
        try {
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(assignedUsers, substitutes);
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
            game_1_0_1.setAssignedUsers(null, substitutes);
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

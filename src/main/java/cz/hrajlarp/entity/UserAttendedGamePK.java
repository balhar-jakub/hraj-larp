package cz.hrajlarp.entity;

import java.io.Serializable;

/**
 * Needed only for GenericDao. Otherwise irrelevant.
 */
public class UserAttendedGamePk implements Serializable {
    Integer userId;
    Integer gameId;
}

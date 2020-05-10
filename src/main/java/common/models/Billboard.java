package common.models;

import common.utils.RandomFactory;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * This class consists of the billboard object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Billboard implements Serializable {
    /**
     * The variables of the object billboard.
     */
    public int id;
    public String name;
    public String message;
    public String messageColor;
    public byte[] picture;
    public String backgroundColor;
    public String information;
    public String informationColor;
    public boolean locked;
    public int userId;

    /**
     * An empty constructor just for creating the object.
     */
    public Billboard() {

    }

    /**
     * Billboard object constructor with an id.
     *
     * @param id:               database billboard ID as an int.
     * @param name:             billboard name as a string.
     * @param message:          billboard message as a string.
     * @param messageColor:     billboard message colour as a string.
     * @param picture:          billboard picture as a byte array.
     * @param backgroundColor:  billboard background colour as a string.
     * @param information:      billboard information as a string.
     * @param informationColor: billboard information colour as a string.
     * @param locked:           billboard scheduled as a boolean.
     * @param userId:           owner ID as an int.
     */
    public Billboard(int id,
                     String name,
                     String message,
                     String messageColor,
                     byte[] picture,
                     String backgroundColor,
                     String information,
                     String informationColor,
                     boolean locked,
                     int userId) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userId = userId;
    }

    /**
     * Billboard object constructor without an id.
     *
     * @param name:             billboard name as a string.
     * @param message:          billboard message as a string.
     * @param messageColor:     billboard message colour as a string.
     * @param picture:          billboard picture as a byte array.
     * @param backgroundColor:  billboard background colour as a string.
     * @param information:      billboard information as a string.
     * @param informationColor: billboard information colour as a string.
     * @param locked:           billboard scheduled as a boolean.
     * @param userId:           owner ID as an int.
     */
    public Billboard(
        String name,
        String message,
        String messageColor,
        byte[] picture,
        String backgroundColor,
        String information,
        String informationColor,
        boolean locked,
        int userId) {
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userId = userId;
    }

    /**
     * Generates a random billboard object with random variables
     * @param userId: the id of the user creating the billboard
     * @return a randomised billboard
     */
    public static Billboard Random(int userId) {
        return new Billboard(
            RandomFactory.String(),
            RandomFactory.String(),
            RandomFactory.Color(),
            RandomFactory.Bytes(30),
            RandomFactory.Color(),
            RandomFactory.String(),
            RandomFactory.Color(),
            RandomFactory.Boolean(),
            userId
        );
    }

}

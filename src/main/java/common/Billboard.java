package common;

/**
 * This class consists of the billboard object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Billboard implements IBillboard {
    /**
     * The variables of the object billboard
     */
    private int id;
    private String name;
    private String message;
    private String messageColor;
    private byte[] picture;
    private String pictureColor;
    private String information;
    private String informationColor;
    private boolean locked;
    private int userID;

    /**
     * Billboard object constructor
     *
     * @param id
     * @param name
     * @param message
     * @param messageColor
     * @param picture
     * @param pictureColor
     * @param information
     * @param informationColor
     * @param locked
     * @param userID
     */
    public Billboard(int id,
                     String name,
                     String message,
                     String messageColor,
                     byte[] picture,
                     String pictureColor,
                     String information,
                     String informationColor,
                     boolean locked,
                     int userID) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.pictureColor = pictureColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userID = userID;
    }
}

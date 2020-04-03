package xml;

public class Billboard {
    private String billboardName;
    private String billboardMessage;
    private String billboardPic;
    private String billboardInfo;
    private Boolean billboardLocked;
    private String billboardOwner;

    public String getBillboardName() {
        return billboardName;
    }

    public void setBillboardName(String billboardName) {
        this.billboardName = billboardName;
    }

    public String getBillboardMessage() {
        return billboardMessage;
    }

    public void setBillboardMessage(String billboardMessage) {
        this.billboardMessage = billboardMessage;
    }

    public String getBillboardPic() {
        return billboardPic;
    }

    public void setBillboardPic(String billboardPic) {
        this.billboardPic = billboardPic;
    }

    public String getBillboardInfo() {
        return billboardInfo;
    }

    public void setBillboardInfo(String billboardInfo) {
        this.billboardInfo = billboardInfo;
    }

    public Boolean getBillboardLocked() {
        return billboardLocked;
    }

    public void setBillboardLocked(Boolean billboardLocked) {
        this.billboardLocked = billboardLocked;
    }

    public String getBillboardOwner() {
        return billboardOwner;
    }

    public void setBillboardOwner(String billboardOwner) {
        this.billboardOwner = billboardOwner;
    }
}

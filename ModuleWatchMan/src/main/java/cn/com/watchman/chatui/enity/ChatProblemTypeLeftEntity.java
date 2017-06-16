package cn.com.watchman.chatui.enity;

/**
 * Created by 志强 on 2017.6.14.
 */

public class ChatProblemTypeLeftEntity {
    /**
     * ID : 1
     * Name : 部件上报
     * Pcode : 0
     * Code : 1
     */

    private int ID;
    private String Name;
    private String Pcode;
    private String Code;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPcode() {
        return Pcode;
    }

    public void setPcode(String Pcode) {
        this.Pcode = Pcode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    @Override
    public String toString() {
        return "ProblemTypeLeft{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Pcode='" + Pcode + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }
}

package in.objectsol.my_roomie.SetGet;

/**
 * Created by objsol on 23/04/18.
 */

public class Comment_SetGet {

    String id="",date="",status="",student_id="",comment="",rooms="", campus_id="";

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

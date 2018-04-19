package in.objectsol.my_roomie.SetGet;

/**
 * Created by objsol on 06/03/18.
 */

public class Permission_SetGet {

    String student_id="", campus_id="", id="",description="",created_at="",room_no="",student_name="";
    String permission_type="", from_time="", to_time="",status="",permission_granted="no", permission_rejected="no";

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getPermission_granted() {
        return permission_granted;
    }

    public void setPermission_granted(String permission_granted) {
        this.permission_granted = permission_granted;
    }

    public String getPermission_rejected() {
        return permission_rejected;
    }

    public void setPermission_rejected(String permission_rejected) {
        this.permission_rejected = permission_rejected;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) {
        this.permission_type = permission_type;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

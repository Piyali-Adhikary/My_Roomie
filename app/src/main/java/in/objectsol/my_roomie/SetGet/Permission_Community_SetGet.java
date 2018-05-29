package in.objectsol.my_roomie.SetGet;

/**
 * Created by objsol on 24/05/18.
 */

public class Permission_Community_SetGet {

    String id="",campus_id="", student_id="", community_id="", permission_type="", description="",created_at="", status="";
    String payment_for="", year="", month="", charges="",permission_granted="no", permission_rejected="no";

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

    public String getPayment_for() {
        return payment_for;
    }

    public void setPayment_for(String payment_for) {
        this.payment_for = payment_for;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) {
        this.permission_type = permission_type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

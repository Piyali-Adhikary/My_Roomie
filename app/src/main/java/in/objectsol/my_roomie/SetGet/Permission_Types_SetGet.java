package in.objectsol.my_roomie.SetGet;

/**
 * Created by objsol on 05/03/18.
 */

public class Permission_Types_SetGet {

    String id="", permission_type="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) {
        this.permission_type = permission_type;
    }

    @Override
    public String toString() {
        return getPermission_type(); // You can add anything else like maybe getDrinkType()
    }
}

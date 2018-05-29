package in.objectsol.my_roomie.SetGet;

/**
 * Created by objsol on 20/03/18.
 */

public class SetgetItems {

    String name="",cancel="", start_time="", end_time="", before_12_hours="",food_menu_id="";

    public String getFood_menu_id() {
        return food_menu_id;
    }

    public void setFood_menu_id(String food_menu_id) {
        this.food_menu_id = food_menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getBefore_12_hours() {
        return before_12_hours;
    }

    public void setBefore_12_hours(String before_12_hours) {
        this.before_12_hours = before_12_hours;
    }
}

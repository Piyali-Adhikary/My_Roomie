package in.objectsol.my_roomie.SetGet;

import java.util.ArrayList;

/**
 * Created by objsol on 20/03/18.
 */

public class SetgetDay {

    String dayname="";
    ArrayList<SetgetItems> breakfast=new ArrayList<>();
    ArrayList<SetgetItems> lunch=new ArrayList<>();
    ArrayList<SetgetItems> tiffin=new ArrayList<>();
    ArrayList<SetgetItems> dinner=new ArrayList<>();

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public ArrayList<SetgetItems> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<SetgetItems> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<SetgetItems> getLunch() {
        return lunch;
    }

    public void setLunch(ArrayList<SetgetItems> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<SetgetItems> getTiffin() {
        return tiffin;
    }

    public void setTiffin(ArrayList<SetgetItems> tiffin) {
        this.tiffin = tiffin;
    }

    public ArrayList<SetgetItems> getDinner() {
        return dinner;
    }

    public void setDinner(ArrayList<SetgetItems> dinner) {
        this.dinner = dinner;
    }
}

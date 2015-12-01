package schnauzer.digital.autoamigo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class Ride implements Serializable {
    String name;
    boolean[] days = new boolean[7];
    int departHour;
    int departMinutes;
    int arriveHour;
    int arriveMinutes;
    boolean arrivePm;
    boolean departPm;

    private ArrayList<LatLng> points;
    private ArrayList<LatLng> bounds;

    public Ride(String name, boolean[] days, int departHour, int departMinutes, int arriveHour, int arriveMinutes, boolean departPm, boolean arrivePm) {
        this.name = name;
        this.days = days;
        this.departHour = departHour;
        this.departMinutes = departMinutes;
        this.arriveHour = arriveHour;
        this.arriveMinutes = arriveMinutes;
        this.departPm = departPm;
        this.arrivePm = arrivePm;
        this.points = new ArrayList<LatLng>();
        this.bounds = new ArrayList<LatLng>();
    }

    public Ride () {
        this.name = "Default Ride";
        this.days = new boolean[7];
        this.departHour = 12;
        this.departMinutes = 0;
        this.arriveHour = 12;
        this.arriveMinutes = 0;
        this.departPm = true;
        this.arrivePm = true;
        this.points = new ArrayList<LatLng>();
        this.bounds = new ArrayList<LatLng>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public int getDepartHour() {
        return departHour;
    }

    public void setDepartHour(int departHour) {
        this.departHour = departHour;
    }

    public int getDepartMinutes() {
        return departMinutes;
    }

    public void setDepartMinutes(int departMinutes) {
        this.departMinutes = departMinutes;
    }

    public int getArriveHour() {
        return arriveHour;
    }

    public void setArriveHour(int arriveHour) {
        this.arriveHour = arriveHour;
    }

    public int getArriveMinutes() {
        return arriveMinutes;
    }

    public void setArriveMinutes(int arriveMinutes) {
        this.arriveMinutes = arriveMinutes;
    }

    public boolean isArrivePm() {
        return arrivePm;
    }

    public void setArrivePm(boolean arrivePm) {
        this.arrivePm = arrivePm;
    }

    public boolean isDepartPm() {
        return departPm;
    }

    public void setDepartPm(boolean departPm) {
        this.departPm = departPm;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }

    public ArrayList<LatLng> getBounds() {
        return bounds;
    }

    public void setBounds(ArrayList<LatLng> bounds) {
        this.bounds = bounds;
    }

    protected String getDepartTime() {
        String hour = departHour+"";
        String minutes = departMinutes+"";
        if (hour.length()<2)
            hour="0"+hour;
        if (minutes.length()<2)
            minutes="0"+minutes;
        String ampm = " am";
        if (departPm)
            ampm = " pm";
        return hour+':'+minutes+ampm;
    }

    protected String getArriveTime() {
        String hour = arriveHour+"";
        String minutes = arriveMinutes+"";
        if (hour.length()<2)
            hour="0"+hour;
        if (minutes.length()<2)
            minutes="0"+minutes;
        String ampm = " am";
        if (arrivePm)
            ampm = " pm";
        return hour+':'+minutes+ampm;
    }
}

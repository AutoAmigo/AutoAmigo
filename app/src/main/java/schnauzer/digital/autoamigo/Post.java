package schnauzer.digital.autoamigo;


import java.io.Serializable;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class Post implements Serializable{
    private String user;
    private String date;
    private String content;

    protected Post (String user, int year, int month, int day, int hour, int minutes, String content) {
        this.user = user;
        this.date = getTime(year, month, day, hour, minutes);
        this.content = content;
    }

    protected Post (String user, String date, String content) {
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    protected void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    protected void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    protected String getTime(int year, int month, int day, int hour, int min) {
        String hourString = hour+"";
        String minString = min+"";
        if (hourString.length()<2)
            hourString="0"+hourString;
        if (minString.length()<2)
            minString="0"+minString;
        return year+"/"+month+"/"+day+" - "+hourString+':'+minString;
    }

    public String toString() {
        return user+"; "+date+"; "+content;
    }
}

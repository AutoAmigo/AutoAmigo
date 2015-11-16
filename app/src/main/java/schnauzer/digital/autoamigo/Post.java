package schnauzer.digital.autoamigo;

import java.util.Date;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class Post {
    private String user;
    private Date date;
    private String content;

    protected Post (String user, Date date, String content) {
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

    public Date getDate() {
        return date;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }
}

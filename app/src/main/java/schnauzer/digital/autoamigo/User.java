package schnauzer.digital.autoamigo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class User implements Serializable {
    private String name;
    private String city;
    private int travelCount;
    private float drivingRating;
    private float socialRating;
    private float safetyRating;
    private float comfortRating;
    private String interests;

    ArrayList<Post> posts;

    public User(String name, String city) {
        this.name = name;
        this.city = city;
        this.posts = new ArrayList<Post>();
    }

    public User(String name, String city, int travelCount, float drivingRating, float socialRating, float safetyRating, float comfortRating, String interests, ArrayList<Post> posts) {
        this.name = name;
        this.city = city;
        this.travelCount = travelCount;
        this.drivingRating = drivingRating;
        this.socialRating = socialRating;
        this.safetyRating = safetyRating;
        this.comfortRating = comfortRating;
        this.interests = interests;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(int travelCount) {
        this.travelCount = travelCount;
    }

    public float getDrivingRating() {
        return drivingRating;
    }

    public void setDrivingRating(float drivingRating) {
        this.drivingRating = drivingRating;
    }

    public float getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(float socialRating) {
        this.socialRating = socialRating;
    }

    public float getSafetyRating() {
        return safetyRating;
    }

    public void setSafetyRating(float safetyRating) {
        this.safetyRating = safetyRating;
    }

    public float getComfortRating() {
        return comfortRating;
    }

    public void setComfortRating(float comfortRating) {
        this.comfortRating = comfortRating;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}

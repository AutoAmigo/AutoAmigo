package schnauzer.digital.autoamigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    TextView userNameView;
    TextView cityView;
    TextView travelCountView;

    TextView postUserNameView;
    TextView postDateView;
    TextView postContentView;

    RatingBar drivingRating;
    RatingBar socialRating;
    RatingBar safetyRating;
    RatingBar comfortRating;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        intent = getIntent();

        // Views
        userNameView = (TextView) findViewById(R.id.nameText);
        cityView = (TextView) findViewById(R.id.cityText);
        travelCountView = (TextView) findViewById(R.id.travelsNumber);

        postUserNameView = (TextView) findViewById(R.id.userNameText);
        postDateView = (TextView) findViewById(R.id.dateText);
        postContentView = (TextView) findViewById(R.id.postText);

        drivingRating = (RatingBar) findViewById(R.id.drivingRatingBar);
        socialRating = (RatingBar) findViewById(R.id.socialRatingBar);
        safetyRating = (RatingBar) findViewById(R.id.safetyRatingBar);
        comfortRating = (RatingBar) findViewById(R.id.comfortRatingBar);

        //testing
        User user = new User("Diego Padilla", "Ensenada", 51, 3.8f, 4.76f, 4.5f, 4.0f, "Gatos", new ArrayList<Post>());
        setUser(user);
    }

    protected void setUser (User user) {
        userNameView.setText(user.getName());
        cityView.setText(user.getCity());
        travelCountView.setText(user.getTravelCount() + "");

        if (!user.getPosts().isEmpty()) {
            postUserNameView.setText(user.getPosts().get(0).getUser());
            postDateView.setText(user.getPosts().get(0).getDate().toString());
            postContentView.setText(user.getPosts().get(0).getContent());
        } else {
            postUserNameView.setText("");
            postDateView.setText("No posts");
            postContentView.setText("");
        }

        drivingRating.setRating(user.getDrivingRating());
        socialRating.setRating(user.getSocialRating());
        safetyRating.setRating(user.getSafetyRating());
        comfortRating.setRating(user.getComfortRating());
    }
}

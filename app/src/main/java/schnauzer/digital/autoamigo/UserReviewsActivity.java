package schnauzer.digital.autoamigo;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RatingBar;

public class UserReviewsActivity extends AppCompatActivity {

    Intent intent;
    User user;

    RatingBar drivingRating;
    RatingBar socialRating;
    RatingBar safetyRating;
    RatingBar comfortRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        setTitle(user.getName());

        drivingRating = (RatingBar) findViewById(R.id.drivingRatingBar);
        socialRating = (RatingBar) findViewById(R.id.socialRatingBar);
        safetyRating = (RatingBar) findViewById(R.id.safetyRatingBar);
        comfortRating = (RatingBar) findViewById(R.id.comfortRatingBar);

        setUser(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUser (User user) {
        drivingRating.setRating(user.getDrivingRating());
        socialRating.setRating(user.getSocialRating());
        safetyRating.setRating(user.getSafetyRating());
        comfortRating.setRating(user.getComfortRating());
    }
}

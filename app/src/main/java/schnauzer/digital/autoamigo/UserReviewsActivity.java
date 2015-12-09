package schnauzer.digital.autoamigo;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class UserReviewsActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    User user;

    //NAVIGATION VIEWS
    Button myProfileButton;

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

        myProfileButton = (Button) findViewById(R.id.myProfileButton);
        drivingRating = (RatingBar) findViewById(R.id.drivingRatingBar);
        socialRating = (RatingBar) findViewById(R.id.socialRatingBar);
        safetyRating = (RatingBar) findViewById(R.id.safetyRatingBar);
        comfortRating = (RatingBar) findViewById(R.id.comfortRatingBar);

        myProfileButton.setOnClickListener(this);

        setUser(user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.myProfileButton:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setUser (User user) {
        drivingRating.setRating(user.getDrivingRating());
        socialRating.setRating(user.getSocialRating());
        safetyRating.setRating(user.getSafetyRating());
        comfortRating.setRating(user.getComfortRating());
    }
}

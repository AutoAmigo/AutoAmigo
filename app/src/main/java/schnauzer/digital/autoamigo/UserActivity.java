package schnauzer.digital.autoamigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    static final int USER_REVIEWS_REQUEST = 1;
    static final int USER_POSTS_REQUEST = 2;
    static final int USER_RIDES_REQUEST = 3;

    User user=null;

    //NAVIGATION VIEWS
    Button myProfileButton;
    Button myExternalProfileButton;

    TextView userNameView;
    TextView cityView;
    TextView travelCountView;

    TextView postUserNameView;
    TextView postDateView;
    TextView postContentView;

    Button reviewsButton;
    Button postsButton;

    RatingBar averageRating;

    private ListView rideListView;
    private ArrayList<Ride> userRides;

    Intent intent;

    //DBAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Views
        myProfileButton = (Button) findViewById(R.id.myProfileButton);
        myExternalProfileButton = (Button) findViewById(R.id.myExternalProfileButton);
        userNameView = (TextView) findViewById(R.id.nameText);
        cityView = (TextView) findViewById(R.id.cityText);
        travelCountView = (TextView) findViewById(R.id.travelsNumber);

        reviewsButton = (Button) findViewById(R.id.reviewsButton);
        postsButton = (Button) findViewById(R.id.postsButton);

        postUserNameView = (TextView) findViewById(R.id.userNameText);
        postDateView = (TextView) findViewById(R.id.dateText);
        postContentView = (TextView) findViewById(R.id.postText);

        averageRating = (RatingBar) findViewById(R.id.averageRatingBar);

        rideListView = (ListView) findViewById(R.id.rideListView);

        // Listeners
        myProfileButton.setOnClickListener(this);
        myExternalProfileButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        postsButton.setOnClickListener(this);

        // Testing
        user = new User("Diego Padilla", "Ensenada", 51, 3.8f, 3.76f, 4.2f, 4.0f, "Gatos", new ArrayList<Post>(), new ArrayList<Ride>());

        user.addPost(new Post("Diego Padilla", 2015, 10, 5, 13, 35, "Hoy no voy a poder dar raite"));
        user.addPost(new Post("Rogelio Gonzalez", 2015, 10, 5, 13, 45, "Vales roña, Diego."));
        user.addPost(new Post("Diego Padilla", 2015, 10, 14, 13, 12, "Adivinen que\nno raite today"));
        user.addPost(new Post("Diego Padilla", 2015, 10, 15, 15, 37, "Ya pues hoy si puedo"));
        user.addPost(new Post("Diego Padilla", 2015, 10, 18, 9, 40, "lluvia lluvia"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 3, 3, 35, "ya me quiero eslipear"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 11, 14, 10, "Lo siento chicos pero hoy no podre proporcionales el glorioso raite que se que se merecen"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 17, 14, 24, "give me the rait to be raited"));

        user.addRide(new Ride("Regreso a casa", new boolean[]{false, true, false, true, false, false, false}, 6, 0, 6, 30, true, true));
        user.addRide(new Ride("Al CETYS", new boolean[] {false, false, false, false, false, true, false}, 3, 20, 3, 55, true, true));
        user.addRide(new Ride("Regreso a casa", new boolean[] {false, false, true, false, true, false, false}, 9, 0, 9, 30, true, true));
        user.addRide(new Ride("Regreso a casa", new boolean[] {false, true, false, true, false, false, false}, 7, 0, 7, 30, true, true));
        user.addRide(new Ride("Al CETYS", new boolean[]{false, true, true, true, true, false, false}, 4, 20, 4, 55, true, true));

        setUser(user);

     /*   //DataBase
        openDB();
*/
        //Map
        Button buttonmap = (Button)findViewById(R.id.buttonMap);
        //DataBase
        //openDB();
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //closeDB();
    }

    private void openDB() {
        myDB = new DBAdapter(this);
        myDB.open();
    }

    private void closeDB() {
        myDB.close();
    }

    public void onSaveUser(){
       long newId =  myDB.addUser("KEY_NAME", "KEY_LASTNAME", "KEY_EMAIL","KEY_CITY","KEY_USER","KEY_PASSWORD","KEY_TYPE","KEY_HANDICAPPED","20","KEY_INSTITUTION");
    }
    public void onSavePost(){
        long newId =  myDB.addPost(1, "KEY_DATE", "KEY_TIME", "KEY_POST");

    }
    public void onSaveRide(){
        long newId =  myDB.addRide(1, "KEY_DATE_RIDE", "KEY_START_TIME", "KEY_END_TIME", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "NAME_RIDE");

    }

    }*/
    public void openMap(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void onDisplayDB(){
        // METODO VACIO?
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.myProfileButton:
                intent = new Intent(this, UserInterface.class);
                startActivity(intent);
                break;
            case R.id.myExternalProfileButton:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.reviewsButton:
                //Toast.makeText(UserActivity.this, "Trying to launch user reviews", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, UserReviewsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.postsButton:
                //Toast.makeText(UserActivity.this, "Trying to launch user posts", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, UserPostsActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, USER_POSTS_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_REVIEWS_REQUEST || requestCode == USER_POSTS_REQUEST ||requestCode == USER_RIDES_REQUEST) {
            if (resultCode == RESULT_OK) {
                Post post = (Post) data.getSerializableExtra("lastPost");
                user.addPost(post);
                Log.wtf("User Activity", "Result is: "+post.toString());
                setMainPost(post);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(UserActivity.this, "No user returned", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUser (User user) {
        userNameView.setText(user.getName());
        cityView.setText(user.getCity());
        travelCountView.setText(user.getTravelCount() + "");

        if (!user.getPosts().isEmpty()) {
            setMainPost(user.getPosts().get(0));
        } else {
            setMainPost(null);
        }

        averageRating.setRating(user.getAverageRating());

        userRides = user.getRides();

        // Ride List
        if (!userRides.isEmpty()) {
            RideAdapter rideAdapter = new RideAdapter(this, R.layout.item_ride, userRides);

            rideListView.setAdapter(rideAdapter);

            rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = userRides.get(position).getName();

                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setMainPost(Post post) { // Set main post as the recieved post, if post is null, set "No posts" as the main post
        if (post == null) {
            postUserNameView.setText("");
            postDateView.setText("No posts");
            postContentView.setText("");
        } else {
            postUserNameView.setText(post.getUser());
            postDateView.setText(post.getDate());
            postContentView.setText(post.getContent());
        }
    }
}

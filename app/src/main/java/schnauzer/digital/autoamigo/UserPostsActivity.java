package schnauzer.digital.autoamigo;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UserPostsActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    Intent intent;
    User user;
    String sessionUser;

    private EditText postEditText;
    //private Button postButton;
    private ListView postListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        user = (User) intent.getSerializableExtra("user");
        sessionUser = user.getName();                       // ESTO SE DEBE CAMBIAR POR EL NOMBRE DE USUARIO DE LA CUENTA QUE TENGA LA SESION INICIADA

        // Views
        postEditText = (EditText) findViewById(R.id.postEditText);
        //postButton = (Button) findViewById(R.id.postButton);
        postListView = (ListView) findViewById(R.id.postsListView);

        // Listeners
        postEditText.setOnFocusChangeListener(this);
        //postButton.setOnClickListener(this);

        // Post List
        PostAdapter postAdapter = new PostAdapter(this, R.layout.user_post, user.getPosts());
        postListView.setAdapter(postAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.posts_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuPostButton:
                if (postEditText.getText().length()<1) {
                    postEditText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(postEditText, InputMethodManager.SHOW_IMPLICIT);
                    return true;
                }
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd '-' HH:mm z");
                String date = df.format(Calendar.getInstance().getTime());
                user.addPost(new Post(sessionUser, date, postEditText.getText().toString()));
                postEditText.setText("");
                postEditText.setLines(1);
                intent.putExtra("lastPost", user.getPosts().get(0));
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.postEditText:
                if (hasFocus)
                    postEditText.setLines(5);
                else
                    postEditText.setLines(1);
                break;
        }
    }
}

package schnauzer.digital.autoamigo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class PostAdapter extends ArrayAdapter<Post> {

    Context context;
    int layoutResourceId;
    ArrayList<Post> posts;

    public PostAdapter (Context context, int layoutResourceId, ArrayList<Post> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.posts = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PostHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PostHolder();

            holder.userNameText = (TextView)row.findViewById(R.id.userNameText);
            holder.timeText = (TextView)row.findViewById(R.id.dateText);
            holder.postText = (TextView)row.findViewById(R.id.postText);

            row.setTag(holder);
        }
        else
        {
            holder = (PostHolder)row.getTag();
        }

        Post post = posts.get(position);
        holder.userNameText.setText(post.getUser());
        holder.timeText.setText(post.getDate());
        holder.postText.setText(post.getContent());

        return row;
    }

    static class PostHolder
    {
        TextView userNameText;
        TextView timeText;
        TextView postText;
    }
}

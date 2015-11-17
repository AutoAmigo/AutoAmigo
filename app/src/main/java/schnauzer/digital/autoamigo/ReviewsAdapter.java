package schnauzer.digital.autoamigo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class ReviewsAdapter extends ArrayAdapter<Post> {

    Context context;
    int layoutResourceId;
    Post data[];

    public ReviewsAdapter(Context context, int layoutResourceId, Post[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ReviewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ReviewHolder();

            holder.userNameText = (TextView)row.findViewById(R.id.userNameText);
            holder.timeText = (TextView)row.findViewById(R.id.dateText);
            holder.postText = (TextView)row.findViewById(R.id.postText);

            row.setTag(holder);
        }
        else
        {
            holder = (ReviewHolder)row.getTag();
        }

        Post post= data[position];
        holder.userNameText.setText(post.getUser());
        holder.timeText.setText(post.getDate());
        holder.postText.setText(post.getContent());

        return row;
    }

    static class ReviewHolder
    {
        TextView userNameText;
        TextView timeText;
        TextView postText;
    }
}

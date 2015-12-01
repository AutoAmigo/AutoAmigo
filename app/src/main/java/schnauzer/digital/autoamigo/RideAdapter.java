package schnauzer.digital.autoamigo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rogelio on 11/16/2015.
 */
public class RideAdapter extends ArrayAdapter<Ride> {

    static final String ACTIVE_COLOR = "#FF3FB541";

    Context context;
    int layoutResourceId;
    ArrayList<Ride> data;

    public RideAdapter (Context context, int layoutResourceId, ArrayList<Ride> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RideHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RideHolder();

            holder.txtTitle = (TextView)row.findViewById(R.id.rideNameText);
            holder.monday = (TextView)row.findViewById(R.id.monday);
            holder.tuesday = (TextView)row.findViewById(R.id.tuesday);
            holder.wednesday = (TextView)row.findViewById(R.id.wednesday);
            holder.thursday = (TextView)row.findViewById(R.id.thursday);
            holder.friday = (TextView)row.findViewById(R.id.friday);
            holder.saturday = (TextView)row.findViewById(R.id.saturday);
            holder.sunday = (TextView)row.findViewById(R.id.sunday);
            holder.departTime = (TextView)row.findViewById(R.id.departTimeText);
            holder.arriveTime = (TextView)row.findViewById(R.id.arriveTimeText);

            row.setTag(holder);
        }
        else
        {
            holder = (RideHolder)row.getTag();
        }

        Ride ride = data.get(position);
        holder.txtTitle.setText(ride.getName());

        if (ride.getDays()[0]) {holder.monday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.monday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[1]) {holder.tuesday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.tuesday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[2]) {holder.wednesday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.wednesday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[3]) {holder.thursday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.thursday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[4]) {holder.friday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.friday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[5]) {holder.saturday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.saturday.setTypeface(Typeface.DEFAULT_BOLD);}
        if (ride.getDays()[6]){holder.sunday.setTextColor(Color.parseColor(ACTIVE_COLOR)); holder.sunday.setTypeface(Typeface.DEFAULT_BOLD);}

        holder.departTime.setText(ride.getDepartTime());
        holder.arriveTime.setText(ride.getArriveTime());

        return row;
    }

    static class RideHolder
    {
        TextView txtTitle;
        TextView sunday;
        TextView monday;
        TextView tuesday;
        TextView wednesday;
        TextView thursday;
        TextView friday;
        TextView saturday;
        TextView departTime;
        TextView arriveTime;
    }
}

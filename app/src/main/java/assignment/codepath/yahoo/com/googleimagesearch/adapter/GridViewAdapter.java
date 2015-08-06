package assignment.codepath.yahoo.com.googleimagesearch.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import assignment.codepath.yahoo.com.googleimagesearch.R;
import assignment.codepath.yahoo.com.googleimagesearch.helpers.Utils;

/**
 * Created by jonaswu on 2015/8/2.
 */
public class GridViewAdapter extends CustomizedAdapter {

    // View lookup cache
    private static class ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;
    }

    public GridViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        JSONObject item = (JSONObject) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.title);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.description);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.main_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        try {
            viewHolder.titleTextView.setText(Html.fromHtml(item.getString("title")));
            viewHolder.descriptionTextView.setText(item.getString("contentNoFormatting"));
            Picasso.with(context)
                    .load(item.getString("tbUrl"))
                    .error(R.drawable.images)
                    .placeholder(R.drawable.placeholder)
                    .centerInside()
                    .resize(
                            Integer.valueOf(item.getString("tbWidth")),
                            Integer.valueOf(item.getString("tbHeight"))
                    )
                    .into(viewHolder.imageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

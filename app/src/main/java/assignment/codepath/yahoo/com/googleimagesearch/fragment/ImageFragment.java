package assignment.codepath.yahoo.com.googleimagesearch.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import assignment.codepath.yahoo.com.googleimagesearch.R;
import assignment.codepath.yahoo.com.googleimagesearch.view.TouchImageView;

public class ImageFragment extends DialogFragment {

    public void setObject(JSONObject object) {
        this.data = object;
    }

    public JSONObject getObject() {
        return data;
    }

    private JSONObject data;


    public static ImageFragment newInstance(JSONObject data) {
        ImageFragment frag = new ImageFragment();
        frag.setObject(data);
        return frag;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.image_detail, container);
        JSONObject data = getObject();
        final TouchImageView largeImage = (TouchImageView) view.findViewById(R.id.large_image);
        ImageView zoomin = (ImageView) view.findViewById(R.id.zoomin);
        ImageView zoomout = (ImageView) view.findViewById(R.id.zoomout);
        TextView size = (TextView) view.findViewById(R.id.size);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);

        TextView domain = (TextView) view.findViewById(R.id.domain);
        try {
            size.setText(data.get("width") + " x " + data.get("height"));
            title.setText(Html.fromHtml(data.getString("title")));
            description.setText(data.getString("contentNoFormatting"));
            domain.setText(data.getString("visibleUrl"));
            zoomin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float currentZoom = largeImage.getCurrentZoom();
                    double nextZoom = currentZoom + 0.5;
                    largeImage.setZoom((float) nextZoom);
                }
            });
            zoomout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float currentZoom = largeImage.getCurrentZoom();
                    double nextZoom = currentZoom - 0.5;
                    largeImage.setZoom((float) nextZoom);
                }
            });
            Picasso.with(getActivity())
                    .load(data.getString("url"))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.images)
                    .centerCrop()
                    .resize(600, 800)
                    .into(largeImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
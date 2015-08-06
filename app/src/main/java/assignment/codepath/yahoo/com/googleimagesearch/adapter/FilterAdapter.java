package assignment.codepath.yahoo.com.googleimagesearch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import assignment.codepath.yahoo.com.googleimagesearch.R;
import assignment.codepath.yahoo.com.googleimagesearch.helpers.Storage;

/**
 * Created by jonaswu on 2015/8/2.
 */
public class FilterAdapter extends CustomizedAdapter implements ExpandableListAdapter {
    public FilterAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject data = (JSONObject) getItem(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expandable_item, parent, false);
        try {
            (view.findViewById(R.id.icon)).setBackgroundResource(data.getInt("icon"));
            ((TextView) view.findViewById(R.id.name)).setText(data.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getItem(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expandable_item, parent, false);
        String strGroupPosition = String.valueOf(groupPosition);
        Log.e("strGroupPosition + _icon", strGroupPosition + "_icon");
        String icon = Storage.read(context, strGroupPosition + "_icon", "");
        (view.findViewById(R.id.icon)).setBackground(context.getResources().getDrawable(Integer.valueOf(icon)));
        ((TextView) view.findViewById(R.id.name)).setText(Storage.read(context, strGroupPosition + "_name", ""));
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (groupPosition == 0) { // size setting
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.size_setting, parent, false);
            CheckBox small = (CheckBox) view.findViewById(R.id.small);

            small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Storage.write(context, "isSmall", String.valueOf(isChecked));
                }
            });
            CheckBox medium = (CheckBox) view.findViewById(R.id.medium);
            medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Storage.write(context, "isMedium", String.valueOf(isChecked));
                }
            });
            CheckBox large = (CheckBox) view.findViewById(R.id.large);
            large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Storage.write(context, "isLarge", String.valueOf(isChecked));
                }
            });
            CheckBox extra_large = (CheckBox) view.findViewById(R.id.extra_large);
            extra_large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Storage.write(context, "isExtremeLarge", String.valueOf(isChecked));
                }
            });
            small.setChecked(Boolean.valueOf(Storage.read(context, "isSmall", "false")));
            medium.setChecked(Boolean.valueOf(Storage.read(context, "isMedium", "false")));
            large.setChecked(Boolean.valueOf(Storage.read(context, "isLarge", "false")));
            extra_large.setChecked(Boolean.valueOf(Storage.read(context, "isExtremeLarge", "false")));
        } else if (groupPosition == 1) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.color_setting, parent, false);


            Spinner spinner = (Spinner) view.findViewById(R.id.colorFilter);
            spinner.setAdapter(new CustomizedAdapter(context) {

                @Override
                public void initData() {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("name", "black");
                    data.put("color", Color.BLACK);
                    this.addItem(data);
                    data = new HashMap<String, Object>();
                    data.put("name", "white");
                    data.put("color", Color.WHITE);
                    this.addItem(data);

                    data = new HashMap<String, Object>();
                    data.put("name", "green");
                    data.put("color", context.getResources().getColor(R.color.green));
                    this.addItem(data);

                    data = new HashMap<String, Object>();
                    data.put("name", "red");
                    data.put("color", context.getResources().getColor(R.color.red));
                    this.addItem(data);

                    data = new HashMap<String, Object>();
                    data.put("name", "blue");
                    data.put("color", context.getResources().getColor(R.color.blue));
                    this.addItem(data);

                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    HashMap<String, Object> item = (HashMap<String, Object>) getItem(position);
                    View view = inflater.inflate(R.layout.colorfilter_spinner_layout, parent, false);
                    TextView color = (TextView) view.findViewById(R.id.color);
                    color.setBackgroundColor((int) item.get("color"));
                    TextView text = (TextView) view.findViewById(R.id.text);
                    text.setText(item.get("name").toString());
                    return view;
                }
            });
        } else if (groupPosition == 2) {

            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.imgtype_setting, parent, false);
            final CheckBox face = (CheckBox) view.findViewById(R.id.face);
            final CheckBox photo = (CheckBox) view.findViewById(R.id.photo);
            final CheckBox clipart = (CheckBox) view.findViewById(R.id.clipart);
            final CheckBox lineart = (CheckBox) view.findViewById(R.id.lineart);

            face.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Storage.write(context, "imgtype", "face");
                        photo.setChecked(false);
                        clipart.setChecked(false);
                        lineart.setChecked(false);
                    } else {
                        if (Storage.read(context, "imgtype", "").equals("face"))
                            Storage.write(context, "imgtype", "");
                    }
                }
            });

            photo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Storage.write(context, "imgtype", "photo");
                        face.setChecked(false);
                        clipart.setChecked(false);
                        lineart.setChecked(false);
                    } else {
                        if (Storage.read(context, "imgtype", "").equals("photo"))
                            Storage.write(context, "imgtype", "");
                    }
                }
            });

            clipart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Storage.write(context, "imgtype", "clipart");
                        face.setChecked(false);
                        photo.setChecked(false);
                        lineart.setChecked(false);
                    } else {
                        if (Storage.read(context, "imgtype", "").equals("clipart"))
                            Storage.write(context, "imgtype", "");
                    }
                }
            });

            lineart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Storage.write(context, "imgtype", "lineart");
                        face.setChecked(false);
                        photo.setChecked(false);
                        clipart.setChecked(false);
                    } else {
                        if (Storage.read(context, "imgtype", "").equals("lineart"))
                            Storage.write(context, "imgtype", "");
                    }
                }
            });
            face.setChecked(Boolean.valueOf(Storage.read(context, "isFace", "false")));
            photo.setChecked(Boolean.valueOf(Storage.read(context, "isPhoto", "false")));
            clipart.setChecked(Boolean.valueOf(Storage.read(context, "isClipart", "false")));
            lineart.setChecked(Boolean.valueOf(Storage.read(context, "isLineart", "false")));
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

}

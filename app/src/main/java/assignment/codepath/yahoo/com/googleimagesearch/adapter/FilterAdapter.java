package assignment.codepath.yahoo.com.googleimagesearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import assignment.codepath.yahoo.com.googleimagesearch.R;

/**
 * Created by jonaswu on 2015/8/2.
 */
public class FilterAdapter extends CustomizedAdapter implements ExpandableListAdapter {
    public FilterAdapter(Context context) {
        super(context);
        this.addItem(new JSONObject()); // for size
        this.addItem(new JSONObject()); // for size
    }

    public void setSizeFilter(JSONObject sizeFilter) {
        this.setItem(0, sizeFilter);
    }

    public void setColorFilter(JSONObject colorFilter) {
        this.setItem(1, colorFilter);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject data = getItem(position);
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
        return getCount();
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
        JSONObject data = getItem(groupPosition);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expandable_item, parent, false);
        try {
            (view.findViewById(R.id.icon)).setBackground(context.getResources().getDrawable(data.getInt("icon")));
            ((TextView) view.findViewById(R.id.name)).setText(data.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (groupPosition == 0) { // size setting
            final JSONObject sizeFilter = getSizeFilter();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.size_setting, parent, false);
            CheckBox small = (CheckBox) view.findViewById(R.id.small);

            small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        sizeFilter.put("isSmall", isChecked);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            CheckBox medium = (CheckBox) view.findViewById(R.id.medium);
            medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        sizeFilter.put("isMedium", isChecked);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            CheckBox large = (CheckBox) view.findViewById(R.id.large);
            large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        sizeFilter.put("isLarge", isChecked);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            CheckBox extra_large = (CheckBox) view.findViewById(R.id.extra_large);
            extra_large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        sizeFilter.put("isExtremeLarge", isChecked);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                small.setChecked(sizeFilter.getBoolean("isSmall"));
                medium.setChecked(sizeFilter.getBoolean("isMedium"));
                large.setChecked(sizeFilter.getBoolean("isLarge"));
                extra_large.setChecked(sizeFilter.getBoolean("isExtremeLarge"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (groupPosition == 1) {
            final JSONObject colorFilter = getColorFilter();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.color_setting, parent, false);

            final CheckBox black = (CheckBox) view.findViewById(R.id.black);
            final CheckBox white = (CheckBox) view.findViewById(R.id.white);
            final CheckBox green = (CheckBox) view.findViewById(R.id.green);
            final CheckBox red = (CheckBox) view.findViewById(R.id.red);
            final CheckBox blue = (CheckBox) view.findViewById(R.id.blue);

            black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            colorFilter.put("color", "black");
                            white.setChecked(false);
                            green.setChecked(false);
                            red.setChecked(false);
                            blue.setChecked(false);
                        } else {
                            if (colorFilter.getString("color").equals("black"))
                                colorFilter.put("color", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            white.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            colorFilter.put("color", "white");
                            black.setChecked(false);
                            green.setChecked(false);
                            red.setChecked(false);
                            blue.setChecked(false);
                        } else {
                            if (colorFilter.getString("color").equals("white"))
                                colorFilter.put("color", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            colorFilter.put("color", "green");
                            white.setChecked(false);
                            black.setChecked(false);
                            red.setChecked(false);
                            blue.setChecked(false);
                        } else {
                            if (colorFilter.getString("color").equals("green"))
                                colorFilter.put("color", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            colorFilter.put("color", "red");
                            white.setChecked(false);
                            black.setChecked(false);
                            green.setChecked(false);
                            blue.setChecked(false);
                        } else {
                            if (colorFilter.getString("color").equals("red"))
                                colorFilter.put("color", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            colorFilter.put("color", "blue");
                            white.setChecked(false);
                            black.setChecked(false);
                            green.setChecked(false);
                            red.setChecked(false);
                        } else {
                            if (colorFilter.getString("color").equals("blue"))
                                colorFilter.put("color", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                black.setChecked(colorFilter.getString("color") == "black" ? true : false);
                white.setChecked(colorFilter.getString("color") == "white" ? true : false);
                green.setChecked(colorFilter.getString("color") == "green" ? true : false);
                red.setChecked(colorFilter.getString("color") == "red" ? true : false);
                blue.setChecked(colorFilter.getString("color") == "blue" ? true : false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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


    public JSONObject getSizeFilter() {
        return this.getItem(0);
    }

    public JSONObject getColorFilter() {
        return this.getItem(1);
    }
}

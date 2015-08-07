package assignment.codepath.yahoo.com.googleimagesearch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.HeterogeneousExpandableList;
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
public class FilterAdapter extends CustomizedAdapter implements ExpandableListAdapter, HeterogeneousExpandableList {
    public FilterAdapter(Context context) {
        super(context);
        addItem("1");
        addItem("2");
        addItem("3");
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
    public int getGroupType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupTypeCount() {
        return 1;
    }

    @Override
    public int getChildTypeCount() {
        return 3;
    }

    static class ViewHolder {
        CheckBox small;

        public CheckBox medium;
        public CheckBox large;
        public CheckBox extra_large;
        public Spinner colorSpinner;
        public Spinner imgtypeSpinner;
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
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
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
        String icon = Storage.read(context, strGroupPosition + "_icon", "");
        (view.findViewById(R.id.icon)).setBackground(context.getResources().getDrawable(Integer.valueOf(icon)));
        ((TextView) view.findViewById(R.id.name)).setText(Storage.read(context, strGroupPosition + "_name", ""));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (groupPosition == 0) {
                convertView = inflater.inflate(R.layout.size_setting, parent, false);
                viewHolder.colorSpinner = (Spinner) convertView.findViewById(R.id.colorFilter);
                viewHolder.small = (CheckBox) convertView.findViewById(R.id.small);
                viewHolder.medium = (CheckBox) convertView.findViewById(R.id.medium);
                viewHolder.large = (CheckBox) convertView.findViewById(R.id.large);
                viewHolder.extra_large = (CheckBox) convertView.findViewById(R.id.extra_large);
                viewHolder.small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Storage.write(context, "isSmall", String.valueOf(isChecked));
                    }
                });

                viewHolder.medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Storage.write(context, "isMedium", String.valueOf(isChecked));
                    }
                });

                viewHolder.large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Storage.write(context, "isLarge", String.valueOf(isChecked));
                    }
                });
                viewHolder.extra_large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Storage.write(context, "isExtremeLarge", String.valueOf(isChecked));
                    }
                });
            }

            if (groupPosition == 1) {
                convertView = inflater.inflate(R.layout.color_setting, parent, false);
                viewHolder.colorSpinner = (Spinner) convertView.findViewById(R.id.colorFilter);
                viewHolder.colorSpinner.setAdapter(new CustomizedAdapter(context) {

                    @Override
                    public void initData() {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data = new HashMap<String, Object>();
                        data.put("name", "");
                        data.put("color", Color.TRANSPARENT);
                        this.addItem(data);

                        data = new HashMap<String, Object>();
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
                viewHolder.colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> item = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                        Storage.write(context, "color", item.get("name").toString());
                        Storage.write(context, "colorSelected", String.valueOf(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Storage.write(context, "color", "");
                        Storage.write(context, "colorSelected", "0");
                    }
                });

            }

            if (groupPosition == 2) {
                convertView = inflater.inflate(R.layout.color_setting, parent, false);
                viewHolder.imgtypeSpinner = (Spinner) convertView.findViewById(R.id.colorFilter);
                viewHolder.imgtypeSpinner.setAdapter(new CustomizedAdapter(context) {

                    @Override
                    public void initData() {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data = new HashMap<String, Object>();
                        data.put("name", "");
                        this.addItem(data);

                        data = new HashMap<String, Object>();
                        data.put("name", "face");
                        this.addItem(data);
                        data = new HashMap<String, Object>();
                        data.put("name", "photo");
                        this.addItem(data);

                        data = new HashMap<String, Object>();
                        data.put("name", "clipart");
                        this.addItem(data);

                        data = new HashMap<String, Object>();
                        data.put("name", "lineart");
                        this.addItem(data);
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        HashMap<String, Object> item = (HashMap<String, Object>) getItem(position);
                        View view = inflater.inflate(R.layout.colorfilter_spinner_layout, parent, false);
                        TextView text = (TextView) view.findViewById(R.id.text);
                        text.setText(item.get("name").toString());
                        return view;
                    }
                });
                viewHolder.imgtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> item = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                        Storage.write(context, "imgtype", item.get("name").toString());
                        Storage.write(context, "imgtypeSelected", String.valueOf(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.e("onNothingSelected", "onNothingSelected");
                        Storage.write(context, "imgtype", "");
                        Storage.write(context, "imgtypeSelected", "0");
                    }
                });
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (groupPosition == 0) {
            viewHolder.small.setChecked(Boolean.valueOf(Storage.read(context, "isSmall", "false")));
            viewHolder.medium.setChecked(Boolean.valueOf(Storage.read(context, "isMedium", "false")));
            viewHolder.large.setChecked(Boolean.valueOf(Storage.read(context, "isLarge", "false")));
            viewHolder.extra_large.setChecked(Boolean.valueOf(Storage.read(context, "isExtremeLarge", "false")));
        } else if (groupPosition == 1) {
            viewHolder.colorSpinner.setSelection(Integer.valueOf(Storage.read(context, "colorSelected", "0")));

        } else if (groupPosition == 2) {
            viewHolder.imgtypeSpinner.setSelection(Integer.valueOf(Storage.read(context, "imgtypeSelected", "0")));
        }
        return convertView;
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

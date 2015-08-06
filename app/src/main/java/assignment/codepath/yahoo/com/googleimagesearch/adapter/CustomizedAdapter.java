package assignment.codepath.yahoo.com.googleimagesearch.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jonaswu on 2015/7/23.
 */
public abstract class CustomizedAdapter extends BaseAdapter {

    protected ArrayList<Object> listItem = new ArrayList<Object>();
    protected Context context;

    public CustomizedAdapter(Context context) {
        super();
        this.context = context;
        initData();

    }

    public void initData() {
        // to be override
    }

    public void setItem(int id, Object value) {
        listItem.set(id, value);
        this.notifyDataSetChanged();
    }

    public void addItem(Object value) {
        this.addItemWithoutNotifyChange(value);
        this.notifyDataSetChanged();
    }

    public void addItemWithoutNotifyChange(Object value) {
        listItem.add(value);
    }


    public void deleteItem(int id) {
        listItem.remove(id);
        this.notifyDataSetChanged();
    }

    public void deleteAll() {
        listItem.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}

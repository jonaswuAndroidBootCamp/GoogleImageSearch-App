package assignment.codepath.yahoo.com.googleimagesearch.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import assignment.codepath.yahoo.com.googleimagesearch.R;
import assignment.codepath.yahoo.com.googleimagesearch.adapter.GridViewAdapter;
import assignment.codepath.yahoo.com.googleimagesearch.fragment.FilterDialogFragment;
import assignment.codepath.yahoo.com.googleimagesearch.fragment.ImageFragment;
import assignment.codepath.yahoo.com.googleimagesearch.helpers.BaseActivity;
import assignment.codepath.yahoo.com.googleimagesearch.helpers.EndlessScrollListener;
import assignment.codepath.yahoo.com.googleimagesearch.helpers.Utils;
import assignment.codepath.yahoo.com.googleimagesearch.network.API;


public class MainActivity extends BaseActivity {

    private GridViewAdapter gridViewAdapter;
    private JSONObject sizeFilter;
    private JSONObject colorFilter;

    public void setCurrentQueryString(String currentQueryString) {
        this.currentQueryString = currentQueryString;
    }

    public String getCurrentQueryString() {
        return currentQueryString;
    }

    private String currentQueryString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        gridViewAdapter = new GridViewAdapter(this);
        StaggeredGridView gridView = (StaggeredGridView) findViewById(R.id.grid_view);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                HashMap<String, Object> queryParams = new HashMap<String, Object>();
                queryParams.put("start", totalItemsCount);
                getEventBus().post(new TriggerAPIWithQueryParams(queryParams));
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject data = gridViewAdapter.getItem(position);
                showImageDetail(data);
            }
        });

    }

    private void initData() {
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        // When ready, show the indeterminate progress bar
        setSupportProgressBarIndeterminateVisibility(true);
        getEventBus().post(new ClearAdapter());
        getEventBus().post(new TriggerAPIWithQueryParams(queryParams));
    }


    @Override
    public Context getContext() {
        return this;
    }

    public void onEventMainThread(ClearAdapter clearAdapter) {
        clearAdapter();
    }

    public void onEventMainThread(TriggerAPIWithQueryParams triggerAPIWithQueryParams) {
        if (sizeFilter == null) {
            sizeFilter = new JSONObject();
            try {
                sizeFilter.put("icon", R.drawable.size);
                sizeFilter.put("name", "Size");
                sizeFilter.put("isSmall", false);
                sizeFilter.put("isMedium", false);
                sizeFilter.put("isLarge", false);
                sizeFilter.put("isExtremeLarge", false);
                sizeFilter.put("childView", R.layout.size_setting);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (colorFilter == null) {
            colorFilter = new JSONObject();
            try {
                colorFilter.put("icon", R.drawable.color);
                colorFilter.put("name", "Color");
                colorFilter.put("color", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        triggerAPIWithQueryParams.queryParams.put("q", getCurrentQueryString());
        triggerAPIWithQueryParams.queryParams.put("imgsz", Utils.getImagzStringParamsOfGoogleImageAPI(sizeFilter));
        try {
            triggerAPIWithQueryParams.queryParams.put("imgcolor", colorFilter.getString("color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("TriggerAPIWithQueryParams", triggerAPIWithQueryParams.queryParams.toString());
        new API().getGoogleImageSearch(MainActivity.this, triggerAPIWithQueryParams.queryParams);
    }

    @Override
    public void onEventMainThread(API.ReturnDataEvent dma) {
        // When ready, show the indeterminate progress bar
        setSupportProgressBarIndeterminateVisibility(false);
        try {
            JSONObject responseData = dma.data.getJSONObject("responseData");
            if (responseData != null) {
                JSONArray results = dma.data.getJSONObject("responseData").getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    gridViewAdapter.addItem(item);
                }
                gridViewAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                setSupportProgressBarIndeterminateVisibility(true);
                searchItem.collapseActionView();
                setCurrentQueryString(s);
                HashMap<String, Object> queryParams = new HashMap<String, Object>();
                // When ready, show the indeterminate progress bar
                getEventBus().post(new ClearAdapter());
                getEventBus().post(new TriggerAPIWithQueryParams(queryParams));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("onQueryTextChange", s);
                return false;
            }

        });
        View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundResource(R.color.searchtext_background);
            TextView searchText = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            if (searchText != null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }
        return true;
    }

    private void clearAdapter() {
        gridViewAdapter.deleteAll();
        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.setting) {
            showSettingDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImageDetail(JSONObject data) {
        FragmentManager fm = getSupportFragmentManager();
        ImageFragment imageFragment = ImageFragment.newInstance(data);
        imageFragment.show(fm, "detail");
    }

    private void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment alertDialog = FilterDialogFragment.newInstance(MainActivity.this, sizeFilter, colorFilter);
        alertDialog.show(fm, "filter");
    }

    public static class TriggerAPIWithQueryParams {
        public HashMap<String, Object> queryParams;

        public TriggerAPIWithQueryParams(HashMap<String, Object> queryParams) {
            this.queryParams = queryParams;
        }
    }

    /**
     * Created by jonaswu on 2015/8/3.
     */
    public static class ClearAdapter {
    }
}

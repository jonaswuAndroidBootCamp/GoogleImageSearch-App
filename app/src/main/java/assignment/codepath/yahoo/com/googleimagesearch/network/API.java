package assignment.codepath.yahoo.com.googleimagesearch.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import assignment.codepath.yahoo.com.googleimagesearch.helpers.BaseAsyncTask;
import de.greenrobot.event.EventBus;

/**
 * Created by jonaswu on 2015/7/28.
 */
public class API {


    public String identifier;

    public API(String identifier) {
        this.identifier = identifier;
    }

    public API() {
        this.identifier = "";
    }

    public interface DataHandler {
        public void onEventMainThread(API.ReturnDataEvent dma);
    }

    public void getGoogleImageSearch(APIDelegator apiDelegator) {
        getGoogleImageSearch(apiDelegator, new HashMap<String, Object>());
    }

    public void getGoogleImageSearch(APIDelegator apiDelegator, HashMap<String, Object> queryParams) {
        EventBus eventBus = apiDelegator.getEventBus();
        Context ctx = apiDelegator.getContext();
        if (queryParams.get("q") == null) {
            queryParams.put("q", "minion");
        }
        if (queryParams.get("v") == null) {
            queryParams.put("v", "1.0");
        }
        if (queryParams.get("rsz") == null) {
            queryParams.put("rsz", "8");
        }
        try {
            BaseAsyncTask task = new getDataUrlParams(ctx, "ajax/services/search/images", this.identifier, queryParams, eventBus);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class getDataUrlParams extends BaseAsyncTask {

        private final String identifier;
        private String path;
        private HashMap<String, Object> data;

        public getDataUrlParams(Context context, String path, String identifier, HashMap<String, Object> data, EventBus localBus) {
            super(context, localBus);
            this.path = path;
            this.data = data;
            this.identifier = identifier;
        }


        @Override
        public void my_doInBackground(Object... params) {
            try {
                ReturnDataEvent returnDataEvent;
                returnDataEvent = new ReturnDataEvent(WebClient.basicHTTPGet(context, data, this.path, localBus), this.identifier);
                Log.e("pre receive data", returnDataEvent.data.toString());
                localBus.post(returnDataEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static class ReturnDataEvent {
        public JSONObject data;
        public String identifier;

        public ReturnDataEvent(JSONObject data, String identifier) {
            this.data = data;
            this.identifier = identifier;
        }
    }

    /**
     * Created by jonaswu on 2015/7/28.
     */
    public static interface APIDelegator {
        public EventBus getEventBus();

        public Context getContext();
    }
}

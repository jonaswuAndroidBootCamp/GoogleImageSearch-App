package assignment.codepath.yahoo.com.googleimagesearch.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import assignment.codepath.yahoo.com.googleimagesearch.network.API;
import de.greenrobot.event.EventBus;


/**
 * Created by jonaswu on 2015/7/28.
 */
public abstract class BaseFragmentActivity extends ActionBarActivity implements API.APIDelegator, API.DataHandler {

    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventBus = new EventBus();
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        eventBus.register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

}

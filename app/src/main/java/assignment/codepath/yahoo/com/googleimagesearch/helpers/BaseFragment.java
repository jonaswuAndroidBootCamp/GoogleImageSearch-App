package assignment.codepath.yahoo.com.googleimagesearch.helpers;

import android.app.Fragment;
import android.os.Bundle;

import assignment.codepath.yahoo.com.googleimagesearch.network.API;
import de.greenrobot.event.EventBus;

/**
 * Created by jonaswu on 2015/7/28.
 */
public abstract class BaseFragment extends Fragment implements API.APIDelegator, API.DataHandler {
    private EventBus eventBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        eventBus = new EventBus();
        super.onCreate(savedInstanceState);
    }


    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void onResume() {

        eventBus.register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }
}

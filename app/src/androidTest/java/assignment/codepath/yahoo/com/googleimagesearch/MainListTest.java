package assignment.codepath.yahoo.com.googleimagesearch;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.ListView;

import com.etsy.android.grid.StaggeredGridView;
import com.robotium.solo.Solo;

/**
 * Created by jonaswu on 2015/7/28.
 */
public class MainListTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    String TAG = "functional test";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "assignment.codepath.yahoo.com.googleimagesearch.activity.MainActivity";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public MainListTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRunAndNoCrash() {
        solo.waitForActivity("MainActivity", 2000);
        solo.clickInList(0);
        solo.goBack();
        solo.clickInList(1);
        solo.goBack();
        solo.scrollDown();
        solo.waitForActivity("MainActivity", 2000);
    }

    public void testListViewShouldHaveItems() {
        solo.waitForActivity("MainActivity", 2000);
        solo.scrollDown();
        solo.scrollDown();
        solo.clickOnActionBarItem(R.id.menu_search);
        StaggeredGridView view = (StaggeredGridView) solo.getCurrentActivity().findViewById(R.id.grid_view);
        assertTrue(view.getAdapter().getCount() > 0);
        solo.waitForActivity("MainActivity", 2000);
    }


    public void testListViewShouldAbleToClickSearchBarToSearch() {
        solo.waitForActivity("MainActivity", 2000);
        solo.scrollDown();
        solo.scrollDown();
        // solo.clickOnText("Wiki");
        // solo.clickOnActionBarItem(R.id.menu_search);
        solo.clickOnView(solo.getView(R.id.menu_search));
        StaggeredGridView view = (StaggeredGridView) solo.getCurrentActivity().findViewById(R.id.grid_view);
        String text = "Yahoo";
        char[] ch_array = text.toCharArray();
        for (int i = 0; i < text.toCharArray().length; i++) {
            solo.sendKey(fun_get_android_keycode(ch_array[i]));
        }
        solo.sendKey(Solo.ENTER);
        solo.sleep(3000);
        solo.waitForActivity("MainActivity", 2000);

        solo.clickOnText("Yahoo");
        solo.goBack();
        solo.waitForActivity("MainActivity", 2000);
    }

    public int fun_get_android_keycode(char ch) {
        int keycode = ch;//String.valueOf(ch).codePointAt(0);
        Log.v(TAG, "in fun : " + ch + " : " + keycode + "");

        if (keycode >= 97 && keycode <= 122) {
            Log.v(TAG, "atoz : " + ch + " : " + keycode + " : " + (keycode - 68));
            return keycode - 68;
        } else if (keycode >= 65 && keycode <= 90) {
            Log.v(TAG, "atoz : " + ch + " : " + keycode + " : " + (keycode - 36));
            return keycode - 36;
        } else if (keycode >= 48 && keycode <= 57) {
            Log.v(TAG, "0to9" + ch + " : " + keycode + " : " + (keycode - 41));
            return keycode - 41;
        } else if (keycode == 64) {
            Log.v(TAG, "@" + ch + " : " + keycode + " : " + "77");
            return KeyEvent.KEYCODE_AT;
        } else if (ch == '.') {
            Log.v(TAG, "DOT " + ch + " : " + keycode + " : " + "158");
            return KeyEvent.KEYCODE_PERIOD;
        } else if (ch == ',') {
            Log.v(TAG, "comma " + ch + " : " + keycode + " : " + "55");
            return KeyEvent.KEYCODE_COMMA;
        }
        return 62;
    }
}

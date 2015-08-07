package assignment.codepath.yahoo.com.googleimagesearch.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;

import assignment.codepath.yahoo.com.googleimagesearch.R;
import assignment.codepath.yahoo.com.googleimagesearch.activity.BaseActivity;
import assignment.codepath.yahoo.com.googleimagesearch.activity.MainActivity;
import assignment.codepath.yahoo.com.googleimagesearch.adapter.FilterAdapter;

/**
 * Created by jonaswu on 2015/8/2.
 */
@SuppressLint("ValidFragment")
public class FilterDialogFragment extends DialogFragment {


    public BaseActivity getContext() {
        return context;
    }

    public void setContext(BaseActivity context) {
        this.context = context;
    }

    private BaseActivity context;

    public FilterDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static FilterDialogFragment newInstance(BaseActivity context) {
        FilterDialogFragment frag = new FilterDialogFragment();
        frag.setContext(context);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.layout_filter, null);

        // set content
        ExpandableListView eplv = (ExpandableListView) view.findViewById(R.id.eplv);
        final FilterAdapter filterAdapter = new FilterAdapter(context);

        eplv.setAdapter((ExpandableListAdapter) filterAdapter);

        for (int i = 0; i < filterAdapter.getGroupCount(); i++) // expand all by default
            eplv.expandGroup(i);

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                getContext().getEventBus().post(new MainActivity.ClearAdapter());
                getContext().getEventBus().post(new MainActivity.TriggerAPIWithQueryParams(new HashMap<String, Object>()));
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}

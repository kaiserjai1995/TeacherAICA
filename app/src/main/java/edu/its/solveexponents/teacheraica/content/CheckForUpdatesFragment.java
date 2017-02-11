package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.its.solveexponents.teacheraica.R;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 7/6/16.
 */

public class CheckForUpdatesFragment extends Fragment {

    public CheckForUpdatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_for_updates, container, false);

        FancyButton check_for_updates_btn = (FancyButton) rootView.findViewById(R.id.check_for_updates_btn);

        check_for_updates_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=edu.its.solveexponents.teacheraica"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

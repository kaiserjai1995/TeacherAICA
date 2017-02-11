package edu.its.solveexponents.teacheraica.content.lectures;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.content.LecturesFragment;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 8/1/16.
 */

public class LectureMultiplicationOfExponentsToFindThePowerOfAPowerFragment extends Fragment {

    public LectureMultiplicationOfExponentsToFindThePowerOfAPowerFragment() {
        // Required empty public constructor
    }

    WebView webvw_1, webvw_2;
    FancyButton lecture_next_1, lecture_next_2;
    RelativeLayout lecture_interaction_1, lecture_interaction_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lecture_multiplication_of_exponents_to_find_the_power_of_a_power, container, false);
        getActivity().setTitle("Multiplication of Exponents to Find the Power of a Power");

        webvw_1 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_exponents_to_find_the_power_of_a_power_1);
        webvw_2 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_exponents_to_find_the_power_of_a_power_2);

        lecture_next_1 = (FancyButton) rootView.findViewById(R.id.lecture_next_1);
        lecture_next_2 = (FancyButton) rootView.findViewById(R.id.lecture_next_2);

        lecture_interaction_1 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_1);
        lecture_interaction_2 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_2);

        webvw_1.getSettings().setJavaScriptEnabled(true);
        webvw_1.getSettings().setDomStorageEnabled(true);
        webvw_1.setWebViewClient(new WebViewClient());
        webvw_1.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_exponents_to_find_the_power_of_a_power/multiplication_of_exponents_to_find_the_power_of_a_power_1.html").toString());
        webvw_1.setBackgroundColor(0x00000000);

        lecture_next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecture_interaction_2.setVisibility(View.VISIBLE);
                lecture_next_1.setVisibility(View.GONE);

                webvw_2.getSettings().setJavaScriptEnabled(true);
                webvw_2.getSettings().setDomStorageEnabled(true);
                webvw_2.setWebViewClient(new WebViewClient());
                webvw_2.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_exponents_to_find_the_power_of_a_power/multiplication_of_exponents_to_find_the_power_of_a_power_2.html").toString());
                webvw_2.setBackgroundColor(0x00000000);
            }
        });

        lecture_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass;

                fragmentClass = LecturesFragment.class;

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
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

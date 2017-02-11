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

public class LectureMultiplicationOfBasesWithTheSameExponentsFragment extends Fragment {

    public LectureMultiplicationOfBasesWithTheSameExponentsFragment() {
        // Required empty public constructor
    }

    WebView webvw_1, webvw_2, webvw_3, webvw_4;
    FancyButton lecture_next_1, lecture_next_2, lecture_next_3, lecture_next_4;
    RelativeLayout lecture_interaction_1, lecture_interaction_2, lecture_interaction_3, lecture_interaction_4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lecture_multiplication_of_bases_with_the_same_exponents, container, false);
        getActivity().setTitle("Multiplication of Bases with the Same Exponents");

        webvw_1 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_bases_with_the_same_exponents_1);
        webvw_2 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_bases_with_the_same_exponents_2);
        webvw_3 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_bases_with_the_same_exponents_3);
        webvw_4 = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_bases_with_the_same_exponents_4);

        lecture_next_1 = (FancyButton) rootView.findViewById(R.id.lecture_next_1);
        lecture_next_2 = (FancyButton) rootView.findViewById(R.id.lecture_next_2);
        lecture_next_3 = (FancyButton) rootView.findViewById(R.id.lecture_next_3);
        lecture_next_4 = (FancyButton) rootView.findViewById(R.id.lecture_next_4);

        lecture_interaction_1 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_1);
        lecture_interaction_2 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_2);
        lecture_interaction_3 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_3);
        lecture_interaction_4 = (RelativeLayout) rootView.findViewById(R.id.lecture_interaction_4);

        webvw_1.getSettings().setJavaScriptEnabled(true);
        webvw_1.getSettings().setDomStorageEnabled(true);
        webvw_1.setWebViewClient(new WebViewClient());
        webvw_1.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_bases_with_the_same_exponents/multiplication_of_bases_with_the_same_exponents_1.html").toString());
        webvw_1.setBackgroundColor(0x00000000);

        lecture_next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecture_interaction_2.setVisibility(View.VISIBLE);
                lecture_next_1.setVisibility(View.GONE);

                webvw_2.getSettings().setJavaScriptEnabled(true);
                webvw_2.getSettings().setDomStorageEnabled(true);
                webvw_2.setWebViewClient(new WebViewClient());
                webvw_2.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_bases_with_the_same_exponents/multiplication_of_bases_with_the_same_exponents_2.html").toString());
                webvw_2.setBackgroundColor(0x00000000);
            }
        });

        lecture_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecture_interaction_3.setVisibility(View.VISIBLE);
                lecture_next_2.setVisibility(View.GONE);

                webvw_3.getSettings().setJavaScriptEnabled(true);
                webvw_3.getSettings().setDomStorageEnabled(true);
                webvw_3.setWebViewClient(new WebViewClient());
                webvw_3.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_bases_with_the_same_exponents/multiplication_of_bases_with_the_same_exponents_3.html").toString());
                webvw_3.setBackgroundColor(0x00000000);
            }
        });

        lecture_next_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecture_interaction_4.setVisibility(View.VISIBLE);
                lecture_next_3.setVisibility(View.GONE);

                webvw_4.getSettings().setJavaScriptEnabled(true);
                webvw_4.getSettings().setDomStorageEnabled(true);
                webvw_4.setWebViewClient(new WebViewClient());
                webvw_4.loadUrl(Uri.parse("file:///android_asset/lectures/multiplication_of_bases_with_the_same_exponents/multiplication_of_bases_with_the_same_exponents_4.html").toString());
                webvw_4.setBackgroundColor(0x00000000);
            }
        });

        lecture_next_4.setOnClickListener(new View.OnClickListener() {
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

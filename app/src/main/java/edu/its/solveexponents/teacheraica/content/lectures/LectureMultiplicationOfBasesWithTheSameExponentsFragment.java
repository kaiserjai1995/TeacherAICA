package edu.its.solveexponents.teacheraica.content.lectures;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.its.solveexponents.teacheraica.R;

/**
 * Created by jairus on 8/1/16.
 */

public class LectureMultiplicationOfBasesWithTheSameExponentsFragment extends Fragment {

    public LectureMultiplicationOfBasesWithTheSameExponentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lecture_multiplication_of_bases_with_the_same_exponents, container, false);

        WebView webvw = (WebView) rootView.findViewById(R.id.webvw_lecture_multiplication_of_bases_with_the_same_exponents);

        webvw.getSettings().setJavaScriptEnabled(true);
        webvw.getSettings().setDomStorageEnabled(true);

        webvw.setWebViewClient(new WebViewClient());

        String path = Uri.parse("file:///android_asset/lectures/multiplication_of_bases_with_the_same_exponents.html").toString();
        webvw.loadUrl(path);
        webvw.setBackgroundColor(0x00000000);

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

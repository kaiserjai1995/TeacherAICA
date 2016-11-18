package edu.its.solveexponents.teacheraica.content.user_manuals;

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
 * Created by jairus on 7/11/16.
 */

public class SolveExponentsManualFragment extends Fragment {

    public SolveExponentsManualFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_solve_exponents_manual, container, false);

        WebView webvw = (WebView) rootView.findViewById(R.id.webvw_solve_exponents_manual);

        webvw.getSettings().setJavaScriptEnabled(true);
        webvw.getSettings().setDomStorageEnabled(true);

        webvw.setWebViewClient(new WebViewClient());

        String path = Uri.parse("file:///android_asset/user_manual/solve_exponents_manual.html").toString();
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

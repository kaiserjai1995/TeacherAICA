package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.File;

import edu.its.solveexponents.teacheraica.R;

/**
 * Created by jairus on 7/6/16.
 */

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "/teacheraica");

        if (!fileDir.exists()) fileDir.mkdir();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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

    @Override
    public void onStart() {
        super.onStart();

        new LovelyInfoDialog(getActivity())
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(R.drawable.aica)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                .setNotShowAgainOptionEnabled(0)
                .setTitle(R.string.tip_of_the_day_title)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setCancelable(false)
                .setMessage("In solving problems pertaining to the Laws of Exponents, it is always a good idea to write them legibly in paper first then analyze from there.")
                .show();
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }
}

package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.File;
import java.util.Random;

import edu.its.solveexponents.teacheraica.R;

/**
 * Created by jairus on 7/6/16.
 */

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    String[] tips_list = {"In solving problems pertaining to the Laws of Exponents, it is always a good idea to write them legibly in paper first then analyze from there.",
            "When you multiply exponential expressions that have the same base, you simply add the exponents. This is true for both numbers and variables.",
            "Always practice multiplication of numbers most importantly, the repetition of numbers to facilitate your way to solving problems related to exponents."};
    Random random_tips = new Random();

    LovelyInfoDialog tip_of_the_day_dialog;

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

        FloatingActionButton allow_tip_of_the_day = (FloatingActionButton) rootView.findViewById(R.id.allow_tip_of_the_day);
        allow_tip_of_the_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip_of_the_day(random_tips.nextInt(tips_list.length));
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

    @Override
    public void onStart() {
        super.onStart();

        tip_of_the_day(random_tips.nextInt(tips_list.length));
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }

    public void tip_of_the_day(int random) {
        tip_of_the_day_dialog = new LovelyInfoDialog(getActivity());

        tip_of_the_day_dialog
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(R.drawable.aica)
                .setTitle(R.string.tip_of_the_day_title)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setCancelable(false)
                .setMessage(tips_list[random])
                .show();
    }

}

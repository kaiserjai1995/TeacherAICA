package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.File;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.TeacherAICADB;

/**
 * Created by jairus on 7/6/16.
 */

public class MainFragment extends Fragment {

    public static TeacherAICADB teacheraicadb;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "/teacheraica");

        if (!fileDir.exists()) fileDir.mkdir();

        try {
            teacheraicadb = TeacherAICADB.getInstance(getContext());
        } catch (Exception e) {
            MainFragment.teacheraicadb.logSystemError("TEACHERAICADB \n" + e.toString());

            new AlertDialog.Builder(getContext())
                    .setTitle("Oooops!")
                    .setMessage("Something went wrong. We will fix this as soon as possible.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

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

        new LovelyCustomDialog(getActivity())
                .setTopColorRes(R.color.colorPrimaryDark)
                .setTitle(R.string.tip_of_the_day_title)
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setMessage("In solving problems pertaining to the Laws of Exponents, it is always a good idea to write them legibly in paper first then analyze from there.")
                .show();
    }
}

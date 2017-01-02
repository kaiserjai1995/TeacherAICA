package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.adapter.UserManualRVAdapter;
import edu.its.solveexponents.teacheraica.model.ManualTopic;

/**
 * Created by jairus on 7/6/16.
 */

public class UserManualFragment extends Fragment {

    private List<ManualTopic> manual_topic;
    private RecyclerView rv_user_manual;

    public UserManualFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_manual, container, false);

        rv_user_manual = (RecyclerView) rootView.findViewById(R.id.rv_user_manual);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_user_manual.setLayoutManager(llm);
        rv_user_manual.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializeData() {
        manual_topic = new ArrayList<>();
        manual_topic.add(new ManualTopic(getString(R.string.solve_exponents_user_manual_title),
                getString(R.string.solve_exponents_user_manual_desc),
                R.drawable.ic_solve_exponents));
        manual_topic.add(new ManualTopic(getString(R.string.reading_materials_user_manual_title),
                getString(R.string.reading_materials_user_manual_desc),
                R.drawable.ic_reading_materials));
        manual_topic.add(new ManualTopic(getString(R.string.lectures_user_manual_title),
                getString(R.string.lectures_user_manual_desc),
                R.drawable.ic_lectures));
    }

    private void initializeAdapter() {
        UserManualRVAdapter adapter = new UserManualRVAdapter(getContext(), manual_topic);
        rv_user_manual.setAdapter(adapter);
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


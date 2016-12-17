package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.adapter.ChoosingOfModeRVAdapter;
import edu.its.solveexponents.teacheraica.algo.Randomizer;
import edu.its.solveexponents.teacheraica.model.ModeInput;

/**
 * Created by jairus on 7/6/16.
 */

public class ChoosingOfModeFragment extends Fragment {

    private List<ModeInput> mode_input;
    private RecyclerView rv_mode_input;

    public ChoosingOfModeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choosing_of_mode, container, false);

        rv_mode_input = (RecyclerView) rootView.findViewById(R.id.rv_choosing_of_mode);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_mode_input.setLayoutManager(llm);
        rv_mode_input.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializeData() {
        mode_input = new ArrayList<>();
        mode_input.add(new ModeInput(getString(R.string.mode_input_one_title)));
        mode_input.add(new ModeInput(getString(R.string.mode_input_two_title)));
    }

    private void initializeAdapter(){
        ChoosingOfModeRVAdapter adapter = new ChoosingOfModeRVAdapter(getContext(), mode_input);
        rv_mode_input.setAdapter(adapter);
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

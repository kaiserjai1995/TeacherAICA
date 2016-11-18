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
import edu.its.solveexponents.teacheraica.adapter.ReadingMaterialsRVAdapter;
import edu.its.solveexponents.teacheraica.model.ReadingMaterialsTopic;

/**
 * Created by jairus on 7/6/16.
 */

public class ReadingMaterialsFragment extends Fragment {

    private List<ReadingMaterialsTopic> reading_materials_topic;
    private RecyclerView rv_reading_materials;

    public ReadingMaterialsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reading_materials, container, false);

        rv_reading_materials = (RecyclerView) rootView.findViewById(R.id.rv_reading_materials);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_reading_materials.setLayoutManager(llm);
        rv_reading_materials.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializeData(){
        reading_materials_topic = new ArrayList<>();
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_one_title),
                getString(R.string.reading_materials_one_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_two_title),
                getString(R.string.reading_materials_two_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_three_title),
                getString(R.string.reading_materials_three_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_four_title),
                getString(R.string.reading_materials_four_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_five_title),
                getString(R.string.reading_materials_five_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_six_title),
                getString(R.string.reading_materials_six_desc),
                R.drawable.ic_reading_materials));
        reading_materials_topic.add(new ReadingMaterialsTopic(getString(R.string.reading_materials_seven_title),
                getString(R.string.reading_materials_seven_desc),
                R.drawable.ic_reading_materials));
    }

    private void initializeAdapter(){
        ReadingMaterialsRVAdapter adapter = new ReadingMaterialsRVAdapter(getContext(), reading_materials_topic);
        rv_reading_materials.setAdapter(adapter);
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

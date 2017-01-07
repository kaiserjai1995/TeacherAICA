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
import edu.its.solveexponents.teacheraica.adapter.LecturesRVAdapter;
import edu.its.solveexponents.teacheraica.model.LecturesTopic;

/**
 * Created by jairus on 7/6/16.
 */

public class LecturesFragment extends Fragment {

    private List<LecturesTopic> lectures_topic;
    private RecyclerView rv_lectures;

    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);

        rv_lectures = (RecyclerView) rootView.findViewById(R.id.rv_lectures);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_lectures.setLayoutManager(llm);
        rv_lectures.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        // Inflate the layout for this fragment
        return rootView;
    }

    public void initializeData() {
        lectures_topic = new ArrayList<>();
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_introduction_title),
                getString(R.string.lecture_introduction_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_base_raised_to_zero_title),
                getString(R.string.lecture_base_raised_to_zero_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_addition_of_exponents_with_the_same_bases_title),
                getString(R.string.lecture_addition_of_exponents_with_the_same_bases_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_multiplication_of_bases_with_the_same_exponents_title),
                getString(R.string.lecture_multiplication_of_bases_with_the_same_exponents_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_multiplication_of_exponents_to_find_the_power_of_a_power_title),
                getString(R.string.lecture_multiplication_of_exponents_to_find_the_power_of_a_power_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_subtraction_of_exponents_title),
                getString(R.string.lecture_subtraction_of_exponents_desc),
                R.drawable.ic_lectures));
        lectures_topic.add(new LecturesTopic(getString(R.string.lecture_negative_integer_exponents_title),
                getString(R.string.lecture_negative_integer_exponents_desc),
                R.drawable.ic_lectures));
    }

    public void initializeAdapter() {
        LecturesRVAdapter adapter = new LecturesRVAdapter(getContext(), lectures_topic);
        rv_lectures.setAdapter(adapter);
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

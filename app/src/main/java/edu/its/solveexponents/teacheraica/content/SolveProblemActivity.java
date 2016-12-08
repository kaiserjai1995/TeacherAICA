package edu.its.solveexponents.teacheraica.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;
import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.adapter.SolveProblemRVAdapter;
import edu.its.solveexponents.teacheraica.model.SolveProblemItems;

/**
 * Created by jairus on 12/8/16.
 */

public class SolveProblemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private List<SolveProblemItems> solve_problem_items;
    private RecyclerView rv_solve_problem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_problem);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv_solve_problem = (RecyclerView) findViewById(R.id.rv_solve_problem);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv_solve_problem.setLayoutManager(llm);
        rv_solve_problem.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

    }

    public void initializeData(){
        solve_problem_items = new ArrayList<>();
        solve_problem_items.add(new SolveProblemItems(getString(R.string.solve_problem_problem_title),
                getString(R.string.solve_problem_equation),
                R.drawable.question));
    }

    public void initializeAdapter(){
        SolveProblemRVAdapter adapter = new SolveProblemRVAdapter(getApplicationContext(), solve_problem_items);
        rv_solve_problem.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        new LovelyCustomDialog(this)
                .setView(R.layout.abort_problem_view)
                .setTopColorRes(R.color.darkDeepOrange)
                .setTitle(R.string.abort_problem_title)
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setCancelable(false)
                .setListener(R.id.abort_problem_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                })
                .setListener(R.id.resume_problem_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();

    }
}

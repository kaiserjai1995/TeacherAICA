package edu.its.solveexponents.teacheraica.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import org.w3c.dom.Text;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.algo.Randomizer;

/**
 * Created by jairus on 12/8/16.
 */

public class SolveProblemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int level;
    private int sublevel;
    private String equation;
    private String equationType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_problem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView solve_problem_equation = (TextView)findViewById(R.id.solve_problem_equation);

        if(getIntent().getExtras().getBoolean("generated")) {
            level = getIntent().getExtras().getInt("level");
            sublevel = getIntent().getExtras().getInt("sublevel");
            equation = getIntent().getExtras().getString("equation");

            Log.d("TEACHERAICADB", "LEVEL: " + level + "_" + sublevel);

            solve_problem_equation.setText(equation);

            this.equationType = "generated";
        } else {
            this.equationType = "custom";
        }

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

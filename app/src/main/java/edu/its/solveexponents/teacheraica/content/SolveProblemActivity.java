package edu.its.solveexponents.teacheraica.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    private int errorsCommited;
    boolean solved;

    //FOR HINTING
    int solutionNumber;
    int hintType;
    int stepNumber;

    public static View parentView;
    public static View solutionScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_problem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView solve_problem_equation = (TextView)findViewById(R.id.solve_problem_equation);

        equation = getIntent().getExtras().getString("equation");

        if(getIntent().getExtras().getBoolean("generated")) {
            level = getIntent().getExtras().getInt("level");
            sublevel = getIntent().getExtras().getInt("sublevel");

            Log.d("TEACHERAICADB", "LEVEL: " + level + "_" + sublevel);

            solve_problem_equation.setText(equation);

            this.equationType = "generated";
        } else {
            solve_problem_equation.setText(equation);
            this.equationType = "custom";
        }

        errorsCommited = 0;

        solutionNumber = 0;
        hintType = MainFragment.teacheraicadb.determineHintType();

        stepNumber = 0;

        this.solved = true;

        addTextChangeListenerToTextboxes();

        parentView = (View) findViewById(R.id.activity_layout_solve);
        solutionScrollView = (View) findViewById(R.id.scrollView_solve);
    }

    private void addTextChangeListenerToTextboxes() {
        ArrayList<EditText> stepsTextBoxes = new ArrayList<>();
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_1));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_2));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_3));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_4));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_5));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_6));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_7));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_8));
        stepsTextBoxes.add((EditText) findViewById(R.id.solution_step_9));

        for (EditText stepTextbox : stepsTextBoxes) {
            stepTextbox.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    EditText solution_step = null;

                    switch(stepNumber + 1) {
                        case 1:
                            solution_step = (EditText) findViewById(R.id.solution_step_1);
                            break;
                        case 2:
                            solution_step = (EditText) findViewById(R.id.solution_step_2);
                            break;
                        case 3:
                            solution_step = (EditText) findViewById(R.id.solution_step_3);
                            break;
                        case 4:
                            solution_step = (EditText) findViewById(R.id.solution_step_4);
                            break;
                        case 5:
                            solution_step = (EditText) findViewById(R.id.solution_step_5);
                            break;
                        case 6:
                            solution_step = (EditText) findViewById(R.id.solution_step_6);
                            break;
                        case 7:
                            solution_step = (EditText) findViewById(R.id.solution_step_7);
                            break;
                        case 8:
                            solution_step = (EditText) findViewById(R.id.solution_step_8);
                            break;
                        case 9:
                            solution_step = (EditText) findViewById(R.id.solution_step_9);
                            break;
                    }

                    String inputStep = solution_step.getText().toString();

//                    if (Validations.isInputIncomplete(inputStep)) {
//                        setAllButtonsToQuestion();
//                    } else {
//                        setAllButtonsToCheck();
//                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                }
            });
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

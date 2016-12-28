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

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;

import edu.its.solveexponents.teacheraica.R;
import io.github.kexanie.library.MathView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 12/8/16.
 */

public class SolveProblemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int level;
    private int sublevel;
    private String result;
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

    MathView math_equation;
    String equation_start = "\\(";
    String equation_end = "\\)";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_problem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MathView math_equation = (MathView) findViewById(R.id.math_equation);

        equation = getIntent().getExtras().getString("equation");

        if(getIntent().getExtras().getBoolean("generated")) {
            level = getIntent().getExtras().getInt("level");
            sublevel = getIntent().getExtras().getInt("sublevel");
            result = getIntent().getExtras().getString("result");

            Log.d("TEACHERAICADB", "LEVEL: " + level + "_" + sublevel);

            math_equation.setText(equation_start + equation + equation_end);

            this.equationType = "generated";
        } else {
            result = getIntent().getExtras().getString("result");
            math_equation.setText(equation_start + equation + equation_end);
            this.equationType = "custom";
        }

        FancyButton btn_one = (FancyButton)findViewById(R.id.btn_one);
        btn_one.setText("1");
        btn_one.setTextSize(20);

        FancyButton btn_two = (FancyButton)findViewById(R.id.btn_two);
        btn_two.setText("2");
        btn_two.setTextSize(20);

        FancyButton btn_three = (FancyButton)findViewById(R.id.btn_three);
        btn_three.setText("3");
        btn_three.setTextSize(20);

        FancyButton btn_four = (FancyButton)findViewById(R.id.btn_four);
        btn_four.setText("4");
        btn_four.setTextSize(20);

        FancyButton btn_five = (FancyButton)findViewById(R.id.btn_five);
        btn_five.setText("5");
        btn_five.setTextSize(20);

        FancyButton btn_six = (FancyButton)findViewById(R.id.btn_six);
        btn_six.setText("6");
        btn_six.setTextSize(20);

        FancyButton btn_seven = (FancyButton)findViewById(R.id.btn_seven);
        btn_seven.setText("7");
        btn_seven.setTextSize(20);

        FancyButton btn_eight = (FancyButton)findViewById(R.id.btn_eight);
        btn_eight.setText("8");
        btn_eight.setTextSize(20);

        FancyButton btn_nine = (FancyButton)findViewById(R.id.btn_nine);
        btn_nine.setText("9");
        btn_nine.setTextSize(20);

        FancyButton btn_zero = (FancyButton)findViewById(R.id.btn_zero);
        btn_zero.setText("0");
        btn_zero.setTextSize(20);

        FancyButton btn_left_shift = (FancyButton)findViewById(R.id.btn_left_shift);
        btn_left_shift.setText("←");
        btn_left_shift.setTextSize(20);

        FancyButton btn_right_shift = (FancyButton)findViewById(R.id.btn_right_shift);
        btn_right_shift.setText("→");
        btn_right_shift.setTextSize(20);

        FancyButton btn_add = (FancyButton)findViewById(R.id.btn_add);
        btn_add.setText("+");
        btn_add.setTextSize(20);

        FancyButton btn_subtract = (FancyButton)findViewById(R.id.btn_subtract);
        btn_subtract.setText("-");
        btn_subtract.setTextSize(20);

        FancyButton btn_multiply = (FancyButton)findViewById(R.id.btn_multiply);
        btn_multiply.setText("*");
        btn_multiply.setTextSize(20);

        FancyButton btn_divide = (FancyButton)findViewById(R.id.btn_divide);
        btn_divide.setText("/");
        btn_divide.setTextSize(20);

        FancyButton btn_power = (FancyButton)findViewById(R.id.btn_power);
        btn_power.setText("^");
        btn_power.setTextSize(20);

        FancyButton btn_decimal = (FancyButton)findViewById(R.id.btn_decimal);
        btn_decimal.setText(".");
        btn_decimal.setTextSize(20);

        FancyButton btn_open_parenthesis = (FancyButton)findViewById(R.id.btn_open_parenthesis);
        btn_open_parenthesis.setText("(");
        btn_open_parenthesis.setTextSize(20);

        FancyButton btn_closing_parenthesis = (FancyButton)findViewById(R.id.btn_closing_parenthesis);
        btn_closing_parenthesis.setText(")");
        btn_closing_parenthesis.setTextSize(20);

        FancyButton btn_open_brace = (FancyButton)findViewById(R.id.btn_open_brace);
        btn_open_brace.setText("{");
        btn_open_brace.setTextSize(20);

        FancyButton btn_closing_brace = (FancyButton)findViewById(R.id.btn_closing_brace);
        btn_closing_brace.setText("}");
        btn_closing_brace.setTextSize(20);

        FancyButton btn_backspace = (FancyButton)findViewById(R.id.btn_backspace);
        btn_backspace.setIconResource(R.drawable.sym_keyboard_delete);
        btn_backspace.setTextSize(20);

        FancyButton btn_clear = (FancyButton)findViewById(R.id.btn_clear);
        btn_clear.setText("C");
        btn_clear.setTextSize(20);

        FancyButton btn_var_w = (FancyButton)findViewById(R.id.btn_var_w);
        btn_var_w.setText("w");
        btn_var_w.setTextSize(20);

        FancyButton btn_var_x = (FancyButton)findViewById(R.id.btn_var_x);
        btn_var_x.setText("x");
        btn_var_x.setTextSize(20);

        FancyButton btn_var_y = (FancyButton)findViewById(R.id.btn_var_y);
        btn_var_y.setText("y");
        btn_var_y.setTextSize(20);

        FancyButton btn_var_z = (FancyButton)findViewById(R.id.btn_var_z);
        btn_var_z.setText("z");
        btn_var_z.setTextSize(20);

        FancyButton btn_var_a = (FancyButton)findViewById(R.id.btn_var_a);
        btn_var_a.setText("a");
        btn_var_a.setTextSize(20);

        FancyButton btn_var_b = (FancyButton)findViewById(R.id.btn_var_b);
        btn_var_b.setText("b");
        btn_var_b.setTextSize(20);

        FancyButton btn_var_c = (FancyButton)findViewById(R.id.btn_var_c);
        btn_var_c.setText("c");
        btn_var_c.setTextSize(20);

        FancyButton btn_var_d = (FancyButton)findViewById(R.id.btn_var_d);
        btn_var_d.setText("d");
        btn_var_d.setTextSize(20);

        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "1");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "2");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "3");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "4");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "5");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "6");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "7");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "8");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "9");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "0");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_left_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (input_problem.getSelectionStart() == 0) {
//                    input_problem.setSelection(0);
//                } else {
//                    input_problem.setSelection(input_problem.getSelectionEnd() - 1);
//                }
            }
        });

        btn_right_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (input_problem.getSelectionEnd() == input_problem.length()) {
//                    input_problem.setSelection(input_problem.length());
//                } else {
//                    input_problem.setSelection(input_problem.getSelectionEnd() + 1);
//                }
            }
        });

        btn_var_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "w");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "x");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "y");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "z");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "a");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "b");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "c");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_var_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "d");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "+");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "-");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "*");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "/");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "^");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), ".");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_open_parenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "(");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_closing_parenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), ")");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int cursorPosition = input_problem.getSelectionStart();
//                if (cursorPosition > 0) {
//                    input_problem.setText(input_problem.getText().delete(cursorPosition - 1, cursorPosition));
//                    input_problem.setSelection(cursorPosition-1);
//                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.setText("");
            }
        });

        btn_open_brace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "{");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });

        btn_closing_brace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                input_problem.getText().insert(input_problem.getSelectionStart(), "}");
//                input_problem.setSelection(input_problem.getSelectionStart());
            }
        });


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

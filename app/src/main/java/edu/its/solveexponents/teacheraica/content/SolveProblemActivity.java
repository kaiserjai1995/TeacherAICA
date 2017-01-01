package edu.its.solveexponents.teacheraica.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;

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
    private String final_answer;
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
    String equation_string;

    FancyButton btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine,
            btn_zero, btn_left_shift, btn_right_shift, btn_add, btn_subtract, btn_multiply, btn_divide, btn_power,
            btn_decimal, btn_open_parenthesis, btn_closing_parenthesis, btn_backspace, btn_clear, btn_open_brace,
            btn_closing_brace, btn_var_w, btn_var_x, btn_var_y, btn_var_z, btn_var_a, btn_var_b, btn_var_c,
            btn_var_d;
    FancyButton submit_solution_step_1, submit_solution_step_2, submit_solution_step_3, submit_solution_step_4,
            submit_solution_step_5, submit_solution_step_6, submit_solution_step_7, submit_solution_step_8,
            submit_solution_step_9;
    MaterialEditText solution_step_1, solution_step_2, solution_step_3, solution_step_4, solution_step_5,
            solution_step_6, solution_step_7, solution_step_8, solution_step_9;
    LinearLayout solution_step_1_view, solution_step_2_view, solution_step_3_view, solution_step_4_view,
            solution_step_5_view, solution_step_6_view, solution_step_7_view, solution_step_8_view,
            solution_step_9_view;
    ExprEvaluator util;
    IExpr current_result;
    String current_solution;
    String final_equation_string;

    private static class StepListener extends AbstractEvalStepListener {
        /**
         * Listens to the evaluation step in the evaluation engine.
         */
        @Override
        public void add(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            System.out.println("Depth " + recursionDepth + " Iteration " + iterationCounter + ": " + inputExpr.toString() + " ==> "
                    + resultExpr.toString() + " ==> " + hint);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_problem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        math_equation = (MathView) findViewById(R.id.math_equation);

        equation = getIntent().getExtras().getString("equation");

        equation_string = getIntent().getExtras().getString("equation_string");

        final_equation_string = equation_string.replace("<center>", "")
                .replace("</center>", "");

        if (getIntent().getExtras().getBoolean("generated")) {
            level = getIntent().getExtras().getInt("level");
            sublevel = getIntent().getExtras().getInt("sublevel");
            final_answer = getIntent().getExtras().getString("result");

            Log.d("TEACHERAICADB", "LEVEL: " + level + "_" + sublevel);

            math_equation.setText(final_equation_string);

            this.equationType = "generated";
        } else {
            final_answer = getIntent().getExtras().getString("result");
            math_equation.setText(final_equation_string);
            this.equationType = "custom";
        }

        Toast.makeText(getApplicationContext(), final_answer, Toast.LENGTH_LONG).show();

        submit_solution_step_1 = (FancyButton) findViewById(R.id.submit_solution_step_1);
        submit_solution_step_2 = (FancyButton) findViewById(R.id.submit_solution_step_2);
        submit_solution_step_3 = (FancyButton) findViewById(R.id.submit_solution_step_3);
        submit_solution_step_4 = (FancyButton) findViewById(R.id.submit_solution_step_4);
        submit_solution_step_5 = (FancyButton) findViewById(R.id.submit_solution_step_5);
        submit_solution_step_6 = (FancyButton) findViewById(R.id.submit_solution_step_6);
        submit_solution_step_7 = (FancyButton) findViewById(R.id.submit_solution_step_7);
        submit_solution_step_8 = (FancyButton) findViewById(R.id.submit_solution_step_8);
        submit_solution_step_9 = (FancyButton) findViewById(R.id.submit_solution_step_9);

        btn_one = (FancyButton) findViewById(R.id.btn_one);
        btn_two = (FancyButton) findViewById(R.id.btn_two);
        btn_three = (FancyButton) findViewById(R.id.btn_three);
        btn_four = (FancyButton) findViewById(R.id.btn_four);
        btn_five = (FancyButton) findViewById(R.id.btn_five);
        btn_six = (FancyButton) findViewById(R.id.btn_six);
        btn_seven = (FancyButton) findViewById(R.id.btn_seven);
        btn_eight = (FancyButton) findViewById(R.id.btn_eight);
        btn_nine = (FancyButton) findViewById(R.id.btn_nine);
        btn_zero = (FancyButton) findViewById(R.id.btn_zero);
        btn_left_shift = (FancyButton) findViewById(R.id.btn_left_shift);
        btn_right_shift = (FancyButton) findViewById(R.id.btn_right_shift);
        btn_add = (FancyButton) findViewById(R.id.btn_add);
        btn_subtract = (FancyButton) findViewById(R.id.btn_subtract);
        btn_multiply = (FancyButton) findViewById(R.id.btn_multiply);
        btn_divide = (FancyButton) findViewById(R.id.btn_divide);
        btn_power = (FancyButton) findViewById(R.id.btn_power);
        btn_decimal = (FancyButton) findViewById(R.id.btn_decimal);
        btn_open_parenthesis = (FancyButton) findViewById(R.id.btn_open_parenthesis);
        btn_closing_parenthesis = (FancyButton) findViewById(R.id.btn_closing_parenthesis);
        btn_open_brace = (FancyButton) findViewById(R.id.btn_open_brace);
        btn_closing_brace = (FancyButton) findViewById(R.id.btn_closing_brace);
        btn_backspace = (FancyButton) findViewById(R.id.btn_backspace);
        btn_clear = (FancyButton) findViewById(R.id.btn_clear);
        btn_var_w = (FancyButton) findViewById(R.id.btn_var_w);
        btn_var_x = (FancyButton) findViewById(R.id.btn_var_x);
        btn_var_y = (FancyButton) findViewById(R.id.btn_var_y);
        btn_var_z = (FancyButton) findViewById(R.id.btn_var_z);
        btn_var_a = (FancyButton) findViewById(R.id.btn_var_a);
        btn_var_b = (FancyButton) findViewById(R.id.btn_var_b);
        btn_var_c = (FancyButton) findViewById(R.id.btn_var_c);
        btn_var_d = (FancyButton) findViewById(R.id.btn_var_d);

        btn_one.setText("1");
        btn_one.setTextSize(20);
        btn_two.setText("2");
        btn_two.setTextSize(20);
        btn_three.setText("3");
        btn_three.setTextSize(20);
        btn_four.setText("4");
        btn_four.setTextSize(20);
        btn_five.setText("5");
        btn_five.setTextSize(20);
        btn_six.setText("6");
        btn_six.setTextSize(20);
        btn_seven.setText("7");
        btn_seven.setTextSize(20);
        btn_eight.setText("8");
        btn_eight.setTextSize(20);
        btn_nine.setText("9");
        btn_nine.setTextSize(20);
        btn_zero.setText("0");
        btn_zero.setTextSize(20);
        btn_left_shift.setText("←");
        btn_left_shift.setTextSize(20);
        btn_right_shift.setText("→");
        btn_right_shift.setTextSize(20);
        btn_add.setText("+");
        btn_add.setTextSize(20);
        btn_subtract.setText("-");
        btn_subtract.setTextSize(20);
        btn_multiply.setText("*");
        btn_multiply.setTextSize(20);
        btn_divide.setText("/");
        btn_divide.setTextSize(20);
        btn_power.setText("^");
        btn_power.setTextSize(20);
        btn_decimal.setText(".");
        btn_decimal.setTextSize(20);
        btn_open_parenthesis.setText("(");
        btn_open_parenthesis.setTextSize(20);
        btn_closing_parenthesis.setText(")");
        btn_closing_parenthesis.setTextSize(20);
        btn_open_brace.setText("{");
        btn_open_brace.setTextSize(20);
        btn_closing_brace.setText("}");
        btn_closing_brace.setTextSize(20);
        btn_backspace.setIconResource(R.drawable.sym_keyboard_delete);
        btn_backspace.setTextSize(20);
        btn_clear.setText("C");
        btn_clear.setTextSize(20);
        btn_var_w.setText("w");
        btn_var_w.setTextSize(20);
        btn_var_x.setText("x");
        btn_var_x.setTextSize(20);
        btn_var_y.setText("y");
        btn_var_y.setTextSize(20);
        btn_var_z.setText("z");
        btn_var_z.setTextSize(20);
        btn_var_a.setText("a");
        btn_var_a.setTextSize(20);
        btn_var_b.setText("b");
        btn_var_b.setTextSize(20);
        btn_var_c.setText("c");
        btn_var_c.setTextSize(20);
        btn_var_d.setText("d");
        btn_var_d.setTextSize(20);

        solution_step_1 = (MaterialEditText) findViewById(R.id.solution_step_1);
        solution_step_2 = (MaterialEditText) findViewById(R.id.solution_step_2);
        solution_step_3 = (MaterialEditText) findViewById(R.id.solution_step_3);
        solution_step_4 = (MaterialEditText) findViewById(R.id.solution_step_4);
        solution_step_5 = (MaterialEditText) findViewById(R.id.solution_step_5);
        solution_step_6 = (MaterialEditText) findViewById(R.id.solution_step_6);
        solution_step_7 = (MaterialEditText) findViewById(R.id.solution_step_7);
        solution_step_8 = (MaterialEditText) findViewById(R.id.solution_step_8);
        solution_step_9 = (MaterialEditText) findViewById(R.id.solution_step_9);

        solution_step_1_view = (LinearLayout) findViewById(R.id.solution_step_1_view);
        solution_step_2_view = (LinearLayout) findViewById(R.id.solution_step_2_view);
        solution_step_3_view = (LinearLayout) findViewById(R.id.solution_step_3_view);
        solution_step_4_view = (LinearLayout) findViewById(R.id.solution_step_4_view);
        solution_step_5_view = (LinearLayout) findViewById(R.id.solution_step_5_view);
        solution_step_6_view = (LinearLayout) findViewById(R.id.solution_step_6_view);
        solution_step_7_view = (LinearLayout) findViewById(R.id.solution_step_7_view);
        solution_step_8_view = (LinearLayout) findViewById(R.id.solution_step_8_view);
        solution_step_9_view = (LinearLayout) findViewById(R.id.solution_step_9_view);

        errorsCommited = 0;
        solutionNumber = 0;
        hintType = MainFragment.teacheraicadb.determineHintType();
        stepNumber = 0;

        solution_step_1.requestFocus();

        focus_settings();

        submit_solution_step_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_solution = solution_step_1.getText().toString();

                Toast.makeText(getApplicationContext(), final_answer, Toast.LENGTH_LONG).show();

                if (!current_solution.isEmpty()) {
                    if (errorsCommited == 3) {
                        //POPUP FOR HINT
                    } else if (errorsCommited == 5) {
                        //POPUP FOR ANSWERING PROBLEM
                    }

                    util = new ExprEvaluator();
                    current_result = util.evaluate(current_solution);

                    if (current_solution.equals(final_answer) || current_solution.equals(final_answer.replace("*", ""))) {

                        Toast.makeText(getApplicationContext(), "Final Answer has been reached!", Toast.LENGTH_LONG).show();
                        solved = true;

                        if(equationType.equals("generated")) {
                            next_problem_prompt();
                        } else if (equationType.equals("custom")) {
                            next_problem_prompt();
                        } // Add for quizzes coming from Lecture
                    } else if (current_result.toString().equals(final_answer)) {
                        Toast.makeText(getApplicationContext(), "Correct Solution!", Toast.LENGTH_LONG).show();
                        solution_step_1.setEnabled(false);
                        solution_step_2_view.setVisibility(View.VISIBLE);
                        submit_solution_step_1.setGhost(true);
                        submit_solution_step_1.setEnabled(false);
                        solution_step_2.requestFocus();
                        focus_settings();
                    } else {
                        errorsCommited += 1;
                        solution_step_1.setError("Wrong solution!");
                    }

//                    else if (current_solution != result.toString()) {
//                        Toast.makeText(getApplicationContext(), "Wrong solution!", Toast.LENGTH_LONG).show();
//                    } else {
//                        solution_step_1.setEnabled(false);
//                        solution_step_2_view.setVisibility(View.VISIBLE);
//                        submit_solution_step_1.setGhost(true);
//                        submit_solution_step_1.setEnabled(false);
//                        solution_step_2.requestFocus();
//                        focus_settings();
//                    }

                } else {
                    solution_step_1.setError("Wrong solution!");
                }

            }

        });

        submit_solution_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_2.setEnabled(false);
                solution_step_3_view.setVisibility(View.VISIBLE);
                submit_solution_step_2.setGhost(true);
                submit_solution_step_2.setEnabled(false);
                solution_step_3.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_3.setEnabled(false);
                solution_step_4_view.setVisibility(View.VISIBLE);
                submit_solution_step_3.setGhost(true);
                submit_solution_step_3.setEnabled(false);
                solution_step_4.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_4.setEnabled(false);
                solution_step_5_view.setVisibility(View.VISIBLE);
                submit_solution_step_4.setGhost(true);
                submit_solution_step_4.setEnabled(false);
                solution_step_5.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_5.setEnabled(false);
                solution_step_6_view.setVisibility(View.VISIBLE);
                submit_solution_step_5.setGhost(true);
                submit_solution_step_5.setEnabled(false);
                solution_step_6.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_6.setEnabled(false);
                solution_step_7_view.setVisibility(View.VISIBLE);
                submit_solution_step_6.setGhost(true);
                submit_solution_step_6.setEnabled(false);
                solution_step_7.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_7.setEnabled(false);
                solution_step_8_view.setVisibility(View.VISIBLE);
                submit_solution_step_7.setGhost(true);
                submit_solution_step_7.setEnabled(false);
                solution_step_8.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_8.setEnabled(false);
                solution_step_9_view.setVisibility(View.VISIBLE);
                submit_solution_step_8.setGhost(true);
                submit_solution_step_8.setEnabled(false);
                solution_step_9.requestFocus();
                focus_settings();
            }
        });

        submit_solution_step_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solution_step_9.setEnabled(false);
                submit_solution_step_9.setGhost(true);
                submit_solution_step_9.setEnabled(false);
                focus_settings();
            }
        });


        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_one.getText().toString());
            }
        });

        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_two.getText().toString());
            }
        });

        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_three.getText().toString());
            }
        });

        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_four.getText().toString());
            }
        });

        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_five.getText().toString());
            }
        });

        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_six.getText().toString());
            }
        });

        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_seven.getText().toString());
            }
        });

        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_eight.getText().toString());
            }
        });

        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_nine.getText().toString());
            }
        });

        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_zero.getText().toString());
            }
        });

        btn_left_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left_shift();
            }
        });

        btn_right_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_shift();
            }
        });

        btn_var_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_w.getText().toString());
            }
        });

        btn_var_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_x.getText().toString());
            }
        });

        btn_var_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_y.getText().toString());
            }
        });

        btn_var_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_z.getText().toString());
            }
        });

        btn_var_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_a.getText().toString());
            }
        });

        btn_var_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_b.getText().toString());
            }
        });

        btn_var_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_c.getText().toString());
            }
        });

        btn_var_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_var_d.getText().toString());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_add.getText().toString());
            }
        });

        btn_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_subtract.getText().toString());
            }
        });

        btn_multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_multiply.getText().toString());
            }
        });

        btn_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_divide.getText().toString());
            }
        });

        btn_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_power.getText().toString());
            }
        });

        btn_decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_decimal.getText().toString());
            }
        });

        btn_open_parenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_open_parenthesis.getText().toString());
            }
        });

        btn_closing_parenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_closing_parenthesis.getText().toString());
            }
        });

        btn_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backspace();
            }
        });

        btn_backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clear_equation();
                return false;
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_equation();
            }
        });

        btn_open_brace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_open_brace.getText().toString());
            }
        });

        btn_closing_brace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process_btn_chars(btn_closing_brace.getText().toString());
            }
        });

        parentView = (View) findViewById(R.id.activity_layout_solve);
        solutionScrollView = (View) findViewById(R.id.scrollView_solve);
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

    public void focus_settings() {
        if (solution_step_1.hasFocus()) {
            solution_step_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_2.hasFocus()) {
            solution_step_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_3.hasFocus()) {
            solution_step_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_4.hasFocus()) {
            solution_step_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_5.hasFocus()) {
            solution_step_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_6.hasFocus()) {
            solution_step_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_7.hasFocus()) {
            solution_step_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_8.hasFocus()) {
            solution_step_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_9.hasFocus()) {
            solution_step_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        }


    }

    public void process_btn_chars(String chars) {
        if (solution_step_1.hasFocus()) {
            solution_step_1.getText().insert(solution_step_1.getSelectionStart(), chars);
            solution_step_1.setSelection(solution_step_1.getSelectionStart());
        } else if (solution_step_2.hasFocus()) {
            solution_step_2.getText().insert(solution_step_2.getSelectionStart(), chars);
            solution_step_2.setSelection(solution_step_2.getSelectionStart());
        } else if (solution_step_3.hasFocus()) {
            solution_step_3.getText().insert(solution_step_3.getSelectionStart(), chars);
            solution_step_3.setSelection(solution_step_3.getSelectionStart());
        } else if (solution_step_4.hasFocus()) {
            solution_step_4.getText().insert(solution_step_4.getSelectionStart(), chars);
            solution_step_4.setSelection(solution_step_4.getSelectionStart());
        } else if (solution_step_5.hasFocus()) {
            solution_step_5.getText().insert(solution_step_5.getSelectionStart(), chars);
            solution_step_5.setSelection(solution_step_5.getSelectionStart());
        } else if (solution_step_6.hasFocus()) {
            solution_step_6.getText().insert(solution_step_6.getSelectionStart(), chars);
            solution_step_6.setSelection(solution_step_6.getSelectionStart());
        } else if (solution_step_7.hasFocus()) {
            solution_step_7.getText().insert(solution_step_7.getSelectionStart(), chars);
            solution_step_7.setSelection(solution_step_7.getSelectionStart());
        } else if (solution_step_8.hasFocus()) {
            solution_step_8.getText().insert(solution_step_8.getSelectionStart(), chars);
            solution_step_8.setSelection(solution_step_8.getSelectionStart());
        } else if (solution_step_9.hasFocus()) {
            solution_step_9.getText().insert(solution_step_9.getSelectionStart(), chars);
            solution_step_9.setSelection(solution_step_9.getSelectionStart());
        }
    }

    public void left_shift() {
        if (solution_step_1.hasFocus()) {
            if (solution_step_1.getSelectionStart() == 0) {
                solution_step_1.setSelection(0);
            } else {
                solution_step_1.setSelection(solution_step_1.getSelectionEnd() - 1);
            }
        } else if (solution_step_2.hasFocus()) {
            if (solution_step_2.getSelectionStart() == 0) {
                solution_step_2.setSelection(0);
            } else {
                solution_step_2.setSelection(solution_step_2.getSelectionEnd() - 1);
            }
        } else if (solution_step_3.hasFocus()) {
            if (solution_step_3.getSelectionStart() == 0) {
                solution_step_3.setSelection(0);
            } else {
                solution_step_3.setSelection(solution_step_3.getSelectionEnd() - 1);
            }
        } else if (solution_step_4.hasFocus()) {
            if (solution_step_4.getSelectionStart() == 0) {
                solution_step_4.setSelection(0);
            } else {
                solution_step_4.setSelection(solution_step_4.getSelectionEnd() - 1);
            }
        } else if (solution_step_5.hasFocus()) {
            if (solution_step_5.getSelectionStart() == 0) {
                solution_step_5.setSelection(0);
            } else {
                solution_step_5.setSelection(solution_step_5.getSelectionEnd() - 1);
            }
        } else if (solution_step_6.hasFocus()) {
            if (solution_step_6.getSelectionStart() == 0) {
                solution_step_6.setSelection(0);
            } else {
                solution_step_6.setSelection(solution_step_6.getSelectionEnd() - 1);
            }
        } else if (solution_step_7.hasFocus()) {
            if (solution_step_7.getSelectionStart() == 0) {
                solution_step_7.setSelection(0);
            } else {
                solution_step_7.setSelection(solution_step_7.getSelectionEnd() - 1);
            }
        } else if (solution_step_8.hasFocus()) {
            if (solution_step_8.getSelectionStart() == 0) {
                solution_step_8.setSelection(0);
            } else {
                solution_step_8.setSelection(solution_step_8.getSelectionEnd() - 1);
            }
        } else if (solution_step_9.hasFocus()) {
            if (solution_step_9.getSelectionStart() == 0) {
                solution_step_9.setSelection(0);
            } else {
                solution_step_9.setSelection(solution_step_9.getSelectionEnd() - 1);
            }
        }

    }

    public void right_shift() {
        if (solution_step_1.hasFocus()) {
            if (solution_step_1.getSelectionEnd() == solution_step_1.length()) {
                solution_step_1.setSelection(solution_step_1.length());
            } else {
                solution_step_1.setSelection(solution_step_1.getSelectionEnd() + 1);
            }
        } else if (solution_step_2.hasFocus()) {
            if (solution_step_2.getSelectionEnd() == solution_step_2.length()) {
                solution_step_2.setSelection(solution_step_2.length());
            } else {
                solution_step_2.setSelection(solution_step_2.getSelectionEnd() + 1);
            }
        } else if (solution_step_3.hasFocus()) {
            if (solution_step_3.getSelectionEnd() == solution_step_3.length()) {
                solution_step_3.setSelection(solution_step_3.length());
            } else {
                solution_step_3.setSelection(solution_step_3.getSelectionEnd() + 1);
            }
        } else if (solution_step_4.hasFocus()) {
            if (solution_step_4.getSelectionEnd() == solution_step_4.length()) {
                solution_step_4.setSelection(solution_step_4.length());
            } else {
                solution_step_4.setSelection(solution_step_4.getSelectionEnd() + 1);
            }
        } else if (solution_step_5.hasFocus()) {
            if (solution_step_5.getSelectionEnd() == solution_step_5.length()) {
                solution_step_5.setSelection(solution_step_5.length());
            } else {
                solution_step_5.setSelection(solution_step_5.getSelectionEnd() + 1);
            }
        } else if (solution_step_6.hasFocus()) {
            if (solution_step_6.getSelectionEnd() == solution_step_6.length()) {
                solution_step_6.setSelection(solution_step_6.length());
            } else {
                solution_step_6.setSelection(solution_step_6.getSelectionEnd() + 1);
            }
        } else if (solution_step_7.hasFocus()) {
            if (solution_step_7.getSelectionEnd() == solution_step_7.length()) {
                solution_step_7.setSelection(solution_step_7.length());
            } else {
                solution_step_7.setSelection(solution_step_7.getSelectionEnd() + 1);
            }
        } else if (solution_step_8.hasFocus()) {
            if (solution_step_8.getSelectionEnd() == solution_step_8.length()) {
                solution_step_8.setSelection(solution_step_8.length());
            } else {
                solution_step_8.setSelection(solution_step_8.getSelectionEnd() + 1);
            }
        } else if (solution_step_9.hasFocus()) {
            if (solution_step_9.getSelectionEnd() == solution_step_9.length()) {
                solution_step_9.setSelection(solution_step_9.length());
            } else {
                solution_step_9.setSelection(solution_step_9.getSelectionEnd() + 1);
            }
        }
    }

    public void backspace() {
        int cursorPosition;

        if (solution_step_1.hasFocus()) {
            cursorPosition = solution_step_1.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_1.setText(solution_step_1.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_1.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_2.hasFocus()) {
            cursorPosition = solution_step_2.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_2.setText(solution_step_2.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_2.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_3.hasFocus()) {
            cursorPosition = solution_step_3.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_3.setText(solution_step_3.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_3.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_4.hasFocus()) {
            cursorPosition = solution_step_4.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_4.setText(solution_step_4.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_4.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_5.hasFocus()) {
            cursorPosition = solution_step_5.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_5.setText(solution_step_5.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_5.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_6.hasFocus()) {
            cursorPosition = solution_step_6.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_6.setText(solution_step_6.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_6.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_7.hasFocus()) {
            cursorPosition = solution_step_7.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_7.setText(solution_step_7.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_7.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_8.hasFocus()) {
            cursorPosition = solution_step_8.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_8.setText(solution_step_8.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_8.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_9.hasFocus()) {
            cursorPosition = solution_step_9.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_9.setText(solution_step_9.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_9.setSelection(cursorPosition - 1);
            }
        }
    }

    public void clear_equation() {
        if (solution_step_1.hasFocus()) {
            solution_step_1.setText("");
        } else if (solution_step_2.hasFocus()) {
            solution_step_2.setText("");
        } else if (solution_step_3.hasFocus()) {
            solution_step_3.setText("");
        } else if (solution_step_4.hasFocus()) {
            solution_step_4.setText("");
        } else if (solution_step_5.hasFocus()) {
            solution_step_5.setText("");
        } else if (solution_step_6.hasFocus()) {
            solution_step_6.setText("");
        } else if (solution_step_7.hasFocus()) {
            solution_step_7.setText("");
        } else if (solution_step_8.hasFocus()) {
            solution_step_8.setText("");
        } else if (solution_step_9.hasFocus()) {
            solution_step_9.setText("");
        }
    }

    public void congrats_popup() {
        new LovelyInfoDialog(this)
                .setMessage("Congrats")
                .setTitle("Congratulation")
                .setIcon(R.drawable.aica)
                .setTopColorRes(R.color.darkDeepOrange)
                .show();
    }

    public void next_problem_prompt() {
        new LovelyInfoDialog(this)
                .setIcon(R.drawable.aica)
                .setTitle("Try Solving Another Problem Again?")
                .setTopColorRes(R.color.darkDeepOrange)
                .show();
    }

    public void evaluate_solution(String current_solution) {

    }
}

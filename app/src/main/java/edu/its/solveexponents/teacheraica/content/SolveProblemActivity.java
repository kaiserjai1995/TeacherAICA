package edu.its.solveexponents.teacheraica.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.MathMLUtilities;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.algo.Randomizer;
import io.github.kexanie.library.MathView;
import mehdi.sakout.fancybuttons.FancyButton;

import static io.github.kexanie.library.R.id.MathJax;
import static org.matheclipse.core.expression.F.f;

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

    String hint;

    public static View parentView;
    public static View solutionScrollView;

    MathView math_equation, solution_preview, abort_problem_final_answer, abort_problem_equation;
    MathView generated_problem;
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
    EvalEngine engine;
    String final_equation_string;
    String preview_string;
    String abort_problem_final_answer_string;
    String step_by_step;
    StringWriter stw_step_by_step;
    Intent i;
    ArrayList<String> step_list = new ArrayList<>();
    FancyButton btn_hint;
    ArrayList<String> read_url = new ArrayList<>();
    String final_equation;
    IExpr result;
    int current_level;
    int current_sublevel;
    MaterialEditText input_problem;
    InputMethodManager inputMethodManager;
    Button validate_problem_btn;
    MathView input_mode_problem;
    String resultString;
    Boolean match;
    int number_of_steps;
    int step_number;
    int solution_error_number;
    ArrayList<String> solutions_list;
    ArrayList<String> solution_errors_list;
    boolean hinted, solving, five_errors, nine_errors;

    public class StepListener extends AbstractEvalStepListener {
        /**
         * Listens to the evaluation step in the evaluation engine.
         */
        @Override
        public void add(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            step_by_step = inputExpr.toString() + " ==> " + resultExpr.toString() + "\n";

            stw_step_by_step = new StringWriter();
            stw_step_by_step.write(step_by_step);

            step_list.add(stw_step_by_step.toString());

            System.out.println("Out: " + stw_step_by_step.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_solve_problem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        math_equation = (MathView) findViewById(R.id.math_equation);
        equation = getIntent().getExtras().getString("equation");
        equation_string = getIntent().getExtras().getString("equation_string");
        final_equation_string = equation_string.replace("<center>", "")
                .replace("</center>", "")
                .replace("<font size='+2'>", "<font size='5px'>");

        if (getIntent().getExtras().getBoolean("generated")) {
            level = getIntent().getExtras().getInt("level");
            sublevel = getIntent().getExtras().getInt("sublevel");
            final_answer = getIntent().getExtras().getString("result");
            hint = getIntent().getExtras().getString("hint");

            Log.d("TEACHERAICADB", "LEVEL: " + level + "_" + sublevel);

            math_equation.config(
                    "MathJax.Hub.Config({\n" +
                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                            "         SVG: { linebreaks: { automatic: true } }\n" +
                            "});");

            math_equation.setText(final_equation_string);

            this.equationType = "generated";
            MainFragment.teacheraicadb.addProblem(equation, equationType);
        } else {
            //TODO Analyze equation string for determining hint type
            final_answer = getIntent().getExtras().getString("result");
            math_equation.setText(final_equation_string);

            System.out.println("EQUATION TO ANALYZE: " + equation);

            ArrayList<Character> equation_token = new ArrayList<Character>();

            for (int i = 0; i < equation.length(); i++) {
                equation_token.add(equation.charAt(i));
                System.out.println("Token: " + equation_token.get(i).toString());
            }

            this.equationType = "custom";
            MainFragment.teacheraicadb.addProblem(equation, equationType);
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

        solution_errors_list = new ArrayList<>();
        solutions_list = new ArrayList<>();

        step_number = 0;
        number_of_steps = 0;

        errorsCommited = 0;
        solution_error_number = 0;

        solution_step_1.requestFocus();

        focus_settings();

        submit_solution_step_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_1.getText().toString(),
                        solution_step_1,
                        solution_step_2_view,
                        submit_solution_step_1,
                        solution_step_2);
            }

        });

        submit_solution_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_2.getText().toString(),
                        solution_step_2,
                        solution_step_3_view,
                        submit_solution_step_2,
                        solution_step_3);
            }
        });

        submit_solution_step_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_3.getText().toString(),
                        solution_step_3,
                        solution_step_4_view,
                        submit_solution_step_3,
                        solution_step_4);
            }
        });

        submit_solution_step_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_4.getText().toString(),
                        solution_step_4,
                        solution_step_5_view,
                        submit_solution_step_4,
                        solution_step_5);
            }
        });

        submit_solution_step_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_5.getText().toString(),
                        solution_step_5,
                        solution_step_6_view,
                        submit_solution_step_5,
                        solution_step_6);
            }
        });

        submit_solution_step_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_6.getText().toString(),
                        solution_step_6,
                        solution_step_7_view,
                        submit_solution_step_6,
                        solution_step_7);
            }
        });

        submit_solution_step_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_7.getText().toString(),
                        solution_step_7,
                        solution_step_8_view,
                        submit_solution_step_7,
                        solution_step_8);
            }
        });

        submit_solution_step_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_8.getText().toString(),
                        solution_step_8,
                        solution_step_9_view,
                        submit_solution_step_8,
                        solution_step_9);
            }
        });

        submit_solution_step_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_9.getText().toString(),
                        solution_step_9,
                        solution_step_9_view,
                        submit_solution_step_9,
                        solution_step_9);
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
                .setMessage("Are you sure about this?")
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setCancelable(false)
                .setListener(R.id.abort_problem_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainFragment.teacheraicadb.updateProblemStatus("aborted");
                        show_answer();
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

    public void next_problem_prompt(String problem_type) {
        if (problem_type.equals("generated")) {
            new LovelyCustomDialog(this)
                    .setIcon(R.drawable.aica)
                    .setTopColorRes(R.color.darkRed)
                    .setCancelable(false)
                    .setTitle("YOU'VE GOT THE CORRECT ANSWER! GOOD JOB!")
                    .setMessage("Congratulations! Do you want to solve another equation?")
                    .setMessageGravity(1)
                    .setTitleGravity(1)
                    .setView(R.layout.generated_problem_try_again)
                    .setListener(R.id.solve_another_input_problem, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new LovelyCustomDialog(SolveProblemActivity.this)
                                    .setView(R.layout.generated_mode_problem_view)
                                    .setTopColorRes(R.color.darkRed)
                                    .setTitle(R.string.generated_problem_title)
                                    .setIcon(R.drawable.aica)
                                    .setTitleGravity(1)
                                    .setMessageGravity(1)
                                    .setCancelable(false)
                                    .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                        @Override
                                        public void configureView(View v) {
                                            Button cancel_problem_btn = (Button) v.findViewById(R.id.cancel_problem_btn);
                                            cancel_problem_btn.setVisibility(View.GONE);

                                            level = MainFragment.teacheraicadb.getCurrentLevel();
                                            sublevel = MainFragment.teacheraicadb.getCurrentSublevel(level);
                                            equation = Randomizer.getRandomEquation(level, sublevel);
                                            hint = Randomizer.getHint(level, sublevel);

                                            generated_problem = (MathView) v.findViewById(R.id.generated_problem);

                                            Toast.makeText(getApplicationContext(), "Level: " + level +
                                                    "\nSublevel: " + sublevel +
                                                    "\nEquation: " + equation, Toast.LENGTH_LONG).show();

                                            util = new ExprEvaluator();
                                            engine = util.getEvalEngine();

                                            MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);

                                            StringWriter stw = new StringWriter();
                                            mathUtil.toMathML(engine.parse(equation), stw);

                                            System.out.println(stw.toString());

                                            final_equation = stw.toString();

                                            equation_string = "<center><font size='+2'>" + final_equation + "</font></center>";

                                            generated_problem.config(
                                                    "MathJax.Hub.Config({\n" +
                                                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                                            "         SVG: { linebreaks: { automatic: true } }\n" +
                                                            "});");

                                            generated_problem.setText(equation_string);
                                        }
                                    })
                                    .setListener(R.id.solve_problem_btn, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            solving = true;
                                            i = new Intent(SolveProblemActivity.this, SolveProblemActivity.class);

                                            util = new ExprEvaluator();
                                            engine = util.getEvalEngine();
                                            result = util.evaluate(equation);
                                            System.out.println("Result: " + result.toString());

                                            current_level = MainFragment.teacheraicadb.getCurrentLevel();
                                            current_sublevel = MainFragment.teacheraicadb.getCurrentSublevel(current_level);

                                            i.putExtra("generated", true);
                                            i.putExtra("level", current_level);
                                            i.putExtra("sublevel", current_sublevel);
                                            i.putExtra("equation", equation);
                                            i.putExtra("result", result.toString());
                                            i.putExtra("equation_string", equation_string);
                                            i.putExtra("expr_result", result);
                                            i.putExtra("hint", hint);
                                            startActivity(i);
                                        }
                                    })
                                    .setListener(R.id.next_problem_btn, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            MainFragment.teacheraicadb.addProblem(equation, equationType);
                                            MainFragment.teacheraicadb.updateProblemStatus("skipped");

                                            equation = Randomizer.getRandomEquation(level, sublevel);
                                            hint = Randomizer.getHint(level, sublevel);

                                            Toast.makeText(SolveProblemActivity.this, "Level: " + level +
                                                    "\nSublevel: " + sublevel +
                                                    "\nEquation: " + equation, Toast.LENGTH_LONG).show();

                                            util = new ExprEvaluator();
                                            engine = util.getEvalEngine();

                                            MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);

                                            StringWriter stw = new StringWriter();
                                            mathUtil.toMathML(engine.parse(equation), stw);

                                            System.out.println(stw.toString());

                                            final_equation = stw.toString();

                                            equation_string = "<center><font size='+2'>" + final_equation + "</font></center>";

                                            generated_problem.setText(equation_string);
                                        }
                                    })
                                    .show();
                        }
                    })
                    .setListener(R.id.back_to_main, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            solving = true;
                            i = new Intent(SolveProblemActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .show();
        } else if (problem_type.equals("custom")) {
            new LovelyCustomDialog(this)
                    .setIcon(R.drawable.aica)
                    .setTopColorRes(R.color.darkGreen)
                    .setCancelable(false)
                    .setMessageGravity(1)
                    .setTitleGravity(1)
                    .setTitle("YOU'VE GOT THE CORRECT ANSWER! GOOD JOB!")
                    .setMessage("Congratulations! Do you want to solve another equation?")
                    .setView(R.layout.input_problem_try_again)
                    .setListener(R.id.solve_another_input_problem, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new LovelyCustomDialog(SolveProblemActivity.this)
                                    .setTitle(R.string.input_problem_title)
                                    .setIcon(R.drawable.aica)
                                    .setTitleGravity(1)
                                    .setTopColorRes(R.color.darkGreen)
                                    .setCancelable(false)
                                    .setView(R.layout.input_equation)
                                    .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                        @Override
                                        public void configureView(View v) {
                                            input_problem = (MaterialEditText) v.findViewById(R.id.input_problem);

                                            input_problem.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                                }
                                            });

                                            btn_one = (FancyButton) v.findViewById(R.id.btn_one);
                                            btn_two = (FancyButton) v.findViewById(R.id.btn_two);
                                            btn_three = (FancyButton) v.findViewById(R.id.btn_three);
                                            btn_four = (FancyButton) v.findViewById(R.id.btn_four);
                                            btn_five = (FancyButton) v.findViewById(R.id.btn_five);
                                            btn_six = (FancyButton) v.findViewById(R.id.btn_six);
                                            btn_seven = (FancyButton) v.findViewById(R.id.btn_seven);
                                            btn_eight = (FancyButton) v.findViewById(R.id.btn_eight);
                                            btn_nine = (FancyButton) v.findViewById(R.id.btn_nine);
                                            btn_zero = (FancyButton) v.findViewById(R.id.btn_zero);
                                            btn_left_shift = (FancyButton) v.findViewById(R.id.btn_left_shift);
                                            btn_right_shift = (FancyButton) v.findViewById(R.id.btn_right_shift);
                                            btn_add = (FancyButton) v.findViewById(R.id.btn_add);
                                            btn_subtract = (FancyButton) v.findViewById(R.id.btn_subtract);
                                            btn_multiply = (FancyButton) v.findViewById(R.id.btn_multiply);
                                            btn_divide = (FancyButton) v.findViewById(R.id.btn_divide);
                                            btn_power = (FancyButton) v.findViewById(R.id.btn_power);
                                            btn_decimal = (FancyButton) v.findViewById(R.id.btn_decimal);
                                            btn_open_parenthesis = (FancyButton) v.findViewById(R.id.btn_open_parenthesis);
                                            btn_closing_parenthesis = (FancyButton) v.findViewById(R.id.btn_closing_parenthesis);
                                            btn_open_brace = (FancyButton) v.findViewById(R.id.btn_open_brace);
                                            btn_closing_brace = (FancyButton) v.findViewById(R.id.btn_closing_brace);
                                            btn_backspace = (FancyButton) v.findViewById(R.id.btn_backspace);
                                            btn_clear = (FancyButton) v.findViewById(R.id.btn_clear);
                                            btn_var_w = (FancyButton) v.findViewById(R.id.btn_var_w);
                                            btn_var_x = (FancyButton) v.findViewById(R.id.btn_var_x);
                                            btn_var_y = (FancyButton) v.findViewById(R.id.btn_var_y);
                                            btn_var_z = (FancyButton) v.findViewById(R.id.btn_var_z);
                                            btn_var_a = (FancyButton) v.findViewById(R.id.btn_var_a);
                                            btn_var_b = (FancyButton) v.findViewById(R.id.btn_var_b);
                                            btn_var_c = (FancyButton) v.findViewById(R.id.btn_var_c);
                                            btn_var_d = (FancyButton) v.findViewById(R.id.btn_var_d);

                                            validate_problem_btn = (Button) v.findViewById(R.id.validate_problem_btn);

                                            btn_one.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_one.getText().toString());
                                                }
                                            });

                                            btn_two.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_two.getText().toString());
                                                }
                                            });

                                            btn_three.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_three.getText().toString());
                                                }
                                            });

                                            btn_four.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_four.getText().toString());
                                                }
                                            });

                                            btn_five.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_five.getText().toString());
                                                }
                                            });

                                            btn_six.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_six.getText().toString());
                                                }
                                            });

                                            btn_seven.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_seven.getText().toString());
                                                }
                                            });

                                            btn_eight.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_eight.getText().toString());
                                                }
                                            });

                                            btn_nine.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_nine.getText().toString());
                                                }
                                            });

                                            btn_zero.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_zero.getText().toString());
                                                }
                                            });

                                            btn_left_shift.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    left_shift_input();
                                                }
                                            });

                                            btn_right_shift.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    right_shift_input();
                                                }
                                            });

                                            btn_var_w.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_w.getText().toString());
                                                }
                                            });

                                            btn_var_x.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_x.getText().toString());
                                                }
                                            });

                                            btn_var_y.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_y.getText().toString());
                                                }
                                            });

                                            btn_var_z.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_z.getText().toString());
                                                }
                                            });

                                            btn_var_a.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_a.getText().toString());
                                                }
                                            });

                                            btn_var_b.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_b.getText().toString());
                                                }
                                            });

                                            btn_var_c.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_c.getText().toString());
                                                }
                                            });

                                            btn_var_d.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_var_d.getText().toString());
                                                }
                                            });

                                            btn_add.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_add.getText().toString());
                                                }
                                            });

                                            btn_subtract.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_subtract.getText().toString());
                                                }
                                            });

                                            btn_multiply.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_multiply.getText().toString());
                                                }
                                            });

                                            btn_divide.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_divide.getText().toString());
                                                }
                                            });

                                            btn_power.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_power.getText().toString());
                                                }
                                            });

                                            btn_decimal.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_decimal.getText().toString());
                                                }
                                            });

                                            btn_open_parenthesis.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_open_parenthesis.getText().toString());
                                                }
                                            });

                                            btn_closing_parenthesis.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_closing_parenthesis.getText().toString());
                                                }
                                            });

                                            btn_backspace.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    backspace_input();
                                                }
                                            });

                                            btn_backspace.setOnLongClickListener(new View.OnLongClickListener() {
                                                @Override
                                                public boolean onLongClick(View view) {
                                                    clear_equation_input();
                                                    return false;
                                                }
                                            });

                                            btn_clear.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    clear_equation_input();
                                                }
                                            });

                                            btn_open_brace.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_open_brace.getText().toString());
                                                }
                                            });

                                            btn_closing_brace.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    process_btn_chars_input(btn_closing_brace.getText().toString());
                                                }
                                            });

                                            validate_problem_btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    equation = input_problem.getText().toString();

                                                    if (check_input(equation)) {
                                                        new LovelyCustomDialog(SolveProblemActivity.this)
                                                                .setIcon(R.drawable.aica)
                                                                .setTitle(R.string.validate_problem_title)
                                                                .setView(R.layout.input_mode_problem_view)
                                                                .setTitleGravity(1)
                                                                .setTopColorRes(R.color.darkGreen)
                                                                .setCancelable(false)
                                                                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                                                    @Override
                                                                    public void configureView(View v) {
                                                                        input_mode_problem = (MathView) v.findViewById(R.id.input_mode_problem);

                                                                        util = new ExprEvaluator();
                                                                        engine = util.getEvalEngine();

                                                                        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);

                                                                        StringWriter stw = new StringWriter();
                                                                        mathUtil.toMathML(engine.parse(equation), stw);

                                                                        equation_string = "<center><font size='+2'>" + stw.toString().replace("&#x2062;", "*") + "</font></center>";

                                                                        input_mode_problem.config(
                                                                                "MathJax.Hub.Config({\n" +
                                                                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                                                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                                                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                                                                        "});");

                                                                        input_mode_problem.setText(equation_string);
                                                                    }
                                                                })
                                                                .setListener(R.id.submit_problem_btn, true, new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        i = new Intent(SolveProblemActivity.this, SolveProblemActivity.class);

                                                                        util = new ExprEvaluator();
                                                                        engine = util.getEvalEngine();
                                                                        engine.setStepListener(new SolveProblemActivity.StepListener());
                                                                        result = util.evaluate(equation);

                                                                        if (result.toString().contains("{") && result.toString().contains("}")) {
                                                                            resultString = result.toString().substring(1, result.toString().length() - 1);
                                                                        } else {
                                                                            resultString = result.toString();
                                                                        }

                                                                        System.out.println("Result: " + resultString);

                                                                        i.putExtra("generated", false);
                                                                        i.putExtra("equation", equation);
                                                                        i.putExtra("result", resultString);
                                                                        i.putExtra("equation_string", equation_string);
                                                                        i.putExtra("expr_result", result);

                                                                        startActivity(i);
                                                                    }
                                                                })
                                                                .setListener(R.id.cancel_input_problem_btn, true, new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {

                                                                    }
                                                                })
                                                                .show();
                                                    } else {
                                                        input_problem.setError("Invalid input! Please check your input and try again.");
                                                    }

                                                }
                                            });

                                        }
                                    })
                                    .setListener(R.id.cancel_input_problem_btn, true, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .show();

                        }

                    })
                    .setListener(R.id.back_to_main, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i = new Intent(SolveProblemActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .show();
        }
    }

    public void preview_current_solution(final String current_solution_string) {
        new LovelyCustomDialog(SolveProblemActivity.this)
                .setTitle("CURRENT SOLUTION")
                .setTitleGravity(1)
                .setTopColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.aica)
                .setCancelable(false)
                .setView(R.layout.preview_math_solution)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        solution_preview = (MathView) v.findViewById(R.id.solution_preview);

                        util = new ExprEvaluator();
                        engine = util.getEvalEngine();
                        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);
                        StringWriter stw = new StringWriter();
                        mathUtil.toMathML(current_solution_string, stw);

                        System.out.println(stw.toString());
                        preview_string = "<center><b><font size='+2'>" + stw.toString().replace("&#x2062;", "*") + "</font></b></center>";
                        solution_preview.setText(preview_string);
                    }
                })
                .setListener(R.id.ok_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
    }

    public void evaluate_solution(String current_solution, EditText current_solution_step, LinearLayout next_solution_step_view, FancyButton submit_current_solution_step, EditText next_solution_step) {
        Toast.makeText(getApplicationContext(), final_answer, Toast.LENGTH_LONG).show();

        try {
            if (!current_solution.isEmpty()) {
                util = new ExprEvaluator();
                current_result = util.evaluate(current_solution);

                String[] current_answer_tokens = current_solution.split("(?<=[-+*/])|(?=[-+*/])");
                String[] final_answer_tokens = final_answer.split("(?<=[-+*/])|(?=[-+*/])");

                String[] final_answer_with_asterisk_tokens = final_answer.replace("*", "").replace("(", "").replace(")", "").replace("+-", "-").split("(?<=[-+*/])|(?=[-+*/])");
                String[] current_answer_with_asterisk_tokens = current_solution.replace("*", "").replace("(", "").replace(")", "").replace("+-", "-").split("(?<=[-+*/])|(?=[-+*/])");

                if (Arrays.toString(final_answer_tokens).contains("+")
                        || Arrays.toString(final_answer_tokens).contains("-")
                        || Arrays.toString(final_answer_with_asterisk_tokens).contains("+")
                        || Arrays.toString(final_answer_with_asterisk_tokens).contains("-")) {
                    System.out.println("Has + or - Sign");
                    Arrays.sort(final_answer_tokens);
                    Arrays.sort(final_answer_with_asterisk_tokens);
                    Arrays.sort(current_answer_tokens);
                    Arrays.sort(current_answer_with_asterisk_tokens);
                }

                System.out.println(Arrays.toString(current_answer_tokens));
                System.out.println(Arrays.toString(final_answer_tokens));
                System.out.println(Arrays.toString(current_answer_with_asterisk_tokens));
                System.out.println(Arrays.toString(final_answer_with_asterisk_tokens));

                if ((Arrays.equals(current_answer_with_asterisk_tokens, final_answer_with_asterisk_tokens)
                        && current_answer_with_asterisk_tokens.length == final_answer_with_asterisk_tokens.length)
                        || (Arrays.equals(current_answer_tokens, final_answer_tokens)
                        && current_answer_tokens.length == final_answer_tokens.length)) {
                    Toast.makeText(getApplicationContext(), "Final Answer has been reached!", Toast.LENGTH_LONG).show();
                    step_number++;
                    number_of_steps++;
                    solutions_list.add(current_solution);

                    for (int i = 0; i < solutions_list.size(); i++) {
                        Toast.makeText(getApplicationContext(),
                                "Number of Steps Made: " + number_of_steps + "\n" +
                                        "Solutions Made: " + solutions_list.get(i), Toast.LENGTH_LONG).show();
                    }
                    solved = true;

                    if (hinted) {
                        MainFragment.teacheraicadb.updateProblemStatus("hinted and solved");
                    } else {
                        MainFragment.teacheraicadb.updateProblemStatus("solved");
                    }

                    if (equationType.equals("generated")) {

                        int increment = 0;
                        if (level == 1 && errorsCommited < 3 ||
                                level == 2 && errorsCommited < 3 ||
                                level == 3 && errorsCommited < 3 ||
                                level == 4 && errorsCommited < 3) {
                            increment = 1;
                        }

                        MainFragment.teacheraicadb.incrementLevelSublevelProblemsCount(level, sublevel, increment);

                        next_problem_prompt("generated");
                    } else if (equationType.equals("custom")) {
                        next_problem_prompt("custom");
                    } // Add for quizzes coming from Lecture
                } else if (current_result.toString().equals(final_answer)) {
                    Toast.makeText(getApplicationContext(), "Correct Solution!", Toast.LENGTH_LONG).show();
                    step_number++;
                    number_of_steps++;
                    solutions_list.add(current_solution);

                    Toast.makeText(getApplicationContext(),
                            "Number of Steps Made: " + number_of_steps + "\n" +
                                    "Solutions Made: " + solutions_list, Toast.LENGTH_LONG).show();

                    current_solution_step.setEnabled(false);
                    next_solution_step_view.setVisibility(View.VISIBLE);
                    submit_current_solution_step.setGhost(true);
                    submit_current_solution_step.setEnabled(false);
                    next_solution_step.requestFocus();
                    focus_settings();
                } else {
                    errorsCommited++;
                    solution_error_number = 1;
                    solution_errors_list.add(current_solution);

                    get_solution_step_number(current_solution_step);

                    Toast.makeText(getApplicationContext(), "Errors Committed: " + errorsCommited + "\n" +
                            "Erring Solutions: " + solution_errors_list + "\n" +
                            "Error Number: " + solution_error_number + "\n" +
                            "Step Erred: " + step_number, Toast.LENGTH_LONG).show();

                    current_solution_step.setError("Wrong solution!");
                }
            } else {
                errorsCommited++;
                solution_error_number = 2;
                solution_errors_list.add(current_solution);

                get_solution_step_number(current_solution_step);

                Toast.makeText(getApplicationContext(), "Errors Committed: " + errorsCommited + "\n" +
                        "Erring Solutions: " + solution_errors_list + "\n" +
                        "Error Number: " + solution_error_number + "\n" +
                        "Step Erred: " + step_number, Toast.LENGTH_LONG).show();

                current_solution_step.setError("Input should not be blank!");
            }
        } catch (SyntaxError e) {
            current_solution_step.setError("Invalid input");
        } catch (MathException e) {
            current_solution_step.setError("Invalid input");
        } catch (Exception e) {
            current_solution_step.setError("Invalid input");
        }

        if (solution_errors_list.size() == 3) {
            new LovelyCustomDialog(SolveProblemActivity.this)
                    .setTitle("UH OH...")
                    .setMessage("It seems that you are having a difficulty solving this problem. Want to take a hint?")
                    .setTitleGravity(1)
                    .setMessageGravity(1)
                    .setTopColorRes(R.color.darkDeepOrange)
                    .setIcon(R.drawable.aica)
                    .setView(R.layout.hint)
                    .setListener(R.id.take_hint, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hinted = true;

                            new LovelyCustomDialog(SolveProblemActivity.this)
                                    .setTitle("AVAILABLE HINTS")
                                    .setMessage("These are the hints that you can view at your disposal. Tapping one of them will take you to the Reading Materials associated with that hint you have chosen.")
                                    .setTitleGravity(1)
                                    .setMessageGravity(1)
                                    .setTopColorRes(R.color.darkDeepOrange)
                                    .setIcon(R.drawable.aica)
                                    .setView(R.layout.available_hints)
                                    .setCancelable(false)
                                    .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                        @Override
                                        public void configureView(View v) {
                                            solving = true;
                                            String[] hint_choices = hint.split(", ");

                                            LinearLayout available_hints_view = (LinearLayout) v.findViewById(R.id.available_hints_view);

                                            for (int i = 0; i < hint_choices.length; i++) {
                                                switch (hint_choices[i]) {
                                                    case "Base Raised to Zero":
                                                        read_url.add("file:///android_asset/reading_materials/base_raised_to_zero.html");
                                                        break;
                                                    case "Addition of Exponents with the Same Bases":
                                                        read_url.add("file:///android_asset/reading_materials/addition_of_exponents_with_the_same_bases.html");
                                                        break;
                                                    case "Multiplication of Bases with the Same Exponents":
                                                        read_url.add("file:///android_asset/reading_materials/multiplication_of_bases_with_the_same_exponents.html");
                                                        break;
                                                    case "Multiplication of Exponents to Find the Power of a Power":
                                                        read_url.add("file:///android_asset/reading_materials/multiplication_of_exponents_to_find_the_power_of_a_power.html");
                                                        break;
                                                    case "Subtraction of Exponents":
                                                        read_url.add("file:///android_asset/reading_materials/subtraction_of_exponents.html");
                                                        break;
                                                    case "Negative Integer Exponents":
                                                        read_url.add("file:///android_asset/reading_materials/negative_integer_exponents.html");
                                                        break;
                                                }

                                                btn_hint = new FancyButton(SolveProblemActivity.this);
                                                btn_hint.setId(i + 1);
                                                btn_hint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                btn_hint.setGravity(1);
                                                btn_hint.setPadding(20, 10, 20, 10);
                                                btn_hint.setBorderColor(R.color.white);
                                                btn_hint.setBorderWidth(1);
                                                btn_hint.setRadius(20);
                                                btn_hint.setIconPosition(0);
                                                btn_hint.setTextSize(15);
                                                btn_hint.setBottom(20);
                                                btn_hint.setIconPosition(FancyButton.POSITION_LEFT);
                                                btn_hint.setIconResource(R.drawable.ic_white_lecture);
                                                btn_hint.setBackgroundColor(getResources().getColor(R.color.fb_primary_color));
                                                btn_hint.setFocusBackgroundColor(getResources().getColor(R.color.fb_focus_color));
                                                btn_hint.setText(hint_choices[i]);

                                                Toast.makeText(getApplicationContext(), hint_choices[i].toString(), Toast.LENGTH_LONG).show();

                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                layoutParams.setMargins(10, 10, 10, 10);

                                                available_hints_view.addView(btn_hint, layoutParams);

                                                btn_hint.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(final View view) {
                                                        new LovelyCustomDialog(SolveProblemActivity.this)
                                                                .setTopColorRes(R.color.darkDeepOrange)
                                                                .setCancelable(false)
                                                                .setIcon(R.drawable.aica)
                                                                .setView(R.layout.hint_view)
                                                                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                                                    @Override
                                                                    public void configureView(View v) {
                                                                        LinearLayout chosen_hint_view = (LinearLayout) v.findViewById(R.id.chosen_hint_view);

                                                                        if (view.getId() == 1) {
                                                                            WebView hint_webview1 = new WebView(SolveProblemActivity.this);
                                                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                                                            hint_webview1.getSettings().setJavaScriptEnabled(true);
                                                                            hint_webview1.getSettings().setDomStorageEnabled(true);

                                                                            hint_webview1.setWebViewClient(new WebViewClient());

                                                                            String path = Uri.parse(read_url.get(0)).toString();
                                                                            hint_webview1.loadUrl(path);
                                                                            hint_webview1.setBackgroundColor(0x00000000);
                                                                            chosen_hint_view.addView(hint_webview1, layoutParams);

                                                                        } else {
                                                                            WebView hint_webview2 = new WebView(SolveProblemActivity.this);
                                                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                                                            hint_webview2.getSettings().setJavaScriptEnabled(true);
                                                                            hint_webview2.getSettings().setDomStorageEnabled(true);

                                                                            hint_webview2.setWebViewClient(new WebViewClient());

                                                                            String path = Uri.parse(read_url.get(1)).toString();
                                                                            hint_webview2.loadUrl(path);
                                                                            hint_webview2.setBackgroundColor(0x00000000);
                                                                            chosen_hint_view.addView(hint_webview2, layoutParams);
                                                                        }

                                                                    }
                                                                })
                                                                .setListener(R.id.solve_expo_back, true, new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        errorsCommited = 3;
                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                });
                                            }

                                        }
                                    })
                                    .setListener(R.id.back_to_solve, true, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            solving = true;
                                        }
                                    })
                                    .show();
                        }
                    })
                    .setListener(R.id.ignore_hint, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            solving = true;
                        }
                    })
                    .show();
        } else if (errorsCommited == 5) {
            new LovelyCustomDialog(SolveProblemActivity.this)
                    .setTitle("IS THE PROBLEM VERY DIFFICULT?")
                    .setMessage("It seems that this equation is giving you a hard time. I can provide you the answer to this equation if you want, however, you have to quit solving this problem. Is that all right?")
                    .setTitleGravity(1)
                    .setMessageGravity(1)
                    .setTopColorRes(R.color.darkDeepOrange)
                    .setIcon(R.drawable.aica)
                    .setCancelable(false)
                    .setView(R.layout.option_to_quit)
                    .setListener(R.id.answer_problem_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            five_errors = true;
                            MainFragment.teacheraicadb.updateProblemStatus("Errors 5x and Aborted");
                            onBackPressed();
                        }
                    })
                    .setListener(R.id.back_to_solve_btn, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        } else if (errorsCommited == 9) {
            new LovelyStandardDialog(SolveProblemActivity.this)
                    .setTitle("I'M SORRY")
                    .setMessage("I think its time to let go of this problem and solve a new one. Nice try, though.")
                    .setTitleGravity(1)
                    .setMessageGravity(1)
                    .setTopColorRes(R.color.darkDeepOrange)
                    .setIcon(R.drawable.aica)
                    .setCancelable(false)
                    .setPositiveButtonColorRes(R.color.colorAccent)
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nine_errors = true;
                            show_answer();
                        }
                    })
                    .show();
        }

    }

    public void get_solution_step(MaterialEditText current_solution_step) {
        if (!current_solution_step.getText().toString().isEmpty()) {
            preview_current_solution(current_solution_step.getText().toString());
        } else {
            current_solution_step.setError("Field is blank. Cannot preview current solution.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_solve_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_all_solutions_entered:
                new LovelyCustomDialog(SolveProblemActivity.this)
                        .setTitle("ENTERED SOLUTIONS")
                        .setMessage("These are the solutions that you have entered:")
                        .setTitleGravity(1)
                        .setMessageGravity(1)
                        .setCancelable(false)
                        .setIcon(R.drawable.aica)
                        .setTopColorRes(R.color.darkDeepOrange)
                        .setView(R.layout.view_entered_solutions)
                        .configureView(new LovelyCustomDialog.ViewConfigurator() {
                            @Override
                            public void configureView(View v) {
                                LinearLayout entered_solutions_view = (LinearLayout) v.findViewById(R.id.entered_solutions_view);
                                TextView no_entered_solutions = (TextView) v.findViewById(R.id.no_entered_solutions);
                                ArrayList<String> correct_solutions = new ArrayList<String>();

                                int num_of_solutions = 0;

                                if (!solution_step_1.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_1.getText().toString());
                                if (!solution_step_2.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_2.getText().toString());
                                if (!solution_step_3.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_3.getText().toString());
                                if (!solution_step_4.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_4.getText().toString());
                                if (!solution_step_5.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_5.getText().toString());
                                if (!solution_step_6.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_6.getText().toString());
                                if (!solution_step_7.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_7.getText().toString());
                                if (!solution_step_8.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_8.getText().toString());
                                if (!solution_step_9.isEnabled()) num_of_solutions++;
                                correct_solutions.add(solution_step_9.getText().toString());

                                if (num_of_solutions != 0) {
                                    for (int i = 0; i < num_of_solutions; i++) {
                                        MathView entered_solution = new MathView(SolveProblemActivity.this, null);
                                        TextView entered_solution_step_numbers = new TextView(SolveProblemActivity.this);

                                        entered_solution.setEngine(MathJax);

                                        entered_solution_step_numbers.setGravity(1);
                                        entered_solution_step_numbers.setTextSize(17);
                                        entered_solution_step_numbers.setTypeface(null, Typeface.BOLD);

                                        no_entered_solutions.setTextSize(18);
                                        no_entered_solutions.setText("Number of Entered Solutions: " + num_of_solutions);
                                        no_entered_solutions.setTypeface(null, Typeface.BOLD);

                                        entered_solution.config(
                                                "MathJax.Hub.Config({\n" +
                                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                                        "});");

                                        entered_solution_step_numbers.setText("Solution Step Number " + (i + 1));
                                        entered_solution.setText("$$" + correct_solutions.get(i) + "$$");

                                        entered_solutions_view.addView(entered_solution_step_numbers);
                                        entered_solutions_view.addView(entered_solution);
                                    }
                                } else {
                                    no_entered_solutions.setText("No solutions have been correct yet.");
                                }


                            }
                        })
                        .setListener(R.id.ok_btn, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();

                return true;

            case R.id.action_view_current_solution:
                if (solution_step_1.hasFocus()) get_solution_step(solution_step_1);
                else if (solution_step_2.hasFocus()) get_solution_step(solution_step_2);
                else if (solution_step_3.hasFocus()) get_solution_step(solution_step_3);
                else if (solution_step_4.hasFocus()) get_solution_step(solution_step_4);
                else if (solution_step_5.hasFocus()) get_solution_step(solution_step_5);
                else if (solution_step_6.hasFocus()) get_solution_step(solution_step_6);
                else if (solution_step_7.hasFocus()) get_solution_step(solution_step_7);
                else if (solution_step_8.hasFocus()) get_solution_step(solution_step_8);
                else if (solution_step_9.hasFocus()) get_solution_step(solution_step_9);
                else {
                    new LovelyInfoDialog(SolveProblemActivity.this)
                            .setTitle("UNABLE TO PREVIEW CURRENT SOLUTION")
                            .setTitleGravity(1)
                            .setMessage("Try placing your input in one of the existing input fields first, then preview your current solution.")
                            .setMessageGravity(1)
                            .setTopColorRes(R.color.darkDeepOrange)
                            .setCancelable(false)
                            .setIcon(R.drawable.aica)
                            .setConfirmButtonText("OK")
                            .setConfirmButtonColor(R.color.darkBlueGrey)
                            .show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void show_answer() {
        MainFragment.teacheraicadb.updateProblemStatus("abort");

        new LovelyCustomDialog(SolveProblemActivity.this)
                .setIcon(R.drawable.aica)
                .setTopColorRes(R.color.darkDeepOrange)
                .setTitle("ANSWER TO THE EQUATION PROBLEM")
                .setMessage("You could've answered this, though. Still, nice try though and I know you'll do better next time you encounter this type of problem. The final answer to the problem is:")
                .setCancelable(false)
                .setMessageGravity(1)
                .setTitleGravity(1)
                .setView(R.layout.abort_problem_show_answer)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        abort_problem_final_answer = (MathView) v.findViewById(R.id.abort_problem_final_answer);
                        abort_problem_equation = (MathView) v.findViewById(R.id.abort_problem_equation);

                        util = new ExprEvaluator();
                        engine = util.getEvalEngine();
                        engine.setStepListener(new StepListener());
                        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);
                        StringWriter stw = new StringWriter();
                        mathUtil.toMathML(final_answer, stw);

                        IExpr abort_final_ans = util.evaluate(equation);

                        abort_problem_final_answer_string = "<center><b><font size='+2'>" + stw.toString().replace("&#x2062;", "*") + "</font></b></center>";

                        abort_problem_final_answer.config(
                                "MathJax.Hub.Config({\n" +
                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                        "});");

                        abort_problem_final_answer.setText(abort_problem_final_answer_string);

                        StringWriter stw1 = new StringWriter();
                        mathUtil.toMathML(equation, stw1);

                        abort_problem_equation.config(
                                "MathJax.Hub.Config({\n" +
                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                        "});");

                        abort_problem_equation.setText("<center><b><font size='+2'>" + stw1.toString().replace("&#x2062;", "*") + "</font></b></center>");

                        LinearLayout abort_answers_view = (LinearLayout) v.findViewById(R.id.abort_answers_view);
                        for (int i = 0; i < step_list.size(); i++) {
                            MathView abort_problem_solution = new MathView(SolveProblemActivity.this, null);
                            abort_problem_solution.setEngine(MathJax);

                            abort_problem_solution.config(
                                    "MathJax.Hub.Config({\n" +
                                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                            "         SVG: { linebreaks: { automatic: true } }\n" +
                                            "});");

                            abort_problem_solution.setText("$$" + step_list.get(i) + "$$");
                            abort_answers_view.addView(abort_problem_solution);
                        }

                    }
                })
                .setListener(R.id.ok_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                })
                .show();
    }

    public boolean check_input(String equation) {
        try {
            match = true;

            if (equation.matches("^-?\\d+$")) {
                match = false;
            }
            if (equation.matches("(\\w+)")) {
                match = false;
            }

            util = new ExprEvaluator();
            engine = util.getEvalEngine();
            engine.setStepListener(new StepListener());
            result = util.evaluate(equation);

            System.out.println("Result: " + result.toString());

            if (result.isAST()) {
                match = true;
            }
            if (result.isIndeterminate()) {
                match = false;
            }
            if (result.toString().equals("{}")) {
                match = false;
            }

            // disable trace mode if the step listener isn't necessary anymore
            engine.setTraceMode(false);

        } catch (SyntaxError e) {
            // catch Symja parser errors here
            System.out.println(e.getMessage());
            match = false;
        } catch (MathException me) {
            // catch Symja math errors here
            System.out.println(me.getMessage());
            match = false;
        } catch (Exception e) {
            e.printStackTrace();
            match = false;
        }

        return match;
    }

    public boolean check_solution_input(String equation) {
        try {
            match = true;

            if (equation.matches("^-?\\d+$")) {
                match = false;
            }
            if (equation.matches("(\\w+)")) {
                match = false;
            }

//            util = new ExprEvaluator();
//            engine = util.getEvalEngine();
//            engine.setStepListener(new StepListener());
//            result = util.evaluate(equation);
//
//            System.out.println("Result: " + result.toString());
//
//            if (result.isAST()) {
//                match = true;
//            }
//
//            if (result.toString().equals("{}")) {
//                match = false;
//            }

            // disable trace mode if the step listener isn't necessary anymore
//            engine.setTraceMode(false);

        } catch (SyntaxError e) {
            // catch Symja parser errors here
            System.out.println(e.getMessage());
            match = false;
        } catch (MathException me) {
            // catch Symja math errors here
            System.out.println(me.getMessage());
            match = false;
        } catch (Exception e) {
            e.printStackTrace();
            match = false;
        }

        return match;
    }


    public void process_btn_chars_input(String chars) {
        input_problem.getText().insert(input_problem.getSelectionStart(), chars);
        input_problem.setSelection(input_problem.getSelectionStart());
    }

    public void left_shift_input() {
        if (input_problem.getSelectionStart() == 0) {
            input_problem.setSelection(0);
        } else {
            input_problem.setSelection(input_problem.getSelectionEnd() - 1);
        }
    }

    public void right_shift_input() {
        if (input_problem.getSelectionEnd() == input_problem.length()) {
            input_problem.setSelection(input_problem.length());
        } else {
            input_problem.setSelection(input_problem.getSelectionEnd() + 1);
        }
    }

    public void backspace_input() {
        int cursorPosition = input_problem.getSelectionStart();
        if (cursorPosition > 0) {
            input_problem.setText(input_problem.getText().delete(cursorPosition - 1, cursorPosition));
            input_problem.setSelection(cursorPosition - 1);
        }
    }

    public void clear_equation_input() {
        input_problem.setText("");
    }

    public void get_solution_step_number(EditText current_solution_step) {
        if (current_solution_step.equals(solution_step_1)) {
            step_number = 1;
        } else if (current_solution_step.equals(solution_step_2)) {
            step_number = 2;
        } else if (current_solution_step.equals(solution_step_3)) {
            step_number = 3;
        } else if (current_solution_step.equals(solution_step_4)) {
            step_number = 4;
        } else if (current_solution_step.equals(solution_step_5)) {
            step_number = 5;
        } else if (current_solution_step.equals(solution_step_6)) {
            step_number = 6;
        } else if (current_solution_step.equals(solution_step_7)) {
            step_number = 7;
        } else if (current_solution_step.equals(solution_step_8)) {
            step_number = 8;
        } else if (current_solution_step.equals(solution_step_9)) {
            step_number = 9;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hinted) {
            i = new Intent(this, MainActivity.class);
            MainFragment.teacheraicadb.updateProblemStatus("hinted and left");
            finish();
            startActivity(i);
        }

        if (!solving) {
            i = new Intent(this, MainActivity.class);
            MainFragment.teacheraicadb.updateProblemStatus("left");
            finish();
            startActivity(i);
        }

        if (five_errors) {
            MainFragment.teacheraicadb.updateProblemStatus("Errors 5x and Left");
        }

        if (nine_errors) {
            MainFragment.teacheraicadb.updateProblemStatus("Errors 9x");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
package edu.its.solveexponents.teacheraica.content.lectures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

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
import edu.its.solveexponents.teacheraica.content.LoginActivity;
import io.github.kexanie.library.MathView;
import mehdi.sakout.fancybuttons.FancyButton;

import static io.github.kexanie.library.R.id.MathJax;

//TODO: Add more content

/**
 * Created by jairus on 1/23/17.
 */

public class LecturePositiveIntegerExponentsFragment extends Fragment {

    public LecturePositiveIntegerExponentsFragment() {
        // Required empty public constructor
    }

    WebView webvw_1;
    String path_1;
    String question_1, question_string_1, question_2, question_string_2, question_3, question_string_3;
    MathView math_question_1, math_question_2, math_question_3;
    FancyButton lecture_solve_btn_1, lecture_solve_btn_2, lecture_solve_btn_3;

    LovelyCustomDialog dialog;

    private int errorsCommited;
    private String final_answer, equationType;

    String equation;

    int number_of_steps, step_number, dialog_loaded;

    boolean solved, hinted_and_left, hinted_and_solved, hinted, solving, five_errors, nine_errors,
            max_solution_reached;

    String hint, equation_string, abort_problem_final_answer_string, step_by_step, final_equation, hint_code;
    String equation_lecture;

    FancyButton btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine,
            btn_zero, btn_left_shift, btn_right_shift, btn_add, btn_subtract, btn_multiply, btn_divide, btn_power,
            btn_decimal, btn_open_parenthesis, btn_closing_parenthesis, btn_backspace, btn_clear, btn_open_brace,
            btn_closing_brace, btn_var_w, btn_var_x, btn_var_y, btn_var_z, btn_var_a, btn_var_b, btn_var_c,
            btn_var_d;
    FancyButton submit_solution_step_1, submit_solution_step_2, submit_solution_step_3, submit_solution_step_4,
            submit_solution_step_5, submit_solution_step_6, submit_solution_step_7, submit_solution_step_8,
            submit_solution_step_9, submit_solution_step_10, submit_solution_step_11, submit_solution_step_12,
            submit_solution_step_13, submit_solution_step_14, submit_solution_step_15, submit_solution_step_16,
            submit_solution_step_17, submit_solution_step_18, submit_solution_step_19, submit_solution_step_20;
    FancyButton btn_hint;
    MaterialEditText solution_step_1, solution_step_2, solution_step_3, solution_step_4, solution_step_5,
            solution_step_6, solution_step_7, solution_step_8, solution_step_9, solution_step_10, solution_step_11,
            solution_step_12, solution_step_13, solution_step_14, solution_step_15, solution_step_16,
            solution_step_17, solution_step_18, solution_step_19, solution_step_20;
    LinearLayout solution_step_1_view, solution_step_2_view, solution_step_3_view, solution_step_4_view,
            solution_step_5_view, solution_step_6_view, solution_step_7_view, solution_step_8_view,
            solution_step_9_view, solution_step_10_view, solution_step_11_view, solution_step_12_view,
            solution_step_13_view, solution_step_14_view, solution_step_15_view, solution_step_16_view,
            solution_step_17_view, solution_step_18_view, solution_step_19_view, solution_step_20_view;

    TextView show_problem_answer_title, final_answer_show_title;

    ExprEvaluator util;
    IExpr current_result, result;
    EvalEngine engine;
    StringWriter stw_step_by_step;

    ArrayList<String> step_list = new ArrayList<>(),
            read_url = new ArrayList<>();

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
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lecture_positive_integer_exponents, container, false);
        getActivity().setTitle("Positive Integer Exponents");
        this.equationType = "Lecture";

        final RelativeLayout lecture_question_1 = (RelativeLayout) rootView.findViewById(R.id.lecture_question_1);
        final RelativeLayout lecture_question_2 = (RelativeLayout) rootView.findViewById(R.id.lecture_question_2);
        final RelativeLayout lecture_question_3 = (RelativeLayout) rootView.findViewById(R.id.lecture_question_3);
        final RelativeLayout lecture_question_4 = (RelativeLayout) rootView.findViewById(R.id.lecture_question_4);

        webvw_1 = (WebView) rootView.findViewById(R.id.webvw_lecture_positive_integer_exponents_1);
        webvw_1.getSettings().setJavaScriptEnabled(true);
        webvw_1.getSettings().setDomStorageEnabled(true);
        webvw_1.setWebViewClient(new WebViewClient());
        path_1 = Uri.parse("file:///android_asset/lectures/positive_integer_exponents/positive_integer_exponents_1.html").toString();
        webvw_1.loadUrl(path_1);
        webvw_1.setBackgroundColor(0x00000000);

        dialog_loaded = 1;

        FloatingActionButton next_interaction = (FloatingActionButton) rootView.findViewById(R.id.next_interaction);
        next_interaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(dialog_loaded);

                if (dialog_loaded == 1) {
                    lecture_question_1.setVisibility(View.VISIBLE);
                } else if (dialog_loaded == 2) {
                    lecture_question_1.setVisibility(View.VISIBLE);
                    lecture_question_2.setVisibility(View.VISIBLE);
                } else if (dialog_loaded == 3) {
                    lecture_question_1.setVisibility(View.VISIBLE);
                    lecture_question_2.setVisibility(View.VISIBLE);
                    lecture_question_3.setVisibility(View.VISIBLE);
                } else if (dialog_loaded == 4) {
                    lecture_question_1.setVisibility(View.VISIBLE);
                    lecture_question_2.setVisibility(View.VISIBLE);
                    lecture_question_3.setVisibility(View.VISIBLE);
                    lecture_question_4.setVisibility(View.VISIBLE);
                }
            }
        });

        math_question_1 = (MathView) rootView.findViewById(R.id.math_question_1);
        question_1 = "3 * 3";
        question_string_1 = convert_equation_to_mathML(question_1);
        display_question(math_question_1, question_string_1);
        hint = "Positive Integer Exponents";

        lecture_solve_btn_1 = (FancyButton) rootView.findViewById(R.id.lecture_solve_btn_1);

        if (LoginActivity.teacheraicadb.checkProblemIfExistsLecture(question_1)) {
            lecture_solve_btn_1.setEnabled(false);
            lecture_solve_btn_1.setIconResource(R.drawable.ic_ok);
            lecture_solve_btn_1.setText("THIS HAS BEEN SOLVED!");
            dialog_loaded++;
        }

        if (dialog_loaded == 1) {
            System.out.println("dialog_loaded_1" + question_string_1);
            lecture_answer_problem_dialog(question_1, question_string_1, lecture_solve_btn_1);
        }

        math_question_2 = (MathView) rootView.findViewById(R.id.math_question_2);
        question_2 = "4 * 4 * 4";
        question_string_2 = convert_equation_to_mathML(question_2);
        display_question(math_question_2, question_string_2);
        hint = "Positive Integer Exponents";

        lecture_solve_btn_2 = (FancyButton) rootView.findViewById(R.id.lecture_solve_btn_2);

        if (LoginActivity.teacheraicadb.checkProblemIfExistsLecture(question_2)) {
            lecture_solve_btn_2.setEnabled(false);
            lecture_solve_btn_2.setIconResource(R.drawable.ic_ok);
            lecture_solve_btn_2.setText("THIS HAS BEEN SOLVED!");
            dialog_loaded++;
        }

        if (dialog_loaded == 2) {
            System.out.println("dialog_loaded_2" + question_string_2);
            lecture_answer_problem_dialog(question_2, question_string_2, lecture_solve_btn_2);
        }

        math_question_3 = (MathView) rootView.findViewById(R.id.math_question_3);
        question_3 = "5 * 5 * 5";
        question_string_3 = convert_equation_to_mathML(question_3);
        display_question(math_question_3, question_string_3);
        hint = "Positive Integer Exponents";

        lecture_solve_btn_3 = (FancyButton) rootView.findViewById(R.id.lecture_solve_btn_3);

        if (LoginActivity.teacheraicadb.checkProblemIfExistsLecture(question_3)) {
            lecture_solve_btn_3.setEnabled(false);
            lecture_solve_btn_3.setIconResource(R.drawable.ic_ok);
            lecture_solve_btn_3.setText("THIS HAS BEEN SOLVED!");
            dialog_loaded++;
        }

        if (dialog_loaded == 3) {
            System.out.println("dialog_loaded_3" + question_string_3);
            lecture_answer_problem_dialog(question_3, question_string_3, lecture_solve_btn_3);
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public void lecture_answer_problem_dialog(final String equation, final String question_string, final FancyButton lecture_solve_btn) {
        equation_lecture = equation;

        lecture_solve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.teacheraicadb.addProblem(equation, equationType);

                dialog = new LovelyCustomDialog(getContext());
                dialog.setView(R.layout.lecture_solve_questions)
                        .setTopColorRes(R.color.primary_dark)
                        .setIcon(R.drawable.aica)
                        .setCancelable(true)
                        .configureView(new LovelyCustomDialog.ViewConfigurator() {
                            @Override
                            public void configureView(View v) {
                                MathView math_question = (MathView) v.findViewById(R.id.math_question);
                                display_question(math_question, question_string);

                                initializeObjectVariables(v);
                            }
                        })
                        .setListener(R.id.lecture_go_back_btn, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (hinted) {
                                    LoginActivity.teacheraicadb.updateProblemStatus("Hinted and Left");
                                } else {
                                    LoginActivity.teacheraicadb.updateProblemStatus("Left");
                                }
                                onDetach();
                            }
                        })
                        .show();
            }
        });
    }

    public String convert_equation_to_mathML(String question) {
        util = new ExprEvaluator();
        engine = util.getEvalEngine();

        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);
        StringWriter stw = new StringWriter();
        mathUtil.toMathML(engine.parse(question), stw);

        result = util.evaluate(question);

        if (result.toString().contains("{") && result.toString().contains("}")) {
            final_answer = result.toString().substring(1, result.toString().length() - 1);
        } else {
            final_answer = result.toString();
        }

        final_equation = stw.toString();
        equation_string = "<font size='+2'>" + stw.toString().replace("&#x2062;", "*") + "</font>";

        return equation_string;
    }

    public void display_question(MathView math_question, String question_string) {
        math_question.config(
                "MathJax.Hub.Config({\n" +
                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                        "         SVG: { linebreaks: { automatic: true } }\n" +
                        "});");
        math_question.setText(question_string);
    }

    public void initializeObjectVariables(View v) {
        submit_solution_step_1 = (FancyButton) v.findViewById(R.id.submit_solution_step_1);
        submit_solution_step_2 = (FancyButton) v.findViewById(R.id.submit_solution_step_2);
        submit_solution_step_3 = (FancyButton) v.findViewById(R.id.submit_solution_step_3);
        submit_solution_step_4 = (FancyButton) v.findViewById(R.id.submit_solution_step_4);
        submit_solution_step_5 = (FancyButton) v.findViewById(R.id.submit_solution_step_5);
        submit_solution_step_6 = (FancyButton) v.findViewById(R.id.submit_solution_step_6);
        submit_solution_step_7 = (FancyButton) v.findViewById(R.id.submit_solution_step_7);
        submit_solution_step_8 = (FancyButton) v.findViewById(R.id.submit_solution_step_8);
        submit_solution_step_9 = (FancyButton) v.findViewById(R.id.submit_solution_step_9);
        submit_solution_step_10 = (FancyButton) v.findViewById(R.id.submit_solution_step_10);
        submit_solution_step_11 = (FancyButton) v.findViewById(R.id.submit_solution_step_11);
        submit_solution_step_12 = (FancyButton) v.findViewById(R.id.submit_solution_step_12);
        submit_solution_step_13 = (FancyButton) v.findViewById(R.id.submit_solution_step_13);
        submit_solution_step_14 = (FancyButton) v.findViewById(R.id.submit_solution_step_14);
        submit_solution_step_15 = (FancyButton) v.findViewById(R.id.submit_solution_step_15);
        submit_solution_step_16 = (FancyButton) v.findViewById(R.id.submit_solution_step_16);
        submit_solution_step_17 = (FancyButton) v.findViewById(R.id.submit_solution_step_17);
        submit_solution_step_18 = (FancyButton) v.findViewById(R.id.submit_solution_step_18);
        submit_solution_step_19 = (FancyButton) v.findViewById(R.id.submit_solution_step_19);
        submit_solution_step_20 = (FancyButton) v.findViewById(R.id.submit_solution_step_20);

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

        solution_step_1 = (MaterialEditText) v.findViewById(R.id.solution_step_1);
        solution_step_2 = (MaterialEditText) v.findViewById(R.id.solution_step_2);
        solution_step_3 = (MaterialEditText) v.findViewById(R.id.solution_step_3);
        solution_step_4 = (MaterialEditText) v.findViewById(R.id.solution_step_4);
        solution_step_5 = (MaterialEditText) v.findViewById(R.id.solution_step_5);
        solution_step_6 = (MaterialEditText) v.findViewById(R.id.solution_step_6);
        solution_step_7 = (MaterialEditText) v.findViewById(R.id.solution_step_7);
        solution_step_8 = (MaterialEditText) v.findViewById(R.id.solution_step_8);
        solution_step_9 = (MaterialEditText) v.findViewById(R.id.solution_step_9);
        solution_step_10 = (MaterialEditText) v.findViewById(R.id.solution_step_10);
        solution_step_11 = (MaterialEditText) v.findViewById(R.id.solution_step_11);
        solution_step_12 = (MaterialEditText) v.findViewById(R.id.solution_step_12);
        solution_step_13 = (MaterialEditText) v.findViewById(R.id.solution_step_13);
        solution_step_14 = (MaterialEditText) v.findViewById(R.id.solution_step_14);
        solution_step_15 = (MaterialEditText) v.findViewById(R.id.solution_step_15);
        solution_step_16 = (MaterialEditText) v.findViewById(R.id.solution_step_16);
        solution_step_17 = (MaterialEditText) v.findViewById(R.id.solution_step_17);
        solution_step_18 = (MaterialEditText) v.findViewById(R.id.solution_step_18);
        solution_step_19 = (MaterialEditText) v.findViewById(R.id.solution_step_19);
        solution_step_20 = (MaterialEditText) v.findViewById(R.id.solution_step_20);

        solution_step_1_view = (LinearLayout) v.findViewById(R.id.solution_step_1_view);
        solution_step_2_view = (LinearLayout) v.findViewById(R.id.solution_step_2_view);
        solution_step_3_view = (LinearLayout) v.findViewById(R.id.solution_step_3_view);
        solution_step_4_view = (LinearLayout) v.findViewById(R.id.solution_step_4_view);
        solution_step_5_view = (LinearLayout) v.findViewById(R.id.solution_step_5_view);
        solution_step_6_view = (LinearLayout) v.findViewById(R.id.solution_step_6_view);
        solution_step_7_view = (LinearLayout) v.findViewById(R.id.solution_step_7_view);
        solution_step_8_view = (LinearLayout) v.findViewById(R.id.solution_step_8_view);
        solution_step_9_view = (LinearLayout) v.findViewById(R.id.solution_step_9_view);
        solution_step_10_view = (LinearLayout) v.findViewById(R.id.solution_step_10_view);
        solution_step_11_view = (LinearLayout) v.findViewById(R.id.solution_step_11_view);
        solution_step_12_view = (LinearLayout) v.findViewById(R.id.solution_step_12_view);
        solution_step_13_view = (LinearLayout) v.findViewById(R.id.solution_step_13_view);
        solution_step_14_view = (LinearLayout) v.findViewById(R.id.solution_step_14_view);
        solution_step_15_view = (LinearLayout) v.findViewById(R.id.solution_step_15_view);
        solution_step_16_view = (LinearLayout) v.findViewById(R.id.solution_step_16_view);
        solution_step_17_view = (LinearLayout) v.findViewById(R.id.solution_step_17_view);
        solution_step_18_view = (LinearLayout) v.findViewById(R.id.solution_step_18_view);
        solution_step_19_view = (LinearLayout) v.findViewById(R.id.solution_step_19_view);
        solution_step_20_view = (LinearLayout) v.findViewById(R.id.solution_step_20_view);

        submit_solution_step_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_1.getText().toString(), solution_step_1, solution_step_2_view,
                        submit_solution_step_1, solution_step_2);
            }
        });

        submit_solution_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_2.getText().toString(), solution_step_2, solution_step_3_view,
                        submit_solution_step_2, solution_step_3);
            }
        });
        submit_solution_step_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_3.getText().toString(), solution_step_3, solution_step_4_view,
                        submit_solution_step_3, solution_step_4);
            }
        });
        submit_solution_step_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_4.getText().toString(), solution_step_4, solution_step_5_view,
                        submit_solution_step_4, solution_step_5);
            }
        });
        submit_solution_step_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_5.getText().toString(), solution_step_5, solution_step_6_view,
                        submit_solution_step_5, solution_step_6);
            }
        });
        submit_solution_step_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_6.getText().toString(), solution_step_6, solution_step_7_view,
                        submit_solution_step_6, solution_step_7);
            }
        });
        submit_solution_step_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_7.getText().toString(), solution_step_7, solution_step_8_view,
                        submit_solution_step_7, solution_step_8);
            }
        });
        submit_solution_step_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_8.getText().toString(), solution_step_8, solution_step_9_view,
                        submit_solution_step_8, solution_step_9);
            }
        });
        submit_solution_step_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_9.getText().toString(), solution_step_9, solution_step_10_view,
                        submit_solution_step_9, solution_step_10);
            }
        });
        submit_solution_step_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_10.getText().toString(), solution_step_10, solution_step_11_view,
                        submit_solution_step_10, solution_step_11);
            }
        });
        submit_solution_step_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_11.getText().toString(), solution_step_11, solution_step_12_view,
                        submit_solution_step_11, solution_step_12);
            }
        });
        submit_solution_step_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_12.getText().toString(), solution_step_12, solution_step_13_view,
                        submit_solution_step_12, solution_step_13);
            }
        });
        submit_solution_step_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_13.getText().toString(), solution_step_13, solution_step_14_view,
                        submit_solution_step_13, solution_step_14);
            }
        });
        submit_solution_step_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_14.getText().toString(), solution_step_14, solution_step_15_view,
                        submit_solution_step_14, solution_step_15);
            }
        });
        submit_solution_step_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_15.getText().toString(), solution_step_15, solution_step_16_view,
                        submit_solution_step_15, solution_step_16);
            }
        });
        submit_solution_step_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_16.getText().toString(), solution_step_16, solution_step_17_view,
                        submit_solution_step_16, solution_step_17);
            }
        });
        submit_solution_step_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_17.getText().toString(), solution_step_17, solution_step_18_view,
                        submit_solution_step_17, solution_step_18);
            }
        });
        submit_solution_step_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_18.getText().toString(), solution_step_18, solution_step_19_view,
                        submit_solution_step_18, solution_step_19);
            }
        });
        submit_solution_step_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate_solution(solution_step_19.getText().toString(), solution_step_19, solution_step_20_view,
                        submit_solution_step_19, solution_step_20);
            }
        });
        submit_solution_step_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                max_solution_reached = true;
                force_solving_abort();
                LoginActivity.teacheraicadb.updateProblemStatus("Max Num of Solution Reached");
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

        btn_backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clear_equation();
                return false;
            }
        });

        errorsCommited = 0;
        solution_step_1.requestFocus();
        focus_settings();
    }

    public void focus_settings() {
        if (solution_step_1.hasFocus()) {
            solution_step_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_2.hasFocus()) {
            solution_step_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_3.hasFocus()) {
            solution_step_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_4.hasFocus()) {
            solution_step_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_5.hasFocus()) {
            solution_step_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_6.hasFocus()) {
            solution_step_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_7.hasFocus()) {
            solution_step_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_8.hasFocus()) {
            solution_step_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_9.hasFocus()) {
            solution_step_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_10.hasFocus()) {
            solution_step_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_11.hasFocus()) {
            solution_step_11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_12.hasFocus()) {
            solution_step_12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_13.hasFocus()) {
            solution_step_13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_14.hasFocus()) {
            solution_step_14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_15.hasFocus()) {
            solution_step_15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_16.hasFocus()) {
            solution_step_16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_17.hasFocus()) {
            solution_step_17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_18.hasFocus()) {
            solution_step_18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_19.hasFocus()) {
            solution_step_19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        } else if (solution_step_20.hasFocus()) {
            solution_step_20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        }

    }

    public void evaluate_solution(String current_solution, EditText current_solution_step, LinearLayout next_solution_step_view, FancyButton submit_current_solution_step, EditText next_solution_step) {
        try {
            if (!current_solution.isEmpty()) {
                util = new ExprEvaluator();
                current_result = util.evaluate(current_solution);

                String[] current_answer_tokens = current_solution.replace(" ", "").split("(?<=[-+*/])|(?=[-+*/])");
                String[] final_answer_tokens = final_answer.split("(?<=[-+*/])|(?=[-+*/])");

                String[] final_answer_with_asterisk_tokens = final_answer.replace("*", "").replace("(", "").replace(")", "").replace("+-", "-").split("(?<=[-+*/])|(?=[-+*/])");
                String[] current_answer_with_asterisk_tokens = current_solution.replace(" ", "").replace("*", "").replace("(", "").replace(")", "").replace("+-", "-").split("(?<=[-+*/])|(?=[-+*/])");

                if (Arrays.toString(final_answer_tokens).contains("+")
                        || Arrays.toString(final_answer_tokens).contains("-")
                        || Arrays.toString(final_answer_with_asterisk_tokens).contains("+")
                        || Arrays.toString(final_answer_with_asterisk_tokens).contains("-")) {
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
                    step_number++;
                    number_of_steps++;
                    LoginActivity.teacheraicadb.addStep(current_solution);

                    solved = true;

                    if (hinted_and_solved) {
                        LoginActivity.teacheraicadb.updateProblemStatus("Hinted and Solved");
                    } else {
                        LoginActivity.teacheraicadb.updateProblemStatus("Solved");
                    }

                    new LovelyCustomDialog(getContext())
                            .setIcon(R.drawable.aica)
                            .setTopColorRes(R.color.darkRed)
                            .setCancelable(false)
                            .setTitle("YOU'VE GOT THE CORRECT ANSWER! GOOD JOB!")
                            .setMessage("Congratulations!")
                            .setMessageGravity(1)
                            .setTitleGravity(1)
                            .setView(R.layout.correct_answer_lecture)
                            .setListener(R.id.ok_btn, true, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (dialog_loaded == 1) {
                                        lecture_solve_btn_1.setEnabled(false);
                                        lecture_solve_btn_1.setIconResource(R.drawable.ic_ok);
                                        lecture_solve_btn_1.setText("THIS HAS BEEN SOLVED!");
                                    } else if (dialog_loaded == 2) {
                                        lecture_solve_btn_2.setEnabled(false);
                                        lecture_solve_btn_2.setIconResource(R.drawable.ic_ok);
                                        lecture_solve_btn_2.setText("THIS HAS BEEN SOLVED!");
                                    } else if (dialog_loaded == 3) {
                                        lecture_solve_btn_3.setEnabled(false);
                                        lecture_solve_btn_3.setIconResource(R.drawable.ic_ok);
                                        lecture_solve_btn_3.setText("THIS HAS BEEN SOLVED!");
                                    }

                                    dialog_loaded++;
                                    dialog.dismiss();
                                }
                            })
                            .show();

                } else if (current_result.toString().equals(final_answer)) {
                    Toast.makeText(getContext(), "Correct Solution!", Toast.LENGTH_LONG).show();
                    step_number++;
                    number_of_steps++;
                    LoginActivity.teacheraicadb.addStep(current_solution);

                    current_solution_step.setEnabled(false);
                    next_solution_step_view.setVisibility(View.VISIBLE);
                    submit_current_solution_step.setGhost(true);
                    submit_current_solution_step.setEnabled(false);
                    next_solution_step.requestFocus();
                    focus_settings();
                } else {
                    errorsCommited++;
                    get_solution_step_number(current_solution_step);

                    LoginActivity.teacheraicadb.addError(equation, current_solution);

                    current_solution_step.setError("Wrong solution!");
                }
            } else {
                errorsCommited++;
                get_solution_step_number(current_solution_step);

                LoginActivity.teacheraicadb.addError(equation, current_solution);

                current_solution_step.setError("Input should not be blank!");
            }
        } catch (SyntaxError e) {
            current_solution_step.setError("Invalid input");
            LoginActivity.teacheraicadb.addError(equation, current_solution);
        } catch (MathException e) {
            current_solution_step.setError("Invalid input");
            LoginActivity.teacheraicadb.addError(equation, current_solution);
        } catch (Exception e) {
            current_solution_step.setError("Invalid input");
            LoginActivity.teacheraicadb.addError(equation, current_solution);
        }

        if (errorsCommited == 3 && !solved) {
            new LovelyCustomDialog(getContext())
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
                                    hinted_and_solved = true;

                                    new LovelyCustomDialog(getContext())
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
                                                                   final String[] hint_choices = hint.split(", ");

                                                                   LinearLayout available_hints_view = (LinearLayout) v.findViewById(R.id.available_hints_view);

                                                                   for (int i = 0; i < hint_choices.length; i++) {
                                                                       switch (hint_choices[i]) {
                                                                           case "Positive Integer Exponents":
                                                                               hint_code = "P";
                                                                               read_url.add("file:///android_asset/reading_materials/positive_integer_exponents.html");
                                                                               break;
                                                                           case "Base Raised to Zero":
                                                                               hint_code = "Z";
                                                                               read_url.add("file:///android_asset/reading_materials/base_raised_to_zero.html");
                                                                               break;
                                                                           case "Addition of Exponents with the Same Bases":
                                                                               hint_code = "AE";
                                                                               read_url.add("file:///android_asset/reading_materials/addition_of_exponents_with_the_same_bases.html");
                                                                               break;
                                                                           case "Multiplication of Bases with the Same Exponents":
                                                                               hint_code = "MB";
                                                                               read_url.add("file:///android_asset/reading_materials/multiplication_of_bases_with_the_same_exponents.html");
                                                                               break;
                                                                           case "Multiplication of Exponents to Find the Power of a Power":
                                                                               hint_code = "ME";
                                                                               read_url.add("file:///android_asset/reading_materials/multiplication_of_exponents_to_find_the_power_of_a_power.html");
                                                                               break;
                                                                           case "Subtraction of Exponents":
                                                                               hint_code = "SE";
                                                                               read_url.add("file:///android_asset/reading_materials/subtraction_of_exponents.html");
                                                                               break;
                                                                           case "Negative Integer Exponents":
                                                                               hint_code = "N";
                                                                               read_url.add("file:///android_asset/reading_materials/negative_integer_exponents.html");
                                                                               break;
                                                                       }

                                                                       btn_hint = new FancyButton(getContext());
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

                                                                       LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                                       layoutParams.setMargins(10, 10, 10, 10);

                                                                       available_hints_view.addView(btn_hint, layoutParams);

                                                                       btn_hint.setOnClickListener(new View.OnClickListener() {
                                                                                                       @Override
                                                                                                       public void onClick(final View view) {
                                                                                                           new LovelyCustomDialog(getContext())
                                                                                                                   .setTopColorRes(R.color.darkDeepOrange)
                                                                                                                   .setCancelable(false)
                                                                                                                   .setIcon(R.drawable.aica)
                                                                                                                   .setView(R.layout.hint_view)
                                                                                                                   .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                                                                                                                      @Override
                                                                                                                                      public void configureView(View v) {
                                                                                                                                          LinearLayout chosen_hint_view = (LinearLayout) v.findViewById(R.id.chosen_hint_view);

                                                                                                                                          if (view.getId() == 1) {
                                                                                                                                              switch (hint_choices[0]) {
                                                                                                                                                  case "Positive Integer Exponents":
                                                                                                                                                      hint_code = "P";
                                                                                                                                                      break;
                                                                                                                                                  case "Base Raised to Zero":
                                                                                                                                                      hint_code = "Z";
                                                                                                                                                      break;
                                                                                                                                                  case "Addition of Exponents with the Same Bases":
                                                                                                                                                      hint_code = "AE";
                                                                                                                                                      break;
                                                                                                                                                  case "Multiplication of Bases with the Same Exponents":
                                                                                                                                                      hint_code = "MB";
                                                                                                                                                      break;
                                                                                                                                                  case "Multiplication of Exponents to Find the Power of a Power":
                                                                                                                                                      hint_code = "ME";
                                                                                                                                                      break;
                                                                                                                                                  case "Subtraction of Exponents":
                                                                                                                                                      hint_code = "SE";
                                                                                                                                                      break;
                                                                                                                                                  case "Negative Integer Exponents":
                                                                                                                                                      hint_code = "N";
                                                                                                                                                      break;
                                                                                                                                              }

                                                                                                                                              LoginActivity.teacheraicadb.addHintUsed(equation, hint_code, hint_choices[0]);

                                                                                                                                              WebView hint_webview1 = new WebView(getContext());
                                                                                                                                              LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                                                                                                                              hint_webview1.getSettings().setJavaScriptEnabled(true);
                                                                                                                                              hint_webview1.getSettings().setDomStorageEnabled(true);

                                                                                                                                              hint_webview1.setWebViewClient(new WebViewClient());

                                                                                                                                              String path = Uri.parse(read_url.get(0)).toString();
                                                                                                                                              hint_webview1.loadUrl(path);
                                                                                                                                              hint_webview1.setBackgroundColor(0x00000000);
                                                                                                                                              chosen_hint_view.addView(hint_webview1, layoutParams);

                                                                                                                                          } else {
                                                                                                                                              switch (hint_choices[1]) {
                                                                                                                                                  case "Positive Integer Exponents":
                                                                                                                                                      hint_code = "P";
                                                                                                                                                      break;
                                                                                                                                                  case "Base Raised to Zero":
                                                                                                                                                      hint_code = "Z";
                                                                                                                                                      break;
                                                                                                                                                  case "Addition of Exponents with the Same Bases":
                                                                                                                                                      hint_code = "AE";
                                                                                                                                                      break;
                                                                                                                                                  case "Multiplication of Bases with the Same Exponents":
                                                                                                                                                      hint_code = "MB";
                                                                                                                                                      break;
                                                                                                                                                  case "Multiplication of Exponents to Find the Power of a Power":
                                                                                                                                                      hint_code = "ME";
                                                                                                                                                      break;
                                                                                                                                                  case "Subtraction of Exponents":
                                                                                                                                                      hint_code = "SE";
                                                                                                                                                      break;
                                                                                                                                                  case "Negative Integer Exponents":
                                                                                                                                                      hint_code = "N";
                                                                                                                                                      break;
                                                                                                                                              }

                                                                                                                                              System.out.println("Hints Used: " + hint_choices[0].toString() + "\n" +
                                                                                                                                                      "Hint Code: " + hint_code);

                                                                                                                                              LoginActivity.teacheraicadb.addHintUsed(equation, hint_code, hint_choices[1]);

                                                                                                                                              WebView hint_webview2 = new WebView(getContext());
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
                                                                                                                                  }
                                                                                                                   )
                                                                                                                   .setListener(R.id.solve_expo_back, true, new View.OnClickListener() {
                                                                                                                               @Override
                                                                                                                               public void onClick(View view) {

                                                                                                                               }
                                                                                                                           }
                                                                                                                   )
                                                                                                                   .show();
                                                                                                       }
                                                                                                   }
                                                                       );
                                                                   }

                                                               }
                                                           }

                                            )
                                            .setListener(R.id.back_to_solve, true, new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            solving = true;
                                                        }
                                                    }
                                            )
                                            .show();
                                }
                            }
                    )
                    .setListener(R.id.ignore_hint, true, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    solving = true;
                                }
                            }
                    )
                    .show();
        } else if (errorsCommited == 5 && !solved) {
            new LovelyCustomDialog(getContext())
                    .setTitle("IS THE PROBLEM VERY DIFFICULT?")
                    .setMessage("It seems that this equation is giving you a hard time. I can provide you the answer to this equation if you want, however, you have to quit solving this problem. Is that all right?")
                    .setTitleGravity(1)
                    .setMessageGravity(1)
                    .setTopColorRes(R.color.darkDeepOrange)
                    .setIcon(R.drawable.aica)
                    .setCancelable(false)
                    .setView(R.layout.option_to_quit)
                    .setListener(R.id.answer_problem_btn, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            five_errors = true;
                            LoginActivity.teacheraicadb.updateProblemStatus("Errors 5x and Aborted");
                            onDetach();
                        }
                    })
                    .setListener(R.id.back_to_solve_btn, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        } else if (errorsCommited == 9 && !solved) {
            nine_errors = true;
            force_solving_abort();
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
        } else if (solution_step_10.hasFocus()) {
            solution_step_10.getText().insert(solution_step_10.getSelectionStart(), chars);
            solution_step_10.setSelection(solution_step_10.getSelectionStart());
        } else if (solution_step_11.hasFocus()) {
            solution_step_11.getText().insert(solution_step_11.getSelectionStart(), chars);
            solution_step_11.setSelection(solution_step_11.getSelectionStart());
        } else if (solution_step_12.hasFocus()) {
            solution_step_12.getText().insert(solution_step_12.getSelectionStart(), chars);
            solution_step_12.setSelection(solution_step_12.getSelectionStart());
        } else if (solution_step_13.hasFocus()) {
            solution_step_13.getText().insert(solution_step_13.getSelectionStart(), chars);
            solution_step_13.setSelection(solution_step_13.getSelectionStart());
        } else if (solution_step_14.hasFocus()) {
            solution_step_14.getText().insert(solution_step_14.getSelectionStart(), chars);
            solution_step_14.setSelection(solution_step_14.getSelectionStart());
        } else if (solution_step_15.hasFocus()) {
            solution_step_15.getText().insert(solution_step_15.getSelectionStart(), chars);
            solution_step_15.setSelection(solution_step_15.getSelectionStart());
        } else if (solution_step_16.hasFocus()) {
            solution_step_16.getText().insert(solution_step_16.getSelectionStart(), chars);
            solution_step_16.setSelection(solution_step_16.getSelectionStart());
        } else if (solution_step_17.hasFocus()) {
            solution_step_17.getText().insert(solution_step_17.getSelectionStart(), chars);
            solution_step_17.setSelection(solution_step_17.getSelectionStart());
        } else if (solution_step_18.hasFocus()) {
            solution_step_18.getText().insert(solution_step_18.getSelectionStart(), chars);
            solution_step_18.setSelection(solution_step_18.getSelectionStart());
        } else if (solution_step_19.hasFocus()) {
            solution_step_19.getText().insert(solution_step_19.getSelectionStart(), chars);
            solution_step_19.setSelection(solution_step_19.getSelectionStart());
        } else if (solution_step_20.hasFocus()) {
            solution_step_20.getText().insert(solution_step_20.getSelectionStart(), chars);
            solution_step_20.setSelection(solution_step_20.getSelectionStart());
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
        } else if (solution_step_10.hasFocus()) {
            if (solution_step_10.getSelectionStart() == 0) {
                solution_step_10.setSelection(0);
            } else {
                solution_step_10.setSelection(solution_step_10.getSelectionEnd() - 1);
            }
        } else if (solution_step_11.hasFocus()) {
            if (solution_step_11.getSelectionStart() == 0) {
                solution_step_11.setSelection(0);
            } else {
                solution_step_11.setSelection(solution_step_11.getSelectionEnd() - 1);
            }
        } else if (solution_step_12.hasFocus()) {
            if (solution_step_12.getSelectionStart() == 0) {
                solution_step_12.setSelection(0);
            } else {
                solution_step_12.setSelection(solution_step_12.getSelectionEnd() - 1);
            }
        } else if (solution_step_13.hasFocus()) {
            if (solution_step_13.getSelectionStart() == 0) {
                solution_step_13.setSelection(0);
            } else {
                solution_step_13.setSelection(solution_step_13.getSelectionEnd() - 1);
            }
        } else if (solution_step_14.hasFocus()) {
            if (solution_step_14.getSelectionStart() == 0) {
                solution_step_14.setSelection(0);
            } else {
                solution_step_14.setSelection(solution_step_14.getSelectionEnd() - 1);
            }
        } else if (solution_step_15.hasFocus()) {
            if (solution_step_15.getSelectionStart() == 0) {
                solution_step_15.setSelection(0);
            } else {
                solution_step_15.setSelection(solution_step_15.getSelectionEnd() - 1);
            }
        } else if (solution_step_16.hasFocus()) {
            if (solution_step_16.getSelectionStart() == 0) {
                solution_step_16.setSelection(0);
            } else {
                solution_step_16.setSelection(solution_step_16.getSelectionEnd() - 1);
            }
        } else if (solution_step_17.hasFocus()) {
            if (solution_step_17.getSelectionStart() == 0) {
                solution_step_17.setSelection(0);
            } else {
                solution_step_17.setSelection(solution_step_17.getSelectionEnd() - 1);
            }
        } else if (solution_step_18.hasFocus()) {
            if (solution_step_18.getSelectionStart() == 0) {
                solution_step_18.setSelection(0);
            } else {
                solution_step_18.setSelection(solution_step_18.getSelectionEnd() - 1);
            }
        } else if (solution_step_19.hasFocus()) {
            if (solution_step_19.getSelectionStart() == 0) {
                solution_step_19.setSelection(0);
            } else {
                solution_step_19.setSelection(solution_step_19.getSelectionEnd() - 1);
            }
        } else if (solution_step_20.hasFocus()) {
            if (solution_step_20.getSelectionStart() == 0) {
                solution_step_20.setSelection(0);
            } else {
                solution_step_20.setSelection(solution_step_20.getSelectionEnd() - 1);
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
        } else if (solution_step_10.hasFocus()) {
            if (solution_step_10.getSelectionEnd() == solution_step_10.length()) {
                solution_step_10.setSelection(solution_step_10.length());
            } else {
                solution_step_10.setSelection(solution_step_10.getSelectionEnd() + 1);
            }
        } else if (solution_step_11.hasFocus()) {
            if (solution_step_11.getSelectionEnd() == solution_step_11.length()) {
                solution_step_11.setSelection(solution_step_11.length());
            } else {
                solution_step_11.setSelection(solution_step_11.getSelectionEnd() + 1);
            }
        } else if (solution_step_12.hasFocus()) {
            if (solution_step_12.getSelectionEnd() == solution_step_12.length()) {
                solution_step_12.setSelection(solution_step_12.length());
            } else {
                solution_step_12.setSelection(solution_step_12.getSelectionEnd() + 1);
            }
        } else if (solution_step_13.hasFocus()) {
            if (solution_step_13.getSelectionEnd() == solution_step_13.length()) {
                solution_step_13.setSelection(solution_step_13.length());
            } else {
                solution_step_13.setSelection(solution_step_13.getSelectionEnd() + 1);
            }
        } else if (solution_step_14.hasFocus()) {
            if (solution_step_14.getSelectionEnd() == solution_step_14.length()) {
                solution_step_14.setSelection(solution_step_14.length());
            } else {
                solution_step_14.setSelection(solution_step_14.getSelectionEnd() + 1);
            }
        } else if (solution_step_15.hasFocus()) {
            if (solution_step_15.getSelectionEnd() == solution_step_15.length()) {
                solution_step_15.setSelection(solution_step_15.length());
            } else {
                solution_step_15.setSelection(solution_step_15.getSelectionEnd() + 1);
            }
        } else if (solution_step_16.hasFocus()) {
            if (solution_step_16.getSelectionEnd() == solution_step_16.length()) {
                solution_step_16.setSelection(solution_step_16.length());
            } else {
                solution_step_16.setSelection(solution_step_16.getSelectionEnd() + 1);
            }
        } else if (solution_step_17.hasFocus()) {
            if (solution_step_17.getSelectionEnd() == solution_step_17.length()) {
                solution_step_17.setSelection(solution_step_17.length());
            } else {
                solution_step_17.setSelection(solution_step_17.getSelectionEnd() + 1);
            }
        } else if (solution_step_18.hasFocus()) {
            if (solution_step_18.getSelectionEnd() == solution_step_18.length()) {
                solution_step_18.setSelection(solution_step_18.length());
            } else {
                solution_step_18.setSelection(solution_step_18.getSelectionEnd() + 1);
            }
        } else if (solution_step_19.hasFocus()) {
            if (solution_step_19.getSelectionEnd() == solution_step_19.length()) {
                solution_step_19.setSelection(solution_step_19.length());
            } else {
                solution_step_19.setSelection(solution_step_19.getSelectionEnd() + 1);
            }
        } else if (solution_step_20.hasFocus()) {
            if (solution_step_20.getSelectionEnd() == solution_step_20.length()) {
                solution_step_20.setSelection(solution_step_20.length());
            } else {
                solution_step_20.setSelection(solution_step_20.getSelectionEnd() + 1);
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
        } else if (solution_step_10.hasFocus()) {
            cursorPosition = solution_step_10.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_10.setText(solution_step_10.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_10.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_11.hasFocus()) {
            cursorPosition = solution_step_11.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_11.setText(solution_step_11.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_11.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_12.hasFocus()) {
            cursorPosition = solution_step_12.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_12.setText(solution_step_12.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_12.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_13.hasFocus()) {
            cursorPosition = solution_step_13.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_13.setText(solution_step_13.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_13.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_14.hasFocus()) {
            cursorPosition = solution_step_14.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_14.setText(solution_step_14.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_14.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_15.hasFocus()) {
            cursorPosition = solution_step_15.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_15.setText(solution_step_15.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_15.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_16.hasFocus()) {
            cursorPosition = solution_step_16.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_16.setText(solution_step_16.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_16.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_17.hasFocus()) {
            cursorPosition = solution_step_17.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_17.setText(solution_step_17.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_17.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_18.hasFocus()) {
            cursorPosition = solution_step_18.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_18.setText(solution_step_18.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_18.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_19.hasFocus()) {
            cursorPosition = solution_step_19.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_19.setText(solution_step_19.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_19.setSelection(cursorPosition - 1);
            }
        } else if (solution_step_20.hasFocus()) {
            cursorPosition = solution_step_20.getSelectionStart();
            if (cursorPosition > 0) {
                solution_step_20.setText(solution_step_20.getText().delete(cursorPosition - 1, cursorPosition));
                solution_step_20.setSelection(cursorPosition - 1);
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
        } else if (solution_step_10.hasFocus()) {
            solution_step_10.setText("");
        } else if (solution_step_11.hasFocus()) {
            solution_step_11.setText("");
        } else if (solution_step_12.hasFocus()) {
            solution_step_12.setText("");
        } else if (solution_step_13.hasFocus()) {
            solution_step_13.setText("");
        } else if (solution_step_14.hasFocus()) {
            solution_step_14.setText("");
        } else if (solution_step_15.hasFocus()) {
            solution_step_15.setText("");
        } else if (solution_step_16.hasFocus()) {
            solution_step_16.setText("");
        } else if (solution_step_17.hasFocus()) {
            solution_step_17.setText("");
        } else if (solution_step_18.hasFocus()) {
            solution_step_18.setText("");
        } else if (solution_step_19.hasFocus()) {
            solution_step_19.setText("");
        } else if (solution_step_20.hasFocus()) {
            solution_step_20.setText("");
        }
    }

    public void force_solving_abort() {
        new LovelyCustomDialog(getContext())
                .setTitle("I'M SORRY")
                .setMessage("I think its time to let go of this problem and solve a new one. Nice try, though.")
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setTopColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.aica)
                .setCancelable(false)
                .setView(R.layout.force_abort)
                .setListener(R.id.back_to_main, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show_answer();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void show_answer() {
        System.out.println("Show Answer");
        LoginActivity.teacheraicadb.updateProblemStatus("Left");

        new LovelyCustomDialog(getContext())
                .setIcon(R.drawable.aica)
                .setTopColorRes(R.color.darkDeepOrange)
                .setCancelable(false)
                .setMessageGravity(1)
                .setTitleGravity(1)
                .setView(R.layout.abort_problem_show_answer_lecture)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        show_problem_answer_title = (TextView) v.findViewById(R.id.show_problem_answer_title);
                        show_problem_answer_title.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                                "fonts/Raleway-Bold.ttf"));

                        final_answer_show_title = (TextView) v.findViewById(R.id.final_answer_show_title);
                        final_answer_show_title.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                                "fonts/Raleway-Bold.ttf"));

                        MathView abort_problem_final_answer_lecture = (MathView) v.findViewById(R.id.abort_problem_final_answer_lecture);
                        MathView abort_problem_equation_lecture = (MathView) v.findViewById(R.id.abort_problem_equation_lecture);

                        util = new ExprEvaluator();
                        engine = util.getEvalEngine();
                        engine.setStepListener(new StepListener());
                        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);
                        StringWriter stw = new StringWriter();
                        mathUtil.toMathML(final_answer, stw);

                        IExpr abort_final_ans = util.evaluate(equation_lecture);

                        abort_problem_final_answer_string = "<center><b><font size='+2'>" + stw.toString().replace("&#x2062;", "*") + "</font></b></center>";

                        abort_problem_final_answer_lecture.config(
                                "MathJax.Hub.Config({\n" +
                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                        "});");

                        abort_problem_final_answer_lecture.setText(abort_problem_final_answer_string);

                        StringWriter stw1 = new StringWriter();
                        mathUtil.toMathML(equation_lecture, stw1);

                        System.out.println(equation_lecture);

                        abort_problem_equation_lecture.config(
                                "MathJax.Hub.Config({\n" +
                                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                        "         SVG: { linebreaks: { automatic: true } }\n" +
                                        "});");

                        abort_problem_equation_lecture.setText("<center><b><font size='+2'>" + stw1.toString().replace("&#x2062;", "*") + "</font></b></center>");

                        LinearLayout abort_answers_view = (LinearLayout) v.findViewById(R.id.abort_answers_view);
                        for (int i = 0; i < step_list.size(); i++) {
                            MathView abort_problem_solution_lecture = new MathView(getContext(), null);
                            abort_problem_solution_lecture.setEngine(MathJax);

                            abort_problem_solution_lecture.config(
                                    "MathJax.Hub.Config({\n" +
                                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                            "         SVG: { linebreaks: { automatic: true } }\n" +
                                            "});");

                            abort_problem_solution_lecture.setText("$$" + step_list.get(i) + "$$");
                            abort_answers_view.addView(abort_problem_solution_lecture);
                        }
                        step_list.clear();
                    }
                })
                .setListener(R.id.ok_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nine_errors) {
                            LoginActivity.teacheraicadb.updateProblemStatus("Errors 9x");
                        } else if (hinted) {
                            LoginActivity.teacheraicadb.updateProblemStatus("Hinted and Left");
                        } else {
                            LoginActivity.teacheraicadb.updateProblemStatus("Left");
                        }
                        dialog.dismiss();
                    }
                })
                .show();
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
        } else if (current_solution_step.equals(solution_step_10)) {
            step_number = 10;
        } else if (current_solution_step.equals(solution_step_11)) {
            step_number = 11;
        } else if (current_solution_step.equals(solution_step_12)) {
            step_number = 12;
        } else if (current_solution_step.equals(solution_step_13)) {
            step_number = 13;
        } else if (current_solution_step.equals(solution_step_14)) {
            step_number = 14;
        } else if (current_solution_step.equals(solution_step_15)) {
            step_number = 15;
        } else if (current_solution_step.equals(solution_step_16)) {
            step_number = 16;
        } else if (current_solution_step.equals(solution_step_17)) {
            step_number = 17;
        } else if (current_solution_step.equals(solution_step_18)) {
            step_number = 18;
        } else if (current_solution_step.equals(solution_step_19)) {
            step_number = 19;
        } else if (current_solution_step.equals(solution_step_20)) {
            step_number = 20;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        new LovelyCustomDialog(getContext())
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
                        LoginActivity.teacheraicadb.updateProblemStatus("Left");
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

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("onPause: " + hinted_and_left);

        if (hinted_and_left) {
            LoginActivity.teacheraicadb.updateProblemStatus("Hinted and Left");
            dialog.dismiss();
        }

        if (five_errors) {
            LoginActivity.teacheraicadb.updateProblemStatus("Errors 5x and Left");
            dialog.dismiss();
        }

        if (max_solution_reached) {
            LoginActivity.teacheraicadb.updateProblemStatus("Max Num of Solution Reached");
            dialog.dismiss();
        }

    }
}


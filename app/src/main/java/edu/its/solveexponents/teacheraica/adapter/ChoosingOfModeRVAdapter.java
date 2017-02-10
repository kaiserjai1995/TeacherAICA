package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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
import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.algo.Randomizer;
import edu.its.solveexponents.teacheraica.content.LoginActivity;
import edu.its.solveexponents.teacheraica.content.SolveProblemActivity;
import edu.its.solveexponents.teacheraica.model.ModeInput;
import io.github.kexanie.library.MathView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 8/2/16.
 */

    //TODO: Refactor Codes

public class ChoosingOfModeRVAdapter extends RecyclerView.Adapter<ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder> {
    public static class ChoosingOfModeViewHolder extends RecyclerView.ViewHolder {

        CardView cv_choosing_of_mode;
        TextView mode_title;

        public ChoosingOfModeViewHolder(View itemView) {
            super(itemView);
            cv_choosing_of_mode = (CardView) itemView.findViewById(R.id.cv_choosing_of_mode);
            mode_title = (TextView) itemView.findViewById(R.id.mode_title);
        }
    }

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

    private Context mContext;

    String hint, resultString, equation_string, final_equation, equation, equationType;
    int level, sublevel;
    boolean match;

    List<ModeInput> mode_input;

    MathView generated_problem, input_mode_problem;

    Intent i;

    ExprEvaluator util;
    EvalEngine engine;
    IExpr result;

    InputMethodManager inputMethodManager;

    MaterialEditText input_problem;
    FancyButton btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine,
            btn_zero, btn_left_shift, btn_right_shift, btn_add, btn_subtract, btn_multiply, btn_divide, btn_power,
            btn_decimal, btn_open_parenthesis, btn_closing_parenthesis, btn_backspace, btn_clear, btn_open_brace,
            btn_closing_brace, btn_var_w, btn_var_x, btn_var_y, btn_var_z, btn_var_a, btn_var_b, btn_var_c,
            btn_var_d;
    FancyButton validate_problem_btn;
    TextView input_exponent_title, generated_exponent_title;

    public ChoosingOfModeRVAdapter(Context context, List<ModeInput> mode_input) {
        this.mode_input = mode_input;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_choosing_of_mode, viewGroup, false);
        ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder comvh = new ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder(v);
        return comvh;
    }

    @Override
    public void onBindViewHolder(final ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder choosingOfModeViewHolder, final int i) {
        choosingOfModeViewHolder.mode_title.setText(mode_input.get(i).mode_title);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        switch (i) {
            case 0:
                if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP)
                    choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.darkRed);
                else
                    choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_generated);
                break;
            case 1:
                if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP)
                    choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.primary_dark);
                else
                    choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_input);
                break;
        }

        choosingOfModeViewHolder.cv_choosing_of_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode_input.get(i).mode_title) {
                    case "Solve COMPUTER-GENERATED Problems":
                        level = LoginActivity.teacheraicadb.getCurrentLevel();
                        sublevel = LoginActivity.teacheraicadb.getCurrentSublevel(level);
                        equation = checkIfProblemExists(level, sublevel);
                        hint = Randomizer.getHint(level, sublevel);
                        equationType = "Generated";

                        showGeneratedProblemView(level, sublevel);
                        break;
                    case "Solve USER-INPUT Problems":
                        showInputProblemView();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mode_input.size();
    }

    public void showGeneratedProblemView(final int level, final int sublevel) {
        new LovelyCustomDialog(mContext)
                .setView(R.layout.generated_mode_problem_view)
                .setTopColorRes(R.color.darkRed)
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setMessageGravity(1)
                .setCancelable(false)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        generated_exponent_title = (TextView) v.findViewById(R.id.generated_exponent_title);
                        generated_exponent_title.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                                "fonts/Raleway-Bold.ttf"));

                        generated_problem = (MathView) v.findViewById(R.id.generated_problem);
                        convert_equation_to_mathML();

                        generated_problem.config(
                                "MathJax.Hub.Config({\n"+
                                        "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                                        "         SVG: { linebreaks: { automatic: true } }\n"+
                                        "});");
                        generated_problem.setText(equation_string);
                    }
                })
                .setListener(R.id.solve_problem_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        get_equation_result();

                        i = new Intent(mContext, SolveProblemActivity.class);
                        i.putExtra("Generated", true);
                        i.putExtra("level", level);
                        i.putExtra("sublevel", sublevel);
                        i.putExtra("equation", equation);
                        i.putExtra("result", result.toString());
                        i.putExtra("equation_string", equation_string);
                        i.putExtra("expr_result", result);
                        i.putExtra("hint", hint);
                        mContext.startActivity(i);
                    }
                })
                .setListener(R.id.next_problem_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginActivity.teacheraicadb.addProblem(equation, equationType);
                        LoginActivity.teacheraicadb.updateProblemStatus("Skipped");

                        equation = checkIfProblemExists(level, sublevel);

                        hint = Randomizer.getHint(level, sublevel);

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
                .setListener(R.id.cancel_problem_btn, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.teacheraicadb.addProblem(equation, equationType);
                        LoginActivity.teacheraicadb.updateProblemStatus("Skipped");
                    }
                })
                .show();
    }

    public void showInputProblemView() {
        new LovelyCustomDialog(mContext)
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setTopColorRes(R.color.darkGreen)
                .setCancelable(false)
                .setView(R.layout.input_equation)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        input_problem = (MaterialEditText) v.findViewById(R.id.input_problem);

                        input_exponent_title = (TextView) v.findViewById(R.id.input_exponent_title);
                        input_exponent_title.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                                "fonts/Raleway-Bold.ttf"));

                        input_problem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                        validate_problem_btn = (FancyButton) v.findViewById(R.id.validate_problem_btn);

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

                        validate_problem_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                equation = input_problem.getText().toString();

                                if (check(equation)) {
                                    new LovelyCustomDialog(mContext)
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
                                                            "MathJax.Hub.Config({\n"+
                                                                    "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                                                                    "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                                                                    "         SVG: { linebreaks: { automatic: true } }\n"+
                                                                    "});");

                                                    input_mode_problem.setText(equation_string);
                                                }
                                            })
                                            .setListener(R.id.submit_problem_btn, true, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    i = new Intent(mContext, SolveProblemActivity.class);

                                                    util = new ExprEvaluator();
                                                    engine = util.getEvalEngine();
                                                    engine.setStepListener(new StepListener());
                                                    result = util.evaluate(equation);

                                                    if (result.toString().contains("{") && result.toString().contains("}")) {
                                                        resultString = result.toString().substring(1, result.toString().length() - 1);
                                                    } else {
                                                        resultString = result.toString();
                                                    }

                                                    i.putExtra("Generated", false);
                                                    i.putExtra("equation", equation);
                                                    i.putExtra("result", resultString);
                                                    i.putExtra("equation_string", equation_string);
                                                    i.putExtra("expr_result", result);

                                                    mContext.startActivity(i);
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

    public void convert_equation_to_mathML() {
        util = new ExprEvaluator();
        engine = util.getEvalEngine();

        MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);
        StringWriter stw = new StringWriter();
        mathUtil.toMathML(engine.parse(equation), stw);

        final_equation = stw.toString();
        equation_string = "<center><font size='+2'>" + final_equation + "</font></center>";
    }

    public void get_equation_result() {
        util = new ExprEvaluator();
        engine = util.getEvalEngine();
        result = util.evaluate(equation);
    }

    public boolean check(String equation) {
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

    public void process_btn_chars(String chars) {
        input_problem.getText().insert(input_problem.getSelectionStart(), chars);
        input_problem.setSelection(input_problem.getSelectionStart());
    }

    public void left_shift() {
        if (input_problem.getSelectionStart() == 0) {
            input_problem.setSelection(0);
        } else {
            input_problem.setSelection(input_problem.getSelectionEnd() - 1);
        }
    }

    public void right_shift() {
        if (input_problem.getSelectionEnd() == input_problem.length()) {
            input_problem.setSelection(input_problem.length());
        } else {
            input_problem.setSelection(input_problem.getSelectionEnd() + 1);
        }
    }

    public void backspace() {
        int cursorPosition = input_problem.getSelectionStart();
        if (cursorPosition > 0) {
            input_problem.setText(input_problem.getText().delete(cursorPosition - 1, cursorPosition));
            input_problem.setSelection(cursorPosition - 1);
        }
    }

    public void clear_equation() {
        input_problem.setText("");
    }

    public String checkIfProblemExists(int level, int sublevel) {
        equation = Randomizer.getRandomEquation(level, sublevel);

        if (LoginActivity.teacheraicadb.checkProblemIfExists(equation)) {
            System.out.println("Problem Exists!");
            equation = Randomizer.getRandomEquation(level, sublevel);

            if (LoginActivity.teacheraicadb.checkProblemIfExists(equation)) {
                System.out.println("Problem Exists!");
                equation = Randomizer.getRandomEquation(level, sublevel);

                if (LoginActivity.teacheraicadb.checkProblemIfExists(equation)) {
                    System.out.println("Problem Exists!");
                    equation = Randomizer.getRandomEquation(level, sublevel);

                    if (LoginActivity.teacheraicadb.checkProblemIfExists(equation)) {
                        System.out.println("Problem Exists!");
                        equation = Randomizer.getRandomEquation(level, sublevel);
                    }
                }
            }
        }

        return equation;
    }
}

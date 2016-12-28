package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.algo.Randomizer;
import edu.its.solveexponents.teacheraica.content.MainFragment;
import edu.its.solveexponents.teacheraica.content.SolveProblemActivity;
import edu.its.solveexponents.teacheraica.model.ModeInput;
import io.github.kexanie.library.MathView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 8/2/16.
 */

public class ChoosingOfModeRVAdapter extends RecyclerView.Adapter<ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder>  {
    public static class ChoosingOfModeViewHolder extends RecyclerView.ViewHolder {

        CardView cv_choosing_of_mode;
        TextView mode_title;

        public ChoosingOfModeViewHolder(View itemView) {
            super(itemView);
            cv_choosing_of_mode = (CardView)itemView.findViewById(R.id.cv_choosing_of_mode);
            mode_title = (TextView)itemView.findViewById(R.id.mode_title);
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

    List<ModeInput> mode_input;
    private Context mContext;

    public ChoosingOfModeRVAdapter(Context context, List<ModeInput> mode_input){
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

        if (i == 0) {
            choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_generated);
            // Replace setBackgroundResource with code below if API Level of device is below 21
//
//            choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.colorAccent);
        }
        if (i == 1) {
            choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_input);
            // Replace setBackgroundResource with code below if API Level of device is below 21
//
//            choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.colorPrimaryDark);
        }

        choosingOfModeViewHolder.cv_choosing_of_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mode_input.get(i).mode_title) {
                    case "Solve COMPUTER-GENERATED Problems":

                        int level = MainFragment.teacheraicadb.getCurrentLevel();
                        int sublevel = MainFragment.teacheraicadb.getCurrentSublevel(level);

                        String equation = Randomizer.getRandomEquation(level, sublevel);

                        showGeneratedProblemView(equation);
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

    public void showGeneratedProblemView(final String equation) {

        //Randomize Algorithm

        new LovelyCustomDialog(mContext)
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
                        MathView generated_problem = (MathView)v.findViewById(R.id.generated_problem);

                        generated_problem.setText("$$" + equation + "$$");
                    }
                })
            .setListener(R.id.solve_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, SolveProblemActivity.class);

                    ExprEvaluator util = new ExprEvaluator();
                    EvalEngine engine = util.getEvalEngine();
                    engine.setStepListener(new StepListener());
                    IExpr result = util.evaluate(equation);
                    System.out.println("Result: " + result.toString());

                    i.putExtra("generated", true);
                    i.putExtra("level", MainFragment.teacheraicadb.getCurrentLevel());
                    i.putExtra("sublevel", MainFragment.teacheraicadb.getCurrentSublevel(MainFragment.teacheraicadb.getCurrentLevel()));
                    i.putExtra("equation", equation);
                    i.putExtra("result", result.toString());
                    mContext.startActivity(i);
                }
            })
            .setListener(R.id.cancel_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove Problem data here and randomize
                }
            })
            .show();
    }

    public void showInputProblemView() {

        new LovelyCustomDialog(mContext)
                .setTitle("INPUT EXPONENT PROBLEM")
                .setIcon(R.drawable.aica)
                .setTitleGravity(1)
                .setTopColorRes(R.color.darkGreen)
                .setCancelable(false)
                .setView(R.layout.input_equation)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        final MaterialEditText input_problem = (MaterialEditText) v.findViewById(R.id.input_problem);

                        input_problem.setText("");

                        String currentDisplayedInput = "";
                        String inputToBeParsed = "";

                        input_problem.setSelection(0);

                        input_problem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            }
                        });

//                        input_problem.requestFocus();

                        Button validate_problem_btn = (Button) v.findViewById(R.id.validate_problem_btn);

                        FancyButton btn_one = (FancyButton)v.findViewById(R.id.btn_one);
                        btn_one.setText("1");
                        btn_one.setTextSize(20);

                        FancyButton btn_two = (FancyButton)v.findViewById(R.id.btn_two);
                        btn_two.setText("2");
                        btn_two.setTextSize(20);

                        FancyButton btn_three = (FancyButton)v.findViewById(R.id.btn_three);
                        btn_three.setText("3");
                        btn_three.setTextSize(20);

                        FancyButton btn_four = (FancyButton)v.findViewById(R.id.btn_four);
                        btn_four.setText("4");
                        btn_four.setTextSize(20);

                        FancyButton btn_five = (FancyButton)v.findViewById(R.id.btn_five);
                        btn_five.setText("5");
                        btn_five.setTextSize(20);

                        FancyButton btn_six = (FancyButton)v.findViewById(R.id.btn_six);
                        btn_six.setText("6");
                        btn_six.setTextSize(20);

                        FancyButton btn_seven = (FancyButton)v.findViewById(R.id.btn_seven);
                        btn_seven.setText("7");
                        btn_seven.setTextSize(20);

                        FancyButton btn_eight = (FancyButton)v.findViewById(R.id.btn_eight);
                        btn_eight.setText("8");
                        btn_eight.setTextSize(20);

                        FancyButton btn_nine = (FancyButton)v.findViewById(R.id.btn_nine);
                        btn_nine.setText("9");
                        btn_nine.setTextSize(20);

                        FancyButton btn_zero = (FancyButton)v.findViewById(R.id.btn_zero);
                        btn_zero.setText("0");
                        btn_zero.setTextSize(20);

                        FancyButton btn_left_shift = (FancyButton)v.findViewById(R.id.btn_left_shift);
                        btn_left_shift.setText("←");
                        btn_left_shift.setTextSize(20);

                        FancyButton btn_right_shift = (FancyButton)v.findViewById(R.id.btn_right_shift);
                        btn_right_shift.setText("→");
                        btn_right_shift.setTextSize(20);

                        FancyButton btn_add = (FancyButton)v.findViewById(R.id.btn_add);
                        btn_add.setText("+");
                        btn_add.setTextSize(20);

                        FancyButton btn_subtract = (FancyButton)v.findViewById(R.id.btn_subtract);
                        btn_subtract.setText("-");
                        btn_subtract.setTextSize(20);

                        FancyButton btn_multiply = (FancyButton)v.findViewById(R.id.btn_multiply);
                        btn_multiply.setText("*");
                        btn_multiply.setTextSize(20);

                        FancyButton btn_divide = (FancyButton)v.findViewById(R.id.btn_divide);
                        btn_divide.setText("/");
                        btn_divide.setTextSize(20);

                        FancyButton btn_power = (FancyButton)v.findViewById(R.id.btn_power);
                        btn_power.setText("^");
                        btn_power.setTextSize(20);

                        FancyButton btn_decimal = (FancyButton)v.findViewById(R.id.btn_decimal);
                        btn_decimal.setText(".");
                        btn_decimal.setTextSize(20);

                        FancyButton btn_open_parenthesis = (FancyButton)v.findViewById(R.id.btn_open_parenthesis);
                        btn_open_parenthesis.setText("(");
                        btn_open_parenthesis.setTextSize(20);

                        FancyButton btn_closing_parenthesis = (FancyButton)v.findViewById(R.id.btn_closing_parenthesis);
                        btn_closing_parenthesis.setText(")");
                        btn_closing_parenthesis.setTextSize(20);

                        FancyButton btn_open_brace = (FancyButton)v.findViewById(R.id.btn_open_brace);
                        btn_open_brace.setText("{");
                        btn_open_brace.setTextSize(20);

                        FancyButton btn_closing_brace = (FancyButton)v.findViewById(R.id.btn_closing_brace);
                        btn_closing_brace.setText("}");
                        btn_closing_brace.setTextSize(20);

                        FancyButton btn_backspace = (FancyButton)v.findViewById(R.id.btn_backspace);
                        btn_backspace.setIconResource(R.drawable.sym_keyboard_delete);
                        btn_backspace.setTextSize(20);

                        FancyButton btn_clear = (FancyButton)v.findViewById(R.id.btn_clear);
                        btn_clear.setText("C");
                        btn_clear.setTextSize(20);

                        FancyButton btn_var_w = (FancyButton)v.findViewById(R.id.btn_var_w);
                        btn_var_w.setText("w");
                        btn_var_w.setTextSize(20);

                        FancyButton btn_var_x = (FancyButton)v.findViewById(R.id.btn_var_x);
                        btn_var_x.setText("x");
                        btn_var_x.setTextSize(20);

                        FancyButton btn_var_y = (FancyButton)v.findViewById(R.id.btn_var_y);
                        btn_var_y.setText("y");
                        btn_var_y.setTextSize(20);

                        FancyButton btn_var_z = (FancyButton)v.findViewById(R.id.btn_var_z);
                        btn_var_z.setText("z");
                        btn_var_z.setTextSize(20);

                        FancyButton btn_var_a = (FancyButton)v.findViewById(R.id.btn_var_a);
                        btn_var_a.setText("a");
                        btn_var_a.setTextSize(20);

                        FancyButton btn_var_b = (FancyButton)v.findViewById(R.id.btn_var_b);
                        btn_var_b.setText("b");
                        btn_var_b.setTextSize(20);

                        FancyButton btn_var_c = (FancyButton)v.findViewById(R.id.btn_var_c);
                        btn_var_c.setText("c");
                        btn_var_c.setTextSize(20);

                        FancyButton btn_var_d = (FancyButton)v.findViewById(R.id.btn_var_d);
                        btn_var_d.setText("d");
                        btn_var_d.setTextSize(20);

                        btn_one.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    input_problem.getText().insert(input_problem.getSelectionStart(), "1");
                                    input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "2");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_three.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "3");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_four.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "4");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_five.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "5");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_six.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "6");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_seven.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "7");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_eight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "8");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_nine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "9");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_zero.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "0");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_left_shift.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (input_problem.getSelectionStart() == 0) {
                                    input_problem.setSelection(0);
                                } else {
                                    input_problem.setSelection(input_problem.getSelectionEnd() - 1);
                                }
                            }
                        });

                        btn_right_shift.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (input_problem.getSelectionEnd() == input_problem.length()) {
                                    input_problem.setSelection(input_problem.length());
                                } else {
                                    input_problem.setSelection(input_problem.getSelectionEnd() + 1);
                                }
                            }
                        });

                        btn_var_w.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "w");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_x.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "x");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "y");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_z.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "z");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "a");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "b");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "c");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_var_d.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "d");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "+");
                                input_problem.setSelection(input_problem.getSelectionStart());                            }
                        });

                        btn_subtract.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "-");
                                input_problem.setSelection(input_problem.getSelectionStart());                            }
                        });

                        btn_multiply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "*");
                                input_problem.setSelection(input_problem.getSelectionStart());                            }
                        });

                        btn_divide.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "/");
                                input_problem.setSelection(input_problem.getSelectionStart());                            }
                        });

                        btn_power.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "^");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_decimal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), ".");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_open_parenthesis.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "(");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_closing_parenthesis.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), ")");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_backspace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int cursorPosition = input_problem.getSelectionStart();
                                if (cursorPosition > 0) {
                                    input_problem.setText(input_problem.getText().delete(cursorPosition - 1, cursorPosition));
                                    input_problem.setSelection(cursorPosition-1);
                                }
                            }
                        });

                        btn_clear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.setText("");
                            }
                        });

                        btn_open_brace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "{");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        btn_closing_brace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                input_problem.getText().insert(input_problem.getSelectionStart(), "}");
                                input_problem.setSelection(input_problem.getSelectionStart());
                            }
                        });

                        validate_problem_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String equation = input_problem.getText().toString();

                                if (check(equation)) {
                                    new LovelyCustomDialog(mContext)
                                            .setIcon(R.drawable.aica)
                                            .setTitle("SOLVE THIS EQUATION?")
                                            .setView(R.layout.input_mode_problem_view)
                                            .setTitleGravity(1)
                                            .setTopColorRes(R.color.darkGreen)
                                            .setCancelable(false)
                                            .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                                @Override
                                                public void configureView(View v) {
                                                    MathView input_mode_problem = (MathView) v.findViewById(R.id.input_mode_problem);

                                                    input_mode_problem.setText("$$" + equation + "$$");
                                                }
                                            })
                                            .setListener(R.id.submit_problem_btn, true, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent i = new Intent(mContext, SolveProblemActivity.class);

                                                    String resultString;

                                                    ExprEvaluator util = new ExprEvaluator();
                                                    EvalEngine engine = util.getEvalEngine();
                                                    engine.setStepListener(new StepListener());
                                                    IExpr result = util.evaluate(equation);

                                                    if (result.toString().contains("{") && result.toString().contains("}")) {
                                                        resultString = result.toString().substring(1, result.toString().length()-1);
                                                    } else {
                                                        resultString = result.toString();
                                                    }

                                                    System.out.println("Result: " + resultString);

                                                    i.putExtra("generated", false);
                                                    i.putExtra("equation", equation);
                                                    i.putExtra("result", resultString);

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

    public boolean check(String equation) {
        Boolean match;

        try {
            match = true;

            if (equation.matches("^-?\\d+$")) {
                match = false;
            }
            if (equation.matches("(\\w+)")) {
                match = false;
            }

            ExprEvaluator util = new ExprEvaluator();
            EvalEngine engine = util.getEvalEngine();
            engine.setStepListener(new StepListener());
            IExpr result = util.evaluate(equation);
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
}

package edu.its.solveexponents.teacheraica.algo;

import java.util.Random;

/**
 * Created by jairus on 12/14/16.
 */

public class Randomizer {
    /*
    Each skill set will be having 12 equations overall in 4 levels of difficulty, having 3 equations each level
        1. Base Raised to Zero:
            Level 1 (2 Items): [s1][a]^0
            Level 2 (2 Items): ([s1][a]^0) [s2] ([s1][a]^0)
            Level 3 (2 Items): ([s1][a][v1]^0) [s2] ([s3][b][v2]^0)
            Level 4 (2 Items): (([s1][a][v1]^0)^[e1]) [s2] (([s3][b][v1]^0)^[e2])

        2. Addition of Exponents with Same Bases
            Level 1 (4 Items): ([v1]^[e1]) * ([v1]^[e2]) -
            Level 2 (4 Items): ([a][v1]^[e1]) * ([b][v1]^[e2]) -
            Level 3 (5 Items): ([s1][a][v1]^[e1]) * ([s3][b][v1]^[e2]) -
            Level 4 (5 Items): (([s1][a][v1]^[e1])^[e3]) * (([s3][b][v1]^[e2])^[e4]) -

        3. Multiplication of Bases with the Same Exponents
            Level 1 (4 Items): ([s1][a]^[e1]) * ([s2][b]^[e1]) -
            Level 2 (4 Items): ([s1][a][v1]^[e1]) * ([s3][b][v1]^[e1]) -
            Level 3 (5 Items): (([s1][a][v1]^[e1])^[e3]) * (([s3][b][v1]^[e2])^[e3]) -
            Level 4 (5 Items): (([s1][a][v1]^[e1]) * ([s3][b][v1]^[e1])) [s2] (([s4][c][v1]^[e1]) * ([s5][d][v1]^[e1])) -

        4. Multiplication of Exponents to Find the Power of a Power
            Level 1 (4 Items): ([a]^[e1])^[e2] -
            Level 2 (4 Items): ([s1][a][v1]^[e1])^[e2] -
            Level 3 (5 Items): (([a][v1]^[e1])^[e2]) [s2] (([b][v1]^[e3])^[e4]) -
            Level 4 (5 Items): (([s1][a][v1]^[e1])^[e2]) [s2] (([s3][b][v1]^[e3])^[e4]) -

        5. Subtraction of Exponents
            Level 1 (4 Items): ([a]^[e1]) / ([b]^[e2]) -
            Level 2 (5 Items): ([a][v1]^[e1]) / ([b][v1]^[e2]) -
            Level 3 (5 Items): ([s1][a][v1]^[e1]) / ([s3][b][v1]^[e2]) -
            Level 4 (5 Items): (([s1][a][v1]^[e1])^[e3]) / (([s3][b][v1]^[e2])^[e4]) -

        6. Negative Integer Exponents
            Level 1 (4 Items): [a]^(-[e1]) -
            Level 2 (5 Items): ([s1][a][v1])^(-[e1]) -
            Level 3 (5 Items): (([s1][a][v1])^(-[e1])) [s2] (([s3][b][v1])^(-[e2])) -
            Level 4 (5 Items): ((([s1][a][v1])^([s7][e1]))^([s8][e3])) [s6] ((([s3][b][v1])^([s9][e2]))^([s10][e4]))

    */

    public static String getRandomEquation(int level, int sublevel) {
        String equation = "";

        switch (level) {
            case 1:
                equation = generateLevel1(sublevel);
                break;
            case 2:
                equation = generateLevel2(sublevel);
                break;
            case 3:
                equation = generateLevel3(sublevel);
                break;
            case 4:
                equation = generateLevel4(sublevel);
                break;
        }

        return equation;
    }

    private static String generateLevel1(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String equation = "";
        String a = "";
        String b = "";
        String e1 = "";
        String e2 = "";
        String v1 = variables[random.nextInt(variables.length)];
        String s1 = random_plus_minus_sign(false);
        String s3 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                //Z
                equation = "[s1][a]^0";
                a = random_integer(false, 1, 1000);
                break;
            case 2:
                //Addition of Exponents with Same Bases
                //AE
                equation = "([v1]^[e1]) * ([v1]^[e2])";
                e1 = random_integer(false, 2, 9);
                e2 = random_integer(false, 2, 9);
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                //MB
                equation = "([s1][a]^[e1]) * ([s3][b]^[e1])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 1, 2);
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                //ME
                equation = "([a]^[e1])^[e2]";
                a = random_integer(false, 1, 3);
                e1 = random_integer(false, 1, 2);
                e2 = random_integer(false, 1, 2);
                break;
            case 5:
                //Subtraction of Exponents
                //SE
                equation = "([a]^[e1]) / ([b]^[e2])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 2);
                e2 = random_integer(false, 2, 2);
                break;
            case 6:
                //Negative Integer Exponents
                //N
                equation = "[a]^(-[e1])";
                a = random_integer(false, 1, 4);
                e1 = random_integer(false, 2, 2);
                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[b]", b)
                .replace("[s1]", s1)
                .replace("[s3]", s3)
                .replace("[e1]", e1)
                .replace("[e2]", e2)
                .replace("[v1]", v1);

        System.out.println(equation);

        return equation;
    }

    private static String generateLevel2(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String equation = "";
        String a = "";
        String b = "";
        String e1 = "";
        String e2 = "";
        String v1 = variables[random.nextInt(variables.length)];
        String s1 = random_plus_minus_sign(false);
        String s2 = random_all_sign();
        String s3 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                //Z
                equation = "([s1][a]^0) [s2] ([s3][b]^0)";
                a = random_integer(false, 1, 1000);
                b = random_integer(false, 1, 1000);
                break;
            case 2:
                //Addition of Exponents with Same Bases
                //AE
                equation = "([a][v1]^[e1]) * ([b][v1]^[e2])";
                a = random_integer(true, 1, 4);
                b = random_integer(true, 1, 4);
                e1 = random_integer(false, 2, 3);
                e2 = random_integer(false, 2, 3);
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                //MB, AE
                equation = "([s1][a][v1]^[e1]) * ([s3][b][v1]^[e1])";
                a = random_integer(false, 1, 4);
                b = random_integer(false, 1, 4);
                e1 = random_integer(false, 2, 3);
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                //ME
                equation = "([s1][a][v1]^[e1])^[e2]";
                a = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 2);
                e2 = random_integer(false, 2, 2);
                break;
            case 5:
                //Subtraction of Exponents
                //SE
                equation = "([a][v1]^[e1]) / ([b][v1]^[e2])";
                a = random_integer(false, 1, 4);
                b = random_integer(false, 1, 4);
                e1 = random_integer(false, 4, 20);
                e2 = random_integer(false, 2, 4);
                break;
            case 6:
                //Negative Integer Exponents
                //N
                equation = "([s1][a][v1])^(-[e1])";
                a = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 2);
                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[b]", b)
                .replace("[e1]", e1)
                .replace("[e2]", e2)
                .replace("[s1]", s1)
                .replace("[s2]", s2)
                .replace("[s3]", s3)
                .replace("[v1]", v1);

        System.out.println(equation);

        return equation;
    }

    private static String generateLevel3(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String v1 = variables[random.nextInt(variables.length)];
        String v2 = variables[random.nextInt(variables.length)];
        String equation = "";
        String a = "";
        String b = "";
        String e1 = "";
        String e2 = "";
        String e3 = "";
        String e4 = "";
        String s1 = random_plus_minus_sign(false);
        String s2 = random_all_sign();
        String s3 = random_plus_minus_sign(false);
        String s4 = random_times_divide_sign();

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                //Z
                equation = "([s1][a][v1]^0) [s2] ([s3][b][v2]^0)";
                a = random_integer(true, 1, 9);
                b = random_integer(true, 1, 9);
                break;
            case 2:
                //Addition of Exponents with Same Bases
                //AE
                equation = "([s1][a][v1]^[e1]) * ([s3][b][v1]^[e2])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                //MB, ME
                equation = "(([s1][a][v1]^[e1])^[e2]) * (([s3][b][v1]^[e1])^[e2])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                //ME
                equation = "(([a][v1]^[e1])^[e2]) [s2] (([b][v1]^[e3])^[e4])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 2);
                e2 = random_integer(false, 2, 2);
                e3 = random_integer(false, 2, 2);
                e4 = random_integer(false, 2, 2);
                break;
            case 5:
                //Subtraction of Exponents
                //SE
                equation = "([s1][a][v1]^[e1]) / ([s3][b][v1]^[e2])";
                a = random_integer(false, 1, 4);
                b = random_integer(false, 1, 4);
                e1 = random_integer(false, 4, 20);
                e2 = random_integer(false, 0, 4);
                break;
            case 6:
                //Negative Integer Exponents
                //N
                equation = "(([a][v1])^(-[e1])) [s4] (([b][v1])^(-[e2]))";
                a = random_integer(false, 2, 3);
                b = random_integer(false, 2, 3);
                e1 = random_integer(false, 2, 2);
                e2 = random_integer(false, 2, 2);
                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[b]", b)
                .replace("[e1]", e1)
                .replace("[e2]", e2)
                .replace("[e3]", e3)
                .replace("[e4]", e4)
                .replace("[s1]", s1)
                .replace("[s2]", s2)
                .replace("[s3]", s3)
                .replace("[s4]", s4)
                .replace("[v1]", v1)
                .replace("[v2]", v2);

        System.out.println(equation);

        return equation;
    }

    private static String generateLevel4(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String v1 = variables[random.nextInt(variables.length)];
        String equation = "";
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e1 = "";
        String e2 = "";
        String e3 = "";
        String e4 = "";
        String s1 = random_plus_minus_sign(false);
        String s2 = random_all_sign();
        String s3 = random_plus_minus_sign(false);
        String s4 = random_plus_minus_sign(false);
        String s5 = random_plus_minus_sign(false);
        String s6 = random_plus_minus_sign(true);
        String s7 = random_plus_minus_sign(false);
        String s8 = random_plus_minus_sign(false);
        String s9 = random_plus_minus_sign(false);
        String s10 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                //Z, ME
                equation = "(([s1][a][v1]^0)^[e1]) [s2] (([s3][b][v1]^0)^[e2])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                break;
            case 2:
                //Addition of Exponents with Same Bases
                //AE, ME
                equation = "(([s1][a][v1]^[e1])^[e3]) * (([s3][b][v1]^[e2])^[e3])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                e3 = random_integer(false, 0, 3);
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                //MB, AE
                equation = "(([s1][a][v1]^[e1]) * ([s3][b][v1]^[e1])) [s2] (([s4][c][v1]^[e1]) * ([s5][d][v1]^[e1]))";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                c = random_integer(true, 1, 3);
                d = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                //ME
                equation = "(([a][v1]^[e1])^[e2]) [s6] (([b][v1]^[e3])^[e4])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 2);
                e2 = random_integer(false, 2, 2);
                e3 = random_integer(false, 2, 2);
                e4 = random_integer(false, 2, 2);
                break;
            case 5:
                //Subtraction of Exponents
                //SE, ME
                equation = "(([s1][a][v1]^[e1])^[e3]) / (([s3][b][v1]^[e2])^[e4])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 2, 3);
                e2 = random_integer(false, 2, 2);
                e3 = random_integer(false, 2, 3);
                e4 = random_integer(false, 2, 2);
                break;
            case 6:
                //Negative Integer Exponents
                //N, ME
                equation = "((([s1][a][v1])^([s7][e1]))^([s8][e3])) [s6] ((([s3][b][v1])^([s9][e2]))^([s10][e4]))";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(false, 1, 3);
                e2 = random_integer(false, 1, 3);
                e3 = random_integer(false, 1, 3);
                e4 = random_integer(false, 1, 3);
                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[b]", b)
                .replace("[c]", c)
                .replace("[d]", d)
                .replace("[e1]", e1)
                .replace("[e2]", e2)
                .replace("[e3]", e3)
                .replace("[e4]", e4)
                .replace("[s1]", s1)
                .replace("[s2]", s2)
                .replace("[s3]", s3)
                .replace("[s4]", s4)
                .replace("[s5]", s5)
                .replace("[s6]", s6)
                .replace("[s7]", s7)
                .replace("[s8]", s8)
                .replace("[s9]", s9)
                .replace("[s10]", s10)
                .replace("[v1]", v1);

        System.out.println(equation);
        return equation;
    }

    private static String random_integer(boolean is_a_coefficient, int start, int end) {
        Random random = new Random();
        String string = String.valueOf(random.nextInt(end) + start);

        if (is_a_coefficient) {
            string = (string.equals(("1")) ? "" : string);
        }

        return string;
    }

    private static String random_plus_minus_sign(boolean isInBetween) {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        String sign;

        if (randomNum < 3) {
            sign = "+";
        } else {
            sign = "-";
        }

        if (sign.equals("+") && !isInBetween) {
            sign = "";
        }

        return sign;
    }

    private static String random_times_divide_sign() {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        String sign;

        if (randomNum < 3) {
            sign = "*";
        } else {
            sign = "/";
        }

        return sign;
    }

    private static String random_all_sign() {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        String sign;

        if (randomNum == 3) {
            sign = "+";
        } else if (randomNum == 2) {
            sign = "-";
        } else if (randomNum == 1) {
            sign = "*";
        } else {
            sign = "/";
        }

        return sign;
    }

    public static String getHint(int level, int sublevel) {
        String hint = "";

        switch (level) {
            case 1:
                hint = generateHintLevel1(sublevel);
                break;
            case 2:
                hint = generateHintLevel2(sublevel);
                break;
            case 3:
                hint = generateHintLevel3(sublevel);
                break;
            case 4:
                hint = generateHintLevel4(sublevel);
                break;
        }

        return hint;
    }

    private static String generateHintLevel1(int sublevel) {
        String hint = "";

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                hint = "Base Raised to Zero";
                break;
            case 2:
                //Addition of Exponents with the Same Bases
                hint = "Addition of Exponents with the Same Bases";
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                hint = "Multiplication of Bases with the Same Exponents";
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                hint = "Multiplication of Exponents to Find the Power of a Power";
                break;
            case 5:
                //Subtraction of Exponents
                hint = "Subtraction of Exponents";
                break;
            case 6:
                //Negative Integer Exponents
                hint = "Negative Integer Exponents";
                break;
        }

        return hint;
    }

    private static String generateHintLevel2(int sublevel) {
        String hint ="";

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                hint = "Base Raised to Zero";
                break;
            case 2:
                //Addition of Exponents with the Same Bases
                hint = "Addition of Exponents with the Same Bases";
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                hint = "Multiplication of Bases with the Same Exponents, Addition of Exponents with the Same Bases";
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                hint = "Multiplication of Exponents to Find the Power of a Power";
                break;
            case 5:
                //Subtraction of Exponents
                hint = "Subtraction of Exponents";
                break;
            case 6:
                //Negative Integer Exponents
                hint = "Negative Integer Exponents";
                break;
        }

        return hint;
    }

    private static String generateHintLevel3(int sublevel) {
        String hint = "";

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                hint = "Base Raised to Zero";
                break;
            case 2:
                //Addition of Exponents with the Same Bases
                hint = "Addition of Exponents with the Same Bases";
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                hint = "Multiplication of Bases with the Same Exponents, Multiplication of Exponents to Find the Power of a Power";
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                hint = "Multiplication of Exponents to Find the Power of a Power";
                break;
            case 5:
                //Subtraction of Exponents
                hint = "Subtraction of Exponents";
                break;
            case 6:
                //Negative Integer Exponents
                hint = "Negative Integer Exponents";
                break;
        }

        return hint;
    }

    private static String generateHintLevel4(int sublevel) {
        String hint = "";

        switch (sublevel) {
            case 1:
                //Base Raised to Zero
                hint = "Base Raised to Zero, Multiplication of Exponents to Find the Power of a Power";
                break;
            case 2:
                //Addition of Exponents with the Same Bases
                hint = "Addition of Exponents with the Same Bases, Multiplication of Exponents to Find the Power of a Power";
                break;
            case 3:
                //Multiplication of Bases with the Same Exponents
                hint = "Multiplication of Bases with the Same Exponents, Addition of Exponents with the Same Bases";
                break;
            case 4:
                //Multiplication of Exponents to Find the Power of a Power
                hint = "Multiplication of Exponents to Find the Power of a Power";
                break;
            case 5:
                //Subtraction of Exponents
                hint = "Subtraction of Exponents, Multiplication of Exponents to Find the Power of a Power";
                break;
            case 6:
                //Negative Integer Exponents
                hint = "Negative Integer Exponents, Multiplication of Exponents to Find the Power of a Power";
                break;
        }

        return hint;
    }



}
package edu.its.solveexponents.teacheraica.algo;

import java.util.Random;

/**
 * Created by jairus on 12/14/16.
 */

public class Randomizer {

    /*
    Each skill set will be having 12 equations overall in 4 levels of difficulty, having 3 equations each level
    variables = {a-d, w-z}

        1. Positive Integer Exponents:
            Level 1: [s1][a]^[e1]
            1. 2^2 = 4 -
            2. 7^2 = 49 -
            3. 5^2 = 25 -
            4. 4^3 = 64 -
            5. 5^3 = 125 -
            6. 10^6 = 1000000 -
            Level 2: ([s1][a]^[e1]) [s2] ([s3][b]^[e2])
            4. 5^2 - 4^2 = 9 -
            5. 3^4 + 2^5 = 113 -
            6. 2^2 * 3^3 = 108 -
            Level 3: ([s1][a][v]^[e1]) [s2] ([s3][b][v]^[e2])
            7. x^2 + (x^12)^0 + 24x^0 = x^2 + 25 -
            8. 3x^3 + 2x^3 = 5x^3 -
            9. (-3)^4 = 81 -
            Level 4: (([s1][a][v1]^[e1])^[e3]) [s2] (([s3][b][v1]^[e2])^[e4])
            10. (3x^10 + 5x^10 + 10x^10)/(2x^10) = 9 -
            11. 2^5 + 3x^2 = 3x^2 + 32 -
            12. (-2)^3 * (-3)^2 = -72 -

        2. Base Raised to Zero:
            Level 1: [s1][a]^0
            1. 3^0 = 1 -
            2. 199^0 = 1 -
            3. 100000000^0 = 1 -
            4. (10^6)^0 = 1 -
            5. ((5^2)^3)^0 = 1 -
            Level 2: ([s1][a]^0) [s2] ([s1][a]^0)
            4. 3x^0 + 2x^2 = 2x^2 + 3 -
            5. 4y^2 - 2x^0 = 4y^2 - 2 -
            6. (100x^0) / (50y^0) = 2 -
            Level 3: ([s1][a][v1]^0) [s2] ([s3][b][v2]^0)
            7. 3^4 - 200^0 = 80 -
            8. (-5)^0 + 3x^0 = 4 -
            9. (3x^2)(5x^0) + 3y^0 = 15x^2 + 3 -
            Level 4: (([s1][a][v1]^0)^[e1]) [s2] (([s3][b][v1]^0)^[e2])
            10. (4x^2)^0 = 1 -
            11. ((3x^4)^0)^3 = 1 -
            12. (2x^0)^(3+2) = 32 -

        3. Addition of Exponents with Same Bases
            Level 1:
            1. x^2 * x^4 = x^6 -
            2. y^3 * y^2 * y = y^6 -
            3. (2x^2) * (5x^2) = 10x^4 -
            4. x^5 * x^2 * x^3 = x^10 -
            5. y^4 * y^2 = y^6 -
            Level 2:
            4. 3x^2 * 5x^3 + 2x^2 = 15x^5 + 2x^2 -
            5. y^2 * 3y^3 * y^3 = 3y^8 -
            6. 5x^3 + 2x^3 * 4^0 = 7x^3 -
            Level 3:
            7. 6^2 + x^3 * y^2 * 2x^4 = 2x^7 * y^2 + 36
            8. x^5 * x + y^5 * y = (x + y)^6
            9. (x^3 * y^2) * (x * y^3) = x^4 * y^5 -
            Level 4:
            10. x^(3+y) * x^(3y-1) = x^{4y + 2}
            11. y^(2-x+3) * x^(5+x) = x^{x+5} * y^{x-5}
            12. (3x^4) * (-2x^4 * y) = -6x^8 * y

        4. Multiplication of Bases with the Same Exponents
            Level 1:
            1. 3^2 * 3^2 = 81
            2. 2^2 * 3^2 = 36
            3. 1^3 * 2^3 = 8
            4. 4^2 * 2^4 = 256
            5. 3^2 * 4^2 = 144
            Level 2:
            4. 2x^2 * x^2 = 2x^4
            5. x^2 * 2^2 = 4x^2
            6. 3x^3 * 5x^2 = 15x^5
            Level 3:
            7. ((x^2 * 2x^2)^2) * ((2x^2 * x^2)^2) = 16x^{16}
            8. ((x^2 * 5x^2)^2) * ((x^4)^2) = 25x^{16}
            9. ((x^3 * 2x^3)^2) * ((x^3 * x^3)^2) = 4x^{24}
            Level 4:
            10. ((x + y)^2) * ((x^2)^2) * (x^2) = (x + y)^2 * x^6
            11. ((x^0)^3) * (y^(2+1)) * (x^3) = x^3 * y^3
            12. (2y^3) * (3^3) * (y^3) = 54y^6

        5. Multiplication of Exponents to Find the Power of a Power
            Level 1:
            1. (2^2)^3 = 64
            2. (3^2)^2 = 81
            3. (2 + 4)^2 = 64
            4. (x^2)^3 = x^6
            5. ((2x)^2)^2 = 16x^4
            Level 2:
            4. (3x^2)^2 * (2x^3)^2 = 36x^{10}
            5. (x^3 * x^4)^2 = x^{14}
            6. (x^3 * y^2)^3 = x^9 * y^6
            Level 3:
            7. ((2 + x)^2)^3 = (2+x)^6     //Edit
            8. ((x^3 * y)^2) * (x * y^2) = x^7 * y^4
            9. ((x^3)^2) * ((2x * y)^3) = 8x^9 * y^3
            Level 4:
            10. ((2x^3) * ((3y^2)^2) = 18x^3 * y^4
            11. (2x^3 * y^2)^3 = 8x^9 * y^6
            12. ((x^2 * 2x^2)^2)^2 = 16x^16

        6. Subtraction of Exponents
            Level 1:
            1. (y^10) / (y^7) = y^3
            2. (x^5) / (x^2) = x^3
            3. (2x^2) / (2x^2) = 1
            4. (y^12) / (y^7) = y^5
            5. (x^3) / (x^2) = x
            Level 2:
            4. ((x + 3)^5) / ((x + 3)^6) = 1 / (3 + x)
            5. (60x^3) / (10x^3) = 6
            6. ((x^3) * y) / (x^3) = y
            Level 3:
            7. (2 / 3)^3 = 8 / 27
            8. (3 / x)^4 = 81 / x^4
            9. (x / y)^3 = x^3 / y^3
            Level 4:
            10. ((4x^2 * y^2)^3) / ((8x^3 * y)^2) = y^4
            11. ((2x^2) * (3x^3)^2) / ((6x^2) * (3x^2)) = x^4
            12. ((x^3 * y^2) / (2x)^2 = 1 / 4 * x * y^2

        7. Negative Integer Exponents
            Level 1:
            1. 2^(-3) = 1/8
            2. 3^(-2) = 1/3
            3. (2^(-2))^2 = 1/16
            4. 4^(-2) = 1/16
            5. 5^(-2) = 1/25
            6. (3^(-3))^(-2) = 81
            Level 2:
            4. (x^(-2))^3 = 1 / x^6
            5. x^3 * x^(-4) = 1 / x
            6. (x^(-4)) / (x^(-2)) = 1 / x^2
            Level 3:
            7. (2^(-2) * 3^(-2))^(-1) = 36
            8. 1 / (x^(-4)) = x^4
            9. (12x^4) / (48x^(-1)) = x^5 / 4
            Level 4:
            10. ((2x^(-1)) / (4x^2))^(-2) = 4x^6
            11. ((x^4) * y^(-3)) / (x * y^2) = x^3 / y^5
            12. ((-x)^2)^2 * (-x)^(-4) * ((-x)^(-9))^(-2) = x^18
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
        String equation = "";
        String a = "";
        String e1 = "";
        String s1 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Positive Integer Exponents
                equation = "[s1][a]^[e1]";
                a = random_integer(false, 1, 4);
                e1 = random_integer(false, 2, 3);
                break;
            case 2:
                //Base Raised to Zero
                equation = "[s1][a]^0";
                a = random_integer(false, 1, 1000);
                break;
            case 3:
                //Addition of Exponents with Same Bases

                break;
            case 4:
                //Multiplication of Bases with the Same Exponents

                break;
            case 5:
                //Multiplication of Exponents to Find the Power of a Power

                break;
            case 6:
                //Subtraction of Exponents

                break;
            case 7:
                //Negative Integer Exponents

                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[s1]", s1)
                .replace("[e1]", e1);

        System.out.println(equation);

        return equation;
    }

    private static String generateLevel2(int sublevel) {
        String equation = "";
        String a = "";
        String b = "";
        String e1 = "";
        String e2 = "";
        String s1 = random_plus_minus_sign(false);
        String s2 = random_all_sign();
        String s3 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Positive Integer Exponents
                equation = "([s1][a]^[e1]) [s2] ([s3][b]^[e2])";
                a = random_integer(false, 1, 3);
                b = random_integer(false, 1, 3);
                e1 = random_integer(true, 2, 2);
                e2 = random_integer(true, 2, 2);
                break;
            case 2:
                //Base Raised to Zero
                equation = "([s1][a]^0) [s2] ([s3][b]^0)";
                a = random_integer(false, 1, 1000);
                b = random_integer(false, 1, 1000);
                break;
            case 3:
                //Addition of Exponents with Same Bases

                break;
            case 4:
                //Multiplication of Bases with the Same Exponents

                break;
            case 5:
                //Multiplication of Exponents to Find the Power of a Power

                break;
            case 6:
                //Subtraction of Exponents

                break;
            case 7:
                //Negative Integer Exponents

                break;
        }

        equation = equation.replace("[a]", a)
                .replace("[b]", b)
                .replace("[e1]", e1)
                .replace("[e2]", e2)
                .replace("[s1]", s1)
                .replace("[s2]", s2)
                .replace("[s3]", s3);

        System.out.println(equation);

        return equation;
    }

    private static String generateLevel3(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String v1 = variables[random.nextInt(variables.length)];
        String equation = "";
        String a = "";
        String b = "";
        String e1 = "";
        String e2 = "";
        String s1 = random_plus_minus_sign(false);
        String s2 = random_all_sign();
        String s3 = random_plus_minus_sign(false);

        switch (sublevel) {
            case 1:
                //Positive Integer Exponents
                equation = "([a][v1]^[e1]) [s2] ([b][v1]^[e2])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                break;
            case 2:
                //Base Raised to Zero
                equation = "([s1][a][v1]^0) [s2] ([s3][b][v2]^0)";
                a = random_integer(true, 1, 9);
                b = random_integer(true, 1, 9);
                break;
            case 3:
                //Addition of Exponents with Same Bases

                break;
            case 4:
                //Multiplication of Bases with the Same Exponents

                break;
            case 5:
                //Multiplication of Exponents to Find the Power of a Power

                break;
            case 6:
                //Subtraction of Exponents

                break;
            case 7:
                //Negative Integer Exponents

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

    private static String generateLevel4(int sublevel) {
        String[] variables = {"a", "b", "c", "d", "w", "x", "y", "z"};
        Random random = new Random();
        String v1 = variables[random.nextInt(variables.length)];
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

        switch (sublevel) {
            case 1:
                //Positive Integer Exponents
                equation = "(([s1][a][v1]^[e1])^[e3]) [s2] (([s3][b][v1]^[e2])^[e4])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                e3 = random_integer(false, 0, 3);
                e4 = random_integer(false, 0, 3);
                break;
            case 2:
                //Base Raised to Zero
                equation = "(([s1][a][v1]^0)^[e1]) [s2] (([s3][b][v1]^0)^[e2])";
                a = random_integer(true, 1, 3);
                b = random_integer(true, 1, 3);
                e1 = random_integer(false, 0, 3);
                e2 = random_integer(false, 0, 3);
                break;
            case 3:
                //Addition of Exponents with Same Bases

                break;
            case 4:
                //Multiplication of Bases with the Same Exponents

                break;
            case 5:
                //Multiplication of Exponents to Find the Power of a Power

                break;
            case 6:
                //Subtraction of Exponents

                break;
            case 7:
                //Negative Integer Exponents

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
        } else  {
            sign = "/";
        }

        return sign;
    }
}
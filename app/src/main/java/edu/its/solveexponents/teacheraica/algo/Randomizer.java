package edu.its.solveexponents.teacheraica.algo;

import java.util.Random;

/**
 * Created by jairus on 12/14/16.
 */

public class Randomizer {

    /*
    Each skill set will be having 12 equations overall in 4 levels of difficulty, having 3 equations each level
        Positive Integer Exponents:
            Level 1:
            1. 2^2 = 4
            2. 7^2 = 49
            3. 5^2 = 25
            4. 4^3 = 64
            5. 5^3 = 125
            6. 10^6 = 1000000
            Level 2:
            4. 5^2 - 4^2 = 9
            5. 3^4 + 2^5 = 113
            6. 2^2 * 3^3 = 108
            Level 3:
            7. x^2 + x^12 + 24x^0 = x^2 + x^12 + 24
            8. 3x^3 + 2x^3 = 5x^3
            9. (-3)^4 = 81
            Level 4:
            10. 3x^10 + 5x^10 + 10y^10 / 2^0 = 8x^10 + 10y^10
            11. 2^5 + 3x^2 = 3x^2 + 32
            12. (-2)^5 * (-3)^2 = -288

        Base Raised to Zero:
            Level 1:
            1. 3^0 = 1
            2. 199^0 = 1
            3. 100000000^0 = 1
            4. (10^6)^0 = 1
            5. ((5^2)^3)^0 = 1
            Level 2:
            4. 3x^0 + 2x^2 = 2x^2 + 3
            5. 4y^2 - 2x^0 = 4y^2 - 2
            6. 100x^0 / 50y^0 = 2
            Level 3:
            7. 3^4 - 200^0 = 80
            8. (-5)^0 + 3x^0 = 4
            9. 3^2 * 5x^0 + 3y^0 = 48
            Level 4:
            10. (4x^2)^0 = 1
            11. ((3x^4)^0)^3 = 1
            12. (2x^(2*0))^(5*2) = 1024

        Addition of Exponents with Same Bases
            Level 1:
            1. x^2 * x^4 = x^6
            2. y^3 * y^2 * y = y^6
            3. (2x^2) * (5x^2) = 10x^4
            4. x^5 * x^2 * x^3 = x^10
            5. y^4 * y^2 = y^6
            Level 2:
            4. 3x^2 * 5x^3 + 2x^2 = 15x^5 + 2x^2
            5. 4y^2 * 3x^3 * 4y^3 = 48x^3 * y^5
            6. 5x^3 + 2x^2 * 4^0 = 5x^3 + 2x^2
            Level 3:
            7. 6^2 + x^3 * y^2 * 2x^4 = 2x^7 * y^2 + 36
            8. (x + y)^5 * (x + y) = (x + y)^6
            9. (x^3 * y^2) * (x * y^3) = x^4 * y^5
            Level 4:
            10. x^(3+y) * x^(3y-1) = x^{4y + 2}
            11. y^(2-x+3) * x^(5+x) = x^{x+5} * y^{x-5}
            12. (3x^4) * (-2x^4 * y) = -6x^8 * y

        Multiplication of Bases with the Same Exponents
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

        Multiplication of Exponents to Find the Power of a Power
            Level 1:
            1. (2^2)^3 = 64
            2. (3^2)^2 = 81
            3. (2 * 4)^2 = 64
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

        Subtraction of Exponents
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

        Negative Integer Exponents
            Level 1:
            1. 2^(-3) = 1/8
            2. 3^(-1) = 1/3
            3. (2^(-2))^2 = 1/16
            4. 4^(-2) = 1/16
            5. 5^(-2) = 1/25
            6. (3^(-3))^(-1) = 27
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
        Random random = new Random();
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
        String equation = "((4x^2 * y^2)^3) / ((8x^3 * y)^2)";

        return equation;
    }

    private static String generateLevel2(int sublevel) {
        String equation ="";

        return equation;

    }

    private static String generateLevel3(int sublevel) {
        String equation ="";

        return equation;

    }

    private static String generateLevel4(int sublevel) {
        String equation ="";

        return equation;

    }
}
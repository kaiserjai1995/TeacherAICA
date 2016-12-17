package edu.its.solveexponents.teacheraica.algo;

import android.widget.TextView;

import java.util.Random;

/**
 * Created by jairus on 12/14/16.
 */

public class Randomizer {

    /*
    Each skill set will be having 12 equations overall in 4 levels of difficulty, having 3 equations each level
        Positive Integer Exponents:
            Level 1:
            1. 2^2
            2. 7^2
            3. 5^2
            4. 4^3
            5. 5^3
            6. 10^6
            Level 2:
            4. 5^2 - 4^2
            5. 3^4 + 2^5
            6. 2^5 * 3^3
            Level 3:
            7. x^2 + x^12
            8. 3x^3 + 2x^3
            9. (-3)^4
            Level 4:
            10. 3x^10 + 5x^10 + 10y^10
            11. 2^5 + 3x^2
            12. (-2)^5 * (-3)^2

        Base Raised to Zero:
            Level 1:
            1. 3^0
            2. 199^0
            3. 100000000^0
            4. (10^6)^0
            5. ((5^2)^3)^0
            Level 2:
            4. 3x^0 + 2x^2
            5. 4y^2 - 2x^0
            6. 100x^0 / 50y^0
            Level 3:
            7. 3^4 - 200^0
            8. (-5)^0 + 3x^0
            9. 3^2 * 5x^0 + 3y^0
            Level 4:
            10. (4x^2)^0
            11. ((3x^4)^0)^3
            12. (2x^(2*0))^(5*2)

        Addition of Exponents with Same Bases
            Level 1:
            1. x^2 * x^4
            2. y^3 * y^2 * y
            3. (2x^2) * (5x^2)
            4. x^5 * x^2 * x^3
            5. y^4 * y^2
            Level 2:
            4. 3x^2 * 5x^3 + 2x^2
            5. 4y^2 * 3x^3 * 4y^3
            6. 5x^3 + 2x^2 * 4^0
            Level 3:
            7. 6^2 + x^3 * y^2 * 2x^4
            8. (x + y)^5 * (x + y)
            9. (x^3 * y^2) * (x * y^3)
            Level 4:
            10. x^(3+y) * x^(3y-1)
            11. y^(2-x+3) * x^(5+x)
            12. (3x^4) * (-2x^4 * y)

        Multiplication of Bases with the Same Exponents
            Level 1:
            1. 3^2 * 3^2
            2. 2^2 * 3^2
            3. 1^3 * 2^3
            4. 4^4 * 2^4
            5. 3^2 * 4^2
            Level 2:
            4. 2x^2 * x^2
            5. x^2 * 2^2
            6. 3x^3 * 5x^2
            Level 3:
            7. (3x^2 * 2x^2)^3 * (2x^2 * 3x^2)^3
            8. (x^2 * 5x^2)^2 * x^(4^2)
            9. (3x^3 * 3x^3)^2 * (2x^3 * 2x^3)^2
            Level 4:
            10. (x + y)^2 * (x^2 + y^3)^2 * x^2
            11. (x^0)^3 * y^(2+1) * x^3
            12. (y + 2)^3 * (x + 3)^3 * y^3

        Multiplication of Exponents to Find the Power of a Power
            Level 1:
            1. (2^3)^4
            2. (5^4)^2
            3. (3 * 4)^3
            4. ((x^3 * y)^2) * (x * y^2)
            5. ((x^3)^2) * ((2x * y)^3)
            Level 2:
            4. (x^7)^9
            5. (x^3 * x^4)^2
            6. ((3x)^2)^3
            Level 3:
            7. ((2 + x)^2)^3
            8. (3x^2)^2 * (2x^3)^2
            9. (x^3 * y^2)^3
            Level 4:
            10. ((2x^3) * (3y^2))^2
            11. (2x^3 * y^2)^5
            12. ((2x^2 * 5x^2)^2)^2

        Subtraction of Exponents
            Level 1:
            1. (y^10) / (y^7)
            2. (x^5) / (x^2)
            3. (2x^2) / (2x^2)
            4. (y^12) / (y^7)
            5. (x^3) / (x^2)
            Level 2:
            4. ((x + 3)^5) / ((x + 3)^6)
            5. (60x^3) / (10x^3)
            6. ((x^3) * y) / (x^3)
            Level 3:
            7. (2 / 3)^3
            8. (3 / m)^4
            9. (x / y)^3
            Level 4:
            10. ((4x^2 * y^2)^3) / ((8x^3 * y)^2)
            11. (((2x^2) * ((3x^3)^2)) / ((6x^2) * (3x^2)))
            12. ((x^3 * y^2) / (2x))^4

        Negative Integer Exponents
            Level 1:
            1. 2^(-3)
            2. 3^(-1)
            3. (2^(-2))^2
            4. 4^(-2)
            5. 5^(-3)
            6. (3^(-3))^(-1)
            Level 2:
            4. (x^(-2))^3
            5. x^3 * x^(-4)
            6. (x^(-4)) / (x^(-2))
            Level 3:
            7. (2^(-2) * 3^(-2))^(-1)
            8. 1 / (s^(-4))
            9. (12x^4) / (48x^(-1))
            Level 4:
            10. ((2x^(-1)) / (4x^2))^(-2)
            11. ((x^4) * y^(-3)) / (x * y^2)
            12. (((-x)^2)^2) * ((-x)^(-4)) * ((-x)^(-9))^(-2)

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
        String equation = "2^2";

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
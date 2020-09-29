package com.company;

/**
 * <p>
 *     This solves math problems where there's a given a set of numbers that can be added, subtracted, multiplied, or
 *     divided so that they equal a certain target number. Each number must be used exactly once. This brute-forces
 *     every possible equation, although it DOES NOT ACCOUNT FOR PARENTHESES. As such, it can miss possible solutions.
 * </p>
 * <p>
 *     I coded this for a competition that involved a few of these puzzles, so my main priority was on functionality.
 *     This could probably be done in a more efficient manner, and I didn't spend so long on cleaning up my code or
 *     writing clarifying comments. If anything is unclear, please let me know, and I'll be happy to clarify.
 * </p>
 *
 * @author IsaacL
 */
public class MathPuzzleSolver {

    public static void solveProblem(int target, int[] numbersToUse) {
        testAllPossiblePermutations(numbersToUse.length, numbersToUse, target);
    }

    //Heap's algorithm for listing all the permutations of a list
    private static void testAllPossiblePermutations(int n, int[] numbers, int target) {

        if (n == 1) {
            MathOperation[] operations = new MathOperation[numbers.length - 1];
            tryEachOperationCombination(operations.length - 1, operations, numbers, target);
        } else {
            for (int i = 0; i < n - 1; i++) {
                testAllPossiblePermutations(n - 1, numbers, target);
                if (n % 2 == 0) {
                    swap(numbers, i, n - 1);
                } else {
                    swap(numbers, 0, n - 1);
                }
            }
            testAllPossiblePermutations(n - 1, numbers, target);
        }
    }

    private static void swap(int[] numbers, int num1, int num2) {
        int tmp = numbers[num1];
        numbers[num1] = numbers[num2];
        numbers[num2] = tmp;
    }

    private static void tryEachOperationCombination(int levelsLeft, MathOperation[] operations, int[] numbers, int target) {
        for (MathOperation operation : MathOperation.values()) {
            operations[levelsLeft] = operation;
            if (levelsLeft == 0) {
                EvaluateEquation(numbers, operations, target);
            } else {
                tryEachOperationCombination(levelsLeft - 1, operations, numbers, target);
            }
        }
    }

    private static void EvaluateEquation(int[] numbers, MathOperation[] operations, int target) {
        double[] temporaryValues = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            temporaryValues[i] = numbers[i];
        }

        for (int i = 0; i < operations.length; i++) {
            temporaryValues[i + 1] = runCalculation(temporaryValues[i], temporaryValues[i + 1], operations[i]);
        }

        double result = temporaryValues[temporaryValues.length - 1];

        //If the absolute difference is less than 0.01, that means that the solution is likely correct.
        if (Math.abs(target - result) < 0.01) {
            printSolution(numbers, operations);
        }
    }

    public static double runCalculation(double a, double b, MathOperation operation) {
        switch (operation) {
            case plus:
                return a + b;
            case minus:
                return a - b;
            case times:
                return a * b;
            case dividedBy:
                return a / b;
            default:
                throw new IllegalArgumentException("The only legal operations are addition, subtraction, multiplication, and division.");
        }
    }

    private static void printSolution(int[] numbers, MathOperation[] operations) {
        for (int i = 0; i < operations.length; i++) {
            System.out.print(numbers[i] + " " + operations[i] + " ");
        }
        System.out.println(numbers[numbers.length - 1]);
        System.out.println();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatordemo;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author TESLA_CN
 */
public class Arithmetic {

    private static final char[] OPSET = {
        '+', '-', '*', '/', '(', ')', '#'};
    private static final char[][] PRIOR = {
        //        +    -    *    /    (    )    #
        {'>', '>', '<', '<', '<', '>', '>'},// +
        {'>', '>', '<', '<', '<', '>', '>'},// -
        {'>', '>', '>', '>', '<', '>', '>'},// *
        {'>', '>', '>', '>', '<', '>', '>'},// /
        {'<', '<', '<', '<', '<', '=', ' '},// (
        {'>', '>', '>', '>', ' ', '>', '>'},// )
        {'<', '<', '<', '<', '<', ' ', '='} // #
    };

    Stack<Character> symbol = new Stack<>();
    Stack<Double> number = new Stack<>();

    Arithmetic() {
    }

    public double EvaluateExpression(String expression) {

        return 0;
    }

    private String toReversePolishNotation(String expr) {
        String result = "";
        Scanner s = new Scanner(expr);
        while (s.hasNext()) {
            String temp = s.next();
            if (Character.isDigit(temp.charAt(0))) {
                result += temp;
                result += " ";
            }
            if (isSymbol(temp.charAt(0))) {
                symbol.push(temp.charAt(0));
            }
            try {
                if (temp.charAt(0) == ')' || precede(OPSET[returnOpOrd(temp.charAt(0))], symbol.peek()) != '>') {
                    while (symbol.peek() != '(') {
                        result += symbol.pop().toString();
                        result += " ";
                    }
                    symbol.pop();
                }
            } catch (EmptyStackException ex) {
                System.err.println("ERROR");
            }
        }
        while (!symbol.empty()) {
            result += symbol.pop();
            result += " ";
        }
        System.out.println(result);
        return result;
    }

    private boolean isSymbol(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(') {
            return true;
        }
        return false;
    }

    private char precede(char a, char b) {
        return PRIOR[returnOpOrd(a)][returnOpOrd(b)];
    }

    private int returnOpOrd(char op) {
        for (int i = 0; i < OPSET.length; i++) {
            if (op == OPSET[i]) {
                return i;
            }
        }
        return 0;
    }

    private boolean In(char ch) {
        for (int i = 0; i < OPSET.length; i++) {
            if (ch == OPSET[i]) {
                return true;
            }
        }
        return false;
    }

    private double Operation(double a, char theta, double b) {
        switch (theta) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return 0;
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Arithmetic a = new Arithmetic();
        Scanner input = new Scanner(System.in);
        a.toReversePolishNotation(input.nextLine());
    }
}

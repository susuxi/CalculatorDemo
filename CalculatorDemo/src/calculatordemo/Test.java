/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatordemo;

import java.util.*;

//输入格式:数字与算子之间空一格,最后用" #"结尾
public class Test {

    static Stack<Double> number = new Stack<>();
    static Stack<String> symbol = new Stack<>();
    static Map<String, Integer> cmp = new HashMap<>();

    public static void main(String[] args) {
        Test sample = new Test();
        symbol.push("#");
        cmp.put("+", 1);
        cmp.put("-", 1);
        cmp.put("*", 10);
        cmp.put("/", 10);
        cmp.put("(", 100);
        cmp.put(")", -100);
        cmp.put("#", -1000);

        System.out.println(cmp.get("53"));

        System.out.println(sample.solve(number, symbol));

    }

    public Double solve(Stack<Double> n, Stack<String> s) {
        Scanner input = new Scanner(System.in);
        String temp = input.next();
        while (temp != "#" || symbol.peek() != "#") {
            if (Character.isDigit(temp.charAt(0))) {
                Double temp_num = Double.parseDouble(temp);
                number.push(temp_num);
                temp = input.next();
            } else if (cmp.get(symbol.peek()) < cmp.get(temp)) {
                symbol.push(temp);
                temp = input.next();
            } else if (cmp.get(symbol.peek()) + cmp.get(temp) == 0) {
                symbol.pop();
                temp = input.next();
            } else if (temp == "+" || temp == "-" || temp == "*" || temp == "/") {
                Double b = number.pop();
                Double a = number.pop();
                number.push(calculate(a, temp, b));
            }
            System.out.print("1");
        }
        return number.peek();
    }

    public Double calculate(Double a, String s, Double b) {
        switch (s) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                return 0.0;
        }
    }

    //判断是否是小数
    public boolean isdigit(String judge) {
        boolean point = true;
        for (int i = 0; i < judge.length(); i++) {
            if (judge.charAt(i) == '.' || Character.isDigit(judge.charAt(i))) {
                if (judge.charAt(i) == '.' && point) {
                    point = false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}

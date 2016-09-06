/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatordemo;

import java.util.Scanner;

//import org.python.core.PyFunction;
//import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.util.PythonInterpreter;

/**
 *
 * @author TESLA_CN
 */
public class CalculatorOperation {

    private String expression = "";

    CalculatorOperation() {

    }

    public String getAnswer(String expr) {
        Scanner s = new Scanner(expr);
        while(s.hasNext()) {
            expression += s.next();
        }
        return Calculate();
    }

    private String Calculate() {
//        PythonInterpreter pyInterpreter = new PythonInterpreter();
//        pyInterpreter.exec("print Exec python");
//        pyInterpreter.execfile("src/calculatordemo/newfile.py");
//        PyFunction func = (PyFunction) pyInterpreter.get("Calculate", PyFunction.class);
//        PyObject pyObj = func.__call__(new PyString(expression));
//        return pyObj.toString();
        return "Will be Finished soon";
    }

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        CalculatorOperation c = new CalculatorOperation();
//        System.out.println(c.getAnswer(input.nextLine()));
    }
}

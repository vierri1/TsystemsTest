package com.tsystems.javaschool.tasks.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        String result;
        if (statement == null || statement == "") {
            return null;
        }
        if (!validate(statement)) {
            return null;
        }
        statement = statement.replaceAll(" ", ""); // remove the spaces
        statement = statement.replaceAll("-", "+-");
        if (statement.startsWith("+")) {
            statement = statement.substring(1);
        }
        String statementParentheses = findStatementParentheses(statement);
        while (!statementParentheses.equals("NoParentheses")) {
            String resultParentheses = resolve(statementParentheses);
            if (resultParentheses == null) {
                return null;
            }
            statement = statement.replace(statementParentheses, resultParentheses);
            statementParentheses = findStatementParentheses(statement);
        }
        result = resolve(statement);
        if (result == null) {
            return null;
        }
        if (result.endsWith(".0")) {
            //cut unsignificant zero
            result = result.substring(0, result.length() - 2);
        }
        else {
            //rounding result
            double doubleResult = Double.parseDouble(result);
            doubleResult = doubleResult * 10000;
            doubleResult = Math.round(doubleResult);
            doubleResult = doubleResult / 10000;
            result = String.valueOf(doubleResult);
        }
        return result;
    }

    public String resolve(String statement) {
        double result = 0;
        boolean isFirstMinus = false;
        statement = statement.replaceAll("\\(", "");
        statement = statement.replaceAll("\\)", "");
        while (statement.contains("*") || statement.contains("/") || statement.contains("-") || statement.contains("+")) {
            for (int i = 0; i < statement.length(); i++) {
                char ch = statement.charAt(i);
                if (statement.contains("*") || statement.contains("/")) {
                    if (ch == '*' || ch == '/') {
                        String subStatement = findSubstatement(statement, i);
                        try {
                        double firstNumber = findNumber(statement, i, false);
                        double secondNumber = findNumber(statement, i, true);
                            if (ch == '*') {
                                result = firstNumber * secondNumber;
                            } else {
                                result = firstNumber / secondNumber;
                            }
                        }catch (Exception ex) {
                            return null;
                        }
                        statement = statement.replace(subStatement, String.valueOf(result));
                        //System.out.println(statement);
                        break;
                    }
                }
                else if (ch == '-' || ch == '+') {
                    if (statement.startsWith("-")) {
                        int countMinus = 0;
                        for (int j = 0; j < statement.length(); j++) {
                            if (statement.charAt(j) == '-') {
                                countMinus++;
                            }
                        }
                        if (countMinus == 1 && !statement.contains("+")) {
                            statement = statement.substring(1);
                            break;
                        }
                        else {
                            isFirstMinus = true;
                            statement = statement.substring(1);
                            statement = statement.replaceAll("-", "m");
                            statement = statement.replaceAll("\\+", "-");
                            statement = statement.replaceAll("m", "+");
                            break;
                        }
                    }

                    String subStatement = findSubstatement(statement, i);
                    try {
                    double firstNumber = findNumber(statement, i, false);
                    double secondNumber = findNumber(statement, i, true);
                        if (ch == '+') {
                            result = firstNumber + secondNumber;
                        } else {
                            result = firstNumber - secondNumber;
                        }
                    }catch (Exception ex) {
                        return null;
                    }
                    statement = statement.replace(subStatement, String.valueOf(result));
                    //System.out.println(statement);
                    break;
                }
            }
        }
        if (Double.isInfinite(result)) {
            return null;
        }
        if (isFirstMinus && result != 0) {
            result = result * (-1);
        }
        return String.valueOf(result);
    }

    public String findStatementParentheses(String statement) {

        Pattern pattern = Pattern.compile("\\([\\d+.\\-*/]+\\)");
        Matcher matcher = pattern.matcher(statement);

        if (matcher.find()) {
            String statementParentheses = matcher.group();
            return statementParentheses;
        }
        else {
            return "NoParentheses";
        }
    }

    public double findNumber(String statement, int indexSign, boolean direction) {
        if (direction) {
            indexSign++;
            String number = "";
            //search number on the right of the sign
            for (int i = indexSign; i < statement.length(); i++) {
                char ch = statement.charAt(i);
                if (ch == '+' || ch == '*' || ch == '/' || ch == '(' || ch == ')') {
                    break;
                }
                number += ch;
            }

            return Double.parseDouble(number);
        }
        else {
            indexSign--;
            StringBuilder number = new StringBuilder();
            //search number on the left of the sign
            for (int i = indexSign; i >= 0; i--) {
                char ch = statement.charAt(i);
                if (ch == '+' || ch == '*' || ch == '/' || ch == '(' || ch == ')') {
                    break;
                }
                number.append(ch);
            }
            number.reverse();
            return Double.parseDouble(number.toString());
        }
    }

    public String findSubstatement(String statement, int indexSign) {
        int firstIndex = 0, lastIndex = 0;
        for (int i = indexSign + 1; i < statement.length(); i++) {
            char ch = statement.charAt(i);
            if (ch == '+' || ch == '*' || ch == '/'|| ch == '(' || ch == ')') {
                break;
            }
            lastIndex = i;
        }
        for (int i = indexSign - 1; i >= 0; i--) {
            char ch = statement.charAt(i);
            if (ch == '+' || ch == '*' || ch == '/'|| ch == '(' || ch == ')') {
                break;
            }
            firstIndex = i;
        }
        return statement.substring(firstIndex, lastIndex + 1);
    }

    public boolean validate(String statement) {
        int countLeftParentheses = 0;
        int countRightParentheses = 0;
        for (int i = 0; i < statement.length(); i++) {
            char ch = statement.charAt(i);
            if (ch == '(') {
                countLeftParentheses++;
            }
            if (ch == ')') {
                countRightParentheses++;
            }
        }
        if (countLeftParentheses == countRightParentheses) {
            return true;
        }
        else {
            return false;
        }
    }

}

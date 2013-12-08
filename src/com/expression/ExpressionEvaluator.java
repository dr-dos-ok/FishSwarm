package com.expression;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class ExpressionEvaluator {
	 static Stack operatorStack = new Stack();
	 static Stack operandStack = new Stack();

	 static String toPostfix(String infix) {
		StringTokenizer s = new StringTokenizer(infix);

		String symbol, postfix = "";
		while (s.hasMoreTokens()) {
			symbol = s.nextToken();
			if (Character.isDigit(symbol.charAt(0)))
				postfix = postfix + " " + (Integer.parseInt(symbol));
			else if (symbol.equals("(")) {
				Character operator = new Character('(');
				operatorStack.push(operator);
			} else if (symbol.equals(")")) {
				while (((Character) operatorStack.peek()).charValue() != '(') {
					postfix = postfix + " " + operatorStack.pop();
				}
				operatorStack.pop();
			} else {
				while (!operatorStack.empty()
						&& !(operatorStack.peek()).equals("(")
						&& prec(symbol.charAt(0)) <= prec(((Character) operatorStack
								.peek()).charValue()))
					postfix = postfix + " " + operatorStack.pop();
				Character operator = new Character(symbol.charAt(0));
				operatorStack.push(operator);
			}
		}
		while (!operatorStack.empty())
			postfix = postfix + " " + operatorStack.pop();
		return postfix;
	}

	 static int evaluate(String postfix) {
		StringTokenizer s = new StringTokenizer(postfix);
		int value;
		String symbol;
		//System.out.println(operandStack.toString());
		while (s.hasMoreTokens()) {
			symbol = s.nextToken();
			if (Character.isDigit(symbol.charAt(0))) {
				Integer operand = new Integer(Integer.parseInt(symbol));
				operandStack.push(operand);
			} else {
				int op2 = ((Integer) operandStack.pop()).intValue();
				int op1 = ((Integer) operandStack.pop()).intValue();
				int result = 0;
				switch (symbol.charAt(0)) {
				case '*': {
					result = op1 * op2;
					break;
				}
				case '+': {
					result = op1 + op2;
					break;
				}
				case '-': {
					result = op1 - op2;
					break;
				}
				case '/': {
					result = op1 / op2;
					break;
				}
				case '%': {
					result = op1 % op2;
					break;
				}
				}
				Integer operand = new Integer(result);
				operandStack.push(operand);
				System.out.println(result);
			}
		}
		value = ((Integer) operandStack.pop()).intValue();
		return value;
	}

	private static int prec(char x) {
		if (x == '+' || x == '-')
			return 1;
		if (x == '*' || x == '/' || x == '%')
			return 2;
		return 0;
	}

	public static void main(String args[]) throws IOException {
		String infix;
		
		ExpressionEvaluatorFrame evaluator = new ExpressionEvaluatorFrame();

//		infix = JOptionPane.showInputDialog(null, "Enter an Infix Expression:");
//		infix = infix.replace("+", " + ");
//		infix = infix.replace("-", " - ");
//		infix = infix.replace("*", " * ");
//		infix = infix.replace("/", " / ");
//		infix = infix.replace("%", " % ");
//		infix = infix.replace(")", " ) ");
//		infix = infix.replace("(", " ( ");
//		System.out.println(infix);
//		JOptionPane.showMessageDialog(null,
//				"Converted to a Postfix Expression: " + toPostfix(infix));
//		JOptionPane.showMessageDialog(null, "The answer is: "
//				+ evaluate(toPostfix(infix)));

	}

}

package com.expression;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.*;

public class ExpressionEvaluatorFrame extends JFrame implements ActionListener,
		Runnable {
	JButton calc, simulation;
	JLabel infix, postfix, result, simulate;
	JTextField infixText, postfixText, resultText;
	Thread thread;

	public ExpressionEvaluatorFrame() {

		infix = new JLabel("Infix Expression :");
		infix.setBounds(20, 30, 120, 20);
		add(infix);

		postfix = new JLabel("PostFix Expression :");
		postfix.setBounds(20, 60, 120, 20);
		add(postfix);

		result = new JLabel("Result:");
		result.setBounds(20, 90, 120, 20);
		add(result);

		simulate = new JLabel();
		simulate.setBounds(20, 130, 200, 20);
		add(simulate);

		infixText = new JTextField();
		infixText.setBounds(150, 30, 200, 20);
		add(infixText);

		postfixText = new JTextField();
		postfixText.setBounds(150, 60, 200, 20);
		add(postfixText);

		resultText = new JTextField();
		resultText.setBounds(150, 90, 200, 20);
		add(resultText);

		calc = new JButton("Calc");
		calc.setBounds(300, 300, 70, 30);
		add(calc);
		calc.addActionListener(this);

		simulation = new JButton("simulation");
		simulation.setBounds(180, 300, 100, 30);
		add(simulation);
		simulation.addActionListener(this);

		setBounds(100, 100, 400, 400);
		setLayout(null);
		setResizable(false);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String infix;
		if (e.getSource().equals(calc)) {
			infix = infixText.getText();

			if (infix.equals("")) {
				JOptionPane
						.showMessageDialog(null, "Enter an Infix Expression");
				return;
			}
			infix = infix.replace("+", " + ");
			infix = infix.replace("-", " - ");
			infix = infix.replace("*", " * ");
			infix = infix.replace("/", " / ");
			infix = infix.replace("%", " % ");
			infix = infix.replace(")", " ) ");
			infix = infix.replace("(", " ( ");

			// System.out.println(infix);
			postfixText.setText(ExpressionEvaluator.toPostfix(infix));
			resultText.setText(ExpressionEvaluator.evaluate(ExpressionEvaluator
					.toPostfix(infix)) + "");
		} else if (e.getSource().equals(simulation)) {
			infix = infixText.getText();

			if (infix.equals("")) {
				JOptionPane
						.showMessageDialog(null, "Enter an Infix Expression");
				return;
			}
			infix = infix.replace("+", " + ");
			infix = infix.replace("-", " - ");
			infix = infix.replace("*", " * ");
			infix = infix.replace("/", " / ");
			infix = infix.replace("%", " % ");
			infix = infix.replace(")", " ) ");
			infix = infix.replace("(", " ( ");

			// System.out.println(infix);
			postfixText.setText(ExpressionEvaluator.toPostfix(infix));
			resultText.setText(ExpressionEvaluator.evaluate(ExpressionEvaluator
					.toPostfix(infix)) + "");
			thread = new Thread(this);
			thread.start();
		}

	}

	@Override
	public void run() {
		String simulateText = postfixText.getText().trim();
		simulateText = simulateText.replace(" ", " | ");
		String[] simData = postfixText.getText().trim().split(" ");
		List<String> list = Arrays.asList(simData);
		Collections.reverse(list);
		Stack stack = new Stack();
		Stack stackShow = new Stack();
		stackShow.addAll(list);
		System.out.println(stack.toString());
		System.out.println("stackShow " + stackShow.toString());
		simulate.setText(stackShow.toString());

		StringTokenizer s = new StringTokenizer(postfixText.getText().trim());
		int value;
		String symbol;
		while (s.hasMoreTokens()) {
			symbol = s.nextToken();
			System.out.println("symbol " + symbol);
			if (Character.isDigit(symbol.charAt(0))) {
				Integer operand = new Integer(Integer.parseInt(symbol));
				System.out.println("operand " + operand);
				stack.push(operand);
			} else {
				int op2 = new Integer(stack.pop().toString());
				int op1 = new Integer(stack.pop().toString());
				System.out.println("op2 " + op2);
				System.out.println("op1 " + op1);
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
				stack.push(operand);
				stackShow.pop();
				stackShow.pop();
				stackShow.pop();
				stackShow.push(operand);
				System.out.println("stackShow " + stackShow.toString());
				try {
					thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simulate.setText(stackShow.toString());
				System.out.println(stack.toString());
				System.out.println("result " + result);
			}
		}
		value = ((Integer) stack.pop()).intValue();
		System.out.println(value);

	}

}

// PostFixCalculator
// Rowan Rice
// December 4, 2019

/*
This file contains a PostFixCalculator class. 

It takes in an infix expression from the user and creates an
instance of a Converter, which then converts the infix expression
to a postfix expression.  The calculator class then determines
the result of the postfix expression and prints that for the user.
*/


import java.util.Scanner;
import java.io.*; 
import java.util.*;

public class PostFixCalculator
{
	// operand constants
	public final static String MULT = "*" + "";
	public final static String ADD = "+" + "";
	public final static String SUB = "-" + "";
	public final static String DIV = "/" + "";
	public final static String EXPO = "^" + "";
	public final static String OPEN = "(" + "";
	public final static String CLOSE = ")" + "";

	public static void main (String [] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a prefix expression: ");
		String expression = input.nextLine();

		// convert the infix expression to postfix
		Converter postFix = new Converter(expression);
		String postFixExpression = postFix.toPostFix();

		System.out.println("Converted to postfix: ");
		System.out.println(postFixExpression);

		// calculate answer
		PostFixCalculator calculator = new PostFixCalculator();
		double ans = calculator.calculate(postFixExpression);

		System.out.println("Answer is: " + ans);
		

	}

	// Stack that holds the numbers to evaluate
	private Stack<Double> numbers;

	// constructor
	PostFixCalculator()
	{
		numbers = new LinkedStack<Double>();
	}

	// calculate takes in the postfix expression in the form of a String
	// and returns a double with the result of the equation
	public double calculate (String equation)
	{
		// create a char array to use the parse helper method
		char[] eqArray = equation.toCharArray();
		List<String> values = Converter.parse(eqArray); //parse helper method

		Double holdNumValue;
		String holdStringValue;

		// iterate through entire list and check the String
		// if the String is a number, convert it to a Double and add to Stack
		// if the String is an operator, pop off top two numbers from the Stack
		// and evaluate them using the operator; push the result onto the Stack
		for (int i = 0; i < values.size(); i++)
		{
			holdStringValue = values.get(i);

			if (Converter.isNumber(holdStringValue))
			{
				holdNumValue = Double.valueOf(holdStringValue);
				numbers.push(holdNumValue.doubleValue());
			}

			else
			{
				double operand1 = numbers.pop();
				double operand2 = numbers.pop();
				double answer = calcAnswer(operand1, operand2, holdStringValue);

				numbers.push(answer);
			}
		}

		return numbers.top(); //remaining value is the answer to the expression
	}

	// calc answer takes in the two operands and the operator and returns
	// the result
	// NOTE: num1 is the top value, which is the second operator in 
	// the postfix expression -- order matters for subtraction, division
	// and the exponent operator
	private double calcAnswer(double num1, double num2, String operator)
	{
		if (operator.equals(EXPO))
		{
			return Math.pow(num2, num1);
		}

		else if (operator.equals(MULT))
		{
			return num1 * num2;
		}

		else if (operator.equals(ADD))
		{
			return num1 + num2;
		}

		else if (operator.equals(SUB))
		{
			return num2 - num1;
		}

		else // only division remaining
		{
			return num2 / num1;
		}
	}

}
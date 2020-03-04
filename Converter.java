// Converter
// Rowan Rice
// December 5, 2019

import java.io.*; 
import java.util.*;

public class Converter
{
	private String originalExpression;
	private Stack<String> conversionStack;

	public final static String MULT = "*" + "";
	public final static String ADD = "+" + "";
	public final static String SUB = "-" + "";
	public final static String DIV = "/" + "";
	public final static String EXPO = "^" + "";
	public final static String OPEN = "(" + "";
	public final static String CLOSE = ")" + "";


	/* constructor takes in input expression from user */
	Converter(String input)
	{
		originalExpression = input;
		conversionStack = new LinkedStack<String>();
	}

	// uses helper methods to build the postfix expression
	public String toPostFix()
	{
		// set up a char array in order to use parse helper method
		char[] arrayOfInput = new char[originalExpression.length()];

		for (int i = 0; i < originalExpression.length(); i++)
		{
			arrayOfInput[i] = originalExpression.charAt(i);
		}

		// create list of tokens from original expression
		List<String> listTokens = parse(arrayOfInput); // use helper method
		
		return postFixOutput(listTokens); // user helper method
	}

	// takes in the List of Strings from the original expression 
	private String postFixOutput(List<String> exp)
	{
		String postFixExp = "";
		String holderList = "";

		// iterate through entire list
		for (int i = 0; i < exp.size(); i++)
		{
			holderList = exp.get(i); // hold the current item

			// open parenthesis get pushed on stack
			if (holderList.equals(OPEN))
			{
				conversionStack.push(holderList);
			}

			// numbers get added to the post fix expression
			else if (isNumber(holderList))
			{
				postFixExp += holderList + " ";
			}

			// closed parenthesis prompt popping off items until an 
			// open parenthesis is found
			else if (holderList.equals(CLOSE))
			{
				postFixExp += popOffUntilOpen();
			}

			// deals with the operators in a helper method
			else 
			{
				postFixExp += dealWithOperators(holderList);
			}
		}

		// at the end of the list, make sure stack is empty
		if (!(conversionStack.isEmpty()))
		{
			postFixExp += popOffUntilEmpty();
		}

		return postFixExp;

	}

	// determines if the String is a number, parenthesis or operator
	public static boolean isNumber(String token)
	{
		boolean operator = (token.equals(MULT) || token.equals(DIV) || 
	                   token.equals(ADD) || token.equals(EXPO) || 
	                   token.equals(SUB) || token.equals(OPEN) || token.equals(CLOSE));

		return (!operator);
	}

	// accesses the stack to pop off all items until an open parenthesis is found
	private String popOffUntilOpen()
	{
		String topOfStack = conversionStack.top();
		String toAddtoPostFix = "";

		while (!(topOfStack.equals(OPEN)))
		{
			toAddtoPostFix += conversionStack.pop() + " ";
			topOfStack = conversionStack.top();
		}

		conversionStack.pop(); // pop off open parenthesis

		return toAddtoPostFix;
	}

	// takes in the operator string and determines the precedence of it
	// if lower or equal precedence, pop off the operators
	// if higher precedence, add operator to stack

	private String dealWithOperators(String oper)
	{
		String toAddtoPostFix = "";
		String topOfStack = "";

		// add to empty stack
		if (conversionStack.isEmpty())
		{
			conversionStack.push(oper);
			return toAddtoPostFix;
		}

		// otherwise, check precedence of top
		topOfStack = conversionStack.top();

		int topPrecedence = operatorValue(topOfStack);
		int operPrecedence = operatorValue(oper);

		// if higher precedence, add to stack
		if (operPrecedence > topPrecedence)
		{
			conversionStack.push(oper);
			return toAddtoPostFix;
		}

		// otherwise, pop off and recursively call deal with operators
		// to look at the precedence of the lower items on the stack
		toAddtoPostFix += conversionStack.pop() + " ";
		toAddtoPostFix += dealWithOperators(oper);
		
		return toAddtoPostFix;
	}

	// clears the stack
	private String popOffUntilEmpty()
	{
		String toAddtoPostFix = "";

		while (!(conversionStack.isEmpty()))
		{
			toAddtoPostFix += conversionStack.pop() + " ";
		}

		return toAddtoPostFix;
	}

	// determines precedence of the operators
	private int operatorValue(String op)
	{
		if (op.equals(EXPO))
		{
			return 3;
		}

		if ((op.equals(MULT)) || (op.equals(DIV)))
		{
			return 2;
		}

		if ((op.equals(ADD)) || (op.equals(SUB)))
		{	
			return 1;
		}

		return 0; // open parenthesis
	} 


	private boolean isOperator(String s)
	{
		return (s.equals(MULT) || s.equals(DIV) || 
	                   s.equals(ADD) || s.equals(EXPO) || 
	                   s.equals(SUB) || s.equals(OPEN) || s.equals(CLOSE));
	}

	// parse method provided
	public static List<String> parse(char[] input) 
	{
	    List<String> parsed = new ArrayList<String>();

	    for (int i = 0; i < input.length; ++i) 
	    {
	        char c = input[i];

	        if (Character.isDigit(c)) 
	        {
	            String number = input[i] + ""; // this isnt adding a space -- it's forcing input[i] to be a string

	            //System.out.println("Iteration number " + i + " is " + number);

	            for (int j = i + 1; j < input.length; ++j) 
	            {
	                if (Character.isDigit(input[j])) //checks to see if a 2-digit number
	                {   
	                    number += input[j];
	                    //System.out.println("Iteration number2 " + i + " is " + number);
	                    i = j;
	                } 

	                else 
	                {
	                    break;
	                }
	            }

	            parsed.add(number); // adds the number string to the list
	        } 

	        else if (c == '*' || c == '/' || 
	                   c == '+' || c == '^' || 
	                   c == '-' || c == '(' || c == ')') 
	        {
	            //System.out.println("Iteration operator " + i + " is " + c);

	            parsed.add(c + ""); // makes c a string
	        }

	        else // if a space
	        {
	        }

	    }

	    return parsed;
	}



}
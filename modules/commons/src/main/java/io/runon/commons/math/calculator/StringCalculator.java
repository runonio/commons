

package io.runon.commons.math.calculator;

import lombok.extern.slf4j.Slf4j;

import java.util.StringTokenizer;


/**
 * @author macle
 */
@Slf4j
public class StringCalculator {

	boolean inputError;		
	ListNode infixHead, infixTail; 
	static final String[] SYMBOLS={" ","("," + "," - "," * "," / ",")"," ="};
	static final double OPENPAR= 1;	
	static final double PLUS= 2;		
	static final double MINUS= 3;	
	static final double MULTIPLY= 4;	
	static final double DIVIDE= 5;	
	static final double CLOSEPAR = 6;	
	static final double EQUALS= 7;	
	
	private boolean validCharacters(String dataIn){
		StringTokenizer tester = new StringTokenizer(dataIn," .0123456789()+-*/=");
		if (tester.countTokens()==0){
			return true;
		}else{ 
			String s = "Invalid data : ";
			while (tester.hasMoreTokens())
				{
				s += "\"" + tester.nextToken() + "\"";
				if (tester.hasMoreTokens())
					s += ", ";
				}
			setError(s);
			return false;
		}
	}
	private void insertComponent(boolean op,double value,ListNode node){
		ListNode thisNode = new ListNode(new EquationComponent(op,value),
						node,node.getPrev());
		node.getPrev().setNext(thisNode);
		node.setPrev(thisNode);
	}

	private boolean makeInfixList(String s, ListNode infixHead, ListNode infixTail){
		String thisToken;
		StringTokenizer tester = new StringTokenizer(s,"(+-*/)= ",true);
		while (tester.hasMoreTokens()){
			thisToken = tester.nextToken();
			switch (thisToken.charAt(0)){
				case ' ' : break;
				case '(' : 		
					insertComponent(true,StringCalculator.OPENPAR,infixTail);
					break;
				case '+' : 				
					insertComponent(true,StringCalculator.PLUS,infixTail);
					break;
				case '-' : 				
					insertComponent(true,StringCalculator.MINUS,infixTail);
					break;
				case '*' : 				
					insertComponent(true,StringCalculator.MULTIPLY,infixTail);
					break;
				case '/' : 				
					insertComponent(true,StringCalculator.DIVIDE,infixTail);
					break;
				case ')' : 				
					insertComponent(true,StringCalculator.CLOSEPAR,infixTail);
					break;
				case '=' : 				
					insertComponent(true,StringCalculator.EQUALS,infixTail);
					break;
				default :
					try{
						insertComponent(false,Double.valueOf(thisToken).doubleValue(),infixTail);
					}catch (NumberFormatException e){
						setError("Invalid number detected");
						return false;
					}
					break;
				}				
		}
		return true;
	}
	
	private void setError(String error){
		inputError = true;
		log.error(error);
		
	}

	private void checkConsecutiveNumbers(EquationComponent thisComp,
						EquationComponent lastComp){
		if ((! thisComp.isOperator()) && (! lastComp.isOperator()))
			{
			setError("There are two numbers not seperated by an operator");
			}
	}

	private void checkMissingTimes(EquationComponent thisComp,
					EquationComponent lastComp,
					ListNode thisNode){
		if ((thisComp.isOperator()) && (thisComp.getValue()==OPENPAR) && (! lastComp.isOperator())){
			insertComponent(true,MULTIPLY,thisNode);
		}
	}

	private int checkConsecutiveOperators(EquationComponent thisComp,
					EquationComponent lastComp,
					ListNode thisNode, int brackets){
		if (thisComp.isOperator() && lastComp.isOperator()){ 
			if (thisComp.getValue()!=StringCalculator.OPENPAR){
				if (lastComp.getValue()!=StringCalculator.CLOSEPAR)   { 
					if (thisComp.getValue()==StringCalculator.EQUALS)
						setError("Missing value before =");
					else{
						EquationComponent nextComp = (EquationComponent) thisNode.getNext().getElement();
						if ((!nextComp.isOperator())
							&& (thisComp.getValue()==StringCalculator.MINUS) &&
							( (lastComp.getValue()==StringCalculator.MULTIPLY) || 
							  (lastComp.getValue()==StringCalculator.DIVIDE) ||
							  (lastComp.getValue()==StringCalculator.OPENPAR) 
							)){ 
							insertComponent(true,OPENPAR,thisNode);
							brackets++;
							insertComponent(false,0,thisNode);
							insertComponent(true,CLOSEPAR,thisNode.getNext().getNext());
						}else{
							setError("Too many consecutive operators : "+StringCalculator.SYMBOLS[(int) lastComp.getValue()]+SYMBOLS[(int) thisComp.getValue()]);
						}
					}
				}
			}else			
				// convert (7-8)(9-5) to (7-8)*(9-5)
				if  (lastComp.getValue()==CLOSEPAR)
	  				insertComponent(true,MULTIPLY,thisNode);
		}
		return brackets;
	}

	private int checkBrackets(EquationComponent thisComp,int brackets){

		if (thisComp.isOperator() && (thisComp.getValue()==OPENPAR))
			brackets++;
		if (thisComp.isOperator() && (thisComp.getValue()==CLOSEPAR))
			{
			brackets--;
			if (brackets==-1)
				{
				setError("Too many ) symbols");
				}
			}
		return brackets;
	}

	private void checkEquals(EquationComponent thisComp,
				ListNode thisNode, ListNode tail){

		if (thisComp.isOperator() && 
				(thisComp.getValue()==EQUALS) && 
				(thisNode.getNext()!=tail))
			{
			setError("= may only appear at the end");
			}
	}

	private int checkStart(EquationComponent thisComp,
					ListNode thisNode, int brackets){
		EquationComponent nextComp = (EquationComponent) thisNode.getNext().getElement();
		if (thisComp.isOperator())
			if (thisComp.getValue()==MINUS)
				{
				if (! nextComp.isOperator()){
					insertComponent(true,OPENPAR,thisNode);
					brackets++;
					insertComponent(false,0,thisNode);
					insertComponent(true,CLOSEPAR,thisNode.getNext().getNext());
					}
				else 
				  if (nextComp.getValue()==OPENPAR)
					insertComponent(false,0,thisNode);
				  else
					setError("Too many consecutive operators : -"+StringCalculator.SYMBOLS[(int) nextComp.getValue()]);
				}
			else
				if (thisComp.getValue()==OPENPAR)
					brackets++;
				else
					{
					setError("a number must appear at the start.");
					}
		return brackets;
	}

	private void standardiseEquation(ListNode head, ListNode tail){
		int brackets = 0;
		ListNode thisNode = head.getNext();

		EquationComponent thisComp;
		EquationComponent lastComp;
		if (thisNode == tail)
			setError("Nothing to Calculate!");
		else{
			thisComp = (EquationComponent) tail.getPrev().getElement();

			if (!((thisComp.isOperator()) && (thisComp.getValue()==EQUALS)))
				insertComponent(true,EQUALS,tail);

			while (thisNode != tail)
				{
				thisComp = (EquationComponent) thisNode.getElement();
				if (thisNode.getPrev() !=head)
					{
					lastComp = (EquationComponent) thisNode.getPrev().getElement();
					checkConsecutiveNumbers(thisComp, lastComp);
					checkMissingTimes(thisComp, lastComp, thisNode);
					brackets=checkConsecutiveOperators(thisComp, lastComp, thisNode,brackets);
					brackets=checkBrackets(thisComp,brackets);
					checkEquals(thisComp,thisNode,tail);
					}
				else
					{
					brackets =checkStart(thisComp, thisNode, brackets);
					} 
				thisNode = thisNode.getNext();
				}
			if (brackets!=0)
				{
//				setMessage("Warning : Missing )");
				for (int cnt=0; cnt<brackets; cnt++)
					insertComponent(true,StringCalculator.CLOSEPAR,tail.getPrev());
				}
		}
	}


	private boolean setupCalculation(String dataIn){
		inputError = false;
		if (validCharacters(dataIn))
			{
			infixHead = new ListNode(null,null,null);
			infixTail = new ListNode(null,null,infixHead);
			infixHead.setNext(infixTail);
			if (makeInfixList(dataIn,infixHead,infixTail))
				standardiseEquation(infixHead,infixTail);
			else{
			
				setError("Invalid number entered " + dataIn);
			}
//			if (!inputError){	
//				ListNode thisNode = infixHead.getNext();
//				EquationComponent eq;
//				while (thisNode!=infixTail)
//					{
//					eq = (EquationComponent) thisNode.getElement();
//					if (eq.isOperator())
//						results.append(Calculator.SYMBOLS[(int) eq.getValue()]);
//					else
//						results.append(Double.toString(eq.getValue()));
//					thisNode = thisNode.getNext();
//					}
//			}
		}
		return (! inputError);
	}

////////////////////////////////////// Stack Popping
//// these stacks only pop objects... we want to use primitive doubles
//// These methods pop the object, assumed to be a Double, and convert it.
	private double popDouble(Stack S){
		Double result = (Double) S.pop();
		return result.doubleValue();
	}

	private double topDouble(Stack S){
		Double result = (Double) S.top();
		return result.doubleValue();
	}

	private void DoTopCalculation(Stack operators, Stack operands){
		double value2 = popDouble(operands);
		double value1 = popDouble(operands);
		double op = popDouble(operators);
		double result=1;
		switch ((int) op)
			{
			case (int) PLUS: 
				result = value1 + value2;
				break;
			case (int) MINUS: 
				result = value1 - value2;
				break;
			case (int) MULTIPLY: 
				result = value1 * value2;
				break;
			case (int) DIVIDE: 
				result = value1 / value2;
				break;
			}
		operands.push(new Double(result));
	}
	
	private void handleOperator(double op, Stack operators, Stack operands){
		switch ( (int) op)
			{
			case (int) OPENPAR :
				operators.push(new Double(OPENPAR));
				break;
			case (int)MULTIPLY: ;
			case (int)DIVIDE  :
				if ((topDouble(operators)==MULTIPLY) ||(topDouble(operators)==DIVIDE))
					DoTopCalculation(operators, operands);
				operators.push(new Double(op));
				break;
			case (int)PLUS : ;
			case (int)MINUS:
				while (topDouble(operators)!=OPENPAR)
					DoTopCalculation(operators, operands);
				operators.push(new Double(op));
				break;
			case (int)CLOSEPAR: ;
			case (int)EQUALS:
				while (topDouble(operators)!=OPENPAR)
					DoTopCalculation(operators, operands);
				operators.pop();
				break;
			}
	}

	public Double getResult(String dataIn){
	
		if (setupCalculation(dataIn)) { 
			Stack operators = new DequeStack();
			Stack operands = new DequeStack();
			ListNode thisNode;
			EquationComponent thisComp;

			operators.push(new Double(OPENPAR));

			thisNode = infixHead.getNext();

			while (thisNode != infixTail) {
				thisComp = (EquationComponent) thisNode.getElement();
				if (thisComp.isOperator()){ 
					handleOperator(thisComp.getValue(),operators,operands);
				}else{ 
					operands.push(new Double(thisComp.getValue()));
				}
				thisNode = thisNode.getNext(); // next item
			}
			Double answer = (Double) operands.pop();
			if (answer.isNaN()){
				log.error("result is NaN " + dataIn);
				return null;
			}else{
				return Double.parseDouble(answer.toString());	
			}
		}else{
			return null;
		}
	}
	
	public static void main(String[] args){
		
		final StringCalculator thisCalc = new StringCalculator();
//		new Thread(){
//			
//			public void run(){
//			for(int i=0 ; i<10000 ; i++){
//			System.out.println(thisCalc.getResult("(((1+(2*3))/4)+5)"));
//			System.out.println(thisCalc.getResult("(1+1)*2"));
//			System.out.println(thisCalc.getResult("1000"));
//			System.out.println(thisCalc.getResult("-5"));
//			System.out.println(thisCalc.getResult("0"));
//			System.out.println(thisCalc.getResult("6"));
//			System.out.println(thisCalc.getResult("- 5"));
//			}
//			}
//		}.start();
		
		System.out.println(thisCalc.getResult("(((1+(2*3))/4)+5)"));
	}

}
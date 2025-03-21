
package io.runon.commons.math.calculator;


class EquationComponent{

	private boolean operator;
	private double value;

////////////////////// Constructors

	public EquationComponent(boolean op,double val)
	// specifying initial values...
	{
		operator = op;
		value = val;
	}

	public EquationComponent(double val)
	// specifying value, assuming not an operator
	{
		this(false,val);
	}

	public EquationComponent()
	// assuming a non operator with inital value 0.
	{
		this(false,0);
	}

////////////////////// Mutator Methods

	public void setValue(double val)
	// sets the value of the operand, or the code of the operator
	{
		value = val;
	}

	public void setOperator(boolean op)
	// set whether the component is an operator (op=TRUE) or
	// an operand (op=FALSE)
	{
		operator = op;
	}

/////////////////////// Accessor Methods
	public boolean isOperator()
	// determine if the component is an operator (returns TRUE) 
	// or an operand (returns FALSE)
	{
		return operator;
	}

	public double getValue()
	// returns the value of the operand, or the code of the operator
	{
		return value;
	}
}
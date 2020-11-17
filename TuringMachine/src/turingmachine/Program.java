package turingmachine;

public class Program
{
	String fromState;
	String letter;
	String newLetter;
	String direction;
	String toState;
	
	public Program(String f, String l, String n, String d, String t)
	{
		this.fromState=f;
		this.letter=l;
		this.newLetter=n;
		this.direction=d;
		this.toState=t;
	}

	public String getFromState()
	{
		return fromState;
	}

	
	public void setLetter(String letter)
	{
		this.letter = letter;
	}

	public String getLetter()
	{
		return letter;
	}

	public String getNewLetter()
	{
		return newLetter;
	}

	public String getDirection()
	{
		return direction;
	}

	public String getToState()
	{
		return toState;
	}
	
	@Override
	public String toString()
	{
		return getFromState() + ", " + getLetter() + ", " + getNewLetter() + ", " + getDirection() + ", " + getToState();
	}
	
	

}

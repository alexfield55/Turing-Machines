package turingmachine;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TuringMachine
{

	public static void main(String[] args)
	{

		String filename = args[0];
		String word = args[1] + "_";
		char[] letters = word.toCharArray();
		int position = 0;
		String state = "1 Start"; // first should always start here
		String start = state;
		String halt = "";

		int loops = 0;

		List<Program> programStates = new LinkedList<>();
		Program x = null;
		String line = "";

		try (Scanner reader = new Scanner(TuringMachine.class.getResourceAsStream(filename)))
		{
			while (reader.hasNextLine())
			{

				line = reader.nextLine();

				if (line.charAt(0) != '#') // account for commented lines
				{
					x = getProgram(line);
					if (x != null)
						programStates.add(x);
				}
			}
		}

		for (int i = 0; i < programStates.size(); i++)
		{
			if (programStates.get(i).getToState().contains("halt") || programStates.get(i).getToState().contains("Halt")
					|| programStates.get(i).getToState().contains("HALT"))
			{
				// System.out.printf("Halt found %s\n", programStates.get(i).getToState());
				halt = programStates.get(i).getToState();
				break;
			} else
			{
				// System.out.println("No halt word rejected/crash");
			}
		}

		
		
		while (!(state.equalsIgnoreCase(halt)))
		{
			char letter;

			// read tape, intialized with zero position
			letter = letters[position];
			int fails=0;
			// looks programs and if the current state matches the state of the program and
			// the letter on the tape matches the letter of that same program
			// then change the letter to the new letter of that program, check for next
			// state, update counters and positions
			for (int i = 0; i < programStates.size(); i++)
			{
			
				
				if (programStates.get(i).getFromState().equalsIgnoreCase(state)
						&& programStates.get(i).getLetter().equalsIgnoreCase(Character.toString(letter)))
				{
/*
					System.out.printf(
							"Current word: %s, While loop counter: %d, For loop counter: %d, Letter: %c, Postion: %d, Variable state: %s, From state: %s, Current letter: %s, New Letter: %s, Direction: %s, toState: %s\n",
							toString(letters), loops, i, letter, position, state, programStates.get(i).getFromState(),
							programStates.get(i).getLetter(), programStates.get(i).getNewLetter(),
							programStates.get(i).getDirection(), programStates.get(i).getToState());
*/
					letters[position] = programStates.get(i).getNewLetter().charAt(0);

					// check for final state move and breaks loop
					if (programStates.get(i).getToState().equalsIgnoreCase(halt))
					{
						System.out.printf("Accepted: %s\n", word);
						state = halt;
						break;
					} else if (programStates.get(i).getToState().equalsIgnoreCase(halt))
					{
						System.out.printf("Accepted: %s\n", word);
						state = halt;
						break;
					} else
					{

						// checks for direction, for R increment position
						if (programStates.get(i).getDirection().equalsIgnoreCase("R") && position < word.length()-1)
						{
							position++;
						}
						// checks for direction L, if L would move off tape (position < 0) crash
						else if (programStates.get(i).getDirection().equalsIgnoreCase("L") && position > 0)
						{
							position--;
						}
						// crashed
						else
						{
							// CRASH
							System.out.printf("Rejected: %s\n", word);
							state = halt;
							break; // to break the for each
						}
					}
				
					// updates state
					state = programStates.get(i).getToState();
					break;
				}
				else
					fails++;
				
				//System.out.printf("Program size: %d, failed loops: %d\n", programStates.size(), fails);
				
				if(programStates.size()==fails)
				{
					System.out.printf("Rejected: %s\n", word);
					state=halt;
					break;
				}
				/*
				else if(!(programStates.get(i).getLetter().equalsIgnoreCase(Character.toString(letter))) && programStates.get(i).getFromState().equalsIgnoreCase(state) )
				{
					System.out.println("no path");
					state=halt;
				}
				*/
				

			}

			loops++;

		}
		
		/*

		System.out.printf("Took %d iterations\n", loops);

		// test print of reading in file System.out.println("Read in file:");
		for (Program p : programStates)
		{
			System.out.println(p.toString());
		}
		System.out.printf("Read in test word: %s\n", word);
		for (int i = 0; i < letters.length; i++)
		{
			System.out.print(letters[i]);
		}
		System.out.println();
		 */
	}

	/**
	 * Parses input line of txt for class constructor
	 * 
	 * @param line
	 * @return
	 */

	private static String toString(char[] a)
	{
		StringBuilder sb = new StringBuilder();

		// Using append() method to create a string
		for (int i = 0; i < a.length; i++)
		{
			sb.append(a[i]);
		}

		return sb.toString();
	}

	private static Program getProgram(String line)
	{
		Program state = null;
		try
		{
			String[] items = line.split(", ");

			items[0] = items[0].toUpperCase();

			if (items[0].contains("HALT"))
			{
				state = new Program(items[0], "", "", "", "");
			} else
			{

				state = new Program(items[0], items[1], items[2], items[3], items[4]);
			}
		}

		catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e)
		{
			System.err.println("ERROR: cant do the thing");

		}

		return state;
	}
}

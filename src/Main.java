import java.util.*;
import java.util.Scanner;

public class Main {

	static boolean debugOn = false;
	static boolean asciiIn = false;
	static ArrayList<Integer> preInput = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		
		String input = "";
		
		//get input from scanner
		System.out.println("Input a program (RUN, HELP)");
		input = getScannerInput();
		
		//run interpretter
		interpret(input);
	}
	
	public static String getScannerInput() {
		Scanner scanner = new Scanner(System.in);
		
		String output = "";
		
		boolean scan = true;
		while(scan)
		{
			//get next input
			String next = scanner.next();
			
			//check against interpreter commands before adding to input string
			if(next.equals("DEBUGON"))
				debugOn = true;
			else if(next.startsWith("IN:"))
			{
				//add character, or ascii code based on mode
				if(!asciiIn)
					preInput.add((int)next.split(":")[1].toCharArray()[0]); //add first character following "IN:" to preInput list
				else
					preInput.add(Integer.parseInt(next));
			}
			else if(next.startsWith("ASCIIIN"))
				asciiIn = true;
			else if(next.equals("HELP"))
			{
				System.out.println("RUN to run, DEBUGON for debug info, ASCIIIN for ascii-code input, IN:x to pre-input");
				System.out.println("github.com/matthewd673");
			}
			else if(next.equals("RUN"))
				break;
			else //not an interpreter command, just add to output string
				output = output.concat(next);
		}
		
		return output;
	}

	public static void interpret(String input) {
		//scanner for if the program asks for input
		Scanner inputScanner = new Scanner(System.in);
		
		System.out.println("Beginning interpretter");
		
		//create commands and memory arrays and pointers
		char[] commands = input.toCharArray();
		int reader = 0;
		int[] memory = new int[256];
		int pointer = 0;
		
		//list to store loop-back positions
		ArrayList<Integer> loopPos = new ArrayList<Integer>();
		
		//loop through until reader reaches end of commands
		while(reader < commands.length) {
			char command = commands[reader];
			
			switch(command) {
			case '+': //increment at pointer
				if(debugOn)
					System.out.print("+");
				memory[pointer]++;
				//loop back to 0 if increases beyond 0
				if(memory[pointer] > 255)
					memory[pointer] = 0;
				break;
			case '-': //decrement at pointer
				if(debugOn)
					System.out.print("-");
				memory[pointer]--;
				//loop back to 255 if decreases beyond 0
				if(memory[pointer] < 0)
					memory[pointer] = 255;
				break;
			case '>': //move pointer forward
				if(debugOn)
					System.out.print(">");
				pointer++;
				//prevent pointer overflowing memory
				if(pointer > 255)
					pointer = 255;
				break;
			case '<': //move pointer backward
				if(debugOn)
					System.out.print("<");
				pointer--;
				//prevent negative pointer
				if(pointer < 0)
					pointer = 0;
				break;
			case '[': //open loop
				if(debugOn)
					System.out.print("[");
				//add to loop position list
				loopPos.add(reader);
				break;
			case ']':
				if(debugOn)
					System.out.println("]");
				
				//if 0, continue, otherwise loop back to last-opened loop position
				if(memory[pointer] != 0)
					reader = loopPos.get(loopPos.size() - 1);
				else
					loopPos.remove(loopPos.size() - 1);
				break;
			case ',':
				if(debugOn)
					System.out.println(",");
				//ask user for input if there is nothing in preinput
				if(preInput.size() == 0)
				{
					System.out.print("Awaiting input: ");
					if(!asciiIn)
					{
						char inputChar = inputScanner.next().toCharArray()[0];
						int charCode = (int)inputChar;
						memory[pointer] = charCode;
					}
					else
					{
						int charCode = Integer.parseInt(inputScanner.next());
						memory[pointer] = charCode;
					}
				}
				//accept input straight from preinput
				else
				{
					System.out.println("Input: " + (char)preInput.get(0).intValue() + " (" + preInput.get(0) + ")");
					memory[pointer] = preInput.get(0);
					preInput.remove(0);
				}
				break;
			case '.':
				if(debugOn)
					System.out.println(".");
				//print out output (as well as ascii code, for lazy programs)
				System.out.println("Output: " + (char)memory[pointer] + " (" + (char)(memory[pointer] + 48) + ")");
				break;
			}
			
			//move reader forward
			reader++;
				
		}
		
		System.out.println("Execution complete.");
		
	}
	
}

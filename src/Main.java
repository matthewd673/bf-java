import java.util.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String input = "";
		
		if(args.length == 0) //no args, get from scanner
		{
			System.out.println("Input a program");
			input = getScannerInput();
		}
		else
			input = args[0];
		
		interpret(input);
	}
	
	public static String getScannerInput() {
		/*
		Scanner scanner = new Scanner(System.in);
		String output = "";
		
		boolean continueReading = true;
		while(scanner.hasNext())
		{
			String next = scanner.next();
			output = output.concat(next);
			if(!scanner.hasNext() || next.equals("#STOP#"))
				continueReading = false;
		}
		*/
		
		String output = ",>,>++++++++[<------<------>>-]<[->>+>+<<<]<[->+>+<<]>>[-<<+>>]<[->>>>+<<<<]>>>>[<[->-<]>>+<<<[-<+>]<[->+>+<<]>>>]>.";
		
		return output;
	}

	public static void interpret(String input) {
		Scanner inputScanner = new Scanner(System.in);
		
		System.out.println("Beginning interpretter");
		
		char[] commands = input.toCharArray();
		int reader = 0;
		
		int[] memory = new int[256];
		int pointer = 0;
		
		ArrayList<Integer> loopPos = new ArrayList<Integer>();
		
		//loop through until reader reaches end of commands
		while(reader < commands.length) {
			char command = commands[reader];
			
			switch(command) {
			case '+': //increment at pointer
				System.out.print("+");
				memory[pointer]++;
				break;
			case '-': //decrement at pointer
				System.out.print("-");
				memory[pointer]--;
				if(memory[pointer] < 0)
					memory[pointer] = 255;
				break;
			case '>': //move pointer forward
				System.out.print(">");
				pointer++;
				break;
			case '<': //move pointer backward
				System.out.print("<");
				pointer--;
				break;
			case '[': //open loop
				System.out.print("[");
				loopPos.add(reader);
				break;
			case ']':
				System.out.println("]");
				
				if(memory[pointer] != 0)
					reader = loopPos.get(loopPos.size() - 1);
				else
					loopPos.remove(loopPos.size() - 1);
				break;
			case ',':
				System.out.println(",");
				System.out.print("Awaiting input: ");
				char inputChar = inputScanner.next().toCharArray()[0];
				int charCode = (int)inputChar;
				memory[pointer] = charCode;
				break;
			case '.':
				System.out.println(".");
				System.out.println("Output: " + (char)memory[pointer] + " (" + (char)(memory[pointer] + 48) + ")");
				break;
			}
			
			//move reader forward
			reader++;
				
		}
		
	}
	
}

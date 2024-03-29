package com.wongnai.interview.wamup;

import java.util.InputMismatchException;

//import java.util.Scanner;

public class DigitalRoot {
	public Object check(long number) {
		// TODO: Warmup practice => Implement this method to find out the digital root
		// number of the input.
		// The digital root of a non-negative integer is the single-digit value obtained
		// by an iterative process
		// of summing digits, on each iteration using the result from the previous
		// iteration to compute the digit sum.
		// The process continues until a single-digit number is reached.
		// -------------------------------
		// -------------------------------
		// Example:
		// Input : 12345
		// Output : 6 (Because 1 + 2 + 3 + 4 + 5 equals 15 and then 1 + 5 equals 6)
		// -------------------------------
		// -------------------------------
		// All test case in DigitalRootTest must be passed.
		// Scanner scan = new Scanner(System.in);
		long sum = 0;

		
		if(number>0){
			while (number > 0 || sum > 9) {
				if (number == 0) {
					number = sum;
					sum = 0;
				}
				sum += number%10;
				number = number / 10;
				
			}
			return sum;
		}
		else throw new InputMismatchException("The number must be positive");
	}
}

/*************************************************************************
 *                                                                       *
 * Open Dynamics Engine 4J, Copyright (C) 2007-2010 Tilmann ZÃ¤schke      *
 * All rights reserved.  Email: ode4j@gmx.de   Web: www.ode4j.org        *
 *                                                                       *
 * This library is free software; you can redistribute it and/or         *
 * modify it under the terms of EITHER:                                  *
 *   (1) The GNU Lesser General Public License as published by the Free  *
 *       Software Foundation; either version 2.1 of the License, or (at  *
 *       your option) any later version. The text of the GNU Lesser      *
 *       General Public License is included with this library in the     *
 *       file LICENSE.TXT.                                               *
 *   (2) The BSD-style license that is included with this library in     *
 *       the file ODE4J-LICENSE-BSD.TXT.                                 *
 *                                                                       *
 * This library is distributed in the hope that it will be useful,       *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the files    *
 * LICENSE.TXT and ODE4J-LICENSE-BSD.TXT for more details.               *
 *                                                                       *
 *************************************************************************/
package org.cpp4j;

import java.util.Random;

public class Cstdlib extends Cstring {

	public static final int RAND_MAX = 2147483647;
	
	/**
	 * Convert string to integer.
	 * Parses the C string str interpreting its content as an integral number, 
	 * which is returned as an int value.
	 * 
	 * The function first discards as many whitespace characters as necessary 
	 * until the first non-whitespace character is found. Then, starting from 
	 * this character, takes an optional initial plus or minus sign followed by
	 * as many numerical digits as possible, and interprets them as a numerical 
	 * value.
	 * 
	 * The string can contain additional characters after those that form the 
	 * integral number, which are ignored and have no effect on the behavior of 
	 * this function.
	 * 
	 * If the first sequence of non-whitespace characters in str is not a valid
	 * integral number, or if no such sequence exists because either str is 
	 * empty or it contains only whitespace characters, no conversion is 
	 * performed.
	 * 
	 * @param str C string beginning with the representation of an integral 
	 * number.
	 * @return On success, the function returns the converted integral number 
	 * as an int value.
	 * If no valid conversion could be performed, a zero value is returned.
	 * If the correct value is out of the range of representable values, 
	 * INT_MAX or INT_MIN is returned.
	 */
	public static int atoi(String str) {
		//TODO ignore additional characters 
		return Integer.parseInt(str);
	}
	
	/**
	 * Generate random number. <br>
	 * 
	 * Returns a pseudo-random integral number in the range 0 to RAND_MAX.
	 * 
	 * This number is generated by an algorithm that returns a sequence of 
	 * apparently non-related numbers each time it is called. This algorithm 
	 * uses a seed to generate the series, which should be initialized to some 
	 * distinctive value using srand.
	 * 
	 * RAND_MAX is a constant defined in <cstdlib>. Its default value may vary 
	 * between implementations but it is granted to be at least 32767.
	 * 
	 * A typical way to generate pseudo-random numbers in a determined range 
	 * using rand is to use the modulo of the returned value by the range span 
	 * and add the initial value of the range: <br>
	 * ( value % 100 ) is in the range 0 to 99 <br>
	 * ( value % 100 + 1 ) is in the range 1 to 100 <br>
	 * ( value % 30 + 1985 ) is in the range 1985 to 2014 <br>
	 * 
	 * Notice though that this modulo operation does not generate a truly 
	 * uniformly distributed random number in the span (since in most cases 
	 * lower numbers are slightly more likely), but it is generally a good 
	 * approximation for short spans.
	 * 
	 * @return An integer value between 0 and RAND_MAX. 
	 */
	public static final int rand() {
		//return (int) (Math.random()*RAND_MAX);
		//return RGEN.nextInt(RAND_MAX); 
		return RGEN.next();
	}
	private static final Random31 RGEN = new Random31();
	private static class Random31 extends Random {
		public int next() {
			return super.next(31);
		}
	}

	/**
	 * Terminate calling process. <br>
	 * Terminates the process normally, performing the regular cleanup for 
	 * terminating processes.
	 * First, all functions registered by calls to atexit are executed in 
	 * the reverse order of their registration. Then, all streams are closed 
	 * and the temporary files deleted, and finally the control is returned 
	 * to the host environment.
	 * 
	 * The status argument is returned to the host environment.
	 * 
	 * @param status Status value returned to the parent process. Generally, 
	 * a return value of 0 or EXIT_SUCCESS indicates success, and any other 
	 * value or the constant EXIT_FAILURE is used to indicate an error or some 
	 * kind of abnormal program termination.
	 */
	public static final void exit(int status) {
		System.exit(status);
	}
}

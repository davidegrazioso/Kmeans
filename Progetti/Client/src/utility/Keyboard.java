//********************************************************************
//  Keyboard.java       Author: Lewis and Loftus
//
//  Facilitates keyboard input by abstracting details about input
//  parsing, conversions, and exception handling.
//********************************************************************

package utility;

import java.io.*;
import java.util.*;

/**
 * Classe che collezionai metodi di classe per l'acquisizione
 * dell'input da tastiera
 */
public class Keyboard {
	

	private static boolean printErrors = true;

	private static int errorCount = 0;

	
 	/**
  	* Ritorna il corrente numero di errori
  	* @return errorCount numero corrente di errori
  	*/
	public static int getErrorCount() {
		return errorCount;
	}

	/**
	 * resetta il numero di errori
	 * @param count 
	 */
	public static void resetErrorCount(int count) {
		errorCount = 0;
	}

	
	/**
	 * Ritorna un booleano che indica se gli errori sono giá stati stampati
	 * @return printErrors 
	 */
	public static boolean getPrintErrors() {
		return printErrors;
	}

	/**
	 * Setta un booleano e indica se gli input sono stampati 
	 * @param flag indica se gli input sono stampati 
	 */
	public static void setPrintErrors(boolean flag) {
		printErrors = flag;
	}

	/**
	 * Restituisce a schermo un errore
	 * @param str stringa che ti ridá l'errore
	 */
	private static void error(String str) {
		errorCount++;
		if (printErrors)
			System.out.println(str);
	}

	private static String current_token = null;

	private static StringTokenizer reader;

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));


	/**
	 * Prendi il prossimo input token 
	 * @return il prossimo input token
	 */
	private static String getNextToken() {
		return getNextToken(true);
	}

	/**
	 * Prende l'input token successivo
	 * @param skip indica se il token é giá stato letto
	 * @return token dell'input successivo
	 */
	private static String getNextToken(boolean skip) {
		String token;

		if (current_token == null)
			token = getNextInputToken(skip);
		else {
			token = current_token;
			current_token = null;
		}

		return token;
	}

	/**
	 * rende l'input token successivo
	 * @param skip indica se il token é giá stato letto
	 * @return token dell'input successivo
	 */
	private static String getNextInputToken(boolean skip) {
		final String delimiters = " \t\n\r\f";
		String token = null;

		try {
			if (reader == null)
				reader = new StringTokenizer(in.readLine(), delimiters, true);

			while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
				while (!reader.hasMoreTokens())
					reader = new StringTokenizer(in.readLine(), delimiters,
							true);

				token = reader.nextToken();
			}
		} catch (Exception exception) {
			token = null;
		}

		return token;
	}

	/**
	 * Ritorna un booleano se non ci sono piú token da leggere
	 * @return un booleano se non ci sono piú token da leggere
	 */
	public static boolean endOfLine() {
		return !reader.hasMoreTokens();
	}

	// ************* Reading Section *********************************

	/**
	 * Ritorna una stringa letta dallo standard input
	 * @return str : una stringa letta dallo standard input
	 */
	public static String readString() {
		String str;

		try {
			str = getNextToken(false);
			while (!endOfLine()) {
				str = str + getNextToken(false);
			}
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			str = null;
		}
		return str;
	}

	/**
	 * Ritorna una stringa letta dallo standard input
	 * @return token una word letta dallo standard input 
	 */
	public static String readWord() {
		String token;
		try {
			token = getNextToken();
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			token = null;
		}
		return token;
	}

	/**
	 * Ritorna un booleano letto dallo standard input
	 * @return bool: un booleano letto dallo standard input
	 */
	public static boolean readBoolean() {
		String token = getNextToken();
		boolean bool;
		try {
			if (token.toLowerCase().equals("true"))
				bool = true;
			else if (token.toLowerCase().equals("false"))
				bool = false;
			else {
				error("Error reading boolean data, false value returned.");
				bool = false;
			}
		} catch (Exception exception) {
			error("Error reading boolean data, false value returned.");
			bool = false;
		}
		return bool;
	}

	/**
	 * Ritorna un char letto dallo standard input
	 * @return value : un char letto dallo standard input
	 */
	public static char readChar() {
		String token = getNextToken(false);
		char value;
		try {
			if (token.length() > 1) {
				current_token = token.substring(1, token.length());
			} else
				current_token = null;
			value = token.charAt(0);
		} catch (Exception exception) {
			error("Error reading char data, MIN_VALUE value returned.");
			value = Character.MIN_VALUE;
		}

		return value;
	}

	/**
	 * Ritorna un intero letto dallo standard input
	 * @return value: un intero letto dallo standard input
	 */
	public static int readInt() {
		String token = getNextToken();
		int value;
		try {
			value = Integer.parseInt(token);
		} catch (Exception exception) {
			error("Error reading int data, MIN_VALUE value returned.");
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Ritorna un long letto dallo standard input
	 * @return value: un long letto dallo standard input
	 */
	public static long readLong() {
		String token = getNextToken();
		long value;
		try {
			value = Long.parseLong(token);
		} catch (Exception exception) {
			error("Error reading long data, MIN_VALUE value returned.");
			value = Long.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Ritorna un float letto dallo standard input
	 * @return value: un float letto dallo standard input
	 */
	public static float readFloat() {
		String token = getNextToken();
		float value;
		try {
			value = (new Float(token)).floatValue();
		} catch (Exception exception) {
			error("Error reading float data, NaN value returned.");
			value = Float.NaN;
		}
		return value;
	}


	/**
	 * Ritorna un double letto dallo standard input
	 * @return value: un double letto dallo standard input
	 */
	public static double readDouble() {
		String token = getNextToken();
		double value;
		try {
			value = (new Double(token)).doubleValue();
		} catch (Exception exception) {
			error("Error reading double data, NaN value returned.");
			value = Double.NaN;
		}
		return value;
	}
}

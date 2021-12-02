import java.util.*;
import java.io.*;

class Assessment2 {
	
	static int charClass;
	static char[] lexeme;
	static char nextChar;
	static int lexLen = 0;;
	static int nextToken;
	static int i;
	static int lineNum = 1;
	static BufferedReader in;
	static char[] c;
	static String input = null;

	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int DECIMAL = 2;
	static final int UNDERSCORE = 3;
	static final int UNKNOWN = 99;
	static final int EOF = -1;

	static final int INT_LIT = 10;
	static final int IDENT = 11;
	static final int FLOAT_LIT = 12;
	static final int ASSIGN_OP = 20;
	static final int ADD_OP = 21;
	static final int SUB_OP = 22;
	static final int MULT_OP = 23;
	static final int DIV_OP = 24;
	static final int LEFT_PAREN = 25;
	static final int RIGHT_PAREN = 26;
	static final int DECIMAL_POINT = 27;
	static final int MOD_OP = 28;
	static final int LEFT_BRACKET = 29;
	static final int RIGHT_BRACKET = 30;
	static final int SEMI = 31;
	static final int GREATER_THAN = 32;
	static final int LESS_THAN = 33;
	static final int EXCLAMATION = 34;
	static final int COLON = 35;

	static final int FOR_CODE = 36;
	static final int IF_CODE = 37;
	static final int ELSE_CODE = 38;
	static final int WHILE_CODE = 39;
	static final int DO_CODE = 40;
	static final int INT_CODE = 41;
	static final int FLOAT_CODE = 42;
	static final int SWITCH_CODE = 43;
	static final int CASE_CODE = 44;
	static final int BREAK_CODE = 45;
	static final int DEFAULT_CODE = 44;
	static final int FOREACH_CODE = 45;
	static final int RETURN_CODE = 46;
	static final int IN_CODE = 47;
	static final int VOID_CODE = 48;
	
	static File file = new File("test.txt");
	
	public static void main(String[] args) {
		//ArrayList<Integer> tokens = new ArrayList<>();
		try {
			in = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException f) {
			System.out.println("This file does not exist");
		}
		readLine(file);
		getChar();
		while (input != null) {
			analyze();
			statement();
		}
		try {
			in.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void readLine(File file) {
		try {
			input = in.readLine();
			if (input != null) {
				c = input.toCharArray();
				i = 0;
			}
			else {
				System.out.println("Reached end of file");
				System.exit(0);
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static int lookup(char ch) {
		switch(ch) {
			case '(':
				addChar();
				nextToken = LEFT_PAREN;
				break;
			case ')':
				addChar();
				nextToken = RIGHT_PAREN;
				break;
			case '+':
				addChar();
				nextToken = ADD_OP;
				break;
			case '-':
				addChar();
				nextToken = SUB_OP;
				break;
			case '=':
				addChar();
				nextToken = ASSIGN_OP;
				break;
			case '*':
				addChar();
				nextToken = MULT_OP;
				break;
			case '/':
				addChar();
				nextToken = DIV_OP;
				break;
			case '.':
				addChar();
				nextToken = DECIMAL_POINT;
				break;
			case '%':
				addChar();
				nextToken = MOD_OP;
				break;
			case '{':
				addChar();
				nextToken = LEFT_BRACKET;
				break;
			case '}': 
				addChar();
				nextToken = RIGHT_BRACKET;
				break;
			case ';':
				addChar();
				nextToken = SEMI;
				break;
			case '>':
				addChar();
				nextToken = GREATER_THAN;
				break;
			case '<':
				addChar();
				nextToken = LESS_THAN;
				break;
			case '!':
				addChar();
				nextToken = EXCLAMATION;
			case ':':
				addChar();
				nextToken = COLON;
			default:
				addChar();
				nextToken = EOF;
				break;
		}
		return nextToken;
	}
	
	public static void addChar() {
		
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
		}
		else {
			System.out.println("Error - lexeme is too long\n");
		}
	}
	
	public static void getChar() {
			if (i < c.length) {
				nextChar = c[i++];
				if (Character.isDigit(nextChar)) {
					charClass = DIGIT;
				}
				else if (Character.isLetter(nextChar)) {
					charClass = LETTER;
				}
				else if (nextChar == '_') {
					charClass = UNDERSCORE;
				}
				else {
					charClass = UNKNOWN;
				}
			}
			else {
				charClass = EOF;
			}
	}
	
	public static void getNonBlank() {
		while (Character.isWhitespace(nextChar)) {
			if (i < c.length) {
				getChar();
			}
			break;
		}
	}
	
	public static int analyze() {
		lexLen = 0;
		lexeme = new char[100];
		String lexem = null;
		getNonBlank();
		switch (charClass) {
			case LETTER:
				addChar();
				getChar();
				while (charClass == LETTER || charClass == DIGIT || charClass == UNDERSCORE) {
					addChar();
					getChar();
				}
				nextToken = IDENT;
				break;
			case UNDERSCORE:
				addChar();
				getChar();
				while (charClass == LETTER || charClass == DIGIT || charClass == UNDERSCORE) {
					addChar();
					getChar();
				}
				nextToken = IDENT;
				break;
			 
		 case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			}
			if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
				addChar();
				getChar();
				nextToken = FLOAT_LIT;
			}
			else if(nextChar == 'E' || nextChar == 'e'){
				addChar();
				getChar();
				if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
					addChar();
					getChar();
				}
				else if (nextChar == '+') {
					addChar();
					getChar();
					if (charClass == DIGIT){
						addChar();
						getChar();
						while (charClass == DIGIT) {
							addChar();
							getChar();
						}
						if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
							addChar();
							getChar();
						}
					}
				}
				else if (charClass == DIGIT) {
					while (charClass == DIGIT) {
						addChar();
						getChar();
					}
					if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
						addChar();
						getChar();
					}
				}
				nextToken = FLOAT_LIT;
			}
			else if (charClass == UNKNOWN && nextChar == '.') {
				addChar();
				getChar();
				while (charClass == DIGIT) {
					addChar();
					getChar();
				}
				if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
					addChar();
					getChar();
				}
				else if(nextChar == 'E' || nextChar == 'e'){
					addChar();
					getChar();
					if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
						addChar();
						getChar();
					}
					else if (nextChar == '+') {
						addChar();
						getChar();
						if (charClass == DIGIT){
							addChar();
							getChar();
							while (charClass == DIGIT) {
								addChar();
								getChar();
							}
							if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
								addChar();
								getChar();
							}
						}
					}
					else if (charClass == DIGIT) {
						while (charClass == DIGIT) {
							addChar();
							getChar();
						}
						if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
							addChar();
						}
					}
				}
				nextToken = FLOAT_LIT;
			}
			else {
				nextToken = INT_LIT;
			}
			break;

		 case UNKNOWN:
			if (nextChar == '.') {
				addChar();
				getChar();
				if (charClass == DIGIT) {
					addChar();
					getChar();
					while (charClass == DIGIT) {
						addChar();
						getChar();
					}
					if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
						addChar();
						getChar();
					}
					else if(nextChar == 'E' || nextChar == 'e'){
						addChar();
						getChar();
						if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
							addChar();
							getChar();
						}
						else if (nextChar == '+') {
							addChar();
							getChar();
							if (charClass == DIGIT){
								addChar();
								getChar();
								while (charClass == DIGIT) {
									addChar();
									getChar();
								}
								if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
									addChar();
									getChar();
								}
							}
						}
						else if (charClass == DIGIT) {
							while (charClass == DIGIT) {
								addChar();
								getChar();
							}
							if (nextChar == 'f' || nextChar == 'F' || nextChar == 'd' || nextChar == 'D'){
								addChar();
								getChar();
							}
						}
					}
					nextToken = FLOAT_LIT;					
				}
				else { 
					nextToken = DECIMAL_POINT;
				}
			}
			else {
				lookup(nextChar);
				getChar();
			}
			break;
		case EOF:
			nextToken = EOF;
			break;
		}
		if (nextToken == EOF) {
			readLine(file);
			lineNum++;
			getChar();
			analyze();
		}
		lexem = String.copyValueOf(lexeme, 0, lexLen);
		if (nextToken == IDENT) {
			switch (lexem){
				case "for":
					nextToken = FOR_CODE;
					break;
				case "if":
					nextToken = IF_CODE;
					break;
				case "else":
					nextToken = ELSE_CODE;
					break;
				case "while":
					nextToken = WHILE_CODE;
					break;
				case "do":
					nextToken = DO_CODE;
					break;
				case "int":
					nextToken = INT_CODE;
					break;
				case "float":
					nextToken = FLOAT_CODE;
					break;
				case "switch":
					nextToken = SWITCH_CODE;
					break;
				case "case":
					nextToken = CASE_CODE;
					break;
				case "break":
					nextToken = BREAK_CODE;
					break;
				case "default":
					nextToken = DEFAULT_CODE;
					break;
				case "foreach":
					nextToken = FOREACH_CODE;
					break;
				case "return":
					nextToken = RETURN_CODE;
					break;
				case "in":
					nextToken = IN_CODE;
					break;
				case "void":
					nextToken = VOID_CODE;
					break;
				default:
					break;
			}
		}
		System.out.println("Next token is " + nextToken + ", next lexem is " + lexem);	
		return nextToken;
	}
	
	public static void statement() {
		switch(nextToken) {
			case IF_CODE:
				ifstmt();
				break;
			case WHILE_CODE:
				whilestmt();
				break;
			case FOR_CODE:
				forstmt();
				break;
			case SWITCH_CODE:
				switchstmt();
				break;
			case DO_CODE:
				dowhilestmt();
				break;
			case IDENT:
				assignstmt();
				if (nextToken != SEMI) {
					System.out.println("Expected ';' at Line " + lineNum);
					System.exit(0);
				}
				break;
			case FOREACH_CODE:
				foreachstmt();
				break;
			case VOID_CODE:
				methodstmt();
				break;
			default:
				expr();
				if (nextToken != SEMI) {
					System.out.println("Expected ';' at Line " + lineNum);
					System.exit(0);
				}
				break;
		}
	}
	
	public static void ifstmt() {
		if(nextToken != IF_CODE) {
			System.out.println("Error: Expected if at if statement at Line " + lineNum);
			System.exit(0);
		}	
		else {
			System.out.println("Enter <ifstmt>");
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: Expected '(' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				boolexpr();
				if (nextToken != RIGHT_PAREN) {
					System.out.println("Error: Expected ')' at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					block();
					if (nextToken == ELSE_CODE) {
						analyze();
						block();
					}
					System.out.println("Exit <ifstmt>");
				}
			}
		}
	}
	
	public static void whilestmt() {
		if(nextToken != WHILE_CODE) {
			System.out.println("Error: Expected while at while statement at Line " + lineNum);
			System.exit(0);
		}
		else {
			System.out.println("Enter <whilestmt>");
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: Expected '(' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				boolexpr();
				if (nextToken != RIGHT_PAREN) {
					System.out.println("Error: Expected ')' at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					block();
					System.out.println("Exit <whilestmt>");
				}
			}
		}
	}
	
	public static void forstmt() {
		if(nextToken != FOR_CODE) {
			System.out.println("Error: Expected for at for statement at Line " + lineNum);
			System.exit(0);
		}
		else {
			System.out.println("Enter <forstmt>");
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: Expected '(' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				assignstmt();
				if (nextToken != SEMI) {
					System.out.println("Error: Expected ';' in for statement at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					boolexpr();
					if (nextToken != SEMI) {
						System.out.println("Error: Expected ';' in for statement at Line " + lineNum);
						System.exit(0);
					}
					else {
						analyze();
						assignstmt();
						if (nextToken != RIGHT_PAREN) {
							System.out.println("Error: Expected ')' at Line " + lineNum);
							System.exit(0);
						}
						else {
							analyze();
							block();
							System.out.println("Exit <forstmt>");
						}	
					}
				}
			}
		}
	}
	
	public static void switchstmt() {
		if (nextToken != SWITCH_CODE) {
			System.out.println("Error: Expected switch at switch statement at Line " + lineNum);
			System.exit(0);
		}
		else {
			System.out.println("Enter <switchstmt>");
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: Expected '(' at Line " + lineNum);
				System.exit(0);
			}
			else {
				expr();
				if (nextToken != LEFT_BRACKET) {
					System.out.println("Error: Expected '{' at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					if (nextToken != CASE_CODE) {
						System.out.println("Error: Expected 'case' keyword at Line " + lineNum);
						System.exit(0);
					}
					else {
						while (nextToken == CASE_CODE) {
							cases();
							analyze();
						}
						if (nextToken == DEFAULT_CODE) {
							defaults();
							analyze();
						}
						if (nextToken != RIGHT_BRACKET) {
							System.out.println("Error: Expected '{' at Line " + lineNum);
							System.exit(0);
						}
						else {
							System.out.println("Exit <switchstmt>");
						}
					}
				}
			}
		}
	}
	
	public static void cases() {
		analyze();
		if (nextToken != IDENT || nextToken != INT_LIT || nextToken != FLOAT_LIT) {
			System.out.println("Error: Expected literal value at Line " + lineNum);
		}
		else {
			analyze();
			if (nextToken != COLON) {
				System.out.println("Error: Expected ':' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				while (nextToken != BREAK_CODE) {
					statement();
					analyze();
				}
				if (nextToken != SEMI) {
						System.out.println("Error: Expected ';' at Line " + lineNum);
						System.exit(0);
				}
			}
		}
	}
	
	public static void defaults() {
		analyze();
		if (nextToken != COLON) {
			System.out.println("Error: Expected ':' at Line " + lineNum);
			System.exit(0);
		}
		else {
			analyze();
			while (nextToken != BREAK_CODE) {
				statement();
				analyze();
			}
			if (nextToken != SEMI) {
					System.out.println("Error: Expected ';' at Line " + lineNum);
					System.exit(0);
			}
		}
	}
	
	public static void dowhilestmt() {
		if (nextToken != SWITCH_CODE) {
			System.out.println("Error: Expected do in do while at Line" + lineNum);
			System.exit(0);
		}
		else {
			System.out.println("Enter <dowhilestmt>");
			analyze();
			block();
			if (nextToken != WHILE_CODE) {
				System.out.println("Error: Expected while in do while at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				if (nextToken != LEFT_PAREN) {
					System.out.println("Error: Expected '(' at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					boolexpr();
					if (nextToken != RIGHT_PAREN) {
						System.out.println("Error: Expected ')' at Line " + lineNum);
						System.exit(0);
					}
					else {
						analyze();
						if (nextToken != SEMI) {
							System.out.println("Error: Expected ';' at Line " + lineNum);
							System.exit(0);
						}
						else {
							System.out.println("Exit <dowhilestmt>");
						}
					}
				}
			}
			
		}
	}
	
	public static void foreachstmt() {
		if (nextToken != FOREACH_CODE) {
			System.out.println("Error: Expected foreach at Line " + lineNum);
			System.exit(0);
		}
		else {
			System.out.println("Enter <foreachstmt>");
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: Expected '(' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				if (nextToken != IDENT || nextToken != INT_CODE) {
					System.out.println("Error: Not an identifier at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					if (nextToken != IDENT) {
						System.out.println("Error: Not an identifier at Line " + lineNum);
						System.exit(0);
					}
					else {
						analyze();
						if (nextToken != IN_CODE) {
							System.out.println("Error: Incorrect syntax. Supposed to be \"in\" at Line " + lineNum);
							System.exit(0);
						}
						else {
							analyze();
							if (nextToken != IDENT) {
								System.out.println("Error: Not an identifier at Line " + lineNum);
								System.exit(0);
							}
							else {
								analyze();
								if (nextToken != RIGHT_PAREN) {
									System.out.println("Error: Expected ')' at Line " + lineNum);
									System.exit(0);
								}
								else {
									analyze();
									block();
									System.out.println("Exit <foreachstmt>");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void assignstmt() {
		System.out.println("Enter <assignstmt>");
		analyze();
		if (nextToken == ADD_OP || nextToken == SUB_OP) {
			analyze();
			if (nextToken == ADD_OP || nextToken == SUB_OP) {
				analyze();
				System.out.println("Exit <assignstmt>");
			}
			else if (nextToken == ASSIGN_OP) {
				analyze();
				expr();
				System.out.println("Exit <assignstmt>");
			}
			else {
				System.out.println("Error: Expected assignment statement at Line " + lineNum);
			}
		}
		else {
			if (nextToken == IDENT) {
				analyze();
			}
			if (nextToken != ASSIGN_OP) {
				System.out.println("Error: Expected '=' at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				expr();
				if (nextToken != SEMI) {
					System.out.println("Error: Expected ';' at Line " + lineNum);
					System.exit(0);
				}
				else {
					System.out.println("Exit <assignstmt>");
				}
			}
		}
	}
	
	public static void block() {
		if (nextToken != LEFT_BRACKET) {
			System.out.println("Error: Missing '{' at Line " + lineNum);
			System.exit(0);	
		}
		else {
			System.out.println("Enter <block>");
			analyze();
			while (nextToken != RIGHT_BRACKET) {
				statement();
				analyze();
			}
			System.out.println("Exit <block>");
		}
	}
	
	public static void expr() {
		System.out.println("Enter <expr>");
		term();
		while (nextToken == ADD_OP || nextToken == SUB_OP) {
			term();
			analyze();
		}
		System.out.println("Exit <expr>");
	}

	public static void term() {
		System.out.println("Enter <term>");
		factor();
		while (nextToken == MULT_OP || nextToken == DIV_OP || nextToken == MOD_OP) {
			factor();
			analyze();
		}
		System.out.println("Exit <term>");
	}

	public static void factor() {
		System.out.println("Enter <factor>");
		if (nextToken == IDENT || nextToken == INT_LIT || nextToken == FLOAT_LIT) {
			analyze();
			System.out.println("Exit <factor>");
		}
		else {
			if (nextToken == LEFT_PAREN) {
				analyze();
				expr();
				if (nextToken == RIGHT_PAREN) {
					analyze();
					System.out.println("Exit <factor>");
				}
				else {
					System.out.println("Error: Expected ')' for expression at Line " + lineNum);
					System.exit(0);
				}
			}
			else {
				System.out.println("Error: Expected ')' for expression at Line " + lineNum);
				System.exit(0);
			}
		}			
	}
	
	public static void boolexpr() {
		System.out.println("Enter <boolexpr>");
		expr();
		if (nextToken == GREATER_THAN || nextToken == LESS_THAN) {
			analyze();
			if (nextToken == ASSIGN_OP) {
				analyze();
			}
			expr();
			System.out.println("Exit <boolexpr>");
		}
		else if (nextToken == ASSIGN_OP) {
			analyze();
			if (nextToken == ASSIGN_OP) {
				analyze();
				expr();
				System.out.println("Exit <boolexpr>");
			}
			else {
				System.out.println("Error: Expected boolean operation at Line " + lineNum);
				System.exit(0);
			}
		}
		else if (nextToken == EXCLAMATION) {
			if (nextToken == ASSIGN_OP) {
				analyze();
				expr();
				System.out.println("Exit <boolexpr>");
			}
			else {
				System.out.println("Error: Expected boolean operation at Line " + lineNum);
				System.exit(0);
			}
		}
		else {
			System.out.println("Error: Expected boolean operation at Line " + lineNum);
			System.exit(0);
		}
	}
	
	public static void methodstmt() {
		System.out.println("Enter <funcstmt>");
		analyze();
		if (nextToken != IDENT) {
			System.out.println("Error: Function name expected at Line " + lineNum);
			System.exit(0);
		}
		else {
			analyze();
			if (nextToken != LEFT_PAREN) {
				System.out.println("Error: ')' expected at Line " + lineNum);
				System.exit(0);
			}
			else {
				analyze();
				if (nextToken != RIGHT_PAREN) {
					System.out.println("Error: Function name expected at Line " + lineNum);
					System.exit(0);
				}
				else {
					analyze();
					block();
					System.out.println("Exit <funcstmt>");
				}
			}
		}
		
	}
}
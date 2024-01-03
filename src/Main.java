import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	//All reserved words
	private static String[] RESERVED_WORDS = {"and", "array", "begin", "case", "const", "div", "do", "downto", "else", "end", "file", "for", "function", "goto", "if", "in", "label", "mod", "nil", "not", "of", "or", "packed", "procedure", "program", "record","repeat","set","then","to","type","until","var","while","with"};

    // Operators in Pascal
    public enum PascalOperator {
        PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), MOD("%"), ASSIGN(":="), EQUAL("="), NOT_EQUAL("<>"), LESS_THAN("<"), LESS_THAN_OR_EQUAL("<="), GREATER_THAN(">"), GREATER_THAN_OR_EQUAL(">=");
        private String lexeme;
        private PascalOperator(String lexeme) {
            this.lexeme = lexeme;
        }
        public String getLexeme() {
            return lexeme;
        }
    }

    // Acceptable characters for variables and constants
    private static String VARIABLE_REGEX = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static String CONSTANT_REGEX = "\\d+";
    
    // Token class to hold token type and lexeme
    public static class Token {
        private TokenType type;
        private String lexeme;
        public Token(TokenType type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }
        public TokenType getType() {
            return type;
        }
        public String getLexeme() {
            return lexeme;
        }
        @Override
        public String toString() {
            return "<" + type + ", " + lexeme + ">";
        }
    }
    
    // Method to analyze input string and return token type and lexeme
    public Token analyze(String input) {
        if (Arrays.asList(RESERVED_WORDS).contains(input)) {
            return new Token(TokenType.RESERVED_WORD, input);
        } else if (Arrays.stream(PascalOperator.values()).anyMatch(o -> o.getLexeme().equals(input))) {
            return new Token(TokenType.OPERATOR, input);
        } else if (input.matches(VARIABLE_REGEX)) {
            return new Token(TokenType.VARIABLE, input);
        } else if (input.matches(CONSTANT_REGEX)) {
            return new Token(TokenType.CONSTANT, input);
        } else {
            return new Token(TokenType.UNKNOWN, input);
        }
    }

    // Enum to represent token types
    public enum TokenType {
        RESERVED_WORD, OPERATOR, VARIABLE, CONSTANT, UNKNOWN
    }

    public static void main(String[] args) throws FileNotFoundException {
        Main lexer = new Main();
        FileInputStream fileInputStream = new FileInputStream("Parse Project Pascal Code.txt");
        Scanner scanner = new Scanner(fileInputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\s+|\n+|\r+");
            for (String token : tokens) {
                Token analyzedToken = lexer.analyze(token);
                System.out.println(analyzedToken);
            }
        }
    }

}

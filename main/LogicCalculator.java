import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class LogicCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a logical expression:");
        System.out.println("Supported operators:");
        System.out.println("and");
        System.out.println("or");
        System.out.println("not");
        System.out.println("xor");
        System.out.println("xnor");
        System.out.println("conditional: A conditional B (A -> B)");
        System.out.println("biconditional: A biconditional B (A <-> B)");
        System.out.println("(): Parentheses");
        System.out.println("Variables: p, q, r, ...");
        System.out.println("Example: (p and q) conditional not r");

        String expression = input.nextLine().toLowerCase().trim();

        try {
            printTruthTable(expression);
        } catch (Exception e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
    }

    public static void printTruthTable(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> variables = extractVariables(tokens);

        if (variables.isEmpty()) {
            throw new RuntimeException("Use at least one variable such as p or q");
        }

        int rows = 1 << variables.size();
        String header = buildHeader(variables, expr);
        System.out.println(header);
        System.out.println(repeat("-", header.length()));

        for (int mask = 0; mask < rows; mask++) {
            Map<String, Boolean> assignment = buildAssignment(variables, mask);
            boolean result = evaluate(tokens, assignment);
            System.out.println(buildRow(variables, assignment, result));
        }
    }

    public static boolean evaluate(List<String> tokens, Map<String, Boolean> valuesByVariable) {
        Stack<Boolean> values = new Stack<Boolean>();
        Stack<String> ops = new Stack<String>();

        for (String token : tokens) {
            if (isBooleanLiteral(token)) {
                values.push(Boolean.parseBoolean(token));
            } else if (isVariable(token)) {
                if (!valuesByVariable.containsKey(token)) {
                    throw new RuntimeException("Missing value for variable: " + token);
                }
                values.push(valuesByVariable.get(token));
            } else if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    processTop(values, ops);
                }
                if (ops.isEmpty()) {
                    throw new RuntimeException("Mismatched parentheses");
                }
                ops.pop();
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && !ops.peek().equals("(")
                        && precedence(ops.peek()) >= precedence(token)) {
                    processTop(values, ops);
                }
                ops.push(token);
            } else {
                throw new RuntimeException("Unknown token: " + token);
            }
        }

        while (!ops.isEmpty()) {
            if (ops.peek().equals("(")) {
                throw new RuntimeException("Mismatched parentheses");
            }
            processTop(values, ops);
        }

        if (values.size() != 1) {
            throw new RuntimeException("Invalid expression");
        }

        return values.pop();
    }

    public static void processTop(Stack<Boolean> values, Stack<String> ops) {
        String op = ops.pop();

        if (op.equals("not")) {
            if (values.isEmpty()) {
                throw new RuntimeException("Missing operand for NOT");
            }
            boolean a = values.pop();
            values.push(!a);
        } else {
            if (values.size() < 2) {
                throw new RuntimeException("Missing operands for " + op);
            }
            boolean b = values.pop();
            boolean a = values.pop();
            values.push(applyBinary(a, b, op));
        }
    }

    public static boolean applyBinary(boolean a, boolean b, String op) {
        if (op.equals("and")) {
            return a && b;
        } else if (op.equals("or")) {
            return a || b;
        } else if (op.equals("xor")) {
            return a ^ b;
        } else if (op.equals("conditional")) {
            return !a || b;
        } else if (op.equals("biconditional")) {
            return a == b;
        } else if (op.equals("xnor")) {
            return a == b;
        }

        throw new RuntimeException("Invalid operator: " + op);
    }

    public static boolean isOperator(String token) {
        return token.equals("and") || token.equals("or") || token.equals("not")
                || token.equals("xor") || token.equals("conditional")
                || token.equals("biconditional") || token.equals("xnor");
    }

    public static int precedence(String op) {
        if (op.equals("not")) {
            return 5;
        } else if (op.equals("and")) {
            return 4;
        } else if (op.equals("xor") || op.equals("xnor")) {
            return 3;
        } else if (op.equals("or")) {
            return 2;
        } else if (op.equals("conditional")) {
            return 1;
        } else if (op.equals("biconditional")) {
            return 0;
        }
        return -1;
    }

    public static List<String> tokenize(String expr) {
        expr = expr.replace("(", " ( ").replace(")", " ) ");
        String[] parts = expr.trim().split("\\s+");
        List<String> tokens = new ArrayList<String>();

        for (String part : parts) {
            if (!part.isEmpty()) {
                tokens.add(part);
            }
        }

        return tokens;
    }

    public static List<String> extractVariables(List<String> tokens) {
        Set<String> variableSet = new LinkedHashSet<String>();

        for (String token : tokens) {
            if (isVariable(token)) {
                variableSet.add(token);
            }
        }

        List<String> variables = new ArrayList<String>(variableSet);
        Collections.sort(variables);
        return variables;
    }

    public static boolean isVariable(String token) {
        return token.matches("[a-z]+") && !isOperator(token) && !isBooleanLiteral(token);
    }

    public static boolean isBooleanLiteral(String token) {
        return token.equals("true") || token.equals("false");
    }

    public static Map<String, Boolean> buildAssignment(List<String> variables, int mask) {
        Map<String, Boolean> assignment = new TreeMap<String, Boolean>();
        int variableCount = variables.size();

        for (int i = 0; i < variableCount; i++) {
            boolean value = ((mask >> (variableCount - i - 1)) & 1) == 1;
            assignment.put(variables.get(i), value);
        }

        return assignment;
    }

    public static String buildHeader(List<String> variables, String expression) {
        StringBuilder header = new StringBuilder();

        for (String variable : variables) {
            header.append(String.format("%-7s", variable));
        }
        header.append("| ").append(expression);

        return header.toString();
    }

    public static String buildRow(List<String> variables, Map<String, Boolean> assignment, boolean result) {
        StringBuilder row = new StringBuilder();

        for (String variable : variables) {
            row.append(String.format("%-7s", assignment.get(variable) ? "T" : "F"));
        }
        row.append("| ").append(result ? "T" : "F");

        return row.toString();
    }

    public static String repeat(String text, int count) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            result.append(text);
        }

        return result.toString();
    }
}

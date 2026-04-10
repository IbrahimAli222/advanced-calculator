import java.util.*;

public class LogicCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a logical expression:");
        System.out.println("supported operators: ");
        System.out.println("and");
        System.out.println("or");
        System.out.println("not");
        System.out.println("xor");
        System.out.println("xnor");
        System.out.println("conditional: If A then B (A → B)");
        System.out.println("biconditional: A if and only if B (A ↔ B)");
        System.out.println("(): Parentheses");
        System.out.println("true / false: boolean values");

        String expression = input.nextLine().toLowerCase();

        try {
            boolean result = evaluate(expression);
            System.out.println("Result = " + result);
        } catch (Exception e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
    }

    public static boolean evaluate(String expr) {
        List<String> tokens = tokenize(expr);
        Stack<Boolean> values = new Stack<Boolean>();
        Stack<String> ops = new Stack<String>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (token.equals("true")) {
                values.push(true);
            } else if (token.equals("false")) {
                values.push(false);
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

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                tokens.add(parts[i]);
            }
        }

        return tokens;
    }
}
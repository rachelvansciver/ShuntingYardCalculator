import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
public class ShuntingYard {
    /**@author Rachel
     * @param user generated math problem in prefix notation
     * @returns user generated math problem in postfix notation
     * psuedocode found at https://en.wikipedia.org/wiki/Shunting-yard_algorithm
     * other resources: https://eddmann.com/posts/shunting-yard-implementation-in-java/**/
    Stack<String> stack;
    StringBuilder postfix;
    public ShuntingYard() {
        this.stack = new Stack<>();
        this.postfix = new StringBuilder();
    }
    static enum operators {
        /**assigns operator operator precedence
         * @param precedence - numbered operator precedence
         * @return matches operator in hashmap operations and returns precedence
         */
        EXPONENTS(3),
        MULTIPLY(2),
        DIVIDE(2),
        ADD(1),
        SUBTRACT(1);

        final int precedence;
        operators(int p){
            this.precedence = p;
        }
    }
    public boolean isDigit(String s) {
        /**Evaluates if string is digit
         * @param String s
         * @return Evaluates if digit*/
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    static Map<String, operators> operations = new HashMap<String, operators>() {{
        /**matches character with operators
         * @param String
         * @param operators - matches with enumerator operators
         */
        put("^", operators.EXPONENTS);
        put("*", operators.MULTIPLY);
        put("/", operators.DIVIDE);
        put("+", operators.ADD);
        put("-", operators.SUBTRACT);

    }};
    public boolean isHigherPrecedence(String current, String next) {
        /**@param current - current token
         * @param next - next token
         * @return boolean for evaluating precedence
         */
        return operations.containsKey(next) && operations.get(current).precedence >= operations.get(next).precedence;
    }

    String shuntingYard(StringBuilder postfix) {
        /**implementation of shunting yard algorithm.
         * @param Calculator.infix - user generated math problem
         * @return postfix - Calculator.infix in postfix notation
         */
        try {
            for(String i: Calculator.infix) {
                if(operations.containsKey(i)) {
                    /**if operator on top of stack is higher precedence, pop operators
                     off stack and onto output*/
                    while(!stack.isEmpty() && isHigherPrecedence(i, stack.peek())) {
                        postfix.append(stack.pop());
                    }
                    /**when operator is of equal or lower precedence, add it to stack*/
                    stack.push(i);
                }
                else if(i.equals("(")) {
                    //add left parenthesis to output queue
                    stack.push(i);
                }
                else if(i.equals(")")) {
                    //pop operators off stack until a match is found
                    while(!stack.isEmpty() && !stack.peek().equals("(")) {
                        postfix.append(stack.pop());
                    }
                    //pop parenthesis off stack if matching is found
                    stack.pop();
                } else {
                    postfix.append(i);
                }
            }
            while(!stack.isEmpty()) {
                //pops off stack and adds it to the output
                postfix.append(stack.pop());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return postfix.toString();
    }
}

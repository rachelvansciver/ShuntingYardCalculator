import java.util.LinkedList;
import java.util.Stack;
public class Arithmetic {
    /**
     * @author Rachel
     * @param postfix StringBuilder from ShuntingYard class
     * @returns solved mathematical expression
     * This class evaluates expressions in postfix notation produces a result using a stack for operands. Once an operator
     * is found in the postfix expression, the operands are popped off the stack and evaluated. Supports adding numbers
     * with decimal places & multi-digit numbers.
     * Resources for supporting multi-digit numbers found at
     * https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-expression/
     */
    ShuntingYard o = new ShuntingYard();
    Stack<Float> operandStack = new Stack<>();
    LinkedList<Float> resultList = new LinkedList<>();
    /**
     * list for displaying result one expression at a time, as opposed to showing the entire stack
     */
    float a = 0;
    float b = 0;
    char operand;
    float result = 0;

    Arithmetic() {
        this.operandStack = new Stack<>();
    }

    /*** @throws Exception*/
    void evalPostFix() throws Exception {
        /**evaluates the string in postfix notation and displays result
         * @param - expression - calls ShuntingYard class and generates a string from
         * the postfix StringBuilder
         * @throws ArithmeticException for division by zero
         * @throws Exception for general syntax errors*/
        String expression = o.shuntingYard(o.postfix);
        boolean hasDecimal = false;
        float divisor = 0.1F;
        boolean isNegative = false;
        try {
            for (int i = 0; i < expression.length(); i++) {
                operand = expression.charAt(i);
                if (operand == '_') {
                    /**flags for number to be converted to negative*/
                    isNegative = true;
                    continue;
                }
                if (operand == ' ') {
                    continue;
                } else if (Character.isDigit(operand) || operand == '.' || operand == '_') {
                    a = 0;
                    divisor = 1;
                    hasDecimal = false;
                    while (Character.isDigit(operand) || operand == '.' || operand == '_') {
                        if (operand == '.') {
                            /** flags for decimal points*/
                            hasDecimal = true;
                        } else if (hasDecimal) {
                            /** does something similar to the reciprocal of the below function:
                             * ie for [3][.][6][6]: reads 3, saves it to float a, flags positive for a
                             * decimal place on next iteration, then for 6, it multiplies 6 * 0.1 to get 0.6,
                             * adds it to three, and multiplies by 0.01 to get 0.06 and adds it to 3.6 to get 3.66*/
                            a = a + ((float) (operand - '0') / (10 * divisor));
                            divisor *= 10;
                        } else {
                            /**adds the individual numbers into a single number. ie. for 36:
                             * multiplies the first a * 10, a becomes 30, then adds 6, becomes 36,
                             * then pushes it on the stack and repeats for any multi digit numbers.*/
                            a = a * 10 + (float) (operand - '0');
                        }
                        i++;
                        operand = expression.charAt(i);
                    }
                    if (isNegative) {
                        /**if is negative, multiplies by -1, and pushes onto stack*/
                        a *= -1;
                        isNegative = false;
                    }
                    i--;
                    operandStack.push(a);
                } else {
                    a = operandStack.pop();
                    b = operandStack.pop();
                    switch (operand) {
                        //if it encounters an operator, pops two operands off the stack and pushes result back onto stack
                        case '+':
                            result = operandStack.push(b + a);
                            break;
                        case '-':
                            result = operandStack.push(b - a);
                            break;
                        case '/':
                            result = operandStack.push(b / a);
                            break;
                        case '*':
                            result = operandStack.push(b * a);
                            break;
                        case '^':
                            result = operandStack.push((float) Math.pow(b, a));
                            break;
                        default:
                            Calculator.numArea.setText("Enter Valid Expression");
                            break;

                    }
                }
            }
            resultList.add(result);
            if (resultList.size() > 1) {
                //discards old answer
                resultList.removeFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
import java.util.Scanner;

public class PermutationAndCombination {
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to calculate:");
        System.out.println("1. Permutation");
        System.out.println("2. Combination");
        int choice = input.nextInt();
        if(choice == 1){
            int n, r, result;
            System.out.println("Permutation formula: P(n,r) = n!/(n-r)!");
            System.out.println("enter n: ");
            n = input.nextInt();

            System.out.println("enter r: ");
            r = input.nextInt();
            if(n < 0 || r < 0){
                throw new IllegalArgumentException("please enter positive numbers");}

            result = calculateFactorial(n) / calculateFactorial(n - r);
            System.out.println(result);
        } else if (choice == 2) {
            int n, r, result;
            System.out.println("combination formula: C(n,r) = n!/r!(n-r)!");
            System.out.println("enter n: ");
            n = input.nextInt();
            System.out.println("enter r: ");
            r = input.nextInt();
            result = calculateFactorial(n) / calculateFactorial(r) * calculateFactorial(n-r);

            if(n < 0 || r < 0){
                throw new IllegalArgumentException("please enter positive numbers");
            }
            System.out.println(result);
        }
    }
    public static int calculateFactorial(int n){
        if(n >=0){
            if(n == 0) return 1;

            int factorialResult = calculateFactorial(n - 1) * n;

            return factorialResult;}
        return -1;
    }
}

public class disc1 {
    public static void main(String[] args) {
        System.out.printf("\n%d is %d Fibonacci number\n", fib(4), 4);
        System.out.printf("\n%d is %d Fibonacci number\n", fib2(4, 0, 0, 1), 4);
    }

    public static int fib(int n) {
        int prev = 0, curr = 1, index = 0, temp;
        while(index != n) {
            temp = prev;
            prev = curr;
            curr = temp + curr;
            index = index + 1;
        }    
        return prev;
    }

    public static int fib2(int n, int k, int f0, int f1) {
        if(n == k) {
            return f0;
        }
        else {
            return fib2(n, k+1, f1, f0+f1);
        }
    }
}

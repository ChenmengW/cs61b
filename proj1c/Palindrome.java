/**
 * Created by varad on 7/19/16.
 */
public class Palindrome {

    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDequeSolution<Character>();
        for (int i = 0; i < word.length(); i++) {
            char x = word.charAt(i);
            deque.addLast(x);
        }

        return deque;
    }

    private static boolean isPalindrome(Deque<Character> x) {
        if (x.size() == 0 || x.size() == 1) {
            return true;
        }

        char first = x.removeFirst();
        char last = x.removeLast();

        boolean isPal = first == last;
        if (isPal) {
            return isPalindrome(x) && isPal;
        } else {
            return false;
        }
    }

    public static boolean isPalindrome(String word) {
        word = word.toLowerCase();      // Ideally this should be here. But not mentioned in spec
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }

    private static boolean isPalindrome(Deque<Character> x, CharacterComparator cc) {
        if (x.size() == 0 || x.size() == 1) {
            return true;
        }

        char first = x.removeFirst();
        char last = x.removeLast();

        boolean isPal = cc.equalChars(first, last);
        if (isPal) {
            return isPalindrome(x, cc) && isPal;
        } else {
            return false;
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        word = word.toLowerCase();      // Ideally this should be here. But not mentioned in spec
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque, cc);
    }

    public static void main(String[] args) {
        String word = args[0];
        Deque<Character> deque = wordToDeque(word);
        deque.printDeque();

        if (isPalindrome(word)) {
            System.out.println("Yes!");
        } else {
            System.out.println("No!");
        }

//        OffByOne obo = new OffByOne();
//
//        if (isPalindrome(word, obo)) {
//            System.out.println("Yes! Off By One.");
//        } else {
//            System.out.println("No!");
//        }

//        OffByN obn = new OffByN(5);
//        if (isPalindrome(word, obn)) {
//            System.out.println("Yes! Off By N.");
//        } else {
//            System.out.println("No!");
//        }
    }
}

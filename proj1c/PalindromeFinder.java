/**
 * This class outputs all palindromes in the words file in the current directory.
 */

public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words");

        CharacterComparator cc = new OffByN(5);

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && Palindrome.isPalindrome(word, cc)) {
                System.out.println(word);
            }
        }

        /** EXTRA 1 :
         *  For what N are there the most palindromes in English?
         *  Since characters can't differ by more than 25, iterate
         *  only upto there.
         */


//        int maxCount = 0;
//        int N = 0;
//
//        for (int i = 0; i < 26; i++) {
//            CharacterComparator cc = new OffByN(i);
//            int count = 0;
//
//            while (!in.isEmpty()) {
//                String word = in.readString();
//                if (word.length() >= minLength && Palindrome.isPalindrome(word, cc)) {
//                    count++;
//                }
//            }
//
//            if (count > maxCount) {
//                maxCount = count;
//                N = i;
//            }
//        }
//
//        System.out.println("N " + Integer.toString(N));   // N = 0 (70 palindromes)
//        System.out.println("Count " + Integer.toString(maxCount));


        /** EXTRA 2 :
         *  What is the longest offByN palindrome for any N?
         */

//        int maxLength = 0;
//        int N = 0;
//        String maxWord = "";
//
//        for (int i = 0; i < 26; i++) {
//            CharacterComparator cc = new OffByN(i);
//
//            while (!in.isEmpty()) {
//                String word = in.readString();
//                if (word.length() >= minLength && Palindrome.isPalindrome(word, cc)) {
//                    if (word.length() > maxLength) {
//                        maxLength = word.length();
//                        maxWord = word;
//                        N = i;
//                    }
//                }
//            }
//        }
//
//        System.out.println("N " + Integer.toString(N)); // 0
//        System.out.println("Word " + maxWord);  // Malayalam

    }
} 

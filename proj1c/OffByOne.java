/**
 * Created by varad on 7/19/16.
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int charDiff = Math.abs(x - y);

        if (charDiff == 1) {
            return true;
        } else {
            return false;
        }
    }
}

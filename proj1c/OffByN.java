/**
 * Created by varad on 7/19/16.
 */
public class OffByN implements CharacterComparator {

    private int diff;

    public OffByN(int N) {
        this.diff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int charDiff = Math.abs(x - y);

        if (charDiff == this.diff) {
            return true;
        } else {
            return false;
        }
    }
}

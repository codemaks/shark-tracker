package sharkitter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The class containing the Konami code.
 */
public class Konami {

    private List<Integer> enteredSequence;
    private static final int[] KONAMICODE = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};

    /**
     * Constructor of Konami class.
     * Creates a list of pressed keys.
     */
    public Konami() {
        enteredSequence = new ArrayList<Integer>();
    }

    /**
     * Adds the given key code to the list of pressed keys that are to be analysed.
     * @param keyCode the integer representation of the pressed key.
     */
    public void registerPressedKey(int keyCode) {
        enteredSequence.add(keyCode);
    }

    /**
     * Checks if the current registered keys form a Konami code.
     * @return true if the sequence is a Konami code, false otherwise.
     */
    public boolean checkKonamiCode() {
        int counter = 0;
        for(int i = 0; i < enteredSequence.size(); ++i) {
            if(enteredSequence.get(i) != KONAMICODE[i]) {
                reset();
                return false;
            }
            ++counter;
        }

        if(counter < KONAMICODE.length)
            return false;
        return true;
    }

    /**
     * Resets the sequence to be analysed.
     */
    public void reset() {
        enteredSequence.clear();
    }
}

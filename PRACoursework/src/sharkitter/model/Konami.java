package sharkitter.model;

import java.util.ArrayList;
import java.util.List;

public class Konami {

    private List<Integer> enteredSequence;
    private static final int[] KONAMICODE = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};

    /**
     * Instantiates a new Konami object with an empty ArrayList of integers corressponding to the sequence of buttons
     * pressed under numerical value.
     */
    public Konami() {
        enteredSequence = new ArrayList<Integer>();
    }


    /**
     * void method which adds a new element to the list of keys pressed
     * @param keyCode, an integer corresponding to the character value of one of the buttons to press
     */
    public void registerPressedKey(int keyCode) {
        enteredSequence.add(keyCode);
    }

    /**
     * boolean method which returns true if the right konami code was typed, otherwise false.
     * @return true if the right konami code was typed, otherwise false.
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

    public void reset() {
        enteredSequence.clear();
    }
}

package sharkitter.model;

import java.util.ArrayList;
import java.util.List;

public class Konami {

    private List<Integer> enteredSequence;
    private static final int[] KONAMICODE = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};

    public Konami() {
        enteredSequence = new ArrayList<Integer>();
    }


    public void registerPressedKey(int keyCode) {
        enteredSequence.add(keyCode);
    }

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

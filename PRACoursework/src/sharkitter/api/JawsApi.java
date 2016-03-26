package sharkitter.api;

import api.jaws.Jaws;

public class JawsApi {
    private static Jaws instance;

    public static Jaws getInstance() {
        if(instance == null) {
            instance = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
        }
        return instance;
    }
}

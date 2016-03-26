package sharkitter.api;

import api.jaws.Jaws;

    /**
     * Singleton class which can only return an instance of the Jaws class.
     */
public class JawsApi {
    private static Jaws instance;

    /**
     * static method returning an instance of the Jaws class from the api if there is any, otherwise returns a new Jaws.
     * @return instance
     */
    public static Jaws getInstance() {
        if(instance == null) {
            instance = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
        }
        return instance;
    }
}

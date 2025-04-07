package exceptionsJackut;

public class LoginInvalidoException extends JackutException {
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
package exceptionsJackut;

public class LoginOuSenhaInvalidoException extends JackutException {
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }
}
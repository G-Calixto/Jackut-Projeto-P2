package exceptionsJackut;

public class SenhaInvalidaException extends JackutException {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
package exceptionsJackut;

public class ContaJaExisteException extends JackutException {
    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
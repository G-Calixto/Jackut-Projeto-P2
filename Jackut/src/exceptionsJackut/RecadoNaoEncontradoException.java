package exceptionsJackut;

public class RecadoNaoEncontradoException extends JackutException {
    public RecadoNaoEncontradoException() {
        super("Não há recados.");
    }
}
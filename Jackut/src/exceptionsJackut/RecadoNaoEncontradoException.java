package exceptionsJackut;

public class RecadoNaoEncontradoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public RecadoNaoEncontradoException() {
        super("Não há recados.");
    }

    public RecadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
package exceptionsJackut;

public class ComunidadeNaoExisteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
} 
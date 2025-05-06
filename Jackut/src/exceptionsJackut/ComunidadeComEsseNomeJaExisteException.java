package exceptionsJackut;

public class ComunidadeComEsseNomeJaExisteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public ComunidadeComEsseNomeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }
} 
package exceptionsJackut;

public class MensagemNaoEncontradaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public MensagemNaoEncontradaException() {
        super("Não há mensagens.");
    }
} 
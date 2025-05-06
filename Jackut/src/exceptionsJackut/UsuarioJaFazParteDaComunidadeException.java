package exceptionsJackut;

public class UsuarioJaFazParteDaComunidadeException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaFazParteDaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }
} 
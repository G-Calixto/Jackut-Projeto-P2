package exceptionsJackut;

public class UsuarioJaAdicionadoComoPaqueraException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComoPaqueraException() {
        super("Usuário já está adicionado como paquera.");
    }
} 
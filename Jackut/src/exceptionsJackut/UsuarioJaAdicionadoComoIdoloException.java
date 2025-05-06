package exceptionsJackut;

public class UsuarioJaAdicionadoComoIdoloException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComoIdoloException() {
        super("Usuário já está adicionado como ídolo.");
    }
} 
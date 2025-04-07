package exceptionsJackut;

public class UsuarioJaAdicionadoComConvitePendenteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComConvitePendenteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
} 
package exceptionsJackut;

public class UsuarioJaAdicionadoComoInimigoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComoInimigoException() {
        super("Usuário já está adicionado como inimigo.");
    }
} 
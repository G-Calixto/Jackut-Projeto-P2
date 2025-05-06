package exceptionsJackut;

public class UsuarioNaoPodeAdicionarASiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
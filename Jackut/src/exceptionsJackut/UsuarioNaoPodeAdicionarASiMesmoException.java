package exceptionsJackut;

public class UsuarioNaoPodeAdicionarASiMesmoException extends JackutException {
    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
package exceptionsJackut;

public class UsuarioNaoCadastradoException extends JackutException {
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
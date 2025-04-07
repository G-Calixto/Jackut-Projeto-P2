package exceptionsJackut;

public class UsuarioJaAdicionadoException extends JackutException {
    public UsuarioJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
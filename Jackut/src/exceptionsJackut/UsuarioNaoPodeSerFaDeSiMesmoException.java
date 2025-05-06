package exceptionsJackut;

public class UsuarioNaoPodeSerFaDeSiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeSerFaDeSiMesmoException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
} 
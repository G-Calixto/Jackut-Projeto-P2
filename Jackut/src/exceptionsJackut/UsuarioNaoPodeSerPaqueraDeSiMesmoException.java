package exceptionsJackut;

public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
} 
package exceptionsJackut;

public class UsuarioInimigoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioInimigoException(String message) {
        super("Função inválida: " + message);
    }
} 
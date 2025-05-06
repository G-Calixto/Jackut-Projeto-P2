package entities.relationship;

import exceptionsJackut.UsuarioJaAdicionadoException;

public class Friendship extends AbstractRelationship {
    private static final long serialVersionUID = 1L;

    @Override
    public String getTipo() {
        return "amigo";
    }

    @Override
    public void adicionarRelacionamento(String login) throws UsuarioJaAdicionadoException {
        if (temRelacionamento(login)) {
            throw new UsuarioJaAdicionadoException();
        }
        adicionarRelacionamentoBase(login);
    }
} 
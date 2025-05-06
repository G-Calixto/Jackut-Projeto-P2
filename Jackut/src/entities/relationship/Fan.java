package entities.relationship;

import exceptionsJackut.UsuarioJaAdicionadoComoIdoloException;

public class Fan extends AbstractRelationship {
    private static final long serialVersionUID = 1L;

    @Override
    public String getTipo() {
        return "fa";
    }

    @Override
    public void adicionarRelacionamento(String login) throws UsuarioJaAdicionadoComoIdoloException {
        if (temRelacionamento(login)) {
            throw new UsuarioJaAdicionadoComoIdoloException();
        }
        adicionarRelacionamentoBase(login);
    }
} 
package entities.relationship;

import exceptionsJackut.UsuarioJaAdicionadoComoInimigoException;

public class Enemy extends AbstractRelationship {
    private static final long serialVersionUID = 1L;

    @Override
    public String getTipo() {
        return "inimigo";
    }

    @Override
    public void adicionarRelacionamento(String login) throws UsuarioJaAdicionadoComoInimigoException {
        if (temRelacionamento(login)) {
            throw new UsuarioJaAdicionadoComoInimigoException();
        }
        adicionarRelacionamentoBase(login);
    }
} 
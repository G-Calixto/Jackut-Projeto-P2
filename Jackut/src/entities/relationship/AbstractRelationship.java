package entities.relationship;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import exceptionsJackut.UsuarioJaAdicionadoException;
import exceptionsJackut.UsuarioJaAdicionadoComoIdoloException;
import exceptionsJackut.UsuarioJaAdicionadoComoInimigoException;

public abstract class AbstractRelationship implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Set<String> relationships;

    public AbstractRelationship() {
        this.relationships = new LinkedHashSet<>();
    }

    public abstract String getTipo();

    protected void adicionarRelacionamentoBase(String login) {
        relationships.add(login);
    }

    public abstract void adicionarRelacionamento(String login) throws UsuarioJaAdicionadoException, 
                                                                    UsuarioJaAdicionadoComoIdoloException, 
                                                                    UsuarioJaAdicionadoComoInimigoException;

    public void removerRelacionamento(String login) {
        relationships.remove(login);
    }

    public boolean temRelacionamento(String login) {
        return relationships.contains(login);
    }

    public Set<String> getRelacionamentos() {
        return new LinkedHashSet<>(relationships);
    }
} 
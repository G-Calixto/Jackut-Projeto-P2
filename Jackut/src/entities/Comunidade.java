package entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import exceptionsJackut.UsuarioJaFazParteDaComunidadeException;

public class Comunidade implements Serializable {
    private String nome;
    private String descricao;
    private String dono;
    private Set<String> membros;

    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new LinkedHashSet<>();
        this.membros.add(dono);
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDono() {
        return dono;
    }

    public Set<String> getMembros() {
        return new LinkedHashSet<>(membros);
    }

    public String getNome() {
        return nome;
    }

    public void adicionarMembro(String login) throws UsuarioJaFazParteDaComunidadeException {
        if (membros.contains(login)) {
            throw new UsuarioJaFazParteDaComunidadeException();
        }
        membros.add(login);
    }

    /**
     * Remove um membro da comunidade.
     * @param login Login do membro a ser removido
     */
    public void removerMembro(String login) {
        membros.remove(login);
    }
} 
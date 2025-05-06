package entities;

import exceptionsJackut.ComunidadeComEsseNomeJaExisteException;
import exceptionsJackut.ComunidadeNaoExisteException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SocialManager implements Serializable {
    private Map<String, Comunidade> comunidades;

    public SocialManager() {
        comunidades = new HashMap<>();
    }

    public void criarComunidade(String nome, String descricao, String dono) throws ComunidadeComEsseNomeJaExisteException {
        if (comunidades.containsKey(nome)) {
            throw new ComunidadeComEsseNomeJaExisteException();
        }
        comunidades.put(nome, new Comunidade(nome, descricao, dono));
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) throw new ComunidadeNaoExisteException();
        return comunidade.getDescricao();
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) throw new ComunidadeNaoExisteException();
        return comunidade.getDono();
    }

    public Set<String> getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getMembros();
    }

    public Comunidade getComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) throw new ComunidadeNaoExisteException();
        return comunidade;
    }

    /**
     * Remove uma comunidade do sistema.
     * @param nome Nome da comunidade a ser removida
     */
    public void removerComunidade(String nome) {
        comunidades.remove(nome);
    }
} 
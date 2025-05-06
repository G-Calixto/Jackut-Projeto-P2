package entities;

import entities.factory.RelationshipFactoryProvider;
import entities.relationship.AbstractRelationship;
import exceptionsJackut.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Map;

/**
 * Classe que representa um usuário do sistema Jackut.
 * Armazena informações do perfil, amigos, convites pendentes e recados.
 * Implementa Serializable para permitir persistência dos dados.
 *
 */
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String senha;
    private String nome;
    private Map<String, String> atributos;
    private Map<String, AbstractRelationship> relationships;
    private Set<String> convitesPendentes;
    private Set<String> comunidades;
    private Queue<String> recados;
    private Queue<String> mensagens;
    private Set<String> paqueras;
    private Set<String> idolos;

    /**
     * Construtor da classe Users.
     * Inicializa os atributos básicos do usuário e as estruturas de dados necessárias.
     * @param login Login único do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     */
    public Users(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.atributos = new HashMap<>();
        this.relationships = new HashMap<>();
        this.convitesPendentes = new LinkedHashSet<>();
        this.comunidades = new LinkedHashSet<>();
        this.recados = new LinkedList<>();
        this.mensagens = new LinkedList<>();
        this.paqueras = new LinkedHashSet<>();
        this.idolos = new LinkedHashSet<>();
        initializeRelationships();
    }

    private void initializeRelationships() {
        relationships.put("amigo", RelationshipFactoryProvider.createRelationship("amigo"));
        relationships.put("fa", RelationshipFactoryProvider.createRelationship("fa"));
        relationships.put("inimigo", RelationshipFactoryProvider.createRelationship("inimigo"));
    }

    /**
     * Retorna o nome do usuário.
     * @return Nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o login do usuário.
     * @return Login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Retorna a senha do usuário.
     * @return Senha do usuário
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define ou atualiza um atributo do perfil do usuário.
     * @param atributo Nome do atributo
     * @param valor Valor do atributo
     */
    public void setAtributo(String atributo, String valor) {
        atributos.put(atributo, valor);
    }

    /**
     * Retorna o valor de um atributo do perfil.
     * @param atributo Nome do atributo
     * @return Valor do atributo, ou null se não existir
     */
    public String getAtributo(String atributo) {
        switch (atributo) {
            case "login":
                return login;
            case "senha":
                return senha;
            case "nome":
                return nome;
            default:
                return atributos.get(atributo);
        }
    }

    /**
     * Adiciona um convite de amizade pendente.
     * @param login Login do usuário que enviou o convite
     */
    public void adicionarConvite(String login) {
        convitesPendentes.add(login);
    }

    /**
     * Aceita um convite de amizade.
     * Remove o convite pendente e adiciona o usuário à lista de amigos.
     * @param login Login do usuário que enviou o convite
     */
    public void aceitarConvite(String login) {
        convitesPendentes.remove(login);
        try {
            relationships.get("amigo").adicionarRelacionamento(login);
        } catch (UsuarioJaAdicionadoException | UsuarioJaAdicionadoComoIdoloException | UsuarioJaAdicionadoComoInimigoException e) {
        }
    }

    /**
     * Rejeita um convite de amizade.
     * Remove o convite pendente sem adicionar à lista de amigos.
     * @param login Login do usuário que enviou o convite
     */
    public void rejeitarConvite(String login) {
        convitesPendentes.remove(login);
    }

    /**
     * Verifica se existe um convite pendente de um usuário.
     * @param login Login do usuário a verificar
     * @return true se existe convite pendente, false caso contrário
     */
    public boolean temConvitePendente(String login) {
        return convitesPendentes.contains(login);
    }

    /**
     * Verifica se um usuário é amigo.
     * @param login Login do usuário a verificar
     * @return true se é amigo, false caso contrário
     */
    public boolean ehAmigo(String login) {
        return relationships.get("amigo").temRelacionamento(login);
    }

    /**
     * Retorna uma cópia do conjunto de amigos do usuário.
     * @return Conjunto de logins dos amigos
     */
    public Set<String> getAmigos() {
        return relationships.get("amigo").getRelacionamentos();
    }

    /**
     * Retorna a lista de amigos formatada como string.
     * Formato: {amigo1,amigo2}
     * @return String formatada com a lista de amigos
     */
    public String getAmigosFormatado() {
        Set<String> amigos = getAmigos();
        if (amigos.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        for (String amigo : amigos) {
            sb.append(amigo).append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Adiciona um recado à fila de recados do usuário.
     * @param recado Texto do recado a ser adicionado
     */
    public void receberRecado(String recado) {
        recados.add(recado);
    }

    /**
     * Lê e remove o primeiro recado da fila.
     * @return O recado lido
     * @throws RecadoNaoEncontradoException Se não houver recados
     */
    public String lerRecado() throws RecadoNaoEncontradoException {
        if (!temRecados()) {
            throw new RecadoNaoEncontradoException();
        }
        return recados.poll();
    }

    /**
     * Verifica se existem recados não lidos.
     * @return true se existem recados, false caso contrário
     */
    public boolean temRecados() {
        return !recados.isEmpty();
    }

    public void adicionarComunidade(String nomeComunidade) {
        comunidades.add(nomeComunidade);
    }

    public Set<String> getComunidades() {
        return new LinkedHashSet<>(comunidades);
    }

    public void receberMensagem(String mensagem) {
        mensagens.add(mensagem);
    }

    public String lerMensagem() throws MensagemNaoEncontradaException {
        if (!temMensagens()) {
            throw new MensagemNaoEncontradaException();
        }
        return mensagens.poll();
    }

    public boolean temMensagens() {
        return !mensagens.isEmpty();
    }

    public void adicionarIdolo(String idolo) throws UsuarioJaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException {
        if (login.equals(idolo)) {
            throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        }
        if (idolos.contains(idolo)) {
            throw new UsuarioJaAdicionadoComoIdoloException();
        }
        idolos.add(idolo);
    }

    public void adicionarFa(String fa) {
        try {
            relationships.get("fa").adicionarRelacionamento(fa);
        } catch (UsuarioJaAdicionadoException | UsuarioJaAdicionadoComoIdoloException | UsuarioJaAdicionadoComoInimigoException e) {
        }
    }

    public boolean ehFa(String idolo) {
        return idolos.contains(idolo);
    }

    public Set<String> getFas() {
        return relationships.get("fa").getRelacionamentos();
    }

    public void adicionarPaquera(String paquera) throws UsuarioJaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        if (login.equals(paquera)) {
            throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();
        }
        if (paqueras.contains(paquera)) {
            throw new UsuarioJaAdicionadoComoPaqueraException();
        }
        paqueras.add(paquera);
    }

    public boolean ehPaquera(String paquera) {
        return paqueras.contains(paquera);
    }

    public Set<String> getPaqueras() {
        return new LinkedHashSet<>(paqueras);
    }


    public void adicionarInimigo(String inimigo) throws UsuarioJaAdicionadoComoInimigoException, UsuarioNaoPodeSerInimigoDeSiMesmoException {
        if (login.equals(inimigo)) {
            throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();
        }
        try {
            relationships.get("inimigo").adicionarRelacionamento(inimigo);
        } catch (UsuarioJaAdicionadoException | UsuarioJaAdicionadoComoIdoloException e) {

        }
    }

    public boolean ehInimigo(String inimigo) {
        return relationships.get("inimigo").temRelacionamento(inimigo);
    }

    /**
     * Remove um amigo da lista de amigos.
     * @param amigo Login do amigo a ser removido
     */
    public void removerAmigo(String amigo) {
        relationships.get("amigo").removerRelacionamento(amigo);
    }

    /**
     * Remove um fã da lista de fãs.
     * @param fa Login do fã a ser removido
     */
    public void removerFa(String fa) {
        relationships.get("fa").removerRelacionamento(fa);
    }

    /**
     * Remove uma paquera da lista de paqueras.
     * @param paquera Login da paquera a ser removida
     */
    public void removerPaquera(String paquera) {
        paqueras.remove(paquera);
    }

    /**
     * Remove um inimigo da lista de inimigos.
     * @param inimigo Login do inimigo a ser removido
     */
    public void removerInimigo(String inimigo) {
        relationships.get("inimigo").removerRelacionamento(inimigo);
    }

    /**
     * Remove uma comunidade da lista de comunidades do usuário.
     * @param nomeComunidade Nome da comunidade a ser removida
     */
    public void removerComunidade(String nomeComunidade) {
        comunidades.remove(nomeComunidade);

        mensagens.clear();
    }

    /**
     * Limpa a fila de mensagens do usuário.
     */
    public void limparMensagens() {
        mensagens.clear();
    }

    /**
     * Limpa a fila de recados do usuário.
     */
    public void limparRecados() {
        recados.clear();
    }
}

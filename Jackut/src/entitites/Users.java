package entitites;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Classe que representa um usuário do sistema Jackut.
 * Armazena informações do perfil, amigos, convites pendentes e recados.
 * Implementa Serializable para permitir persistência dos dados.
 *
 */
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> atributos;
    private Set<String> amigos;
    private Set<String> convitesPendentes;
    private Queue<String> recados;

    /**
     * Construtor da classe Users.
     * Inicializa os atributos básicos do usuário e as estruturas de dados necessárias.
     * @param login Login único do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     */
    public Users(String login, String senha, String nome) {
        atributos = new HashMap<>();
        atributos.put("login", login);
        atributos.put("senha", senha);
        atributos.put("nome", nome);
        
        amigos = new LinkedHashSet<>();
        convitesPendentes = new LinkedHashSet<>();
        recados = new LinkedList<>();
    }

    /**
     * Retorna o nome do usuário.
     * @return Nome do usuário
     */
    public String getNome() {
        return atributos.get("nome");
    }

    /**
     * Retorna o login do usuário.
     * @return Login do usuário
     */
    public String getLogin() {
        return atributos.get("login");
    }

    /**
     * Retorna a senha do usuário.
     * @return Senha do usuário
     */
    public String getSenha() {
        return atributos.get("senha");
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
        return atributos.get(atributo);
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
        amigos.add(login);
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
        return amigos.contains(login);
    }

    /**
     * Retorna uma cópia do conjunto de amigos do usuário.
     * @return Conjunto de logins dos amigos
     */
    public Set<String> getAmigos() {
        return new LinkedHashSet<>(amigos);
    }

    /**
     * Retorna a lista de amigos formatada como string.
     * Formato: {amigo1,amigo2}
     * @return String formatada com a lista de amigos
     */
    public String getAmigosFormatado() {
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
     * @return O recado lido, ou null se não houver recados
     */
    public String lerRecado() {
        return recados.poll();
    }

    /**
     * Verifica se existem recados não lidos.
     * @return true se existem recados, false caso contrário
     */
    public boolean temRecados() {
        return !recados.isEmpty();
    }


}

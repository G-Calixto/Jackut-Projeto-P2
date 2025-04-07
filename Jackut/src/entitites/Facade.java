package entitites;

import exceptionsJackut.*;
import utils.Persistencia;

/**
 * Classe que implementa a interface para os testes de aceitação do EasyAccept.
 * Encapsula as operações do sistema e gerencia a persistência dos dados.
 *
 */
public class Facade {
    private Systems sistema;

    /**
     * Construtor da classe Facade.
     * Carrega o sistema a partir do arquivo de persistência.
     */
    public Facade() {
        sistema = Persistencia.carregarSistema();
    }

    /**
     * Limpa todos os dados do sistema e do arquivo de persistência.
     */
    public void zerarSistema() {
        sistema.zerarSistema();
        Persistencia.limparDados();
    }

    /**
     * Cria um novo usuário no sistema.
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     * @throws Exception Se ocorrer algum erro na criação do usuário
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        try {
            sistema.criarUsuario(login, senha, nome);
            Persistencia.salvarSistema(sistema);
        } catch (ContaJaExisteException | LoginInvalidoException | SenhaInvalidaException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Abre uma sessão para um usuário.
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão criada
     * @throws Exception Se o login ou senha estiverem incorretos
     */
    public String abrirSessao(String login, String senha) throws Exception {
        try {
            return sistema.abrirSessao(login, senha);
        } catch (LoginOuSenhaInvalidoException e) {
            throw new Exception("Login ou senha inválidos.");
        }
    }

    /**
     * Retorna o valor de um atributo do perfil de um usuário.
     * @param login Login do usuário
     * @param atributo Nome do atributo
     * @return Valor do atributo
     * @throws Exception Se o usuário não existir ou o atributo não estiver preenchido
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        try {
            return sistema.getAtributoUsuario(login, atributo);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (AtributoNaoPreenchidoException e) {
            throw new Exception("Atributo não preenchido.");
        }
    }

    /**
     * Edita um atributo do perfil de um usuário.
     * @param id ID da sessão do usuário
     * @param atributo Nome do atributo
     * @param valor Novo valor do atributo
     * @throws Exception Se a sessão não existir
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        try {
            sistema.editarPerfil(id, atributo, valor);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Adiciona um amigo ou envia um convite de amizade.
     * @param idSessao ID da sessão do usuário que está adicionando
     * @param amigo Login do usuário a ser adicionado
     * @throws Exception Se ocorrer algum erro na adição do amigo
     */
    public void adicionarAmigo(String idSessao, String amigo) throws Exception {
        try {
            sistema.adicionarAmigo(idSessao, amigo);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (UsuarioJaAdicionadoException e) {
            throw new Exception("Usuário já está adicionado como amigo.");
        } catch (UsuarioJaAdicionadoComConvitePendenteException e) {
            throw new Exception("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        } catch (UsuarioNaoPodeAdicionarASiMesmoException e) {
            throw new Exception("Usuário não pode adicionar a si mesmo como amigo.");
        }
    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se são amigos, false caso contrário
     * @throws Exception Se algum dos usuários não existir
     */
    public boolean ehAmigo(String login, String amigo) throws Exception {
        try {
            return sistema.ehAmigo(login, amigo);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Retorna a lista de amigos de um usuário formatada.
     * @param login Login do usuário
     * @return String formatada com a lista de amigos
     * @throws Exception Se o usuário não existir
     */
    public String getAmigos(String login) throws Exception {
        try {
            return sistema.getAmigos(login);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Envia um recado de um usuário para outro.
     * @param id ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param mensagem Texto do recado
     * @throws Exception Se ocorrer algum erro no envio do recado
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws Exception {
        try {
            sistema.enviarRecado(id, destinatario, mensagem);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (UsuarioNaoPodeEnviarRecadoParaSiMesmoException e) {
            throw new Exception("Usuário não pode enviar recado para si mesmo.");
        }
    }

    /**
     * Lê o próximo recado de um usuário.
     * @param id ID da sessão do usuário
     * @return Texto do recado
     * @throws Exception Se não houver recados ou a sessão não existir
     */
    public String lerRecado(String id) throws Exception {
        try {
            String recado = sistema.lerRecado(id);
            Persistencia.salvarSistema(sistema);
            return recado;
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (RecadoNaoEncontradoException e) {
            throw new Exception("Não há recados.");
        }
    }

    /**
     * Salva o estado atual do sistema no arquivo de persistência.
     */
    public void encerrarSistema() {
        Persistencia.salvarSistema(sistema);
    }
}

package entities;

import exceptionsJackut.*;
import utils.Persistencia;

import java.util.Set;

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
     * Adiciona um ídolo para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando o ídolo
     * @param idolo Login do usuário a ser adicionado como ídolo
     * @throws Exception Se ocorrer algum erro na adição do ídolo
     */
    public void adicionarIdolo(String idSessao, String idolo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException, UsuarioInimigoException {
        try {
            sistema.adicionarIdolo(idSessao, idolo);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException | UsuarioJaAdicionadoComoIdoloException | UsuarioNaoPodeSerFaDeSiMesmoException | UsuarioInimigoException e) {
            throw e;
        }
    }

    /**
     * Verifica se um usuário é fã de outro.
     * @param login Login do usuário que pode ser fã
     * @param idolo Login do possível ídolo
     * @return true se o usuário é fã do ídolo, false caso contrário
     * @throws Exception Se algum dos usuários não existir
     */
    public boolean ehFa(String login, String idolo) throws Exception {
        try {
            return sistema.ehFa(login, idolo);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Retorna a lista de fãs de um usuário formatada.
     * @param login Login do usuário
     * @return String formatada com a lista de fãs
     * @throws Exception Se o usuário não existir
     */
    public String getFas(String login) throws Exception {
        try {
            return sistema.getFas(login);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Verifica se um usuário tem uma paquera.
     * @param idSessao ID da sessão do usuário
     * @param paquera Login do possível paquera
     * @return true se o usuário tem a paquera, false caso contrário
     * @throws Exception Se algum dos usuários não existir
     */
    public boolean ehPaquera(String idSessao, String paquera) throws Exception {
        try {
            return sistema.ehPaquera(idSessao, paquera);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Adiciona uma paquera para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando a paquera
     * @param paquera Login do usuário a ser adicionado como paquera
     * @throws Exception Se ocorrer algum erro na adição da paquera
     */
    public void adicionarPaquera(String idSessao, String paquera) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException, UsuarioInimigoException {
        try {
            sistema.adicionarPaquera(idSessao, paquera);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException | UsuarioJaAdicionadoComoPaqueraException | UsuarioNaoPodeSerPaqueraDeSiMesmoException | UsuarioInimigoException e) {
            throw e;
        }
    }

    /**
     * Retorna a lista de paqueras de um usuário formatada.
     * @param idSessao ID da sessão do usuário
     * @return String formatada com a lista de paqueras
     * @throws Exception Se o usuário não existir
     */
    public String getPaqueras(String idSessao) throws Exception {
        try {
            return sistema.getPaqueras(idSessao);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    /**
     * Adiciona um inimigo para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando o inimigo
     * @param inimigo Login do usuário a ser adicionado como inimigo
     * @throws Exception Se ocorrer algum erro na adição do inimigo
     */
    public void adicionarInimigo(String idSessao, String inimigo) throws Exception {
        try {
            sistema.adicionarInimigo(idSessao, inimigo);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (UsuarioJaAdicionadoComoInimigoException e) {
            throw new Exception("Usuário já está adicionado como inimigo.");
        } catch (UsuarioNaoPodeSerInimigoDeSiMesmoException e) {
            throw new Exception("Usuário não pode ser inimigo de si mesmo.");
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
    public void adicionarAmigo(String idSessao, String amigo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoException, UsuarioJaAdicionadoComConvitePendenteException, UsuarioNaoPodeAdicionarASiMesmoException, UsuarioInimigoException {
        try {
            sistema.adicionarAmigo(idSessao, amigo);
        } catch (UsuarioNaoCadastradoException | UsuarioJaAdicionadoException | UsuarioJaAdicionadoComConvitePendenteException | UsuarioNaoPodeAdicionarASiMesmoException | UsuarioInimigoException e) {
            throw e;
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
     * @param idSessao ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param mensagem Texto do recado
     * @throws Exception Se ocorrer algum erro no envio do recado
     */
    public void enviarRecado(String idSessao, String destinatario, String mensagem) throws UsuarioNaoCadastradoException, UsuarioNaoPodeEnviarRecadoParaSiMesmoException, UsuarioInimigoException {
        try {
            sistema.enviarRecado(idSessao, destinatario, mensagem);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException | UsuarioNaoPodeEnviarRecadoParaSiMesmoException | UsuarioInimigoException e) {
            throw e;
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

    /**
     * Remove um usuário e todas as suas informações do sistema.
     * @param idSessao ID da sessão do usuário a ser removido
     * @throws Exception Se o usuário não existir
     */
    public void removerUsuario(String idSessao) throws Exception {
        try {
            sistema.removerUsuario(idSessao);
            Persistencia.salvarSistema(sistema);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }


    public void criarComunidade(String sessao, String nome, String descricao) throws Exception {
        try {
            sistema.criarComunidade(sessao, nome, descricao);
            Persistencia.salvarSistema(sistema);
        } catch (ComunidadeComEsseNomeJaExisteException e) {
            throw new Exception("Comunidade com esse nome já existe.");
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getDescricaoComunidade(String nome) throws Exception {
        try {
            return sistema.getDescricaoComunidade(nome);
        } catch (ComunidadeNaoExisteException e) {
            throw new Exception("Comunidade não existe.");
        }
    }

    public String getDonoComunidade(String nome) throws Exception {
        try {
            return sistema.getDonoComunidade(nome);
        } catch (ComunidadeNaoExisteException e) {
            throw new Exception("Comunidade não existe.");
        }
    }

    public String getMembrosComunidade(String nome) throws Exception {
        try {
            return sistema.getMembrosComunidade(nome);
        } catch (ComunidadeNaoExisteException e) {
            throw new Exception("Comunidade não existe.");
        }
    }

    public void adicionarComunidade(String sessao, String nome) throws Exception {
        try {
            sistema.adicionarMembroComunidade(sessao, nome);
            Persistencia.salvarSistema(sistema);
        } catch (ComunidadeNaoExisteException e) {
            throw new Exception("Comunidade não existe.");
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (UsuarioJaFazParteDaComunidadeException e) {
            throw new Exception("Usuario já faz parte dessa comunidade.");
        }
    }

    public String getComunidades(String login) throws Exception {
        try {
            return sistema.getComunidades(login);
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    public void enviarMensagem(String id, String comunidade, String mensagem) throws Exception {
        try {
            sistema.enviarMensagem(id, comunidade, mensagem);
            Persistencia.salvarSistema(sistema);
        } catch (ComunidadeNaoExisteException e) {
            throw new Exception("Comunidade não existe.");
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        }
    }

    public String lerMensagem(String id) throws Exception {
        try {
            String mensagem = sistema.lerMensagem(id);
            Persistencia.salvarSistema(sistema);
            return mensagem;
        } catch (UsuarioNaoCadastradoException e) {
            throw new Exception("Usuário não cadastrado.");
        } catch (MensagemNaoEncontradaException e) {
            throw new Exception("Não há mensagens.");
        }
    }
}

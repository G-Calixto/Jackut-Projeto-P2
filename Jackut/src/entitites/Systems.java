package entitites;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import exceptionsJackut.*;

/**
 * Classe que gerencia o sistema Jackut.
 * Responsável por controlar usuários, sessões, amizades e mensagens.
 * Implementa Serializable para permitir persistência dos dados.
 *
 */
public class Systems implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Users> usuarios;
    private Map<String, String> sessoes;

    /**
     * Construtor da classe Systems.
     * Inicializa as estruturas de dados necessárias.
     */
    public Systems() {
        usuarios = new HashMap<>();
        sessoes = new HashMap<>();
    }

    /**
     * Cria um novo usuário no sistema.
     * @param login Login único do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     * @throws ContaJaExisteException Se já existe um usuário com o login fornecido
     * @throws LoginInvalidoException Se o login for inválido (vazio ou nulo)
     * @throws SenhaInvalidaException Se a senha for inválida (vazia ou nula)
     */
    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException, LoginInvalidoException, SenhaInvalidaException {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
        if (usuarios.containsKey(login)) {
            throw new ContaJaExisteException();
        }
        usuarios.put(login, new Users(login, senha, nome));
    }

    /**
     * Retorna um usuário pelo login.
     * @param login Login do usuário
     * @return Objeto Users correspondente ao login
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     */
    public Users getUsuario(String login) throws UsuarioNaoCadastradoException {
        Users usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario;
    }

    /**
     * Abre uma sessão para um usuário.
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão criada
     * @throws LoginOuSenhaInvalidoException Se o login ou senha estiverem incorretos
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        try {
            Users usuario = getUsuario(login);
            if (!usuario.getSenha().equals(senha)) {
                throw new LoginOuSenhaInvalidoException();
            }
            String idSessao = gerarIdSessao();
            sessoes.put(idSessao, login);
            return idSessao;
        } catch (UsuarioNaoCadastradoException e) {
            throw new LoginOuSenhaInvalidoException();
        }
    }

    /**
     * Gera um ID único para uma nova sessão.
     * @return ID da sessão gerado
     */
    private String gerarIdSessao() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * Retorna o usuário associado a uma sessão.
     * @param idSessao ID da sessão
     * @return Objeto Users correspondente à sessão
     * @throws UsuarioNaoCadastradoException Se a sessão não existir
     */
    public Users getUsuarioPorSessao(String idSessao) throws UsuarioNaoCadastradoException {
        String login = sessoes.get(idSessao);
        if (login == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return getUsuario(login);
    }

    /**
     * Edita um atributo do perfil de um usuário.
     * @param idSessao ID da sessão do usuário
     * @param atributo Nome do atributo
     * @param valor Novo valor do atributo
     * @throws UsuarioNaoCadastradoException Se a sessão não existir
     */
    public void editarPerfil(String idSessao, String atributo, String valor) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        usuario.setAtributo(atributo, valor);
    }

    /**
     * Retorna o valor de um atributo do perfil de um usuário.
     * @param login Login do usuário
     * @param atributo Nome do atributo
     * @return Valor do atributo
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     * @throws AtributoNaoPreenchidoException Se o atributo não estiver preenchido
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Users usuario = getUsuario(login);
        String valor = usuario.getAtributo(atributo);
        if (valor == null) {
            throw new AtributoNaoPreenchidoException();
        }
        return valor;
    }

    /**
     * Adiciona um amigo ou envia um convite de amizade.
     * Se o amigo já enviou um convite, a amizade é estabelecida automaticamente.
     * Caso contrário, um convite é enviado.
     * @param idSessao ID da sessão do usuário que está adicionando
     * @param amigo Login do usuário a ser adicionado
     * @throws UsuarioNaoCadastradoException Se o usuário não existe
     * @throws UsuarioJaAdicionadoException Se já são amigos
     * @throws UsuarioJaAdicionadoComConvitePendenteException Se já existe convite pendente
     * @throws UsuarioNaoPodeAdicionarASiMesmoException Se tentar adicionar a si mesmo
     */
    public void adicionarAmigo(String idSessao, String amigo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoException, UsuarioJaAdicionadoComConvitePendenteException, UsuarioNaoPodeAdicionarASiMesmoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Users usuarioAmigo = getUsuario(amigo);

        if (usuario.getLogin().equals(amigo)) {
            throw new UsuarioNaoPodeAdicionarASiMesmoException();
        }

        if (usuario.ehAmigo(amigo)) {
            throw new UsuarioJaAdicionadoException();
        }

        if (usuario.temConvitePendente(amigo)) {
            usuario.aceitarConvite(amigo);
            usuarioAmigo.aceitarConvite(usuario.getLogin());
            return;
        }

        if (usuarioAmigo.temConvitePendente(usuario.getLogin())) {
            throw new UsuarioJaAdicionadoComConvitePendenteException();
        }

        usuarioAmigo.adicionarConvite(usuario.getLogin());
    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se são amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     */
    public boolean ehAmigo(String login, String amigo) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuario(login);
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário formatada.
     * @param login Login do usuário
     * @return String formatada com a lista de amigos no formato {amigo1,amigo2}
     * @throws UsuarioNaoCadastradoException Se o usuário não existe
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuario(login);
        return usuario.getAmigosFormatado();
    }

    /**
     * Envia um recado de um usuário para outro.
     * @param idSessao ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param mensagem Texto do recado
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se tentar enviar recado para si mesmo
     */
    public void enviarRecado(String idSessao, String destinatario, String mensagem) throws UsuarioNaoCadastradoException, UsuarioNaoPodeEnviarRecadoParaSiMesmoException {
        Users remetente = getUsuarioPorSessao(idSessao);
        Users usuarioDestinatario = getUsuario(destinatario);

        if (remetente.getLogin().equals(destinatario)) {
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        }

        usuarioDestinatario.receberRecado(mensagem);
    }

    /**
     * Lê o próximo recado de um usuário.
     * @param idSessao ID da sessão do usuário
     * @return Texto do recado
     * @throws UsuarioNaoCadastradoException Se a sessão não existir
     * @throws RecadoNaoEncontradoException Se não houver recados
     */
    public String lerRecado(String idSessao) throws UsuarioNaoCadastradoException, RecadoNaoEncontradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        if (!usuario.temRecados()) {
            throw new RecadoNaoEncontradoException();
        }
        return usuario.lerRecado();
    }

    /**
     * Limpa todos os dados do sistema.
     */
    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
    }
}

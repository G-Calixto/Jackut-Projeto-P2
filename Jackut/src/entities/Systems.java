package entities;
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
import java.util.LinkedHashSet;

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
    private SocialManager socialManager;

    /**
     * Construtor da classe Systems.
     * Inicializa as estruturas de dados necessárias.
     */
    public Systems() {
        usuarios = new HashMap<>();
        sessoes = new HashMap<>();
        socialManager = new SocialManager();
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
     * @throws UsuarioInimigoException Se o usuário é inimigo
     */
    public void adicionarAmigo(String idSessao, String amigo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoException, UsuarioJaAdicionadoComConvitePendenteException, UsuarioNaoPodeAdicionarASiMesmoException, UsuarioInimigoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Users usuarioAmigo = getUsuario(amigo);

        verificarInteracao(idSessao, amigo);

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
     * @throws UsuarioInimigoException Se o usuário é inimigo
     */
    public void enviarRecado(String idSessao, String destinatario, String mensagem) throws UsuarioNaoCadastradoException, UsuarioNaoPodeEnviarRecadoParaSiMesmoException, UsuarioInimigoException {
        Users remetente = getUsuarioPorSessao(idSessao);
        Users usuarioDestinatario = getUsuario(destinatario);

        verificarInteracao(idSessao, destinatario);

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
            throw new RecadoNaoEncontradoException("Não há recados.");
        }
        return usuario.lerRecado();
    }

    /**
     * Limpa todos os dados do sistema.
     */
    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
        socialManager = new SocialManager();
    }

    /**
     * Adiciona um ídolo para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando o ídolo
     * @param idolo Login do usuário a ser adicionado como ídolo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     * @throws UsuarioJaAdicionadoComoIdoloException Se o usuário já é ídolo
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException Se tentar adicionar a si mesmo como ídolo
     * @throws UsuarioInimigoException Se o usuário é inimigo
     */
    public void adicionarIdolo(String idSessao, String idolo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException, UsuarioInimigoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Users usuarioIdolo = getUsuario(idolo);
        
        verificarInteracao(idSessao, idolo);
        
        if (usuario.getLogin().equals(idolo)) {
            throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        }
        
        usuario.adicionarIdolo(idolo);
        usuarioIdolo.adicionarFa(usuario.getLogin());
    }

    /**
     * Verifica se um usuário é fã de outro.
     * @param login Login do usuário que pode ser fã
     * @param idolo Login do possível ídolo
     * @return true se o usuário é fã do ídolo, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     */
    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuario(login);
        return usuario.ehFa(idolo);
    }

    /**
     * Retorna a lista de fãs de um usuário formatada.
     * @param login Login do usuário
     * @return String formatada com a lista de fãs no formato {fa1,fa2}
     * @throws UsuarioNaoCadastradoException Se o usuário não existe
     */
    public String getFas(String login) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuario(login);
        Set<String> fas = usuario.getFas();
        if (fas.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", fas) + "}";
    }

    /**
     * Verifica se um usuário tem uma paquera.
     * @param idSessao ID da sessão do usuário
     * @param paquera Login do possível paquera
     * @return true se o usuário tem a paquera, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     */
    public boolean ehPaquera(String idSessao, String paquera) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        return usuario.ehPaquera(paquera);
    }

    /**
     * Adiciona uma paquera para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando a paquera
     * @param paquera Login do usuário a ser adicionado como paquera
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     * @throws UsuarioJaAdicionadoComoPaqueraException Se o usuário já é paquera
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException Se tentar adicionar a si mesmo como paquera
     * @throws UsuarioInimigoException Se o usuário é inimigo
     */
    public void adicionarPaquera(String idSessao, String paquera) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException, UsuarioInimigoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Users usuarioPaquera = getUsuario(paquera);
        
        verificarInteracao(idSessao, paquera);
        
        usuario.adicionarPaquera(paquera);
        

        if (usuarioPaquera.ehPaquera(usuario.getLogin())) {

            usuario.receberRecado(usuarioPaquera.getNome() + " é seu paquera - Recado do Jackut.");

            usuarioPaquera.receberRecado(usuario.getNome() + " é seu paquera - Recado do Jackut.");
        }
    }

    /**
     * Retorna a lista de paqueras de um usuário formatada.
     * @param idSessao ID da sessão do usuário
     * @return String formatada com a lista de paqueras no formato {paquera1,paquera2}
     * @throws UsuarioNaoCadastradoException Se o usuário não existe
     */
    public String getPaqueras(String idSessao) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Set<String> paqueras = usuario.getPaqueras();
        if (paqueras.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", paqueras) + "}";
    }

    /**
     * Adiciona um inimigo para um usuário.
     * @param idSessao ID da sessão do usuário que está adicionando o inimigo
     * @param inimigo Login do usuário a ser adicionado como inimigo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     * @throws UsuarioJaAdicionadoComoInimigoException Se o usuário já é inimigo
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException Se tentar adicionar a si mesmo como inimigo
     */
    public void adicionarInimigo(String idSessao, String inimigo) throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoInimigoException, UsuarioNaoPodeSerInimigoDeSiMesmoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        getUsuario(inimigo);
        usuario.adicionarInimigo(inimigo);
    }

    /**
     * Verifica se um usuário é inimigo de outro.
     * @param idSessao ID da sessão do usuário
     * @param inimigo Login do possível inimigo
     * @return true se o usuário é inimigo, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     */
    public boolean ehInimigo(String idSessao, String inimigo) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        return usuario.ehInimigo(inimigo);
    }

    /**
     * Verifica se um usuário pode interagir com outro (não é inimigo).
     * @param idSessao ID da sessão do usuário
     * @param outro Login do outro usuário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existe
     * @throws UsuarioInimigoException Se o outro usuário é inimigo
     */
    public void verificarInteracao(String idSessao, String outro) throws UsuarioNaoCadastradoException, UsuarioInimigoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        Users outroUsuario = getUsuario(outro);
        
        if (usuario.ehInimigo(outro) || outroUsuario.ehInimigo(usuario.getLogin())) {
            throw new UsuarioInimigoException(outroUsuario.getNome() + " é seu inimigo.");
        }
    }


    public void criarComunidade(String sessao, String nome, String descricao) throws ComunidadeComEsseNomeJaExisteException, UsuarioNaoCadastradoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da comunidade não pode ser vazio");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da comunidade não pode ser vazia");
        }
        Users usuario = getUsuarioPorSessao(sessao);
        socialManager.criarComunidade(nome, descricao, usuario.getLogin());
        usuario.adicionarComunidade(nome);
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return socialManager.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return socialManager.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        Set<String> membros = socialManager.getMembrosComunidade(nome);
        if (membros.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", membros) + "}";
    }

    public String getMembrosComunidadeFormatado(String nome) throws ComunidadeNaoExisteException {
        return getMembrosComunidade(nome);
    }

    public void adicionarMembroComunidade(String sessao, String nomeComunidade) throws ComunidadeNaoExisteException, UsuarioNaoCadastradoException, UsuarioJaFazParteDaComunidadeException {
        Users usuario = getUsuarioPorSessao(sessao);
        Comunidade comunidade = socialManager.getComunidade(nomeComunidade);
        comunidade.adicionarMembro(usuario.getLogin());
        usuario.adicionarComunidade(nomeComunidade);
    }

    public String getComunidades(String login) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuario(login);
        Set<String> comunidades = usuario.getComunidades();
        if (comunidades.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", comunidades) + "}";
    }

    public void enviarMensagem(String idSessao, String nomeComunidade, String mensagem) throws ComunidadeNaoExisteException, UsuarioNaoCadastradoException {
        Users remetente = getUsuarioPorSessao(idSessao);
        Comunidade comunidade = socialManager.getComunidade(nomeComunidade);
        

        for (String loginMembro : comunidade.getMembros()) {
            Users membro = getUsuario(loginMembro);
            membro.receberMensagem(mensagem);
        }
    }

    public String lerMensagem(String idSessao) throws UsuarioNaoCadastradoException, MensagemNaoEncontradaException {
        Users usuario = getUsuarioPorSessao(idSessao);
        return usuario.lerMensagem();
    }

    /**
     * Remove um usuário e todas as suas informações do sistema.
     * @param idSessao ID da sessão do usuário a ser removido
     * @throws UsuarioNaoCadastradoException Se o usuário não existe
     */
    public void removerUsuario(String idSessao) throws UsuarioNaoCadastradoException {
        Users usuario = getUsuarioPorSessao(idSessao);
        String login = usuario.getLogin();


        Set<String> comunidadesDoUsuario = new LinkedHashSet<>(usuario.getComunidades());
        for (String comunidade : comunidadesDoUsuario) {
            try {
                Comunidade com = socialManager.getComunidade(comunidade);
                com.removerMembro(login);
                

                if (com.getDono().equals(login)) {
                    socialManager.removerComunidade(comunidade);
                    for (Users outroUsuario : usuarios.values()) {
                        outroUsuario.removerComunidade(comunidade);
                    }
                }
            } catch (ComunidadeNaoExisteException e) {

            }
        }


        for (Users outroUsuario : usuarios.values()) {
            if (outroUsuario.ehAmigo(login)) {
                outroUsuario.removerAmigo(login);
            }
            if (outroUsuario.ehFa(login)) {
                outroUsuario.removerFa(login);
            }
            if (outroUsuario.ehPaquera(login)) {
                outroUsuario.removerPaquera(login);
            }
            if (outroUsuario.ehInimigo(login)) {
                outroUsuario.removerInimigo(login);
            }

            outroUsuario.limparMensagens();
            outroUsuario.limparRecados();
        }


        sessoes.remove(idSessao);


        usuarios.remove(login);
    }
}

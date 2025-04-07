package utils;
import java.io.*;
import entitites.*;

public class Persistencia {
    private static final String ARQUIVO_SISTEMA = "data/sistema.dat";

    public static void salvarObjeto(Users usuario, String nomeArquivo) {
        try (FileOutputStream fos = new FileOutputStream(nomeArquivo, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(usuario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Users carregarObjeto(String nomeArquivo, String login) {
        try (FileInputStream fis = new FileInputStream(nomeArquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Users usuario = (Users) ois.readObject();
                if (usuario.getLogin().equalsIgnoreCase(login)) {
                    return usuario;
                }
            }

        } catch (EOFException e) {
            System.out.println("Usuário não encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void salvarSistema(Systems sistema) {
        try {
            File diretorio = new File("data");
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(ARQUIVO_SISTEMA);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(sistema);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o sistema: " + e.getMessage());
        }
    }

    public static Systems carregarSistema() {
        try {
            File arquivo = new File(ARQUIVO_SISTEMA);
            if (!arquivo.exists()) {
                return new Systems();
            }

            try (FileInputStream fis = new FileInputStream(ARQUIVO_SISTEMA);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (Systems) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar o sistema: " + e.getMessage());
            return new Systems();
        }
    }

    public static void limparDados() {
        File arquivo = new File(ARQUIVO_SISTEMA);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}

import easyaccept.EasyAccept;
import entitites.Facade;

public class Main {
    public static void main(String[] args) {
        String[][] testes = {
                {"entitites.Facade", "Jackut/tests/us1_1.txt"},
                {"entitites.Facade", "Jackut/tests/us1_2.txt"},
                {"entitites.Facade", "Jackut/tests/us2_1.txt"},
                {"entitites.Facade", "Jackut/tests/us2_2.txt"},
                {"entitites.Facade", "Jackut/tests/us3_1.txt"},
                {"entitites.Facade", "Jackut/tests/us3_2.txt"},
                {"entitites.Facade", "Jackut/tests/us4_1.txt"},
                {"entitites.Facade", "Jackut/tests/us4_2.txt"}
        };

        for (String[] teste : testes) {
            System.out.println("\nExecutando teste: " + teste[1]);
            EasyAccept.main(teste);
        }
    }
}
import java.util.Scanner;

import visao.MenuAtores;
import visao.MenuEpisodios;
import visao.MenuSeries;

public class Principal {

    public static void main(String[] args) {
        Scanner console;
        try {

            console = new Scanner(System.in);
            int opcao;
            do {

                System.out.println("PUCFlix 2.0");
                System.out.println( "-----------");
                System.out.println("> Início");
                System.out.println("1 - Series");
                System.out.println("2 - Episodios");
                System.out.println("3 - Atores");
                System.out.println("9 - Povoar");
                System.out.println("0 - Sair");
                
                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch(NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        (new MenuSeries()).menu();
                        break;
                    case 2:
                        (new MenuEpisodios()).menu();
                        break;
                    case 3:
                        (new MenuAtores()).menu();
                        break;
                    case 9:
                        (new MenuSeries()).povoar();
                        (new MenuEpisodios()).povoar();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

package visao;

import entidades.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import modelo.ArquivoAtor;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;


public class MenuAtores {

    ArquivoAtor arqAtores = new ArquivoAtor();
    ArquivoSeries arqSeries = new ArquivoSeries();
    private static Scanner console = new Scanner(System.in);

    public MenuAtores() throws Exception {
        arqSeries = new ArquivoSeries();
        arqAtores = new ArquivoAtor();
    }

  public void menu() throws Exception {
    int opcao;
    do {
        System.out.println("\n\nPUCFlix 2.0");
        System.out.println("-----------");
        System.out.println("> Início > Atores");
        System.out.println("1) Buscar");
        System.out.println("2) Alterar");
        System.out.println("3) Excluir");
        System.out.println("4) Mostrar todos os atores de uma série");
        System.out.println("5) Mostrar todos as series de um ator");
        System.out.println("0) Retornar ao menu anterior");

        System.out.print("\nOpção: ");
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }

        switch (opcao) {
            case 1:
                buscarAtor();
                break;
            case 2:
                alterarAtor();
                break;
            case 3:
                excluirAtor();
                break;
            case 4:
                mostrarAtoresDeSerie();
                break;
            case 5:
                mostrarSeriesDeAtor();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    } while (opcao != 0);
}


    public void buscarAtor() {
        System.out.println("\nBusca de Atores: \n");
        boolean dadosCorretos = false;
        System.out.println();
        do{
            try {
                console.nextLine(); // Limpar buffer
                System.out.print("\nDigite o nome do ator: ");
                String nomeAtor = console.nextLine();
                Ator[] atores = arqAtores.readNome(nomeAtor);
                if(atores != null && atores.length > 0){
                    for (Ator at : atores) {
                        mostraAtor(at);
                        dadosCorretos = true;
                    }
                }else{
                    System.out.println("Nenhum ator encontrado.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar o ator: " + e.getMessage());
            }

        }while(!dadosCorretos);
    }

    //Atualizar Ator pelo nome
    public void alterarAtor() throws Exception {
        System.out.println("\nAlteração de Atores");

        System.out.print("Nome de ator: ");
        String nome = console.nextLine();
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            Ator[] atores = arqAtores.readNome(nome);
            if (atores != null) {
                
                for (int i=0; i < atores.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraAtor(atores[i]);
                }

                System.out.print("Digite o número do ator a ser atualizado: ");
                int num = console.nextInt();
                console.nextLine();

                //testar se o numero digitado e' valido
                if (num >= 0 && atores[num] != null) {

                    //------------- Dados a serem atualizados ----------------//
                    System.out.print("Novo nome (ou Enter para manter): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) {
                        atores[num].setNome(novoNome);
                    }

                    do {
                        System.out.print("Nova data de nascimento (DD/MM/AAAA) (ou Enter para manter): ");
                        String novaData = console.nextLine();
                        if (novaData.isEmpty()) {
                            break;
                        }
                        try {
                            atores[num].setDataNasc(LocalDate.parse(novaData, formatter));
                            break;
                        } catch (Exception e) {
                            System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
                        }
                    } while (true);
                    

                    System.out.print("Alterar nacionalidade (ou Enter para manter): ");
                    String novaSinopse = console.nextLine();
                    if (!novaSinopse.isEmpty()) {
                        atores[num].setNacionalidade(novaSinopse);
                    }

                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        boolean alterado = arqAtores.update(atores[num]);
                        if (alterado) {
                            System.out.println("Ator alterada com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar a Ator.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Não há Ator associada a esse número.");
                }
            } else {
                System.out.println("Ator não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar Ator.");
        }
    }

    //Excluir Ator pelo nome
    public void excluirAtor() throws Exception {
        System.out.println("\nExclusão de Ator");
        
        System.out.print("Nome da Ator: ");
        String nome = console.nextLine();
        System.out.println();

        try {
            Ator[] atores = arqAtores.readNome(nome);
            if (atores != null && atores.length > 0) {
                for (int i=0; i < atores.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraAtor(atores[i]);
                }

                System.out.print("Digite o número do atores a ser excluído: ");
                int num = console.nextInt();
                console.nextLine();
                if(num < 0 || num > atores.length || atores[num] == null){
                    System.err.println("Número inválido!");
                    return;
                }

                System.out.print("Tem certeza que deseja excluir esse ator? (S/N) ");
                char resposta = console.nextLine().charAt(0);
                if (resposta != 'S' && resposta != 's') {
                    System.out.println("O ator não foi excluída.");
                    return;
                } 

                boolean excluido = arqAtores.delete(atores[num].getID());
                if (excluido) {
                    System.out.println("ator excluída com sucesso.");
                } else {
                    System.out.println("Erro ao ator.");
                }

            } else {
                System.out.println("Ator não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir ator.");
        }
    }


    public void mostrarAtoresDeSerie(){
        System.out.println("\nBusca de atores de uma série:");
        System.out.print("De qual série deseja buscar os atores? (Nome da série): ");
        
        String nomeSerieVinculada = console.nextLine();
        System.out.println();
        boolean dadosCorretos = false;
        
        do {
            try {
                Serie[] series = arqSeries.readNome(nomeSerieVinculada);
                
                if (series != null && series.length > 0) {
                    System.out.println("Séries encontradas:");
                    for (int i = 0; i < series.length; i++) {
                        System.out.print("[" + i + "] ");
                        mostraSerie(series[i]);
                    }
                    
                    System.out.print("\nDigite o número da série escolhida: ");
                    if (console.hasNextInt()) {
                        int num = console.nextInt();
                        console.nextLine(); // Limpar buffer
                        
                        if (num < 0 || num >= series.length || series[num] == null) {
                            System.err.println("Número inválido!");
                        } else {
                            System.out.println("Atores da série " + series[num].getNome() + ":");
                            Ator[] atores = arqAtores.readAtoresSerie(series[num].getID());
                            
                            if (episodios != null && episodios.length > 0) {
                                int temporadaAtual = -1;
                                for (Episodio ep : episodios) {
                                    if (ep.getTemporada() != temporadaAtual) {
                                        temporadaAtual = ep.getTemporada();
                                        System.out.println("\nTemporada " + temporadaAtual + ":");
                                    }
                                    menuEpisodio.mostraEpisodio(ep);
                                }
                            } else {
                                System.out.println("Nenhum episódio encontrado para esta série.");
                            }
                            dadosCorretos = true;
                        }
                    } else {
                        System.err.println("Entrada inválida! Digite um número válido.");
                        console.nextLine(); // Limpar buffer
                    }
                } else {
                    System.out.println("Nenhuma série encontrada com esse nome.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar a série: " + e.getMessage());
                dadosCorretos = true;
            }
        } while (!dadosCorretos);
    }




    //Mostrar Ator
    public void mostraAtor(Ator ator) {
        if (ator != null) {
            System.out.println("----------------------");
            System.out.printf("Nome....: %s%n", ator.getNome());
            System.out.printf("Data de nascimento: %s%n", ator.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Nacionalidade....: %s%n", ator.getNacionalidade());
            System.out.println("----------------------");
        }
    }

    //Mostrar Série
    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("----------------------");
            System.out.printf("Nome....: %s%n", serie.getNome());
            System.out.printf("Ano lançamento: %d%n", serie.getAnoLancamento().getYear());
            System.out.printf("Sinopse....: %s%n", serie.getSinopse());
            System.out.printf("Streaming.....: %s%n", serie.getStreaming());
            System.out.printf("Gênero.....: %s%n", serie.getGenero());
            System.out.printf("Classificação Indicativa.....: %s%n", serie.getClassIndicativa());
            System.out.println("----------------------");
        }
    }

}

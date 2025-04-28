
package visao;

import entidades.Episodio;
import entidades.Serie;
import java.time.LocalDate;
import java.util.Scanner;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;

public class MenuSeries {
    ArquivoSeries arqSeries = new ArquivoSeries();
    ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
    private static Scanner console = new Scanner(System.in);
    MenuEpisodios menuEpisodio = new MenuEpisodios(); 

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSeries();
        arqEpisodios = new ArquivoEpisodios();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\n\nPUCFlix 2.0");
            System.out.println("-----------");
            System.out.println("> Início > Séries");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("5) Mostrar todos os episódios de uma série");
            System.out.println("0) Retornar ao menu anterior");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirSerie();
                    break;
                case 2:
                    buscarSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                    case 5:
                    mostrarEpSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirSerie() {
        System.out.println("\nInclusão de Série");

        String nome, sinopse, streaming, genero, classind;
        LocalDate ano = null;
        int anoAtual = LocalDate.now().getYear();

        // Nome
        do {
            System.out.print("Nome da série (min. 4 letras): ");
            nome = console.nextLine();
        } while (nome.length() < 4);

        // Gênero
        do {
            System.out.print("Gênero da série (min. 4 letras): ");
            genero = console.nextLine();
        } while (genero.length() < 4);

        // Classificação indicativa
        do {
            System.out.print("Classificação indicativa da série (min. 2 letras): ");
            classind = console.nextLine();
        } while (classind.length() < 2);

        // Ano de lançamento
        boolean anoValido = false;
        do {
            System.out.print("Ano de lançamento (entre 1900 e " + anoAtual + "): ");
            try {
                int anoDigitado = Integer.parseInt(console.nextLine());
                if (anoDigitado >= 1900 && anoDigitado <= anoAtual) {
                    ano = LocalDate.of(anoDigitado, 1, 1);
                    anoValido = true;
                } else {
                    System.err.println("Ano inválido! Insira um ano entre 1900 e " + anoAtual + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Ano inválido! Insira um valor numérico.");
            }
        } while (!anoValido);

        // Sinopse
        do {
            System.out.print("Sinopse (min. 10 letras): ");
            sinopse = console.nextLine();
        } while (sinopse.length() < 10);

        // Streaming
        do {
            System.out.print("Streaming (min. 3 letras): ");
            streaming = console.nextLine();
        } while (streaming.length() < 3);

        System.out.print("\nConfirma a inclusão da série? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Serie s = new Serie(nome, ano, sinopse, streaming, genero, classind);
                arqSeries.create(s);
                System.out.println("Série incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir série.");
            }
        }
    }

    //Buscar Série pelo nome
    public void buscarSerie() {
        System.out.println("\nBusca de Série");

        System.out.print("Nome da Série: ");
        String nome = console.nextLine();

        System.out.println();
        try {
            Serie[] series = arqSeries.readNome(nome);
            if (series != null && series.length > 0) {
                System.out.println("Séries encontradas:");
                for (Serie s : series) {
                    mostraSerie(s);
                }
            } else {
                System.out.println("Nenhuma série encontrada com esse nome.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao buscar série.");
        }
    }

    //Atualizar Série pelo nome
    public void alterarSerie() throws Exception {
        System.out.println("\nAlteração de Série");

        System.out.print("Nome da Série: ");
        String nome = console.nextLine();
        System.out.println();

        try {
            Serie[] serie = arqSeries.readNome(nome);
            if (serie != null) {
                
                for (int i=0; i < serie.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraSerie(serie[i]);
                }

                System.out.print("Digite o número da série a ser atualizada: ");
                int num = console.nextInt();
                console.nextLine();

                //testar se o numero digitado e' valido
                if (num >= 0 && serie[num] != null) {

                    //------------- Dados a serem atualizados ----------------//
                    System.out.print("Novo nome (ou Enter para manter): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) {
                        serie[num].setNome(novoNome);
                    }

                    System.out.print("Novo ano de lançamento (ou Enter para manter): ");
                    String ano = console.nextLine();
                    if (!ano.isEmpty()) {
                        LocalDate anoS = LocalDate.parse(ano + "-01-01"); // Apenas o ano
                        serie[num].setAnoLancamento(anoS);
                    }

                    System.out.print("Nova sinopse (ou Enter para manter): ");
                    String novaSinopse = console.nextLine();
                    if (!novaSinopse.isEmpty()) {
                        serie[num].setSinopse(novaSinopse);
                    }

                    System.out.print("Novo streaming (ou Enter para manter): ");
                    String novoStreaming = console.nextLine();
                    if (!novoStreaming.isEmpty()) {
                        serie[num].setStreaming(novoStreaming);
                    }

                    System.out.print("Novo genero (ou Enter para manter): ");
                    String novogenero = console.nextLine();
                    if (!novoStreaming.isEmpty()) {
                        serie[num].setGenero(novogenero);
                    }

                    System.out.print("Nova classificação indicada (ou Enter para manter): ");
                    String novoclassind = console.nextLine();
                    if (!novoclassind.isEmpty()) {
                        serie[num].setClassIndicativa(novoclassind);
                    }

                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        boolean alterado = arqSeries.update(serie[num]);
                        if (alterado) {
                            System.out.println("Série alterada com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar a série.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Não há serie associada a esse número.");
                }
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar série.");
        }
    }

    //Excluir Série pelo nome
    public void excluirSerie() throws Exception {
        System.out.println("\nExclusão de Série");
        
        System.out.print("Nome da Série: ");
        String nome = console.nextLine();
        System.out.println();

        try {
            Serie[] serie = arqSeries.readNome(nome);
            if (serie != null && serie.length > 0) {
                for (int i=0; i < serie.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraSerie(serie[i]);
                }

                System.out.print("Digite o número da série a ser excluída: ");
                int num = console.nextInt();
                console.nextLine();

                //testar se o numero digitado e' valido
                if (num >= 0 && serie[num] != null) {
                    Episodio[] episodios = arqEpisodios.readEpisodiosSerie(serie[num].getID());
                    if (episodios != null) {
                        System.out.print("Essa série possui episódios vinculados, deseja excluir mesmo assim? (S/N) ");
                    } else {
                        System.out.print("Tem certeza que deseja excluir essa série? (S/N) ");
                    }
                    char resposta = console.nextLine().charAt(0);
                    if (resposta != 'S' && resposta != 's') {
                        System.out.println("A série não foi excluída.");
                        return;
                    }
                    
                    boolean excluido = arqSeries.delete(serie[num].getID());
                    if (excluido) {
                        System.out.println("Série excluída com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir a série.");
                    }
                } else {
                    System.out.println("Não há serie associada a esse número.");
                }
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir série.");
        }
    }

    public void mostrarEpSerie() {
        System.out.println("\nBusca de episódio de uma série:");
        System.out.print("De qual série deseja buscar o episódio? (Nome da série): ");
        
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
                        
                        if (num < 0 || num >= series.length) {
                            System.err.println("Número inválido!");
                        } else {
                            System.out.println("Episódios da série " + series[num].getNome() + ":");
                            Episodio[] episodios = arqEpisodios.readEpisodiosSerie(series[num].getID());
                            
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
    //Mostrar Série
    public void mostraSerie(Serie serie) {
        try{
            if (serie != null) {
                System.out.println("----------------------");
                System.out.printf("Nome....: %s%n", serie.getNome());
                System.out.printf("Ano lançamento: %d%n", serie.getAnoLancamento().getYear());
                System.out.printf("Sinopse....: %s%n", serie.getSinopse());
                System.out.printf("Streaming.....: %s%n", serie.getStreaming());
                System.out.printf("Gênero.....: %s%n", serie.getGenero());
                System.out.printf("Avaliação Media......: %.2f%n", arqEpisodios.avaliacaoMediaSerie(serie.getID()));

                System.out.printf("Classificação Indicativa.....: %s%n", serie.getClassIndicativa());
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao mostrar série: " + e.getMessage());
        }
    }

    public void povoar() throws Exception {
            arqSeries.create(new Serie( "Breaking Bad", LocalDate.of(2008, 1, 20), "Um professor de química vira traficante de metanfetamina.", "Netflix", "Drama/Crime", "18+"));
            arqSeries.create(new Serie( "Stranger Things", LocalDate.of(2016, 7, 15), "Crianças descobrem segredos sobrenaturais em sua cidade.", "Netflix", "Ficção Científica/Terror", "14+"));
            arqSeries.create(new Serie( "Game of Thrones", LocalDate.of(2011, 4, 17), "Famílias nobres lutam pelo controle do trono de ferro.", "HBO Max", "Fantasia/Drama", "18+"));
            arqSeries.create(new Serie( "The Witcher", LocalDate.of(2019, 12, 20), "Geralt de Rívia enfrenta monstros e conflitos políticos.", "Netflix", "Fantasia/Ação", "16+"));
            arqSeries.create(new Serie( "Dark", LocalDate.of(2017, 12, 1), "Viagens no tempo revelam segredos sombrios de uma cidade.", "Netflix", "Ficção Científica/Suspense", "16+"));
            arqSeries.create(new Serie("The Boys", LocalDate.of(2019, 7, 26), "Super-heróis corruptos são combatidos por um grupo rebelde.", "Prime Video", "Ação/Drama", "18+"));
            arqSeries.create(new Serie( "Peaky Blinders", LocalDate.of(2013, 9, 12), "Gangue britânica liderada por Thomas Shelby.", "Netflix", "Crime/Drama", "16+"));
            arqSeries.create(new Serie( "The Mandalorian", LocalDate.of(2019, 11, 12), "Caçador de recompensas protege uma criança misteriosa.", "Disney+", "Ficção Científica/Aventura", "12+"));
            arqSeries.create(new Serie( "House of the Dragon", LocalDate.of(2022, 8, 21), "A guerra civil da família Targaryen pelo trono de ferro.", "HBO Max", "Fantasia/Drama", "18+"));
            arqSeries.create(new Serie( "Loki", LocalDate.of(2021, 6, 9), "O deus da trapaça embarca em viagens pelo multiverso.", "Disney+", "Ação/Ficção Científica", "12+"));

    }
}

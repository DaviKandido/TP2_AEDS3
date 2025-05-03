package visao;

import entidades.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import modelo.ArquivoAtor;
import modelo.ArquivoElenco;
import modelo.ArquivoSeries;


public class MenuAtores {

    ArquivoAtor arqAtores = new ArquivoAtor();
    ArquivoElenco arqElenco = new ArquivoElenco();
    
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
                mostrarAtoresDaSerie();
                break;
            case 5:
                mostrarSeriesDoAtores();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    } while (opcao != 0);
}


    public void incluirAtores(int idSerie) throws Exception {
        System.out.println("\nInclusão de Atores");
        System.out.println();
        boolean dadosCorretos = false;

        String nome = "", nacionalidade = "", papel = "";
        int tempoTela = 0, idserie = -1;


        LocalDate dataNasc = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Nome
        do {
            System.out.print("Nome do Ator (min. 2 letras): ");
            nome = console.nextLine();
        } while (nome.length() < 2);

        // Nacionalidade
        do {
            System.out.print("Nacionalidade do Ator (min. 2 letras): ");
            nacionalidade = console.nextLine();
        } while (nacionalidade.length() < 2);

        dadosCorretos = false;
        do {
            System.out.print("Data de nascimento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            try {
                dataNasc = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        // Papel
        do {
            System.out.print("Qual papel do Ator na Serie (min. 2 letras): ");
            nome = console.nextLine();
        } while (nome.length() < 2);

        // Tempo de tela
        dadosCorretos = false;
        do {
            System.out.print("Qual o tempo de tela do ator na serie em Minutos (0-999): ");
            if (console.hasNextInt()) {
                tempoTela = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);
        

        System.out.print("\nConfirma a inclusão do Ator na série? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Ator at = new Ator(nome, dataNasc, nacionalidade);
                int idAtor = arqAtores.create(at);
                Elenco elenco = new Elenco(papel, tempoTela, idserie, idAtor);
                arqElenco.create(elenco);
                System.out.println("Ator incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir Ator.");
            }
        }
    }

    public void buscarAtor() {
        System.out.println("\nBusca de Atores: ");
        boolean dadosCorretos = false;
        System.out.println();
        do{
            try {
                System.out.print("Digite o nome do ator: ");
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


    public void mostrarAtoresDaSerie(){
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
                            System.out.println("\nAtores da série " + series[num].getNome() + ":");
                            Ator[] atores = arqAtores.readAtoresDaSerie(series[num].getID());
                            
                            if (atores != null && atores.length > 0) {
                                for (Ator at : atores) {
                                    System.out.println();
                                    mostraAtor(at);

                                    Elenco[] elenco = arqElenco.read(at.getID(), series[num].getID());
                                    if (elenco != null && elenco.length > 0) {
                                        System.out.println("Fazendo o papel de: ");
                                        for(Elenco el : elenco) {
                                            mostraElenco(el);
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Nenhum ator encontrado para esta série.");
                            }
                            dadosCorretos = true;
                        }
                    } else {
                        System.err.println("Entrada inválida! Digite um número válido.");
                        console.nextLine(); // Limpar buffer
                    }
                } else {
                    System.out.println("Nenhum ator encontrada com esse nome.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar ator de uma série: " + e.getMessage());
                dadosCorretos = true;
            }
        } while (!dadosCorretos);
    }



    public void mostrarSeriesDoAtores(){
        System.out.println("\nBusca de Series de um ator:");
        System.out.print("De qual ator deseja buscar as Series? (Nome do ator): ");
        
        String nomeAtorVinculado = console.nextLine();
        System.out.println();
        boolean dadosCorretos = false;
        
        do {
            try {
                Ator[] atores = arqAtores.readNome(nomeAtorVinculado);
                
                if (atores != null && atores.length > 0) {
                    System.out.println("Séries encontradas:");
                    for (int i = 0; i < atores.length; i++) {
                        System.out.print("[" + i + "] ");
                        mostraAtor(atores[i]);
                    }
                    
                    System.out.print("\nDigite o número do ator escolhida: ");
                    if (console.hasNextInt()) {
                        int num = console.nextInt();
                        console.nextLine(); // Limpar buffer
                        
                        if (num < 0 || num >= atores.length || atores[num] == null) {
                            System.err.println("Número inválido!");
                        } else {
                            System.out.println("\nSeries do ator " + atores[num].getNome() + ":");
                            Serie[] series = arqAtores.readSerieDoAtor(atores[num].getID());
                            
                            if (series != null && series.length > 0) {
                                for (Serie se : series) {
                                    System.out.println();
                                    mostraSerie(se);

                                    Elenco[] elenco = arqElenco.read(atores[num].getID(), se.getID());
                                    if (elenco != null && elenco.length > 0) {
                                        System.out.println("Fazendo o papel de: ");
                                        for(Elenco el : elenco) {
                                            mostraElenco(el);
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Nenhum série encontrado para esta ator.");
                            }
                            dadosCorretos = true;
                        }
                    } else {
                        System.err.println("Entrada inválida! Digite um número válido.");
                        console.nextLine(); // Limpar buffer
                    }
                } else {
                    System.out.println("Nenhum série encontrada para esse ator.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar séries de um ator: " + e.getMessage());
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

    //Mostrar Papel
    public void mostraElenco(Elenco elenco) {
        if (elenco != null) {
            System.out.println("----------------------");
            System.out.printf("Papel.....: %s%n", elenco.getPapel());
            System.out.printf("Com um tempo de tela de: %d%n", elenco.getTempoTela());
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


    public void povoar() throws Exception {

        // Cadastrando atores
        int IdAtor1 = arqAtores.create(new Ator("Bryan Cranston", LocalDate.of(1956, 3, 7), "Americano"));
        int IdAtor2 = arqAtores.create(new Ator("Aaron Paul", LocalDate.of(1979, 8, 27), "Americano"));
        int IdAtor3 = arqAtores.create(new Ator("Millie Bobby Brown", LocalDate.of(2004, 2, 19), "Britânica"));
        int IdAtor4 = arqAtores.create(new Ator("David Harbour", LocalDate.of(1975, 4, 10), "Americano"));
        int IdAtor5 = arqAtores.create(new Ator("Emilia Clarke", LocalDate.of(1986, 10, 23), "Britânica"));
        int IdAtor6 = arqAtores.create(new Ator("Kit Harington", LocalDate.of(1986, 12, 26), "Britânico"));
        int IdAtor7 = arqAtores.create(new Ator("Henry Cavill", LocalDate.of(1983, 5, 5), "Britânico"));
        int IdAtor8 = arqAtores.create(new Ator("Anya Chalotra", LocalDate.of(1996, 7, 21), "Britânica"));
        int IdAtor9 = arqAtores.create(new Ator("Louis Hofmann", LocalDate.of(1997, 6, 3), "Alemão"));
        int IdAtor10 = arqAtores.create(new Ator("Lisa Vicari", LocalDate.of(1997, 2, 11), "Alemã"));
        int IdAtor11 = arqAtores.create(new Ator("Karl Urban", LocalDate.of(1972, 6, 7), "Neozelandês"));
        int IdAtor12 = arqAtores.create(new Ator("Jack Quaid", LocalDate.of(1992, 4, 24), "Americano"));
        int IdAtor13 = arqAtores.create(new Ator("Cillian Murphy", LocalDate.of(1976, 5, 25), "Irlandês"));
        int IdAtor14 = arqAtores.create(new Ator("Paul Anderson", LocalDate.of(1978, 11, 12), "Britânico"));
        int IdAtor15 = arqAtores.create(new Ator("Pedro Pascal", LocalDate.of(1975, 4, 2), "Chileno-Americano"));
        int IdAtor16 = arqAtores.create(new Ator("Emily Swallow", LocalDate.of(1979, 12, 18), "Americana"));
        int IdAtor17 = arqAtores.create(new Ator("Matt Smith", LocalDate.of(1982, 10, 28), "Britânico"));
        int IdAtor18 = arqAtores.create(new Ator("Emma D'Arcy", LocalDate.of(1992, 6, 27), "Britânica"));
        int IdAtor19 = arqAtores.create(new Ator("Tom Hiddleston", LocalDate.of(1981, 2, 9), "Britânico"));
        int IdAtor20 = arqAtores.create(new Ator("Sophia Di Martino", LocalDate.of(1983, 11, 15), "Britânica"));

        // Cadastrando elencos (referenciando apenas séries de ID 1 a 10)
        arqElenco.create(new Elenco("Walter White", 95, 1, IdAtor1));
        arqElenco.create(new Elenco("Jesse Pinkman", 92, 1, IdAtor2));

        arqElenco.create(new Elenco("Eleven", 88, 2, IdAtor3));
        arqElenco.create(new Elenco("Jim Hopper", 85, 2, IdAtor4));

        arqElenco.create(new Elenco("Daenerys Targaryen", 84, 3, IdAtor5));
        arqElenco.create(new Elenco("Jon Snow", 86, 3, IdAtor6));

        arqElenco.create(new Elenco("Geralt de Rívia", 91, 4, IdAtor7));
        arqElenco.create(new Elenco("Yennefer", 82, 4, IdAtor8));

        arqElenco.create(new Elenco("Jonas Kahnwald", 80, 5, IdAtor9));
        arqElenco.create(new Elenco("Martha Nielsen", 78, 5, IdAtor10));

        arqElenco.create(new Elenco("Billy Butcher", 86, 6, IdAtor11));
        arqElenco.create(new Elenco("Hughie Campbell", 83, 6, IdAtor12));

        arqElenco.create(new Elenco("Thomas Shelby", 93, 7, IdAtor13));
        arqElenco.create(new Elenco("Arthur Shelby", 81, 7, IdAtor14));

        arqElenco.create(new Elenco("Din Djarin (Mando)", 89, 8, IdAtor15));
        arqElenco.create(new Elenco("The Armorer", 75, 8, IdAtor16));

        arqElenco.create(new Elenco("Daemon Targaryen", 79, 9, IdAtor17));
        arqElenco.create(new Elenco("Rhaenyra Targaryen", 82, 9, IdAtor18));

        arqElenco.create(new Elenco("Loki", 92, 10, IdAtor19));
        arqElenco.create(new Elenco("Sylvie", 87, 10, IdAtor20));

    }
}

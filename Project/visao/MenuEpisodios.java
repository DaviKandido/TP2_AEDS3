package visao;

import entidades.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import modelo.*;

public class MenuEpisodios {
    
    ArquivoEpisodios arqEpisodios;
    ArquivoSeries arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodios() throws Exception {
        arqEpisodios = new ArquivoEpisodios();
        arqSeries = new ArquivoSeries();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n\nPUCFlix 2.0");
            System.out.println("-----------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("0) Retornar ao menu anterior");
            
            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episódio: \n");
        String nome = "";
        int temporada = 0;
        LocalDate dataLancamento = LocalDate.now();
        int duracaoMinutos = 0;
        float avaliacao = 0F;
        boolean especial = false;
        String descricao = "";
        int id_serie = 0;

        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



        dadosCorretos = false;
        do {
            System.out.print("Em que serie deseja incluir o episodio? (Nome da serie): ");
            String nomeSerieVinculada = console.nextLine();
            System.out.println();

                try{
                    Serie[] series = arqSeries.readNome(nomeSerieVinculada);
                    if (series != null && series.length > 0) {
                        System.out.println("Séries encontradas: ");
                        for (int i = 0; i < series.length; i++) {
                            System.out.println("\t[" + i + "]");
                            mostraSerie(series[i]);
                        }

                        System.out.print("\nDigite o número da série a ser vinculada: ");
                        if (console.hasNextInt()) {
                            int num = console.nextInt();
                            System.out.println();
                            if (num < 0 || num > series.length) {
                                System.err.println("Número inválido!");
                            } else {
                                id_serie = series[num].getID();
                                dadosCorretos = true;
                            }
                        } else {
                            System.err.println("Número inválido!");
                        }
                    }

                    
                }catch(Exception e){
                    System.err.println("Erro ao buscar a série: " + e.getMessage());
                }

            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Nome do episodio (min. de 4 letras): ");
            nome = console.nextLine();
            if(nome.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O Nome do episódio deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Temporada: ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            try {
                dataLancamento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        
        dadosCorretos = false;
        do {
            System.out.print("Duração em minutos (0-999): ");
            if (console.hasNextInt()) {
                duracaoMinutos = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Avaliação do episodio (0,0-10,0): ");
            if (console.hasNextFloat() || console.hasNextInt()) {
                avaliacao = console.nextFloat();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("O episodio é especial? (S/N): ");
            if(console.hasNext()){
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's' || resp == 'N' || resp == 'n') {
                    especial = (resp == 'S' || resp == 's');
                    dadosCorretos = true;
                }else{
                    System.err.println("Resposta inválida! Use S ou N.");
                }
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Descrição do espisodio (min. de 10 letras): ");
            descricao = console.nextLine();
            if(descricao.length()>=10)
                dadosCorretos = true;
            else
                System.err.println("A descricao do episódio deve ter no mínimo 10 caracteres.");
        } while(!dadosCorretos);



        System.out.print("\nConfirma a inclusão do episódio? (S/N): ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio e = new Episodio(nome, temporada, dataLancamento, duracaoMinutos, avaliacao, especial, descricao, id_serie);
                arqEpisodios.create(e);
                System.out.println("Episódio incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o episódio! " + e.getMessage());
            }
        }
    }

    public void buscarEpisodio() {
        System.out.println("\nBusca de episódio: \n");
        System.out.print("\nDe qual serie deseja buscar o episodio? (Nome da serie): ");
        boolean dadosCorretos = false;

        String nomeSerieVinculada = console.nextLine();
        System.out.println();

        do{
            try {
                Serie[] series = arqSeries.readNome(nomeSerieVinculada);
                if (series != null && series.length > 0) {
                    System.out.println("Series encontradas: ");
                    for(int i = 0; i < series.length; i++){
                        System.out.println("\t[" + i + "]");
                        mostraSerie(series[i]);
                    }
                        
                    System.out.print("\nDigite o número da série escolhida: ");
                    if (console.hasNextInt()) {
                        int num = console.nextInt();
                        if (num < 0 || num >= series.length) {
                            System.err.println("Número inválido!");
                        }else{
                            console.nextLine(); // Limpar buffer
                            System.out.print("\nDigite o nome do episódio: ");
                            String nomeEpisodio = console.nextLine();
                            Episodio[] episodios = arqEpisodios.readNomeEpisodioPorSerie(nomeEpisodio, series[num].getID());
                            if(episodios != null && episodios.length > 0){
                                for (Episodio ep : episodios) {
                                    mostraEpisodio(ep);
                                    dadosCorretos = true;
                                }
                            }else{
                                System.out.println("Nenhum episódio encontrado para esta série.");
                                dadosCorretos = true;
                            }
                        }
                    } else {
                        System.err.println("Número inválido!");
                        dadosCorretos = true;
                    }
                }else{
                    System.out.println("Nenhuma série encontrada com esse nome.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar a série: " + e.getMessage());
            }

        }while(!dadosCorretos);
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de episódio: \n");
        System.out.print("\nDe qual serie deseja excluir o episodio? (Nome da serie): ");
        String nomeSerieVinculada = console.nextLine();
        System.out.println();

        try {
            Serie[] series = arqSeries.readNome(nomeSerieVinculada);
            
            if(series != null && series.length > 0) {
                System.out.println("Séries encontradas: ");
                for(int i = 0; i < series.length; i++){
                    System.out.println("\t[" + i + "]");
                    mostraSerie(series[i]);
                }

                System.out.print("\nDigite o número da série escolhida: ");
                if(console.hasNextInt()){
                    int num = console.nextInt();
                    if(num < 0 || num > series.length) {
                        System.err.println("Número inválido!");
                    }else{
                        System.out.println("\nEpisódios da série " + series[num].getNome() + ": ");
                        Episodio[] episodio = arqEpisodios.readEpisodiosSerie(series[num].getID());

                        for (int i = 0; i < episodio.length; i++) {
                            System.out.println("\t[" + i + "]");
                            mostraEpisodio(episodio[i]);
                        }
                        System.out.print("\nDigite o número do episódio a ser excluído: ");
                        int num2 = console.nextInt();
                        if(num2 < 0 || num2 > episodio.length){
                            System.err.println("Número inválido!");
                        }else{
                            System.out.print("\nConfirma a exclusão do episódio? (S/N) ");
                            char resp = console.next().charAt(0);
                            console.nextLine(); // Limpar buffer
                            if (resp == 'S' || resp == 's') {
                                boolean excluido = arqEpisodios.delete(episodio[num2].getID());
                                if (excluido) {
                                    System.out.println("Episódio excluído com sucesso.");
                                } else {
                                    System.out.println("Erro ao excluir o episódio.");
                                }
                            } else {
                                System.out.println("Exclusão cancelada.");
                            }
                        }
                    }

                }else{
                    System.err.println("Número inválido!");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o episódio! " + e.getMessage());
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de episódio");
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String nomeSerieVinculada;
        do {
            System.out.print("Digite o nome da série em que deseja alterar o episódio: ");
            nomeSerieVinculada = console.nextLine();
        } while (nomeSerieVinculada.trim().isEmpty());
    
        try {
            Serie[] series = arqSeries.readNome(nomeSerieVinculada);
            if (series == null || series.length == 0) {
                System.err.println("Nenhuma série encontrada com esse nome.");
                return;
            }
            
            System.out.println("Séries encontradas: ");
            for (int i = 0; i < series.length; i++) {
                System.out.println("\t[" + i + "]");
                mostraSerie(series[i]);
            }
            
            int numSerie;
            do {
                System.out.print("\nDigite o número da série escolhida: ");
                while (!console.hasNextInt()) {
                    System.err.println("Número inválido!");
                    console.next();
                }
                numSerie = console.nextInt();
                console.nextLine();
            } while (numSerie < 0 || numSerie >= series.length);
            
            Episodio[] episodios = arqEpisodios.readEpisodiosSerie(series[numSerie].getID());
            if (episodios == null || episodios.length == 0) {
                System.err.println("Nenhum episódio encontrado para esta série.");
                return;
            }
            
            System.out.println("\nEpisódios da série " + series[numSerie].getNome() + ": ");
            for (int i = 0; i < episodios.length; i++) {
                System.out.println("\t[" + i + "]");
                mostraEpisodio(episodios[i]);
            }
            
            int numEpisodio;
            do {
                System.out.print("\nDigite o número do episódio a ser alterado: ");
                while (!console.hasNextInt()) {
                    System.err.println("Número inválido!");
                    console.next();
                }
                numEpisodio = console.nextInt();
                console.nextLine();
            } while (numEpisodio < 0 || numEpisodio >= episodios.length);
            
            Episodio episodioEncontrado = episodios[numEpisodio];
            System.out.println("Episódio encontrado: ");
            mostraEpisodio(episodioEncontrado);
            
            do {
                System.out.print("Novo nome do episodio (min. de 4 letras) (ou Enter para manter): ");
                String novoNome = console.nextLine();
                if (novoNome.isEmpty() || novoNome.length() >= 4) {
                    if (!novoNome.isEmpty()) {
                        episodioEncontrado.setNome(novoNome);
                    }
                    break;
                } else {
                    System.err.println("O Nome do episódio deve ter no mínimo 4 caracteres.");
                }
            } while (true);
            
            int novaTemporada = episodioEncontrado.getTemporada();
            do {
                System.out.print("Nova temporada (ou Enter para manter): ");
                String entrada = console.nextLine();
                if (entrada.isEmpty()) {
                    break;
                } else {
                    try {
                        novaTemporada = Integer.parseInt(entrada);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Número inválido!");
                    }
                }
            } while (true);
            
            do {
                System.out.print("Nova data de lançamento (DD/MM/AAAA) (ou Enter para manter): ");
                String novaData = console.nextLine();
                if (novaData.isEmpty()) {
                    break;
                }
                try {
                    episodioEncontrado.setDataLancamento(LocalDate.parse(novaData, formatter));
                    break;
                } catch (Exception e) {
                    System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
                }
            } while (true);
            
            do {
                System.out.print("Nova duração em minutos (0-999) (ou Enter para manter): ");
                String entrada = console.nextLine();
                if (entrada.isEmpty()) {
                    break;
                }
                try {
                    int novaDuracaoMinutos = Integer.parseInt(entrada);
                    episodioEncontrado.setDuracaoMinutos(novaDuracaoMinutos);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Número inválido!");
                }
            } while (true);
            
            do {
                System.out.print("Avaliação do episodio (0,0-10,0) (ou Enter para manter): ");
                String entrada = console.nextLine();
                if (entrada.isEmpty()) {
                    break;
                }
                try {
                    float novaAvaliacao = Float.parseFloat(entrada);
                    episodioEncontrado.setAvaliacao(novaAvaliacao);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Número inválido!");
                }
            } while (true);
            
            do {
                System.out.print("O episodio é especial? (S/N) (ou Enter para manter): ");
                String entrada = console.nextLine().trim();
                if (entrada.isEmpty()) {
                    break;
                }
                char resp = entrada.charAt(0);
                if (resp == 'S' || resp == 's' || resp == 'N' || resp == 'n') {
                    episodioEncontrado.setEspecial(resp == 'S' || resp == 's');
                    break;
                } else {
                    System.err.println("Resposta inválida! Use S ou N.");
                }
            } while (true);
            
            do {
                System.out.print("Descrição do episódio (min. de 10 letras) (ou Enter para manter): ");
                String novaDescricao = console.nextLine();
                if (novaDescricao.isEmpty() || novaDescricao.length() >= 10) {
                    if (!novaDescricao.isEmpty()) {
                        episodioEncontrado.setDescricao(novaDescricao);
                    }
                    break;
                } else {
                    System.err.println("A descrição do episódio deve ter no mínimo 10 caracteres.");
                }
            } while (true);
            
            System.out.print("\nConfirma as alterações? (S/N) ");
            char resp = console.next().charAt(0);
            console.nextLine();
            if (resp == 'S' || resp == 's') {
                boolean alterado = arqEpisodios.update(episodioEncontrado);
                if (alterado) {
                    System.out.println("Episódio alterado com sucesso.");
                } else {
                    System.out.println("Erro ao alterar o episódio.");
                }
            } else {
                System.out.println("Alterações canceladas.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o episódio! " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.printf("Nome.......: %s%n", episodio.getNome());
            System.out.printf("Lançamento.: %s%n", episodio.getDataLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Duração....: %d%n", episodio.getDuracaoMinutos());
            System.out.printf("Avaliação..: %s%n", episodio.getAvaliacao());
            System.out.printf("Especial...: %s%n", episodio.isEspecial() ? "Sim" : "Nao");
            System.out.printf("Descrição..: %s%n", episodio.getDescricao());
            System.out.printf("Serie......: %d%n", episodio.getID_serie());
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

        // Serie de id 1
        arqEpisodios.create(new Episodio( "Piloto",  1, LocalDate.of(2008, 1, 20), 58, 9.5f, false, "Walter White toma uma decisão que muda sua vida.", 1));
        arqEpisodios.create(new Episodio( "Gato Sai do Saco", (short) 1, LocalDate.of(2008, 1, 27), 48, 8.7f, false, "As consequências da primeira venda aparecem.", 1));

        // Serie de id 2
        arqEpisodios.create(new Episodio( "Desaparecimento de Will", 1, LocalDate.of(2016, 7, 15), 47, 8.9f, false, "Will desaparece misteriosamente.", 2));
        arqEpisodios.create(new Episodio( "Estranha da Maple Street", 1, LocalDate.of(2016, 7, 15), 55, 9.1f, false, "Mike e seus amigos encontram uma garota misteriosa.", 2));

        // Serie de id 3
        arqEpisodios.create(new Episodio( "Winter is Coming", 1, LocalDate.of(2011, 4, 17), 61, 9.0f, false, "Os Stark recebem o rei Robert Baratheon.", 3));

        // Serie de id 4
        arqEpisodios.create(new Episodio( "Princípio do Fim", 1, LocalDate.of(2019, 12, 20), 60, 8.5f, false, "Geralt enfrenta sua primeira batalha.", 4));
        arqEpisodios.create(new Episodio( "Quatro Marcas", 1, LocalDate.of(2019, 12, 20), 59, 8.7f, false, "Yennefer descobre seu verdadeiro poder.", 4));

        // Serie de id 5
        arqEpisodios.create(new Episodio( "A Chegada", 1, LocalDate.of(2019, 7, 26), 60, 9.0f, false, "Billy Butcher apresenta Hughie aos The Boys.", 5));

        // Serie de id 6
        arqEpisodios.create(new Episodio( "Segredos", 1, LocalDate.of(2017, 12, 1), 53, 8.9f, false, "Jonas começa a desvendar os mistérios de Winden.", 6));

        // Serie de id 7
        arqEpisodios.create(new Episodio( "Apostando Alto", 1, LocalDate.of(2013, 9, 12), 55, 9.2f, false, "Tommy Shelby expande seu império.", 7));

        // Serie de id 8
        arqEpisodios.create(new Episodio( "O Legado Targaryen", 1, LocalDate.of(2022, 8, 21), 66, 8.7f, false, "Rei Viserys enfrenta um dilema sucessório.", 8));

        // Serie de id 9
        arqEpisodios.create(new Episodio( "Renascimento", 1, LocalDate.of(2016, 10, 2), 57, 9.0f, false, "Os anfitriões começam a questionar sua realidade.", 9));
        
        // Serie de id 10
        arqEpisodios.create(new Episodio( "Volto Já", 1, LocalDate.of(2011, 12, 4), 44, 8.6f, false, "Uma mulher tenta reviver seu namorado morto usando IA.", 10));
    }
}

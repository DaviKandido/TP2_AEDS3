Etrutura:

	Entidade elenco:
  id_elenco
		id_ator
		id_serie
		papel
		tempoDeTela


	Entidade Ator:
		id
		nome
		dataNascimento
		nacionalidade




# TP1_AEDS3 💻

## 👨‍🎓 Alunos integrantes da equipe

* Davi Cândido de Almeida
* Letícia da Silva Rocha
* Rayssa Mell de Souza Silva

## 👨‍🏫 Professor responsável

* Marcos André Silveira Kutova
 
## 🎯 O que o trabalho faz?

Este projeto é uma aplicação desenvolvida em Java chamada PUCFlix 2.0, que simula um sistema de gerenciamento de séries e episódios. A aplicação permite realizar operações de cadastro, leitura, atualização e remoção (CRUD) de registros, utilizando arquivos binários para armazenar os dados de forma persistente. Para otimizar as buscas e garantir eficiência no acesso às informações, o sistema implementa estruturas de dados avançadas como Hash Extensível e Árvore B+, que são utilizadas para indexar os registros por diferentes chaves, como identificadores e nomes, o sistema possui também relacionamentos 1:N e N:N. O programa possui uma interface interativa por meio de menus no terminal, permitindo ao usuário navegar entre as opções de gerenciamento de séries e episódios, além de oferecer uma função para popular o sistema com dados de exemplo para testes.

## Classes criadas e os seus métodos principais.


```java
└── 📁Project
   └── 📁aeds3
   	└── Arquivo.java //(Métodos: read, create, delete, update, addDeleted, getDeleted)
   	└── ArvoreBMais.java //(Métodos: empty, read, create, delete, update, print)
   	└── Pagina.java //(Métodos: toByteArray, fromByteArray)
   	└── EntidadeArquivo.java //(Interface: setID, getID, toByteArray, fromByteArray)
   	└── HashExtensivel.java //(Métodos: toByteArray, fromByteArray, create, read, update, delete, empty, full, print, atualizaEndereco)
   	└── ParIDEndereco.java //(Métodos: getId, getEndereco, hashCode, size, toString, toByteArray, fromByteArray)
   	└── ParIdId.java //(Métodos: getId_agregado, clone, size, compareTo, toString, toByteArray, fromByteArray)
   	└── RegistroArvoreBMais.java  //(Métodos: size, toByteArray, fromByteArray, compareTo, clone)
   	└── RegistroHashExtensivel.java  //(Métodos: hashCode, size, toByteArray, fromByteArray)
   └── 📁dados
	└── 📁ator
            └── ator.db
            └── indiceAtor.db
            └── indiceDireito.c.db
            └── indiceDireito.d.db
   	└── 📁episodio
       	   └── episodio.db
       	   └── indiceDireito.c.db
       	   └── indiceDireito.d.db
       	   └── indiceIdEpisodios_IdSerie.db
       	   └── indiceNomeEpisodios.db
   	└── 📁series
           └── indiceDireito.c.db
       	   └── indiceDireito.d.db
       	   └── indiceNomeSerie.d.db
       	   └── series.db
   └── 📁entidades
	└── Ator.java // (Métodos: Ator, getID, setID, getNome, setNome, getDataNasc, setDataNasc, getNacionalidade, toByteArray, fromByteArray, toString, equals, compareTo)
   	└── Episodio.java //(Métodos: getID, setID, getID_serie, setID_serie, getNome, setNome, getTemporada, setTemporada, getDataLancamento, setDataLancamento, getDuracaoMinutos, setDuracaoMinutos, getAvaliacao, setAvaliacao, isEspecial, setEspecial, getDescricao, setDescricao, toByteArray, fromByteArray, toString, equals, compareTo)
   	└── Serie.java //(Métodos: getID, setID, getNome, setNome, getAnoLancamento, setAnoLancamento, getSinopse, setSinopse, getStreaming, setStreaming, getGenero, setGenero, getClassIndicativa, toByteArray, fromByteArray, toString, equals, compareTo)
   └── 📁modelo
	└── ArquivoAtor.java // (Métodos: ArquivoAtor, create, readNome, readAtoresDaSerie, readSerieDoAtor, delete, delete, update, atorExiste)
	└── ArquivoElenco.java //ArquivoElenco, readElencoPorAtor, read, readElencoPorSerie, delete, deleteAtorElenco, update)
   	└── ArquivoEpisodios.java //(Métodos: create, readNomeEpisodio, readNomeEpisodioPorSerie, readEpisodiosSerie, delete, deleteEpisodioSerie, update, avaliacaoMediaSerie)
   	└── ArquivoSeries.java //(Métodos: create, readNome, delete, update, serieExiste)
   	└── ParAtorId.java //(Métodos: ParAtorId, getNome, getId, clone, size, compareTo, toString, toByteArray, fromByteAray, transforma)
   	└── ParTituloId.java //(Métodos: getTitulo, getId, clone, size, compareTo, toString, toByteArray, fromByteArray, transforma)
   └── 📁visao
   	└── MenuEpisodios.java //(Métodos: menu, incluirEpisodio, buscarEpisodio, excluirEpisodio, alterarEpisodio, mostraEpisodio, mostraSerie, povoar)
        └── MenuAtores.java //(Métodos: MenuAtores, menu, incluirAtores, buscarAtor, excluirAtor, mostrarAtoresDaSerie, mostraAtor, mostraElenco, mostraSerie, povoar)
        └── MenuSeries.java //(Métodos: menu, incluirSerie, buscarSerie, alterarSerie, excluirSerie, mostrarEpSerie, mostraSerie, povoar)
   └── Principal.java //(Método: main)
```


## Relato de experiências:

```
Todos os requisitos foram implementados ?  
 - Sim

Houve alguma operação mais difícil? 
 - Sim, o gerenciamento dos índices do relacionamento 1:N.

Vocês enfrentaram algum desafio na implementação? 
 - Sim, como não poder testar até que tudo estivesse pronto, na exclusão de todos os episódios quando a série é excluída e no gerenciamento dos índices do relacionamento 1:N. 

Os resultados foram alcançados? 
 - Sim
```


### Checklist :

```
    As operações de inclusão, busca, alteração e exclusão de atores estão implementadas e funcionando corretamente?
    - Sim
    O relacionamento entre séries e atores foi implementado com árvores B+ e funciona corretamente, assegurando a consistência entre as duas entidades?
    - Sim
    É possível consultar quais são os atores de uma série?
    - Sim
    É posssível consultar quais são as séries de um ator?
    - Sim
    A remoção de séries remove os seus vínculos de atores?
    - Sim
    A inclusão de um ator em uma série em um episódio se limita aos atores existentes?
    - Sim
    A remoção de um ator checa se há alguma série vinculado a ele?
    - Sim
    O trabalho está funcionando corretamente?
    - Sim
    O trabalho está completo?
    - Sim
    O trabalho é original e não a cópia de um trabalho de outro grupo?
    - Sim


```



## 📄 Licença
Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.



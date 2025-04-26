# TP1_AEDS3 💻

## 👨‍🎓 Alunos integrantes da equipe

* Davi Cândido de Almeida
* Letícia da Silva Rocha
* Rayssa Mell de Souza Silva

## 👨‍🏫 Professor responsável

* Marcos André Silveira Kutova
 
## 🎯 O que o trabalho faz?

Este projeto é uma aplicação desenvolvida em Java chamada PUCFlix 2.0, que simula um sistema de gerenciamento de séries e episódios. A aplicação permite realizar operações de cadastro, leitura, atualização e remoção (CRUD) de registros, utilizando arquivos binários para armazenar os dados de forma persistente. Para otimizar as buscas e garantir eficiência no acesso às informações, o sistema implementa estruturas de dados avançadas como Hash Extensível e Árvore B+, que são utilizadas para indexar os registros por diferentes chaves, como identificadores e nomes. O programa possui uma interface interativa por meio de menus no terminal, permitindo ao usuário navegar entre as opções de gerenciamento de séries e episódios, além de oferecer uma função para popular o sistema com dados de exemplo para testes.

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
   	└── Episodio.java //(Métodos: getID, setID, getID_serie, setID_serie, getNome, setNome, getTemporada, setTemporada, getDataLancamento, setDataLancamento, getDuracaoMinutos, setDuracaoMinutos, getAvaliacao, setAvaliacao, isEspecial, setEspecial, getDescricao, setDescricao, toByteArray, fromByteArray, toString, equals, compareTo)
   	└── Serie.java //(Métodos: getID, setID, getNome, setNome, getAnoLancamento, setAnoLancamento, getSinopse, setSinopse, getStreaming, setStreaming, getGenero, setGenero, getClassIndicativa, toByteArray, fromByteArray, toString, equals, compareTo)
   └── 📁modelo
   	└── ArquivoEpisodios.java //(Métodos: create, readNomeEpisodio, readNomeEpisodioPorSerie, readEpisodiosSerie, delete, deleteEpisodioSerie, update, avaliacaoMediaSerie)
   	└── ArquivoSeries.java //(Métodos: create, readNome, delete, update, serieExiste)
   	└── ParNomeSerieId.java //(Métodos: getNome, getId, clone, size, compareTo, toString, toByteArray, fromByteArray, transforma)
   	└── ParTituloId.java //(Métodos: getTitulo, getId, clone, size, compareTo, toString, toByteArray, fromByteArray, transforma)
   └── 📁visao
   	└── MenuEpisodios.java //(Métodos: menu, incluirEpisodio, buscarEpisodio, excluirEpisodio, alterarEpisodio, mostraEpisodio, mostraSerie, povoar)
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
As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente?
 - Sim

As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente?
 - Sim

Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? 
 - Sim

O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios?
 - Sim

Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries?
 - Sim

Há uma visualização das séries que mostre os episódios por temporada?
 - Sim

A remoção de séries checa se há algum episódio vinculado a ela?
 - Sim

A inclusão da série em um episódio se limita às séries existentes?
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



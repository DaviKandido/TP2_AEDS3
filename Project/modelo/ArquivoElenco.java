package modelo;

import aeds3.*;
import entidades.Elenco;
<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/main
import java.util.ArrayList;

public class ArquivoElenco extends Arquivo<Elenco> {

  Arquivo<Elenco> arqElenco;
<<<<<<< HEAD
  ArvoreBMais<ParIdId> indiceIdAtor_IdElenco;
  ArvoreBMais<ParIdId> indiceIdSerie_IdElenco;
  ArvoreBMais<ParTituloId> indiceNomeAtor;

  private ArquivoAtor arqAtor;
=======
  ArvoreBMais<ParIdId> indiceIdAtor_IdSerie;
  ArvoreBMais<ParAtorId> indiceNomeAtor;

  private ArquivoAtor arqAtor; 
>>>>>>> refs/remotes/origin/main

  public ArquivoElenco() throws Exception {
    super("elenco", Elenco.class.getConstructor());
    arqAtor = new ArquivoAtor();

<<<<<<< HEAD
    //arvore b+ para o par ator, elenco
    indiceIdAtor_IdElenco = new ArvoreBMais<>(
        ParIdId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceIdAtor_IdElenco.db");

    //arvore b+ para o par serie, elenco
    indiceIdSerie_IdElenco = new ArvoreBMais<>(
        ParIdId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceIdSerie_IdElenco.db");
=======
    indiceIdAtor_IdSerie = new ArvoreBMais<>(
        ParIdId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceIdAtor_IdSerie.db");
>>>>>>> refs/remotes/origin/main
  }

  @Override
  public int create(Elenco e) throws Exception {

<<<<<<< HEAD
    // verificar se a serie existe
=======
    // Metodo para verificar se a serie existe
>>>>>>> refs/remotes/origin/main
    if (ArquivoSeries.serieExiste(e.getIdSerie()) == false) {
      throw new Exception("Elenco não pode ser criado pois a serie vinculada não existe");
    }

<<<<<<< HEAD
    // verificar se o ator existe
=======
    //verificar se o ator existe
>>>>>>> refs/remotes/origin/main
    if (arqAtor.atorExiste(e.getIdAtor()) == false) {
      throw new Exception("Elenco não pode ser criado pois esse ator não existe");
    }

    int id = super.create(e);

<<<<<<< HEAD
    indiceIdAtor_IdElenco.create(new ParIdId(e.getIdAtor(), id));
    indiceIdSerie_IdElenco.create(new ParIdId(e.getIdSerie(), id));
=======
    indiceIdAtor_IdSerie.create(new ParIdId(e.getIdSerie(), id));
>>>>>>> refs/remotes/origin/main

    return id;
  }

<<<<<<< HEAD
  // Metodo para buscar elenco pelo ator
  public Elenco[] readElencoPorAtor(String nome) throws Exception{
    if(nome.length() == 0)
      return null;

    ArrayList<ParTituloId> ptis = indiceNomeAtor.read(new ParTituloId(nome, -1));
    
    if(ptis.size() > 0){
      Elenco[] elenco = new Elenco[ptis.size()];
      int i = 0;

      for(ParTituloId pti: ptis)
        elenco[i++] = read(pti.getId());

      return elenco;
    }else
      return null;
  }

=======
  public Elenco[] readElenco(String nome) throws Exception {
    if (nome.length() == 0)
      return null;

    ArrayList<ParAtorId> ptis = indiceNomeAtor.read(new ParAtorId(nome, -1));
    if (ptis.size() > 0) {
      Elenco[] elenco = new Elenco[ptis.size()];
      int i = 0;
      for (ParAtorId pti : ptis)
        elenco[i++] = read(pti.getId());
      return elenco;

    } else
      return null;
  }

  public Elenco[] readElencoPorSerie(String nome, int id_serie) throws Exception {
    if (nome.length() == 0)
      return null;

    ArrayList<ParAtorId> ptis = indiceNomeAtor.read(new ParAtorId(nome, -1));
    if (ptis.size() > 0) {
      Elenco[] elenco = new Elenco[ptis.size()];
      int i = 0;
      for (ParAtorId pti : ptis)
        elenco[i++] = read(pti.getId());

      ArrayList<Elenco> elencoSerie = new ArrayList<>();

      // Verifica se o elenco pertence a serie
      for (Elenco e : elenco) {
        if (e.getIdSerie() == id_serie)
          elencoSerie.add(e);
      }

      return elencoSerie.toArray(new Elenco[elencoSerie.size()]);
    } else
      return null;
  }
>>>>>>> refs/remotes/origin/main

  @Override
  public boolean delete(int id) throws Exception {
    Elenco e = read(id);
    if (e != null) {
      if (super.delete(id))
<<<<<<< HEAD
        return indiceIdAtor_IdElenco.delete(new ParIdId(e.getIdAtor(), id))
            && indiceIdSerie_IdElenco.delete(new ParIdId(e.getIdSerie(), id))
            && indiceNomeAtor.delete(new ParTituloId(e.getNome(), id));

=======
        return indiceIdAtor_IdSerie.delete(new ParIdId(e.getIdSerie(), id))
            && indiceNomeAtor.delete(new ParAtorId(e.getNome(), id));
>>>>>>> refs/remotes/origin/main
    }
    return false;
  }

<<<<<<< HEAD
  public boolean deleteAtorElenco(int id_ator) throws Exception {

    // Metodo para verificar se a serie vinculada ao elenco existe
    ArrayList<ParIdId> pIds = indiceIdAtor_IdElenco.read(new ParIdId(id_ator, -1));

    System.out.println("Quantidade de elencos deletados: " + pIds.size());
=======
  public boolean deleteElencoSerie(int id_serie) throws Exception {

    // Metodo para verificar se a serie vinculada ao elenco existe
    ArrayList<ParIdId> pIds = indiceIdAtor_IdSerie.read(new ParIdId(id_serie, -1));

    System.out.println("Quantidade de atores da serie deletados: " + pIds.size());
>>>>>>> refs/remotes/origin/main

    if (pIds.size() > 0) {
      for (ParIdId pID : pIds)
        delete(pID.getId_agregado());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Elenco novoElenco) throws Exception {
    Elenco e = read(novoElenco.getID());
    if (e != null) {
      if (super.update(novoElenco)) {
<<<<<<< HEAD
        if (e.getIdAtor() != novoElenco.getIdAtor()) {
          indiceIdAtor_IdElenco.delete(new ParIdId(e.getIdAtor(), e.getID()));
          indiceIdAtor_IdElenco.create(new ParIdId(novoElenco.getIdAtor(), e.getID()));
=======
        if (e.getIdSerie() != novoElenco.getIdSerie()) {
          indiceIdAtor_IdSerie.delete(new ParIdId(e.getIdSerie(), e.getID()));
          indiceIdAtor_IdSerie.create(new ParIdId(novoElenco.getIdSerie(), e.getID()));
>>>>>>> refs/remotes/origin/main
        }

        return true;
      }
    }
    return false;
  }

<<<<<<< HEAD
}
=======
}
>>>>>>> refs/remotes/origin/main

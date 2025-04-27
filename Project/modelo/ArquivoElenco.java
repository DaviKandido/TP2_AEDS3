package modelo;

import aeds3.*;
import entidades.Elenco;
import java.util.ArrayList;

public class ArquivoElenco extends Arquivo<Elenco> {

  Arquivo<Elenco> arqElenco;
  ArvoreBMais<ParIdId> indiceIdAtor_IdSerie;
  ArvoreBMais<ParAtorId> indiceNomeAtor;

  private ArquivoAtor arqAtor; 

  public ArquivoElenco() throws Exception {
    super("elenco", Elenco.class.getConstructor());
    arqAtor = new ArquivoAtor();

    indiceIdAtor_IdSerie = new ArvoreBMais<>(
        ParIdId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceIdAtor_IdSerie.db");
  }

  @Override
  public int create(Elenco e) throws Exception {

    // Metodo para verificar se a serie existe
    if (ArquivoSeries.serieExiste(e.getIdSerie()) == false) {
      throw new Exception("Elenco não pode ser criado pois a serie vinculada não existe");
    }

    //verificar se o ator existe
    if (arqAtor.atorExiste(e.getIdAtor()) == false) {
      throw new Exception("Elenco não pode ser criado pois esse ator não existe");
    }

    int id = super.create(e);

    indiceIdAtor_IdSerie.create(new ParIdId(e.getIdSerie(), id));

    return id;
  }

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

  @Override
  public boolean delete(int id) throws Exception {
    Elenco e = read(id);
    if (e != null) {
      if (super.delete(id))
        return indiceIdAtor_IdSerie.delete(new ParIdId(e.getIdSerie(), id))
            && indiceNomeAtor.delete(new ParAtorId(e.getNome(), id));
    }
    return false;
  }

  public boolean deleteElencoSerie(int id_serie) throws Exception {

    // Metodo para verificar se a serie vinculada ao elenco existe
    ArrayList<ParIdId> pIds = indiceIdAtor_IdSerie.read(new ParIdId(id_serie, -1));

    System.out.println("Quantidade de atores da serie deletados: " + pIds.size());

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
        if (e.getIdSerie() != novoElenco.getIdSerie()) {
          indiceIdAtor_IdSerie.delete(new ParIdId(e.getIdSerie(), e.getID()));
          indiceIdAtor_IdSerie.create(new ParIdId(novoElenco.getIdSerie(), e.getID()));
        }

        return true;
      }
    }
    return false;
  }

}

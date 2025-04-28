package modelo;

import aeds3.*;
import entidades.Elenco;

import java.util.ArrayList;

public class ArquivoElenco extends Arquivo<Elenco> {

  Arquivo<Elenco> arqElenco;
  ArvoreBMais<ParIdId> indiceIdAtor_IdElenco;
  ArvoreBMais<ParIdId> indiceIdSerie_IdElenco;
  ArvoreBMais<ParTituloId> indiceNomeAtor;

  private ArquivoAtor arqAtor;

  public ArquivoElenco() throws Exception {
    super("elenco", Elenco.class.getConstructor());
    arqAtor = new ArquivoAtor();

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
  }

  @Override
  public int create(Elenco e) throws Exception {

    // verificar se a serie existe
    if (ArquivoSeries.serieExiste(e.getIdSerie()) == false) {
      throw new Exception("Elenco não pode ser criado pois a serie vinculada não existe");
    }

    // verificar se o ator existe
    if (arqAtor.atorExiste(e.getIdAtor()) == false) {
      throw new Exception("Elenco não pode ser criado pois esse ator não existe");
    }

    int id = super.create(e);

    indiceIdAtor_IdElenco.create(new ParIdId(e.getIdAtor(), id));
    indiceIdSerie_IdElenco.create(new ParIdId(e.getIdSerie(), id));

    return id;
  }

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


  @Override
  public boolean delete(int id) throws Exception {
    Elenco e = read(id);
    if (e != null) {
      if (super.delete(id))
        return indiceIdAtor_IdElenco.delete(new ParIdId(e.getIdAtor(), id))
            && indiceIdSerie_IdElenco.delete(new ParIdId(e.getIdSerie(), id))
            && indiceNomeAtor.delete(new ParTituloId(e.getNome(), id));

    }
    return false;
  }

  public boolean deleteAtorElenco(int id_ator) throws Exception {

    // Metodo para verificar se a serie vinculada ao elenco existe
    ArrayList<ParIdId> pIds = indiceIdAtor_IdElenco.read(new ParIdId(id_ator, -1));

    System.out.println("Quantidade de elencos deletados: " + pIds.size());

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
        if (e.getIdAtor() != novoElenco.getIdAtor()) {
          indiceIdAtor_IdElenco.delete(new ParIdId(e.getIdAtor(), e.getID()));
          indiceIdAtor_IdElenco.create(new ParIdId(novoElenco.getIdAtor(), e.getID()));
        }

        return true;
      }
    }
    return false;
  }

}
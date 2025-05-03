package modelo;

import aeds3.*;
import entidades.*;
import java.util.ArrayList;

public class ArquivoElenco extends Arquivo<Elenco> {

  Arquivo<Elenco> arqElenco;
  ArvoreBMais<ParIdId> indiceIdAtor_IdElenco;
  ArvoreBMais<ParIdId> indiceIdSerie_IdElenco;
  ArvoreBMais<ParTituloId> indiceNomeAtor;

  private ArquivoAtor arqAtor = new ArquivoAtor();
  private ArquivoSeries arqSerie = new ArquivoSeries();


  public ArquivoElenco() throws Exception {
    super("elenco", Elenco.class.getConstructor());

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
  public Elenco[] readElencoPorAtor(int id_ator) throws Exception{

    Ator ator = arqAtor.read(id_ator);
    if(ator == null)
      throw new Exception("Ator nao encontrado");

    ArrayList<ParIdId> ptis = indiceIdAtor_IdElenco.read(new ParIdId(id_ator, -1));
    
    if(ptis.size() > 0){
      Elenco[] elenco = new Elenco[ptis.size()];
      int i = 0;

      for(ParIdId pti: ptis)
        elenco[i++] = read(pti.getId_agregador());

      return elenco;
    }else
      return null;
  }


    // Metodo para buscar elenco pelo ator
    public Elenco[] read(int id_ator, int id_serie) throws Exception {
      Ator ator = arqAtor.read(id_ator);
      if (ator == null)
          throw new Exception("Ator não encontrado");
  
      Serie serie = arqSerie.read(id_serie);
      if (serie == null)
          throw new Exception("Série não encontrada");
  
      ArrayList<ParIdId> listaAtor = indiceIdAtor_IdElenco.read(new ParIdId(id_ator, -1));
      ArrayList<ParIdId> listaSerie = indiceIdSerie_IdElenco.read(new ParIdId(id_serie, -1));
  
      // Interseção: achar apenas os Elencos que estão em ambas as listas
      ArrayList<Elenco> elencosEncontrados = new ArrayList<>();
      for (ParIdId p1 : listaAtor) {
          for (ParIdId p2 : listaSerie) {
              if (p1.getId_agregado() == p2.getId_agregado()) {
                  elencosEncontrados.add(read(p1.getId_agregado()));
              }
          }
      }
  
      if (elencosEncontrados.size() > 0)
          return elencosEncontrados.toArray(new Elenco[0]);
      else
          return null;
  }

  public Elenco[] readElencoPorSerie(int id_serie) throws Exception{

    Serie serie = arqSerie.read(id_serie);
    if(serie == null)
      throw new Exception("serie nao encontrado");

    ArrayList<ParIdId> ptis = indiceIdSerie_IdElenco.read(new ParIdId(id_serie, -1));
    
    if(ptis.size() > 0){
      Elenco[] elenco = new Elenco[ptis.size()];
      int i = 0;

      for(ParIdId pti: ptis)
        elenco[i++] = read(pti.getId_agregado());

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
            && indiceIdSerie_IdElenco.delete(new ParIdId(e.getIdSerie(), id));
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
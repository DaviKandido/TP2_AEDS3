package modelo;

import entidades.Episodio;
import entidades.Serie;

import java.util.ArrayList;

import aeds3.*;

public class ArquivoSeries extends Arquivo<Serie> {

    Arquivo<Serie> arqSeries;
    ArvoreBMais<ParTituloId> indiceNomeSerie;
    ArvoreBMais<ParIdId> indices;

    public ArquivoSeries() throws Exception {
        super("series", Serie.class.getConstructor());

        indiceNomeSerie = new ArvoreBMais<>(
                ParTituloId.class.getConstructor(),
                5,
                "./dados/" + nomeEntidade + "/indiceNomeSerie.d.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNomeSerie.create(new ParTituloId(s.getNome(), id));
        return id;
    }

    public Serie[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        ArrayList<ParTituloId> ptis = indiceNomeSerie.read(new ParTituloId(nome, -1));
        if (ptis.size() > 0) {
            Serie[] series = new Serie[ptis.size()];
            int i = 0;

            for (ParTituloId pti : ptis)
                series[i++] = read(pti.getId());
            return series;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = read(id); // na superclasse
        if (s != null) {
            if (super.delete(id))
                return indiceNomeSerie.delete(new ParTituloId(s.getNome(), id));
        }
        return false;
    }

    public boolean delete(String nome, int id) throws Exception {
        ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();

        Serie s = read(id);
        // verificar se a serie existe
        if (s != null && s.getID() == id && s.getNome().equals(nome)) {

            System.out.println(s);

            // verificar episodios vinculados a serie
            Episodio[] ids = arqEpisodios.readEpisodiosSerie(id);

            if (ids != null) {
                // excluir todos os episodios
                boolean deletados = arqEpisodios.deleteEpisodioSerie(id);
                // deletar a serie
                if (deletados) {
                    return delete(id);
                }
            }

            return delete(id);

        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getID()); // na superclasse
        if (s != null) {
            if (super.update(novaSerie)) {
                if (!s.getNome().equals(novaSerie.getNome())) {
                    indiceNomeSerie.delete(new ParTituloId(s.getNome(), s.getID()));
                    indiceNomeSerie.create(new ParTituloId(novaSerie.getNome(), s.getID()));
                }
                return true;
            }
        }
        return false;
    }

    // metodo para testar se ha serie vinculada ao id buscado
    public static boolean serieExiste(int id) throws Exception {
        ArquivoSeries arqSeries = new ArquivoSeries();
        Serie s = arqSeries.read(id); // na superclasse
        if (s != null) {
            return true;
        }
        return false;
    }
}

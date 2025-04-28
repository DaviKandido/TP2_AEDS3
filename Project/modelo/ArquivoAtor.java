package modelo;

import aeds3.*;
import entidades.Ator;

import java.util.ArrayList;

public class ArquivoAtor extends Arquivo<Ator> {
    
    ArvoreBMais<ParTituloId> indiceNomeAtor;

    public ArquivoAtor() throws Exception {
        super("ator", Ator.class.getConstructor());

        indiceNomeAtor = new ArvoreBMais<>(
        ParTituloId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceAtor.db");
    }

    @Override
    public int create(Ator a) throws Exception {
        int id = super.create(a);
        indiceNomeAtor.create(new ParTituloId(a.getNome(), id));

        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        ArrayList<ParTituloId> ptis = indiceNomeAtor.read(new ParTituloId(nome, -1));
        if (ptis.size() > 0) {
            Ator[] atores = new Ator[ptis.size()];
            int i = 0;

            for (ParTituloId pti : ptis)
                atores[i++] = read(pti.getId());
            return atores;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Ator ator = read(id);
        if (ator != null) {
            if (super.delete(id))
                return indiceNomeAtor.delete(new ParTituloId(ator.getNome(), id));
        }
        return false;
    }

    public boolean delete(String nome, int id) throws Exception {
        // Verifica se o ator está vinculado a algum elenco
        ArquivoElenco arquivoElenco = new ArquivoElenco();
        ArrayList<ParIdId> elenco = arquivoElenco.indiceIdAtor_IdElenco.read(new ParIdId(id, -1));

        if (elenco != null && elenco.size() > 0) {
            throw new Exception("Não é possível excluir o ator. Ele está vinculado a uma ou mais séries.");
        }

        return super.delete(id) && indiceNomeAtor.delete(new ParTituloId(nome, id));
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator ator = read(novoAtor.getID());
        if (ator != null) {
            if (super.update(novoAtor)) {
                if (!ator.getNome().equals(novoAtor.getNome())) {
                    indiceNomeAtor.delete(new ParTituloId(ator.getNome(), ator.getID()));
                    indiceNomeAtor.create(new ParTituloId(novoAtor.getNome(), ator.getID()));
                }
                return true;
            }
        }
        return false;
    }

    public boolean atorExiste(int id_ator) throws Exception{
        Ator a = read(id_ator);   // na superclasse
        if(a != null) {
            return true;
        }
        return false;
    }
    
}

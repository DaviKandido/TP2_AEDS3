package modelo;

import aeds3.*;
import entidades.Ator;
import entidades.Elenco;

import java.util.ArrayList;

public class ArquivoAtor extends Arquivo<Ator> {
    
    ArvoreBMais<ParIdId> indiceIdAtor_IdSerie;
    ArvoreBMais<ParAtorId> indiceNomeAtor;

    public ArquivoAtor() throws Exception {
        super("ator", Ator.class.getConstructor());

        indiceNomeAtor = new ArvoreBMais<>(
        ParAtorId.class.getConstructor(),
        5,
        "./dados/" + nomeEntidade + "/indiceAtor.db");
    }

    @Override
    public int create(Ator a) throws Exception {
        int id = super.create(a);
        indiceNomeAtor.create(new ParAtorId(a.getNome(), id));

        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        ArrayList<ParAtorId> ptis = indiceNomeAtor.read(new ParAtorId(nome, -1));
        if (ptis.size() > 0) {
            Ator[] atores = new Ator[ptis.size()];
            int i = 0;

            for (ParAtorId pti : ptis)
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
                return indiceNomeAtor.delete(new ParAtorId(ator.getNome(), id));
        }
        return false;
    }

    public boolean delete(String nome, int id) throws Exception {
        // Verifica se o ator está vinculado a alguma série
        ArquivoElenco arquivoElenco = new ArquivoElenco();
        Elenco[] elenco = arquivoElenco.readElencoPorSerie(nome, id);

        if (elenco != null && elenco.length > 0) {
            throw new Exception("Não é possível excluir o ator. Ele está vinculado a uma ou mais séries.");
        }

        return super.delete(id) && indiceNomeAtor.delete(new ParAtorId(nome, id));
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator ator = read(novoAtor.getID());
        if (ator != null) {
            if (super.update(novoAtor)) {
                if (!ator.getNome().equals(novoAtor.getNome())) {
                    indiceNomeAtor.delete(new ParAtorId(ator.getNome(), ator.getID()));
                    indiceNomeAtor.create(new ParAtorId(novoAtor.getNome(), ator.getID()));
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

package modelo;

import aeds3.*;
import entidades.Ator;
<<<<<<< HEAD
=======
import entidades.Elenco;
>>>>>>> refs/remotes/origin/main

import java.util.ArrayList;

public class ArquivoAtor extends Arquivo<Ator> {
    
<<<<<<< HEAD
    ArvoreBMais<ParTituloId> indiceNomeAtor;
=======
    ArvoreBMais<ParIdId> indiceIdAtor_IdSerie;
    ArvoreBMais<ParAtorId> indiceNomeAtor;
>>>>>>> refs/remotes/origin/main

    public ArquivoAtor() throws Exception {
        super("ator", Ator.class.getConstructor());

        indiceNomeAtor = new ArvoreBMais<>(
<<<<<<< HEAD
        ParTituloId.class.getConstructor(),
=======
        ParAtorId.class.getConstructor(),
>>>>>>> refs/remotes/origin/main
        5,
        "./dados/" + nomeEntidade + "/indiceAtor.db");
    }

    @Override
    public int create(Ator a) throws Exception {
        int id = super.create(a);
<<<<<<< HEAD
        indiceNomeAtor.create(new ParTituloId(a.getNome(), id));
=======
        indiceNomeAtor.create(new ParAtorId(a.getNome(), id));
>>>>>>> refs/remotes/origin/main

        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

<<<<<<< HEAD
        ArrayList<ParTituloId> ptis = indiceNomeAtor.read(new ParTituloId(nome, -1));
=======
        ArrayList<ParAtorId> ptis = indiceNomeAtor.read(new ParAtorId(nome, -1));
>>>>>>> refs/remotes/origin/main
        if (ptis.size() > 0) {
            Ator[] atores = new Ator[ptis.size()];
            int i = 0;

<<<<<<< HEAD
            for (ParTituloId pti : ptis)
=======
            for (ParAtorId pti : ptis)
>>>>>>> refs/remotes/origin/main
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
<<<<<<< HEAD
                return indiceNomeAtor.delete(new ParTituloId(ator.getNome(), id));
=======
                return indiceNomeAtor.delete(new ParAtorId(ator.getNome(), id));
>>>>>>> refs/remotes/origin/main
        }
        return false;
    }

    public boolean delete(String nome, int id) throws Exception {
<<<<<<< HEAD
        // Verifica se o ator está vinculado a algum elenco
        ArquivoElenco arquivoElenco = new ArquivoElenco();
        ArrayList<ParIdId> elenco = arquivoElenco.indiceIdAtor_IdElenco.read(new ParIdId(id, -1));

        if (elenco != null && elenco.size() > 0) {
            throw new Exception("Não é possível excluir o ator. Ele está vinculado a uma ou mais séries.");
        }

        return super.delete(id) && indiceNomeAtor.delete(new ParTituloId(nome, id));
=======
        // Verifica se o ator está vinculado a alguma série
        ArquivoElenco arquivoElenco = new ArquivoElenco();
        Elenco[] elenco = arquivoElenco.readElencoPorSerie(nome, id);

        if (elenco != null && elenco.length > 0) {
            throw new Exception("Não é possível excluir o ator. Ele está vinculado a uma ou mais séries.");
        }

        return super.delete(id) && indiceNomeAtor.delete(new ParAtorId(nome, id));
>>>>>>> refs/remotes/origin/main
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator ator = read(novoAtor.getID());
        if (ator != null) {
            if (super.update(novoAtor)) {
                if (!ator.getNome().equals(novoAtor.getNome())) {
<<<<<<< HEAD
                    indiceNomeAtor.delete(new ParTituloId(ator.getNome(), ator.getID()));
                    indiceNomeAtor.create(new ParTituloId(novoAtor.getNome(), ator.getID()));
=======
                    indiceNomeAtor.delete(new ParAtorId(ator.getNome(), ator.getID()));
                    indiceNomeAtor.create(new ParAtorId(novoAtor.getNome(), ator.getID()));
>>>>>>> refs/remotes/origin/main
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

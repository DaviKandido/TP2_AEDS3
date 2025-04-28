package entidades;

import aeds3.EntidadeArquivo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Elenco implements EntidadeArquivo {

    private int id;  
    private String nome;
    private String papel;
    private int tempoTela;
    private int id_serie;    
    private int id_ator;

    public Elenco() throws Exception  {
        this(-1, "", "", -1, -1, -1);
    }

    public Elenco(String nome, String papel, int tempoTela, int id_serie, int id_ator) throws Exception {
        this(-1, nome, papel, tempoTela, id_serie, id_ator);
    }

    public Elenco(int id, String nome, String papel, int tempoTela, int id_serie, int id_ator) throws Exception {
        this.id = id;
        this.nome = nome;
        this.papel = papel;
        this.tempoTela = tempoTela;
        this.id_serie = id_serie;
        this.id_ator = id_ator;
    } 

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public int getTempoTela() {
        return tempoTela;
    }

    public void setTempoTela(int tempoTela) {
        this.tempoTela = tempoTela;
    }

    public int getIdSerie() {
        return id_serie;
    }

    public void setIdAtor(int id_ator) {
        this.id_ator = id_ator;
    }

    public int getIdAtor(){
        return id_ator;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(papel);
        dos.writeInt(tempoTela);
        dos.writeInt(id_serie);
        dos.writeInt(id_ator);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
    
        id = dis.readInt();
        nome = dis.readUTF();
        papel = dis.readUTF();
        tempoTela = dis.readInt();
        id_serie = dis.readInt();
        id_ator = dis.readInt();
    }

    public String toString(){
        return "Elenco = [Nome: " + nome +
                "\nPapel: " + papel +
                "\nTempo de Tela: " + tempoTela + 
                "\nid_serie: " + id_serie +
                "\nid_ator: " + id_ator + "]";
    }

    @Override
 	public boolean equals(Object obj){
 		return (this.getID() == ((Elenco) obj).getID());
 	}

     public int compareTo(Elenco elenco) {
        return Integer.compare(this.id, elenco.id);
    }
}

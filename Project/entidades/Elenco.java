package entidades;

import aeds3.EntidadeArquivo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

public class Ator implements EntidadeArquivo {

    private int id;  
    private String nome;
    private LocalDate dataNasc;
    private String nacionalidade;

    public Ator() throws Exception  {
        this(-1, "", LocalDate.now(), "");
    }

    public Ator(String nome, LocalDate dataNasc, String nacionalidade) throws Exception {
        this(-1, nome, dataNasc, nacionalidade);
    }

    public Ator(int id, String nome, LocalDate datasNasc, String nacionalidade) throws Exception {
        this.id = id;
        this.nome = nome;
        this.dataNasc = datasNasc;
        this.nacionalidade = nacionalidade;
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

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt((int)dataNasc.toEpochDay());
        dos.writeUTF(nacionalidade);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
    
        id = dis.readInt();
        nome = dis.readUTF();
        dataNasc = LocalDate.ofEpochDay(dis.readInt());
        nacionalidade = dis.readUTF();
    }

    public String toString(){
        return "Ator = [ID: " + id +
                "\nNome: " + nome +
                "\nData de Nascimento: " + dataNasc + 
                "\nNacionalidade: " + nacionalidade +
                "]";
    }

    @Override
 	public boolean equals(Object obj){
 		return (this.getID() == ((Elenco) obj).getID());
 	}

     public int compareTo(Ator ator) {
        return Integer.compare(this.id, ator.id);
    }
}
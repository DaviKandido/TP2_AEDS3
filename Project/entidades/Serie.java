package entidades;

import aeds3.EntidadeArquivo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

public class Serie implements EntidadeArquivo {

    private int id;
    private String nome;
    private LocalDate anoLancamento;
    private String sinopse;
    private String streaming;
    private String genero;
    private String classIndicativa;

    public Serie() throws Exception  {
        this("", LocalDate.now(),"","", "", "");
    }

    public Serie(String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, String classIndicativa) throws Exception {
        this(-1, nome, anoLancamento, sinopse, streaming, genero, classIndicativa);
    }

    public Serie(int id, String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, String classIndicativa) throws Exception {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
        this.genero = genero;
        this.classIndicativa = classIndicativa;

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

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClassIndicativa() {
        return classIndicativa;
    }

    public void setClassIndicativa(String classIndicativa) {
        this.classIndicativa = classIndicativa;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt((int)anoLancamento.toEpochDay());
        dos.writeUTF(sinopse);
        dos.writeUTF(streaming);
        dos.writeUTF(genero);
        dos.writeUTF(classIndicativa);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
    
        id = dis.readInt();
        nome = dis.readUTF();
        anoLancamento = LocalDate.ofEpochDay(dis.readInt());
        sinopse = dis.readUTF();
        streaming = dis.readUTF();
        genero = dis.readUTF();
        classIndicativa = dis.readUTF();
    }

    public String toString(){
        return "Série = [ID: " + id +
                "\nNome: " + nome +
                "\nAno de lançamento: " + anoLancamento +
                "\nSinopse " + sinopse + 
                "\nStreaming: " + streaming + 
                "\nGênero: " + genero + 
                "\nClassificação Indicativa: " + classIndicativa + "]";
    }

    @Override
 	public boolean equals(Object obj){
 		return (this.getID() == ((Serie) obj).getID());
 	}

    public int compareTo(Serie serie) {
        return Integer.compare(this.id, serie.id);
    }
}

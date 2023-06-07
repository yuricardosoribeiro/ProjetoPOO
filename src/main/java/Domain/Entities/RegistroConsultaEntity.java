package Domain.Entities;

import org.bson.Document;

public class RegistroConsultaEntity extends BaseEntity<RegistroConsultaEntity>{
    private int BatimentosCardiacos;
    private String Pressao;
    private double Temperatura;
    private String Diagnostico;
    private String Observacoes;

    public int getBatimentosCardiacos() {
        return BatimentosCardiacos;
    }

    public void setBatimentosCardiacos(int batimentosCardiacos) {
        BatimentosCardiacos = batimentosCardiacos;
    }

    public String getPressao() {
        return Pressao;
    }

    public void setPressao(String pressao) {
        Pressao = pressao;
    }

    public double getTemperatura() {
        return Temperatura;
    }

    public void setTemperatura(double temperatura) {
        Temperatura = temperatura;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        Diagnostico = diagnostico;
    }

    public String getObservacoes() {
        return Observacoes;
    }

    public void setObservacoes(String observacoes) {
        Observacoes = observacoes;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("BatimentosCardiacos", this.BatimentosCardiacos);
        doc.append("Pressao", this.Pressao);
        doc.append("Temperatura", this.Temperatura);
        doc.append("Diagnostico", this.Diagnostico);
        doc.append("Observacoes", this.Observacoes);

        return doc;
    }

    @Override
    public void ToClass(Document document) {
        this.BatimentosCardiacos = (int) document.get("BatimentosCardiacos");
        this.Pressao = (String) document.get("Pressao");
        this.Temperatura = (int) document.get("Temperatura");
        this.Diagnostico = (String) document.get("v");
        this.Observacoes = (String) document.get("Observacoes");
    }
}

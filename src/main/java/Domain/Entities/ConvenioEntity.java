package Domain.Entities;

import Domain.Exceptions.ValidationException;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ConvenioEntity extends BaseEntity<ConvenioEntity> {
    private String Nome;
    private double Subsidio;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) throws ValidationException {
        if(nome == "") {
            this.Nome = null;
            throw new ValidationException("O nome é obrigatório!");
        }
        else
            this.Nome = nome;
    }

    public double getSubsidio() {
        return Subsidio;
    }

    public void setSubsidio(double subsidio) throws ValidationException {
        if(subsidio < 0)
            throw new ValidationException("Subsídio não pode ser menor que zero!");

        this.Subsidio = subsidio;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Nome", this.getNome());
        doc.append("Subsidio", this.getSubsidio());

        if(this.Id != null)
            doc.append("_id", this.getId());

        return doc;
    }

    public void ToClass(Document document) {
        this.Id = (ObjectId) document.get("_id");
        this.Nome = (String) document.get("Nome");
        this.Subsidio = (double) document.get("Subsidio");
    }
}

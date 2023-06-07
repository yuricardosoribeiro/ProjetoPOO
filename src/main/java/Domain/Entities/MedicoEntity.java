package Domain.Entities;

import Domain.Exceptions.ValidationException;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MedicoEntity extends BaseEntity<MedicoEntity> {
    private String Nome;
    private String CRM;
    private String Especialidade;
    private ConvenioEntity Convenio;

    public MedicoEntity() {

    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) throws ValidationException {
        String[] nomeArray = nome.split(" ");

        if(nomeArray.length > 1)
            this.Nome = nome;
        else {
            this.Nome = null;
            throw new ValidationException("Digite o nome completo do médico!");
        }
    }

    public String getCRM() { return this.CRM; }

    public void setCRM(String CRM) throws ValidationException {
        if(CRM == "")
            throw new ValidationException("O CRM é obrigatório!");
        else
            this.CRM = CRM;
    }

    public String getEspecialidade() {
        return this.Especialidade;
    }

    public void setEspecialidade(String Especialidade) {
        this.Especialidade = Especialidade;
    }

    public ConvenioEntity getConvenio() {
        return Convenio;
    }

    public void setConvenio(ConvenioEntity convenio) {
        Convenio = convenio;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Nome", this.getNome());
        doc.append("CRM", this.getCRM());
        doc.append("Especialidade", this.getEspecialidade());

        if(Convenio != null)
            doc.append("Convenio", this.getConvenio().ToDocument());
        if(this.Id != null)
            doc.append("_id", this.getId());

        return doc;
    }

    public void ToClass(Document document) {
        this.Id = (ObjectId) document.get("_id");
        this.Nome = (String) document.get("Nome");
        this.CRM = (String) document.get("CRM");
        this.Especialidade = (String) document.get("Especialidade");

        Document convenioDocument = (Document)document.get("Convenio");
        if(convenioDocument != null) {
            this.Convenio = new ConvenioEntity();
            this.Convenio.ToClass(convenioDocument);
        }
    }
}

package Domain.Entities;

import Domain.Exceptions.ValidationException;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class EnderecoEntity extends BaseEntity {
    private String Logradouro;
    private String CEP;
    private int Numero;
    private String Bairro;
    private String Cidade;
    private String Complemento;

    public EnderecoEntity(String logradouro, String CEP, int numero, String bairro, String cidade, String complemento) {
        this.Logradouro = logradouro;
        this.CEP = CEP;
        this.Numero = numero;
        this.Bairro = bairro;
        this.Cidade = cidade;
        this.Complemento = complemento;
    }

    public EnderecoEntity() {

    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) throws ValidationException {
        if(logradouro == "") {
            this.Logradouro = null;
            throw new ValidationException("O logradouro é obrigatório!");
        } else {
            this.Logradouro = logradouro;
        }
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) throws ValidationException {
        if(CEP == "") {
            this.CEP = null;
            throw new ValidationException("O CEP é obrigatório!");
        } else {
            this.CEP = CEP;
        }
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) throws ValidationException {
        if(bairro == "") {
            this.Bairro = null;
            throw new ValidationException("O bairro é obrigatório!");
        } else {
            this.Bairro = bairro;
        }
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) throws ValidationException {
        if(cidade == "") {
            this.Cidade = null;
            throw new ValidationException("A cidade é obrigatória!");
        } else {
            this.Cidade = cidade;
        }
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Logradouro", this.getLogradouro());
        doc.append("CEP", this.getCEP());
        doc.append("Numero", this.getNumero());
        doc.append("Bairro", this.getBairro());
        doc.append("Cidade", this.getCidade());
        doc.append("Complemento", this.getComplemento());

        return doc;
    }

    public static EnderecoEntity ToClass(Document document) {
        EnderecoEntity entity = new EnderecoEntity();

        entity.Logradouro = ((String) document.get("Logradouro"));
        entity.CEP = ((String) document.get("CEP"));
        entity.Numero = ((int) document.get("Numero"));
        entity.Bairro = ((String) document.get("Bairro"));
        entity.Cidade = ((String) document.get("Cidade"));
        entity.Complemento = ((String) document.get("Complemento"));

        return entity;
    }
}

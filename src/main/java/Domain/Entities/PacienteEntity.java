package Domain.Entities;

import Domain.Exceptions.ValidationException;
import Domain.Utils.Validators;
import Services.ConvenioService;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.text.html.parser.TagElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PacienteEntity extends BaseEntity{
    private String Nome;
    private String Cpf;
    private String Telefone;
    private Date DataNascimento;
    private EnderecoEntity Endereco;
    private ConvenioEntity Convenio;

    public PacienteEntity() {

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
            throw new ValidationException("Digite o nome completo do paciente!");
        }
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) throws ValidationException {
        if(Validators.ValidarCPF(cpf))
            this.Cpf = cpf;
        else {
            this.Cpf = null;
            throw new ValidationException("O CPF inserido é inválido!");
        }
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public String getStringDataNascimento() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.DataNascimento);
    }
    public void setDataNascimento(String dataNascimento) throws ValidationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.DataNascimento = dateFormat.parse(dataNascimento);
        }
        catch(ParseException exception) {
            this.DataNascimento = null;
            throw new ValidationException("Formato incorreto de data!");
        }
    }

    public void setTelefone(String telefone) throws ValidationException {
        if((telefone.length() == 10 || telefone.length() == 11) && Validators.ValidaSoTemNumero(telefone)) {
            this.Telefone = telefone;
        } else {
            this.Telefone = null;
            throw new ValidationException("Telefone inválido!");
        }
    }

    public String getTelefone() {
        return this.Telefone;
    }

    public EnderecoEntity getEndereco() {
        return Endereco;
    }

    public void setEndereco(EnderecoEntity endereco) {
        Endereco = endereco;
    }

    public void setConvenio(ConvenioEntity convenio) { this.Convenio = convenio; }

    public ConvenioEntity getConvenio() { return this.Convenio; }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Nome", this.getNome());

        doc.append("Cpf", this.getCpf());
        doc.append("DataNascimento", this.getDataNascimento());
        doc.append("Telefone", this.getTelefone());
        doc.append("Endereco", this.getEndereco().ToDocument());

        if(Convenio != null)
            doc.append("Convenio", this.getConvenio().ToDocument());

        return doc;
    }

    public void ToClass(Document document) {
        PacienteEntity entity = new PacienteEntity();

        this.Id = ((ObjectId) document.get("_id"));
        this.Nome = ((String) document.get("Nome"));
        this.Cpf = ((String) document.get("Cpf"));
        this.DataNascimento = (Date) document.get("DataNascimento");
        this.Endereco = new EnderecoEntity();
        this.Endereco.ToClass((Document)document.get("Endereco"));
        this.Telefone = (String) document.get("Telefone");
        this.Convenio = new ConvenioEntity();

        Document convenioDocument = (Document)document.get("Convenio");
        if(convenioDocument != null)
            this.Convenio.ToClass(convenioDocument);
    }
}

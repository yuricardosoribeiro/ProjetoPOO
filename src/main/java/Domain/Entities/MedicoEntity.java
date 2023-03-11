package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

public class MedicoEntity extends BaseEntity<MedicoEntity> {
    private String Nome;
    private String Email;
    private String Senha;

    public MedicoEntity() {

    }

    public MedicoEntity(ObjectId id, String nome, String email, String senha) {
        Id = id;
        Nome = nome;
        Email = email;
        Senha = senha;
    }

    public ObjectId getId() {
        return Id;
    }

    public void setId(ObjectId id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document("Nome", this.getNome());
        doc.append("Email", this.getEmail());
        doc.append("Senha", this.getSenha());

        return doc;
    }

    public static MedicoEntity toClass(Document document) {
        MedicoEntity entity = new MedicoEntity();

        entity.setId((ObjectId) document.get("_id"));
        entity.setEmail((String) document.get("Email"));
        entity.setNome((String) document.get("Nome"));
        entity.setSenha((String) document.get("Senha"));

        return entity;
    }
}

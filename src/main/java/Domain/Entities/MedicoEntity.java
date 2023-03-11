package Domain.Entities;

import org.bson.Document;

public class MedicoEntity extends BaseEntity {
    private String Nome;
    private String Email;
    private String Senha;

    public MedicoEntity(int id, String nome, String email, String senha) {
        Id = id;
        Nome = nome;
        Email = email;
        Senha = senha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public Document toDocument() {
        Document doc = new Document("Nome", this.getNome());
        doc.append("Email", this.getEmail());
        doc.append("Senha", this.getSenha());

        return doc;
    }
}

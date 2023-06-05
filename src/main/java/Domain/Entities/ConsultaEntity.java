package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalTime;
import java.util.Date;

public class ConsultaEntity extends BaseEntity<ConsultaEntity> {

    private Date Data;
    private ObjectId MedicoId;
    private ObjectId PacienteId;

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public ObjectId getMedicoId() {
        return MedicoId;
    }

    public void setMedicoId(ObjectId medicoId) {
        MedicoId = medicoId;
    }

    public ObjectId getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(ObjectId pacienteId) {
        PacienteId = pacienteId;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Data", this.getData());
        doc.append("MedicoId", this.getMedicoId());
        doc.append("PacienteId", this.getPacienteId());

        return doc;
    }

    @Override
    public void ToClass(Document document) {

    }
}

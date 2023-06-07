package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalTime;
import java.util.Date;

public class ConsultaEntity extends BaseEntity<ConsultaEntity> {

    private Date Data;
    private MedicoEntity Medico;
    private PacienteEntity Paciente;
    private boolean ConsultaAconteceu;
    private RegistroConsultaEntity Registros;

    public RegistroConsultaEntity getRegistros() {
        return Registros;
    }

    public void setRegistros(RegistroConsultaEntity registros) {
        Registros = registros;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public MedicoEntity getMedico() {
        this.ConsultaAconteceu = false;
        return Medico;
    }

    public void setMedico(MedicoEntity medico) {
        Medico = medico;
    }

    public PacienteEntity getPaciente() {
        return Paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        Paciente = paciente;
    }

    public boolean isConsultaAconteceu() {
        return ConsultaAconteceu;
    }

    public void setConsultaAconteceu(boolean consultaAconteceu) {
        ConsultaAconteceu = consultaAconteceu;
    }

    @Override
    public Document ToDocument() {
        Document doc = new Document("Data", this.getData());
        doc.append("Medico", this.getMedico().ToDocument());
        doc.append("Paciente", this.getPaciente().ToDocument());
        doc.append("ConsultaAconteceu", this.isConsultaAconteceu());

        if(this.Id != null)
            doc.append("_id", this.getId());

        if(this.Registros != null)
            doc.append("Registros", this.getRegistros().ToDocument());

        return doc;
    }

    @Override
    public void ToClass(Document document) {
        this.Id = (ObjectId) document.get("_id");
        this.Data = (Date) document.get("Data");
        this.ConsultaAconteceu = (boolean) document.get("ConsultaAconteceu");

        this.Medico = new MedicoEntity();
        Medico.ToClass((Document) document.get("Medico"));

        this.Paciente = new PacienteEntity();
        Paciente.ToClass((Document) document.get("Paciente"));

        Document convenioDocument = (Document)document.get("Registros");
        if(convenioDocument != null)
            this.Registros.ToClass(convenioDocument);
    }
}

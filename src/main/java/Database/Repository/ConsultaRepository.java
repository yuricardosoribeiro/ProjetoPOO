package Database.Repository;

import Domain.Entities.ConsultaEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultaRepository extends BaseRepository<ConsultaEntity> {

    public ConsultaRepository() {
        super("consulta", new ConsultaEntity());
    }

    public List<ConsultaEntity> GetAllConsultasMedicoDia(ObjectId medicoId) {
        List<ConsultaEntity> consultas = this.GetAll();
        LocalDate dataAtual = LocalDate.now();

        return consultas.stream().filter(consulta -> {
            LocalDate dataConsulta = consulta.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            return consulta.getMedico().getId().equals(medicoId) && dataConsulta.isEqual(dataAtual);
        }).toList();
    }

    public List<ConsultaEntity> GetAllConsultasMedicoSemana(ObjectId medicoId) {
        List<ConsultaEntity> consultas = this.GetAll();

        LocalDate dataAtual = LocalDate.now();
        LocalDate segundaFeira = dataAtual.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate sextaFeira = dataAtual.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        return consultas.stream().filter(consulta -> {
            LocalDate dataConsulta = consulta.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return consulta.getMedico().getId().equals(medicoId) && (dataConsulta.isAfter(segundaFeira) && dataConsulta.isBefore(sextaFeira) && !consulta.isConsultaAconteceu());
        }).toList();
    }

    public List<ConsultaEntity> GetByDiaMedico(ObjectId medicoId, Date dia) {
        List<ConsultaEntity> consultas = this.GetAll();

        return consultas.stream().filter(consulta ->
            consulta.getMedico().getId().equals(medicoId) &&
            consulta.getData().getDate() == dia.getDate() &&
            consulta.getData().getMonth() == dia.getMonth() &&
            consulta.getData().getYear() == dia.getYear()
        ).toList();
    }

    public List<ConsultaEntity> GetAllConsultasPaciente(ObjectId pacienteId) {
        List<ConsultaEntity> consultas = this.GetAll();

        return consultas.stream().filter(consulta ->
                consulta.getPaciente().getId().equals(pacienteId) && !consulta.isConsultaAconteceu()).toList();
    }

    public ConsultaEntity GetConsultaOcorrendoAgora(ObjectId medicoId) {
        List<ConsultaEntity> consultas = this.GetAll();
        ConsultaEntity consultaEscolhida = null;
        Date dataAtual = new Date();

        for(ConsultaEntity consulta : consultas) {
            if(consulta.getData().getDate() == dataAtual.getDate() &&
                consulta.getData().getMonth() == dataAtual.getMonth() &&
                consulta.getData().getYear() == dataAtual.getYear() &&
                consulta.getData().getHours() == dataAtual.getHours() &&
                consulta.getMedico().getId() == medicoId)
                consultaEscolhida = consulta;
        }

        return consultaEscolhida;
    }
}

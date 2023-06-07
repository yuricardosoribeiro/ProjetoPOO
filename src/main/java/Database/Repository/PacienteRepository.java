package Database.Repository;

import Domain.Entities.PacienteEntity;

public class PacienteRepository extends BaseRepository<PacienteEntity> {
    public PacienteRepository() {
        super("paciente", new PacienteEntity());
    }
}

package Database.Repository;

import Domain.Entities.ConsultaEntity;

public class ConsultaRepository extends BaseRepository<ConsultaEntity> {

    public ConsultaRepository() {
        super("consulta", new ConsultaEntity());
    }
}

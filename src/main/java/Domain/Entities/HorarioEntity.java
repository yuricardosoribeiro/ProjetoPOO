package Domain.Entities;

import org.bson.Document;

import java.util.Date;

public class HorarioEntity extends BaseEntity {
    private Date DataInicial;
    private Date DataFinal;

    @Override
    public Document ToDocument() {
        return null;
    }
}

package Domain.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EspecialidadeConstant {
    private List<String> lstEspecialidades = new ArrayList<String>() {{
        add("Cardiologia");
        add("Neurologia");
        add("Oftalmologia");
        add("Patologia");
        add("Pediatria");
        add("Psiquiatria");
        add("Radioterapia");
        add("Urologia");
        add("Geriatria");
        add("Anestesiologista");
    }};

    public List<String> ObterTodasEspecialidades() {
        return Collections.unmodifiableList(this.lstEspecialidades);
    }

}

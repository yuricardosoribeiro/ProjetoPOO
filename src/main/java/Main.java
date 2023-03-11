import Database.Repository.MedicoRepository;
import Domain.Entities.MedicoEntity;
import org.bson.types.ObjectId;

import java.util.List;

public class Main {
    public static void main(String[] args){
        MedicoEntity entity = new MedicoEntity(ObjectId.get(), "Doutor Yuri", "yuri@gmail.com", "yurinhobembonitinho");

        MedicoRepository repository = new MedicoRepository();
//        repository.Insert(entity);

//        List<MedicoEntity> lst = repository.GetAll();
//        MedicoEntity entity1 = repository.GetById(new ObjectId("640bdefd7e61f45bdad2e8fd"));
//        entity1.setNome("Doutor Chinês");
//
//        repository.Update(entity1);
//
//        System.out.println(entity1.getNome() + entity1.getEmail() + entity1.getSenha());

        repository.Delete(new ObjectId("640bdefd7e61f45bdad2e8fd"));
    }
}
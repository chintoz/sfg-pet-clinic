package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(owner -> lastName.equals(owner.getLastName()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Owner> findByLastNameLike(String lastName) {
        String lastNameModified = lastName.replace("%", "");

        return this.map.values().stream()
                .filter(o -> o.getLastName().contains(lastNameModified))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner object) {

        if (object == null) {
            return null;
        }

        if (object.getPets() != null) {
            Set<Pet> pets = object.getPets().stream()
                    .map(this::persistPetType)
                    .map(pet -> pet.getId() != null ? pet : petService.save(pet))
                    .collect(Collectors.toSet());
            object.getPets().clear();
            object.getPets().addAll(pets);
        }

        return super.save(object);
    }

    private Pet persistPetType(Pet pet) {
        if (pet.getPetType() == null) {
            throw new RuntimeException("Pet Type is required");
        }
        if (pet.getPetType().getId() != null) {
            pet.setPetType(petTypeService.save(pet.getPetType()));
        }
        return pet;
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

}

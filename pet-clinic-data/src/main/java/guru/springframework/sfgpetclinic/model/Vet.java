package guru.springframework.sfgpetclinic.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "vet_speciality",
        joinColumns = @JoinColumn(name = "vet_id"),
        inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }
}

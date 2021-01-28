package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {

    @Builder
    public Vet(Long id, String firstName, String lastName, Set<Speciality> specialities) {
        super(id, firstName, lastName);
        this.specialities = specialities;
    }

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "vet_speciality",
        joinColumns = @JoinColumn(name = "vet_id"),
        inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

}

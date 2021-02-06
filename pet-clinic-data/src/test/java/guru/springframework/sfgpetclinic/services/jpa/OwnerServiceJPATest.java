package guru.springframework.sfgpetclinic.services.jpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJPATest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerServiceJPA ownerService;

    private static final String SMITH_LAST_NAME = "Smith";

    private Owner defaultOwner;

    @BeforeEach
    void setUp() {
        defaultOwner = Owner.builder().id(1L).lastName(SMITH_LAST_NAME).build();
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll())
                .thenReturn(List.of(defaultOwner, Owner.builder().id(2L).build()));

        Set<Owner> owners = ownerService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findByExistingId() {
        when(ownerRepository.findById(eq(1L)))
                .thenReturn(Optional.of(defaultOwner));

        Owner owner = ownerService.findById(1L);

        assertNotNull(owner);
        assertEquals(1L, owner.getId());
        verify(ownerRepository, times(1)).findById(eq(1L));
    }

    @Test
    void findByNotExistingId() {
        when(ownerRepository.findById(eq(1L)))
                .thenReturn(Optional.empty());

        Owner owner = ownerService.findById(1L);

        assertNull(owner);
        verify(ownerRepository, times(1)).findById(eq(1L));
    }

    @Test
    void save() {

        when(ownerRepository.save(any())).thenReturn(defaultOwner);

        Owner ownerSaved = ownerService.save(defaultOwner);

        assertNotNull(ownerSaved);
        assertEquals(defaultOwner, ownerSaved);
        verify(ownerRepository, times(1)).save(eq(defaultOwner));
    }

    @Test
    void delete() {

        ownerService.delete(defaultOwner);

        verify(ownerRepository, times(1)).delete(defaultOwner);

    }

    @Test
    void deleteById() {

        ownerService.deleteById(1L);

        verify(ownerRepository, times(1)).deleteById(eq(1L));
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(SMITH_LAST_NAME))
                .thenReturn(Optional.of(defaultOwner));

        Owner owner = ownerService.findByLastName(SMITH_LAST_NAME);

        assertNotNull(owner);
        assertEquals(SMITH_LAST_NAME, owner.getLastName());
        assertEquals(1L, owner.getId());
        verify(ownerRepository, times(1)).findByLastName(eq(SMITH_LAST_NAME));
    }
}
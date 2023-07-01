package user.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import user.repository.RoleRepository;
import user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserGenerationServiceTest {

    @InjectMocks
    UserGenerationService userGenerationService;

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @Test
    void createFiveUsers() {
        userGenerationService.createFiveUsers();
    }
}
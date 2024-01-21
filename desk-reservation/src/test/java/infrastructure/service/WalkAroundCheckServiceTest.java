package infrastructure.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@QuarkusTest
class WalkAroundCheckServiceTest {

    @Inject
    WalkAroundCheckService service;

    @Test
    void loadTemplateAndReplacePlaceholdersTest() throws IOException {

        service.loadTemplateAndReplacePlaceholders();
    }
}
package id.ac.ui.cs.advprog.eshop.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.EshopApplication;

@ExtendWith(MockitoExtension.class)

class ApplicationTest {



    @InjectMocks
    EshopApplication application = new EshopApplication();

    @Test
    void shouldRunMainWithoutException() {
        assertDoesNotThrow(() -> 
            application.main(new String[]{"test"})
        );
}
    
}

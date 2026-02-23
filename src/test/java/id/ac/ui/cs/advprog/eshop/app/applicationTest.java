package id.ac.ui.cs.advprog.eshop.app;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import id.ac.ui.cs.advprog.eshop.*;

import java.util.Iterator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class applicationTest {



    @InjectMocks
    EshopApplication application = new EshopApplication();

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldRunMainWithoutException() {
        assertDoesNotThrow(() -> 
            application.main(new String[]{"test"})
        );
}
    
}

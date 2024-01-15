package com.job.testsender.handler;

import ca.uhn.fhir.parser.DataFormatException;
import com.job.testsender.service.KafkaService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FhirHandlerTest {


    @MockBean
    private KafkaService kafkaService;

    private FhirBundleMessageHandler fhirBundleMessageHandler;

    private String mockStringBundle;
    private String mockExpectedJson;

    @BeforeAll
    public void init() {
        fhirBundleMessageHandler = new FhirBundleMessageHandler(kafkaService);
        mockStringBundle = new String(fromFile("mock_data.json"));
        mockExpectedJson = new String(fromFile("mock_expected_data.json"));
    }

    @SneakyThrows
    private byte[] fromFile(String path) {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }

    @Test
    public void testFhir_Valid_data_provided() {
        fhirBundleMessageHandler.collectAndProcessStringBundle(mockStringBundle);
        verify(kafkaService).sendMultipleKafkaMessages(new ArrayList<>(Collections.singleton(mockExpectedJson)));
    }

    @Test
    public void testFhir_Empty_string_provided() {
        NullPointerException nullExp = assertThrows(NullPointerException.class,
                () -> fhirBundleMessageHandler.collectAndProcessStringBundle(""));
        assertEquals("Provided bundle string is null or empty", nullExp.getMessage());
    }

    @Test
    public void testFhir_Null_data_provided() {
        NullPointerException nullExp = assertThrows(NullPointerException.class,
                () -> fhirBundleMessageHandler.collectAndProcessBundle(null));
        assertEquals("Provided bundle object is null", nullExp.getMessage());
    }

    @Test
    public void testFhir_Incorrect_bundle_string_provided() {
        DataFormatException dataFormatException = assertThrows(DataFormatException.class,
                () -> fhirBundleMessageHandler.collectAndProcessStringBundle("INCORRECT STRING"));
        assertEquals("HAPI-1861: Failed to parse JSON encoded FHIR content: HAPI-1859: Content does not appear to be FHIR JSON, first non-whitespace character was: 'I' (must be '{')", dataFormatException.getMessage());
    }

    @Test
    public void testFhir_Empty_json_provided() {
        DataFormatException dataFormatException = assertThrows(DataFormatException.class,
                () -> fhirBundleMessageHandler.collectAndProcessStringBundle("{}"));
        assertEquals("HAPI-1838: Invalid JSON content detected, missing required element: 'resourceType'", dataFormatException.getMessage());
    }

    // ca.uhn.fhir.parser.DataFormatException: HAPI-1861: Failed to parse JSON encoded FHIR content: HAPI-1857: Did not find any content to parse

}

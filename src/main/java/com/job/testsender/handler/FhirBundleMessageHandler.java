package com.job.testsender.handler;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.job.testsender.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.ResourceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FhirBundleMessageHandler {

    private final KafkaService kafkaService;

    private final IParser parser;

    public FhirBundleMessageHandler(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
        this.parser = FhirContext.forR4().newJsonParser().setPrettyPrint(true);
    }

    public void collectAndProcessStringBundle(String message) {
        if (message == null || message.isEmpty()) throw new NullPointerException("Provided bundle string is null or empty");
        Bundle bundle = parser.parseResource(Bundle.class, message);
        collectAndProcessBundle(bundle);
    }

    public void collectAndProcessBundle(Bundle bundle) {
        Objects.requireNonNull(bundle, "Provided bundle object is null");
        List<String> finishedEncounterStringStream = bundle.getEntry().stream()
                .filter(entry -> entry.getResource().getResourceType() == ResourceType.Encounter)
                .map(entry -> (Encounter) entry.getResource())
                .filter(entryEncounter -> entryEncounter.getStatus() == Encounter.EncounterStatus.FINISHED)
                .map(entryEncounter -> {
                    entryEncounter.setSubject(null);
                    entryEncounter.setLocation(null);
                    entryEncounter.setServiceProvider(null);
                    entryEncounter.getParticipant().forEach(participant -> participant.setIndividual(null));
                    return parser.encodeResourceToString(entryEncounter);
                })
                .collect(Collectors.toList());
        if (finishedEncounterStringStream.size() > 0) {
            log.info("Encounter list contains elements, processing");
            kafkaService.sendMultipleKafkaMessages(finishedEncounterStringStream);
        }
    }

}

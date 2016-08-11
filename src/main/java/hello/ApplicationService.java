package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final ApplicationTranslator applicationTranslator;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(
            ApplicationTranslator applicationTranslator,
            ApplicationRepository applicationRepository
    ) {
        this.applicationTranslator = applicationTranslator;
        this.applicationRepository = applicationRepository;
    }

    public Structure postStructure(String input) {
        Structure structure = applicationTranslator.inputToDomain(input);
        StructureEntity translatedEntity = applicationTranslator.domainToEntity(structure);
        StructureEntity savedEntity = applicationRepository.save(translatedEntity);
        return applicationTranslator.entityToDomain(savedEntity);
    }
}

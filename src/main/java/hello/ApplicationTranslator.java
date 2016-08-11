package hello;

import org.springframework.stereotype.Component;

@Component
public class ApplicationTranslator {

    public StructureEntity domainToEntity(Structure structure) {
        StructureEntity structureEntity = new StructureEntity();
        structureEntity.setId(structure.getId());
        structureEntity.setPostDate(structure.getPostDate());
        structureEntity.setMass(structure.getMass());
        return structureEntity;
    }

    public Structure entityToDomain(StructureEntity structureEntity) {
        Structure structure = new Structure();
        structure.setId(structureEntity.getId());
        structure.setPostDate(structureEntity.getPostDate());
        structure.setMass(structureEntity.getMass());
        return structure;
    }

    // TODO: break parser out into separate service for scaling?
    public Structure inputToDomain(String input) {
        Structure structure = new Structure(
                1,
                System.currentTimeMillis(),
                7695
        );
        return structure;
    }
}

package eu.smartx.keler.objectmetadata.service.metadata;

import lombok.Data;

import java.util.Map;

@Data
public class ObjectWithMetaData {
    private final String propertyName; // a nodeot tartalmazó property neve a szülő beanben
    private final String className; // az érték java class neve
    private final String translationKey; // az érték fordításához használt MessageSource kulcs,
    private final String value; // ha egyszerű értéket hordoz a mező.
    private final Map<String, ObjectWithMetaData> children; // komplex esetben a bean property-k mint kulcs, és az értékek ObjectWithMetaData reprezentációi
}

package eu.smartx.keler.objectmetadata.service.metadata.primitive;

public interface PrimitiveValueExtractor {
    boolean supports(Object o);
    String extract(Object o);
}

package eu.smartx.keler.objectmetadata.service.metadata.primitive;

public class SimplePrimitiveTypeClassifier implements PrimitiveTypeClassifier {

    @Override
    public boolean isPrimitiveType(Object o) {
        return o != null &&
                (o instanceof Integer || o instanceof String);
    }
}

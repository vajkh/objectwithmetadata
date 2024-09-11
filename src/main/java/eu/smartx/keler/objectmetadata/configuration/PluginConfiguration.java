package eu.smartx.keler.objectmetadata.configuration;

import eu.smartx.keler.objectmetadata.service.metadata.primitive.IntegerExtractor;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.PrimitiveTypeClassifier;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.PrimitiveValueExtractor;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.SimplePrimitiveTypeClassifier;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.StringExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class PluginConfiguration {


    @Bean
    public Set<PrimitiveValueExtractor> primitiveValueExtractors(){
        return Set.of(new IntegerExtractor(), new StringExtractor());
    }

    @Bean
    public PrimitiveTypeClassifier primitiveTypeClassifier(){
        return new SimplePrimitiveTypeClassifier();
    }
}

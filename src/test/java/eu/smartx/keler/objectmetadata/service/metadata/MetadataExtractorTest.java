package eu.smartx.keler.objectmetadata.service.metadata;

import eu.smartx.keler.objectmetadata.service.metadata.primitive.IntegerExtractor;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.SimplePrimitiveTypeClassifier;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.StringExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;

public class MetadataExtractorTest {

    private MetadataExtractor metadataExtractor = new MetadataExtractor(new SimplePrimitiveTypeClassifier(), Set.of(new IntegerExtractor(), new StringExtractor()));
    @Test
    void testExtractPrimitive() {
        assertNull(metadataExtractor.extract(null));

        ObjectWithMetaData testString = metadataExtractor.extract("test");
        Assertions.assertThat(testString.getPropertyName()).isEqualTo("root");
        Assertions.assertThat(testString.getClassName()).isEqualTo("java.lang.String");
        Assertions.assertThat(testString.getValue()).isEqualTo("test");
        assertNull(testString.getChildren());
        Assertions.assertThat(testString.getTranslationKey()).isEqualTo(null);
    }

    @Test
    void testExtractComposite() {

        var testDTO = TestDTO.builder()
                .id(1)
                .name("name")
                .description("description")
                .status(TestDTO.TestStatus.OK)
                .mother(TestDTO.builder().id(2).build())
                .build();
        ObjectWithMetaData testMetaData = metadataExtractor.extract(testDTO);

        //System.out.println(testMetaData);
        //Assertions.assertThat(testMetaData.getChildren().keySet()).hasSameElementsAs();

        Assertions.assertThat(testMetaData.getChildren().get("id").getPropertyName()).isEqualTo("id");
        Assertions.assertThat(testMetaData.getChildren().get("id").getValue()).isEqualTo("1");
        Assertions.assertThat(testMetaData.getChildren().get("id").getClassName()).isEqualTo("int");
        Assertions.assertThat(testMetaData.getChildren().get("id").getChildren()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("id").getTranslationKey()).isNull();

        Assertions.assertThat(testMetaData.getChildren().get("description").getPropertyName()).isEqualTo("description");
        Assertions.assertThat(testMetaData.getChildren().get("description").getValue()).isEqualTo("description");
        Assertions.assertThat(testMetaData.getChildren().get("description").getClassName()).isEqualTo("java.lang.String");
        Assertions.assertThat(testMetaData.getChildren().get("description").getChildren()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("description").getTranslationKey()).isNull();

        Assertions.assertThat(testMetaData.getChildren().get("status").getPropertyName()).isEqualTo("status");
        Assertions.assertThat(testMetaData.getChildren().get("status").getValue()).isEqualTo(null); //FIXME enum value
        Assertions.assertThat(testMetaData.getChildren().get("status").getClassName()).isEqualTo("eu.smartx.keler.objectmetadata.service.metadata.TestDTO$TestStatus");
        //Assertions.assertThat(testMetaData.getChildren().get("status").getChildren()).isNull(); //FIXME enum primitive
        //Assertions.assertThat(testMetaData.getChildren().get("status").getTranslationKey()).isEqualTo("translationkey");

        Assertions.assertThat(testMetaData.getChildren().get("mother").getPropertyName()).isEqualTo("mother");
        Assertions.assertThat(testMetaData.getChildren().get("mother").getValue()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("mother").getClassName()).isEqualTo("eu.smartx.keler.objectmetadata.service.metadata.TestDTO");
        Assertions.assertThat(testMetaData.getChildren().get("mother").getChildren()).isNotEmpty();
        Assertions.assertThat(testMetaData.getChildren().get("mother").getTranslationKey()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("mother").getChildren().get("id").getValue()).isEqualTo("2");

        Assertions.assertThat(testMetaData.getChildren().get("father").getPropertyName()).isEqualTo("father");
        Assertions.assertThat(testMetaData.getChildren().get("father").getValue()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("father").getClassName()).isEqualTo("eu.smartx.keler.objectmetadata.service.metadata.TestDTO");
        Assertions.assertThat(testMetaData.getChildren().get("father").getChildren()).isNull();
        Assertions.assertThat(testMetaData.getChildren().get("mother").getTranslationKey()).isNull();
    }
}

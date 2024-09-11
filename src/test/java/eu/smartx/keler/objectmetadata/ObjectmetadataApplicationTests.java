package eu.smartx.keler.objectmetadata;

import eu.smartx.keler.objectmetadata.repository.ConversionRepository;
import eu.smartx.keler.objectmetadata.service.ConversionService;
import eu.smartx.keler.objectmetadata.service.MetaDataService;
import eu.smartx.keler.objectmetadata.service.metadata.MetadataExtractor;
import eu.smartx.keler.objectmetadata.service.metadata.TestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ObjectmetadataApplicationTests {

	@Autowired
	private MetadataExtractor metadataExtractor;

	@Autowired
	private MetaDataService metaDataService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ConversionRepository conversionRepository;

	@Test
	void test() {
		var testDTO1 = testDTO(1);
		var savedMeta1 = conversionService.convert(testDTO1);

		var testDTO2 = testDTO(2);
		var savedMeta2 = conversionService.convert(testDTO2);

		var loaded = conversionRepository.findById(savedMeta1.getId());
		System.out.println(loaded);

		var loaded2 = conversionRepository.findById(savedMeta2.getId());
		System.out.println(loaded2);

	}

	private TestDTO testDTO(int id){
		return TestDTO.builder()
				.id(id)
				.name("name")
				.description("description")
				.status(TestDTO.TestStatus.OK)
				.mother(TestDTO.builder().id(id + 100).build())
				.build();
	}

}

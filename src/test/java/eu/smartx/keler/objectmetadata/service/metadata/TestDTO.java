package eu.smartx.keler.objectmetadata.service.metadata;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class TestDTO {
    private int id;
    private String name;
    private String description;
    private TestStatus status;
    private TestDTO mother;
    private TestDTO father;

    // List Támpogatása nem MVP
    private List<TestDTO> neighborhood;

    // Map Támpogatása nem MVP
    // the key is the id of the objects
    private Map<String, TestDTO> children;

    public enum TestStatus
    {
        OK, FAIL, PENDING;
    }
}
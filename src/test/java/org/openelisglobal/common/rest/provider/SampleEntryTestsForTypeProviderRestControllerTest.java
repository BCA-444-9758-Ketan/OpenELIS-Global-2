package org.openelisglobal.common.rest.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.openelisglobal.BaseWebContextSensitiveTest;
import org.openelisglobal.typeofsample.service.TypeOfSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class SampleEntryTestsForTypeProviderRestControllerTest extends BaseWebContextSensitiveTest {

    @Autowired
    private TypeOfSampleService typeOfSampleService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        typeOfSampleService.clearCache();
        executeDataSetWithStateManagement("testdata/sample-entry-tests-provider.xml");
    }

    @Test
    public void getSampleTypeDefaultUoms_shouldReturnTypeIdsWithDefaultUomIds() throws Exception {
        MvcResult result = super.mockMvc
                .perform(get("/rest/sample-type-default-uoms").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());

        String json = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> uomMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });

        assertTrue("Sample type with a default UoM should be present in the map", uomMap.containsKey("1"));
        assertEquals("The default UoM id should match", "1", uomMap.get("1"));
    }

    @Test
    public void getSampleTypeDefaultUoms_shouldOmitTypesWithoutDefaultUom() throws Exception {
        MvcResult result = super.mockMvc
                .perform(get("/rest/sample-type-default-uoms").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());

        String json = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> uomMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });

        assertFalse("Sample type without a default UoM should be absent from the map", uomMap.containsKey("2"));
    }
}

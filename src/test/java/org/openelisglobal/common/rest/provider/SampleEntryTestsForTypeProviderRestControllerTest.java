package org.openelisglobal.common.rest.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
        executeDataSetWithStateManagement("testdata/type-of-sample-default-uoms.xml");
    }

    @Test
    public void getSampleTypeDefaultUoms_shouldReturnMapOfTypeIdToDefaultUomId() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/rest/sample-type-default-uoms").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());

        String json = result.getResponse().getContentAsString();
        assertNotNull("Response body should not be null", json);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> typeUomMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });

        assertNotNull("Returned map should not be null", typeUomMap);
        // Sample type "1" has default_uom_id="1" in the test data
        assertTrue("Map should contain entry for sample type 1", typeUomMap.containsKey("1"));
        assertEquals("Default UoM for sample type 1 should be '1'", "1", typeUomMap.get("1"));
        // Sample type "2" has no default_uom_id, so it should not appear in the map
        assertFalse("Map should not contain entry for sample type 2 (no default UoM)", typeUomMap.containsKey("2"));
    }
}

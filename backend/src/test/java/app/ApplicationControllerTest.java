package app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {
    private MockMvc mockMvc;
    private ApplicationController subject;
    @Mock ApplicationService mockApplicationService;

    @Before
    public void setup() {
        subject = new ApplicationController(mockApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void postStructure() throws Exception {
        mockMvc.perform(post("/structure")
                .header("Structure-Id", "tall-building")
                .contentType(MediaType.TEXT_PLAIN)
                .content("comma,delimited,data".getBytes()))
                .andExpect(status().isCreated());
    }

    @Test
    public void getStructure_delegatesToService() {
        StructureEntity expectedStructure = new StructureEntity();
        doReturn(expectedStructure).when(mockApplicationService).getStructure(anyString());

        StructureEntity actualStructure = subject.getStructure("tall-building");

        assertThat(actualStructure, equalTo(expectedStructure));
    }

    @Test
    public void getStructure_mapping() throws Exception {
        mockMvc.perform(get("/structure/tall-building"))
                .andExpect(status().isOk());
    }
}
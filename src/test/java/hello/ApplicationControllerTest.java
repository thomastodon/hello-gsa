package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {
    private MockMvc mockMvc;
    private ApplicationController subject;

    @Before
    public void setup() {
        subject = new ApplicationController();
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void postStructures() throws Exception {
        mockMvc.perform(post("/structures")
                .contentType(MediaType.TEXT_PLAIN)
                .content("comma,delimited,data".getBytes()))
                .andExpect(status().isCreated());
    }

    @Test
    public void getHelloPig() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
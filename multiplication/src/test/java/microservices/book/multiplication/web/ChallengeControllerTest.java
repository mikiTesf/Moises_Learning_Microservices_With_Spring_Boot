package microservices.book.multiplication.web;

import microservices.book.multiplication.challenge.Challenge;
import microservices.book.multiplication.service.ChallengeGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ChallengeController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
class ChallengeControllerTest {

    @MockBean
    private ChallengeGeneratorService challengeGeneratorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Challenge> challengeJacksonTester;

    @Test
    void getRandomChallenge() throws Exception {
        // given
        Challenge expectedResponse = new Challenge(12, 12);
        when(challengeGeneratorService.randomChallenge()).thenReturn(expectedResponse);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/challenges/random")
        ).andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(challengeJacksonTester.write(expectedResponse).getJson());
    }
}

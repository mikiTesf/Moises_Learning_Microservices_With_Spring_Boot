package microservices.book.multiplication.web;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import microservices.book.multiplication.service.ChallengeService;
import microservices.book.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ChallengeAttemptController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
class ChallengeAttemptControllerTest {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;

    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Autowired
    private JacksonTester<List<ChallengeAttempt>> jsonResultAttemptList;

    @Test
    public void testValidResult() throws Exception {
        final int FACTOR_A = 12, FACTOR_B = 12;
        // given
        ChallengeAttemptDTO validAttempt = new ChallengeAttemptDTO(FACTOR_A, FACTOR_B, "John Doe", 144);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(null, null, FACTOR_A, FACTOR_B, 144, true);

        given(challengeService.verifyAttempt(eq(validAttempt)))
                .willReturn(expectedResponse);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/attempts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestAttempt.write(validAttempt).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    public void testInvalidAttempt() throws Exception {
        // given
        ChallengeAttemptDTO invalidAttempt = new ChallengeAttemptDTO(-1, 10, "John Doe", 11);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/attempts").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequestAttempt.write(invalidAttempt).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testInvalidFactors() throws Exception {
        // given
        ChallengeAttemptDTO invalidAttempt = new ChallengeAttemptDTO(-1, 200, "John Doe", 11);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestAttempt.write(invalidAttempt).getJson()))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testRecentAttempts() throws Exception {
        // given
        final String USER_ALIAS = "john_doe";
        User johnDoe = new User(USER_ALIAS);
        List<ChallengeAttempt> recentAttempts = Arrays.asList(
                new ChallengeAttempt(null, johnDoe, 10, 10, 20, false),
                new ChallengeAttempt(null, johnDoe, 10, 10, 100, true)
        );
        given(challengeService.getLast10Attempts(USER_ALIAS)).willReturn(recentAttempts);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/attempts/recent").param("alias", USER_ALIAS))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonResultAttemptList.write(recentAttempts).getJson()
        );
    }
}

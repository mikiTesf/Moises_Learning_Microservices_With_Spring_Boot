package microservices.book.multiplication.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import microservices.book.multiplication.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a REST endpoint to POST attempts from the users
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/attempts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChallengeAttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeAttempt> postAttempt(
            @RequestBody @Valid ChallengeAttemptDTO attemptDTO)
    {
        return ResponseEntity.ok(challengeService.verifyAttempt(attemptDTO));
    }

    @GetMapping
    public ResponseEntity<List<ChallengeAttempt>> getLast10Attempts(@RequestParam String alias) {
        List<ChallengeAttempt> recentAttempts = challengeService.getLast10Attempts(alias);

        if (recentAttempts.size() > 0)
            return new ResponseEntity<>(recentAttempts, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }
}

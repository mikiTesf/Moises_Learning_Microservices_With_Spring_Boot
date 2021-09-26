class ApiClient {

    static SERVER_URL = "http://localhost:8080";
    static GET_CHALLENGE = '/challenge/random';
    static POST_RESULT = '/attempts';

    static challenge(): Promise<Response> {
        return fetch(ApiClient.SERVER_URL + ApiClient.GET_CHALLENGE).catch(error => console.log(error));
    }

    static sendGuess(user: string, a: number, b: number, guess: number): Promise<Response> {
        return fetch(ApiClient.SERVER_URL + ApiClient.POST_RESULT, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(
                    {
                        userAlias: user,
                        factorA: a,
                        factorB: b,
                        guess: guess
                    }
                )
            });
    }
}

export default ApiClient;

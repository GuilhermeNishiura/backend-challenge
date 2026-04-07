Feature: Consulta de extratos

    @query @success @smoke
    Scenario: Consultar extratos por conta
        Given estou autenticado no serviço de consulta
        When consulto os extratos da conta "123"
        Then recebo a lista de extratos com sucesso

    @query @success
    Scenario: Consultar extratos de conta sem transações
        Given estou autenticado no serviço de consulta
        When consulto os extratos da conta "999"
        Then recebo lista vazia de extratos

    @query @error
    Scenario: Consultar extrato inexistente
        Given estou autenticado no serviço de consulta
        When consulto o extrato pelo paymentId "inexistente"
        Then recebo erro de extrato nao encontrado

    @query @validation @error
    Scenario: Consultar extrato inválido
        Given estou autenticado no serviço de consulta
        When consulto o extrato pelo paymentId ""
        Then recebo erro de validaçao
    
    @query @security @error
    Scenario: Consultar extrato sem autenticaçao
        Given nao estou autenticado no serviço de consulta
        When consulto os extratos da conta "123"
        Then recebo erro de autenticaçao
    

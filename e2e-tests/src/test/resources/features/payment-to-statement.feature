Feature: Fluxo de pagamento para extrato

    @e2e @success
    Scenario: Pagamento aparece na listagem de extratos
        Given envio um pagamento válido pela API de pagamentos
        When consulto os extratos da conta "123"
        Then o extrato do pagamento aparece na listagem
    
    @e2e @success
    Scenario: Criar vários pagamentos e validar paginaçao da listagem de extratos
        Given envio 6 pagamentos válidos pela API de pagamentos
        When consulto os extratos da conta "456" com paginaçao
        Then a listagem de extratos respeita a paginaçao

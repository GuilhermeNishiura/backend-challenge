Feature: Fluxo de pagamento para notificação

    @e2e @success
    Scenario: Criar pagamento e notificaçao ser enviada com sucesso
        Given que um pagamento válido é criado "E2E_SUCCESS"
        When o fluxo de notificaçoes é processado
        Then a notificaçao deve existir com status "SENT" e 0 tentativas
    
    @e2e @error
    Scenario: Criar pagamento com falha e retry bem sucedido
        Given que um pagamento válido é criado "E2E_FAIL"
        When o retry de notificaçoes é executado
        Then a notificaçao deve existir com status "SENT" e 1 tentativas
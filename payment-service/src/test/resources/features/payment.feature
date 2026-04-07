Feature: Pagamentos

  @payment @success @smoke
  Scenario: Criar pagamento com sucesso
    Given estou autenticado no serviço de pagamento
    When envio um pagamento válido
    Then o pagamento deve ser criado com sucesso
 
  @payment @validation @error
  Scenario: Criar pagamento inválido
    Given estou autenticado no serviço de pagamento
    When envio um pagamento inválido
    Then recebo erro de validaçao

  @payment @security @error
  Scenario: Criar pagamento sem autenticaçao
    Given nao estou autenticado no serviço de pagamento
    When envio um pagamento válido
    Then recebo erro de autenticaçao
  
  @payment @business @error
  Scenario: Criar pagamento com saldo insuficiente
    Given estou autenticado no serviço de pagamento
    When envio um pagamento válido com saldo insuficiente
    Then recebo erro de saldo insuficiente

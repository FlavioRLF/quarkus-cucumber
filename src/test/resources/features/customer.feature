# language: pt

Funcionalidade: Endpoints de Cliente

  Cenário: Criar um novo cliente
    Dado que tenho os dados de um novo cliente
    Quando envio uma requisição POST para "/customer/create" com os dados do cliente
    Então o status da resposta deve ser 201
    E a resposta deve conter os detalhes do cliente criado

  Cenário: Listar todos os clientes
    Dado que existem clientes cadastrados
    Quando envio uma requisição GET para "/customer/getAll"
    Então o status da resposta deve ser 200
    E a resposta deve conter a lista de clientes

  Cenário: Listar um cliente pelo id
    Dado que tenho o id de um cliente existente
    Quando envio uma requisição GET para "/customer/{id}" com o id do cliente
    Então o status da resposta consulta cliente deve ser 200
    E a resposta deve conter os dados do cliente

  Cenário: Editar um cliente existente
    Dado que tenho os dados atualizados de um cliente existente
    Quando envio uma requisição PUT para "/customer/editCustomer/{id}" com os dados atualizados do cliente
    Então o status da resposta edita cliente deve ser 200
    E a resposta deve conter os detalhes do cliente atualizado

  Cenário: Deletar um cliente existente
    Dado que tenho o id de um cliente para deletar
    Quando envio uma requisição DELETE para "/customer/delete/{id}" com o id do cliente
    Então o status da resposta delete cliente deve ser 200
    E o cliente deve ser deletado com sucesso
package com.dev.customer;

import com.dev.customer.dtos.requisicao.RequisicaoCriaCustomer;
import com.dev.customer.dtos.requisicao.RequisicaoEditarCustomer;
import io.cucumber.java.pt.*;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;
import org.mockito.Mockito;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;

@QuarkusTest
public class CustomerStepsDefinitions {

    @Inject
    CustomerService customerService;

    private Response response;
    private RequestSpecification request;

    private Customer mockCustomer;
    private List<Customer> mockCustomerList;

    private Long existingCustomerId;

    @Dado("que tenho os dados de um novo cliente")
    public void que_tenho_os_dados_de_um_novo_cliente() {
        request = given()
                .contentType("application/json")
                .body(
                        "{" + "\"name\":\"João Silva\"," +
                        "\"email\":\"joao.silva@example.com\"," +
                        "\"phone\":\"123456789\"" +
                        ",\"address\":\"Rua A, 123\"}"
                );

        mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("João Silva");
        mockCustomer.setEmail("joao.silva@example.com");
        mockCustomer.setPhone("123456789");
        mockCustomer.setAddress("Rua A, 123");

        Mockito.when(customerService.createCustomer(any(RequisicaoCriaCustomer.class))).thenReturn(mockCustomer);
    }

    @Quando("envio uma requisição POST para {string} com os dados do cliente")
    public void envio_uma_requisicao_post_para_com_os_dados_do_cliente(String path) {
        response = request.when().post(path);
    }

    @Então("o status da resposta deve ser {int}")
    public void o_status_da_resposta_deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode(), is(statusCode));
    }

    @E("a resposta deve conter os detalhes do cliente criado")
    public void a_resposta_deve_conter_os_detalhes_do_cliente_criado() {
        final Customer responseCustomer = response.getBody().as(Customer.class);
        assertThat(responseCustomer.getName(), is(mockCustomer.getName()));
        assertThat(responseCustomer.getEmail(), is(mockCustomer.getEmail()));
        assertThat(responseCustomer.getPhone(), is(mockCustomer.getPhone()));
        assertThat(responseCustomer.getAddress(), is(mockCustomer.getAddress()));
    }

    // Cenário: Listar todos os clientes
    @Dado("que existem clientes cadastrados")
    public void que_existem_clientes_cadastrados() {
        final Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("João Silva");
        customer1.setEmail("joao.silva@example.com");
        customer1.setPhone("123456789");
        customer1.setAddress("Rua A, 123");

        final Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Maria Santos");
        customer2.setEmail("maria.santos@example.com");
        customer2.setPhone("987654321");
        customer2.setAddress("Rua B, 456");

        mockCustomerList = Arrays.asList(customer1, customer2);

        Mockito.when(customerService.getAllCustomer()).thenReturn(mockCustomerList);
    }

    @Quando("envio uma requisição GET para {string}")
    public void envio_uma_requisicao_get_para(String path) {
        response = given()
                .contentType("application/json")
                .when()
                .get(path);
    }

    @E("a resposta deve conter a lista de clientes")
    public void a_resposta_deve_conter_a_lista_de_clientes() {
        final Customer[] responseCustomers = response.getBody().as(Customer[].class);
        assertThat(responseCustomers.length, is(mockCustomerList.size()));
        assertThat(responseCustomers[0].getName(), is(mockCustomerList.get(0).getName()));
        assertThat(responseCustomers[0].getEmail(), is(mockCustomer.getEmail()));
        assertThat(responseCustomers[0].getPhone(), is(mockCustomer.getPhone()));
        assertThat(responseCustomers[0].getAddress(), is(mockCustomer.getAddress()));
    }

    // Cenário: Consultar um cliente pelo id
    @Dado("que tenho o id de um cliente existente")
    public void que_tenho_o_id_de_um_cliente_existente() {
        existingCustomerId = 1L;

        mockCustomer = new Customer();
        mockCustomer.setId(existingCustomerId);
        mockCustomer.setName("João Silva");
        mockCustomer.setEmail("joao.silva@example.com");
        mockCustomer.setPhone("123456789");
        mockCustomer.setAddress("Rua A, 123");

        Mockito.when(customerService.getCustomerById(anyLong())).thenReturn(mockCustomer);
    }

    @Quando("envio uma requisição GET para {string} com o id do cliente")
    public void envio_uma_requisicao_get_para_com_o_id_do_cliente(String path) {
        response = given()
                .contentType("application/json")
                .when()
                .get(path.replace("{id}", existingCustomerId.toString()));
    }

    @Então("o status da resposta consulta cliente deve ser {int}")
    public void o_status_da_resposta_consulta_cliente__deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode(), is(statusCode));
    }

    @E("a resposta deve conter os dados do cliente")
    public void a_resposta_deve_conter_os_detalhes_do_cliente() {
        final Customer responseCustomer = response.getBody().as(Customer.class);

        assertThat(responseCustomer.getId(), is(mockCustomer.getId()));
        assertThat(responseCustomer.getName(), is(mockCustomer.getName()));
        assertThat(responseCustomer.getEmail(), is(mockCustomer.getEmail()));
        assertThat(responseCustomer.getPhone(), is(mockCustomer.getPhone()));
        assertThat(responseCustomer.getAddress(), is(mockCustomer.getAddress()));
    }

    // Cenário: Editar um cliente existente
    @Dado("que tenho os dados atualizados de um cliente existente")
    public void que_tenho_os_dados_atualizados_de_um_cliente_existente() {
        existingCustomerId = 1L;

        // Dados atualizados do cliente
        final RequisicaoEditarCustomer updatedCustomerRequest = new RequisicaoEditarCustomer();
        updatedCustomerRequest.setName("João Silva Filho");
        updatedCustomerRequest.setEmail("joao.filho@example.com");
        updatedCustomerRequest.setPhone("987654321");
        updatedCustomerRequest.setAddress("Rua B, 456");

        // Cliente atualizado esperado
        mockCustomer = new Customer();
        mockCustomer.setId(existingCustomerId);
        mockCustomer.setName(updatedCustomerRequest.getName());
        mockCustomer.setEmail(updatedCustomerRequest.getEmail());
        mockCustomer.setPhone(updatedCustomerRequest.getPhone());
        mockCustomer.setAddress(updatedCustomerRequest.getAddress());

        Mockito.when(customerService.updateCustomer(eq(existingCustomerId), any(RequisicaoEditarCustomer.class)))
                .thenReturn(mockCustomer);

        request = given()
                .contentType("application/json")
                .body(updatedCustomerRequest);
    }

    @Quando("envio uma requisição PUT para {string} com os dados atualizados do cliente")
    public void envio_uma_requisicao_put_para_com_os_dados_atualizados_do_cliente(String path) {
        response = request.when().put(path.replace("{id}", existingCustomerId.toString()));
    }

    @Então("o status da resposta edita cliente deve ser {int}")
    public void o_status_da_resposta_editar_cliente_deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode(), is(statusCode));
    }

    @E("a resposta deve conter os detalhes do cliente atualizado")
    public void a_resposta_deve_conter_os_detalhes_do_cliente_atualizado() {
        final Customer responseCustomer = response.getBody().as(Customer.class);
        assertThat(responseCustomer.getId(), is(mockCustomer.getId()));
        assertThat(responseCustomer.getName(), is(mockCustomer.getName()));
        assertThat(responseCustomer.getEmail(), is(mockCustomer.getEmail()));
        assertThat(responseCustomer.getPhone(), is(mockCustomer.getPhone()));
        assertThat(responseCustomer.getAddress(), is(mockCustomer.getAddress()));
    }

    // Cenário: Deletar um cliente existente
    @Dado("que tenho o id de um cliente para deletar")
    public void que_tenho_o_id_de_um_cliente_para_deletar() {
        existingCustomerId = 1L;

        // Configurar o mock para o método deleteCustomer
        doNothing().when(customerService).deleteCustomer(existingCustomerId);
    }

    @Quando("envio uma requisição DELETE para {string} com o id do cliente")
    public void envio_uma_requisicao_delete_para_com_o_id_do_cliente(String path) {
        response = given()
                .contentType("application/json")
                .when()
                .delete(path.replace("{id}", existingCustomerId.toString()));
    }

    @Então("o status da resposta delete cliente deve ser {int}")
    public void o_status_da_resposta_delete_cliente_deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode(), is(statusCode));
    }

    @E("o cliente deve ser deletado com sucesso")
    public void o_cliente_deve_ser_deletado_com_sucesso() {
        // Verificar que o método deleteCustomer foi chamado com o ID correto
        Mockito.verify(customerService).deleteCustomer(existingCustomerId);
    }
}
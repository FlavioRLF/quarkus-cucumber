package com.dev.customer;

import com.dev.customer.dtos.requisicao.RequisicaoCriaCustomer;
import com.dev.customer.dtos.requisicao.RequisicaoEditarCustomer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public Customer createCustomer(final RequisicaoCriaCustomer requisition) {
        final Customer customer = new Customer();

        customer.setName(requisition.getName());
        customer.setEmail(requisition.getEmail());
        customer.setPhone(requisition.getPhone());
        customer.setAddress(requisition.getAddress());

        customer.persist();

        return customer;
    }

    public Customer getCustomerById(final Long id) {
        return this.customerRepository.findCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Consumer not found with id: " + id));
    }

    public List<Customer> getAllCustomer() {
        return this.customerRepository.listAll();
    }

    @Transactional
    public Customer updateCustomer(final Long id, final RequisicaoEditarCustomer newCustomer) {

        final Customer customer = this.customerRepository.findById(id);

        verificaDadosIguais(customer, newCustomer);

        if (newCustomer.getName() != null && !newCustomer.getName().isEmpty()) {
            customer.setName(newCustomer.getName());
        }

        if (newCustomer.getEmail() != null && !newCustomer.getEmail().isEmpty()) {
            customer.setEmail(newCustomer.getEmail());
        }

        if (newCustomer.getPhone() != null && !newCustomer.getPhone().isEmpty()) {
            customer.setPhone(newCustomer.getPhone());
        }

        if (newCustomer.getAddress() != null && !newCustomer.getAddress().isEmpty()) {
            customer.setAddress(newCustomer.getAddress());
        }

        return customer;
    }

    @Transactional
    public void deleteCustomer(final Long id) {
        final Customer customer = this.customerRepository.findById(id);
        this.customerRepository.delete(customer);
    }


    private void verificaDadosIguais(final Customer customer, final RequisicaoEditarCustomer newCustomer) {
        boolean noChanges =
                (newCustomer.getName() != null && customer.getName().equals(newCustomer.getName())) &&
                        (newCustomer.getEmail() != null && customer.getEmail().equals(newCustomer.getEmail())) &&
                        (newCustomer.getAddress() != null && customer.getAddress().equals(newCustomer.getAddress())) &&
                        (newCustomer.getPhone() != null && customer.getPhone().equals(newCustomer.getPhone()));

        if (noChanges) {
            throw new RuntimeException("No changes detected: the provided data is identical to the current data.");
        }
    }
}

package com.dev.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import org.mockito.Mockito;

import javax.annotation.Priority;

@Alternative
@Priority(1)
@ApplicationScoped
public class TestMockProducer {

    @Produces
    @Singleton
    public CustomerService produceCustomerServiceMock() {
        return Mockito.mock(CustomerService.class);
    }
}
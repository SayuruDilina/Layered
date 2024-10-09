package controller.customer;

import javafx.collections.ObservableList;
import dto.Customer;

public interface customerService {
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAll();
    boolean updateCustomer(Customer customer);
    public ObservableList<String> getCustomerIds();
    Customer searchCustomer(String id);
}

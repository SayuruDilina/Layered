package service.custom;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import dto.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean deleteCustomer(String id);
    ObservableList<CustomerEntity> getAll();
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    ObservableList<String> getCustomerIds();
}

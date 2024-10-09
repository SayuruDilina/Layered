package service.custom.Impl;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("Service Lazyer:"+customer);
        CustomerEntity entity=new ModelMapper().map(customer,CustomerEntity.class);

        CustomerDao customerDao= DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        customerDao.save(entity);
        System.out.println("Service Lazyer:"+customer);
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        CustomerDao customerDao=DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        customerDao.delete(id);

        return  false;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        CustomerDao customerDao=DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        ObservableList<CustomerEntity> all = customerDao.getAll();
        System.out.println(all);

        return all;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        return null;
    }
}

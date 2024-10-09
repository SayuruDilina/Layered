package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VIewCustomerController implements customerService{
    public static VIewCustomerController instance;
    private VIewCustomerController(){}

    public static VIewCustomerController getInstance(){
        return instance == null ? instance = new VIewCustomerController() : instance;
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL="DELETE FROM customer WHERE CustID='" +id+"'";
        try {
            return CrudUtil.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customer";
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()){
                          customerObservableList.add(new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")

                ));

           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObservableList;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public ObservableList<String> getCustomerIds(){
        ObservableList<String> customerIds=FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList=getAll();
        customerObservableList.forEach(customer->{
            customerIds.add(customer.getId());
        });
        return  customerIds;
    }

    @Override
    public Customer searchCustomer(String id) {
        String SQL="SELECT * FROM Customer WHERE CustID=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL,id);
            while (resultSet.next()) {
             return new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}

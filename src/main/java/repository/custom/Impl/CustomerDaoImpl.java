package repository.custom.Impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import org.hibernate.Session;
import repository.custom.CustomerDao;
import util.CrudUtil;
import util.HibernateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        System.out.println("Repository:"+customer);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        String SQL="DELETE FROM customer WHERE CustID='" +id+"'";
        try {
            return CrudUtil.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        ObservableList<CustomerEntity> customerObservableList = FXCollections.observableArrayList();
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        List<CustomerEntity> customerEntities=session.createQuery("from CustomerEntity",CustomerEntity.class).list();
        customerObservableList.addAll(customerEntities);
        session.getTransaction().commit();
        session.close();

//
//        try {
//            String sql = "SELECT * FROM customerentity";
//            ResultSet resultSet = CrudUtil.execute(sql);
//            while (resultSet.next()){
//                customerObservableList.add(new CustomerEntity(
//                        resultSet.getString("id"),
//                        resultSet.getString("title"),
//                        resultSet.getString("name"),
//                        resultSet.getDate("dob").toLocalDate(),
//                        resultSet.getDouble("salary"),
//                        resultSet.getString("address"),
//                        resultSet.getString("city"),
//                        resultSet.getString("province"),
//                        resultSet.getString("postalCode")
//
//                ));
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return customerObservableList;
    }

    @Override
    public boolean update(CustomerEntity customer) {
        return false;
    }

    @Override
    public CustomerEntity search(String id) {
        return null;
    }
}

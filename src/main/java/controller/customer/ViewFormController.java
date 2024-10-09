package controller.customer;

import com.jfoenix.controls.JFXTextField;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.Customer;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewFormController implements Initializable {

    customerService service=VIewCustomerController.getInstance();
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;

    public TableColumn colSalary;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public DatePicker dateDob;
    public ComboBox<String> cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerEntity> tblCustomers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Mrs");
        cmbTitle.setItems(titles);

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
           if (newValue!=null) {
               setTextToValues(newValue);
           }
        }));
    }
    private void setTextToValues(CustomerEntity newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtProvince.setText(newValue.getProvince());
        txtCity.setText(newValue.getCity());
        cmbTitle.setValue(newValue.getTitle());
        dateDob.setValue(newValue.getDob());
        txtPostalCode.setText(newValue.getPostalCode());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));


        CustomerService service1=ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
      //  service1.getAll();
        tblCustomers.setItems(service1.getAll());
    }
    public void btnAddOnAction(ActionEvent actionEvent) {
      CustomerService serviceType = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
        Customer customer = new Customer(txtId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                txtAddress.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
      serviceType.addCustomer(customer);
   //   service.addCustomer(customer);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        CustomerService serviceType = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
      // service.deleteCustomer(txtId.getText());
        serviceType.deleteCustomer(txtId.getText());

    }
}

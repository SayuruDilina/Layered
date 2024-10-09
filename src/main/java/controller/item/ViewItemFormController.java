package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewItemFormController implements Initializable {


    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblCustomers;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtId;
    

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item=new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
              Integer.parseInt(txtQtyOnHand.getText())
        );
        if (ViewItemController.getInstance().addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Added !!").show();

        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added :(").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ViewItemController.getInstance().deleteItem(txtItemCode.getText());

    }

    @FXML
    //--------------------changes will happen here --------------------------------//
    void btnReloadOnAction(ActionEvent event) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblCustomers.setItems(ViewItemController.getInstance().getAll());


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item=new Item(
        txtItemCode.getText(),
        txtDescription.getText(),
        txtPackSize.getText(),
        Double.parseDouble(txtUnitPrice.getText()) ,
         Integer.parseInt(txtQtyOnHand.getText())

);
        ViewItemController.getInstance().updateItem(item);


    }
public void setTextToValues(Item newValue){
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        txtUnitPrice.setText(newValue.getUnitPrice().toString());
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue,oldValue,newValue) ->{
            if (newValue != null) {
                setTextToValues(newValue);
            }
        } ));
    }
}

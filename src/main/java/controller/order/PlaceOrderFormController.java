package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.VIewCustomerController;
import controller.item.ViewItemController;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import dto.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {



    public Label lblTotal;
    public TableColumn colDelete;
    public JFXTextField txtOrderId;
    @FXML
    private JFXComboBox<String> cmbCustomerID;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> coldescription;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOid;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tvblViewPlaceOrder;

    @FXML
    private JFXTextField txtCustAddress;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;

    ObservableList<CartTM> cartTMS=FXCollections.observableArrayList();
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
    colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
    coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    colDelete.setCellValueFactory(new PropertyValueFactory<>("deletebtn"));


        String itemCode = cmbItemCode.getValue();
        String description = txtDescription.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double total = unitPrice*qty;

        if (Integer.parseInt(txtStock.getText())<qty){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
        }else{
            cartTMS.add(new CartTM(itemCode,description,qty,unitPrice,total,new javafx.scene.control.Button("Delete")));
            calNetTotal();
        }


       tvblViewPlaceOrder.setItems(cartTMS);
    }


    @FXML
    void btnPlaceOrderOnACtion(ActionEvent event) {
        String orderId = txtOrderId.getText();
        LocalDate orderDate = LocalDate.now();
        String custId = cmbCustomerID.getValue();
        List<OrderDetail> orderDetails=new ArrayList<>();
        cartTMS.forEach(obj->{
                    orderDetails.add(new OrderDetail(orderId,obj.getItemCode(), obj.getQty(), 0));
        });
        Order order = new Order(orderId, orderDate, custId, orderDetails);
//        System.out.println(order);
        new PlaceOrderController().placeorder(order);

    }
    private void calNetTotal(){

        Double netTot=0.0;
       for(CartTM cartTM: cartTMS){
         netTot+=cartTM.getTotal();
           lblTotal.setText(netTot.toString());

       }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadDateAndTime();
        loadCustomerIds();
        loadItemIds();
        tvblViewPlaceOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, cartTM, deleteValue) -> {
            if(deleteValue!=null){
                deleteItem(deleteValue);
            }
        });
        tvblViewPlaceOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, cartTM, newValue) -> {
            if(newValue !=null){
                updateItem(newValue);
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) ->{
            if(newValue!=null){
                searchItem(newValue);
            }
        } );
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchCustomer(newVal);
            }
        });
    }

    private void deleteItem(CartTM deleteValue) {
         javafx.scene.control.Button deletebtn=deleteValue.getDeletebtn();
        deletebtn.setOnAction(event-> {
            System.out.println("clicked");
            cartTMS.remove(deleteValue);
            tvblViewPlaceOrder.refresh();
            calNetTotal();
        });
    }

    private void updateItem(CartTM newValue) {
        txtQty.setText(newValue.getQty().toString());
        cmbItemCode.setValue(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(newValue.getUnitPrice().toString());

    }

    private void searchCustomer(String customerID) {
        Customer customer=VIewCustomerController.getInstance().searchCustomer(customerID);
        txtCustName.setText(customer.getName());
        txtCustAddress.setText(customer.getAddress());
    }
    private void searchItem(String itemId){
        Item item=ViewItemController.getInstance().searchItems(itemId);
       txtUnitPrice.setText(item.getUnitPrice().toString());
       txtDescription.setText(item.getDescription());
       txtStock.setText(item.getQtyOnHand().toString());
    }

    public void loadDateAndTime(){
        Date date=new Date();
       SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
       String dateNow=f.format(date);
       lblDate.setText(dateNow);

       //-------------------------
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO,e->{
            LocalTime now=LocalTime.now();
            lblTime.setText(now.getHour()+" : "+now.getMinute()+" : "+ now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerIds(){
        cmbCustomerID.setItems(VIewCustomerController.getInstance().getCustomerIds());
    }
    private void loadItemIds(){
        cmbItemCode.setItems(ViewItemController.getInstance().getItemIdss());
    }

    public void btnUpdateCartOnAction(ActionEvent actionEvent) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        String itemCode = cmbItemCode.getValue();
        String description = txtDescription.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double total = unitPrice*qty;

      for(CartTM cartTm:cartTMS){
          if(cartTm.getItemCode()==itemCode){
             cartTm.setDescription(description);
             cartTm.setQty(qty);
             cartTm.setTotal(total);
             tvblViewPlaceOrder.refresh();
              calNetTotal();
          }
      }


    }

    public void btnCommitOnACtion(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

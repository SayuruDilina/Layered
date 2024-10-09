package controller.order;

import controller.item.ViewItemController;
import db.DBConnection;
import javafx.scene.control.Alert;
import dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlaceOrderController {

    public boolean placeorder(Order order){

        try {
            String SQL="INSERT INTO orders VALUES(?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement psTm=connection.prepareStatement(SQL);
            psTm.setObject(1,order.getOrderId());
            psTm.setObject(2,order.getOrderDate());
            psTm.setObject(3,order.getCustId());
            boolean isAdd = psTm.executeUpdate()>0;
            if(isAdd){
                boolean isOrderDetailsAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                if(isOrderDetailsAdd){
                boolean isUpdateStock= ViewItemController.getInstance().updateStock(order.getOrderDetails());
                    if(isUpdateStock){
                        new Alert(Alert.AlertType.INFORMATION,"order Placed!!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Order Not Placed!!").show();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

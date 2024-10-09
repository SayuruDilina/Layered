package controller.item;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetail;
import util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ViewItemController implements itemService {
private static ViewItemController instance;
private ViewItemController(){}

    public static ViewItemController getInstance() {
        return null==instance?new ViewItemController():instance;
    }

    @Override
    public boolean addItem(Item item) {
        try {
            String SQL="INSERT INTO item VALUES (?,?,?,?,?)";

        return  CrudUtil.execute(SQL,
            item.getItemCode(),
            item.getDescription(),
            item.getPackSize(),
            item.getUnitPrice(),
            item.getQtyOnHand()
                );
             } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean deleteItem(String itemCode) {
        try {
            String sql   = " DELETE FROM item WHERE ItemCode='" +itemCode+"'";
          return   CrudUtil.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        try {
            String sql="SELECT * FROM item";
          ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()){
                itemList.add(new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                ));
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  itemList;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            String sql = "UPDATE item SET Description=? , PackSize=? ,UnitPrice=?,QtyOnHand=? WHERE ItemCode = ?";
        return CrudUtil.execute(sql,
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getItemCode()
                );

              } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public ObservableList<String> getItemIdss(){
        ObservableList<Item> itemObservableList=getAll();
        ObservableList<String> itemIds=FXCollections.observableArrayList();
        itemObservableList.forEach(item -> {
            itemIds.add(item.getItemCode());
        });
     return itemIds;
    }
    public Item  searchItems(String id){
    String SQL="SELECT *  FROM item WHERE ItemCode=? ";
        try {
        ResultSet resultSet=CrudUtil.execute(SQL,id);
            while (resultSet.next()){
              return   new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
@Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail: orderDetails){
            boolean updateStock = updateStock(orderDetail);
            if (!updateStock){
                return false;
            }
        }
        return true;
    }
    public boolean updateStock(OrderDetail orderDetails) {
        String SQL="UPDATE Item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?";
        try {
            return CrudUtil.execute(SQL,orderDetails.getOrderQty(),orderDetails.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

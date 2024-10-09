package controller.item;

import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetail;

import java.util.List;

public interface itemService {
    boolean addItem(Item item);
    boolean deleteItem(String itemCode);
    ObservableList<Item> getAll();
    boolean updateItem(Item item);
    public ObservableList<String> getItemIdss();
    public boolean updateStock(List<OrderDetail> orderDetails);
    }

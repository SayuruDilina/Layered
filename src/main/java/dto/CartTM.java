package dto;

import javafx.scene.control.Button;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor

@Data
@ToString
public class CartTM {
    private  String itemCode;
    private String description;
    private Integer qty;
    private Double unitPrice;
    private   Double total;

    public CartTM(String itemCode, String description, Integer qty, Double unitPrice, Double total, Button deletebtn) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.deletebtn = deletebtn;
        this.deletebtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    private javafx.scene.control.Button deletebtn;

}

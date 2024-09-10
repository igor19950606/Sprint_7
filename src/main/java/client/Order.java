package client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
}
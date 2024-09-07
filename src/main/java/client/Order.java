package client;


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

        public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment,  String[] color) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.comment = comment;
            this.color = color;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAddress() {
            return address;
        }

        public int getMetroStation() {
            return metroStation;
        }

        public String getPhone() {
            return phone;
        }

        public int getRentTime() {
            return rentTime;
        }

        public String getComment() {
            return comment;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public String[] getColor() {
            return color;
        }
    }

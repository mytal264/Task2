public class Property {
    private final City CITY_OF_PROPERTY;
    private final String STREET_OF_PROPERTY;
    private final int ROOMS;
    private final int PRICE;
    private final String TYPE_OF_PROPERTY;
    private final boolean IS_FOR_RENT;
    private final int PROPERTY_NUMBER;
    private final int FLOOR;
    private final User USER;
    public Property(City cityOfProperty, String streetOfProperty, int rooms, int price, String typeOfProperty,
                    boolean isForRent, int propertyNumber, int floor, User user) {
        this.CITY_OF_PROPERTY = cityOfProperty;
        this.STREET_OF_PROPERTY = streetOfProperty;
        this.ROOMS = rooms;
        this.PRICE = price;
        this.TYPE_OF_PROPERTY = typeOfProperty;
        this.IS_FOR_RENT = isForRent;
        this.PROPERTY_NUMBER = propertyNumber;
        this.FLOOR = floor;
        this.USER = user;
    }
    @Override
    public String toString() { // O(1)
        return CITY_OF_PROPERTY.getCityName() + " - " + STREET_OF_PROPERTY + " "+PROPERTY_NUMBER+".\n" +
                TYPE_OF_PROPERTY + (IS_FOR_RENT ? " - for rent: " : " - for sale: ") + ROOMS + " rooms" +
                (TYPE_OF_PROPERTY.equals("Regular apartment") || TYPE_OF_PROPERTY.equals("Penthouse") ?
                        ", floor " + FLOOR : "") + ".\nPrice: "+ PRICE +"$.\nContact info: "+ USER;
    }
    public boolean equals (Property other){
        if (other!=null){
            return this.CITY_OF_PROPERTY.equals(other.CITY_OF_PROPERTY) &&
                    this.STREET_OF_PROPERTY.equals(other.STREET_OF_PROPERTY) &&
                    this.ROOMS==other.ROOMS && this.PRICE== other.PRICE &&
                    this.TYPE_OF_PROPERTY.equals(other.TYPE_OF_PROPERTY) && this.IS_FOR_RENT==other.IS_FOR_RENT &&
                    this.PROPERTY_NUMBER== other.PROPERTY_NUMBER && this.FLOOR==other.FLOOR &&
                    this.USER.equals(other.USER);
        } else return false;
    }
    public int getRooms() { // O(1)
        return ROOMS;
    }
    public int getPrice() { // O(1)
        return PRICE;
    }
    public String getTypeOfProperty() { // O(1)
        return TYPE_OF_PROPERTY;
    }
    public boolean isForRent() { // O(1)
        return IS_FOR_RENT;
    }
    public User getUser(){ // O(1)
        return USER;
    }
}
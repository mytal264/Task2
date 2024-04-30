import java.util.Scanner;

public class RealEstate {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int STRONG_PASSWORD_LENGTH = 5;
    private static final int PHONE_NUMBER_LENGTH = 10;
    private static final String START_OF_PHONE_NUMBER = "05";
    private static final int BROKER = 1;
    private static final int REGULAR_USER = 2;
    private static final int LIMIT_POSTS_REGULAR_USER = 2;
    private static final int LIMIT_POSTS_BROKER = 5;
    private static final int REGULAR_APARTMENT = 1;
    private static final int PENTHOUSE = 2;
    private static final int PRIVATE_HOUSE = 3;
    private static final int IGNORE = -999;
    private static final int FOR_RENT = 1;
    private static final int FOR_SALE = 2;
    private User[] users = new User[0];
    private Property[] properties = new Property[0];
    private final City[] CITIES = new City[10];
    public RealEstate() {
        int j = 0;
        for (int i = 0; i < CITIES.length; i += 2) {
            CITIES[i] = new City(j, 0);
            CITIES[i + 1] = new City(j, 1);
            j++;
        }
    }
    public int mainMenu() { // O(1)
        System.out.println("""
                \nWhat are you interested to do: (please enter by number)
                1. Create an account
                2. Log in
                3. Exit""");
        return SCANNER.nextInt();
    }
    private String getAvailableUserName() { // O(n)
        SCANNER.nextLine();
        String userName;
        boolean isUserNameTaken;
        do {
            isUserNameTaken=false;
            System.out.println("\nEnter a username");
            userName = SCANNER.nextLine();
            for (User user : users) {
                if (user.getUserName().equals(userName)) {
                    System.out.println("The username is taken, please enter a new one.");
                    isUserNameTaken=true;
                    break;
                }
            }
        } while (isUserNameTaken);
        return userName;
    }
    private String getStrongPassword() { // O(1)
        String password;
        boolean isStrongPassword=false;
        do {
            System.out.println("""
                    \nEnter a strong password:
                    At least 5 characters
                    Must contains at least one digit
                    Must contains $ or % or _""");
            password = SCANNER.nextLine();
            boolean isContainDigit = password.matches(".*\\d.*");
            if (password.length() >= STRONG_PASSWORD_LENGTH && isContainDigit && (password.contains("$") ||
                    password.contains("%") || password.contains("_"))) {
                isStrongPassword=true;
            } else {
                System.out.println("The password isn't strong, please enter a new one.");
            }
        } while (!isStrongPassword);
        return password;
    }
    private String getPhoneNumberValid() { // O(1)
        String phoneNumber;
        boolean isPhoneNumberValid=false;
        do {
            System.out.println("\nEnter your phone number");
            phoneNumber = SCANNER.nextLine();
            boolean isNumeric = phoneNumber.matches("\\d+");
            if (phoneNumber.length() == PHONE_NUMBER_LENGTH && phoneNumber.startsWith(START_OF_PHONE_NUMBER)
                    && isNumeric) {
                isPhoneNumberValid=true;
            } else {
                System.out.println("The phone number isn't valid, please enter again.");
            }
        } while (!isPhoneNumberValid);
        return phoneNumber;
    }
    private boolean isBroker() { // O(1)
        int userInput;
        boolean isBroker = false;
        do {
            System.out.println("""
                    \nAre you a: (please enter by number)
                    1. Real estate broker
                    2. Regular user""");
            userInput = SCANNER.nextInt();
            SCANNER.nextLine();
            if (userInput == BROKER) {
                isBroker = true;
            } else if (userInput != REGULAR_USER) {
                System.out.println("Invalid input.");
            }
        } while (userInput != BROKER && userInput != REGULAR_USER);
        return isBroker;
    }
    private void addUser(User User) { // O(n)
        User[] temp = new User[users.length + 1];
        System.arraycopy(users, 0, temp, 0, users.length);
        temp[users.length] = User;
        users = temp;
    }
    public void createUser() { // O(n)
        User user = new User(getAvailableUserName(), getStrongPassword(), getPhoneNumberValid(), isBroker());
        addUser(user);
    }
    private User checkLoginInfo (String userName, String password){ // O(n)
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPASSWORD().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public User login() { // O(n)
        SCANNER.nextLine();
        System.out.println("\nEnter your username");
        String userName = SCANNER.nextLine();
        System.out.println("\nEnter your password");
        String password = SCANNER.nextLine();
        return checkLoginInfo(userName,password);
    }
    public int secondaryMenu() { // O(1)
        System.out.println("""
                \nWhat are you interested to do: (please enter by number)
                1. Post a new property
                2. Remove advertising on a property
                3. View all properties in the system
                4. View all properties published by you
                5. Search for a property by parameters
                6. Log out and return to the main menu""");
        return SCANNER.nextInt();
    }
    private int userPosts(User user) { // O(n)
        int count = 0;
        for (Property property : properties) {
            if (property.getUser().equals(user)) {
                count++;
            }
        }
        return count;
    }
    private boolean isAllowedToPublish(User user) { // O(n)
        int count = userPosts(user);
        if ((user.isBroker() && count==LIMIT_POSTS_BROKER) || (!user.isBroker() && count==LIMIT_POSTS_REGULAR_USER)){
            System.out.println("You've reached your limit of publications");
            return false;
        }
        return true;
    }
    private City citySelection() { // O(n)
        System.out.println("\nIn which city is the property located? (please enter by name)");
        for (int i = 0; i < CITIES.length; i++) {
            System.out.println(i + 1 + ". " + CITIES[i]);
        }
        String userInput = SCANNER.nextLine();
        for (City city : CITIES) {
            if (city.getCityName().equalsIgnoreCase(userInput)) {
                return city;
            }
        }
        System.out.println("The city you entered doesn't appear in the list.");
        return null;
    }
    private String streetSelection(City city) { // O(n)
        String[] streets = city.getStreets();
        System.out.println("\nIn which street is the property located? (please enter by name)");
        city.printStreets();
        String userInput = SCANNER.nextLine();
        for (String street : streets) {
            if (street.equalsIgnoreCase(userInput)) {
                return street;
            }
        }
        System.out.println("The street you entered doesn't appear in the list");
        return null;
    }
    private String typeOfPropertySelection() { // O(1)
        System.out.println("""
                \nWhat type of property? (please enter by number)
                1. A regular apartment in an apartment building
                2. Penthouse apartment in an apartment building
                3. Private house""");
        int userInput = SCANNER.nextInt();
        switch (userInput){
            case REGULAR_APARTMENT -> { return "Regular apartment"; }
            case PENTHOUSE -> { return "Penthouse"; }
            case PRIVATE_HOUSE -> { return "Private house"; }
            case IGNORE -> { return ""; }
            default -> {
                System.out.println("Invalid input");
                return null;
            }
        }
    }
    private int floorNumber(String typeOfProperty) { // O(1)
        SCANNER.nextLine();
        int floor=0;
        if (!typeOfProperty.equals("Private house")) {
            do {
                System.out.println("\nWhat floor is the property on?");
                floor = SCANNER.nextInt();
            } while (floor<0);
        }
        return floor;
    }
    private int numberOfRooms (int option){ // O(1)
        int rooms;
        do {
            System.out.println("\nHow many rooms are " + (option == Main.SEARCH ? "desired?" :
                    "there in the property?"));
            rooms = SCANNER.nextInt();
        } while ((option==Main.NEW_POST && rooms<1) || (option==Main.SEARCH && rooms<1 && rooms!=IGNORE));
        return rooms;
    }
    private int rentOrSale (int option){ // O(1)
        int rentOrSale;
        do {
            System.out.println((option == Main.SEARCH ? "\nAre you looking" : "\nIs the") +
                    " property 1 - for rent or 2 - for sale?");
            rentOrSale = SCANNER.nextInt();
        } while ((option==Main.NEW_POST && rentOrSale!=FOR_RENT && rentOrSale!=FOR_SALE) ||
                (option==Main.SEARCH && rentOrSale!=FOR_RENT && rentOrSale!=FOR_SALE && rentOrSale!=IGNORE));
        return rentOrSale;
    }
    private Property createProperty(User user){ // O(n)
        SCANNER.nextLine();
        boolean isForRent;
        int price,propertyNumber;
        if (isAllowedToPublish(user)) {
            City cityOfProperty = citySelection();
            if (cityOfProperty != null) {
                String streetOfProperty = streetSelection(cityOfProperty);
                if (streetOfProperty != null) {
                    String typeOfProperty = typeOfPropertySelection();
                    if (typeOfProperty != null && !typeOfProperty.isEmpty()) {
                        int floor = floorNumber(typeOfProperty);
                        int rooms = numberOfRooms(Main.NEW_POST);
                        do {
                            System.out.println("\nWhat is the property number?");
                            propertyNumber=SCANNER.nextInt();
                        } while (propertyNumber<1);
                        switch (rentOrSale(Main.NEW_POST)) {
                            case FOR_RENT -> isForRent = true;
                            case FOR_SALE -> isForRent = false;
                            default -> { return null; }
                        }
                        do {
                            System.out.println("\nWhat is the required price for the property?");
                            price = SCANNER.nextInt();
                        } while (price < 0);
                        return new Property(cityOfProperty, streetOfProperty, rooms, price, typeOfProperty,
                                isForRent, propertyNumber, floor, user);
                    }
                }
            }
        }
        return null;
    }
    private void addProperty(Property property) { // O(n)
        Property[] temp = new Property[properties.length + 1];
        System.arraycopy(properties, 0, temp, 0, properties.length);
        temp[properties.length] = property;
        properties = temp;
    }
    public boolean postNewProperty(User user) { // O(n)
        Property property = createProperty(user);
        if (property!=null){
            addProperty(property);
            return true;
        }
        return false;
    }
    private Property[] userProperties(User user) { // O(n)
        Property[] userProperties = new Property[userPosts(user)];
        if (userProperties.length>0) {
            int i = 0;
            for (Property property : properties) {
                if (property.getUser().equals(user)) {
                    userProperties[i] = property;
                    i++;
                }
                if (i == userProperties.length) break;
            }
        } else System.out.println("\nYou haven't published any properties yet.");
        return userProperties;
    }
    private Property removePropertySelection (User user){ // O(n)
        Property[] userProperties = userProperties(user);
        if (userProperties.length > 0){
            System.out.println("\nWhich property do you want to remove? (please enter by number)");
            printProperties(user);
            int userInput = SCANNER.nextInt();
            if (userInput>0 && userInput<=userProperties.length) {
                return userProperties[userInput-1];
            } else {
                System.out.println("\nThere is no property numbered with this number.");
                return null;
            }
        }
        return null;
    }
    public void removeProperty(User user) { // O(n)
        Property selectedProperty = removePropertySelection(user);
        if (selectedProperty!=null){
            Property[] temp = new Property[properties.length-1];
            int i=0;
            for (Property property : properties){
                if (i == temp.length) break;
                if (!property.equals(selectedProperty)){
                    temp[i]=property;
                    i++;
                }
            }
            properties=temp;
            System.out.println("\nRemoval completed successfully.");
        }
    }
    public void printAllProperties() { // O(n)
        if (properties.length>0){
            System.out.println("\nList of all properties:");
            for (int i = 0; i < properties.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + properties[i]);
            }
        } else {
            System.out.println("\nThere are no properties published in the system yet.");
        }
    }
    public void printProperties(User user) { // O(n)
        Property[] userProperties = userProperties(user);
        if (userProperties.length > 0) {
            System.out.println("\nThe properties you have published:");
            for (int i = 0; i < userProperties.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + userProperties[i]);
            }
        }
    }
    private boolean isSuitableProperty(Property property, int rentOrSale, String typeOfProperty, int rooms,
                                       int minPriceRange, int maxPriceRange) { // O(1)
        if (rentOrSale != IGNORE && ((rentOrSale == FOR_RENT && !property.isForRent()) ||
                (rentOrSale == FOR_SALE && property.isForRent()))) {
            return false;
        }
        if (!typeOfProperty.isEmpty() && !property.getTypeOfProperty().equals(typeOfProperty)) {
            return false;
        }
        if (rooms != IGNORE && property.getRooms() != rooms) {
            return false;
        }
        return (minPriceRange == IGNORE || property.getPrice() >= minPriceRange) &&
                (maxPriceRange == IGNORE || property.getPrice() <= maxPriceRange);
    }
    private Property[] propertiesSearchResults(int rentOrSale, String typeOfProperty, int rooms,
                                               int minPriceRange, int maxPriceRange){ // O(n)
        Property[] propertiesSearchResults = new Property[0];
        for (Property property : properties) {
            if (isSuitableProperty(property,rentOrSale,typeOfProperty,rooms,minPriceRange,maxPriceRange)) {
                Property[] temp = new Property[propertiesSearchResults.length + 1];
                System.arraycopy(propertiesSearchResults, 0, temp, 0, propertiesSearchResults.length);
                temp[propertiesSearchResults.length] = property;
                propertiesSearchResults = temp;
            }
        }
        return propertiesSearchResults;
    }
    public Property[] search() { // O(n)
        int rooms,minPriceRange,maxPriceRange;
        String typeOfProperty;
        System.out.println("\nIn any parameter that is not critical for you, you can enter: -999");
        int rentOrSale = rentOrSale(Main.SEARCH);
        do {
            typeOfProperty = typeOfPropertySelection();
        } while (typeOfProperty==null);
        rooms = numberOfRooms(Main.SEARCH);
        System.out.println("\nWhat is the desired price range?\n(Enter the minimum first, then the maximum)");
        minPriceRange = SCANNER.nextInt();
        maxPriceRange = SCANNER.nextInt();
        return propertiesSearchResults(rentOrSale,typeOfProperty,rooms,minPriceRange,maxPriceRange);
    }
}
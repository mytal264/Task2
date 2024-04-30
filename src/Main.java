public class Main {
    private static final int SIGN_UP = 1;
    private static final int LOG_IN = 2;
    private static final int END = 3;
    public static final int NEW_POST = 1;
    private static final int REMOVE_POST = 2;
    private static final int PRINT_ALL_PROPERTIES = 3;
    private static final int PRINT_USER_PROPERTIES = 4;
    public static final int SEARCH = 5;
    private static final int LOG_OUT = 6;
    public static void main(String[] args) { // O(n)
        RealEstate realEstate=new RealEstate();
        int mainOption,secondaryOption;
        do {
            mainOption=realEstate.mainMenu();
            switch (mainOption){
                case SIGN_UP -> realEstate.createUser();
                case LOG_IN -> {
                    User user=realEstate.login();
                    if (user==null){
                        System.out.println("""
                                \nThe username or password is incorrect,
                                or there is no such user in the system.""");
                    } else {
                        do {
                            secondaryOption=realEstate.secondaryMenu();
                            switch (secondaryOption){
                                case NEW_POST -> {
                                    boolean isSaved = realEstate.postNewProperty(user);
                                    if (isSaved) {
                                        System.out.println("\nThe property has been successfully saved in the system.");
                                    } else {
                                        System.out.println("\nThe property wasn't successfully saved in the system.");
                                    }
                                }
                                case REMOVE_POST -> realEstate.removeProperty(user);
                                case PRINT_ALL_PROPERTIES -> realEstate.printAllProperties();
                                case PRINT_USER_PROPERTIES -> realEstate.printProperties(user);
                                case SEARCH -> {
                                    Property[] propertiesSearchResults=realEstate.search();
                                    if (propertiesSearchResults.length==0){
                                        System.out.println("\nThere is no suitable property for your search.");
                                    } else {
                                        System.out.println("\nThe properties that match your search:");
                                        for (int i = 0; i < propertiesSearchResults.length; i++) {
                                            System.out.println("\n"+(i + 1) + ". " + propertiesSearchResults[i]);
                                        }
                                    }
                                }
                            }
                        } while (secondaryOption!=LOG_OUT);
                    }
                }
            }
        }while (mainOption!=END);
    }
}
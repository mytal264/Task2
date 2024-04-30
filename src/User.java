public class User {
    private final String USER_NAME;
    private final String PASSWORD;
    private final String PHONE_NUMBER;
    private final boolean IS_BROKER;
    public User (String userName,String password,String phoneNumber,boolean isBroker){
        this.USER_NAME =userName;
        this.PASSWORD =password;
        this.PHONE_NUMBER =phoneNumber;
        this.IS_BROKER =isBroker;
    }
    public String toString() { // O(1)
        return USER_NAME +" "+ PHONE_NUMBER +" "+ (this.isBroker() ? "(real estate broker)." : "(private person).");
    }
    public boolean equals(User other){ // O(1)
        if (other != null) {
            return this.USER_NAME.equals(other.USER_NAME);
        } return false;
    }
    public String getUserName(){ // O(1)
        return USER_NAME;
    }
    public String getPASSWORD(){ // O(1)
        return PASSWORD;
    }
    public boolean isBroker (){ // O(1)
        return IS_BROKER;
    }
}
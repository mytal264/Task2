public class City {
    private static final String[] DISTRICTS = {"Negev","South","Center","Sharon","North"};
    private final String DISTRICT;
    private static final String[] CITIES_IN_NEGEV = {"Beer Sheva","Dimona"};
    private static final String[] CITIES_IN_SOUTH = {"Ashdod","Ashkelon"};
    private static final String[] CITIES_IN_CENTER = {"Tel Aviv","Holon"};
    private static final String[] CITIES_IN_SHARON = {"Raanana","Natanya"};
    private static final String[] CITIES_IN_NORTH = {"Heifa","Nahariya"};
    private String CITY_NAME;
    private final String[] STREETS = {"Rothschild","Herzl","Bialik"};
    public City (int district,int city){
        this.DISTRICT = DISTRICTS[district];
        switch (district){
            case 0 -> this.CITY_NAME = CITIES_IN_NEGEV[city];
            case 1 -> this.CITY_NAME = CITIES_IN_SOUTH[city];
            case 2 -> this.CITY_NAME = CITIES_IN_CENTER[city];
            case 3 -> this.CITY_NAME = CITIES_IN_SHARON[city];
            case 4 -> this.CITY_NAME = CITIES_IN_NORTH[city];
        }
    }
    public String toString(){ // O(1)
        return CITY_NAME + " in the " + DISTRICT;
    }
    public boolean equals (City other){
        if (other!=null){
            return this.CITY_NAME.equals(other.CITY_NAME);
        } else return false;
    }
    public String getCityName(){ // O(1)
        return CITY_NAME;
    }
    public String[] getStreets(){ // O(1)
        return STREETS;
    }
    public void printStreets(){ // O(n)
        for (int i = 0; i < STREETS.length; i++) {
            System.out.println(i + 1 + ". " + STREETS[i]);
        }
    }
}
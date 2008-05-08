import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CarbohydrateFamily implements Iterable {

    private int carbons;
    private ArrayList<Carbohydrate> carbohydrates;
    private ArrayList< ArrayList<String> > cachedCarbohydrateStrings
        = new ArrayList< ArrayList<String> >();

    public CarbohydrateFamily( int carbons ) {

      this.carbons = carbons;
      this.carbohydrates = possibleCarbohydrates( carbons );
    }

    public int size() {
      return carbohydrates.size();
    }

    public Iterator<Carbohydrate> iterator() {
      return carbohydrates.iterator();
    }

    private ArrayList<Carbohydrate> possibleCarbohydrates(int carbons) {
      ArrayList<Carbohydrate> carbohydrates = new ArrayList<Carbohydrate>();

      for ( String carbohydrateString : possibleCarbohydrateStrings(carbons) ) {
        Carbohydrate newCarbohydrate = new Carbohydrate( carbohydrateString );

        if ( !carbohydrates.contains(newCarbohydrate) )
          carbohydrates.add(newCarbohydrate);
      }

      return carbohydrates;
    }

    private ArrayList<String> possibleCarbohydrateStrings(int carbons) {

      if ( cachedCarbohydrateStrings.size() > carbons )
        return cachedCarbohydrateStrings.get(carbons);

      ArrayList<String> carbohydrateStrings = new ArrayList<String>();

      for ( String alkylString : possibleAlkylStrings(carbons-1) )
        carbohydrateStrings.add( "C(" + alkylString + ")" );

      if ( cachedCarbohydrateStrings.size() == carbons )
        cachedCarbohydrateStrings.add( carbohydrateStrings );

      return carbohydrateStrings;
    }

    private ArrayList<String> possibleAlkylStrings(int carbons) {
      ArrayList<String> alkylStrings = new ArrayList<String>();

      if ( carbons == 0 )
        alkylStrings.add( "" );

      if ( carbons > 0 )
        alkylStrings.addAll( possibleCarbohydrateStrings(carbons) );

      if ( carbons > 1 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( String firstCarbon : possibleCarbohydrateStrings(i) )
            for ( String secondCarbon : possibleCarbohydrateStrings(carbons-i) )
              alkylStrings.add( firstCarbon + secondCarbon );

      if ( carbons > 2 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( int j = 1; j <= (carbons-i)/2; j++ )
            for ( String firstCarbon : possibleCarbohydrateStrings(i) )
              for ( String secondCarbon : possibleCarbohydrateStrings(j) )
                for ( String thirdCarbon : possibleCarbohydrateStrings(carbons-i-j) )
                  alkylStrings.add( firstCarbon + secondCarbon + thirdCarbon );

      return alkylStrings;
    }

    public String toString() {
      return carbohydrates.toString();
    }

    public static void main( String args[] ) {
      for ( int carbons = 0; carbons <= 20; carbons++ )
        System.out.println( new CarbohydrateFamily(carbons).size() );
    }
}

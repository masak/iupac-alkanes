import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class HydrocarbonFamily implements Iterable {

    private int carbons;
    private ArrayList<Hydrocarbon> hydrocarbons;
    private ArrayList< ArrayList<String> > cachedHydrocarbonStrings
        = new ArrayList< ArrayList<String> >();

    public HydrocarbonFamily( int carbons ) {

      this.carbons = carbons;
      this.hydrocarbons = possibleHydrocarbons( carbons );
    }

    public int size() {
      return hydrocarbons.size();
    }

    public Iterator<Hydrocarbon> iterator() {
      return hydrocarbons.iterator();
    }

    private ArrayList<Hydrocarbon> possibleHydrocarbons(int carbons) {
      ArrayList<Hydrocarbon> hydrocarbons = new ArrayList<Hydrocarbon>();

      for ( String hydrocarbonString : possibleHydrocarbonStrings(carbons) ) {
        Hydrocarbon newHydrocarbon
          = Hydrocarbon.fromSmiles( hydrocarbonString );

        if ( !hydrocarbons.contains(newHydrocarbon) )
          hydrocarbons.add(newHydrocarbon);
      }

      return hydrocarbons;
    }

    private ArrayList<String> possibleHydrocarbonStrings(int carbons) {

      if ( cachedHydrocarbonStrings.size() > carbons )
        return cachedHydrocarbonStrings.get(carbons);

      ArrayList<String> hydrocarbonStrings = new ArrayList<String>();

      for ( String alkylString : possibleAlkylStrings(carbons-1) )
        hydrocarbonStrings.add( "C(" + alkylString + ")" );

      if ( cachedHydrocarbonStrings.size() == carbons )
        cachedHydrocarbonStrings.add( hydrocarbonStrings );

      return hydrocarbonStrings;
    }

    private ArrayList<String> possibleAlkylStrings(int carbons) {
      ArrayList<String> alkylStrings = new ArrayList<String>();

      if ( carbons == 0 )
        alkylStrings.add( "" );

      if ( carbons > 0 )
        alkylStrings.addAll( possibleHydrocarbonStrings(carbons) );

      if ( carbons > 1 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( String firstCarbon : possibleHydrocarbonStrings(i) )
            for ( String secondCarbon : possibleHydrocarbonStrings(carbons-i) )
              alkylStrings.add( firstCarbon + secondCarbon );

      if ( carbons > 2 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( int j = 1; j <= (carbons-i)/2; j++ )
            for ( String firstCarbon : possibleHydrocarbonStrings(i) )
              for ( String secondCarbon : possibleHydrocarbonStrings(j) )
                for ( String thirdCarbon : possibleHydrocarbonStrings(carbons-i-j) )
                  alkylStrings.add( firstCarbon + secondCarbon + thirdCarbon );

      return alkylStrings;
    }

    public String toString() {
      return hydrocarbons.toString();
    }

    public static void main( String args[] ) {
      for ( int carbons = 0; carbons <= 20; carbons++ )
        System.out.println( new HydrocarbonFamily(carbons).size() );
    }
}

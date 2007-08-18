import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class AlkaneFamily implements Iterable {

    private int carbons;
    private ArrayList<Alkane> alkanes;
    private ArrayList< ArrayList<String> > cachedAlkaneStrings
        = new ArrayList< ArrayList<String> >();

    public AlkaneFamily( int carbons ) {

      this.carbons = carbons;
      this.alkanes = possibleAlkanes( carbons );
    }

    public int size() {
      return alkanes.size();
    }

    public Iterator<Alkane> iterator() {
      return alkanes.iterator();
    }

    private ArrayList<Alkane> possibleAlkanes(int carbons) {
      ArrayList<Alkane> alkanes = new ArrayList<Alkane>();

      for ( String alkaneString : possibleAlkaneStrings(carbons) ) {
        Alkane newAlkane = new Alkane( alkaneString );

        if ( !alkanes.contains(newAlkane) )
          alkanes.add(newAlkane);
      }

      return alkanes;
    }

    private ArrayList<String> possibleAlkaneStrings(int carbons) {

      if ( cachedAlkaneStrings.size() > carbons )
        return cachedAlkaneStrings.get(carbons);

      ArrayList<String> alkaneStrings = new ArrayList<String>();

      for ( String alkylString : possibleAlkylStrings(carbons-1) )
        alkaneStrings.add( "C(" + alkylString + ")" );

      if ( cachedAlkaneStrings.size() == carbons )
        cachedAlkaneStrings.add( alkaneStrings );

      return alkaneStrings;
    }

    private ArrayList<String> possibleAlkylStrings(int carbons) {
      ArrayList<String> alkylStrings = new ArrayList<String>();

      if ( carbons == 0 )
        alkylStrings.add( "" );

      if ( carbons > 0 )
        alkylStrings.addAll( possibleAlkaneStrings(carbons) );

      if ( carbons > 1 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( String firstCarbon : possibleAlkaneStrings(i) )
            for ( String secondCarbon : possibleAlkaneStrings(carbons-i) )
              alkylStrings.add( firstCarbon + secondCarbon );

      if ( carbons > 2 )
        for ( int i = 1; i <= carbons/2; i++ )
          for ( int j = 1; j <= (carbons-i)/2; j++ )
            for ( String firstCarbon : possibleAlkaneStrings(i) )
              for ( String secondCarbon : possibleAlkaneStrings(j) )
                for ( String thirdCarbon : possibleAlkaneStrings(carbons-i-j) )
                  alkylStrings.add( firstCarbon + secondCarbon + thirdCarbon );

      return alkylStrings;
    }

    public String toString() {
      return alkanes.toString();
    }

    public static void main( String args[] ) {
      for ( int carbons = 0; carbons <= 20; carbons++ )
        System.out.println( new AlkaneFamily(carbons).size() );
    }
}

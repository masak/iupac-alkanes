import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Carbohydrate {

    private String description;
    private List<Carbon> carbons = new ArrayList<Carbon>();

    private Carbohydrate() {}

    public static Carbohydrate fromSmiles( String description ) {
      
      Carbohydrate carbohydrate = new Carbohydrate();

      carbohydrate.description = description;
      carbohydrate.buildFromSmiles(description);

      return carbohydrate;
    }

    private static int findStartParen( String s, int endParenPos ) {
      int currentPos = endParenPos - 1,
          parenLevel = 1;

      while ( parenLevel > 0 ) {

        if ( currentPos < 0 )
          throw new IllegalArgumentException( "Too many )'s: '"
                                              + s + "':" + endParenPos );

        switch ( s.charAt( --currentPos ) ) {
          case ')': parenLevel++; break;
          case '(': parenLevel--; break;
        }
      }

      return currentPos;
    }

    private Carbon buildFromSmiles( String desc ) {
      Carbon carbon = null;

      for (int i = desc.length() - 1; i >= 0; i--) {

        if (carbon == null) {
          carbon = new Carbon();
        }
        else if (desc.charAt(i) == ')') {
          List<Carbon> neighbors = new ArrayList<Carbon>();
          neighbors.add(carbon);
          
          int startParenPos = findStartParen(desc, i);
          String subexpression = desc.substring(startParenPos + 1, i);

          Carbon headCarbon = buildFromSmiles( subexpression );
          neighbors.add(headCarbon);

          i = startParenPos - 1;

          if (desc.charAt(i) == ')') {
            startParenPos = findStartParen(desc, i);
            subexpression = desc.substring(startParenPos + 1, i);

            headCarbon = buildFromSmiles( subexpression );
            neighbors.add(headCarbon);

            i = startParenPos - 1;
          }

          carbon = new Carbon(neighbors);
        }
        else if (desc.charAt(i) == '=') {
          List<Carbon> neighbors = new ArrayList<Carbon>();
          neighbors.add(carbon);

          carbon = new Carbon( neighbors, Bond.Type.DOUBLE );
          i--;
        }
        else {
          List<Carbon> neighbors = new ArrayList<Carbon>();
          neighbors.add(carbon);

          carbon = new Carbon(neighbors);
        }

        carbons.add(carbon);
      }

      return carbon;
    }

    public List<Carbon> carbons() {
      return new ArrayList<Carbon>(carbons);
    }

    public int numberOfCarbons() {
      return carbons.size();
    }

    public boolean hasDoubleBond() {
      for (Carbon carbon : carbons) {
        for (Bond bond : carbon.bonds()) {
          if (bond.type() == Bond.Type.DOUBLE)
            return true;
        }
      }

      return false;
    }

    public String toString() {
      return normalization();
    }

    public String normalization() {
      List<String> descriptions = new ArrayList<String>();

      if ( carbons.size() == 1 )
        return "C";

      for ( Carbon leaf : leaves() )
        descriptions.add( traverse(leaf, new ArrayList<Carbon>()) );

      Collections.sort( descriptions );
      return descriptions.size() > 0 ? descriptions.get(0) : "";
    }

    private static String traverse( Carbon node,
                                    List<Carbon> alreadyTraversed ) {

      List<String> descriptions = new ArrayList<String>();

      alreadyTraversed.add(node);

      for ( Carbon neighbor : node.neighbors() )
        if ( !alreadyTraversed.contains(neighbor) )
           descriptions.add( traverse(neighbor, alreadyTraversed) );

      Collections.sort( descriptions );

      StringBuilder sb = new StringBuilder();
      sb.append('C');

      if ( descriptions.size() > 0 ) {
        sb.append('(');
        for ( String desc : descriptions )
          sb.append( desc );
        sb.append(')');
      }

      return sb.toString();
    }

    public List<Carbon> leaves() {
      List<Carbon> leaves = new ArrayList<Carbon>();

      for ( Carbon carbon : this.carbons )
        if ( carbon.neighbors().size() < 2 )
          leaves.add( carbon );

      return leaves;
    }

    public boolean equals( Object o ) {
      if ( o.getClass() != Carbohydrate.class )
        return false;

      Carbohydrate other = (Carbohydrate)o;
      return normalization().equals( other.normalization() );
    }

    public int hashCode() {
      return normalization().hashCode();
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Alkane {

    private String description;
    private ArrayList<Carbon> carbons = new ArrayList<Carbon>();

    private static String[] iupacNames = null;

    public Alkane( String description ) {
      this.description = description;

      build( description );
    }

    private ArrayList<Carbon> build( String desc ) {
      ArrayList<Carbon> accumCarbons = new ArrayList<Carbon>();

      for ( int pos = 0; pos < desc.length(); pos++ ) {
        if ( desc.charAt( pos ) == 'C' ) {
          Carbon newCarbon;

          if ( pos < desc.length() - 1 && desc.charAt( pos+1 ) == '(' ) {
            int startParenPos = ++pos,
              endParenPos = startParenPos,
              parenLevel = 1;

            while ( parenLevel > 0 ) {
              switch ( desc.charAt( ++endParenPos ) ) {
                case '(': parenLevel++; break;
                case ')': parenLevel--; break;
              }
            }

            newCarbon = new Carbon(
              build(desc.substring(startParenPos+1, endParenPos)) );

            pos = endParenPos;
          }
          else {
            newCarbon = new Carbon();
          }

          carbons.add( newCarbon );
          accumCarbons.add( newCarbon );
        }
        else {
          throw new IllegalArgumentException( "Strange description: '"
                                              + desc + "'" + pos );
        }
      }

      return accumCarbons;
    }

    public String toString() {
      return normalization();
    }

    public String normalization() {
      ArrayList<String> descriptions = new ArrayList<String>();

      if ( carbons.size() == 1 )
        return "C";

      for ( Carbon leaf : leaves() )
        descriptions.add( traverse(leaf, new ArrayList<Carbon>()) );

      Collections.sort( descriptions );
      return descriptions.size() > 0 ? descriptions.get(0) : "";
    }

    private String traverse( Carbon node,
                             ArrayList<Carbon> alreadyTraversed ) {

      ArrayList<String> descriptions = new ArrayList<String>();

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

    private ArrayList<Carbon> leaves() {
      ArrayList<Carbon> leaves = new ArrayList<Carbon>();

      for ( Carbon carbon : this.carbons )
        if ( carbon.neighbors().size() == 1 )
          leaves.add( carbon );

      return leaves;
    }

    public List<Carbon> longestChain() {

      if (carbons.isEmpty())
        return new ArrayList<Carbon>();

      ArrayList<Carbon> longestChain = new ArrayList<Carbon>();

      for ( Carbon leaf : leaves() ) {
        ArrayList< ArrayList<Carbon> > queue
          = new ArrayList< ArrayList<Carbon> >();
        ArrayList<Carbon> traversed = new ArrayList<Carbon>();

        ArrayList<Carbon> seed = new ArrayList<Carbon>();
        seed.add(leaf);
        queue.add(seed);

        int length = 0;
        while ( !queue.isEmpty() ) {

          ArrayList< ArrayList<Carbon> > newChains
            = new ArrayList< ArrayList<Carbon> >();

          for ( ArrayList<Carbon> currentChain : queue ) {

            Carbon currentNode = currentChain.get(currentChain.size() - 1);
            traversed.add(currentNode);

            for ( Carbon neighbor : currentNode.neighbors() ) {
              if ( !traversed.contains(neighbor) ) {
                ArrayList<Carbon> longerChain
                  = new ArrayList<Carbon>(currentChain);

                longerChain.add(neighbor);
                newChains.add(longerChain);
              }
            }
          }

          if ( newChains.isEmpty()
               && longestChain.size() < queue.get(0).size() )
            longestChain = queue.get(0);

          queue = newChains;
        }
      }

      return longestChain;
    }     

    public String iupacName() {
      
      if ( iupacNames == null ) {
        
        String[] lowNames = new String[] {
          "",
          "Methane", "Ethane",  "Propane",
          "Buthane", "Pentane", "Hexane",
          "Heptane", "Octane",  "Nonane",
        };

        ArrayList<String> names = new ArrayList<String>();
        for ( int i = 0; i < lowNames.length; i++ )
          names.add( lowNames[i] );

        String[] suffixes = new String[] {
          "",
          "Decane",       "Cosane",       "Triacontane",
          "Tetracontane", "Pentacontane", "Hexacontane",
          "Heptacontane", "Octacontane",  "Nonacontane",
        };

        String[] prefixes = new String[] {
          "",
          "Hen",   "Do",    "Tri",
          "Tetra", "Penta", "Hexa",
          "Hepta", "Octa",  "Nona",
        };

        for ( int tens = 1; tens <= 9; tens++ ) {
          names.add( suffixes[tens] );
          for ( int ones = 1; ones <= 9; ones++ )
            names.add( prefixes[ones] + suffixes[tens].toLowerCase() );
        }

        // Exceptions
        names.set( 11, "Undecane" );
        names.set( 20, "Eicosane" );
        names.set( 21, "Heneicosane" );
        names.add("Hectane");

        iupacNames = names.toArray( new String[0] );
      }

      return iupacNames[ longestChain().size() ];
    }

    public boolean equals( Object o ) {
      if ( !(o instanceof Alkane) )
        return false;

      Alkane other = (Alkane)o;
      return normalization().equals( other.normalization() );
    }

    public int hashCode() {
      return normalization().hashCode();
    }
}

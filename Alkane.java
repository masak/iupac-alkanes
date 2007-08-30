import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Alkane {

    private String description;
    private List<Carbon> carbons = new ArrayList<Carbon>();

    private static String[] iupacNames = null;
    private static String[] multipliers = new String[] {
      "",
      "",       "Di",    "Tri",  "Tetra", "Penta",
      "Hexa",   "Hepta", "Octa", "Nona",  "Deca",
      "Undeca",
    };
    private static HashMap<String, String> commonAlkylNames = null;

    public Alkane( String description ) {
      this.description = description;

      build( description );
    }

    private List<Carbon> build( String desc ) {
      List<Carbon> accumCarbons = new ArrayList<Carbon>();

      for ( int pos = 0; pos < desc.length(); pos++ ) {
        if ( desc.charAt( pos ) == 'C' ) {
          Carbon newCarbon;

          if ( pos < desc.length() - 1 && desc.charAt( pos+1 ) == '(' ) {
            int startParenPos = ++pos,
              endParenPos = startParenPos,
              parenLevel = 1;

            while ( parenLevel > 0 ) {
              if ( endParenPos >= desc.length()-1 )
                throw new IllegalArgumentException( "Too few )'s: '"
                                                    + desc + "':" + pos );

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
                                              + desc + "':" + pos );
        }
      }

      return accumCarbons;
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

    private String traverse( Carbon node,
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

    private List<Carbon> leaves() {
      List<Carbon> leaves = new ArrayList<Carbon>();

      for ( Carbon carbon : this.carbons )
        if ( carbon.neighbors().size() == 1 )
          leaves.add( carbon );

      return leaves;
    }

    private List<List<Carbon>> extendChains(
      List<List<Carbon>> chains,
      List<Carbon> traversed ) {

      List< List<Carbon> > newChains
        = new ArrayList< List<Carbon> >();

      for ( List<Carbon> currentChain : chains ) {

        Carbon currentNode = currentChain.get(currentChain.size() - 1);
        traversed.add(currentNode);

        for ( Carbon neighbor : currentNode.neighbors() ) {
          if ( !traversed.contains(neighbor) ) {
            List<Carbon> longerChain
              = new ArrayList<Carbon>(currentChain);

            longerChain.add(neighbor);
            newChains.add(longerChain);
          }
        }
      }

      return newChains;
    }

    private List<Carbon> longestChain() {

      if (carbons.isEmpty())
        return new ArrayList<Carbon>();

      List<Carbon> longestChain = new ArrayList<Carbon>();
      longestChain.add( carbons.get(0) );

      for ( Carbon leaf : leaves() ) {
        List< List<Carbon> > queue
          = new ArrayList< List<Carbon> >();
        List<Carbon> traversed = new ArrayList<Carbon>();

        List<Carbon> seed = new ArrayList<Carbon>();
        seed.add(leaf);
        queue.add(seed);

        int length = 0;
        while ( !queue.isEmpty() ) {

          List< List<Carbon> > newChains
            = extendChains(queue, traversed);

          if ( longestChain.size() < queue.get(0).size() )
            longestChain = queue.get(0);

          queue = newChains;
        }
      }

      return longestChain;
    }     

    private List<Carbon> longestChain(
       Carbon trunkCarbon, Carbon branchCarbon) {

      List<Carbon> longestChain = new ArrayList<Carbon>();

      List< List<Carbon> > queue
        = new ArrayList< List<Carbon> >();

      List<Carbon> traversed = new ArrayList<Carbon>();
      traversed.add(trunkCarbon);

      List<Carbon> seed = new ArrayList<Carbon>();
      seed.add(branchCarbon);
      queue.add(seed);

      int length = 0;
      while ( !queue.isEmpty() ) {

        List< List<Carbon> > newChains = extendChains(queue, traversed);

        if ( longestChain.size() < queue.get(0).size() )
          longestChain = queue.get(0);

        queue = newChains;
      }

      return longestChain;
    }

    private List<Carbon> branchCarbons(
      Carbon trunkCarbon,
      Carbon branchCarbon) {

      List<Carbon> queue = new ArrayList<Carbon>();

      List<Carbon> traversed = new ArrayList<Carbon>();
      traversed.add(trunkCarbon);

      queue.add(branchCarbon);

      while ( !queue.isEmpty() ) {

        Carbon current = queue.remove(0);

        for ( Carbon neighbor : current.neighbors() )
          if ( !traversed.contains(neighbor) )
            queue.add(neighbor);

        traversed.add(current);
      }

      traversed.remove(trunkCarbon);
      return traversed;
    }

    private HashMap<String, String> commonAlkylNamesHashMap() {
      HashMap<String, String> names = new HashMap<String, String>();

      String[][] pairs = new String[][] {
        { "1-Methylethyl",      "Isopropyl"   },
        { "2-Methylpropyl",     "Isobutyl"    },
        { "1-Methylpropyl",     "sec-Butyl"   },
        { "1,1-Dimethylethyl",  "tert-Butyl"  },
        { "3-Methylbuthyl",     "Isopentyl"   },
        { "2,2-Dimethylpropyl", "Neopentyl"   },
        { "1,1-Dimethylpropyl", "tert-Pentyl" },
        { "4-Methylpentyl",     "Isohexyl"    },
      };

      for ( String[] pair : pairs )
        names.put(pair[0], pair[1]);

      return names;
    }

    private String alkylGroupName( Carbon trunkCarbon, Carbon branchCarbon ) {

      List<Carbon>   alkylGroup = branchCarbons(trunkCarbon, branchCarbon),
                   longestChain = longestChain(trunkCarbon, branchCarbon);

      if ( alkylGroup.size() == longestChain.size() )
        return iupacNames[ longestChain.size() ] + "yl";

      String prefix = sideChains(longestChain, trunkCarbon);

      if ( commonAlkylNames == null )
        commonAlkylNames = commonAlkylNamesHashMap();

      String  name = prefix
                     + iupacNames[ longestChain.size() ].toLowerCase()
                     + "yl",
        commonName = commonAlkylNames.get(name);

      if ( commonName != null )
        return commonName;

      return "(" + name + ")";
    }

    private String[] iupacNameList() {
        String[] lowNames = new String[] {
          "",
          "Meth", "Eth",  "Prop",
          "Buth", "Pent", "Hex",
          "Hept", "Oct",  "Non",
        };

        List<String> names = new ArrayList<String>();
        for ( int i = 0; i < lowNames.length; i++ )
          names.add( lowNames[i] );

        String[] suffixes = new String[] {
          "",
          "Dec",       "Cos",       "Triacont",
          "Tetracont", "Pentacont", "Hexacont",
          "Heptacont", "Octacont",  "Nonacont",
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
        names.set( 11, "Undec" );
        names.set( 20, "Eicos" );
        names.set( 21, "Heneicos" );
        names.add("Hect");

        return names.toArray( new String[0] );
    }

    private String commaSeparatedList(
      List<Integer> list,
      boolean isReversed,
      int lengthOfLongChain ) {

      StringBuilder sb = new StringBuilder();

      boolean first = true;
      if (isReversed) {
        for ( int i = list.size()-1; i >= 0; i-- ) {
          if ( !first )
            sb.append( ',' );
          first = false;

          sb.append( 1 + lengthOfLongChain - list.get(i) );
        }
      }
      else {
        for ( Integer item : list ) {
          if ( !first )
            sb.append( ',' );
          first = false;

          sb.append(item);
        }
      }

      return sb.toString();
    }

    private String sideChains(List<Carbon> chain) {
      return sideChains(chain, null);
    }

    private String sideChains(List<Carbon> chain, Carbon trunkCarbon) {
      HashMap<String, List<Integer>> sideChains
        = new HashMap<String, List<Integer>>();

      int position = 0;

      for ( Carbon chainCarbon : chain ) {
        position++;

        for ( Carbon neighbor : chainCarbon.neighbors() ) {
          if ( !neighbor.equals(trunkCarbon) && !chain.contains(neighbor) ) {
            String alkyl = alkylGroupName(chainCarbon, neighbor);

            if ( !sideChains.containsKey(alkyl) )
              sideChains.put(alkyl, new ArrayList<Integer>());

            sideChains.get(alkyl).add(new Integer(position));
          }
        }
      }

      List<String> sideChainsOrder = new ArrayList<String>(
        sideChains.keySet() );
      Collections.sort( sideChainsOrder );

      boolean isReversed = false;

      if ( trunkCarbon == null ) {
        List<Integer>  forwardNumbering = new ArrayList<Integer>(),
                      backwardNumbering = new ArrayList<Integer>();

        for ( List<Integer> sideChain : sideChains.values() ) {
          for ( int i = 0; i < sideChain.size(); i++ ) {
            int pos             = sideChain.get(i),
                posFromOtherEnd = 1 + chain.size()
                                  - sideChain.get( sideChain.size() - 1 - i );

            forwardNumbering.add(pos);
            backwardNumbering.add(posFromOtherEnd);
          }
        }

        for ( int i = 0; i < forwardNumbering.size(); i++ ) {
          if ( forwardNumbering.get(i) > backwardNumbering.get(i) ) {
            isReversed = true;
            break;
          }
          else if ( forwardNumbering.get(i) < backwardNumbering.get(i) ) {
            isReversed = false;
            break;
          }
        }
      }

      String description = "";
      for ( String alkyl : sideChainsOrder ) {
        List<Integer> positions = sideChains.get(alkyl);

        String posList = commaSeparatedList(
          positions, isReversed, chain.size() );

        String fullName = positions.size() > 1
          ? multipliers[positions.size()] + alkyl.toLowerCase()
          : alkyl;

        if ( "".equals(description) )
          description += posList + "-" + fullName;
        else
          description += "-" + posList + "-" + fullName.toLowerCase();
      }

      return description;
    }

    public String iupacName() {
      
      if ( iupacNames == null )
        iupacNames = iupacNameList();

      List<Carbon> longestChain = longestChain();
      
      if ( carbons.size() == longestChain.size() )
         return iupacNames[ carbons.size() ] + "ane";

      String prefix = sideChains(longestChain);

      return prefix + iupacNames[ longestChain.size() ].toLowerCase() + "ane";
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

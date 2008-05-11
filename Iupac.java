import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Iupac {

    private static String[] iupacNames = null;
    private static String[] multipliers = new String[] {
      "",
      "",       "Di",    "Tri",  "Tetra", "Penta",
      "Hexa",   "Hepta", "Octa", "Nona",  "Deca",
      "Undeca",
    };
    private static String[] secondLevelMultipliers = new String[] {
      "",
      "",        "Bis",      "Tris",    "Tetrakis", "Pentakis",
      "Hexakis", "Heptakis", "Octakis", "Nonakis",  "Decakis",
      "Undecakis",
    };
    private static Map<String, String> commonAlkylNames = null;

    private static List<List<Carbon>> extendChains(
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

    private static List<List<Carbon>> longestFrom(
      Carbon startNode,
      List<List<Carbon>> longestChainsBefore ) {

      return longestFrom(startNode, longestChainsBefore, null);
    }

    private static List<List<Carbon>> longestFrom(
      Carbon startNode,
      List<List<Carbon>> longestChainsBefore,
      Carbon offBoundsNode ) {

      List<List<Carbon>>
        longestChains = new ArrayList<List<Carbon>>( longestChainsBefore ),
        queue         = new ArrayList<List<Carbon>>();

      if (longestChains.size() == 0) {
        List<Carbon> singleCarbonChain = new ArrayList<Carbon>();
        singleCarbonChain.add( startNode );
        longestChains.add( singleCarbonChain );
      }

      List<Carbon> seed = new ArrayList<Carbon>();
      seed.add(startNode);
      queue.add(seed);

      List<Carbon> traversed = new ArrayList<Carbon>();
      traversed.add( offBoundsNode );

      while ( !queue.isEmpty() ) {

        List<List<Carbon>> newChains = extendChains(queue, traversed);

        if ( queue.get(0).size() > longestChains.get(0).size() ) {
          longestChains.clear();
          longestChains.addAll(queue);
        }
        if ( queue.get(0).size() == longestChains.get(0).size() ) {
          longestChains.addAll(queue);
        }

        queue = newChains;
      }

      return longestChains;
    }

    private static List<List<Carbon>> longestChains(Carbohydrate carbohydrate) {

      if (carbohydrate.numberOfCarbons() == 0)
        throw new IllegalArgumentException("Empty molecule");

      List<List<Carbon>> longestChains = new ArrayList<List<Carbon>>();

      for ( Carbon leaf : carbohydrate.leaves() )
        longestChains = longestFrom( leaf, longestChains );

      return longestChains;
    }

    private static List<List<Carbon>> longestChains(
       Carbon trunkCarbon, Carbon branchCarbon) {

      return longestFrom(branchCarbon,
                         new ArrayList<List<Carbon>>(),
                         trunkCarbon);
    }

    private static List<Carbon> branchCarbons(
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

    private static Map<String, String> commonAlkylNamesMap() {

      Map<String, String> names = new HashMap<String, String>();

      String[][] pairs = new String[][] {
        { "1-Methylethyl",      "Isopropyl"   },
        { "2-Methylpropyl",     "Isobutyl"    },
        { "1-Methylpropyl",     "sec-Butyl"   },
        { "1,1-Dimethylethyl",  "tert-Butyl"  },
        { "3-Methylbutyl",      "Isopentyl"   },
        { "2,2-Dimethylpropyl", "Neopentyl"   },
        { "1,1-Dimethylpropyl", "tert-Pentyl" },
        { "4-Methylpentyl",     "Isohexyl"    },
      };

      for ( String[] pair : pairs )
        names.put(pair[0], pair[1]);

      return names;
    }

    private static String alkylGroupName(
      Carbon trunkCarbon,
      Carbon branchCarbon) {

      List<Carbon>
        alkylGroup
          = branchCarbons(trunkCarbon, branchCarbon),
        longestChain
          = longestChains(trunkCarbon, branchCarbon).get(0);

      if ( alkylGroup.size() == longestChain.size() )
        return iupacNames[ longestChain.size() ] + "yl";

      String prefix = sideChains(longestChain, trunkCarbon);

      if ( commonAlkylNames == null )
        commonAlkylNames = commonAlkylNamesMap();

      String  name = prefix
                     + iupacNames[ longestChain.size() ].toLowerCase()
                     + "yl",
        commonName = commonAlkylNames.get(name);

      if ( commonName != null )
        return commonName;

      return "(" + name + ")";
    }

    private static String[] iupacNameList() {
        String[] lowNames = new String[] {
          "",
          "Meth", "Eth",  "Prop",
          "But",  "Pent", "Hex",
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

    private static String commaSeparatedList(List<Integer> list) {

      StringBuilder sb = new StringBuilder();

      boolean first = true;
      for ( Integer item : list ) {
        if ( !first )
          sb.append( ',' );
        first = false;
        
        sb.append(item);
      }

      return sb.toString();
    }

    private static List<Integer> numbering(List<Carbon> chain) {
      return numbering(chain, null);
    }

    private static List<Integer> numbering(
      List<Carbon> chain,
      Carbon trunkCarbon) {

      List<Integer> numbering = new ArrayList<Integer>();

      int position = 0;

      for ( Carbon chainCarbon : chain ) {
        position++;

        for ( Carbon neighbor : chainCarbon.neighbors() )
          if ( !neighbor.equals(trunkCarbon) && !chain.contains(neighbor) )
            numbering.add(new Integer(position));
      }

      return numbering;
    }

    private static String sideChains(List<Carbon> chain) {
      return sideChains(chain, null);
    }

    private static String sideChains(List<Carbon> chain, Carbon trunkCarbon) {

      Map<String, List<Integer>> sideChains
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

      String description = "";
      for ( String alkyl : sideChainsOrder ) {
        List<Integer> positions = sideChains.get(alkyl);

        String posList = commaSeparatedList(positions);

        String fullName;
        int multiples = positions.size();
        if (multiples > 1) {
          if ( usesMultipliers(alkyl) )
            fullName = secondLevelMultipliers[ multiples ]
                       + alkyl.toLowerCase();
          else
            fullName = multipliers[ multiples ]
                       + alkyl.toLowerCase();
        }
        else {
          fullName = alkyl;
        }

        if ( "".equals(description) )
          description += posList + "-" + fullName;
        else
          description += "-" + posList + "-" + fullName.toLowerCase();
      }

      return description;
    }

    private static boolean usesMultipliers(String alkyl) {
      for (String multiplier : multipliers)
        if (alkyl.toLowerCase().indexOf(
              "-" + multiplier.toLowerCase() ) > -1)
          return true;

      return false;
    }

    private static List<List<Integer>> sort(Set<List<Integer>> set) {

      List<List<Integer>> sorted = new ArrayList<List<Integer>>(set);

      Collections.sort(sorted, new Comparator<List<Integer>>() {
            public int compare(List<Integer> n1, List<Integer> n2) {

              if ( n1.size() != n2.size() )
                return n2.size() - n1.size();

              for ( int i = 0; i < n1.size(); i++ )
                if ( n1.get(i).compareTo(n2.get(i)) != 0 )
                  return n1.get(i) - n2.get(i);
            
              return 0;
            }
        });

      return sorted;
    }

    private static List<Carbon> highestValuedChain(Carbohydrate carbohydrate) {

      List<List<Carbon>> longestChains = longestChains(carbohydrate);

      if ( carbohydrate.numberOfCarbons() == longestChains.get(0).size() )
        return carbohydrate.carbons();

      Map<List<Integer>, List<Carbon>> numberings
        = new HashMap<List<Integer>, List<Carbon>>();
      for ( List<Carbon> chain : longestChains )
        numberings.put( numbering(chain), chain );

      return numberings.get( sort( numberings.keySet() ).get(0) );
    }

    public static String fromMolecule(Carbohydrate carbohydrate) {
      
      if ( iupacNames == null )
        iupacNames = iupacNameList();

      List<Carbon> bestChain = highestValuedChain(carbohydrate);
      String prefix = sideChains(bestChain),
        mainChain = iupacNames[ bestChain.size() ],
        suffix = carbohydrate.hasDoubleBond() ? "ene" : "ane";

      if ( suffix.equals("ene") )
        mainChain = "2-" + mainChain;

      if (prefix.length() == 0)
        return mainChain + suffix;

      return prefix
             + mainChain.toLowerCase()
             + suffix;
    }
}
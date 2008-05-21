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

    private static List<Chain> extendChains(
      List<Chain> chains,
      List<Carbon> traversed ) {

      List<Chain> newChains = new ArrayList<Chain>();

      for ( Chain currentChain : chains ) {

        Carbon currentNode = currentChain.lastAtom();
        traversed.add(currentNode);

        for ( Carbon neighbor : currentNode.neighbors() ) {
          if ( !traversed.contains(neighbor) ) {

            Chain longerChain = currentChain.copy();
            longerChain.add(neighbor);
            newChains.add(longerChain);
          }
        }
      }

      return newChains;
    }

    private static List<Chain> longestFrom(
      Carbon startNode,
      List<Chain> longestChainsBefore ) {

      return longestFrom(startNode, longestChainsBefore, null);
    }

    private static List<Chain> longestFrom(
      Carbon startNode,
      List<Chain> longestChainsBefore,
      Carbon offBoundsNode ) {

      List<Chain>
        longestChains = new ArrayList<Chain>( longestChainsBefore ),
        queue         = new ArrayList<Chain>();

      if (longestChains.size() == 0) {
        Chain singleCarbonChain = new Chain();
        singleCarbonChain.add( startNode );
        longestChains.add( singleCarbonChain );
      }

      Chain seed = new Chain();
      seed.add(startNode);
      queue.add(seed);

      List<Carbon> traversed = new ArrayList<Carbon>();
      traversed.add( offBoundsNode );

      while ( !queue.isEmpty() ) {

        List<Chain> newChains = extendChains(queue, traversed);

        if ( queue.get(0).atoms().size()
             > longestChains.get(0).atoms().size() ) {

          longestChains.clear();
          longestChains.addAll(queue);
        }
        if ( queue.get(0).atoms().size()
             == longestChains.get(0).atoms().size() ) {

          longestChains.addAll(queue);
        }

        queue = newChains;
      }

      return longestChains;
    }

    private static List<Chain> longestChains(Hydrocarbon hydrocarbon) {

      if (hydrocarbon.numberOfCarbons() == 0)
        throw new IllegalArgumentException("Empty molecule");

      List<Chain> longestChains = new ArrayList<Chain>();

      for ( Carbon leaf : hydrocarbon.leaves() )
        longestChains = longestFrom( leaf, longestChains );

      return longestChains;
    }

    private static List<Chain> longestChains(
       Carbon trunkCarbon, Carbon branchCarbon) {

      return longestFrom(branchCarbon,
                         new ArrayList<Chain>(),
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

      List<Carbon> alkylGroup = branchCarbons(trunkCarbon, branchCarbon);
      Chain longestChain = longestChains(trunkCarbon, branchCarbon).get(0);

      if ( alkylGroup.size() == longestChain.atoms().size() )
        return iupacNames[ longestChain.atoms().size() ] + "yl";

      String prefix = sideChains(longestChain, trunkCarbon);

      if ( commonAlkylNames == null )
        commonAlkylNames = commonAlkylNamesMap();

      String  name
        = prefix
          + iupacNames[ longestChain.atoms().size() ].toLowerCase()
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

    private static String commaSeparatedList(final List<Integer> list) {

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

    private static List<Integer> numbering(Chain chain) {
      return numbering(chain, null);
    }

    private static List<Integer> numbering(Chain chain, Carbon trunkCarbon) {

      List<Integer> numbering = new ArrayList<Integer>();

      int position = 0;
      for ( Carbon chainCarbon : chain.atoms() ) {
        position++;

        for ( Carbon neighbor : chainCarbon.neighbors() )
          if ( !neighbor.equals(trunkCarbon) && !chain.contains(neighbor) )
            numbering.add(new Integer(position));
      }

      return numbering;
    }

    private static String sideChains(Chain chain) {
      return sideChains(chain, null);
    }

    private static String sideChains(Chain chain, Carbon trunkCarbon) {

      Map<String, List<Integer>> sideChains
        = new HashMap<String, List<Integer>>();

      int position = 0;

      for ( Carbon chainCarbon : chain.atoms() ) {
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

    private static List<Chain> mostSaturated(final List<Chain> chains) {
      int mostSaturatedSoFar = 0;
      for ( Chain chain : chains )
        if ( mostSaturatedSoFar < chain.doubleAndTripleBonds().size() )
          mostSaturatedSoFar = chain.doubleAndTripleBonds().size();

      final int mostSaturated = mostSaturatedSoFar;
      return new ArrayList<Chain>() {{
          for ( Chain chain : chains )
            if ( chain.doubleAndTripleBonds().size() == mostSaturated )
              add( chain );
      }};
    }

    private static List<Chain> longest(final List<Chain> chains) {
      int longestSoFar = 0;
      for ( Chain chain : chains )
        if ( longestSoFar < chain.atoms().size() )
          longestSoFar = chain.atoms().size();

      final int longest = longestSoFar;
      return new ArrayList<Chain>() {{
          for ( Chain chain : chains )
            if ( chain.atoms().size() == longest )
              add( chain );
      }};
    }

    private static <T> List<T> append(final List<T> a, final List<T> b) {
      return new ArrayList<T>(a) {{ addAll(b); }};
    }

    private static Chain highestValuedChain(Hydrocarbon hydrocarbon) {

      final List<Chain> allChains = hydrocarbon.allChains(),
              mostSaturatedChains = mostSaturated(allChains),
                    longestChains = longest(mostSaturatedChains);

      Map<List<Integer>, Chain> numberings
        = new HashMap<List<Integer>, Chain>() {{
            if ( !longestChains.get(0).tripleBonds().isEmpty() )
              for ( Chain chain : longestChains )
                put( append(
                       locationsOn(chain.doubleBonds(), chain),
                       locationsOn(chain.tripleBonds(), chain)
                     ), chain );
            else if ( !longestChains.get(0).doubleBonds().isEmpty() )
              for ( Chain chain : longestChains )
                put( locationsOn(chain.doubleBonds(), chain), chain );
            else
              for ( Chain chain : longestChains )
                put( numbering(chain), chain );
          }};

      return numberings.get( sort( numberings.keySet() ).get(0) );
    }

    private static String someSuffix(String suffix, int number) {
      return number == 1
        ? suffix
        : "a" + multipliers[number].toLowerCase() + suffix;
    }

    private static String alkeneSuffix(Chain chain) {
      return someSuffix("ene", chain.doubleBonds().size());
    }

    private static String alkenSuffix(Chain chain) {
      return someSuffix("en", chain.doubleBonds().size());
    }

    private static String alkyneSuffix(Chain chain) {
      return someSuffix("yne", chain.tripleBonds().size());
    }

    private static List<Integer> locationsOn( final List<Bond> bonds,
                                              final Chain chain ) {
      return new ArrayList<Integer>() {{
          int position = 0;
          for ( Bond bond : chain.bonds() ) {
            ++position;
            if ( bonds.contains(bond) )
              add(position);
          }
      }};              
    }

    public static String fromMolecule(Hydrocarbon hydrocarbon) {
      
      if ( iupacNames == null )
        iupacNames = iupacNameList();

      Chain bestChain = highestValuedChain(hydrocarbon);
      String prefix = sideChains(bestChain),
        mainChain = iupacNames[ bestChain.atoms().size() ],
        suffix = bestChain.tripleBonds().size() > 0
                 ? alkyneSuffix(bestChain) : bestChain.doubleBonds().size() > 0
                 ? alkeneSuffix(bestChain) : "ane";

      if ( suffix.endsWith("ene") )
        mainChain
          = commaSeparatedList(
              locationsOn(bestChain.doubleBonds(), bestChain)
            ) + '-' + mainChain;
      else if ( suffix.endsWith("yne") ) {
        if ( bestChain.doubleBonds().isEmpty() ) {
          mainChain
            = commaSeparatedList(
                locationsOn(bestChain.tripleBonds(), bestChain)
              ) + '-' + mainChain;
        }
        else {
          mainChain
            = commaSeparatedList(
                locationsOn(bestChain.doubleBonds(), bestChain)
              ) + '-' + mainChain;
          suffix = alkenSuffix(bestChain)
                   + '-'
                   + commaSeparatedList(
                       locationsOn(bestChain.tripleBonds(), bestChain)
                     )
                   + '-'
                   + suffix;
        }
      }

      String name = prefix.length() == 0
        ? mainChain + suffix
        : prefix + mainChain.toLowerCase() + suffix;

      Map<String, String> nonSystematicNames = new HashMap<String, String>() {{
          put("1-Ethene",       "Ethylene");
          put("1,2-Propadiene", "Allene");
          put("1-Ethyne",       "Acetylene");
      }};

      return nonSystematicNames.containsKey(name)
        ? nonSystematicNames.get(name)
        : name;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Hydrocarbon {

    private String description;
    private List<Carbon> carbons = new ArrayList<Carbon>();

    private Hydrocarbon() {}

    public static Hydrocarbon fromSmiles( String description ) {
      
      Hydrocarbon hydrocarbon = new Hydrocarbon();

      hydrocarbon.description = description;
      hydrocarbon.buildFromSmiles(description);

      return hydrocarbon;
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
        else if (desc.charAt(i) == '#') {
          List<Carbon> neighbors = new ArrayList<Carbon>();
          neighbors.add(carbon);

          carbon = new Carbon( neighbors, Bond.Type.TRIPLE );
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

      return new ArrayList<Carbon>() {{
          for ( Carbon carbon : carbons )
            if ( carbon.neighbors().size() < 2 )
              add( carbon );
      }};
    }

    public List<Chain> allChains() {

      List<Chain> chains = new ArrayList<Chain>();

      for (Carbon leaf : leaves())
        addChainsFrom(leaf, chains);

      return chains;
    }

    private void addChainsFrom(Carbon leaf, List<Chain> chains) {
      Chain chain = new Chain();

      traverseAndAddChains(chain, leaf, chains);
    }

    private void traverseAndAddChains(Chain currentChain,
                                      Carbon currentAtom,
                                      List<Chain> chains) {

      List<Bond> bondsToNewAtoms
        = new ArrayList<Bond>(currentAtom.bonds());

      if (currentChain.atoms().size() > 0) {
        Carbon lastAtom = currentChain.lastAtom();

        for (Bond bond : currentAtom.bonds())
          if (bond.atOtherEndOf(currentAtom) == lastAtom)
            bondsToNewAtoms.remove(bond); // need to do more than this
                                          // when we have cycles
      }

      currentChain.add( currentAtom );

      if (bondsToNewAtoms.size() == 0)
        chains.add(currentChain);
      else
        for (Bond bond : bondsToNewAtoms)
          traverseAndAddChains( currentChain.copy(), bond, chains );
    }

    private void traverseAndAddChains(Chain currentChain,
                                      Bond currentBond,
                                      List<Chain> chains) {
      currentChain.add( currentBond );
      Carbon lastAtom = currentChain.lastAtom();
      traverseAndAddChains( currentChain,
                            currentBond.atOtherEndOf(lastAtom),
                            chains );
    }

    public boolean equals( Object o ) {
      if ( o.getClass() != Hydrocarbon.class )
        return false;

      Hydrocarbon other = (Hydrocarbon)o;
      return normalization().equals( other.normalization() );
    }

    public int hashCode() {
      return normalization().hashCode();
    }
}

import java.util.ArrayList;
import java.util.List;

class Carbon {

    private List<Bond> bonds = new ArrayList<Bond>();

    public Carbon() {
    }

    public Carbon( List<Carbon> neighbors ) {
      for ( Carbon neighbor : neighbors )
        bind( this, neighbor, Bond.Type.SINGLE );
    }

    public Carbon( List<Carbon> neighbors, Bond.Type bondType ) {
      for ( Carbon neighbor : neighbors )
        bind( this, neighbor, bondType );
    }

    private static void bind( Carbon a, Carbon b, Bond.Type bondType ) {
      Bond bond = new Bond(a, b, bondType);
      a.bonds.add(bond);
      b.bonds.add(bond);
    }

    public List<Bond> bonds() {
      return new ArrayList<Bond>(bonds);
    }

    public List<Carbon> neighbors() {
      List<Carbon> neighbors = new ArrayList<Carbon>();

      for ( Bond bond : bonds )
        neighbors.add( bond.atOtherEndOf( this ) );

      return neighbors;
    }
}

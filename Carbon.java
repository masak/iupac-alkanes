import java.util.ArrayList;
import java.util.List;

class Carbon {

    private List<Carbon> neighbors = new ArrayList<Carbon>();

    public Carbon() {
    }

    public Carbon( List<Carbon> neighbors ) {
      this.neighbors = neighbors;

      for ( Carbon neighbor : neighbors )
        neighbor.addNeighbor( this );
    }

    private void addNeighbor( Carbon neighbor ) {
      neighbors.add( neighbor );
    }

    public List<Carbon> neighbors() {
      return new ArrayList<Carbon>(neighbors);
    }
}

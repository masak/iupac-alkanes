import java.util.ArrayList;

class Carbon {

    private ArrayList<Carbon> neighbors = new ArrayList<Carbon>();

    public Carbon() {
    }

    public Carbon( ArrayList<Carbon> neighbors ) {
      this.neighbors = neighbors;

      for ( Carbon neighbor : neighbors )
        neighbor.addNeighbor( this );
    }

    private void addNeighbor( Carbon neighbor ) {
      neighbors.add( neighbor );
    }

    public ArrayList<Carbon> neighbors() {
      return new ArrayList<Carbon>(neighbors);
    }
}

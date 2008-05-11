import java.util.ArrayList;
import java.util.List;

public class Chain {
    List<Carbon> atoms;
    List<Bond> bonds;

    public Chain() {
      this( new ArrayList<Carbon>(), new ArrayList<Bond>() );
    }

    private Chain(List<Carbon> atoms, List<Bond> bonds) {
      this.atoms = atoms;
      this.bonds = bonds;
    }

    public List<Carbon> atoms() {
      return new ArrayList<Carbon>(atoms);
    }

    public List<Bond> bonds() {
      return new ArrayList<Bond>(bonds);
    }

    public Carbon lastAtom() {
      return atoms.get( atoms.size() - 1 );
    }

    public void add(Carbon atom) {
      atoms.add(atom);
    }

    public void add(Bond bond) {
      bonds.add(bond);
    }

    public boolean contains(Carbon atom) {
      return atoms.contains(atom);
    }

    public List<Bond> doubleAndTripleBonds() {
      return new ArrayList<Bond>() {{
          for (Bond bond : bonds)
            if (bond.type() == Bond.Type.DOUBLE
                || bond.type() == Bond.Type.TRIPLE)
              add(bond);
      }};
    }

    public List<Bond> doubleBonds() {
      return new ArrayList<Bond>() {{
          for (Bond bond : bonds)
            if (bond.type() == Bond.Type.DOUBLE)
              add(bond);
      }};
    }

    public List<Bond> tripleBonds() {
      return new ArrayList<Bond>() {{
          for (Bond bond : bonds)
            if (bond.type() == Bond.Type.TRIPLE)
              add(bond);
      }};
    }

    public Chain copy() {
      return new Chain( new ArrayList<Carbon>(atoms),
                        new ArrayList<Bond>(bonds) );
    }
}

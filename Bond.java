public class Bond {
    private Carbon atom1, atom2;
    private Type type = Type.SINGLE;

    public static enum Type {
      SINGLE,
      DOUBLE,
      TRIPLE;
    }

    public Bond(Carbon atom1, Carbon atom2) {
      this(atom1, atom2, Type.SINGLE);
    }

    public Bond(Carbon atom1, Carbon atom2, Type type) {
      this.atom1 = atom1;
      this.atom2 = atom2;
      this.type = type;
    }

    public Type type() {
      return type;
    }

    public Carbon atOtherEndOf( Carbon atom ) {
      if ( atom == atom1 )
        return atom2;
      if ( atom == atom2 )
        return atom1;

      throw new IllegalArgumentException( atom + " is not at either end of "
                                          + "this bond" );
    }
}

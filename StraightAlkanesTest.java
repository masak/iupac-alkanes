class StraightAlkanesTest extends HydrocarbonsTest {

    private StraightAlkanesTest() {
      super("Straight alkanes", 100);
    }

    public void runTests() {
      String[] names = new String[] {
        "methane", "ethane", "propane", "butane",
        "pentane", "hexane", "heptane", "octane",
        "nonane", "decane",

        "undecane", "dodecane", "tridecane", "tetradecane",
        "pentadecane", "hexadecane", "heptadecane", "octadecane",
        "nonadecane", "eicosane",

        "heneicosane", "docosane", "tricosane", "tetracosane",
        "pentacosane", "hexacosane", "heptacosane", "octacosane",
        "nonacosane", "triacontane",

        "hentriacontane", "dotriacontane", "tritriacontane",
        "tetratriacontane", "pentatriacontane", "hexatriacontane",
        "heptatriacontane", "octatriacontane", "nonatriacontane",
        "tetracontane",

        "hentetracontane", "dotetracontane", "tritetracontane",
        "tetratetracontane", "pentatetracontane", "hexatetracontane",
        "heptatetracontane", "octatetracontane", "nonatetracontane",
        "pentacontane",

        "henpentacontane", "dopentacontane", "tripentacontane",
        "tetrapentacontane", "pentapentacontane", "hexapentacontane",
        "heptapentacontane", "octapentacontane", "nonapentacontane",
        "hexacontane",

        "henhexacontane", "dohexacontane", "trihexacontane",
        "tetrahexacontane", "pentahexacontane", "hexahexacontane",
        "heptahexacontane", "octahexacontane", "nonahexacontane",
        "heptacontane",

        "henheptacontane", "doheptacontane", "triheptacontane",
        "tetraheptacontane", "pentaheptacontane", "hexaheptacontane",
        "heptaheptacontane", "octaheptacontane", "nonaheptacontane",
        "octacontane",

        "henoctacontane", "dooctacontane", "trioctacontane",
        "tetraoctacontane", "pentaoctacontane", "hexaoctacontane",
        "heptaoctacontane", "octaoctacontane", "nonaoctacontane",
        "nonacontane",

        "hennonacontane", "dononacontane", "trinonacontane",
        "tetranonacontane", "pentanonacontane", "hexanonacontane",
        "heptanonacontane", "octanonacontane", "nonanonacontane",
        "hectane",
      };

      String exp = "C";
      for ( int i = 0; i < names.length; i++ ) {
        names[i] = capitalize( names[i] );
        is( Iupac.fromMolecule( Hydrocarbon.fromSmiles( exp ) ),
            names[i],
            names[i] );
        exp += "C";
      }
    }

    private static String capitalize( String s ) {
      return Character.toUpperCase( s.charAt(0) ) + s.substring(1);
    }

    public static void main( String args[] ) {
      new StraightAlkanesTest().test();
    }
}

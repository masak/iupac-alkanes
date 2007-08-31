class StraightAlkanesTest extends AlkanesTest {

      private StraightAlkanesTest( String name, int testsPlanned ) {
         super( name, testsPlanned );
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
            names[i] = Character.toUpperCase( names[i].charAt(0) ) + names[i].substring(1);
            is( new Alkane( exp ).iupacName(), names[i], names[i] );
            exp = "C(" + exp + ")";
         }
      }

      public static void main( String args[] ) {
         new StraightAlkanesTest( "Straight alkanes",
                                  100
            ).test();
      }
}

class StraightAlkanesTest extends Test {

      private StraightAlkanesTest( String name, int testsPlanned ) {
         super( name, testsPlanned );
      }

      private void is( String actual, String expected, String description ) {

         boolean test = actual.equals(expected);
         ok( test, description );

         if ( !test ) {
            System.out.println("     Expected: '" + expected + "'");
            System.out.println("       Actual: '" + actual   + "'");
         }
      }

      public void test() {
         String[] names = new String[] {
            "methane", "ethane", "propane", "buthane",
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
            "heptatriacontane", "octatriacontane", "nonatriacontaney",
            "tetracontane",

            "hentetracontane", "dotetracontane", "tritetracontane",
            "tetratetracontane", "pentatetracontane", "hexatetracontane",
            "heptatetracontane", "octatetracontane", "nonatetracontaney",
            "pentacontane",

            "henpentacontane", "dopentacontane", "tripentacontane",
            "tetrapentacontane", "pentapentacontane", "hexapentacontane",
            "heptapentacontane", "octapentacontane", "nonapentacontaney",
            "hexacontane",

            "henhexacontane", "dohexacontane", "trihexacontane",
            "tetrahexacontane", "pentahexacontane", "hexahexacontane",
            "heptahexacontane", "octahexacontane", "nonahexacontaney",
            "heptacontane",

            "henheptacontane", "doheptacontane", "triheptacontane",
            "tetraheptacontane", "pentaheptacontane", "hexaheptacontane",
            "heptaheptacontane", "octaheptacontane", "nonaheptacontaney",
            "octacontane",

            "henoctacontane", "dooctacontane", "trioctacontane",
            "tetraoctacontane", "pentaoctacontane", "hexaoctacontane",
            "heptaoctacontane", "octaoctacontane", "nonaoctacontaney",
            "nonacontane",

            "hennonacontane", "dononacontane", "trinonacontane",
            "tetranonacontane", "pentanonacontane", "hexanonacontane",
            "heptanonacontane", "octanonacontane", "nonanonacontaney",
            "hectane",
         };

         String exp = "C";
         for ( int i = 0; i < names.length; i++ ) {
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

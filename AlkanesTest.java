abstract class AlkanesTest extends Test {

      AlkanesTest( String name, int testsPlanned ) {
         super(name, testsPlanned);
      }

      protected void is( String actual, String expected, String description ) {

         boolean test = actual.equals(expected);
         ok( test, description );

         if ( !test ) {
            System.out.println("     Expected: '" + expected + "'");
            System.out.println("       Actual: '" + actual   + "'");
         }
      }
}

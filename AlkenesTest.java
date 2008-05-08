class AlkenesTest extends CarbohydratesTest {

    private AlkenesTest() {
      super( "Alkenes", 28 );
    }

    private void isRadical(String radical, String name) {
      isRadical(radical, name, name);
    }

    private void isRadical(String radical, String name, String description) {

      final String header = "C#CCCCCCCCCCCCC",
                   footer = "CCCCCCCCCCCCC#C";

      is( Carbohydrate.fromSmiles( header + radical + footer ).iupacName(),
          name,
          description );
    }

    protected void runTests() {
      // These tests are heavily based on
      // http://www.acdlabs.com/iupac/nomenclature/79/r79_53.htm

      is( Carbohydrate.fromSmiles("CCCC=CC").iupacName(),
          "2-Hexene",
          "One double bond -> ending is '-ene'" );
         
      is( Carbohydrate.fromSmiles("CC=CCC=C").iupacName(),
          "1,4-Hexadiene",
          "Two double bonds -> ending is '-diene'" );
         
      is( Carbohydrate.fromSmiles("C=C").iupacName(),
          "Ethylene",
          "Non-systematic name: 'ethylene' instead of '1-Ethene'" );

      is( Carbohydrate.fromSmiles("C=C=C").iupacName(),
          "Allene",
          "Non-systematic name: 'allene' instead of '1,2-Propdiene'" );

      is( Carbohydrate.fromSmiles( "C#CCC" ).iupacName(),
          "2-Butyne",
          "One triple bond -> ending is '-yne'"
        );
         
      is( Carbohydrate.fromSmiles( "C#C" ).iupacName(),
          "Acetylene",
          "Non-systematic name: 'acetylene' instead of 'methyne'"
        );
         
      is( Carbohydrate.fromSmiles( "C#CC=CC=C" ).iupacName(),
          "1,3-Hexadien-5-yne",
          "Interaction between double and triple bonds I"
        );

      is( Carbohydrate.fromSmiles( "CC=CC#C" ).iupacName(),
          "3-Penten-1-yne",
          "Interaction between double and triple bonds II" );
         
      is( Carbohydrate.fromSmiles( "C#CCC=C" ).iupacName(),
          "1-Penten-4-yne",
          "Interaction between double and triple bonds III" );
         
      is( Carbohydrate.fromSmiles( "C#CC(CCC)=C(CCC)C=C" ).iupacName(),
          "3,4-Dipropyl-1,3-hexadien-5-yne",
          "Main chain has maximum number of double and triple bonds I" );
         
      is( Carbohydrate.fromSmiles( "C=CC(C#C)C=CC=C" ).iupacName(),
          "5-Ethynyl-1,3,6-heptatriene",
          "Main chain has maximum number of double and triple bonds II" );
         
      is( Carbohydrate.fromSmiles( "C(C)(C)(C)CCC=C" ).iupacName(),
          "5,5-Dimethyl-1-hexene",
          "Main chain has maximum number of double and triple bonds III" );
         
      is( Carbohydrate.fromSmiles( "CC#C(C=C)CCC=C" ).iupacName(),
          "4-Vinyl-1-hepten-5-yne",
          "Main chain has maximum number of double and triple bonds IV" );
         
      is( Carbohydrate.fromSmiles( "C=CC(C)=C" ).iupacName(),
          "Isoprene",
          "Exception: isoprene" );
         
      isRadical( "(C#C)", "Ehtynyl" );
      isRadical( "(CC#C)", "2-Propynyl" );
      isRadical( "(C=CC)", "1-Propenyl" );
      isRadical( "(CC=CC)", "2-Butenyl" );
      isRadical( "(C=CC=C)", "1,3-Butadienyl" );
      isRadical( "(CC=CCC)", "2-Pentenyl" );
      isRadical( "(CC=CC#C)", "2-Penten-4-ynyl" );
      isRadical( "(C=C)", "Vinyl" );
      isRadical( "(CC=C)", "Allyl" );
      isRadical( "(C(C)=C)", "Isoprenyl" );

      isRadical( "(C#CC=CC(CCC=CC)C=CC=CC)",
          "5-(3-Pentenyl)-3,6,8-decatrien-1-ynyl",
          "Choice of chain: (1a) maximum number of double+triple bonds"
        );

      isRadical( "(CC=CC=CC(C=CC=CC)C=CC#CCC)",
          "6-(1,3-Pentadienyl)-2,4,7-dodecatrien-9-ynyl",
          "Choice of chain: (2) largest number of carbon atoms"
        );

      isRadical( "(CC=CC=CC(C=CC#CC)C=CC=CC)",
          "6-(1-Penten-3-ynyl)-2,4,7,9-undecatetraenyl",
          "Choice of chain: (3) largest number of double bonds"
        );

      isRadical( "(CC(CCCCCCCCC)=CC)",
          "2-Nonyl-2-butenyl",
          "Choice of chain: (1b) maximum number of double+triple bonds"
        );
    }

    public static void main( String args[] ) {
      new AlkenesTest().test();
    }
}

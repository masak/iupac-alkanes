class BranchedAlkanesTest extends CarbohydratesTest {

    private BranchedAlkanesTest() {
      super( "Branched alkanes", 26 );
    }

    protected void runTests() {
      // These tests are heavily based on
      // http://www.acdlabs.com/iupac/nomenclature/79/r79_36.htm

      is( Carbohydrate.fromSmiles("CCC(C)CC").iupacName(),
          "3-Methylpentane",
          "Side chains and longest chain name the alkyl" );
         
      is( Carbohydrate.fromSmiles("CC(C)C(C)CC(C)C").iupacName(),
          "2,3,5-Trimethylhexane",
          "Direction chosen so as to give lowest possible numbers I" );
         
      is( Carbohydrate.fromSmiles("CC(C)CCCCC(C)C(C)CC").iupacName(),
          "2,7,8-Trimethyldecane",
          "Direction chosen so as to give lowest possible numbers II" );

      is( Carbohydrate.fromSmiles("CCCC(CCC)C(C)CCCC").iupacName(),
          "5-Methyl-4-propylnonane",
          "Direction chosen so as to give lowest possible numbers III" );

      is( Carbohydrate.fromSmiles( "CCCCCCC(C(C)CCCC)CCCCCC" ).iupacName(),
          "7-(1-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain I"
        );
         
      is( Carbohydrate.fromSmiles( "CCCCCCC(CC(C)CCC)CCCCCC" ).iupacName(),
          "7-(2-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain II"
        );
         
      is( Carbohydrate.fromSmiles( "CCCCCCCC(CCCCC(C)C)CCCCCCC" ).iupacName(),
          "8-(5-Methylhexyl)pentadecane",
          "Branches are numbered from the trunk out along longest chain III"
        );

      is( Carbohydrate.fromSmiles( "CCCCC(C(C)C)CCCC" ).iupacName(),
          "5-Isopropylnonane",
          "Isopropyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCC(CC(C)C)CCCC" ).iupacName(),
          "5-Isobutylnonane",
          "Isobutyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCC(C(C)CC)CCCC" ).iupacName(),
          "5-sec-Butylnonane",
          "sec-butyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCCC(C(C)(C)C)CCCCC" ).iupacName(),
          "6-tert-Butylundecane",
          "tert-butyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCCCC(CCC(C)C)CCCCCC" ).iupacName(),
          "7-Isopentyltridecane",
          "Isopentyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCCC(CC(C)(C)C)CCCCC" ).iupacName(),
          "6-Neopentylundecane",
          "Neopentyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCCC(C(C)(C)CC)CCCCC" ).iupacName(),
          "6-tert-Pentylundecane",
          "tert-Pentyl" );
         
      is( Carbohydrate.fromSmiles( "CCCCCCC(CCCC(C)C)CCCCCC" ).iupacName(),
          "7-Isohexyltridecane",
          "Isohexyl" );

      is( Carbohydrate.fromSmiles( "CCC(C)(C)C(CC)CCC" ).iupacName(),
          "4-Ethyl-3,3-dimethylheptane",
          "Simple radicals are alphabetized before prefixes are inserted" );

      is( Carbohydrate.fromSmiles(
            "CCCCC(CC)CC(C(C)C(C)CCC)CCCCCC"
            ).iupacName(),

          "7-(1,2-Dimethylpentyl)-5-ethyltridecane",
          "Complex radicals are alphabetized by their complete names" );

      is( Carbohydrate.fromSmiles(
            "CCCCCC(C(C)CCC)CC(CC(C)CC)CCCCC"
          ).iupacName(),

          "6-(1-Methylbutyl)-8-(2-methylbutyl)tridecane",
          "Complex radicals with same name give priority to lowest locant" );

      is( Carbohydrate.fromSmiles( "CCCC(CC)C(C)CCC" ).iupacName(),
          "4-Ethyl-5-methyloctane",
          "Side chains on equivalent positions are given numbers in order I"
        );
      
      is( Carbohydrate.fromSmiles( "CCCC(C(C)C)C(CCC)CCC" ).iupacName(),
          "4-Isopropyl-5-propyloctane",
          "Side chains on equivalent positions are given numbers in order II"
        );

      is( Carbohydrate.fromSmiles( "CCC(C)(C)CC" ).iupacName(),
          "3,3-Dimethylpentane",
          "Multiple identical side chains are marked di-, tri-, tetra-, etc"
        );

      is( Carbohydrate.fromSmiles(
            "CC(C)CCC(C(CC)(CC)CC)(C(CC)(CC)CC)CCCCC"
          ).iupacName(),

          "5,5-Bis(1,1-diethylpropyl)-2-methyldecane",
          "Complicated identical side groups marked bis-, tris-, etc" );

      is( Carbohydrate.fromSmiles( "CC(C)C(C)C(CCC)C(C)CC" ).iupacName(),
          "2,3,5-Trimethyl-4-propylheptane",
          "The chosen chain is one with the greatest number of side chains" );

      is( Carbohydrate.fromSmiles( "CC(C)CC(CC(C)C)C(C)CC" ).iupacName(),
          "4-Isobutyl-2,5-dimethylheptane",
          "...or, failing that, the one with side chains with low locants" );

      is( Carbohydrate.fromSmiles(
            "CCC(CC)CC(C)CC(CC(C)CC(C)CC)(CC(C)CC(C)CC)CC(C)CC(C)CC"
          ).iupacName(),

          "7,7-Bis(2,4-dimethylhexyl)-3-ethyl-5,9,11-trimethyltridecane",
          "...or, failing that, side chains with large number of carbons" );

      is( Carbohydrate.fromSmiles(
            "CCCCC(CCC)C(C(C(C)C)CCCC)CCCCCC"
          ).iupacName(),
          "6-(1-Isopropylpentyl)-5-propyldodecane",
          "...or, failing that, the chain w/ the least branched side chains" );
    }

    public static void main( String args[] ) {
      new BranchedAlkanesTest().test();
    }
}

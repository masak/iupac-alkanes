class BranchedAlkanesTest extends CarbohydratesTest {

    private BranchedAlkanesTest() {
      super( "Branched alkanes", 26 );
    }

    protected void runTests() {
      // These tests are heavily based on
      // http://www.acdlabs.com/iupac/nomenclature/79/r79_36.htm

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles("CCC(C)CC") ),
          "3-Methylpentane",
          "Side chains and longest chain name the alkyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles("CC(C)C(C)CC(C)C") ),
          "2,3,5-Trimethylhexane",
          "Direction chosen so as to give lowest possible numbers I" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles("CC(C)CCCCC(C)C(C)CC") ),
          "2,7,8-Trimethyldecane",
          "Direction chosen so as to give lowest possible numbers II" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles("CCCC(CCC)C(C)CCCC") ),
          "5-Methyl-4-propylnonane",
          "Direction chosen so as to give lowest possible numbers III" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCCC(C(C)CCCC)CCCCCC" ) ),
          "7-(1-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain I"
        );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCCC(CC(C)CCC)CCCCCC" ) ),
          "7-(2-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain II"
        );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCCCC(CCCCC(C)C)CCCCCCC" ) ),
          "8-(5-Methylhexyl)pentadecane",
          "Branches are numbered from the trunk out along longest chain III"
        );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCC(C(C)C)CCCC" ) ),
          "5-Isopropylnonane",
          "Isopropyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCC(CC(C)C)CCCC" ) ),
          "5-Isobutylnonane",
          "Isobutyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCC(C(C)CC)CCCC" ) ),
          "5-sec-Butylnonane",
          "sec-butyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCC(C(C)(C)C)CCCCC" ) ),
          "6-tert-Butylundecane",
          "tert-butyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCCC(CCC(C)C)CCCCCC" ) ),
          "7-Isopentyltridecane",
          "Isopentyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCC(CC(C)(C)C)CCCCC" ) ),
          "6-Neopentylundecane",
          "Neopentyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCC(C(C)(C)CC)CCCCC" ) ),
          "6-tert-Pentylundecane",
          "tert-Pentyl" );
         
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCCCCC(CCCC(C)C)CCCCCC" ) ),
          "7-Isohexyltridecane",
          "Isohexyl" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCC(C)(C)C(CC)CCC" ) ),
          "4-Ethyl-3,3-dimethylheptane",
          "Simple radicals are alphabetized before prefixes are inserted" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles(
            "CCCCC(CC)CC(C(C)C(C)CCC)CCCCCC"
          ) ),

          "7-(1,2-Dimethylpentyl)-5-ethyltridecane",
          "Complex radicals are alphabetized by their complete names" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles(
            "CCCCCC(C(C)CCC)CC(CC(C)CC)CCCCC"
          ) ),

          "6-(1-Methylbutyl)-8-(2-methylbutyl)tridecane",
          "Complex radicals with same name give priority to lowest locant" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCC(CC)C(C)CCC" ) ),
          "4-Ethyl-5-methyloctane",
          "Side chains on equivalent positions are given numbers in order I"
        );
      
      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCCC(C(C)C)C(CCC)CCC" ) ),
          "4-Isopropyl-5-propyloctane",
          "Side chains on equivalent positions are given numbers in order II"
        );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CCC(C)(C)CC" ) ),
          "3,3-Dimethylpentane",
          "Multiple identical side chains are marked di-, tri-, tetra-, etc"
        );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles(
            "CC(C)CCC(C(CC)(CC)CC)(C(CC)(CC)CC)CCCCC"
          ) ),

          "5,5-Bis(1,1-diethylpropyl)-2-methyldecane",
          "Complicated identical side groups marked bis-, tris-, etc" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CC(C)C(C)C(CCC)C(C)CC" ) ),
          "2,3,5-Trimethyl-4-propylheptane",
          "The chosen chain is one with the greatest number of side chains" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles( "CC(C)CC(CC(C)C)C(C)CC" ) ),
          "4-Isobutyl-2,5-dimethylheptane",
          "...or, failing that, the one with side chains with low locants" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles(
            "CCC(CC)CC(C)CC(CC(C)CC(C)CC)(CC(C)CC(C)CC)CC(C)CC(C)CC"
          ) ),

          "7,7-Bis(2,4-dimethylhexyl)-3-ethyl-5,9,11-trimethyltridecane",
          "...or, failing that, side chains with large number of carbons" );

      is( Iupac.fromMolecule( Carbohydrate.fromSmiles(
            "CCCCC(CCC)C(C(C(C)C)CCCC)CCCCCC"
          ) ),
          "6-(1-Isopropylpentyl)-5-propyldodecane",
          "...or, failing that, the chain w/ the least branched side chains" );
    }

    public static void main( String args[] ) {
      new BranchedAlkanesTest().test();
    }
}

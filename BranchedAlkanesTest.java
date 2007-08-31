class BranchedAlkanesTest extends AlkanesTest {

    private BranchedAlkanesTest() {
      super( "Branched alkanes", 26 );
    }

    protected void runTests() {
      // These tests are heavily based on
      // http://www.acdlabs.com/iupac/nomenclature/79/r79_36.htm

      is( new Alkane("C(C(C(CC(C))))").iupacName(),
          "3-Methylpentane",
          "Side chains and longest chain name the alkyl" );
         
      is( new Alkane("C(C(CC(C(CC(CC)))))").iupacName(),
          "2,3,5-Trimethylhexane",
          "Direction chosen so as to give lowest possible numbers I" );
         
      is( new Alkane("C(C(C(CC(CC(C(C(C(C(CC)))))))))").iupacName(),
          "2,7,8-Trimethyldecane",
          "Direction chosen so as to give lowest possible numbers II" );

      is( new Alkane("C(C(C(C(C(CC(C(C(C))C(C(C))))))))").iupacName(),
          "5-Methyl-4-propylnonane",
          "Direction chosen so as to give lowest possible numbers III" );

      String oneMethylPentyl = graftOnto( pentane(), 1, methane() );
      is( new Alkane( graftOnto(straight(13), 7, oneMethylPentyl) ).iupacName(),
          "7-(1-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain I"
        );
         
      String twoMethylPentyl = graftOnto( pentane(), 2, methane() );
      is( new Alkane( graftOnto(straight(13), 7, twoMethylPentyl)).iupacName(),
          "7-(2-Methylpentyl)tridecane",
          "Branches are numbered from the trunk out along longest chain II"
        );
         
      String fiveMethylHexyl = graftOnto( hexane(), 5, methane() );
      is( new Alkane( graftOnto(straight(15), 8, fiveMethylHexyl) ).iupacName(),
          "8-(5-Methylhexyl)pentadecane",
          "Branches are numbered from the trunk out along longest chain III"
        );

      is( new Alkane( graftOnto(nonane(), 5, "C(CC)") ).iupacName(),
          "5-Isopropylnonane",
          "Isopropyl" );
         
      is( new Alkane( graftOnto(nonane(), 5, "C(C(CC))") ).iupacName(),
          "5-Isobutylnonane",
          "Isobutyl" );
         
      is( new Alkane( graftOnto(nonane(), 5, "C(CC(C))") ).iupacName(),
          "5-sec-Butylnonane",
          "sec-butyl" );
         
      is( new Alkane( graftOnto(straight(11), 6, "C(CCC)") ).iupacName(),
          "6-tert-Butylundecane",
          "tert-butyl" );
         
      is( new Alkane( graftOnto(straight(13), 7, "C(C(C(CC)))") ).iupacName(),
          "7-Isopentyltridecane",
          "Isopentyl" );
         
      is( new Alkane( graftOnto(straight(11), 6, "C(C(CCC))") ).iupacName(),
          "6-Neopentylundecane",
          "Neopentyl" );
         
      is( new Alkane( graftOnto(straight(11), 6, "C(CCC(C))") ).iupacName(),
          "6-tert-Pentylundecane",
          "tert-Pentyl" );
         
      is( new Alkane( graftOnto(straight(13),
                                7,
                                "C(C(C(C(CC))))") ).iupacName(),
          "7-Isohexyltridecane",
          "Isohexyl" );

      is( new Alkane( "C(C(C(C(C(C))C(CCC(C)))))" ).iupacName(),
          "4-Ethyl-3,3-dimethylheptane",
          "Simple radicals are alphabetized before prefixes are inserted" );

      String oneTwoDiMethylPentyl
        = graftOnto(graftOnto(pentane(), 1, methane() ), 2, methane());
      is( new Alkane( graftOnto(
                        graftOnto(straight(13), 7, oneTwoDiMethylPentyl),
                        5, ethane()) ).iupacName(),
          "7-(1,2-Dimethylpentyl)-5-ethyltridecane",
          "Complex radicals are alphabetized by their complete names" );

      String oneMethylButyl = graftOnto(butane(), 1, methane()),
             twoMethylButyl = graftOnto(butane(), 2, methane());
      is( new Alkane( graftOnto(graftOnto(straight(13), 8, twoMethylButyl),
                                6, oneMethylButyl) ).iupacName(),
          "6-(1-Methylbutyl)-8-(2-methylbutyl)tridecane",
          "Complex radicals with same name give priority to lowest locant" );

      is( new Alkane( graftOnto(graftOnto(straight(8),
                                          5, methane()),
                                          4, straight(2)) ).iupacName(),
          "4-Ethyl-5-methyloctane",
          "Side chains on equivalent positions are given numbers in order I"
        );
      
      is( new Alkane( graftOnto(graftOnto(straight(8),
                                          5, straight(3)),
                                          4, graftOnto(straight(2),
                                                       1, methane()))
                    ).iupacName(),
          "4-Isopropyl-5-propyloctane",
          "Side chains on equivalent positions are given numbers in order II"
        );

      is( new Alkane( graftOnto(graftOnto(pentane(),
                                          3, methane()),
                                          3, methane()) ).iupacName(),
          "3,3-Dimethylpentane",
          "Multiple identical side chains are marked di-, tri-, tetra-, etc"
        );

      String oneOneDiMethylPropyl
        = graftOnto(graftOnto(propane(),
                              1, ethane()),
                              1, ethane());
      is( new Alkane( graftOnto(graftOnto(graftOnto(decane(),
                                                    2, methane()),
                                                    5, oneOneDiMethylPropyl),
                                                    5, oneOneDiMethylPropyl)
                    ).iupacName(),
          "5,5-Bis(1,1-diethylpropyl)-2-methyldecane",
          "Complicated indentical side groups marked bis-, tris-, etc" );

      is( new Alkane( graftOnto(graftOnto(graftOnto(graftOnto(heptane(),
                                                              2, methane()),
                                                              3, methane()),
                                                              5, methane()),
                                                              4, propane())
                    ).iupacName(),
          "2,3,5-Trimethyl-4-propylheptane",
          "The chosen chain is one with the greatest number of side chains" );

      String isoButyl = graftOnto(butane(), 2, methane());
      is( new Alkane( graftOnto(graftOnto(graftOnto(heptane(),
                                                    4, isoButyl),
                                                    2, methane()),
                                                    5, methane())
            ).iupacName(),
          "4-Isobutyl-2,5-dimethylheptane",
          "...or, failing that, the one with side chains with low locants" );

      String largeSideChain = graftOnto(graftOnto(hexane(),
                                                  2, methane()),
                                                  4, methane());
      is( new Alkane( graftOnto(graftOnto(graftOnto(
                      graftOnto(graftOnto(graftOnto(straight(13),
                                                    7, largeSideChain),
                                                    7, largeSideChain),
                                                    3, ethane()),
                                                    5, methane()),
                                                    9, methane()),
                                                    11, methane())
                    ).iupacName(),
          "7,7-Bis(2,4-dimethylhexyl)-3-ethyl-5,9,11-trimethyltridecane",
          "...or, failing that, side chains with large number of carbons" );

      String  isoPropyl = graftOnto(ethane(), 1, methane()),
        isoPropylPentyl = graftOnto(pentane(), 1, isoPropyl);
      is( new Alkane( graftOnto(graftOnto(straight(12),
                                          6, isoPropylPentyl),
                                          5, propane()) ).iupacName(),
          "6-(1-Isopropylpentyl)-5-propyldodecane",
          "...or, failing that, the chain w/ the least branched side chains" );
    }

    public static void main( String args[] ) {
      new BranchedAlkanesTest().test();
    }
}

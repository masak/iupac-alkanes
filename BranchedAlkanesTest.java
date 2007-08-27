class BranchedAlkanesTest extends AlkanesTest {

      private BranchedAlkanesTest( String name, int testsPlanned ) {
         super( name, testsPlanned );
      }

      public void runTests() {
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

         is( new Alkane("C(C(C(C(C(C(C(C(CC(C(C(C))))"
                        + "C(C(C(C(C(C))))))))))))").iupacName(),
             "6-(1-Methylpentyl)dodecane",
             "Branches are numbered from the trunk out along longest chain I" );
         
         is( new Alkane("C(C(C(C(C(C(C(C(C(CC(C(C))))"
                        + "C(C(C(C(C(C))))))))))))").iupacName(),
             "6-(2-Methylpentyl)dodecane",
             "Branches are numbered from the trunk out along longest chain II" );
         
         is( new Alkane("C(C(C(C(C(C(C(C(C(C(C(CC))))"
                        + "C(C(C(C(C(C))))))))))))").iupacName(),
             "6-(5-Methylhexyl)dodecane",
             "Branches are numbered from the trunk out along longest chain III" );
      }

      public static void main( String args[] ) {
         new BranchedAlkanesTest( "Branched alkanes",
                                  7
            ).test();
      }
}

abstract class AlkanesTest extends Test {

      AlkanesTest( String name, int testsPlanned ) {
         super(name, testsPlanned);
      }

      protected void is( String actual, String expected, String description ) {

         boolean test = actual == null && expected == null
                     || actual != null && actual.equals(expected);
         ok( test, description );

         if ( !test ) {
            System.out.println("     Expected: '" + expected + "'");
            System.out.println("       Actual: '" + actual   + "'");
         }
      }

    protected String straight(int length) {
      StringBuilder sb = new StringBuilder();

      for ( int i = 0; i < length - 1; i++ )
        sb.append("C(");

      sb.append("C");
      
      for ( int i = 0; i < length - 1; i++ )
        sb.append(")");

      return sb.toString();
    }

    protected String methane() { return straight(1);  }
    protected String  ethane() { return straight(2);  }
    protected String propane() { return straight(3);  }
    protected String  butane() { return straight(4);  }
    protected String pentane() { return straight(5);  }
    protected String  hexane() { return straight(6);  }
    protected String heptane() { return straight(7);  }
    protected String  octane() { return straight(8);  }
    protected String  nonane() { return straight(9);  }
    protected String  decane() { return straight(10); }

    private char afterParentheses(String chain, int startLoc) {
      int runningLoc = startLoc + 1;

      for ( int level = 1;
            level > 0 && runningLoc < chain.length();
            runningLoc++ )

        if (chain.charAt(runningLoc) == '(')
          level++;
        else if (chain.charAt(runningLoc) == ')')
          level--;

      if (runningLoc > chain.length() - 1)
        throw new IllegalStateException( chain.substring(0,startLoc)
                                         + "X"
                                         + chain.substring(startLoc) );

      return chain.charAt(runningLoc);
    }

    protected String graftOnto(String bigChain, int pos, String smallChain) {
      for (int i = 0, level = 0; i < bigChain.length(); i++ ) {

        if ( bigChain.charAt(i) == '(' )
          level++;

        else if ( bigChain.charAt(i) == ')' )
          level--;

        else if ( level == pos && bigChain.charAt(i) == 'C'
                  && ((bigChain.charAt(i+1) == '('
                       && afterParentheses(bigChain, i+1) != 'C')
                      || bigChain.charAt(i+1) == ')') )

          return bigChain.substring(0, i)
                 + smallChain
                 + bigChain.substring(i);

        else if ( level == pos - 1
                  && bigChain.charAt(i) == 'C' && bigChain.charAt(i+1) == ')' )

          return bigChain.substring(0, i+1)
                 + smallChain
                 + bigChain.substring(i+1);
      }

      throw new IllegalArgumentException(
        "Could not graft " + smallChain + " onto position " + pos
        + " of " + bigChain + "."
      );
    }
}

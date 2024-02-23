/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.apex.cpd;

import org.junit.jupiter.api.Test;

import net.sourceforge.pmd.lang.test.cpd.CpdTextComparisonTest;
import net.sourceforge.pmd.lang.apex.ApexLanguageModule;

class ApexCpdLexerTest extends CpdTextComparisonTest {

    ApexCpdLexerTest() {
        super(ApexLanguageModule.getInstance(), ".cls");
    }

    @Test
    void testTokenize() {
        doTest("Simple");
    }

    /**
     * Comments are ignored since using ApexLexer.
     */
    @Test
    void testTokenizeWithComments() {
        doTest("comments");
    }

    @Test
    void testTabWidth() {
        doTest("tabWidth");
    }
}

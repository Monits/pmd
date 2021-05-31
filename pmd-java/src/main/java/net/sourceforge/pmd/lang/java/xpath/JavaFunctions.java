/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.annotation.InternalApi;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.xpath.saxon.DocumentNode;
import net.sourceforge.pmd.lang.ast.xpath.saxon.ElementNode;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.symboltable.ClassScope;
import net.sourceforge.pmd.lang.java.symboltable.MethodNameDeclaration;
import net.sourceforge.pmd.lang.symboltable.NameOccurrence;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.EmptyIterator;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeListIterator;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.trans.XPathException;

/**
 * Exposes all Java Language specific functions for Saxon use.
 */
@InternalApi
@Deprecated
public final class JavaFunctions {

    private JavaFunctions() {
        // utility class
    }

    @Deprecated
    public static boolean typeof(final XPathContext context, final String nodeTypeName, final String fullTypeName) {
        return typeof(context, nodeTypeName, fullTypeName, null);
    }

    @Deprecated
    public static boolean typeof(final XPathContext context, final String nodeTypeName,
            final String fullTypeName, final String shortTypeName) {
        return TypeOfFunction.typeof((Node) ((ElementNode) context.getContextItem()).getUnderlyingNode(), nodeTypeName,
                fullTypeName, shortTypeName);
    }

    public static double metric(final XPathContext context, final String metricKeyName) {
        return MetricFunction.getMetric((Node) ((ElementNode) context.getContextItem()).getUnderlyingNode(), metricKeyName);
    }

    public static boolean typeIs(final XPathContext context, final String fullTypeName) {
        return TypeIsFunction.typeIs((Node) ((ElementNode) context.getContextItem()).getUnderlyingNode(), fullTypeName);
    }

    public static boolean typeIsExactly(final XPathContext context, final String fullTypeName) {
        return TypeIsExactlyFunction.typeIsExactly((Node) ((ElementNode) context.getContextItem()).getUnderlyingNode(), fullTypeName);
    }
    
    public static SequenceIterator usages(SequenceIterator iterator) throws XPathException {
        Item item;
        List<ElementNode> ret = new ArrayList<>();
        
        while ((item = iterator.next()) != null) {
            if (item instanceof ElementNode) {
                ElementNode node = (ElementNode) item;
                Node n = (Node) ((ElementNode) node).getUnderlyingNode();
                final DocumentNode doc = (DocumentNode) node.getDocumentRoot();
                if (n instanceof ASTVariableDeclaratorId) {
                    for (final NameOccurrence no : ((ASTVariableDeclaratorId) n).getUsages()) {
                        ret.add(doc.nodeToElementNode.get(no.getLocation()));
                    }
                }
            }
        }
        
        if (ret.isEmpty()) {
            return EmptyIterator.getInstance();
        }
        
        return new NodeListIterator(ret);
    }
}

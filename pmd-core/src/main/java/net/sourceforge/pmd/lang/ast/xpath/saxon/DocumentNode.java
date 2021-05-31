/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast.xpath.saxon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sourceforge.pmd.annotation.InternalApi;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.xpath.internal.AstNodeOwner;
import net.sourceforge.pmd.lang.ast.xpath.internal.DeprecatedAttrLogger;
import net.sourceforge.pmd.lang.rule.xpath.SaxonXPathRuleQuery;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.Axis;
import net.sf.saxon.om.AxisIterator;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.om.NamePool;
import net.sf.saxon.om.Navigator;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SingleNodeIterator;
import net.sf.saxon.type.Type;

/**
 * A Saxon OM Document node for an AST Node.
 */
@Deprecated
@InternalApi
public class DocumentNode extends BaseNodeInfo implements DocumentInfo, AstNodeOwner {

    /**
     * The root ElementNode of the DocumentNode.
     */
    protected final ElementNode rootNode;

    /**
     * Mapping from AST Node to corresponding ElementNode.
     */
    public final Map<Node, ElementNode> nodeToElementNode = new HashMap<>();

    private DeprecatedAttrLogger attrCtx;

    /**
     * Construct a DocumentNode, with the given AST Node serving as the root
     * ElementNode.
     *
     * @param node     The root AST Node.
     * @param namePool Pool to share names
     *
     * @see ElementNode
     */
    public DocumentNode(Node node, Configuration configuration) {
        super(Type.DOCUMENT, configuration, "", null);
        this.rootNode = new ElementNode(this, new IdGenerator(), null, node, -1, configuration);
    }

    @Deprecated
    public DocumentNode(Node node) {
        this(node, SaxonXPathRuleQuery.getConfiguration());
    }

    @Override
    public String[] getUnparsedEntity(String name) {
        throw createUnsupportedOperationException("DocumentInfo.getUnparsedEntity(String)");
    }

    @Override
    public Iterator getUnparsedEntityNames() {
        throw createUnsupportedOperationException("DocumentInfo.getUnparsedEntityNames()");
    }

    @Override
    public NodeInfo selectID(String id) {
        throw createUnsupportedOperationException("DocumentInfo.selectID(String)");
    }

    @Override
    public DocumentInfo getDocumentRoot() {
        return this;
    }

    @Override
    public boolean hasChildNodes() {
        return true;
    }

    @Override
    public AxisIterator iterateAxis(byte axisNumber) {
        switch (axisNumber) {
        case Axis.DESCENDANT:
            return new Navigator.DescendantEnumeration(this, false, true);
        case Axis.DESCENDANT_OR_SELF:
            return new Navigator.DescendantEnumeration(this, true, true);
        case Axis.CHILD:
            return SingleNodeIterator.makeIterator(rootNode);
        case Axis.SELF:
            return SingleNodeIterator.makeIterator(this);
        default:
            return super.iterateAxis(axisNumber);
        }
    }

    @Override
    public Node getUnderlyingNode() {
        // this is a concession to the model, so that the expression "/"
        // may be interpreted as the root node
        return rootNode.getUnderlyingNode();
    }

    public DeprecatedAttrLogger getAttrCtx() {
        return attrCtx == null ? DeprecatedAttrLogger.noop() : attrCtx;
    }

    public void setAttrCtx(DeprecatedAttrLogger attrCtx) {
        this.attrCtx = attrCtx;
    }
}

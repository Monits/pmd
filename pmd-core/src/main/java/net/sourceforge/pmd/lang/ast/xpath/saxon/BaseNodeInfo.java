/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast.xpath.saxon;


import net.sf.saxon.Configuration;
import net.sf.saxon.om.FingerprintedNode;
import net.sf.saxon.om.NamePool;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SiblingCountingNode;
import net.sf.saxon.om.VirtualNode;

abstract class BaseNodeInfo extends AbstractNodeInfo implements VirtualNode, SiblingCountingNode, FingerprintedNode {

    // It's important that all our NodeInfo implementations share the
    // same getNodeKind implementation, otherwise NameTest spends a lot
    // of time in virtual dispatch
    private final int nodeKind;
    private final Configuration configuration;
    private final int fingerprint;

    protected final ElementNode parent;

    BaseNodeInfo(int nodeKind, Configuration configuration, String localName, ElementNode parent) {
        this.nodeKind = nodeKind;
        this.configuration = configuration;
        this.fingerprint = configuration.getNamePool().allocate("", "", localName) & NamePool.FP_MASK;
        this.parent = parent;
    }

    @Override
    public final String getURI() {
        return "";
    }

    @Override
    public final String getBaseURI() {
        return "";
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public final NodeInfo getParent() {
        return parent;
    }

    @Override
    public int getNameCode() {
        // note: the nameCode is only equal to the fingerprint because
        // this implementation does not use namespace prefixes
        // if we change that (eg for embedded language support) then
        // we'll need to worry about this. See NamePool.FP_MASK
        return fingerprint;
    }

    @Override
    public final int getFingerprint() {
        return fingerprint;
    }
    
    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public final NamePool getNamePool() {
        return configuration.getNamePool();
    }

    @Override
    public final int getNodeKind() {
        return nodeKind;
    }

}

package com.ning.api.client.access.impl;

import com.ning.api.client.access.Anchor;

/**
 * Container class that can contain one optional
 * {@link Anchor}. Used for both passing in an optional
 * anchor and returning new anchor back from List-returning methods.
 */
public class AnchorHolder
{
    protected Anchor anchor;
    
    public AnchorHolder() { this(null); }
    public AnchorHolder(Anchor anchor) { this.anchor = anchor; }

    public Anchor getAnchor() { return anchor; }
    public void setAnchor(Anchor anchor) { this.anchor = anchor; }
    public void setAnchor(String str) {
        anchor = (str == null || str.length() == 0) ? null : new Anchor(str);
    }
    
    public boolean hasAnchor() { return anchor != null; }

    @Override
    public String toString() { return (anchor == null)  ? "" : anchor.toString(); }
}

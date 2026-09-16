package gg.umbra.runtime.model;

import gg.umbra.runtime.model.DetachedStringTreeEntry;
import gg.umbra.runtime.model.DetachedStringTreeNode;
import java.util.List;

class DetachedStringTreeListEntry
extends DetachedStringTreeEntry {
    final DetachedStringTreeNode listOwnerNode;
    List<String> values;

    DetachedStringTreeListEntry(DetachedStringTreeNode ownerNode) {
        super(ownerNode);
        this.listOwnerNode = ownerNode;
    }
}

package gg.umbra.runtime.model;

import gg.umbra.runtime.model.DetachedStringTree;
import gg.umbra.runtime.model.DetachedStringTreeEntry;
import java.util.List;

class DetachedStringTreeNode {
    List<DetachedStringTreeEntry> entries;
    final DetachedStringTree ownerTree;
    String primaryValue;
    String secondaryValue;

    DetachedStringTreeNode(DetachedStringTree ownerTree) {
        this.ownerTree = ownerTree;
    }
}

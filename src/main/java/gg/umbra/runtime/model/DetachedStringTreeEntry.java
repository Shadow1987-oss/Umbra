package gg.umbra.runtime.model;

import gg.umbra.runtime.model.DetachedStringTreeNode;

class DetachedStringTreeEntry {
    String value;
    final DetachedStringTreeNode ownerNode;

    DetachedStringTreeEntry(DetachedStringTreeNode ownerNode) {
        this.ownerNode = ownerNode;
    }
}

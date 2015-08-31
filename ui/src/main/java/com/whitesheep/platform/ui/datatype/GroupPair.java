package com.whitesheep.platform.ui.datatype;

import java.awt.Component;
import javax.swing.GroupLayout;

public class GroupPair {
    private GroupLayout layout;
    private GroupLayout.Group hgroup;
    private GroupLayout.Group vgroup;

    public GroupPair(GroupLayout layout) {
        this.layout = layout;
    }

    public GroupPair(GroupLayout layout, GroupLayout.Group hgroup, GroupLayout.Group vgroup) {
        this.layout = layout;
        this.hgroup = hgroup;
        this.vgroup = vgroup;
    }

    public GroupLayout.Group getHgroup() {
        return hgroup;
    }

    public GroupLayout.Group getVgroup() {
        return vgroup;
    }

    public void setHgroup(GroupLayout.Group hgroup) {
        this.hgroup = hgroup;
    }

    public void setVgroup(GroupLayout.Group vgroup) {
        this.vgroup = vgroup;
    }
    
    public GroupPair addRight(GroupLayout.Alignment alignment, GroupPair... groups) {
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        if (hgroup != null) seqGroup.addGroup(hgroup);
        for (GroupPair group : groups) seqGroup.addGroup(group.getHgroup());
        hgroup = seqGroup;
        
        GroupLayout.ParallelGroup parGroup  = layout.createParallelGroup(alignment);
        if (vgroup != null) parGroup.addGroup(alignment, vgroup);
        for (GroupPair group : groups) parGroup.addGroup(alignment, group.getVgroup());
        vgroup = parGroup;
        
        return this;
    }
    
    public GroupPair addRight(GroupLayout.Alignment alignment, boolean resizableH, boolean resizableV, Component... components) {
        int minWidth = resizableH ? 0 : GroupLayout.PREFERRED_SIZE;
        int maxWidth = resizableH ? Short.MAX_VALUE : GroupLayout.PREFERRED_SIZE;
        int minHeight = resizableV ? 0 : GroupLayout.PREFERRED_SIZE;
        int maxHeight = resizableV ? Short.MAX_VALUE : GroupLayout.PREFERRED_SIZE;
        int pref = GroupLayout.DEFAULT_SIZE;
        
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        if (hgroup != null) seqGroup.addGroup(hgroup);
        for (Component component : components) seqGroup.addComponent(component, minWidth, pref, maxWidth);
        hgroup = seqGroup;
        
        GroupLayout.ParallelGroup parGroup  = layout.createParallelGroup(alignment);
        if (vgroup != null) parGroup.addGroup(alignment, vgroup);
        for (Component component : components) parGroup.addComponent(component, minHeight, pref, maxHeight);
        vgroup = parGroup;
        
        return this;
    }
    
    public GroupPair addBelow(GroupLayout.Alignment alignment, GroupPair... groups) {
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        if (vgroup != null) seqGroup.addGroup(vgroup);
        for (GroupPair group : groups) seqGroup.addGroup(group.getVgroup());
        vgroup = seqGroup;
        
        GroupLayout.ParallelGroup parGroup  = layout.createParallelGroup(alignment);
        if (hgroup != null) parGroup.addGroup(alignment, hgroup);
        for (GroupPair group : groups) parGroup.addGroup(alignment, group.getHgroup());
        hgroup = parGroup;
        
        return this;
    }
    
    public GroupPair addBelow(GroupLayout.Alignment alignment, boolean resizableH, boolean resizableV, Component... components) {
        int minWidth = resizableH ? 0 : GroupLayout.PREFERRED_SIZE;
        int maxWidth = resizableH ? Short.MAX_VALUE : GroupLayout.PREFERRED_SIZE;
        int minHeight = resizableV ? 0 : GroupLayout.PREFERRED_SIZE;
        int maxHeight = resizableV ? Short.MAX_VALUE : GroupLayout.PREFERRED_SIZE;
        int pref = GroupLayout.DEFAULT_SIZE;
        
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        if (vgroup != null) seqGroup.addGroup(vgroup);
        for (Component component : components) seqGroup.addComponent(component, minHeight, pref, maxHeight);
        vgroup = seqGroup;
        
        GroupLayout.ParallelGroup parGroup  = layout.createParallelGroup(alignment);
        if (hgroup != null) parGroup.addGroup(alignment, hgroup);
        for (Component component : components) parGroup.addComponent(component, minWidth, pref, maxWidth);
        hgroup = parGroup;
        
        return this;
    }
}

package com.gammadevs.gui.category;

import com.gammadevs.dao.DbStorage;
import com.gammadevs.model.Category;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * Created by Anton on 9/30/2014.
 */
public class CategoryTabPanel extends JPanel {

    private final DbStorage storage = DbStorage.getInstance();
    private JTree tree;

    public CategoryTabPanel() {
        super(new GridLayout(2,0));
        createUi();
    }

    private void createUi() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
        initNode(storage.getCategories(), root, null);
        tree = new JTree(root){

            @Override
            public String convertValueToText(Object value, boolean selected
                    , boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value != null
                        && value instanceof DefaultMutableTreeNode
                        && ((DefaultMutableTreeNode) value).getUserObject() instanceof Category) {
                    return ((Category) ((DefaultMutableTreeNode) value).getUserObject()).getName();
                }
                return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
            }

        };
        this.add(new JScrollPane(tree));

        final JPopupMenu menu = new JPopupMenu();
        menu.add(new AbstractAction("Add sub-category") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        menu.add(new AbstractAction("Update") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = tree.getRowForLocation(e.getX(), e.getY());
                if (row > -1) {
                    tree.setSelectionRow(row);
                    if (e.isPopupTrigger()) {
                        menu.show((JComponent) e.getSource(), e.getX(), e.getY());
                    }
                }
            }
        });

    }

    private void initNode(Collection<Category> categories, DefaultMutableTreeNode parentNode, Long parentId) {
        for (Category category : categories) {
            if (parentId != null && parentId.equals(category.getParentId())
                    || category.getParentId() == parentId) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(category);
                parentNode.add(node);
                initNode(categories, node, category.getId());
            }
        }
    }

}

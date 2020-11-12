package cs3560_twitter;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;


public class TreePanel extends JPanel implements TreeSelectionListener {
	
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private DefaultMutableTreeNode path;
    
	public TreePanel() {
        this.setLayout(new GridLayout(1,0));
        path = null;
        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
	}
	
	public DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent, String name) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(name);
        if(parent==null) parent = rootNode;
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        return childNode;
	}
	
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }
    
    private void setSelectedPath(DefaultMutableTreeNode node) {
    	path = node;
    }
    
    public DefaultMutableTreeNode getSelectedPath() {
    	return path;
    }

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String selected = "" + tree.getLastSelectedPathComponent();

		if(node==null) {
			AdminControl.getInstance().reloadFrame("User ID");
		} else {
			setSelectedPath(node);
			AdminControl.getInstance().reloadFrame(selected);
		}
	}
	
	

	
   
}

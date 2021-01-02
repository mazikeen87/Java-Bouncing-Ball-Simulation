/**
 * @author Prof. Frank Ferrie
 * @author Christopher Boustros
 * ECSE 202 Introduction to Software Engineering
 */
package assets;

import acm.graphics.GOval;

import project.gBall;

// Implements a binary tree
public class bTree {
	private bNode root=null;				
	
	/**
	 * Created by Prof. Frank Ferrie
	 * addNode method - wrapper for rNode
 	 */	
	public void addNode(gBall ball) {	
		root=rNode(root,ball);			
	}
	
	/**
	 * Created by Prof. Frank Ferrie
 	 * rNode method - recursively adds a new entry into the B-Tree
 	 */	
	private bNode rNode(bNode root, gBall ball) {
		//
		//  Termination condition for recursion.  We have descended to a leaf
		//  node of the tree (or the tree may initially be empty).  In either case,
		//	create a new node --> copy over data, set successor nodes to null.
		//
		if (root==null) {
			bNode node = new bNode();
			node.ball = ball;
			node.left = null;
			node.right = null;
			root=node;
			return root;
		}

		else if (ball.getSize() < root.ball.getSize()) {
			root.left = rNode(root.left,ball);
		}

		else {
			root.right = rNode(root.right,ball);
		}
		return root;
	}
	
	/**
	 * Created by Prof. Frank Ferrie
 	 * inorder method - inorder traversal via call to recursive method
 	 */
	public void inorder() {	
		traverse_inorder(root);		
	}								
	
	/**
	 * Created by Prof. Frank Ferrie
	 * traverse_inorder method - recursively traverses tree in order and prints each node.
	 * Order: Descend following left successor links, returning back to the current
 	 *        root node (where a specific action takes place, e.g., printing data),
 	 *        and then repeat the descent along right successor links.
 	 */
	private void traverse_inorder(bNode root) {
		if (root.left != null) traverse_inorder(root.left);
		System.out.println(root.ball);
		if (root.right != null) traverse_inorder(root.right);
	}
	
	/**
	 * Created by Prof. Frank Ferrie
	 * preorder method - preorder traversal via call to recursive method
	 * 
	 */
	public void preorder() {
		traverse_preorder(root);	
	}
	

	/**
	 * Created by Prof. Frank Ferrie
	 * traverse_preorder method - recursively traverses tree in preorder and prints each node.
	 * Order: Similar approach to traverse_inorder, except that the pattern is now
	 *        do at root, then traverse left followed by traverse right.
	 */
	public void traverse_preorder(bNode root) {
		System.out.println(root.ball);
		if (root.left != null) traverse_preorder(root.left);
		if (root.right != null) traverse_preorder(root.right);
	}
	
	/**
	 * Created by Prof. Frank Ferrie
	 * postorder method - postorder traversal via call to recursive method
	 */
	public void postorder() {
		traverse_postorder(root);	
	}

	/**
	 * Created by Prof. Frank Ferrie
	 * traverse_postorder method - recursively traverses tree in postorder and prints each node.
	 * Order: Similar approach to traverse_inorder, except that the pattern is now
	 *        traverse left, then traverse right, followed by do at root.
	 */
	public void traverse_postorder(bNode root) {
		if (root.left != null) traverse_postorder(root.left);
		if (root.right != null) traverse_postorder(root.right);
		System.out.println(root.ball);
	}
	
	private bNode node = null; // The node that will be returned by findNode(GOval oval)
	
	// Returns the node corresponding to a particular GOval object
	public bNode findNode(GOval oval) { 
		findNode_traverse_inorder(root, oval);
		return node;
	}
	
	// Helper method for findNode(GOval oval)
	private void findNode_traverse_inorder (bNode root, GOval value) { 
		if (root.left != null) findNode_traverse_inorder(root.left, value);
		if (root.ball.getBall() == value) {
			node = root;
			return;
		}
		
		if (root.right != null) findNode_traverse_inorder(root.right, value);
	}
	
	private bTree newTree; // used in the reSort() method
	
	// Re-sorts the bTree by traversing it, adding each node to a new bTree, then copying the reference to the new bTree's root
	public void reSort() {
		newTree = new bTree();
		reSort_traverse_inorder(root);
		root = newTree.root;
	}
	
	// Helper method for reSort()
	private void reSort_traverse_inorder(bNode root) { 
		if (root.left != null) reSort_traverse_inorder(root.left);
		newTree.addNode(root.ball);
		if (root.right != null) reSort_traverse_inorder(root.right);
	}
	
	// Clears the binary tree
	public void clear() {
		root = null;
	}
	
	// Returns true if the root is null
	public boolean isEmpty() {
		return root == null;
	}
	
	// Calls stopSimulation() on every node through inorder traversal
	public void stopSimulation() {
		stopSimulation_traverse_inorder(root);
	}
	
	// Helper method for stopSimulation()
	private void stopSimulation_traverse_inorder(bNode root) {
		if (root.left != null) stopSimulation_traverse_inorder(root.left);
		root.ball.stopSimulation();
		if (root.right != null) stopSimulation_traverse_inorder(root.right);
	}
	
	private boolean running;
	public boolean isRunning() {
		running = false;
		isRunning_traverse_inorder(root);
		return running;
	}
	
	private void isRunning_traverse_inorder(bNode root) {
		if (root.left != null) isRunning_traverse_inorder(root.left);
		
		if (root.ball.isActive()) {
			running = true;
			return;
		}
		
		if (root.right != null) isRunning_traverse_inorder(root.right);
	}
	
	private double xPos;
	public void moveSort() {
		xPos = 0.0;
		moveSort_traverse_inorder(root);
	}

	private void moveSort_traverse_inorder(bNode root) {
		if (root.left != null) moveSort_traverse_inorder(root.left);
		root.ball.moveTo(xPos, 0);
		xPos += 2*root.ball.getSize();
		if (root.right != null) moveSort_traverse_inorder(root.right);
	}
}


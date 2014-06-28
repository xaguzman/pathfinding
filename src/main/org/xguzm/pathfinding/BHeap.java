package org.xguzm.pathfinding;

import java.util.Comparator;

/*
 * Slight modification over Nathan Sweet's implementation on libgdx.
 */
@SuppressWarnings("unchecked")
public class BHeap<T extends BHeapNode> {
	
	public int size = 0;

	private BHeapNode[] nodes;
	//private final boolean isMaxHeap;
	//Comparator<T> comparator;
	//Class<T> clazz;
	Comparator<T> comparator;

	public BHeap(Comparator<T> comparator) {
		this(comparator, 16);
	}

	public BHeap (Comparator<T> comparator, int capacity) {
		//this.isMaxHeap = isMaxHeap;
		//this.clazz = clazz;
		nodes = new BHeapNode[capacity];// new GridCell[capacity];
		this.comparator = comparator;
	}

	public T add (T node) {
		// Expand if necessary.
		if (size == nodes.length) {
			BHeapNode[] newNodes =  new BHeapNode[size << 1];
			System.arraycopy(nodes, 0, newNodes, 0, size);
			nodes = newNodes;
		}
		// Insert at end and bubble up.
		node.setIndex(size);
		nodes[size] = node;
		up(size++);
		return node;
	}

	public T peek () {
		if (size == 0) throw new IllegalStateException("The heap is empty.");
		return (T)nodes[0];
	}

	public T pop () {
		BHeapNode[] nodes = this.nodes;
		BHeapNode popped = nodes[0];
		nodes[0] = nodes[--size];
		nodes[size] = null;
		if (size > 0) down(0);
		return (T)popped;
	}
	
	public void clear () {
		for (int i = 0, n = size; i < n; i++)
			nodes[i] = null;
		size = 0;
	}
	
	public void updateNode(T node, float valueComparison){
		int i = node.getIndex();
		if (valueComparison < 0)
			up(i);
		else
			down(i);
	}

	private void up (int index) {
		BHeapNode[] nodes = this.nodes;
		BHeapNode node = nodes[index];
		//float value = node.f;
		while (index > 0) {
			int parentIndex = (index - 1) >> 1;
			BHeapNode parent = nodes[parentIndex];
			//if ( value < parent.getValue()) {
			if (comparator.compare( (T) node, (T) parent) < 0){
				nodes[index] = parent;
				parent.setIndex(index);
				index = parentIndex;
			} else
				break;
		}
		nodes[index] = node;
		node.setIndex(index);
	}

	private void down (int index) {
		BHeapNode[] nodes = this.nodes;
		int size = this.size;

		BHeapNode node = nodes[index];
		//float value = node.getValue();

		while (true) {
			int leftIndex = 1 + (index << 1);
			if (leftIndex >= size) break;
			int rightIndex = leftIndex + 1;

			// Always have a left child.
			BHeapNode leftNode = nodes[leftIndex];
			//float leftValue = leftNode.getValue();

			// May have a right child.
			BHeapNode rightNode;
			//float rightValue;
			if (rightIndex >= size) {
				rightNode = null;
				//rightValue = isMaxHeap ? Float.MIN_VALUE : Float.MAX_VALUE;
				//rightValue = Float.MAX_VALUE;
			} else {
				rightNode = nodes[rightIndex];
				//rightValue = rightNode.getValue();
			}

			// The smallest of the three values is the parent.
			//if (leftValue < rightValue ^ isMaxHeap) {
			if (comparator.compare( (T)leftNode, (T)rightNode) < 0){
				//if (leftValue == value || (leftValue > value ^ isMaxHeap)) break;
				if (leftNode == null || comparator.compare((T)leftNode, (T)node) > 0) break;
				nodes[index] = leftNode;
				leftNode.setIndex(index);
				index = leftIndex;
			} else {
				//if (rightValue == value || (rightValue > value ^ isMaxHeap)) break;
				if (rightNode == null || comparator.compare( (T)rightNode, (T)node) > 0) break;
				nodes[index] = rightNode;
				rightNode.setIndex(index);
				index = rightIndex;
			}
		}

		nodes[index] = node;
		node.setIndex(index);
	}

	public String toString () {
		if (size == 0) return "[]";
		Object[] nodes = this.nodes;
		StringBuilder buffer = new StringBuilder(32);
		buffer.append('[');
		buffer.append(nodes[0]);
		for (int i = 1; i < size; i++) {
			buffer.append(", ");
			buffer.append(nodes[i]);
		}
		buffer.append(']');
		return buffer.toString();
	}
}

package com.imfsoftware.datastructure;

import java.security.InvalidParameterException;
import java.util.ListIterator;

class UnrolledListIterator<E> implements ListIterator<E> {
	UnrolledLinkedList<E> list;
	UnrolledLinkedList<E>.UnrolledNode<E> current;
	int pos = 0;
	int index = -1;

	// constructor
	UnrolledListIterator(UnrolledLinkedList<E> list) {
		this.list = list;
		this.current = list.getHead();
		this.pos = 0;
		this.index = -1;
	}
	
	UnrolledListIterator(UnrolledLinkedList<E> list, int index) {
		this.list = list;
		this.current = list.getHead();
		this.pos = index;
		this.index = index;
		while (pos > current.size) {
			if (current.next == null) {
				throw new InvalidParameterException("Index exceeds capacity!");
			} else {
				pos -= current.size;
				current = current.next;
			}
		}
	}

	// Checks if the next element exists
	@Override
	public boolean hasNext() {
		if (current == null) return false;
		
		if ((pos >= current.size) && (current.next == null)) {
			return false;
		}
		return true;
	}

	// moves the cursor/iterator to next element
	@Override
	public E next() {
		if (current == null) return null;
		E data = current.elements[pos++];
		index++;
		if ((pos >= current.size) && (current.next != null)) {
			current = current.next;
			pos = 0;
		}
		return data;
	}

	@Override
	public boolean hasPrevious() {
		if (current != null) {
			if (pos > 0) return true;
			return current.hasPrevious();
		}
		return false;
	}

	@Override
	public E previous() {
		if (current == null) return null;
		index--;
		if (pos < 0) {
			current = current.previous;
			if (current == null) return null;
			pos = current.size;
		}
		E data = current.elements[--pos];
		
		return data;
	}

	@Override
	public int nextIndex() {
		return index+1;
	}

	@Override
	public int previousIndex() {
		return index-1;
	}

	@Override
	public void remove() {
		list.remove(index-1);	
	}

	@Override
	public void set(E e) {
		list.set(index-1, e);	
	}

	@Override
	public void add(E e) {
		list.add(index-1, e);
	}
}
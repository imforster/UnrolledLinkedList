package com.imfsoftware.datastructure;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic UnrolledLinkedList allows add,remove and get operations. Position is 1
 * based.
 */
public class UnrolledLinkedList<T> implements List<T>, Iterable<T> {
	private static final String CLOSE_BRACE = "]";
	private static final String OPEN_BRACE = "[";
	private static final String EMPTY_LIST = "[]";

	protected class UnrolledNode<E> {
		UnrolledNode<E> next;
		UnrolledNode<E> previous;
		E[] elements;
		int size;

		@SuppressWarnings("unchecked")
		public UnrolledNode(int capacity) {
			super();
			this.next = null;
			this.elements = (E[]) new Object[capacity];
			this.size = 0;
		}

		synchronized void addElement(E e, int pos) {
			elements[pos] = e;
			size++;
		}

		synchronized E get(int pos) {
			return elements[pos];
		}

		boolean hasPrevious() {
			return previous != null;
		}
	}

	private volatile UnrolledNode<T> head;
	private volatile UnrolledNode<T> tail;
	private AtomicInteger size;
	private int capacity = 5;

	public UnrolledLinkedList(int capacity) {
		super();
		this.head = null;
		this.size = new AtomicInteger(0);
		this.capacity = capacity;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public T get(int pos) {
		checkBounds(pos);
		return get(head, pos - 1);
	}


	@Override
	public int size() {
		return this.size.get();
	}

	@Override
	public boolean contains(Object o) {
		boolean found = false;
		int i = 1;
		while (!found) {
			if (i <= this.size()) {
				if (this.get(i++).equals(o)) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] objArr = new Object[this.size()];
		for (int i = 0; i < this.size(); i++) {
			objArr[i] = this.get(i + 1);
		}
		return objArr;
	}

	@Override
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T[] toArray(T[] a) {
		Class<?> ofArray = a.getClass().getComponentType();
		Object objArr = Array.newInstance(ofArray, this.size.get());

		for (int i = 0; i < this.size.get(); i++) {
			Array.set(objArr, i, this.get(i + 1));
		}

		return (T[]) objArr;
	}

	@Override
	public boolean add(T e) {
		this.add(this.size() + 1, e);
		return false;
	}

	@Override
	public boolean remove(Object o) {
		boolean found = false;
		int i = 1;
		while (!found) {
			if (i <= this.size()) {
				if (this.get(i++).equals(o)) {
					this.remove(i - 1);
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean found = false;
		int items_contained = 0;
		int i = this.size();
		while (!found) {
			if (i > 0) {
				T elem = this.get(i--);
				if (c.contains(elem)) {
					items_contained++;
				}
				if (c.size() == items_contained) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		c.forEach(x -> this.add(x));

		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean found = false;
		int i = this.size();
		int items_removed = 0;
		while (!found) {
			if (i > 0) {
				T elem = this.get(i--);
				if (c.contains(elem)) {
					this.remove(i + 1);
					items_removed++;
				}
				if (c.size() == items_removed) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean found = false;
		int i = this.size();
		int items_retained = 0;
		while (!found) {
			if (i > 0) {
				T elem = this.get(i--);
				if (!c.contains(elem)) {
					this.remove(i + 1);
				} else {
					items_retained++;
				}
			} else {
				return false;
			}
		}
		if (c.size() == items_retained) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		this.head = null;
		this.size.getAndSet(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		isValidInsertPosition(index);
		int i = index;
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			this.add(i++, (T) iter.next());
		}
		return true;
	}

	@Override
	public T set(int index, T element) {
		checkBounds(index);
		UnrolledNode<T> current = head;
		int offset = index;
		while ((offset > current.size) && (current != null)) {
			current = current.next;
			if (current == null)
				throw new InvalidParameterException("Invalid index!");
			offset -= current.size;
		}
		T retVal = current.elements[offset - 1];
		current.elements[offset - 1] = element;
		return retVal;

	}

	@Override
	public void add(int index, T element) {
		isValidInsertPosition(index);
		add(head, element, index, 0);
	}

	@Override
	public T remove(int index) {
		checkBounds(index);
		return remove(head, null, index);
	}

	@Override
	public int indexOf(Object o) {
		boolean found = false;
		int i = 1;
		while (!found) {
			if (i <= this.size()) {
				if (this.get(i++).equals(o)) {
					return i - 1;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		boolean found = false;
		int i = this.size();
		while (!found) {
			if (i > 0) {
				if (this.get(i--).equals(o)) {
					return i + 1;
				}
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return new UnrolledListIterator<T>(this);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new UnrolledListIterator<T>(this, index);
	}

	@Override
	public Iterator<T> iterator() {
		return new UnrolledListIterator<T>(this);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		List<T> subList = new ArrayList<>();
		for (int i = fromIndex; i < toIndex; i++) {
			subList.add(this.get(i));
		}
		return subList;
	}
	

	@Override
	public String toString() {
		StringBuilder resultStr = new StringBuilder();
		UnrolledNode<T> currentNode = head;
		int j = 0;
		if (currentNode == null) return EMPTY_LIST;
		resultStr.append(OPEN_BRACE);
		while (currentNode != null) {
			if (j < currentNode.size) {
				resultStr.append(currentNode.elements[j]);
				j += 1;
				if (j < currentNode.size)
					resultStr.append(",");
			} else {
				resultStr.append(CLOSE_BRACE);
				currentNode = currentNode.next;
				j = 0;
				if (currentNode != null) {
					resultStr.append(OPEN_BRACE);
				} 
			}
		}

		return resultStr.toString();
	}

	protected UnrolledNode<T> getHead() {
		return head;
	}

	protected UnrolledNode<T> add(UnrolledNode<T> currentNode, T elem, int pos, int offset) {
		if (currentNode == null) {
			synchronized (this) {
				if (currentNode == null) {
					currentNode = new UnrolledNode<>(capacity);
					currentNode.addElement(elem, pos - 1);
					size.getAndIncrement();
					if (head == null) {
						head = currentNode;
					}
					return currentNode;
				}
			}
		}

		if ((pos - 1 < capacity) && (pos == currentNode.size + 1)) {
			currentNode.addElement(elem, pos - 1);
			size.getAndIncrement();
		} else if (pos > currentNode.size) {
			currentNode.next = add(currentNode.next, elem, pos - currentNode.size, offset += currentNode.size);
		} else {
			synchronized (currentNode) {
				UnrolledNode<T> nextNode = new UnrolledNode<>(capacity);
				int j = 0;
				for (int i = currentNode.size / 2; i < currentNode.size; i++) {
					nextNode.elements[j++] = currentNode.elements[i];
					currentNode.elements[i] = currentNode.elements[i - 1];
				}

				currentNode.elements[pos - 1] = elem;
				nextNode.size = j;
				nextNode.next = currentNode.next;
				nextNode.previous = currentNode;
				currentNode.size = currentNode.size / 2 + 1;
				currentNode.next = nextNode;
				tail = nextNode;
				size.getAndIncrement();
			}
		}

		return currentNode;
	}

	protected T remove(UnrolledNode<T> currentNode, UnrolledNode<T> prevNode, int pos) {
		if (currentNode == null) {
			return null;
		}

		if (pos > currentNode.size) {
			return remove(currentNode.next, currentNode, pos - currentNode.size);
		} else {
			synchronized (currentNode) {
				T removeElem = currentNode.elements[pos - 1];
				if ((currentNode.size == 1) && (prevNode == null)) {
					this.head = currentNode.next;
				} else if (currentNode.size == 1) {
					prevNode.next = currentNode.next;
				} else {
					for (int i = pos; i <= currentNode.size - 1; i++) {
						currentNode.elements[i - 1] = currentNode.elements[i];
						currentNode.elements[i] = null;
					}
					currentNode.size--;
				}
				size.getAndDecrement();
				return removeElem;
			}
		}
	}
	
	protected T get(UnrolledNode<T> currentNode, int pos) {
		if (currentNode == null) {
			return null;
		}

		if (pos >= currentNode.size) {
			return get(currentNode.next, pos - currentNode.size);
		} else {
			return currentNode.get(pos);
		}

	}

	private void isValidInsertPosition(int pos) {
		if ((pos > size.get() + 1) || (pos < 1)) {
			throw new IllegalArgumentException("Invalid position specified.");
		}

	}
	
	private void checkBounds(int pos) {
		if ((size() == 0) || (pos > size())) {
			throw new IndexOutOfBoundsException("Invalid index specified");
		}
	}
}

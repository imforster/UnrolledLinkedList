package com.imfsoftware.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class UnrolledLinkedListFunctionTest {
	public static void main(String[] args) {
		UnrolledLinkedList<Integer> linkList = new UnrolledLinkedList<Integer>(5);
		linkList.add(1, new Integer(1));
		linkList.add(2, new Integer(2));
		linkList.add(3, new Integer(3));
		linkList.add(4, new Integer(4));
		linkList.add(5, new Integer(5));
		linkList.add(6, new Integer(6));

		System.out.println("Item 3: " + linkList.get(3));

		linkList.add(new Integer(7), 3);

		for (int i = 8; i < 500; i++) {
			linkList.add(new Integer(i));
		}

		System.out.println("Item 3: " + linkList.get(3));

		linkList.remove(3);

		System.out.println("Item 3: " + linkList.get(3));

		System.out.println(linkList.toString());

		System.out.println("Item 450: " + linkList.get(450));

		linkList.add(7, new Integer(7));

		System.out.println(linkList.toString());

		System.out.println("Item 450: " + linkList.get(450));

		System.out.println("Size: " + linkList.size());

		linkList.remove(1);

		linkList.remove(498);

		System.out.println(linkList.toString());

		System.out.println("Item 450: " + linkList.get(450));

		System.out.println("Size: " + linkList.size());
		try {
			linkList.remove(0);
			throw new Exception("Remove 0 failed!");
		} catch (Exception e) {
			// good
			System.out.println("Unable to remove 0.");
		}

		try {
			linkList.remove(498);
			throw new Exception("Remove 498 failed!");
		} catch (Exception e) {
			// good
			System.out.println("Unable to remove 498.");
		}
		try {
			linkList.remove(497);
			System.out.println("Remove 497 worked!");

			linkList.remove(496);
		} catch (Exception e) {
			// good
			System.out.println("Unable to remove 497.");
			e.printStackTrace();
		}

		linkList.subList(400, 405).forEach(System.out::println);

		System.out.println("index of 400: " + linkList.indexOf(400));

		System.out.println("index of 495: " + linkList.lastIndexOf(495));

		System.out.println("value of 495: " + linkList.get(linkList.indexOf(495)));

		System.out.println("Size: " + linkList.size());

		ListIterator<Integer> lstIter = linkList.listIterator(300);

		int count = 0;
		while (lstIter.hasNext()) {
			System.out.println(lstIter.next());
			count++;
			if (count == 10)
				break;
		}
		lstIter.remove();
		lstIter.remove();
		System.out.println();
		lstIter.set(311);
		linkList.subList(300, 315).forEach(System.out::println);

		UnrolledLinkedList<String> stringList = new UnrolledLinkedList<String>(5);

		stringList.add(1, "apple");
		stringList.add(2, "orange");
		stringList.add(3, "bannana");
		stringList.add(4, "grape");

		stringList.remove("grape");

		List<String> strList = new ArrayList<>();
		strList.add("mandarin orange");
		strList.add("pear");
		stringList.addAll(strList);

		System.out.println("contains: " + stringList.contains("pear"));

		System.out.println("Fruits: " + stringList.toString());

		System.out.println("Contains all: " + stringList.containsAll(strList));

		stringList.removeAll(strList);

		System.out.println("Fruits: " + stringList.toString());

		stringList.addAll(strList);

		System.out.println("Fruits: " + stringList.toString());

		stringList.retainAll(strList);

		System.out.println("Fruits: " + stringList.toString());

		ListIterator<String> strIter = stringList.listIterator();

		System.out.println("Iterator output.");
		while (strIter.hasNext()) {
			System.out.println(strIter.next());
			System.out.println(strIter.nextIndex());
		}

		System.out.println("Iterator in reverse output.");
		while (strIter.hasPrevious()) {
			System.out.println(strIter.previous());
		}

		System.out.println("Array output.");
		Object[] objArr = stringList.toArray();
		for (Object o : objArr) {
			System.out.println(o.toString());
		}

		System.out.println("Typed Array output.");
		String[] strArr = stringList.toArray(new String[0]);
		for (String s : strArr) {
			System.out.println(s.toString());
		}

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			list.add(i);

		ListIterator<Integer> iter = list.listIterator();

		iter.next();
		iter.next();
		iter.next();
		System.out.println(iter.previousIndex());
		System.out.println(iter.nextIndex());
		iter.remove();
		System.out.println(iter.nextIndex());

		list.forEach(System.out::println);
	}
}

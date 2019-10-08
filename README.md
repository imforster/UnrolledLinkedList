# UnrolledLinkedList Java Implementation

## Overview
In a recent job interview I was introduced to the UnrolledLinkedList data structure.  Key characteristics of this data structure builds on the advantages of low cost insert of a Linked List and allows for a faster than linear lookup for finding an element at a particular position. The interview question only covered a portion of the data structure for get and add at position aspects.  The Implementation based on the idea that a non-zero based insert is supported so if you request an element at pos 1 it is the first element.

The implemented structure supports the List interface and allows for both forward and reverse iteration.

For more information regarding the UnrolledLinkedList see: [UnrolledLinkedList ](https://en.wikipedia.org/wiki/Unrolled_linked_list)

## Variance from defined structure
The current implementation does not rebalance the array on removal.  If the last element is being removed from a node than the node is remove from the linked list structure.

## License
MIT License

Copyright (c) 2019 imforster

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

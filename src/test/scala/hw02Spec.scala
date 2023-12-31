import org.scalatest.flatspec.AnyFlatSpec
import popl.hw02._
import Tree._

class hw02Spec extends AnyFlatSpec:
  
  // Have a few trees pre-made to test against, both valid and invalid

  val t1 = Node(Empty, 2, Empty)
  val t2 = Node(Empty, 4, t1)
  val t3 = Node(t2, 4, Empty)
  val t4 = Node(t1, 4, Node(Node(Empty, 5, Empty), 4, Empty))
  val t5 = Node(t1, 2, Empty)
  val t6 = Node(Empty, 4, Empty)
  val t7 = Node(Node(Empty, 4, Empty), 2, Empty)
  val t8 = Node(Node(Empty, 3, Empty), 2, Node(Empty, 1, Empty))
  val t9 = Node(Node(Empty, 3, Empty), 2, Empty)
  val t10 = Node(Empty, 2, Node(Empty, 1, Empty))
  val t11 = Node(Empty, 3, Node(Empty, 1, Empty))

  // repOk

  "repOk" should "ensure that a tree is properly ordered" in {
    assert(repOk(Empty))
    assert(repOk(t1))
    assert(repOk(t2))
    assert(repOk(t8))
    assert(!repOk(t3))
    assert(!repOk(t4))
    assert(!repOk(t5))
  }

  // insertion

  "insert" should "insert numbers as leaves in trees at the proper position" in {
    assert(insert(Empty, 2) === t1)
    assert(insert(t1, 2) === t1)
    assert(insert(t6, 2) === t2)
    assert(insert(t1, 4) === t7)
  }

  // deleteMin

  "deleteMin" should "remove the smallest value from a tree, and provide the resulting tree" in {
    assert(deleteMin(t7) === (t6, 2))
    assert(deleteMin(t1) === (Empty, 2))
    assert(deleteMin(t5) === (t1, 2))
    assert(deleteMin(t8) === (Node(Node(Empty, 3, Empty), 2, Empty), 1))
  }

  // delete

  "delete" should "remove a given value from a tree, if present" in {
    assert(delete(t1, 2) === Empty)
    assert(delete(t5, 2) === t1)
    assert(delete(t8, 1) === t9)
    assert(delete(t8, 3) === t10)
    assert(delete(t8, 2) === t11)
    assert(delete(t8, 4) === t8)
  }
  // Some more testing code that uses the Scala List library.  The function 'treeFromList'
  // inserts all the elements in the list into the tree.  It is likely the code
  // will look quite mysterious now.  We will break it down eventually in the
  // course, but you can try to figure it out by reading the Scala documentation yourself.

  // Regardless, you can use this function to help test your code.
  def treeFromList(l: List[Int]): Tree = l.foldLeft(Empty: Tree)(insert)
  
  "insert" should "produce trees that satisfy repOk" in {
    assert(repOk(treeFromList(List(3, 4, 7, 2, 1, 10))))
    assert(repOk(treeFromList(List(1, 2, 3, 4, 5))))
    assert(repOk(treeFromList(List(9, 8, 7, 6, 5))))
    assert(repOk(treeFromList(List(1, 1, 1, 1))))
  }

  "insert-delete" should "produce tress that satisfy repOk" in {
    val ins = (n: Int) => (t: Tree) => insert(t, n)
    val del = (n: Int) => (t: Tree) => delete(t, n)
    val l = List(ins(2), ins(6), ins(10), ins(22), del(4), del(6), ins(4), del(10), del(4))
    l.foldLeft(Empty: Tree)(
      (t, f) =>
        val t1 = f(t)
        assert(repOk(t1))
        t1
    )
  }

  // Eval
  
  "eval+" should "perform addition" in
    assert(eval("1 + 1") === 2)

  "eval-" should "perform subtraction" in
    assert(eval("4 - 2") === 2)

  "eval*" should "perform multiplication" in
    assert(eval("4 * 2") === 8)

  "eval/" should "perform division" in
    assert(eval("4 / 2") === 2)
    assert(eval("-4 / 2") === -2)

  "eval/0" should "division by 0 should yield Infinity" in
    assert(eval("4 / 0") === Double.PositiveInfinity)
    assert(eval("-4 / 0") === Double.NegativeInfinity)

end hw02Spec
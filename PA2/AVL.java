// AVL Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)

public class AVL extends BST
{
  public AVL() {
    super();
  }

  public void insert(String key) {
    root = AVLInsert(root, key);
  }
  private Node AVLInsert(Node T, String key) {
    if(T == null) {
      size++;
      return new Node(key);
    }

    if(key.compareTo(T.key)<0) {
      T.left = AVLInsert(T.left, key);

      if(height(T.left) - height(T.right)>=2) { // if imbalanced
        if(key.compareTo(T.left.key)<0) { // saved in left subtree of left subtree
          T = RotateRight(T);
        } else { // saved in right subtree of left subtree
          T = RotateLeftRight(T);
        }
      }
    } else if(key.compareTo(T.key)>0) {
      T.right = AVLInsert(T.right, key);

      if(height(T.right) - height(T.left)>=2) { // if imbalanced
        if(key.compareTo(T.right.key)>0) { // saved in right subtree of right subtree
          T = RotateLeft(T);
        } else { // saved in left subtree of right subtree
          T = RotateRightLeft(T);
        }
      }
    } else {
      T.frequency++;
    }
    T.height = 1 + Math.max(height(T.left), height(T.right));

    return T;
  }
  private int height(Node T) {
    if(T==null) return 0;
    else return T.height;
  }
  private Node RotateRight(Node T) {
    Node tmp = T.left;
    T.left = tmp.right;
    tmp.right = T;

    // height update
    T.height = 1 + Math.max(height(T.left), height(T.right));
    tmp.height = 1 + Math.max(height(tmp.left), height(tmp.right));
    return tmp;
  }
  private Node RotateLeft(Node T) {
    Node tmp = T.right;
    T.right = tmp.left;
    tmp.left = T;

    // height update
    T.height = 1 + Math.max(height(T.left), height(T.right));
    tmp.height = 1 + Math.max(height(tmp.left), height(tmp.right));
    return tmp;
  }
  private Node RotateLeftRight(Node T) {
    T.left = RotateLeft(T.left);
    return RotateRight(T);
  }
  private Node RotateRightLeft(Node T) {
    T.right = RotateRight(T.right);
    return RotateLeft(T);
  }
}


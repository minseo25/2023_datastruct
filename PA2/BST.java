// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)

public class BST { // Binary Search Tree implementation
  protected static class Node {
    String key;
    int frequency;
    int access_cnt;
    int height; // used for AVL Tree
    Node left;
    Node right;

    public Node(String key) {
      this.key = key;
      this.frequency = 1;
      this.access_cnt = 0;
      this.height = 1;
      this.left = null;
      this.right = null;
    }
  }
  protected boolean NOBSTified = false;
  protected boolean OBSTified = false;
  protected int size;
  protected Node root;

  // array for OBST & NOBST
  protected int[][] obst_cost;
  protected int[][] obst_root;
  protected int[] cumulative_frequency;
  protected Node[] sorted_nodes;
  protected int node_idx = 0;


  public BST() {
    this.root = null;
    this.size = 0;
  }

  public int size() {
    return this.size;
  }

  public void insert(String key) {
    if(root == null) {
      size++;
      root = new Node(key);
      return;
    }

    Node curr = root, prev = null;

    while(curr!=null) {
      if(key.compareTo(curr.key)==0) {
        curr.frequency++;
        return;
      }
      prev = curr;
      if(key.compareTo(curr.key)<0) curr = curr.left;
      else curr = curr.right;
    }

    size++;
    if(key.compareTo(prev.key)<0) prev.left = new Node(key);
    else prev.right = new Node(key);
  }
  // ** DEPRECATED ** RECURSION may take more time
  // public void insert(String key) {
  //   root = insert(root, key);
  // }
  // private Node insert(Node T, String key) {
  //   if(T == null) {
  //     size++;
  //     return new Node(key);
  //   }
  //   if(key.compareTo(T.key)<0) {
  //     T.left = insert(T.left, key);
  //   } else if(key.compareTo(T.key)>0) {
  //     T.right = insert(T.right, key);
  //   } else {
  //     T.frequency++;
  //   }
  //   return T;
  // }

  public boolean find(String key) {
    Node curr = root;

    while(curr!=null) {
      curr.access_cnt++;
      if(key.compareTo(curr.key)==0) return true;

      if(key.compareTo(curr.key)>0) curr = curr.right;
      else curr = curr.left;
    }

    return false;
  }
  // ** DEPRECATED ** RECURSION may take more time
  // public boolean find(String key) {
  //   return find(root, key);
  // }
  // private boolean find(Node T, String key) {
  //   if(T == null) return false;

  //   T.access_cnt++;
  //   if(key.compareTo(T.key)<0) return find(T.left, key);
  //   else if(key.compareTo(T.key)>0) return find(T.right, key);
  //   else return true;
  // }

  public int sumFreq() {
    return sumFreq(root);
  }
  private int sumFreq(Node T) {
    if(T == null) return 0;
    return T.frequency + sumFreq(T.left) + sumFreq(T.right);
  }

  public int sumProbes() {
    return sumProbes(root);
  }
  private int sumProbes(Node T) {
    if(T == null) return 0;
    return T.access_cnt + sumProbes(T.left) + sumProbes(T.right);
  }
  
  public int sumWeightedPath() {
    return sumWeightedPath(root, 1);
  }
  private int sumWeightedPath(Node T, int depth) {
    if(T==null) return 0;
    return T.frequency*depth + sumWeightedPath(T.left, depth+1) + sumWeightedPath(T.right, depth+1);
  }

  public void resetCounters() {
    resetCounters(root);
  }
  private void resetCounters(Node T) {
    if(T != null) {
      T.frequency = 0;
      T.access_cnt = 0;
      resetCounters(T.left);
      resetCounters(T.right);
    }
  }

  public void nobst() { // Set NOBSTified to true.
    NOBSTified = true;

    // build NOBST
    root = buildNOBST(1,size);
  }
  private Node buildNOBST(int low, int high) {
    if(low>high) return null;

    // temporary min_idx & min_diff
    int mid, diff;
    // range of min_idx [l,h]
    int l = low, h= high;
    // get idx that minimize difference of weight
    int min_idx = -1;
    int min_diff = Integer.MAX_VALUE;

    do {
      mid = (l+h)/2;
      diff = getWeightSum(mid+1, high) - getWeightSum(low, mid-1);

      if(Math.abs(min_diff)>Math.abs(diff)) { // update if weight diff is smaller than min_diff
        min_diff = diff;
        min_idx = mid;
      } else if(Math.abs(min_diff)==Math.abs(diff) && diff>0 && min_diff<0) { // if same, then we choose the case which makes right weight heavier
        min_diff = diff;
        min_idx = mid;
      }

      if(diff<0) h = mid-1;
      else l = mid+1;
    } while(l<=h && min_diff!=0);

    // Now build sub_NOBST
    Node new_root = sorted_nodes[min_idx];
    new_root.left = buildNOBST(low, min_idx-1);
    new_root.right = buildNOBST(min_idx+1, high);

    return new_root;
  }
  private int getWeightSum(int low, int high) {
    if(low>high) return 0;
    return cumulative_frequency[high] - cumulative_frequency[low-1];
  }

  public void obst() { // Set OBSTified to true.
    OBSTified = true;
    // build OBST

    buildMatrix();
    root = buildOBST(1,size);
  }
  private void buildMatrix() {
    int minIdx, minCost, lidx, hidx, tmpCost;
    obst_cost = new int[size+2][size+1];
    obst_root = new int[size+2][size+1];

    for(int low=size+1; low>=1; low--) {
      for(int high=low-1; high<=size; high++) {
        if(low>high) continue;
        else if(low==high) {
          obst_cost[low][high] = cumulative_frequency[high]-cumulative_frequency[low-1];
          obst_root[low][high] = low;
        } else {
          // root that minimizes cost(low,high) => btw lidx & hidx
          lidx = obst_root[low][high-1];
          hidx = obst_root[low+1][high];

          // find root
          minIdx = lidx;
          minCost = obst_cost[low][lidx-1] + obst_cost[lidx+1][high];
          for(int r=lidx+1; r<=hidx; r++) {
            tmpCost = obst_cost[low][r-1] + obst_cost[r+1][high];
            if(tmpCost<minCost) {
              minCost = tmpCost;
              minIdx = r;
            }
          }

          obst_root[low][high] = minIdx;
          obst_cost[low][high] = minCost + cumulative_frequency[high] - cumulative_frequency[low-1];
        }
      }
    }
  }
  private Node buildOBST(int low, int high) {
    int curr_root = obst_root[low][high];
    if(curr_root==0) return null;

    Node node = sorted_nodes[curr_root];
    node.left = buildOBST(low, curr_root-1);
    node.right = buildOBST(curr_root+1, high);

    return node;
  }

  public void print() {
    if(node_idx<=0) {
      // print & save cumulative sum of frequency & order nodes by key
      cumulative_frequency = new int[size+1];
      sorted_nodes = new Node[size+1];
      print1(root);
    } else {
      // simply print
      print2(root);
    }
  }
  private void print1(Node T) {
    if(T != null) {
      print1(T.left);

      ++node_idx;
      cumulative_frequency[node_idx] = cumulative_frequency[node_idx-1] + T.frequency;
      sorted_nodes[node_idx] = T;
      System.out.println('['+T.key+':'+T.frequency+':'+T.access_cnt+']');

      print1(T.right);
    }
  }
  private void print2(Node T) {
    if(T != null) {
      print2(T.left);
      System.out.println('['+T.key+':'+T.frequency+':'+T.access_cnt+']');
      print2(T.right);
    }
  }
}


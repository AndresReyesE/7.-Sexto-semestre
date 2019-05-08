package Zipper;

public class Tuple <Bouncer, Individual> {
	
	private final Bouncer left;
	private final Individual right;
	
	public Tuple(Bouncer left, Individual right) {
		this.left = left;
		this.right = right;
	}
	
	public Bouncer getLeft() { return left; }
	public Individual getRight() { return right; }
	
	
	public String toString() {
		return "{" + left + ", " + right + "}";
	}
}

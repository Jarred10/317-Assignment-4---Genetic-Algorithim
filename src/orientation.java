// Class to represent a single orientation of a box
public class orientation implements Comparable<orientation> {

	int id, height, width, depth, area;

	// Construct and orientation using its id height width and depth
	public orientation(int id, int height, int width, int depth) {
		this.id = id;
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.area = depth * width;
	}

	// Compare boxes based on area then width then depth then height
	@Override
	public int compareTo(orientation o) {
		if(area > o.area){
			return -1;
		}
		else if (area == o.area){
			if(width > o.width) return -1;
			else if(width == o.width){
				if(depth > o.depth) return -1;
				else if(depth == o.depth){
					if(height > o.height) return -1;
					else if(height == o.height) return 0;
					else return 1;
				}
				else return 1;
			}
			else return 1;
		}
		else return 1;
	}
	
	// Check that this orientation will fit on another given orientation
	public boolean fitsOn(orientation o){
		return (width < o.width && depth < o.depth);
	}


	// To string to print the orientation
	@Override
	public String toString(){
		return "{ID: " + id + ", Height: " + height + ", Width: " + width + ", Depth: " + depth + ", Area: " + area + "}";
	}
}

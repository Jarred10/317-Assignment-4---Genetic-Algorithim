public class orientation implements Comparable<orientation> {

	int height, width, depth, area;

	public orientation(int height, int width, int depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.area = depth * width;
	}

	@Override
	public int compareTo(orientation o) {
		if(width > o.width){
			return -1;
		}
		else if (width == o.width){
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
	
	public boolean fitsOn(orientation o){
		return (width < o.width && depth < o.depth);
	}


	@Override
	public String toString(){
		return "Height: " + height + ", Width: " + width + ", Depth: " + depth + ", Area: " + area + ". ";
	}
}

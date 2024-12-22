package engine.maths;

public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public static Matrix4f identity(){ // base matrice that all matrices based on
        // 1000
        // 0100
        // 0010
        // 0001
        Matrix4f result = new Matrix4f();

        for(int i = 0; i  < SIZE; i++){
            for(int j = 0; j  < SIZE; j++){
                result.set(i, j, 0); // all the elements in the matrix set to zero.
            }
        }
        
        result.set(0, 0, 1);//x, y, value // set operation stands for 
        result.set(1, 1, 1);
        result.set(2, 2, 1);
        result.set(3, 3, 1);

        return result; // return new matrix
    }
    
    public static Matrix4f translate(Vector3f translate){
        // 100x
        // 010y
        // 001z
        // 0001
        Matrix4f result = Matrix4f.identity();
        result.set(0, 3, translate.getX());
        result.set(1, 3, translate.getY());
        result.set(2, 3, translate.getZ());
        return result;
    }

    public static Matrix4f rotate(float angle, Vector3f axis){
        Matrix4f result = Matrix4f.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        // Create the matrix
		result.set(0, 0, cos + axis.getX() * axis.getX() * C);
		result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
		result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
		result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
		result.set(1, 1, cos + axis.getY() * axis.getY() * C);
		result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
		result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
		result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
		result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);
        return result;
    }

    public float get(int x, int y){
        return elements[y * SIZE + x]; // converts 2D numbers to 1D number.
    }

    public void set(int x, int y, float value){
        elements[y * SIZE + x] = value;
    }

    public float[] getAll(){
        return elements;
    }
}

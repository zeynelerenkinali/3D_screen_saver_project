package engine.graphics;

import org.joml.Vector4f;

public class Light 
{   
    private Vector4f ambientColour, diffuseColour, specularColour;
    private float reflectance;
    private Material material;
    public static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    public Light(){
        this.ambientColour = DEFAULT_COLOR;
        this.diffuseColour = DEFAULT_COLOR;
        this.specularColour = DEFAULT_COLOR;
        this.material = null;
        this.reflectance = 0.0f;
    }

    public Light(Vector4f colour, float reflectance, Material material){
        this(colour,colour,colour, reflectance, material);
    }

    public Light(Vector4f colour, float reflectance){
        this(colour,colour,colour, reflectance, null);
    }

    public Light(Material material){
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, 0.0f, null);
    }

    public Light(Vector4f ambientColour, Vector4f diffuseColour, Vector4f specularColour, float reflectance,
            Material material) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.reflectance = reflectance;
        this.material = material;
    }

    public boolean hasMaterial(){
        return material != null;
    }

    public Vector4f getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Vector4f ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Vector4f getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vector4f diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vector4f getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vector4f specularColour) {
        this.specularColour = specularColour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public static Vector4f getDefaultColor() {
        return DEFAULT_COLOR;
    }

    
}










package engine.io;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class ModelLoader {

    private static Vertex[] vertexListvar;
    private static String texturePathvar;
    private static int[] indicesListvar;
    
        public static Mesh loadModel(String filePath, String texturePath){
        // contains all of our 3d model data
        texturePathvar = texturePath;
        AIScene scene = Assimp.aiImportFile(filePath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);

        // check loaded
        if(scene == null) System.err.println("Couldn't load model at " + filePath);

        // with getting the mesh.get(0) we took the first mesh there maybe exist more than one mesh in some objects
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexCount = mesh.mNumVertices();

        // We have num vertices, we will create a for loop to add the verticies into our mesh object

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        // Go through all vertices and add into objects
        // But first we need to create that object with mesh class; list of vertices, list of indices, and material

        Vertex[] vertexList = new Vertex[vertexCount];

        for(int i = 0; i < vertexCount; i++){
            // We will get actual vector
            AIVector3D vertex = vertices.get(i);
            // Now we have that vector now we will get the information out of it
            Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

            // now same thing for normal
            AIVector3D normal = vertices.get(i);
            Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

            // Now we need to get our texture coords
            Vector2f meshTextureCoord = new Vector2f(0.0f, 0.0f);
            if(mesh.mNumUVComponents().get(0) != 0){
                // now we understand there is texture so we will set texture instead of default 0.0f
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTextureCoord.setX(texture.x());
                meshTextureCoord.setY(texture.y());
                // we do not need z component because texture is 2D
            }
            // now we can actually create our vertex and add it to our list 
            vertexList[i] = new Vertex(meshVertex, new Vector3f(0.0f, 0.0f, 0.0f) , meshNormal, meshTextureCoord);
        }
        
        // we need indices
        int faceCount = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] indicesList = new int[faceCount * 3]; // Since there is three points on a triangle we need to multiply that by three

        for(int i = 0; i < faceCount; i++){
            AIFace face = indices.get(i);
            indicesList[i*3 + 0] = face.mIndices().get(0); // we get the indices of the triangle and set it to first one on the indices array and offset by zero
            indicesList[i*3 + 1] = face.mIndices().get(1); // I did 2, 3 because 1 belongs to color value.
            indicesList[i*3 + 2] = face.mIndices().get(2);
        }
        indicesListvar = indicesList;
        vertexListvar = vertexList;
        texturePathvar = texturePath;
        return new Mesh(vertexList, indicesList, new Material(texturePath));
    }

    public static Mesh changeTexture(String texturePath){
        if(vertexListvar != null && indicesListvar != null)
            return new Mesh(vertexListvar, indicesListvar, new Material(texturePath));
        return null;
    }
}

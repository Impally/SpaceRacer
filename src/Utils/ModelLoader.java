package Utils;

import com.jogamp.opengl.util.texture.Texture;
import input.state.Vertex3f;
import objects.DrawPair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import objects.Material;

/**
 * Created by rodge on 12/16/2015.
 */
public class ModelLoader {
    public static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    public static ArrayList<DrawPair> loadOBJModel(final GL2 gl, File modelFile, File materialFile) {
        BufferedReader br;
        ArrayList<Vertex3f> verticies = new ArrayList<Vertex3f>();
        ArrayList<Vertex3f> texturesCoords = new ArrayList<Vertex3f>();
        ArrayList<Vertex3f> normals = new ArrayList<Vertex3f>();
        HashMap<String, Material> materials = new HashMap<String, Material>();
        try {
            br = new BufferedReader(new FileReader(materialFile));
            String line;
            Material currentMat = null;
            try {
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        currentMat = null;
                        continue;
                    }
                    String prefix = line.split("\\s+")[0];

                    if (prefix.equals("#")) {
                        continue;
                    } else if (prefix.equals("newmtl")) {
                        // new material
                        currentMat = addMaterial(line, materials);
                        continue;
                    } else if (prefix.equals("Ns")) {
                        // specular coeff
                        currentMat.Ns = getFloat(line);
                        continue;
                    } else if (prefix.equals("Ni")) {
                        //
                        currentMat.Ni = getFloat(line);
                        continue;
                    } else if (prefix.equals("d")) {
                        // transparency (one implementation)
                        currentMat.d = getFloat(line);
                        continue;
                    } else if (prefix.equals("Tr")) {
                        // transparency (another implementation)
                        currentMat.Tr = getFloat(line);
                        continue;
                    } else if (prefix.equals("Tf")) {
                        //
                        currentMat.Tf = getVertex(line);
                        continue;
                    } else if (prefix.equals("illum")) {
                        // illumination models
						/*
						 * 0. Color on and Ambient off
						 * 1. Color on and Ambient on
						 * 2. Highlight on
						 * 3. Reflection on and Ray trace on
						 * 4. Transparency: Glass on, Reflection: Ray trace on
						 * 5. Reflection: Fresnel on and Ray trace on
						 * 6. Transparency: Refraction on, Reflection: Fresnel off and Ray trace on
						 * 7. Transparency: Refraction on, Reflection: Fresnel on and Ray trace on
						 * 8. Reflection on and Ray trace off
						 * 9. Transparency: Glass on, Reflection: Ray trace off
						 * 10. Casts shadows onto invisible surfaces
						 */
                        currentMat.illum = getFloat(line);
                        continue;
                    } else if (prefix.equals("Ka")) {
                        // ambient color rgb
                        currentMat.Ka = getVertex(line);
                        continue;
                    } else if (prefix.equals("Kd")) {
                        // diffuse color rbg
                        currentMat.Kd = getVertex(line);
                        continue;
                    } else if (prefix.equals("Ks")) {
                        // specular color rgb
                        currentMat.Ks = getVertex(line);
                        continue;
                    } else if (prefix.equals("Ke")) {
                        // emission color rgb
                        currentMat.Ke = getVertex(line);
                        continue;
                    } else if (prefix.equals("map_Ka")) {
                        //
                        currentMat.map_Ka = getString(line);
                        continue;
                    } else if (prefix.equals("map_Kd")) {
                        //
                        currentMat.map_Kd = getString(line);
                        continue;
                    } else if (prefix.equals("map_Ns")) {
                        //
                        currentMat.map_Ns = getString(line);
                        continue;
                    } else if (prefix.equals("map_d")) {
                        //
                        currentMat.map_d = getString(line);
                        continue;
                    } else if (prefix.equals("map_bump")) {
                        //
                        currentMat.map_bump = getString(line);
                        continue;
                    } else {
                        continue;
                    }
                }
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(1);
        }

        ArrayList<DrawPair> drawPairs = new ArrayList<DrawPair>();
        boolean drawing = false;
        Integer drawId = null;
        try {
            br = new BufferedReader(new FileReader(modelFile));
            String line;
            Material currentMat = null;
            try {
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) {
                        currentMat = null;
                        if (drawing) {
                            gl.glEndList();
                            drawing = false;
                        }
                        continue;
                    }
                    String prefix = line.split("\\s+")[0];
                    if (prefix.equals("#")) {
                        continue;
                    } else if (prefix.equals("s")) {
                        // smoothing groups
                        continue;
                    } else if (prefix.equals("v")) {
                        // vertex
                        addVertex(line, verticies);
                        continue;
                    } else if (prefix.equals("vt")) {
                        // vertex texture
                        addVertex(line, texturesCoords);
                        continue;
                    } else if (prefix.equals("vn")) {
                        // vertex normal
                        addVertex(line, normals);
                        continue;
                    } else if (prefix.equals("usemtl")) {
                        // use material
                        currentMat = materials.get(getString(line));
                        drawing = true;
                        drawId = gl.glGenLists(1);
                        gl.glNewList(drawId, GL2.GL_COMPILE);
                        continue;
                    } else if (prefix.equals("f")) {
                        // face
                        drawFace(gl, line.substring(2), verticies, normals, texturesCoords, currentMat, materialFile.getParentFile()
                                .getAbsolutePath(), drawPairs, drawId);
                        continue;
                    } else {
                        continue;
                    }
                }
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(1);
        }

        gl.glEndList();

        return drawPairs;

    }

    private static String getString(String line) {
        String[] elements = line.split("\\s+");
        return elements[1];
    }

    private static Vertex3f getVertex(String line) {
        String[] elements = line.split("\\s+");
        Vertex3f vertex = new Vertex3f();
        vertex.x = (Float.parseFloat(elements[1]));
        vertex.y = (Float.parseFloat(elements[2]));
        if (elements.length >= 4) {
            vertex.z = (Float.parseFloat(elements[3]));
        }
        return vertex;
    }

    private static float getFloat(String line) {
        String[] elements = line.split("\\s+");
        return Float.parseFloat(elements[1]);
    }

    private static Material addMaterial(String line, HashMap<String, Material> materials) {
        String matName = line.split("\\s+")[1];
        Material newMat = new Material();
        materials.put(matName, newMat);
        return newMat;
    }

    private static void drawFace(final GL2 gl, String line, ArrayList<Vertex3f> verticies, ArrayList<Vertex3f> normals,
                                 ArrayList<Vertex3f> texturesCoords, Material material, String materialPath, ArrayList<DrawPair> drawPairs, int drawId) {

        String[] groups = line.trim().split("\\s+");
        String fullMaterialPath = materialPath + "\\" + material.map_Kd;

        if (material.map_Kd != null) {
            Texture texture = null;
            if (material.map_Kd.contains(":")) {
                fullMaterialPath = material.map_Kd;
            }
            if (textures.get(material.map_Kd) == null) {
                texture = TextureLoader.loadTexture(new File(fullMaterialPath));
                textures.put(material.map_Kd, texture);
                drawPairs.add(new DrawPair(texture, drawId));
            }

        }
        gl.glBegin(GL2.GL_POLYGON);
        for (String group : groups) {
            String[] elements = group.split("/");
            Integer vertexId = null;
            Integer textureId = null;
            Integer normalId = null;
            // NOTE DO NOT USE TRY CATCH IF YOU DO NTO HAVE ALL 3 VALUES, IT IS EXPENSIVE TO CATCH EXPECTIONS
            try {
                vertexId = Integer.parseInt(elements[0]);
            } catch (Exception e) {
                // System.err.println("Vertex id must be an int.");
            }
            try {
                textureId = Integer.parseInt(elements[1]);
            } catch (Exception e) {
                // System.err.println("Texture id must be an int.");
            }
            try {
                normalId = Integer.parseInt(elements[2]);
            } catch (Exception e) {
                // System.err.println("Normal id must be an int.");
            }

            if (normalId != null) {
                Vertex3f normal = normals.get(normalId - 1);
                gl.glNormal3f(normal.x, normal.y, normal.z);
            }
            if (textureId != null) {
                Vertex3f textureCoord = texturesCoords.get(textureId - 1);
                gl.glTexCoord2f(textureCoord.x, textureCoord.y);
            }
            if (material.Kd != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, material.Kd.toArray(), 0);
            }
            if (material.Ka != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, material.Ka.toArray(), 0);
            }
            if (material.Ke != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, material.Ke.toArray(), 0);
            }
            if (material.Ks != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, material.Ks.toArray(), 0);
            }
            if (vertexId != null) {
                Vertex3f vertex = verticies.get(vertexId - 1);
                gl.glVertex3f(vertex.x, vertex.y, vertex.z);
            }
        }
        gl.glEnd();
    }

    private static void addVertex(String line, ArrayList<Vertex3f> arrayList) {
        arrayList.add(getVertex(line));
    }
}

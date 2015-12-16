package objects;

import input.state.Vertex3f;

/**
 * Created by rodge on 12/16/2015.
 */
public class Material {
    public float Ns;
    public float Ni;
    public float d;
    public float Tr;
    public Vertex3f Tf;
    public float illum;
    public Vertex3f Ka;
    public Vertex3f Kd;
    public Vertex3f Ks;
    public Vertex3f Ke;
    public String map_Ka;
    public String map_Kd;
    public String map_Ns;
    public String map_d;
    public String map_bump;

    public Material() {

    }

    @Override
    public String toString() {
        return "Material [Ns=" + Ns + ", Ni=" + Ni + ", d=" + d + ", Tr=" + Tr + ", Tf=" + Tf + ", illum=" + illum + ", Ka=" + Ka + ", Kd=" + Kd
                + ", Ks=" + Ks + ", Ke=" + Ke + ", map_Ka=" + map_Ka + ", map_Kd=" + map_Kd + ", map_Ns=" + map_Ns + ", map_d=" + map_d
                + ", map_bump=" + map_bump + "]";
    }

}
package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.*;

import java.util.HashMap;
import java.util.Map;

public class SWorldGenFactory {

    private static Map a = new HashMap();
    private static Map b = new HashMap();
//    private static Map c = new HashMap();
//    private static Map d = new HashMap();

    private static void b(Class oclass, String s) {
        a.put(s, oclass);
        b.put(oclass, s);
    }

//    static void a(Class oclass, String s) {
//        c.put(s, oclass);
//        d.put(oclass, s);
//    }

    public static String a(StructureStart structurestart) {
        return (String) b.get(structurestart.getClass());
    }

    public static String a(StructurePiece structurepiece) {
        return WorldGenFactory.a(structurepiece);
    }

    public static StructureStart a(NBTTagCompound nbttagcompound, World world) {
        StructureStart structurestart = null;

        try {
            Class oclass = (Class) a.get(nbttagcompound.getString("id"));

            if (oclass != null) {
                structurestart = (StructureStart) oclass.newInstance();
            }
        } catch (Exception exception) {
            world.getLogger().warning("Failed Start with id " + nbttagcompound.getString("id"));
            exception.printStackTrace();
        }

        if (structurestart != null) {
            structurestart.a(world, nbttagcompound);
        } else {
            world.getLogger().warning("Skipping Structure with id " + nbttagcompound.getString("id"));
        }

        return structurestart;
    }

    public static StructurePiece b(NBTTagCompound nbttagcompound, World world) {
        return WorldGenFactory.b(nbttagcompound, world);
    }

    static {
        b(WorldGenMineshaftStart.class, "Mineshaft");
        b(WorldGenVillageStart.class, "Village");
        b(WorldGenNetherStart.class, "Fortress");
        b(WorldGenStronghold2Start.class, "Stronghold");
        b(SWorldGenLargeFeatureStart.class, "Temple");
        WorldGenMineshaftPieces.a();
        WorldGenVillagePieces.a();
        WorldGenNetherPieces.a();
        WorldGenStrongholdPieces.a();
        WorldGenRegistration.a();
    }
}

package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.entity.BombEntity;
import com.kampfkaiser.hellfire.entity.FlareEntity;
import com.kampfkaiser.hellfire.entity.MissileEntity;
import com.kampfkaiser.hellfire.entity.MushroomCloudEntity;
import com.kampfkaiser.hellfire.entity.NapalmFieldEntity;
import com.kampfkaiser.hellfire.entity.NuclearChargeEntity;
import com.kampfkaiser.hellfire.entity.PilotPlaneEntity;
import com.kampfkaiser.hellfire.entity.RadiationZoneEntity;
import com.kampfkaiser.hellfire.entity.StrikePlaneEntity;
import com.kampfkaiser.hellfire.entity.ThrownFlareEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {
    public static EntityType<StrikePlaneEntity> PLANE;
    public static EntityType<PilotPlaneEntity> PILOT;
    public static EntityType<BombEntity> BOMB;
    public static EntityType<MissileEntity> MISSILE;
    public static EntityType<FlareEntity> FLARE;
    public static EntityType<ThrownFlareEntity> THROWN;
    public static EntityType<NuclearChargeEntity> CHARGE;
    public static EntityType<MushroomCloudEntity> CLOUD;
    public static EntityType<NapalmFieldEntity> NAPALM;
    public static EntityType<RadiationZoneEntity> RADIATION;

    private ModEntities() {}

    public static void init() {
        PLANE = register("strike_plane", StrikePlaneEntity::new, 2.8F, 0.9F, 160, 1);
        PILOT = register("pilot_plane", PilotPlaneEntity::new, 2.4F, 0.8F, 160, 1);
        BOMB = register("bomb", BombEntity::new, 0.5F, 0.5F, 80, 1);
        MISSILE = register("missile", MissileEntity::new, 0.45F, 0.45F, 96, 1);
        FLARE = register("flare", FlareEntity::new, 0.4F, 1.2F, 80, 2);
        THROWN = register("thrown_flare", ThrownFlareEntity::new, 0.35F, 0.35F, 80, 1);
        CHARGE = register("nuclear_charge", NuclearChargeEntity::new, 0.8F, 0.8F, 64, 2);
        CLOUD = register("mushroom_cloud", MushroomCloudEntity::new, 1.0F, 2.0F, 160, 2);
        NAPALM = register("napalm_field", NapalmFieldEntity::new, 0.6F, 0.6F, 80, 4);
        RADIATION = register("radiation", RadiationZoneEntity::new, 0.6F, 0.6F, 160, 10);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType.EntityFactory<T> factory, float width, float height, int range, int interval) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, HellfireMod.id(name));
        EntityType<T> type = EntityType.Builder.of(factory, MobCategory.MISC).sized(width, height).clientTrackingRange(range).updateInterval(interval).build(key);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
        return type;
    }

    public static EntityType<StrikePlaneEntity> planeType() { return PLANE; }
    public static EntityType<PilotPlaneEntity> pilotType() { return PILOT; }
    public static EntityType<BombEntity> bombType() { return BOMB; }
    public static EntityType<MissileEntity> missileType() { return MISSILE; }
    public static EntityType<FlareEntity> flareType() { return FLARE; }
    public static EntityType<ThrownFlareEntity> thrownType() { return THROWN; }
    public static EntityType<NuclearChargeEntity> chargeType() { return CHARGE; }
    public static EntityType<MushroomCloudEntity> cloudType() { return CLOUD; }
    public static EntityType<NapalmFieldEntity> napalmType() { return NAPALM; }
    public static EntityType<RadiationZoneEntity> radiationType() { return RADIATION; }
}

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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, HellfireMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<StrikePlaneEntity>> PLANE = ENTITIES.register("strike_plane",
            () -> EntityType.Builder.of(StrikePlaneEntity::new, MobCategory.MISC).sized(2.8F, 0.9F).clientTrackingRange(160).updateInterval(1).build("hellfire:strike_plane"));
    public static final DeferredHolder<EntityType<?>, EntityType<PilotPlaneEntity>> PILOT = ENTITIES.register("pilot_plane",
            () -> EntityType.Builder.of(PilotPlaneEntity::new, MobCategory.MISC).sized(2.4F, 0.8F).clientTrackingRange(160).updateInterval(1).build("hellfire:pilot_plane"));
    public static final DeferredHolder<EntityType<?>, EntityType<BombEntity>> BOMB = ENTITIES.register("bomb",
            () -> EntityType.Builder.of(BombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(80).updateInterval(1).build("hellfire:bomb"));
    public static final DeferredHolder<EntityType<?>, EntityType<MissileEntity>> MISSILE = ENTITIES.register("missile",
            () -> EntityType.Builder.of(MissileEntity::new, MobCategory.MISC).sized(0.45F, 0.45F).clientTrackingRange(96).updateInterval(1).build("hellfire:missile"));
    public static final DeferredHolder<EntityType<?>, EntityType<FlareEntity>> FLARE = ENTITIES.register("flare",
            () -> EntityType.Builder.of(FlareEntity::new, MobCategory.MISC).sized(0.4F, 1.2F).clientTrackingRange(80).updateInterval(2).build("hellfire:flare"));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownFlareEntity>> THROWN = ENTITIES.register("thrown_flare",
            () -> EntityType.Builder.of(ThrownFlareEntity::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(80).updateInterval(1).build("hellfire:thrown_flare"));
    public static final DeferredHolder<EntityType<?>, EntityType<NuclearChargeEntity>> CHARGE = ENTITIES.register("nuclear_charge",
            () -> EntityType.Builder.of(NuclearChargeEntity::new, MobCategory.MISC).sized(0.8F, 0.8F).clientTrackingRange(64).updateInterval(2).build("hellfire:nuclear_charge"));
    public static final DeferredHolder<EntityType<?>, EntityType<MushroomCloudEntity>> CLOUD = ENTITIES.register("mushroom_cloud",
            () -> EntityType.Builder.of(MushroomCloudEntity::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(160).updateInterval(2).build("hellfire:mushroom_cloud"));
    public static final DeferredHolder<EntityType<?>, EntityType<NapalmFieldEntity>> NAPALM = ENTITIES.register("napalm_field",
            () -> EntityType.Builder.of(NapalmFieldEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).clientTrackingRange(80).updateInterval(4).build("hellfire:napalm_field"));
    public static final DeferredHolder<EntityType<?>, EntityType<RadiationZoneEntity>> RADIATION = ENTITIES.register("radiation",
            () -> EntityType.Builder.of(RadiationZoneEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).clientTrackingRange(160).updateInterval(10).build("hellfire:radiation"));

    private ModEntities() {}

    public static EntityType<StrikePlaneEntity> planeType() { return PLANE.get(); }
    public static EntityType<PilotPlaneEntity> pilotType() { return PILOT.get(); }
    public static EntityType<BombEntity> bombType() { return BOMB.get(); }
    public static EntityType<MissileEntity> missileType() { return MISSILE.get(); }
    public static EntityType<FlareEntity> flareType() { return FLARE.get(); }
    public static EntityType<ThrownFlareEntity> thrownType() { return THROWN.get(); }
    public static EntityType<NuclearChargeEntity> chargeType() { return CHARGE.get(); }
    public static EntityType<MushroomCloudEntity> cloudType() { return CLOUD.get(); }
    public static EntityType<NapalmFieldEntity> napalmType() { return NAPALM.get(); }
    public static EntityType<RadiationZoneEntity> radiationType() { return RADIATION.get(); }
}

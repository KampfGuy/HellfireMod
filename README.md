# Hellfire

A Minecraft mod that adds fictional military gear. Every blast is a normal Minecraft explosion (the same system TNT uses), plus vanilla particles and sounds. Nothing here describes a real weapon, a real yield, or a real procedure.

The plane, bomb, missile, charge, and flare models are original box models written for this mod. They were not downloaded and they are not copied from Mojang.

## Items

Creative tab: **Hellfire**.

| Item | How to use |
| --- | --- |
| Strike Radio | Right-click a block to call a plane. It flies in from off-screen, drops several bombs along a line, and leaves. Sneak-use calls the strike on the nearest flare instead (within 64 blocks). The radio is not consumed. |
| Flare | Right-click to place a glowing marker with a tall particle beam so you can see where a strike will land. |
| Tactical Missile | Right-click to launch a missile at the block you are looking at. It flies there and creates one large Minecraft explosion. Sneak-use toggles an incendiary flag stored on that stack (`HellfireIgnite`). Fires are off unless you toggle the stack or turn on `missileStartsFires` in the config. |
| Nuclear Charge | Right-click a block face to place and arm it. After about 10 seconds (smoke and flame warning) it detonates: several Minecraft explosions, a stylized particle mushroom cloud, a knockback shockwave, a ring of fire, and a crater of air where stone and dirt were. Bedrock is never removed. The crater is about 30–40 blocks across by default. The tooltip is the warning: it devastates a large area. Treat it as a creative / testing item on a world you care about. |

## Config

Values are in-game block counts and Minecraft explosion power. They are capped (crater radius 8–22, explosion powers up to 6) so a survival world stays playable.

- NeoForge and Forge: `config/hellfire-common.toml` after you launch the game once.
- Fabric: `config/hellfire.properties` (written on first launch).

## Install

Built jars:

- `hellfire-neoforge-1.21.1-1.0.0.jar` — Minecraft **1.21.1**, NeoForge **21.1.250** or newer on the 21.1 line. Put the jar in the `mods` folder of a NeoForge 1.21.1 instance.
- `hellfire-forge-1.20.1-1.0.0.jar` — Minecraft **1.20.1**, Forge **47.4.0** or newer on the 47.x line. Put the jar in the `mods` folder.
- `hellfire-fabric-26.3-1.0.0.jar` — Minecraft **26.3**, Fabric Loader **0.19.5**, and Fabric API **0.161.0+26.3** (or a newer 26.3 build). Install the loader, then put this jar and Fabric API in `mods`.

## Build

NeoForge 1.21.1 and Fabric 26.3 share the root Gradle 9.6 build:

```bash
./gradlew :neoforge-1.21.1:build :fabric-26.3:build
```

Forge 1.20.1 uses ForgeGradle 6, which needs Gradle 8.8, so it is its own build:

```bash
cd forge-1.20.1 && ./gradlew build
```

You need a JDK that can run Gradle (21 is fine for the root build; the Forge build targets Java 17 and Fabric targets Java 25 via toolchains).

## Layout

- `neoforge-1.21.1` and `fabric-26.3` are Gradle subprojects of the root build.
- `forge-1.20.1` is a separate Gradle project in this same repo, because one wrapper cannot satisfy both ForgeGradle 6 and Fabric Loom for 26.3.

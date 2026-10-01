# Hellfire

A Minecraft mod with fictional strike flares and a plane you can fly. Every blast is a normal Minecraft explosion (the same system TNT uses), plus vanilla particles and sounds. Nothing here describes a real weapon, a real yield, or a real procedure. The green zone after a nuclear blast is just called radiation in the game. It is not a model of anything real.

The plane, bomb, missile, and marker models are original box models written for this mod. They were not downloaded and they are not copied from Mojang. Thrown flares are a flat card of that flare's item sprite. The item icons are the red, yellow, orange, blue, and green sprites supplied for this version.

## Items

Creative tab: **Hellfire**. It contains the Radio and the four flares only. None of them are consumed.

The four flares charge like a bow: hold use, then release to throw. A short tap does nothing. On impact the flare calls its strike. It does not place a block.

| Item | How to use |
| --- | --- |
| Missile Strike (red) | Throw it. A red beam marks the landing and a missile comes down for one large Minecraft explosion. |
| Nuclear Strike (yellow) | Throw it. A bomber (the same simple 3D plane) flies over slowly and drops one nuclear bomb on that spot. The blast is about 1.5 times the 1.1.0 size (default crater radius 27 blocks, still capped, bedrock kept): staged explosions, a particle mushroom cloud, knockback, a fire ring, and a crater. Afterward a green zone lingers for about a minute. Living things inside take damage over time. The tooltip calls that radiation. |
| Napalm Strike (orange) | Throw it. A plane flies over and drops a line of fire. Fire blocks stay lit on the surface for about 30 seconds, and entities are set on fire. There is no crater. |
| Bombing Run (blue) | Throw it. A plane flies over and drops a short line of normal bombs (several medium Minecraft explosions). |
| Radio (green) | Not thrown. Right-click to mount a plane. WASD thrusts and strafes. Looking turns the plane; looking up climbs and looking down dives. Sneak descends and does not kick you off. Use drops the current bomb. Sneak-use cycles bombs, napalm, a missile, or the nuclear bomb. The nuclear bomb is one drop, then that option cools down for 30 seconds. Jump gets off: you are set on the ground under the plane with fall distance cleared. If there is no ground close below, you get a short slow-falling effect instead. Switching off the Radio also lands you the same way. |

## Config

Values are in-game block counts and Minecraft explosion power. They are capped (crater radius 8–30, explosion powers up to 6) so a survival world stays playable.

- NeoForge and Forge: `config/hellfire-common.toml` after you launch the game once.
- Fabric: `config/hellfire.properties` (written on first launch).

1.2.0 raises the default `nuclearRadius` from 18 to 27 and `explosionPower` from 3.2 to 4.8. If you already launched 1.1.0, delete those config files or edit the numbers yourself. Old files keep the old values.

## Install

Built jars (1.2.0):

- `hellfire-neoforge-1.21.1-1.2.0.jar` — Minecraft **1.21.1**, NeoForge **21.1.250** or newer on the 21.1 line.
- `hellfire-forge-1.20.1-1.2.0.jar` — Minecraft **1.20.1**, Forge **47.4.0** or newer on the 47.x line.
- `hellfire-fabric-26.3-1.2.0.jar` — Minecraft **26.3**, Fabric Loader **0.19.5**, and Fabric API **0.161.0+26.3** (or a newer 26.3 build).

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
